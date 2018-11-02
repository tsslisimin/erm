/**
 * Copyright (c) 2015-2020 Coomia Network Technology Co., Ltd. All Rights Reserved.
 *
 * <p>
 * This software is licensed not sold. Use or reproduction of this software by any unauthorized
 * individual or entity is strictly prohibited. This software is the confidential and proprietary
 * information of Coomia Network Technology Co., Ltd. Disclosure of such confidential information
 * and shall use it only in accordance with the terms of the license agreement you entered into with
 * Coomia Network Technology Co., Ltd.
 *
 * <p>
 * Coomia Network Technology Co., Ltd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY
 * OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Coomia Network
 * Technology Co., Ltd. SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ANY DERIVATIVES THEREOF.
 */
package com.coomia.erm.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.coomia.erm.constants.SystemConstants;
import com.coomia.erm.dao.ErmFundedDao;
import com.coomia.erm.dao.ErmSchoolFundedDao;
import com.coomia.erm.entity.ErmFundedEntity;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.service.ErmFundedInfoService;
import com.coomia.erm.service.ErmStudentService;
import com.google.common.collect.Maps;

/**
 * @author spancer 定时任务，用来更新学校项目的状态，至其结束 date: 2017年11月27日 下午2:55:07 <br/>
 */

@Component
public class ErmWorlFlowTask {
	@Autowired
	private ErmSchoolFundedDao ermSchoolFundedDao;

	@Autowired
	private ErmFundedDao ermFundedDao;

	@Autowired
	private ErmFundedInfoService ermFundedInfoService;

	@Autowired
	private ErmStudentService ermStudentService;

	/**
	 * 每年9.1号更新学生的年级
	 */
	@Scheduled(cron = "0 55 23 1 9 ?") // 每年的 9/1号 23:55:00执行
	public void stuGradeUpgrade() throws InterruptedException {
		// 更新学生信息，年龄-年级
		this.ermStudentService.upgradeStudentInfo();
	}

	// 每天凌晨一点执行，公示五天修改状态为教育局审批
	@Scheduled(cron = "0 0 1 * * ?")
	public void timerToNow() {
		System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 查询所有学校项目状态为学校公示（状态值9）的列表
		Map<String, Object> map = Maps.newHashMap();
		map.put("status", FundStatus.SCHANNOUNCEMENT.getCode());
		List<ErmSchoolFundedEntity> entitys = this.ermSchoolFundedDao.queryList(map);
		if (entitys != null && entitys.size() > 0) {
			// 判断当前日期和公示日期，如果大于5个工作日，修改学校项目状态为教育局审批
			for (ErmSchoolFundedEntity entity : entitys) {
				Date publicDate = entity.getPublicDate();
				// 判断是否公示了5天
				boolean hasChangeStatus = checkPublicDate(publicDate, 5);
				if (hasChangeStatus) {
					entity.setStatus(FundStatus.EBAUDIT.getCode());
					//修改公示状态为可提交
					entity.setPublicStatus(1);
					this.ermSchoolFundedDao.update(entity);
					System.out.println("------修改状态--------");
				}
			}
		}
	}

	// 判断是否公示了 limit day
	private boolean checkPublicDate(Date publicDate, Integer limitDay) {
		if(publicDate == null) {
			return false;
		}
		System.out.println("------开始判断日期--------");
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(publicDate);
		cal.add(Calendar.DATE, limitDay);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(now);
		if (cal.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal.get(Calendar.MONTH) == cal2.get(Calendar.MONDAY)
				&& cal.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		return false;
	}

	@Scheduled(cron = "0 */30 * * * ? ") // every 30min
	public void finishFund() throws InterruptedException {

		int ebId = SystemConstants.ebId;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ebId", ebId);
		map.put("status", FundStatus.NOTICE.getCode());
		// 查询所有的项目(快要结束的项目）
		List<ErmSchoolFundedEntity> list = ermSchoolFundedDao.querySchFundList(map);
		for (ErmSchoolFundedEntity ermSchoolFundedEntity : list) {
			int schFundId = ermSchoolFundedEntity.getId();
			int remainCounter = ermFundedInfoService.queryRemainUndeltCount(schFundId,
					FundStatus.NOTICE.getCode());
			if (remainCounter == 0) {
				// update fundInfo.status 至 结束
				ermSchoolFundedEntity.setStatus(FundStatus.CLOSE.getCode());
				ermSchoolFundedEntity.setUpdateTime(new Date());
				ermSchoolFundedEntity.setUpdator("AI");
				ermSchoolFundedDao.update(ermSchoolFundedEntity);
			}
		}

		// 查询这个项目下的学校是否都完成了这个项目的实施
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("ebId", ebId);
		List<ErmFundedEntity> funds = ermFundedDao.queryList(p);
		for (ErmFundedEntity fund : funds) {
			// 如果项目未结束，但各学校都完成了该项目的执行，刚这个项目整体要结束掉了。
			if (fund.getStatus() != FundStatus.CLOSE.getCode()) {
				int fundId = fund.getId();
				Map<String, Object> pp = new HashMap<String, Object>();
				pp.put("ebId", ebId);
				pp.put("fundId", fundId);
				pp.put("fundNotStatus", FundStatus.CLOSE.getCode());
				int openFundCount = ermSchoolFundedDao.querySchFundListTotal(pp);
				if (openFundCount == 0) {
					// update fundstatus to close
				    Map<String, Object> qq = new HashMap<String, Object>();
				    qq.put("ebId", ebId);
				    qq.put("fundId", fundId);
				    int hasCount = ermSchoolFundedDao.querySchFundListTotal(qq);
				    //教育局刚启动的项目，有可能还没分配到学校，这时候，是不能置为关闭的。
				    if(hasCount > 0)
				    {
				      fund.setStatus(FundStatus.CLOSE.getCode());
				      ermFundedDao.update(fund);
				    }
				}
			}
		}
	}
}

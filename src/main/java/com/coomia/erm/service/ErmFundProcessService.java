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
package com.coomia.erm.service;

import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmAdjustParam;
import com.coomia.erm.entity.ErmQueryObject;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.util.PageUtils;

/**
 * @author spancer date: 2017年11月9日 下午2:22:54 <br/>
 */
public interface ErmFundProcessService {

  /**
   * FundStatus.AIMATCH 阶段 这个阶段主要做两件事情， 1. 根据操作员录入的受助人员信息（fund_info表），结合规则给学生自动评分，
   * 流程状态变更为FundStatus.OPERAUDIT 2. 生成audit log 记录 状态为OPERAUDIT。 executeAutoMatch TODO
   * 
   * @param schFunId TODO
   * @param stuId TODO
   */
  void executeAutoMatch(int schFunId, int stuId, int weightTotal);

  /**
   * 根据学校ID， 项目ID, 状态 查询待审批的用户列表 这个用户列表信息， 需要把动态的字段信息加载出来。 queryAuditUsers TODO
   * 
   * @param schId
   * @param fundId
   * @param status
   * @param query 分页参数
   * @return
   */
  PageUtils queryToAuditUsers(ErmQueryObject queryObj);

  /**
   * 得到待审核的列表 表头 queryToAuditUsersHeader TODO
   * 
   * @param schId
   * @param fundId
   * @param status
   * @return
   */
  List<ColumnHeader> queryToAuditUsersHeader(Integer schId, Integer fundId);

  /**
   * 根据项目ID及状态查询待审批列表 queryAuditableUser TODO
   * 
   * @param schFundId
   * @param status
   * @return
   */
  List<Map<String, Object>> queryAuditableUser(int schFundId, int status);



  /**
   * 查询待拟定的学生列表 queryToFundStudents:(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选). TODO(这里描述这个方法的执行流程 –
   * 可选). TODO(这里描述这个方法的使用方法 – 可选). TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param queryObj
   * @return
   * @since JDK 1.6
   */
  PageUtils queryToFundStudents(ErmQueryObject queryObj);


  /**
   * 通过学生的一堆值，打一个０～１００的分数．
   * score:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param stuVals
   * @param weightTotal TODO
   * @return
   * @since JDK 1.6
   */
  public double score(List<Map<String, Object>> stuVals, int weightTotal);
  /**
   * 给这个学生打分， 步骤： 1. 查询出来这个学生所在的学校的指标。 2. 根据这个学生的指标值，给这个学生打分。 doScoreStudent:(这里用一句话描述这个方法的作用).
   * TODO(这里描述这个方法适用条件 – 可选). TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选). TODO(这里描述这个方法的注意事项
   * – 可选).
   *
   * @author spancer
   * @param stuId
   * @return
   * @since JDK 1.6
   */

  double doScoreAndUpdateStudent(int stuId, int weightTotal);
  
  public double score(int stuId, int weightTotal);


  /**
   * 删除困难学生 删除的时候，就把困难等级置为0 delDiffStudent:(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选). TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param stuId
   * @return
   * @since JDK 1.6
   */
  boolean delDiffStudent(int stuId);

  /**
   * 修改困难学生， 主要是修改录入他的指标值。
   * 这里要考虑的是： 困难学生的指标有很多历史值，
   * 但是我们要先去查询出当前学校的指标项。
   * updateDiffStudent:(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选). TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选). TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param stuId
   * @return
   * @since JDK 1.6
   */
  boolean updateDiffStudent(int stuId);
  
  
  

  /**
   * 标识困难学生，因为学校指标变更了。 doTagStudent:(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选). TODO(这里描述这个方法的执行流程 –
   * 可选). TODO(这里描述这个方法的使用方法 – 可选). TODO(这里描述这个方法的注意事项 – 可选).
   * 
   * 步骤： 1. 根据困难等级查询现有的学生， 困难等级>0的即为要变更的学生群体。 2. 将这些学生的flag状态置为1.
   *
   * @author spancer
   * @param schId
   * @return
   * @since JDK 1.6
   */
  boolean doTagStudentAsNeedReview(int schId);
  
  /**
   * 
   * doAdjustDiffLevel:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param param
   * @return
   * @since JDK 1.6
   */
  boolean doAdjustDiffLevel(ErmAdjustParam param);
  /**
   * 操作员拟定资助名单
   * doConfirmStudents:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param remainCount TODO
   * @param isPass TODO
   * @param remark TODO
   * @return
   * @since JDK 1.6
   */
  void doAuditStus(int schFundId, List<Integer> stuIds, FundStatus currentStatusSucceed, FundStatus nextStatus, String operator, int remainCount, boolean isPass, String remark);
  /**
   * 查询申报对象名单、资助对象名单
   * @param ermQueryObject
   * @return
   */
  PageUtils queryApply(ErmQueryObject ermQueryObject);

  void doEbAuditStus(int schFundId, List<Integer> stuIds, FundStatus currentStatusSucceed, FundStatus nextStatus, String operator, int remainCount, boolean isPass, String remark);
  
  /**
   * 当前学校的默认权重值。
   * maxDefaultScore:(这里用一句话描述这个方法的作用). 
   * TODO(这里描述这个方法适用条件 – 可选).
   * TODO(这里描述这个方法的执行流程 – 可选).
   * TODO(这里描述这个方法的使用方法 – 可选).
   * TODO(这里描述这个方法的注意事项 – 可选).
   *
   * @author spancer
   * @param schId
   * @return
   * @since JDK 1.6
   */
  public int querySchKPISize(int schId);


  void deleteApply(long id);
}

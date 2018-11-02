package com.coomia.erm.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.coomia.erm.constants.DictConstants;

public class CardUtil {

	/**
	 * 根据身份证的号码算出当前身份证持有者的性别和年龄 18位身份证
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getCarInfo(String CardCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		String year = CardCode.substring(6).substring(0, 4);// 得到年份
		String month = CardCode.substring(10).substring(0, 2);// 得到月份
		String day = CardCode.substring(12).substring(0, 2);// 得到日
		StringBuffer birthDay = new StringBuffer();
		birthDay.append(year).append(month).append(day);
		Integer sex = DictConstants.GENDER_MALE;
		if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
			sex = DictConstants.GENDER_FEMALE;
		}
		Calendar cal = Calendar.getInstance();
		int fyear = cal.get(Calendar.YEAR);
		int fmonth = cal.get(Calendar.MONTH);
		int age = 0;
		if (Integer.parseInt(month) <= fmonth) { // 当前月份大于用户出身的月份表示已过生
			age = fyear - Integer.parseInt(year) + 1;
		} else {// 当前用户还没过生
			age = fyear - Integer.parseInt(year);
		}
		map.put("gender", sex);
		map.put("age", age);
		map.put("birth", birthDay.toString());
		return map;
	}

	public static void main(String[] args) throws Exception {
	}
}


/**
 * Project Name:erm File Name:StringUtil.java Package Name:com.coomia.erm.util
 * Date:2017年12月22日下午1:48:34 Copyright (c) 2017, spancer.ray All Rights Reserved.
 *
 */

package com.coomia.erm.util;

import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:StringUtil Function: TODO ADD FUNCTION. Reason: TODO ADD REASON. Date: 2017年12月22日
 * 下午1:48:34
 * 
 * @author spancer
 * @version
 * @since JDK 1.6
 * @see
 */
public class StringUtil {

  // 取出字符串中的年
  public static int getYear(String sememster) {
    Calendar date = Calendar.getInstance();
    int result = date.get(Calendar.YEAR);
    if (null == sememster)
      return result;
    else {
      String regex = "(\\d+)";
      Pattern pattern = Pattern.compile(regex);
      Matcher m = pattern.matcher(sememster);
      if (m.find()) {
        String dateStr = m.group();
        result = Integer.parseInt(dateStr);
      }
      return result;
    }

  }


  public static String[] chars = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
      "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2",
      "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
      "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

  public static String generateShortUuid() {
    StringBuffer shortBuffer = new StringBuffer();
    String uuid = UUID.randomUUID().toString().replace("-", "");
    for (int i = 0; i < 8; i++) {
      String str = uuid.substring(i * 4, i * 4 + 4);
      int x = Integer.parseInt(str, 16);
      shortBuffer.append(chars[x % 0x3E]);
    }
    return shortBuffer.toString();

  }

}


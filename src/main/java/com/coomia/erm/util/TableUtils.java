package com.coomia.erm.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：yyl .
 * describe:
 * email:zxc7752948@qq.com
 * date: 2018/7/1 0001
 */
public class TableUtils {
    private static char[] chars = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U',
            'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L',
            'Z', 'X', 'C', 'V', 'B', 'N', 'M'};

    /**
     * 生成建表語句
     *
     * @param bean
     * @return
     */

    public static String genCreateTableSql(String bean) {


        List<String> beanPropertyList = getBeanPropertyList(bean);

        StringBuffer sb = new StringBuffer("create table " + parseChar(getBeanName(bean)) + "(\n");
        for (int i = 0; i < beanPropertyList.size(); i++) {
            String string = beanPropertyList.get(i);
            String[] propertys = string.split("`");

            if (!propertys[1].equals("tableName") && !propertys[1].equals("param") && !propertys[0].equals("List")) {

                if (i == 0) {
                    sb.append(String.format("   %s bigint primary key auto_increment,\n", propertys[1]));

                } else {

                    if (propertys[0].equals("Integer")) {

                        sb.append("   " + propertys[1] + " int  comment '',\n");

                    }
                    if (propertys[0].equals("Long")) {

                        sb.append("   " + propertys[1] + " bigint  comment '',\n");

                    } else if (propertys[0].equals("String")) {

                        sb.append("   " + propertys[1] + " varchar(200) default '' comment '',\n");

                    } else if (propertys[0].equals("Double")) {

                        sb.append("   " + propertys[1] + " double(10,2)  comment '',\n");

                    } else if (propertys[0].equals("Date")) {

                        sb.append("   " + propertys[1] + " datetime comment '',\n");

                    }

                }

            }

        }

        sb.append(")");

        sb.deleteCharAt(sb.lastIndexOf(","));

        return sb.toString();

    }

    private static String parseChar(String beanName) {
        StringBuilder sb = new StringBuilder();
        char[] array = beanName.toCharArray();
        for (int i = 0; i < array.length; i++) {
            char c = array[i];
            boolean ret = find(c);
            if (ret && i > 0) {
                sb.append("_").append(("" + c).toLowerCase());
            } else {
                sb.append(("" + c).toLowerCase());
            }

        }

        return sb.toString();
    }

    private static boolean find(char c) {
        for (char a : chars) {
            if (a == c) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getBeanPropertyList(String bean) {

        try {

            Class clz = Class.forName(bean);

            Field[] strs = clz.getDeclaredFields();

            List<String> propertyList = new ArrayList<String>();

            for (int i = 0; i < strs.length; i++) {

                String protype = strs[i].getType().toString();

                propertyList.add(protype.substring(protype.lastIndexOf(".") + 1) + "`" + parseChar(strs[i].getName()));

            }

            return propertyList;

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

            return null;

        }

    }

    public static String getBeanName(String bean) {

        try {

            Class clz = Class.forName(bean);

            String clzStr = clz.toString();

            //得到类名

            String className = clzStr.substring(clzStr.lastIndexOf(".") + 1);
            return parseChar(className).replace("_entity", "");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

            return "";

        }

    }
}

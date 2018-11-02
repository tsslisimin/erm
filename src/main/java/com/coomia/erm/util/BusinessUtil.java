package com.coomia.erm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmExportCloumn;

/**
 * 业务处理工具类
 * 
 * @author Administrator
 *
 */
public class BusinessUtil {
	
	/**
	 * 根据角色ID获取角色名
	 * @param roleId
	 * @return
	 */
	public static String getRoleNameByRoleId(Integer roleId) {
		String roleName = null;
		switch (roleId) {
		case 1:
			roleName = "管理员";
			break;
		case 2:
			roleName = "操作员";
			break;
		case 3:
			roleName = "校长";
			break;
		case 4:
			roleName = "家长";
			break;
		default:
			break;
		}
		return roleName;
	}
	
	public static String getUUId() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return String.format("%015d", hashCodeV);
	}
	
	public static String getCodeUUID() {
		String uuid = getUUId();
		StringBuffer tmp = new StringBuffer();
		int random = (int)(Math.random()*26);
		tmp.append(DictConstants.CHARS.charAt(random));
		tmp.append(uuid);
		return tmp.toString();
	}
	
	public static String getRandCode(int num) {
		StringBuffer tmp = new StringBuffer();
		if(num <= 0) {
			tmp.append(DictConstants.CHARS.charAt(0));
			return tmp.toString();
		}
		for(int i=0;i<num;i++) {
			int random = (int)(Math.random()*26);
			tmp.append(DictConstants.CHARS.charAt(random));
		}
		return tmp.toString();
	}
	
	public static List<ColumnHeader> getTableHeader(String [] keys,String [] names,boolean [] valids){
		if(keys.length == names.length && keys.length == valids.length) {
			List<ColumnHeader> headers = new ArrayList<ColumnHeader>();
			for(int i=0;i<keys.length;i++) {
				ColumnHeader header = new ColumnHeader(keys[i],names[i],valids[i]);
				headers.add(header);
			}
			return headers;
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		System.out.println(getCodeUUID());
	}

	public static List<ColumnHeader> getTableHeaderByDynamicCloumn(List<ErmExportCloumn> dynamicCloumnEntity) {
		if(dynamicCloumnEntity == null) {
			return null;
		}
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		boolean [] validsArr = new boolean[dynamicCloumnEntity.size()];
		for(int i = 0;i<dynamicCloumnEntity.size();i++) {
			ErmExportCloumn entity = dynamicCloumnEntity.get(i);
			String key = entity.getKey();
			String value = entity.getValue();
			keys.add(key);
			values.add(value);
			validsArr[i] = true;
		}
		
		
		return getTableHeader(keys.toArray(new String[keys.size()]), values.toArray(new String[values.size()]), validsArr);
	}

}

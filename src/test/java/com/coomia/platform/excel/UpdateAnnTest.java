package com.coomia.platform.excel;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * Description: TODO {一句话描述类是干什么的}
 * 
 * @author leequn
 * @date: 2017年11月13日 下午3:47:38
 * @version 1.0
 * @since JDK 1.8
 */

public class UpdateAnnTest {
	
	public static CtClass ReadTest() throws NotFoundException {
		ClassPool pool = ClassPool.getDefault();
		// 获取要修改的类的所有信息
		CtClass ct = pool.get("com.coomia.platform.excel.InnerVo");
		// 获取类中的方法
		CtMethod[] cms = ct.getDeclaredMethods();
		// 获取第一个方法（因为只有一个方法）
		CtMethod cm = cms[0];
		System.out.println("方法名称====" + cm.getName());
		// 获取方法信息
		MethodInfo methodInfo = cm.getMethodInfo();
		// 获取类里的em属性
		CtField cf = ct.getField("key");
		// 获取属性信息
		FieldInfo fieldInfo = cf.getFieldInfo();
		System.out.println("属性名称===" + cf.getName());

		// 获取注解属性
		AnnotationsAttribute attribute = (AnnotationsAttribute) fieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
		System.out.println(attribute);
		// 获取注解
		
		
		Annotation [] annotations = attribute.getAnnotations(); 
		
//		System.out.println(annotation+":"+annotation3+"::"+annotation4);
//		// 获取注解的值
//		String text = ((StringMemberValue) annotation.getMemberValue("dicCode")).getValue();
//		System.out.println("注解名称===" + text);

		MethodInfo minInfo = cm.getMethodInfo();
		ConstPool cp = fieldInfo.getConstPool();
		// 获取注解信息
		AnnotationsAttribute attribute2 = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
		for(int i=0;i<annotations.length;i++) {
			annotations[i].addMemberValue("dicCode", new StringMemberValue("yy", cp));
		}
		// 修改名称为unitName的注解
		attribute2.setAnnotations(annotations);
		minInfo.addAttribute(attribute2);

		// 打印修改后方法
		Annotation annotation2 = attribute2.getAnnotation("org.jplus.hyberbin.excel.annotation.output.OutputDicConfig");
		String text1 = ((StringMemberValue) annotation2.getMemberValue("dicCode")).getValue();

		System.out.println("修改后的注解名称===" + text1);

		return ct;

	}

	public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
		System.out.println(ReadTest().toClass());
		Class vo = ReadTest().toClass();
		InnerVo ss = (InnerVo) vo.newInstance();
		
	}

}

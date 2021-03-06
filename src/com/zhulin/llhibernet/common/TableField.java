package com.zhulin.llhibernet.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)// 作用于局部变量
@Retention(RetentionPolicy.RUNTIME)// 在运行时有效（即运行时保留)
public @interface TableField {
	String key() default "";
	String type() default "";
	int length() default 1;
	boolean primaryKey() default false;
}

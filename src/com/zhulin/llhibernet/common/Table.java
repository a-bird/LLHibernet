package com.zhulin.llhibernet.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//作用于类上
@Retention(RetentionPolicy.RUNTIME)//在运行时有效（即运行时保留)
public @interface Table {
	String tableName() default "";
}

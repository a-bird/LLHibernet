package com.zhulin.llhibernet.common;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表的放射
 * 
 * @author zhulin
 * 
 * @param <T>
 */
public class TableReflect<T> {
	private static String PACK_NAME = "com.zhulin.llhibernet.module";
	private String className;

	private static String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS %s(%s)";

	public TableReflect(String className) {
		this.className = className;
	}

	/**
	 * 初始化class
	 * 
	 * @return
	 */
	private Class<?> InitClass() {
		Class<?> cla = null;
		try {
			cla = Class.forName(PACK_NAME + "." + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return cla;
	}

	/**
	 * 反射出sql语句
	 * 
	 * @return
	 */
	public String ReflectSql() {
		String ret = "";

		String tableName = null;

		Class<?> clazz = InitClass();
		Table table = clazz.getAnnotation(Table.class);
		tableName = table.tableName();

		if ("".equals(tableName)) {
			return ret;
		}
		// 解析成实体
		List<TableFieldUnit> litUnit = new ArrayList<TableFieldUnit>();

		Field[] fields = clazz.getFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];

			TableFieldUnit unit = getTableFieldUnit(field);
			if (unit != null) {
				litUnit.add(unit);
			}
		}
		// 主键sql
		String primaryKeySql = getPrimaryKeySql(litUnit);
		// 字段的sql
		String fieldSql = getFieldSql(litUnit);

		String tableFieldSql = "";
		if (!"".equals(primaryKeySql)) {
			tableFieldSql = fieldSql + primaryKeySql;
		} else {
			fieldSql = fieldSql.substring(0, fieldSql.length() - 1);
			tableFieldSql = fieldSql;
		}

		return String.format(CREATE_TABLE_SQL, tableName, tableFieldSql);
	}

	/**
	 * 转换成字段的实体
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	private TableFieldUnit getTableFieldUnit(Field field) {
		TableFieldUnit unit = null;

		TableField tableField = field.getAnnotation(TableField.class);

		if (tableField == null) {
			return unit;
		}

		int length = 0;// 字段长度
		String fieldName = field.getName();// 字段名称
		Type type = field.getGenericType();
		String typeName = type.toString().replace(" ", "").replace("class", "")
				.replace("java.lang.", "");// 字段类型 需要转换
		boolean primaryKey = tableField.primaryKey();

		// 若注解字段名为空 则获取属性名
		if (!"".equals(tableField.key())) {
			fieldName = tableField.key();
		}
		// 设置值
		length = tableField.length();
		if (tableField.length() == 0) {
			length = 20;
		}
		// 设置数据类型
		if (!"".equals(tableField.type())) {
			typeName = tableField.type();
		}
		String fieldType = getSqliteType(typeName);
		//赋值成对象
		unit = new TableFieldUnit();
		unit.setFieldName(fieldName);
		unit.setFieldType(fieldType);
		unit.setFieldLength(length);
		unit.setPrimartKey(primaryKey);

		return unit;
	}

	/**
	 * 获取字段的sql
	 * 
	 * @param litUnit
	 * @return
	 */
	private String getFieldSql(List<TableFieldUnit> litUnit) {
		String ret = "";

		for (int i = 0; i < litUnit.size(); i++) {
			TableFieldUnit unit = litUnit.get(i);
			if (unit != null) {
				ret += unit.getFieldName() + " " + unit.getFieldType() + "("
						+ unit.getFieldLength() + ")" + ",";
			}
		}

		return ret;
	}

	/**
	 * 获取主键的sql
	 * 
	 * @param primaryKeys
	 * @return
	 */
	private String getPrimaryKeySql(List<TableFieldUnit> litUnit) {
		String ret = "";

		// 拼接主键sql
		String keySql = "CONSTRAINT [] PRIMARY KEY (%s)";
		String strKeys = "";
		// 最后一个值 去掉逗号
		for (int i = 0; i < litUnit.size(); i++) {
			if (litUnit.get(i).isPrimartKey()) {
				if (i == litUnit.size() - 1) {
					strKeys = litUnit.get(i).getFieldName();
				} else {
					strKeys = litUnit.get(i).getFieldName() + ",";
				}
			}
		}

		if ("".equals(strKeys)) {
			ret = String.format(keySql, strKeys);
		}

		return ret;
	}

	/**
	 * 转换成数据库字段数据类型
	 * 
	 * @param type
	 * @return
	 */
	private String getSqliteType(String type) {
		String ret = "";

		switch (type) {

		case "String":
			ret = "text";
			break;

		case "int":
			ret = "int";
			break;

		case "boolean":
			ret = "int";
			break;

		case "Date":
			ret = "datetime";
			break;

		default:
			ret = "text";
			break;
		}

		return ret;
	}

}

class TableFieldUnit {
	private String fieldName;
	private String fieldType;
	private int fieldLength;
	private boolean isPrimartKey;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}

	public boolean isPrimartKey() {
		return isPrimartKey;
	}

	public void setPrimartKey(boolean isPrimartKey) {
		this.isPrimartKey = isPrimartKey;
	}

}

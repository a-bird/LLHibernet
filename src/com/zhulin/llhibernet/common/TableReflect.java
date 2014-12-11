package com.zhulin.llhibernet.common;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * ���ݿ��ķ���
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
	 * ��ʼ��class
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
	 * �����sql���
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
		// ������ʵ��
		List<TableFieldUnit> litUnit = new ArrayList<TableFieldUnit>();

		Field[] fields = clazz.getFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];

			TableFieldUnit unit = getTableFieldUnit(field);
			if (unit != null) {
				litUnit.add(unit);
			}
		}
		// ����sql
		String primaryKeySql = getPrimaryKeySql(litUnit);
		// �ֶε�sql
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
	 * ת�����ֶε�ʵ��
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

		int length = 0;// �ֶγ���
		String fieldName = field.getName();// �ֶ�����
		Type type = field.getGenericType();
		String typeName = type.toString().replace(" ", "").replace("class", "")
				.replace("java.lang.", "");// �ֶ����� ��Ҫת��
		boolean primaryKey = tableField.primaryKey();

		// ��ע���ֶ���Ϊ�� ���ȡ������
		if (!"".equals(tableField.key())) {
			fieldName = tableField.key();
		}
		// ����ֵ
		length = tableField.length();
		if (tableField.length() == 0) {
			length = 20;
		}
		// ������������
		if (!"".equals(tableField.type())) {
			typeName = tableField.type();
		}
		String fieldType = getSqliteType(typeName);
		//��ֵ�ɶ���
		unit = new TableFieldUnit();
		unit.setFieldName(fieldName);
		unit.setFieldType(fieldType);
		unit.setFieldLength(length);
		unit.setPrimartKey(primaryKey);

		return unit;
	}

	/**
	 * ��ȡ�ֶε�sql
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
	 * ��ȡ������sql
	 * 
	 * @param primaryKeys
	 * @return
	 */
	private String getPrimaryKeySql(List<TableFieldUnit> litUnit) {
		String ret = "";

		// ƴ������sql
		String keySql = "CONSTRAINT [] PRIMARY KEY (%s)";
		String strKeys = "";
		// ���һ��ֵ ȥ������
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
	 * ת�������ݿ��ֶ���������
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

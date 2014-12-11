package com.zhulin.llhibernet.config;

import java.util.List;

public class SqliteConfigureInfo {
	
	public static String strDbName = "dbName";
	public static String strDbIp = "dbIp";
	public static String strTableClass = "tableClass";
	
	public String dbName;
	public String dbIp;
	public List<String> tableClass;
	
	public List<String> getTableClass() {
		return tableClass;
	}
	public void setTableClass(List<String> tableClass) {
		this.tableClass = tableClass;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbIp() {
		return dbIp;
	}
	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}
}

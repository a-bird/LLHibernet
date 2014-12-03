package com.zhulin.llhibernet.config;

public class SqliteConfigureInfo {
	
	public static String strDbName = "dbName";
	public static String strDbIp = "dbIp";
	
	public String dbName;
	public String dbIp;
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

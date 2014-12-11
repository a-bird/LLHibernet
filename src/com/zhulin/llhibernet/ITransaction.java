package com.zhulin.llhibernet;

public interface ITransaction {
	public void begin();
	public void commit();
	public void rollback();
}

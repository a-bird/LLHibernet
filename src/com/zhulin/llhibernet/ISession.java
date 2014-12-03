package com.zhulin.llhibernet;

public interface ISession <T>{
	public Boolean open();
	public Boolean close();
	
	public void beginTransaction();
	public void save(T t);
	
	public Boolean isOpen();
}

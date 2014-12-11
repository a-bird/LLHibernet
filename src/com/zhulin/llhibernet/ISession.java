package com.zhulin.llhibernet;

public interface ISession <T>{
	public Boolean isOpen();
	public void close();
	
	public void beginTransaction();
	
	public void save(T t);
	public void delete(T t);
	public void update(T t);
	public void createQuery();
	
}

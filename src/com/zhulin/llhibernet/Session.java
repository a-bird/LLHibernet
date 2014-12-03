package com.zhulin.llhibernet;

public class Session<T> implements ISession<T>{

	private IDataBase dataBase;
	private ISession<T> session;
	
	public ISession<T> GetInstance() {
		if (session == null) {
			session = new Session<T>();
		}
		
		return session;
	}
	
	@Override
	public Boolean open() {
		return null;
	}

	@Override
	public Boolean close() {
		return null;
	}

	@Override
	public void beginTransaction() {
		
	}

	@Override
	public void save(T t) {
		
	}

	@Override
	public Boolean isOpen() {
		return null;
	}

}

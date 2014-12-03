package com.zhulin.llhibernet;

public class SessionFactory {

	private IDataBase mDataBase;

	public SessionFactory(IDataBase mDataBase) {
		this.mDataBase = mDataBase;
	}

	public <T> ISession<T> openSession() {
		return new Session<T>();
	}
}

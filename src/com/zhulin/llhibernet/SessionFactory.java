package com.zhulin.llhibernet;

public class SessionFactory {

	private ISqliteDataBase mDataBase;

	public SessionFactory(ISqliteDataBase mDataBase) {
		this.mDataBase = mDataBase;
	}

	public <T> ISession<T> openSession() {
		return new Session<T>();
	}
}

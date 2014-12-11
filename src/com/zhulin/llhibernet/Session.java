package com.zhulin.llhibernet;

import android.database.sqlite.SQLiteDatabase;

public class Session<T> implements ISession<T> {

	private ISqliteDataBase dataBase;
	private ISession<T> session;
	private SQLiteDatabase writableDatabase;
	private ITransaction transaction;

	/**
	 * 获取单例
	 * 
	 * @param dataBase
	 * @return
	 */
	public ISession<T> GetInstance(ISqliteDataBase dataBase) {
		if (session == null) {
			session = new Session<T>();
		}

		this.dataBase = dataBase;
		if (this.dataBase != null) {
			writableDatabase = this.dataBase.GetWritableDatabase();
		}
		transaction = new Transaction(dataBase);
		return session;
	}

	/**
	 * 获取事务
	 * 
	 * @return
	 */
	public ITransaction getTransaction() {
		return transaction;
	}

	/**
	 * 关闭数据库
	 */
	@Override
	public void close() {
		if (writableDatabase != null && writableDatabase.isOpen()) {
			writableDatabase.close();
		}
	}

	/**
	 * 开始事务
	 */
	@Override
	public void beginTransaction() {
		if (transaction != null) {
			transaction.begin();
		}
	}

	/**
	 * 数据库是否打开
	 */
	@Override
	public Boolean isOpen() {
		if (writableDatabase != null) {
			return writableDatabase.isOpen();
		}
		return false;
	}

	/**
	 * 保存数据
	 */
	@Override
	public void save(T t) {

	}

	/**
	 * 删除数据
	 */
	@Override
	public void delete(T t) {

	}

	/**
	 * 创建查询
	 */
	@Override
	public void createQuery() {

	}

	/**
	 * 更新数据
	 */
	@Override
	public void update(T t) {

	}

}

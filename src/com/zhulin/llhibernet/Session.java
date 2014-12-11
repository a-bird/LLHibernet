package com.zhulin.llhibernet;

import android.database.sqlite.SQLiteDatabase;

public class Session<T> implements ISession<T> {

	private ISqliteDataBase dataBase;
	private ISession<T> session;
	private SQLiteDatabase writableDatabase;
	private ITransaction transaction;

	/**
	 * ��ȡ����
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
	 * ��ȡ����
	 * 
	 * @return
	 */
	public ITransaction getTransaction() {
		return transaction;
	}

	/**
	 * �ر����ݿ�
	 */
	@Override
	public void close() {
		if (writableDatabase != null && writableDatabase.isOpen()) {
			writableDatabase.close();
		}
	}

	/**
	 * ��ʼ����
	 */
	@Override
	public void beginTransaction() {
		if (transaction != null) {
			transaction.begin();
		}
	}

	/**
	 * ���ݿ��Ƿ��
	 */
	@Override
	public Boolean isOpen() {
		if (writableDatabase != null) {
			return writableDatabase.isOpen();
		}
		return false;
	}

	/**
	 * ��������
	 */
	@Override
	public void save(T t) {

	}

	/**
	 * ɾ������
	 */
	@Override
	public void delete(T t) {

	}

	/**
	 * ������ѯ
	 */
	@Override
	public void createQuery() {

	}

	/**
	 * ��������
	 */
	@Override
	public void update(T t) {

	}

}

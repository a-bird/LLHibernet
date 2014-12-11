package com.zhulin.llhibernet;

import android.database.sqlite.SQLiteDatabase;

public class Transaction implements ITransaction {

	private SQLiteDatabase writableDatabase;

	public Transaction(ISqliteDataBase dataBase) {
		if (dataBase != null) {
			writableDatabase = dataBase.GetWritableDatabase();
		}
	}

	@Override
	public void begin() {
		if (writableDatabase != null && !writableDatabase.inTransaction()) {
			writableDatabase.beginTransaction();
		}
	}

	@Override
	public void commit() {
		if (writableDatabase != null && writableDatabase.inTransaction()) {
			writableDatabase.endTransaction();
		}
	}

	@Override
	public void rollback() {
		
	}

}

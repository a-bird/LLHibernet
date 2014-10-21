package com.zhulin.llhibernet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDB extends SQLiteOpenHelper implements IDataBase {
	
	private static final int DATABASEVERSION = 2;// Êý¾Ý¿â°æ±¾
	
	private static String DateBaseName;
	
	private IDataBase DB = null;
	
	private SQLiteDatabase mSQLiteDatabase;
	private SqliteDB(Context context) {
		super(context, DateBaseName, null, DATABASEVERSION);
	}

	@Override
	public IDataBase GetInstance(Context context,String DBName) {
		DateBaseName = DBName;
		if (DB == null) {
			DB = new SqliteDB(context);
		}
		return DB;
	}

	@Override
	public Cursor Query(String strSql) {
		mSQLiteDatabase = this.getReadableDatabase();
		
		return null;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}

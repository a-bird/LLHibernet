package com.zhulin.llhibernet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteDB extends SQLiteOpenHelper implements IDataBase {

	private static final int DATABASEVERSION = 2;// Êý¾Ý¿â°æ±¾

	private static String DateBaseName;

	private static IDataBase DB = null;

	private SQLiteDatabase mSQLiteDatabase;

	private SqliteDB(Context context) {
		super(context, DateBaseName, null, DATABASEVERSION);
		Log.i("SqliteDB", "new SqliteDB!");
	}

	public static IDataBase GetInstance(Context context, String DBName) {
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
		Log.i("SqliteDB", "onCreate!");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.i("SqliteDB", "onUpgrade!");

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.i("SqliteDB", "onOpen!");
	}

}

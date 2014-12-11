package com.zhulin.llhibernet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhulin.llhibernet.config.SqliteConfigureInfo;

public class SqliteDataBase implements ISqliteDataBase{
	
	private static SqliteDataBase db;
	private SqliteHelper helper;
	
	@SuppressLint("NewApi")
	private SqliteDataBase(Context context, SqliteConfigureInfo info) {
		if (context != null) {
			helper = SqliteHelper.GetInstance(context, info);
		}
	}
	
	public static SqliteDataBase GetInstance(Context context, SqliteConfigureInfo info) {
		if (db == null) {
			db = new SqliteDataBase(context, info);
		}
		
		return db;
	}
	
	@Override
	public SQLiteDatabase GetReadableDatabase() {
		SQLiteDatabase dataBase = null;
		if (helper != null) {
			dataBase = helper.getReadableDatabase();
		}
		return dataBase;
	}

	@Override
	public SQLiteDatabase GetWritableDatabase() {
		SQLiteDatabase dataBase = null;
		if (helper != null) {
			dataBase = helper.getWritableDatabase();
		}
		
		return dataBase;
	}

}

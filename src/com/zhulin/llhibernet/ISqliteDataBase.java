package com.zhulin.llhibernet;

import android.database.sqlite.SQLiteDatabase;

public interface ISqliteDataBase {
	public SQLiteDatabase GetReadableDatabase();
	public SQLiteDatabase GetWritableDatabase();
	
}

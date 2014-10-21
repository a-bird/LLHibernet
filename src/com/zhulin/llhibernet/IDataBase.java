package com.zhulin.llhibernet;

import android.content.Context;
import android.database.Cursor;

public interface IDataBase {
	IDataBase GetInstance(Context context,String DBName);
	Cursor Query(String strSql);
}

package com.zhulin.llhibernet;

import android.database.Cursor;

public interface IDataBase {
	Cursor Query(String strSql);
}

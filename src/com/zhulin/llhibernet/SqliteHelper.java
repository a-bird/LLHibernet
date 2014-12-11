package com.zhulin.llhibernet;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zhulin.llhibernet.common.TableReflect;
import com.zhulin.llhibernet.config.SqliteConfigureInfo;
import com.zhulin.llhibernet.module.User;

public class SqliteHelper extends SQLiteOpenHelper {

	private static final int DATABASEVERSION = 1;// ���ݿ�汾

	private static SqliteConfigureInfo configureInfo;
	private static SqliteHelper DB = null;

	/**
	 * ��ʼ�� �������ݿ�
	 * 
	 * @param context
	 */
	private SqliteHelper(Context context) {
		super(context, configureInfo.dbName, null, DATABASEVERSION);
		Log.i("SqliteDB", "new SqliteDB!");
	}

	/**
	 * ��ȡ����
	 * 
	 * @param context
	 * @param DBName
	 * @return
	 */
	public static SqliteHelper GetInstance(Context context,
			SqliteConfigureInfo info) {
		configureInfo = info;
		if (DB == null) {
			DB = new SqliteHelper(context);
		}
		return DB;
	}

	/**
	 * ��ȡ�ɶ�ȡ���ݿ�
	 * 
	 * @return
	 */
	public SQLiteDatabase GetReadableSQLiteDatabase() {
		return this.getReadableDatabase();
	}

	/**
	 * ������
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		Log.i("SqliteDB", "onCreate!");
		
		List<String> litClazz = configureInfo.tableClass;
		if (!litClazz.isEmpty()) {
			for (int j = 0; j < litClazz.size(); j++) {
				TableReflect<User> ref = new TableReflect<User>(litClazz.get(j));
				Log.w("Hibernet", ref.ReflectSql());
				arg0.execSQL(ref.ReflectSql());
			}
		}
	}

	/**
	 * �������ݿ�
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.i("SqliteDB", "onUpgrade!");

	}

	/**
	 * �����ݿ�
	 */
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.i("SqliteDB", "onOpen!");
	}

}

package com.zhulin.llhibernet.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;

import com.zhulin.llhibernet.ISqliteDataBase;
import com.zhulin.llhibernet.SessionFactory;
import com.zhulin.llhibernet.SqliteDataBase;

public class SqliteConfiguration implements IConfiguration {

	private String configureName = "SqliteConfigure.json";

	public ISqliteDataBase mDataBase;
	private Context mContext;

	public SqliteConfiguration(Context context) {
		this.mContext = context;
	}

	/**
	 * 读取配置文件config.xml
	 * 
	 * @return
	 * @throws IOException
	 */
	public IConfiguration configure() {
		SqliteConfigureInfo configureInfo = readConfiger();

		if (configureInfo.dbName != null) {
			mDataBase = SqliteDataBase.GetInstance(mContext, configureInfo);
		}

		return this;
	}

	/**
	 * 创建会话工厂
	 * 
	 * @return
	 */
	public SessionFactory buildSessionFactory() {
		return new SessionFactory(mDataBase);
	}

	/**
	 * 读取配置信息
	 * 
	 * @return
	 */
	private SqliteConfigureInfo readConfiger() {
		SqliteConfigureInfo configureInfo = new SqliteConfigureInfo();
		AssetManager am = mContext.getResources().getAssets();
		// File file = new File("SqliteConfigure.json");

		try {
			int charRead = 0;
			int fullIndex = 0;
			char[] bChar = new char[20];
			char[] fullChar = new char[2048];

			// 读取文件
			InputStreamReader reader = new InputStreamReader(am.open(configureName));
			while ((charRead = reader.read(bChar)) != -1) {
				System.arraycopy(bChar, 0, fullChar, fullIndex, charRead);
				fullIndex += charRead;
			}

			// 转换成json
			String strRead = String.valueOf(fullChar);
			if (strRead.length() != 0) {
				JSONObject json = new JSONObject(strRead);
				configureInfo.dbName = (String) getJsonValue(json,
						SqliteConfigureInfo.strDbName);
				configureInfo.dbIp = (String) getJsonValue(json,
						SqliteConfigureInfo.strDbIp);
				configureInfo.tableClass = getTableClass((String) getJsonValue(
						json, SqliteConfigureInfo.strTableClass));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return configureInfo;
	}

	/**
	 * 转换成list
	 * 
	 * @param classes
	 * @return
	 */
	@SuppressLint("NewApi")
	private List<String> getTableClass(String classes) {
		List<String> tableClasses = new ArrayList<String>();

		if (!classes.isEmpty()) {
			String[] temClassess = classes.split(",");
			for (String str : temClassess) {
				tableClasses.add(str);
			}
		}

		return tableClasses;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	private Object getJsonValue(JSONObject json, String key) {
		Object value = null;
		try {
			if (!json.isNull(key)) {
				value = json.get(key);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return value;
	}

}

package com.zhulin.llhibernet.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.zhulin.llhibernet.IDataBase;
import com.zhulin.llhibernet.SessionFactory;
import com.zhulin.llhibernet.SqliteDB;

public class SqliteConfiguration implements IConfiguration {

	public IDataBase mDataBase;
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
			mDataBase = SqliteDB.GetInstance(mContext, configureInfo.dbName);
		}
		
		return this;
	}
	
	public SessionFactory buildSessionFactory() {
		return new SessionFactory(mDataBase);
	}
	
	
	/**
	 *读取配置信息 
	 * @return
	 */
	private SqliteConfigureInfo readConfiger() {
		SqliteConfigureInfo configureInfo = new SqliteConfigureInfo();
		File file = new File("SqliteConfigure.json");

		if (file.isFile()) {
			try {
				int charRead = 0;
				char[] bChar = new char[20];
				char[] fullChar = new char[2048];

				// 读取文件
				InputStreamReader isr = new InputStreamReader(
						new FileInputStream(file));
				while ((charRead = isr.read(bChar)) != -1) {
					System.arraycopy(bChar, 0, fullChar, 0, bChar.length);
				}

				// 转换成json
				String strRead = String.valueOf(fullChar);
				if (strRead.length() != 0) {
					JSONObject json = new JSONObject(strRead);
					configureInfo.dbName = (String) getJsonValue(json,
							SqliteConfigureInfo.strDbName);
					configureInfo.dbIp = (String) getJsonValue(json,
							SqliteConfigureInfo.strDbIp);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return configureInfo;
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

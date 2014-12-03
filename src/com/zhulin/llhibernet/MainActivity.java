package com.zhulin.llhibernet;

import com.zhulin.llhebernet.R;
import com.zhulin.llhibernet.config.IConfiguration;
import com.zhulin.llhibernet.config.SqliteConfiguration;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		IConfiguration configure = new SqliteConfiguration(this).configure();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

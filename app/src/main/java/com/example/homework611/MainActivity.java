package com.example.homework611;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	private static final String LOG_TAG = MainActivity.class.getSimpleName();
	private static final String BUNDLE_CODE_KEY = "bundle_code";

	private static boolean doLogPostMethods = true;

	private TextView logLabel;
	private TextView bundleIndicatorLabel;

	private int bundleCode = (int) (Math.random() * 0x10000); // up to 0xFFFF

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		logLabel = findViewById(R.id.logLabel);
		CheckBox logPostMethodsCheckbox = findViewById(R.id.logPostMethodsCheckbox);
		bundleIndicatorLabel = findViewById(R.id.bundleIndicatorLabel);

		logPostMethodsCheckbox.setOnCheckedChangeListener((v, ch) -> doLogPostMethods = ch);
		logPostMethodsCheckbox.setChecked(doLogPostMethods);

		log();
	}

	private void log(Object... args) {
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		StringBuilder s = new StringBuilder(methodName);
		if (s.toString().startsWith("on"))
			s.delete(0, 2);
		if (args.length != 0) {
			s.append("(").append(args[0]);
			for (int i = 1; i < args.length; i++)
				s.append(", ").append(args[i]);
			s.append(")");
		}

		SimpleArrayLog.log(s.toString());
		Log.v(LOG_TAG, s.toString());
		logLabel.setText(SimpleArrayLog.get());
	}

	private String loadBundleCode(Bundle b) {
		String ret;
		if (b == null) {
			ret = "null";
		} else if (!b.containsKey(BUNDLE_CODE_KEY)) {
			ret = "none";
		} else {
			bundleCode = b.getInt(BUNDLE_CODE_KEY);
			ret = String.format("%04X", bundleCode).toUpperCase();
		}
		bundleIndicatorLabel.setText("Bundle code: " + ret);
		return ret;
	}

	@Override
	protected void onStart() {
		super.onStart();
		log();
	}

	@Override
	protected void onResume() {
		super.onResume();
		log();
	}

	@Override
	protected void onPause() {
		super.onPause();
		log();
	}

	@Override
	protected void onStop() {
		super.onStop();
		log();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		log();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		log();
	}

	@Override
	public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onPostCreate(savedInstanceState, persistentState);
		if (doLogPostMethods) log();
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (doLogPostMethods) log();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		log(keyCode, event.getFlags(), true);
		return true;
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		log(keyCode, event.getFlags(), true);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		log();
	}

	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_CODE_KEY, bundleCode);
		log(loadBundleCode(outState));
	}

	@Override
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		log(loadBundleCode(savedInstanceState));
	}
}

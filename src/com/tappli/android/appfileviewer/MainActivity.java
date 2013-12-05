package com.tappli.android.appfileviewer;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String ARG_DIR = "dir";

	private ListView listView;
	private TextView emptyView;
	private FileListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_file_viewer);

		emptyView = (TextView) findViewById(R.id.empty);
		listView = (ListView) findViewById(R.id.file_list);
		listView.setEmptyView(emptyView);

		File rootDir = getRootDir(getIntent());
		adapter = new FileListAdapter(this, rootDir);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onItemClickListener);

		setTitle(rootDir.getAbsolutePath());
	}

	private File getRootDir(Intent intent) {
		if (intent == null) {
			return getFilesDir().getParentFile();
		}

		String dir = intent.getStringExtra(ARG_DIR);
		if (TextUtils.isEmpty(dir)) {
			return getFilesDir().getParentFile();
		}

		File file = new File(dir);
		if (!file.exists()) {
			return getFilesDir().getParentFile();
		}
		if (!file.isDirectory()) {
			return getFilesDir().getParentFile();
		}

		return file;
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			File file = adapter.getItem(position);
			if (!file.isDirectory()) {
				return;
			}

			startActivity(MainActivity.createIntent(MainActivity.this, file));
		}
	};

	public static Intent createIntent(Context context, File dir) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(ARG_DIR, dir.getAbsolutePath());
		return intent;
	}
}

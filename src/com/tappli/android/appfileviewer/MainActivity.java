package com.tappli.android.appfileviewer;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_file_viewer);

		showAppRoot();
	}

	private void showAppRoot() {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		FileListFragment fragment = FileListFragment.newInstance();
		transaction.add(R.id.content, fragment);
		transaction.commit();
	}

	@Override
	public void onBackPressed() {
		if (getFragmentManager().getBackStackEntryCount() > 0) {
			getFragmentManager().popBackStack();
			return;
		}
		super.onBackPressed();
	}
}

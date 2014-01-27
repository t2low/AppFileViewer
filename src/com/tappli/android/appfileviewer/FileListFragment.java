package com.tappli.android.appfileviewer;

import java.io.File;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class FileListFragment extends Fragment {
	private static final String ARG_DIR = "dir";

	private ListView listView;
	private TextView emptyView;
	private FileListAdapter adapter;

	public static FileListFragment newInstance(String dir) {
		FileListFragment fragment = new FileListFragment();
		Bundle args = new Bundle();
		args.putString(ARG_DIR, dir);
		fragment.setArguments(args);
		return fragment;
	}

	public static FileListFragment newInstance(File dir) {
		String path = (dir == null) ? null : dir.getAbsolutePath();
		return newInstance(path);
	}

	public static FileListFragment newInstance() {
		return newInstance((String) null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_file_list, null);

		emptyView = (TextView) view.findViewById(R.id.empty);
		listView = (ListView) view.findViewById(R.id.file_list);
		listView.setEmptyView(emptyView);

		File currDir = getCurrentDir();
		adapter = new FileListAdapter(getActivity(), currDir);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onItemClickListener);

		TextView pathView = (TextView) view.findViewById(R.id.curr_path);
		pathView.setText(currDir.getAbsolutePath());

		return view;
	}

	private File getCurrentDir() {
		String dir = getArguments().getString(ARG_DIR);
		if (TextUtils.isEmpty(dir)) {
			return getAppRoot();
		}

		File file = new File(dir);
		if (!file.exists()) {
			return getAppRoot();
		}
		if (!file.isDirectory()) {
			return getAppRoot();
		}

		return file;
	}

	private File getAppRoot() {
		return getActivity().getFilesDir().getParentFile();
	}

	private void showNextDir(File dir) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Fragment fragment = FileListFragment.newInstance(dir);
		transaction.replace(getId(), fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			File file = adapter.getItem(position);
			if (!file.isDirectory()) {
				return;
			}
			showNextDir(file);
		}
	};
}

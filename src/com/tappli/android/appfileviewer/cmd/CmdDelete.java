package com.tappli.android.appfileviewer.cmd;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

import com.tappli.android.appfileviewer.R;

public class CmdDelete implements Command {
	private static final long serialVersionUID = 8879831213268172957L;

	@Override
	public int getNameResourceId() {
		return R.string.cmd_delete;
	}

	@Override
	public DialogFragment createFragment(Fragment parent, int requestCode, File file) {
		return DeleteFragment.newInstance(parent, requestCode, file);
	}

	public static class DeleteFragment extends DialogFragment {
		private static final String ARG_FILE = "file";

		public static DeleteFragment newInstance(Fragment parent, int requestCode, File file) {
			DeleteFragment fragment = new DeleteFragment();
			Bundle args = new Bundle();
			args.putSerializable(ARG_FILE, file);
			fragment.setArguments(args);
			fragment.setTargetFragment(parent, requestCode);
			return fragment;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Delete");
			builder.setMessage("削除しても良いですか？");
			builder.setPositiveButton(android.R.string.ok, onClickListener);
			builder.setNegativeButton(android.R.string.cancel, onClickListener);
			return builder.create();
		}

		private OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					File file = (File) getArguments().getSerializable(ARG_FILE);
					int result = Activity.RESULT_CANCELED;
					if (delete(file)) {
						result = Activity.RESULT_OK;
					}
					callback(result, null);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
				default:
					callback(Activity.RESULT_CANCELED, null);
					break;
				}
			}
		};

		private boolean delete(File file) {
			if (file == null || !file.exists()) {
				return false;
			}
			return file.delete();
		}

		private void callback(int resultCode, Intent data) {
			Fragment fragment = getTargetFragment();
			if (fragment != null) {
				fragment.onActivityResult(getTargetRequestCode(), resultCode, data);
				return;
			}
		}
	}
}

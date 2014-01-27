package com.tappli.android.appfileviewer.cmd;

import java.io.File;
import java.io.Serializable;

import android.app.DialogFragment;
import android.app.Fragment;

public interface Command extends Serializable {
	public int getNameResourceId();

	public DialogFragment createFragment(Fragment parent, int requestCode, File file);
}

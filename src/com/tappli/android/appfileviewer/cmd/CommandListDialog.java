package com.tappli.android.appfileviewer.cmd;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommandListDialog extends DialogFragment {
	private static final int REQ_CODE = 1000;
	private static final int REQ_COMMAND = 1001;

	private static final String ARG_FILE = "file";
	private static final String ARG_COMMANDS = "commands";

	public static CommandListDialog newInstance(Fragment targetFragment, File file, Command... commands) {
		CommandListDialog dialog = new CommandListDialog();
		Bundle args = new Bundle();
		args.putSerializable(ARG_FILE, file);
		args.putSerializable(ARG_COMMANDS, commands);
		dialog.setArguments(args);
		dialog.setTargetFragment(targetFragment, REQ_CODE);
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Command[] commands = (Command[]) getArguments().getSerializable(ARG_COMMANDS);
		CommandListAdapter adapter = new CommandListAdapter(getActivity(), commands);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setAdapter(adapter, clickListener);
		return builder.create();
	}

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			File file = (File) getArguments().getSerializable(ARG_FILE);
			Command[] commands = (Command[]) getArguments().getSerializable(ARG_COMMANDS);
			Command cmd = commands[which];

			DialogFragment fragment = cmd.createFragment(getTargetFragment(), REQ_COMMAND, file);
			fragment.show(getFragmentManager(), "command");
		}
	};

	private static class CommandListAdapter extends ArrayAdapter<Command> {

		public CommandListAdapter(Context context, Command[] commands) {
			super(context, android.R.layout.simple_list_item_1, commands);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			Holder holder = (Holder) view.getTag();
			if (holder == null) {
				holder = new Holder(view);
				view.setTag(holder);
			}

			int nameId = getItem(position).getNameResourceId();
			holder.textView.setText(nameId);

			return view;
		}

		private static class Holder {
			TextView textView;

			Holder(View view) {
				textView = (TextView) view.findViewById(android.R.id.text1);
			}
		}
	}
}

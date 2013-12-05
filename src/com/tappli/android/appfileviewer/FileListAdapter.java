package com.tappli.android.appfileviewer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class FileListAdapter extends ArrayAdapter<File> {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

	public FileListAdapter(Context context, File root) {
		super(context, R.layout.item, R.id.name, root.listFiles());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		Holder holder = (Holder) view.getTag();
		if (holder == null) {
			holder = new Holder(view);
			view.setTag(holder);
		}

		File file = getItem(position);

		int colorId = file.isDirectory() ? R.color.color_for_directory : R.color.color_for_file;
		holder.image.setBackgroundColor(getContext().getResources().getColor(colorId));
		holder.name.setText(file.getName());
		holder.d.setTime(file.lastModified());
		holder.date.setText(dateFormat.format(holder.d));
		holder.size.setText(Long.toString(file.length()));

		return view;
	}

	private static class Holder {
		private final View image;
		private final TextView name;
		private final TextView date;
		private final TextView size;
		private final Date d = new Date();

		Holder(View view) {
			image = view.findViewById(R.id.image);
			name = (TextView) view.findViewById(R.id.name);
			date = (TextView) view.findViewById(R.id.date);
			size = (TextView) view.findViewById(R.id.size);
		}
	}
}

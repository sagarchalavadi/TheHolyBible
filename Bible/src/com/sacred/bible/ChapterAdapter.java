package com.sacred.bible;

import java.lang.ref.WeakReference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChapterAdapter extends BaseAdapter {

	private WeakReference<BibleActivity> activityReference;

	private int size;

	public ChapterAdapter(BibleActivity xmlParsingActivity, int size) {
		activityReference = new WeakReference<BibleActivity>(xmlParsingActivity);
		this.size = size;
	}

	@Override
	public int getCount() {
		return size;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			if (activityReference.get() != null) {
				convertView = (View) LayoutInflater.from(
						activityReference.get()).inflate(R.layout.book, null);
				holder = new Holder();
				holder.chapterInfo = (TextView) convertView
						.findViewById(R.id.bookName);
				convertView.setTag(holder);
			}
		}
		holder = (Holder) convertView.getTag();
		if (holder != null)
			holder.chapterInfo.setText("Chapter No: " + (position + 1));

		return convertView;
	}

	public void dataChange() {
		notifyDataSetChanged();
	}

	class Holder {
		public TextView chapterInfo;
	}

}
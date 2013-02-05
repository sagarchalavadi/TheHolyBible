package com.sacred.bible;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContentAdapter extends BaseAdapter {

	private WeakReference<BibleActivity> activityReference;

	private ArrayList<String> verses;

	public ContentAdapter(BibleActivity xmlParsingActivity, ArrayList<String> arrayList) {
		activityReference = new WeakReference<BibleActivity>(xmlParsingActivity);
		verses = arrayList;
	}

	@Override
	public int getCount() {
		return verses.size();
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
			holder = new Holder();
			if (activityReference.get() != null) {
				convertView = LayoutInflater.from(activityReference.get()).inflate(R.layout.quotes, null);
				holder.textView = (TextView) convertView.findViewById(R.id.quote);
				convertView.setTag(holder);
			}
		}
		holder = (Holder) convertView.getTag();
		if (holder != null) {
			holder.textView.setText("" + (position + 1) + ". " + verses.get(position));
		}
		return convertView;
	}

	public void dataChange() {
		notifyDataSetChanged();
	}

	class Holder {
		public TextView textView;
	}
}

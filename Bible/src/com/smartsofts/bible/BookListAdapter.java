package com.smartsofts.bible;

import java.lang.ref.WeakReference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookListAdapter extends BaseAdapter {

	private WeakReference<BibleActivity> activityReference;

	private String[] bookList;

	public BookListAdapter(BibleActivity xmlParsingActivity, String[] booksList) {
		activityReference = new WeakReference<BibleActivity>(xmlParsingActivity);
		this.bookList = booksList;
	}

	@Override
	public int getCount() {
		return bookList.length;
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
				convertView = LayoutInflater.from(activityReference.get()).inflate(R.layout.book, null);
				holder.name = (TextView) convertView.findViewById(R.id.bookName);
				holder.number = (TextView) convertView.findViewById(R.id.bookNumber);
				convertView.setTag(holder);
			}
		}
		holder = (Holder) convertView.getTag();
		if (holder != null) {
			// holder.number.setText("" + (position + 1) + ". ");
			holder.name.setText(bookList[position].trim());
		}

		return convertView;
	}

	public void dataChange() {
		notifyDataSetChanged();
	}

	class Holder {
		public TextView number;
		public TextView name;
	}

}

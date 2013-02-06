package com.smartsofts.bible;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;
import com.smartsofts.bible.util.Util;

public class BibleActivity extends SherlockFragmentActivity implements BgDataHandler, AdListener,
		ActionBar.OnNavigationListener {
	public class ActionBarMenuAdapter extends BaseAdapter {
		Context context;
		int textViewResourceId;
		ArrayList<String> data;
		LayoutInflater inflater;

		public ActionBarMenuAdapter(BibleActivity bibleActivity, int textViewResourceId,
				ArrayList<String> mActionBarListItems) {
			this.data = mActionBarListItems;
			inflater = (LayoutInflater) bibleActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.context = bibleActivity;
			this.textViewResourceId = textViewResourceId;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			ViewHolder holder;
			if (v == null) {
				v = inflater.inflate(R.layout.action_bar_list, null);
				holder = new ViewHolder();
				holder.itemName = (TextView) v.findViewById(R.id.username);
				v.setTag(holder);
			} else
				holder = (ViewHolder) v.getTag();

			final String item = data.get(position);
			if (item != null) {
				holder.itemName.setText(item);
			}
			return v;
		}

	}

	public static class ViewHolder {
		public TextView itemName;
		public ImageView image;
	}

	/** Called when the activity is first created. */
	private TextView bookInfo;
	private ListView content;
	private ContentAdapter contentAdapter;
	private int chapterNumber = 0;
	private ArrayList<ArrayList<String>> bibleData;
	private AdView adView;
	private String[] booksArray;
	private String[] booksArray1;
	private boolean isNewTestament;
	private int currentBookNumber;
	private ArrayList<String> mActionBarListItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		bookInfo = (TextView) findViewById(R.id.book_info);
		content = (ListView) findViewById(R.id.bibleContent);

		mActionBarListItems = new ArrayList<String>();
		mActionBarListItems.add("Choose");
		mActionBarListItems.add("Testamanet");
		mActionBarListItems.add("Book");
		mActionBarListItems.add("Chapter");

		ActionBarMenuAdapter list = new ActionBarMenuAdapter(this, R.layout.action_bar_list, mActionBarListItems);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);

		Util util = new Util();
		booksArray = util.getBookArray();
		booksArray1 = util.getBookArray1();

		adView = (AdView) BibleActivity.this.findViewById(R.id.adView);
		LinearLayout layout = (LinearLayout) findViewById(R.id.adLayout);
		layout.removeAllViews();
		layout.addView(adView);
		if (util.isOnline(this)) {
			adView.loadAd(new AdRequest());
		}

		isNewTestament = true;
		currentBookNumber = 0;
		parseFile("testament1/book1.txt");
		bookInfo.setText(booksArray[currentBookNumber] + ":" + (chapterNumber + 1));

		content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(BibleActivity.this, VerseActivity.class);
				intent.putExtra("content", bibleData.get(chapterNumber));
				intent.putExtra("clickedpostion", arg2);
				intent.putExtra("currentbookposition", currentBookNumber);
				intent.putExtra("currentchapterposition", chapterNumber);
				intent.putExtra("testamentValue", isNewTestament);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		menu.add(0, 1, 0, "Left").setIcon(R.drawable.ic_launcher).setIcon(R.drawable.previous)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0, 2, 0, "Right").setIcon(R.drawable.ic_launcher).setIcon(R.drawable.next)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		switch (itemPosition) {
		case 0:
			break;
		case 1:
			AlertDialog.Builder builder = new AlertDialog.Builder(BibleActivity.this);

			builder.setMessage("Choose Testament").setCancelable(true)
					.setPositiveButton("Old", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							if (!isNewTestament) {
								isNewTestament = true;
								currentBookNumber = 0;
								chapterNumber = 0;
								parseFile("testament1/book1.txt");
								bookInfo.setText(booksArray[0] + ":" + 1);
							}
							dialog.dismiss();
							getSupportActionBar().setSelectedNavigationItem(0);
						}
					}).setNegativeButton("New", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							if (isNewTestament) {
								isNewTestament = false;
								currentBookNumber = 0;
								chapterNumber = 0;
								parseFile("testament2/book1.txt");
								bookInfo.setText(booksArray1[0] + ":" + 1);
							}
							dialog.dismiss();
							getSupportActionBar().setSelectedNavigationItem(0);
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
			break;
		case 2:
			View bookListView = LayoutInflater.from(BibleActivity.this).inflate(R.layout.booklist, null);
			final Dialog dialog = new Dialog(BibleActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
			dialog.setContentView(bookListView);
			final ListView bookList = (ListView) bookListView.findViewById(R.id.booklist);

			BookListAdapter books = new BookListAdapter(BibleActivity.this, booksArray);
			bookList.setAdapter(books);
			bookList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if (isNewTestament) {
						if (currentBookNumber != arg2) {
							currentBookNumber = arg2;
							chapterNumber = 0;
							parseFile("testament1/book" + (arg2 + 1) + ".txt");
							bookInfo.setText(booksArray[arg2] + ":" + 1);
						}
					} else {
						if (currentBookNumber != arg2) {
							currentBookNumber = arg2;
							chapterNumber = 0;
							parseFile("testament2/book" + (arg2 + 1) + ".txt");
							bookInfo.setText(booksArray1[arg2] + ":" + 1);
						}
					}
					dialog.dismiss();
					getSupportActionBar().setSelectedNavigationItem(0);
				}
			});
			dialog.show();
			break;

		case 3:
			View chapterListView = LayoutInflater.from(BibleActivity.this).inflate(R.layout.booklist, null);
			final Dialog chapterDialog = new Dialog(BibleActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
			chapterDialog.setContentView(chapterListView);
			final ListView chapterList = (ListView) chapterListView.findViewById(R.id.booklist);

			ChapterAdapter chapters = new ChapterAdapter(BibleActivity.this, bibleData.size());
			chapterList.setAdapter(chapters);
			chapterList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if (isNewTestament) {
						chapterNumber = arg2;
						onChapterSelection(false);
						chapterDialog.dismiss();
						getSupportActionBar().setSelectedNavigationItem(0);
					} else {
						chapterNumber = arg2;
						onChapterSelection(true);
						chapterDialog.dismiss();
						getSupportActionBar().setSelectedNavigationItem(0);
					}
				}
			});
			chapterDialog.show();
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			if (chapterNumber >= 1) {
				chapterNumber--;
				changeChapter();
			}
			break;
		case 2:
			if (chapterNumber <= bibleData.size() - 2) {
				chapterNumber++;
				changeChapter();
			}
		default:
			break;
		}
		return true;
	}

	private void changeChapter() {
		contentAdapter = new ContentAdapter(BibleActivity.this, bibleData.get(chapterNumber));
		contentAdapter.dataChange();
		if (isNewTestament) {
			bookInfo.setText(booksArray[currentBookNumber] + ":" + (chapterNumber + 1));
		} else {
			bookInfo.setText(booksArray1[currentBookNumber] + ":" + (chapterNumber + 1));
		}
	}

	private void onChapterSelection(boolean isNewTestament) {
		contentAdapter = new ContentAdapter(BibleActivity.this, bibleData.get(chapterNumber));
		contentAdapter.dataChange();
		String bookName = "";
		if (isNewTestament) {
			bookName = booksArray1[currentBookNumber];
		} else {
			bookName = booksArray[currentBookNumber];
		}
		bookInfo.setText(bookName + " :" + (chapterNumber + 1));
	}

	private void parseFile(String fileName) {
		InputStream iS = null;
		try {
			iS = getResources().getAssets().open(fileName);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			if (iS != null)
				parser.parse(iS, new SaxHelper(BibleActivity.this));

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void hanldeData(ArrayList<ArrayList<String>> bookData) {
		if (bookData != null) {
			bibleData = bookData;
			contentAdapter = new ContentAdapter(BibleActivity.this, bibleData.get(0));
			content.setAdapter(contentAdapter);
		}
	}

	@Override
	public void onDismissScreen(Ad arg0) {

	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		adView.stopLoading();
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		adView.stopLoading();
	}

	@Override
	public void onPresentScreen(Ad arg0) {
	}

	@Override
	public void onReceiveAd(Ad arg0) {
	}
}

package com.smartsofts.bible;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.smartsofts.bible.util.Util;

public class VerseActivity extends Activity {

	private Button backButton;
	private TextView title;
	private TextView verse;
	private ImageView prev;
	private ImageView next;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	private ArrayList<String> verses;
	private int position;
	private int currentBookNumber;
	private int chapterNumber;
	private boolean testamentOld;
	private ZoomControls zoomControls;
	private int curentZoomValue;
	private String[] booksArray;
	private String[] booksArray1;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verse);
		backButton = (Button) findViewById(R.id.back);
		verse = (TextView) findViewById(R.id.verse);
		title = (TextView) findViewById(R.id.title);
		prev = (ImageView) findViewById(R.id.prevVerse);
		next = (ImageView) findViewById(R.id.nextVerse);
		zoomControls = (ZoomControls) findViewById(R.id.zoomcontrols);
		Util util = new Util();
		booksArray = util.getBookArray();
		booksArray1 = util.getBookArray1();
		verses = (ArrayList<String>) getIntent().getExtras().getSerializable("content");
		position = getIntent().getExtras().getInt("clickedpostion");
		currentBookNumber = getIntent().getExtras().getInt("currentbookposition");
		chapterNumber = getIntent().getExtras().getInt("currentchapterposition");
		curentZoomValue = 18;
		testamentOld = getIntent().getExtras().getBoolean("testamentValue");
		next.setVisibility(View.VISIBLE);
		prev.setVisibility(View.VISIBLE);
		if (position == 0) {
			prev.setVisibility(View.INVISIBLE);
		}
		if (position == verses.size() - 1) {
			next.setVisibility(View.INVISIBLE);
		}
		setTitle();
		verse.setText("" + (position + 1) + ". " + verses.get(position));
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};

		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				VerseActivity.this.finish();
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (position >= verses.size() - 1) {

				} else if (position == verses.size() - 2) {
					position++;
					setTitle();
					verse.setText("" + (position + 1) + ". " + verses.get(position));
					prev.setVisibility(View.VISIBLE);
					next.setVisibility(View.INVISIBLE);
				} else {
					position++;
					setTitle();
					verse.setText("" + (position + 1) + ". " + verses.get(position));
					prev.setVisibility(View.VISIBLE);
				}
			}
		});

		prev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (position <= 0) {

				} else if (position == 1) {
					position--;
					setTitle();
					verse.setText("" + (position + 1) + ". " + verses.get(position));
					prev.setVisibility(View.INVISIBLE);
					next.setVisibility(View.VISIBLE);
				} else {
					position--;
					setTitle();
					verse.setText("" + (position + 1) + ". " + verses.get(position));
					prev.setVisibility(View.VISIBLE);
					next.setVisibility(View.VISIBLE);
				}
			}
		});
		zoomControls.setIsZoomInEnabled(true);
		zoomControls.setIsZoomOutEnabled(true);
		zoomControls.setOnZoomInClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (curentZoomValue <= 28) {
					zoomControls.setIsZoomOutEnabled(true);
					curentZoomValue++;
					verse.setTextSize(curentZoomValue);
				} else {
					zoomControls.setIsZoomInEnabled(false);
				}
			}
		});

		zoomControls.setOnZoomOutClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (curentZoomValue >= 10) {
					zoomControls.setIsZoomInEnabled(true);
					curentZoomValue--;
					verse.setTextSize(curentZoomValue);
				} else {
					zoomControls.setIsZoomOutEnabled(false);
				}
			}
		});
	}

	private void setTitle() {
		if (testamentOld) {
			title.setText("Old -" + booksArray[currentBookNumber] + " -" + (chapterNumber + 1) + " -" + (position + 1));
		} else {
			title.setText("New -" + booksArray1[currentBookNumber] + " -" + (chapterNumber + 1) + " -" + (position + 1));
		}
	}

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > 250)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > 120 && Math.abs(velocityX) > 200) {
					if (position >= verses.size() - 1) {

					} else if (position == verses.size() - 2) {
						position++;
						setTitle();
						verse.setText("" + (position + 1) + ". " + verses.get(position));
						prev.setVisibility(View.VISIBLE);
						next.setVisibility(View.INVISIBLE);
					} else {
						position++;
						setTitle();
						verse.setText("" + (position + 1) + ". " + verses.get(position));
						prev.setVisibility(View.VISIBLE);
					}

				} else if (e2.getX() - e1.getX() > 120 && Math.abs(velocityX) > 200) {
					if (position <= 0) {

					} else if (position == 1) {
						position--;
						setTitle();
						verse.setText("" + (position + 1) + ". " + verses.get(position));
						prev.setVisibility(View.INVISIBLE);
						next.setVisibility(View.VISIBLE);
					} else {
						position--;
						setTitle();
						verse.setText("" + (position + 1) + ". " + verses.get(position));
						prev.setVisibility(View.VISIBLE);
						next.setVisibility(View.VISIBLE);
					}
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event))
			return true;
		else
			return false;
	}
}

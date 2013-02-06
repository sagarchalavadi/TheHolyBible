package com.smartsofts.bible.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {
	private String[] booksArray = new String[] { "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua",
			"Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra",
			"Nehemiah", "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah",
			"Lamentations", "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum",
			"Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi" };
	private String[] booksArray1 = new String[] { "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1 Corinthians",
			"2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians", "1 Thessalonians",
			"2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter",
			"1 John", "2 John", "3 John", "Jude", "Revelation" };

	public String[] getBookArray() {
		return booksArray;
	}

	public String[] getBookArray1() {
		return booksArray1;
	}
	
	public boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null) {
			return ni.isConnectedOrConnecting();
		} else {
			return false;
		}
	}
}

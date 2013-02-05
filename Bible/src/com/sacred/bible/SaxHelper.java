package com.sacred.bible;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHelper extends DefaultHandler {

	private String value;
	private ArrayList<String> verses;
	private ArrayList<ArrayList<String>> chapters;
	private WeakReference<BibleActivity> activityReference;
	private BgDataHandler dataHandler;

	public SaxHelper(BibleActivity xmlParsingActivity) {
		activityReference = new WeakReference<BibleActivity>(xmlParsingActivity);
		dataHandler = activityReference.get();
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		value = new String(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		dataHandler.hanldeData(chapters);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if (localName.equalsIgnoreCase("verse")) {
			verses.add(value);
		}
		if (localName.equalsIgnoreCase("chapter")) {
			chapters.add(verses);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase("book")) {
			chapters = new ArrayList<ArrayList<String>>();
		}
		if (localName.equalsIgnoreCase("chapter")) {
			verses = new ArrayList<String>();
		}
	}
}

package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.UserActivityFeeds;

public class UsersActivityFeedsParser extends DefaultHandler {
	private String tempVal = null;
	private UserActivityFeeds mUserActivityFeeds;
	private ArrayList<UserActivityFeeds> mActivityFeedsList;
	private String TAG="Values";

	public UsersActivityFeedsParser(){
		mActivityFeedsList=new ArrayList<UserActivityFeeds>();
	}
	public ArrayList<UserActivityFeeds>getUActivityFeeds(){
		return mActivityFeedsList;

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tempVal = "";
		if(qName.equals("activity"))
			mUserActivityFeeds= new UserActivityFeeds();
		Log.i("","qName Values :"+TAG+qName);



		//super.startElement(uri, localName, qName, attributes);
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		//read the text of each xml node
		tempVal = new String(ch, start, length);

		//super.characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {


		// TODO Auto-generated method stub
		if(qName.equalsIgnoreCase("Activity")){
			mActivityFeedsList.add(mUserActivityFeeds);
		}else if(qName.equalsIgnoreCase("ActivityUser")){
			mUserActivityFeeds.setActivityUser(tempVal);
			Log.d("", "Action"+TAG+mUserActivityFeeds.getActivityUser());
		}else if(qName.equalsIgnoreCase("ActivityAction")){
			mUserActivityFeeds.setActivityAction(tempVal);
			Log.d("", "Action "+TAG+mUserActivityFeeds.getActivityAction());
		}else if(qName.equalsIgnoreCase("ActivityName")){
			mUserActivityFeeds.setActivityName(tempVal);
			Log.d("", "Type "+TAG+mUserActivityFeeds.getActivityAction());
		}else if(qName.equalsIgnoreCase("Activitydate")){
			mUserActivityFeeds.setActivitydate(tempVal);
			Log.d("", "Time "+TAG+mUserActivityFeeds.getActivitydate());
		}else if(qName.equalsIgnoreCase("Activityimage")){
			mUserActivityFeeds.setActivityimage(tempVal);
			Log.d("", "UserImage "+TAG+mUserActivityFeeds.getActivityimage());
		}


	}
}
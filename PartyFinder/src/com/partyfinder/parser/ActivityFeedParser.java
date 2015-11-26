package com.partyfinder.parser;
import java.util.ArrayList;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.ActivityFeedItem;

public class ActivityFeedParser extends DefaultHandler{
	
	static String TAG = "Activity Feed List ";
	private ArrayList<ActivityFeedItem> activityFeed;
	private String tempVal = null;
	private ActivityFeedItem mActivityFeedItem;
	private StringBuilder tagVal;
	
	
	public ActivityFeedParser() {
		activityFeed = new ArrayList<ActivityFeedItem>();
		tagVal=new StringBuilder();
	}
	
	public ArrayList<ActivityFeedItem> getActivityFeed(){
		return activityFeed;
	}


	

	
	
	//Event Handler
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
//		tempVal = "";
		tagVal.setLength(0);
		if(qName.equalsIgnoreCase("activity")){
			//create a new instance of ActivityFeedItem
			mActivityFeedItem = new ActivityFeedItem();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//read the text of each xml node
		tagVal.append(ch,start,length);
//		tempVal = new String(ch, start, length);
		//tempVal=tempVal.substring(1);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		String tempVal=tagVal.toString();
		
		if(qName.equalsIgnoreCase("activity")){
			activityFeed.add(mActivityFeedItem);
		
		}else if(qName.equalsIgnoreCase("ActivityUser")){
			mActivityFeedItem.setActivityUser(tempVal);
			Log.d("",TAG+ "Name "+TAG+mActivityFeedItem.getActivityUser());
		}else if(qName.equalsIgnoreCase("ActivityAction")){
			mActivityFeedItem.setActivityAction(tempVal);
			Log.d("",TAG+  "Action "+TAG+mActivityFeedItem.getActivityAction());
		}else if(qName.equalsIgnoreCase("ActivityName")){
			mActivityFeedItem.setActivityType(tempVal);
			Log.d("",TAG+  "Type "+TAG+mActivityFeedItem.getActivityType());
		}else if(qName.equalsIgnoreCase("Activitydate")){
			mActivityFeedItem.setActivityTime(tempVal);
			Log.d("",TAG+  "Time "+TAG+mActivityFeedItem.getActivityTime());
		}else if(qName.equalsIgnoreCase("Activityimage")){
			mActivityFeedItem.setActivityUserImage(tempVal);
			Log.d("",TAG+  "UserImage "+TAG+mActivityFeedItem.getActivityUserImage());
		}
	}

}

package com.partyfinder.model;

import java.io.Serializable;

public class RecommendItem implements Serializable{
	//private EventItem mEventItem;
	private Object mObject;
	private FriendListItem mFriendItem;
	
	public RecommendItem() {
		// TODO Auto-generated constructor stub
	}

	public Object getmObject() {
		return mObject;
	}

	public void setmObject(Object mObject) {
		this.mObject = mObject;
	}

	/*public EventItem getmEventItem() {
		return mEventItem;
	}

	public void setmEventItem(EventItem mEventItem) {
		this.mEventItem = mEventItem;
	}

	public VenuesItem getmVenuesItem() {
		return mVenuesItem;
	}

	public void setmVenuesItem(VenuesItem mVenuesItem) {
		this.mVenuesItem = mVenuesItem;
	}
*/
	public FriendListItem getmFriendItem() {
		return mFriendItem;
	}

	public void setmFriendItem(FriendListItem mFriendItem) {
		this.mFriendItem = mFriendItem;
	}

	
	

}

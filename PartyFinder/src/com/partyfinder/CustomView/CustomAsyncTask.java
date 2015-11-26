package com.partyfinder.CustomView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class CustomAsyncTask extends AsyncTask<Void, Void, Boolean>{

	private TaskListener mTaskListener;
	private ProgressBar mProgBar;
	private ProgressDialog mProgDialog;
	
	public CustomAsyncTask(TaskListener taskListener,ProgressBar progBar,Context context){
		this.mTaskListener=taskListener;
		this.mProgBar=progBar;
		mProgBar.setClickable(false);
	}
	
	
	
	public CustomAsyncTask(TaskListener taskListener,Context context){
		this.mTaskListener=taskListener;
	}
	public CustomAsyncTask(TaskListener taskListener,ProgressDialog progBar,Context context){
		  this.mTaskListener=taskListener;
		  this.mProgDialog=progBar;
//		  this.mProgDialog.setTitle("processamento");
	
		  
		  this.mProgDialog.setCancelable(false);
		 }
	
	
	public CustomAsyncTask(TaskListener taskListener,ProgressDialog progBar,ProgressBar progBars,Context context){
		  this.mTaskListener=taskListener;
		  this.mProgDialog=progBar;

		  
		  this.mProgDialog.setCancelable(false);
			this.mProgBar=progBars;
			mProgBar.setClickable(false);
		 }
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		if(mProgBar!=null){
		mProgBar.setVisibility(View.VISIBLE);
		}
		if(mProgDialog!=null){

			   mProgDialog.show();
			  }
		super.onPreExecute();
	}
	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		mTaskListener.execute();
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		if(mProgBar!=null){
			mProgBar.setVisibility(View.GONE);
		}
		mTaskListener.updateUI();
		if(mProgDialog!=null){
//			   mProgDialog.setMessage("Please wait...");
			   mProgDialog.dismiss();
			  }
	
		super.onPostExecute(result);
	}
}

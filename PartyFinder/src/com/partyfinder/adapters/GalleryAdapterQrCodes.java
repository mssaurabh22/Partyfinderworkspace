package com.partyfinder.adapters;

import com.partyfinder.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class GalleryAdapterQrCodes extends BaseAdapter{
	
	LayoutInflater inflater;
	Context context;


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("","getView()");
		final ViewHolder mViewHolder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.galleryitems_layout_orderqrcodes, null);
			mViewHolder = new ViewHolder();
			mViewHolder.QrCodeImage = (ImageView)convertView.findViewById(R.id.GalleryQrCodeImage);
			mViewHolder.mLoadingProgressBar=(ProgressBar) convertView.findViewById(R.id.LoadingProgressBar);
			
			convertView.setTag(mViewHolder);
		}else {
			mViewHolder = (ViewHolder)convertView.getTag();


		}
		
		
		Picasso.with(context).load("").into(mViewHolder.QrCodeImage, new Callback() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				mViewHolder.mLoadingProgressBar.setVisibility(View.GONE);
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
				mViewHolder.mLoadingProgressBar.setVisibility(View.VISIBLE);
			}
		} );
		return convertView;
	}
	
	private class ViewHolder {
		ImageView QrCodeImage;
		ProgressBar mLoadingProgressBar;
	
	}

}

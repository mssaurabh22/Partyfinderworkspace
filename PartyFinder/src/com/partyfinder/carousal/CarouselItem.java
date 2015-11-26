package com.partyfinder.carousal;

import com.partyfinder.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CarouselItem extends FrameLayout 
implements Comparable<CarouselItem> {

	private ImageView mImage;
	private TextView mText;

	private int index;
	private float currentAngle;
	private float itemX;
	private float itemY;
	private float itemZ;
	private boolean drawn;	
	private String imageUrl;
	private ProgressBar mProgbar;
	public int mScreenWidth;
	public int mScreenHeight;

	// It's needed to find screen coordinates
	private Matrix mCIMatrix;
	private RelativeLayout mImageItemLayout;

	public CarouselItem(Context context,String url) {

		super(context);
		getScreenResolution(context);
		Log.i("", "screen resolution  CarouselItem : " +"width : "+mScreenWidth +"height : "+mScreenHeight);
		imageUrl=url;
		FrameLayout.LayoutParams params = 
				new FrameLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, 
						LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.TOP;

		this.setLayoutParams(params);

		LayoutInflater inflater = LayoutInflater.from(context);
		View itemTemplate = inflater.inflate(R.layout.item, this, true);
		//		itemTemplate.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		//		itemTemplate.setLayerType(View.LAYER_TYPE_NONE, null);


		// line no. 65 CarouselItem

		RelativeLayout.LayoutParams mapLayer = null;

		if(mScreenWidth>500 && mScreenWidth<560){
			if(mScreenHeight>940 && mScreenHeight<980){
				mapLayer = new RelativeLayout.LayoutParams(340,510);
			}
		}else{

			mapLayer = new RelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.whatsHot_image_width),getResources().getDimensionPixelSize(R.dimen.whatsHot_image_height));	

		}

		mImageItemLayout=(RelativeLayout) itemTemplate.findViewById(R.id.whatsHotImageItemLayout);
		mImageItemLayout.setLayoutParams(mapLayer);
		mImage = (ImageView)itemTemplate.findViewById(R.id.item_image);
		mProgbar=(ProgressBar) itemTemplate.findViewById(R.id.carosalItemProg);
		mProgbar.setVisibility(View.VISIBLE);
		//		mText = (TextView)itemTemplate.findViewById(R.id.item_text);
		Picasso.with(context).load(imageUrl).into(mImage,new Callback() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				mProgbar.setVisibility(View.GONE);
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}
		});

	}	 

	public String getName(){
		return mText.getText().toString();
	}	

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setCurrentAngle(float currentAngle) {

		if(index == 0 && currentAngle > 5){
			Log.d("", "");
		}

		this.currentAngle = currentAngle;
	}

	public float getCurrentAngle() {
		return currentAngle;
	}

	public int compareTo(CarouselItem another) {
		return (int)(another.itemZ - this.itemZ);
	}

	public void setItemX(float x) {
		this.itemX = x;
	}

	public float getItemX() {
		return itemX;
	}

	public void setItemY(float y) {
		this.itemY = y;
	}

	public float getItemY() {
		return itemY;
	}

	public void setItemZ(float z) {
		this.itemZ = z;
	}

	public float getItemZ() {
		return itemZ;
	}

	public void setDrawn(boolean drawn) {
		this.drawn = drawn;
	}

	public boolean isDrawn() {
		return drawn;
	}

	public void setImageBitmap(Bitmap bitmap){
		mImage.setImageBitmap(bitmap);

	}

	public void setText(String txt){
		mText.setText(txt);
	}

	Matrix getCIMatrix() {
		return mCIMatrix;
	}

	void setCIMatrix(Matrix mMatrix) {
		this.mCIMatrix = mMatrix;
	}	


	public void getScreenResolution(Context mCtx){

		WindowManager wm = (WindowManager) mCtx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		//		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		mScreenWidth  = size.x;
		mScreenHeight = size.y;

		Log.i("", "Device   Screen Height carousel : "+mScreenHeight);
		Log.i("", "Device   Screen Height carousel : "+mScreenWidth );

	}

}

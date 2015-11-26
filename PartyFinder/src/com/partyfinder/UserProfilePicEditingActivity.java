package com.partyfinder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;



import com.google.android.gms.internal.ml;
import com.partyfinder.CustomView.CustomAsyncTask;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.CustomView.TaskListener;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.ContentManager;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.model.userPicUploadModel;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfilePicEditingActivity extends Activity{
	
	 private ImageButton mBackBtn;
	 private TextView mEditPicTextView;
	 private ImageView mUserImageView;
	 private Context mContext;
	 private Dialog mDialog;
	 private Uri fileUri;
	 public static final int MEDIA_TYPE_IMAGE = 1;
	 private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static int RESULT_LOAD_IMG = 1;
	
	private UserLoginItem mUserLoginItem;
	private Bitmap mBitmap;
	private String xml;
	private Bitmap mGalleryImage;
	private String img_Decodable_Str;
	private userPicUploadModel mUserPicUpdateObject;
	private CustomAsyncTask mAsyncTask;
	private UserLoginItem userLoginItem;
	private ProgressBar mProgressBar;
	private  Bitmap photo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.user_profilepic_editing);
	mContext = this;
	mUserLoginItem=SettingPrefrences.getUserPrefrence(mContext);
	initView();
	
	
	mBackBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			  overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
		}
	});
	
	mEditPicTextView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			displayEditPicDialog();
		}
	});
	
	
	
	
	}
	
	
	
	public void initView(){
		
		mProgressBar=(ProgressBar) findViewById(R.id.editPicProgress);
		mBackBtn = (ImageButton) findViewById(R.id.img_Loginbck);
		mEditPicTextView = (TextView) findViewById(R.id.profileEditTextView);
		mUserImageView=(ImageView) findViewById(R.id.userProfilePic);
		Picasso.with(mContext).load(mUserLoginItem.getVc_image_url()).into(mUserImageView);
		
		Log.i("", "User Image Url" +mUserLoginItem.getVc_image_url());
//		mBitmap = BitmapFactory.decodeFile(mUserLoginItem.getVc_image_url());
//		mUserImageView.setImageBitmap(mBitmap);

		
	}
	
	
	public void displayEditPicDialog(){



		mDialog=new Dialog(mContext);

		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Window window=mDialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		
		mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		mDialog.setContentView(R.layout.dialog_editing_user_pic);
	
		TextView DeleteUserFhoto=(TextView) mDialog.findViewById(R.id.DeletePhotoBtn);
		TextView openCameraBtn =(TextView) mDialog.findViewById(R.id.OpenCameraBtn);
		TextView openGalleryBtn=(TextView) mDialog.findViewById(R.id.OpenGalleryBtn);
		TextView CancelPopUp=(TextView) mDialog.findViewById(R.id.existingCardCancel);
		
		
		DeleteUserFhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				xml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
						+"<image>"+
								"<ucode>"+mUserLoginItem.getVc_user_code()+"</ucode>"+
								"<img>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</img>"+
							"</image>";
				UpdateUserProfilePic();
				mDialog.dismiss();
			}
		});
		
		
		openCameraBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				  Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
	                startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE); 
	                
				
				
			}
		});
		
		openGalleryBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent galleryIntent = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				// Start the Intent
				startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
			}
		});

	

		
		CancelPopUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDialog.dismiss();
			}
		});
		
		mDialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try {
			
			
			   if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK
						&& null != data) {
				   
		 photo = (Bitmap) data.getExtras().get("data"); 
/*//				   Uri.fromFile(getOutputMediaFile(data);
				   Uri selectedImage = data.getData();
//				   fileUri  = data.getData();
					String[] filePathColumn = { MediaStore.Images.Media.DATA };

					// Get the cursor
					Cursor cursor = getContentResolver().query(selectedImage,
							filePathColumn, null, null, null);
					// Move to first row
					cursor.moveToFirst();

					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					img_Decodable_Str = cursor.getString(columnIndex);
					cursor.close();*/
				   
				   BitmapFactory.Options options = new BitmapFactory.Options();
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
					Bitmap mGalleryImageCompressed=Bitmap.createScaledBitmap(photo, 200, 200, false);
					mGalleryImageCompressed.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
					byte[] byteArray = byteArrayOutputStream .toByteArray();
					String encoded =  Base64.encodeToString(byteArray, Base64.NO_WRAP);
					
					xml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
					+"<image>"+
					"<ucode>"+mUserLoginItem.getVc_user_code()+"</ucode>"+
							"<img>"+encoded+"</img>"+
						"</image>";
					
					
					Log.i("", "Camera Photo" +encoded);
				   
					UpdateUserProfilePic();
					mDialog.dismiss();
		         
			   }
			   
			   else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
					&& null != data) {
				   
				   
				   
				   
				// Get the Image from data

				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				// Get the cursor
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				// Move to first row
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				img_Decodable_Str = cursor.getString(columnIndex);
				cursor.close();
	

			

				mGalleryImage = BitmapFactory.decodeFile(img_Decodable_Str);


				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
				
				Bitmap mGalleryImageCompressed=Bitmap.createScaledBitmap(mGalleryImage, 200, 200, false);

				mGalleryImageCompressed.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
				byte[] byteArray = byteArrayOutputStream .toByteArray();


				String encoded =  Base64.encodeToString(byteArray, Base64.NO_WRAP);
				
				xml="<?xml version="+"\"1.0\""+" encoding="+"\"utf-8\""+" ?>"
				+"<image>"+
						"<ucode>"+mUserLoginItem.getVc_user_code()+"</ucode>"+
						"<img>"+encoded+"</img>"+
					"</image>";
				
				Log.i("", "Base 64 String"   +encoded);

				UpdateUserProfilePic();
				mDialog.dismiss();
			} else {
				Toast.makeText(this, "nenhuma imagem escolhido.",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Ocorreu um erro ao por favor tente novamenten", Toast.LENGTH_LONG)
			.show();
			Log.i("", "Execption  :  " +e.toString());
		}
//	
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	   public Uri getOutputMediaFileUri(int type) {
	        return Uri.fromFile(getOutputMediaFile(type));
	    }

	   
	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	 
	        // save file url in bundle as it will be null on screen orientation
	        // changes
	        outState.putParcelable("file_uri", fileUri);
	    }
	 
	    @Override
	    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	        super.onRestoreInstanceState(savedInstanceState);
	 
	        // get the file url
	        fileUri = savedInstanceState.getParcelable("file_uri");
	    }
	    
	    
	    
	    
	    
	    
	    private static File getOutputMediaFile(int type) {
	    	 
	        // External sdcard location
	        File mediaStorageDir = new File(
	                Environment
	                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	                        "Android File Upload");
	 
	        // Create the storage directory if it does not exist
	        if (!mediaStorageDir.exists()) {
	            if (!mediaStorageDir.mkdirs()) {
	             
	                return null;
	            }
	        }
	 
	        // Create a media file name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	                Locale.getDefault()).format(new Date());
	        File mediaFile;
	        if (type == MEDIA_TYPE_IMAGE) {
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                    + "IMG_" + timeStamp + ".jpgxxx");
	       
	        } else {
	            return null;
	        }
	 
	        return mediaFile;
	    }
	    
	    
	    
	    private void UpdateUserProfilePic(){
			if(mAsyncTask!=null){
				mAsyncTask.cancel(true);
				mAsyncTask=null;
			}
			mAsyncTask=new CustomAsyncTask(UpdateProfilePic, mProgressBar, mContext);
			mAsyncTask.execute();
		}

		TaskListener UpdateProfilePic =new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub
//				Log.i("", "Response  : "+mUserPicUpdateObject.getRspMsg() +" : " +mUserPicUpdateObject.getRspMsg());
if(mUserPicUpdateObject!=null){
			if(mUserPicUpdateObject.getRspMsg().equalsIgnoreCase("Success")){
//				
				Log.i("", "Response  : "+mUserPicUpdateObject.getRspMsg());
				
				mUserImageView.setBackground(null);
				
				if(photo!=null){
					mUserImageView.setImageBitmap(photo);
				
				}else if(mGalleryImage!=null){
					mUserImageView.setImageBitmap(mGalleryImage);
				}else{
					mUserImageView.setImageDrawable(getResources().getDrawable(R.drawable.user));
				}

//				ImgUserProfile2.setImageBitmap(BitmapFactory.decodeFile(img_Decodable_Str));
				loadLoginTask();
			
//			Toast.makeText(mContext, "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
			}
else{
	Toast.makeText(mContext, "Ocorreu um erro ao por favor tente novamente", Toast.LENGTH_SHORT).show();
}			
}else{
				Toast.makeText(mContext, "Ocorreu um erro ao por favor tente novamente", Toast.LENGTH_SHORT).show();
			}

			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				try{
				mUserPicUpdateObject=ContentManager.getInstance().userPicUpload(xml);
				Log.i("", "User pic uploaded :");
				} catch(Exception ex){
					Toast.makeText(mContext, "Ocorreu um erro ao por favor tente novamente", Toast.LENGTH_SHORT).show();
					ex.printStackTrace();
					Log.i("", "Execption  : " +ex.toString());
				}
		     
				

			}
		};
		
		
		
		protected void loadLoginTask() {
			// TODO Auto-generated method stub
			TaskListener mTaskListener=new TaskListener() {

				@Override
				public void updateUI() {
					// TODO Auto-generated method stub


					if( userLoginItem!=null){
						userLoginItem.setPf_user_image(userLoginItem.getPf_user_image());
			
						Log.i("","user profile"+userLoginItem.getPf_user_image());
						
//						Toast.makeText(mContext, "Sucessfully Updated ", Toast.LENGTH_SHORT).show();
						
						SettingPrefrences.saveUserPrefrence(userLoginItem,mContext);
						
//				
					}

				}

				@Override
				public void execute() {
					// TODO Auto-generated method stub
					userLoginItem= ContentManager.getInstance().getLogin(mContext,mUserLoginItem.getVc_user_email(),mUserLoginItem.getVc_user_pwd());
				}
			};
			if (mAsyncTask != null) {
				mAsyncTask.cancel(true);
				mAsyncTask = null;
			}
			mAsyncTask = new CustomAsyncTask(mTaskListener,  mContext);
			mAsyncTask.execute();
		}
}

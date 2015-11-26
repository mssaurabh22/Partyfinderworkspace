package com.partyfinder.drawerfragment;

import java.util.ArrayList;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.partyfinder.R;
import com.partyfinder.adapters.CardAdapter;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.Card;
import com.partyfinder.model.CardHolder;
import com.partyfinder.model.Cards;
import com.partyfinder.model.UserLoginItem;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ExistingUserCreditCard extends Fragment{
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransect;
	private Fragment mFragment;
	private FrameLayout mFrameLayout;

//	private ListView mListView;
	private SwipeMenuListView mListView;
	private Context mContext;
	private CardHolder tempCardHolder;
	private CardAdapter cardAdapter;
	private Cards cardHolderList;
	private UserLoginItem mUserLogin;
	private int cardHolderIndex;
	private RelativeLayout mBack;
	ArrayList<CardHolder> mCardHolderList;
	private ImageView creditCardbackBtn;
	private ArrayList<Card> cardList;

	public ExistingUserCreditCard() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext=getActivity().getApplicationContext();
		mUserLogin=SettingPrefrences.getUserPrefrence(mContext);
		cardHolderList=SettingPrefrences.getCardPrefrence(mContext);
		
		super.onCreate(savedInstanceState);
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.user_credit_card_detail_layout, null);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mFragmentManager=getActivity().getSupportFragmentManager();
//		mListView=(ListView) root.findViewById(R.id.userCreditCardList);
		mListView=(SwipeMenuListView) root.findViewById(R.id.userCreditCardList);
		mFrameLayout=(FrameLayout) root.findViewById(R.id.fragmentContainer);
		mFrameLayout.bringToFront();
		mBack=(RelativeLayout) root.findViewById(R.id.backButton_layout);
//		mButton=(Button) root.findViewById(R.id.existingCardCancel);
		creditCardbackBtn=(ImageView) root.findViewById(R.id.btn_settingBck);
//		mButton.bringToFront();
		getCardHolder();
		if(tempCardHolder!=null){
		cardList=tempCardHolder.getCardList();
		if(cardList!=null){	
		cardAdapter=new CardAdapter(mContext, cardList,this.getActivity());
		
		mListView.setAdapter(cardAdapter);
		// step 1. create a MenuCreator
 		SwipeMenuCreator creator = new SwipeMenuCreator() {

 			@Override
 			public void create(SwipeMenu menu) {
 	     				// create "delete" item
 				SwipeMenuItem deleteItem = new SwipeMenuItem(
 						getActivity().getApplicationContext());
 				// set item background
 				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
 						0x3F, 0x25)));
 				// set item width
 				deleteItem.setWidth(dp2px(60));
 				// set a icon
 				deleteItem.setTitle("Delete");
 				deleteItem.setTitleColor(getResources().getColor(R.color.White));
 				deleteItem.setTitleSize(getResources().getDimensionPixelSize(R.dimen.delete_card_item_text_size));
 				// add to menu
 				menu.addMenuItem(deleteItem);
 			}
 		};
 		// set creator
 		mListView.setMenuCreator(creator);

 		// step 2. listener item click event
 		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
 			@Override
 			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
 			

 				switch (index) {
 				
 				case 0:
 	//  Deleting Process  //
 					
 					deletecard(position);
 					cardAdapter.notifyDataSetChanged();
 					
 		
 					break;
 				}
 				return false;
 			}
 		});
 		
 		// set SwipeListener
 		mListView.setOnSwipeListener(new OnSwipeListener() {
 			
 			@Override
 			public void onSwipeStart(int position) {
 				// swipe start
 			}
 			
 			@Override
 			public void onSwipeEnd(int position) {
 				// swipe end
 			}
 		});
		
		
		
		}
	/*	mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
//				ArrayList<Card> cardList=tempCardHolder.getCardList();
//				cardList.remove(position);
//				cardAdapter.notifyDataSetChanged();
//				cardHolderList.getCardHolderList().get(cardHolderIndex);
				
				
				//////////////////////////////////
				//////////////////////////////////
				///////////////////////////////////
//			cardList.remove(position);
//			cardHolderList.getCardHolderList().get(cardHolderIndex).setCardList(cardList);
//			SettingPrefrences.saveCardPrefrence(cardHolderList,mContext);
//			cardAdapter.notifyDataSetChanged();
			
				
//				SettingPrefrences.saveCardPrefrence(item, context)
				return false;
			}
		});*/
		
		}
		
		/*mListView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				
				return false;
			}
		});*/
		/* cancel=(Button) root.findViewById(R.id.existingCardCancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			mFragmentManager.popBackStack();
			}
		});*/
		creditCardbackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack();
			}
		});
		
		
	mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManager.popBackStack();
			}
		});
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Log.i("", "Position.."+position);
//				tempCardHolder=mCardHolderList.get(position);
				mFragmentTransect=mFragmentManager.beginTransaction();
				if(mFragment!=null){
					mFragmentTransect.detach(mFragment);
					mFragment=null;
				}
				mFragment=new UpdateAddNewCardFragment(mCardHolderList,cardHolderIndex,position);
				mFragmentTransect.replace(R.id.fragmentContainer, mFragment);
				mFragmentTransect.addToBackStack(null);
				mFragmentTransect.commitAllowingStateLoss();
		
				
			}
			
			
		});
		// TODO Auto-generated method stub
		return root;
	}

	
	private void deletecard(int cardIndex){
		cardList.remove(cardIndex);
		tempCardHolder.setCardList(cardList);
		cardHolderList.getCardHolderList().set(cardHolderIndex, tempCardHolder);
		SettingPrefrences.saveCardPrefrence(cardHolderList, mContext);
	}

	private void getCardHolder(){
		
		
		if(cardHolderList!=null){
			mCardHolderList=cardHolderList.getCardHolderList();

			Log.i("", " cardlist: "+mCardHolderList.size());

			if(cardHolderList.getCardHolderList().size()>0){
				for(int i=0;i<cardHolderList.getCardHolderList().size();i++){
					if(cardHolderList.getCardHolderList().get(i).getUserCode().equalsIgnoreCase(mUserLogin.getVc_user_code())){
						tempCardHolder=cardHolderList.getCardHolderList().get(i);
						cardHolderIndex=i;
						break;
					}
				}
			}
		}

	}
	/*	
	private void loadExistingCreditCardList(){


		TaskListener loadExistingCreditCard=new TaskListener() {

			@Override
			public void updateUI() {
				// TODO Auto-generated method stub

			}

			@Override
			public void execute() {
				// TODO Auto-generated method stub

			}
		};



		if(mAsycnTask!=null){
			mAsycnTask.cancel(true);
			mAsycnTask=null;
		}
		mAsycnTask=new CustomAsyncTask(loadExistingCreditCard, mContext);
		mAsycnTask.execute();



	}
	 */

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}

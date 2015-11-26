package com.partyfinder.drawerfragment;

import java.util.List;

import com.google.android.gms.internal.ee;
import com.partyfinder.R;
import com.partyfinder.CustomView.CustomDialogBox;
import com.partyfinder.adapters.GalleryAdapterQrCodes;
import com.partyfinder.app.utils.AppUtils;
import com.partyfinder.managers.SettingPrefrences;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.GatewayRespItems;
import com.partyfinder.model.OrderAssignItem;
import com.partyfinder.model.UserLoginItem;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConfirmOrderFragment extends Fragment {

	private double grossTotal;
	private LinearLayout mConfirmTicketLayout,mPendingTicketLayout;
	private EventItem mEventItem;
	private OrderAssignItem mAssignOrderItem;
	private TextView eventName,ticketCost,mOrderNo,ticketUserName,UserCpf,UserAddress,mcityState;
	private ImageView ticketconfirmationQrcode,mnewticketconfirmationQrcode;
	private Button shareTofacebook,finilizButton;
	private UserLoginItem mLoginItem;
	private Context mContext;
	private String Response;
	private GatewayRespItems mGateWayResp;
	private Gallery mQrCodeGallery;
	private TextView mPendingLayoutHeaderText,mTicketStatusMessage,mPendingOrCanceledMessage,mpendingOrCanceledOrderCode;
	private TextView[] mDotsText;
	private LinearLayout mDottedImageCount;
	private int  mDotsCount;
	private GalleryAdapterQrCodes mAdapter;



	public ConfirmOrderFragment(){

	}

	public ConfirmOrderFragment(OrderAssignItem assignOrder,EventItem eventItem,double grossAmount,GatewayRespItems mResp){
		mGateWayResp = mResp;
		mEventItem=eventItem;
		mAssignOrderItem=assignOrder;
		grossTotal=grossAmount;


	}
	public ConfirmOrderFragment(OrderAssignItem assignOrder,EventItem eventItem,double grossAmount){

		mEventItem=eventItem;
		mAssignOrderItem=assignOrder;
		grossTotal=grossAmount;


	}
	/*
	public ConfirmOrderFragment(OrderAssignItem assignOrder,EventItem eventItem,double grossAmount){

		mEventItem=eventItem;
		mAssignOrderItem=assignOrder;
		grossTotal=grossAmount;


	}*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=getActivity().getApplicationContext();
		mLoginItem=SettingPrefrences.getUserPrefrence(mContext);
	}
	//	btn_shareToFacebook btn_addToPassbook
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.order_confirmation_layout, null);
		CustomDialogBox.showAlert(getResources().getString(R.string.infoDialo), "Para visualizar seu ingresso vá para Menu -> Ordens", getActivity());
		//		CustomDialogBox.showAlertAt(getResources().getString(R.string.infoDialo), Response, getActivity());
		mPendingTicketLayout=(LinearLayout) view.findViewById(R.id.PendingTicketLayout);
		mConfirmTicketLayout= (LinearLayout) view.findViewById(R.id.ConfirmTicketLayout);
		
		
		
		

		if(mGateWayResp.getTransectionStatus().equals("1") || mGateWayResp.getTransectionStatus().equals("2")){
			PendingTicketView(view);
		}else if(mGateWayResp.getTransectionStatus().equals("3") || mGateWayResp.getTransectionStatus().equals("4")){
			ConfimTicketView(view);
		}else{
			CanceledTicketView(view);	
		}





		return view;
	}


















	public void ConfimTicketView(View view){



		mConfirmTicketLayout.setVisibility(View.VISIBLE);
		mPendingTicketLayout.setVisibility(View.GONE);
		mDottedImageCount=(LinearLayout) view.findViewById(R.id.DottedImageCount);
		
		
		
		
		
		
		mQrCodeGallery=(Gallery) view.findViewById(R.id.QrCodeGallery);
		mAdapter = new GalleryAdapterQrCodes();
		mQrCodeGallery.setAdapter(mAdapter);
		
		
		
		
	  mDotsCount = mQrCodeGallery.getAdapter().getCount();
		  
	        //here we create the dots
	        //as you can see the dots are nothing but "."  of large size
	        mDotsText = new TextView[mDotsCount];
	 
	        //here we set the dots
	        for (int i = 0; i < mDotsCount; i++) {
	            mDotsText[i] = new TextView(getActivity());
	            mDotsText[i].setText(".");
	            mDotsText[i].setTextSize(45);
	            mDotsText[i].setTypeface(null, Typeface.BOLD);
	            mDotsText[i].setTextColor(android.graphics.Color.GRAY);
	            mDottedImageCount.addView(mDotsText[i]);
	        }
	        
	        mQrCodeGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
					   for (int i = 0; i < mDotsCount; i++) {
		                   mDotsText[i].setTextColor(Color.GRAY);
		                }

		                mDotsText[position].setTextColor(Color.WHITE);
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
		
			
		

		mConfirmTicketLayout=(LinearLayout) view.findViewById(R.id.ConfirmTicketLayout);
		eventName=(TextView) view.findViewById(R.id.ticketAndTable);

//		mnewticketconfirmationQrcode=(ImageView) view.findViewById(R.id.newticketconfirmationQrcode);
//		mnewticketconfirmationQrcode.bringToFront();
		ticketCost=(TextView) view.findViewById(R.id.ticketCost);

		mOrderNo=(TextView) view.findViewById(R.id.ticketOrderCode);
		ticketUserName=(TextView) view.findViewById(R.id.ticketUserName);
		UserCpf=(TextView) view.findViewById(R.id.UserCpf);
		UserAddress=(TextView) view.findViewById(R.id.UserAddress);
		ticketconfirmationQrcode=(ImageView) view.findViewById(R.id.ticketconfirmationQrcode);
		mcityState=(TextView) view.findViewById(R.id.cityState);
		shareTofacebook=(Button) view.findViewById(R.id.btnTofinilize);
		//		finilizButton=(Button) view.findViewById(R.id.btn_addToPassbook);

		mOrderNo.setText(getResources().getString(R.string.order)+mAssignOrderItem.getOrderCode());
		eventName.setText(mEventItem.getVcEventName());
		ticketCost.setText("R$"+AppUtils.formatToatal(grossTotal));
		ticketUserName.setText(mLoginItem.getVc_user_name());
		UserAddress.setText(mLoginItem.getVc_user_add());
		UserCpf.setText(getResources().getString(R.string.id)+mLoginItem.getVc_user_document());

		mcityState.setText(mLoginItem.getVc_user_country()+" "+mLoginItem.getVc_user_state());
		Picasso.with(mContext).load(mAssignOrderItem.getImageUrl()).into(ticketconfirmationQrcode);


		shareTofacebook.setText("Complete Order");
		shareTofacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
	}



	public void PendingTicketView(View view){

		mConfirmTicketLayout.setVisibility(View.GONE);
		mPendingTicketLayout.setVisibility(View.VISIBLE);

		mpendingOrCanceledOrderCode=(TextView) view.findViewById(R.id.pendingOrCanceledOrderCode);
		mpendingOrCanceledOrderCode.setText("Order# "+mAssignOrderItem.getOrderCode());

		mPendingLayoutHeaderText = (TextView) view.findViewById(R.id.PendingLayoutHeaderText);
		mPendingLayoutHeaderText.setText("Pendente");

		mTicketStatusMessage=(TextView) view.findViewById(R.id.TicketStatusMessage);
		mTicketStatusMessage.setText("PEDIDO EM ANÁLISE");

		mPendingOrCanceledMessage=(TextView) view.findViewById(R.id.PendingOrCanceledMessage);
		mPendingOrCanceledMessage.setText("O seu pedido foi recebido e está em análise pelo PagSeguro. Fique antento ao seu telefone, você pode ser contactado para confirmar a sua compra nos próximos minutos.");

		mConfirmTicketLayout=(LinearLayout) view.findViewById(R.id.ConfirmTicketLayout);
		eventName=(TextView) view.findViewById(R.id.ticketAndTable1);
		eventName.setText(mEventItem.getVcEventName());


		ticketCost=(TextView) view.findViewById(R.id.ticketCost1);
		ticketCost.setText("R$"+AppUtils.formatToatal(grossTotal));


		shareTofacebook=(Button) view.findViewById(R.id.btnTofinilize1);
		shareTofacebook.setText("Voltar Para a Loja");



		shareTofacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
	}


	public void CanceledTicketView(View view){

		mConfirmTicketLayout.setVisibility(View.GONE);
		mPendingTicketLayout.setVisibility(View.VISIBLE);

		mpendingOrCanceledOrderCode=(TextView) view.findViewById(R.id.pendingOrCanceledOrderCode);
		mpendingOrCanceledOrderCode.setText("Order# "+mAssignOrderItem.getOrderCode());

		mPendingLayoutHeaderText = (TextView) view.findViewById(R.id.PendingLayoutHeaderText);
		mPendingLayoutHeaderText.setText("Rejeitado");

		mTicketStatusMessage=(TextView) view.findViewById(R.id.TicketStatusMessage);
		mTicketStatusMessage.setText("DESCULPE");
		mTicketStatusMessage.setTextColor(getResources().getColor(R.color.rred));

		mPendingOrCanceledMessage=(TextView) view.findViewById(R.id.PendingOrCanceledMessage);
		mPendingOrCanceledMessage.setText("Seu pagamento foi negado pelo PagSeguro. Por favor, revise os dados do cartão e tente efetuar a compra novamente. Se o problema persistir, entre em contato com a operadora do cartão.");

		mConfirmTicketLayout=(LinearLayout) view.findViewById(R.id.ConfirmTicketLayout);
		eventName=(TextView) view.findViewById(R.id.ticketAndTable1);
		eventName.setText(mEventItem.getVcEventName());


		ticketCost=(TextView) view.findViewById(R.id.ticketCost1);
		ticketCost.setText("R$"+AppUtils.formatToatal(grossTotal));


		shareTofacebook=(Button) view.findViewById(R.id.btnTofinilize1);
		shareTofacebook.setText("Voltar Para a Loja");

		shareTofacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
	}




}

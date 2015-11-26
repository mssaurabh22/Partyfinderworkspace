package com.partyfinder.floorPlan;

import java.util.ArrayList;

import com.partyfinder.R;

import com.partyfinder.model.ReserveTableItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private Context mContext;
    private ArrayList<ReserveTableItem> ticketList;
  /*  private final Integer[] ticketList = { R.drawable.a, R.drawable.b,
            R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
            R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
            R.drawable.k, R.drawable.l };*/
    
//String[ticketListst={"wahid","manmohan","Rahul","Maddy","Subham"};
    public ImageAdapter(Context c,ArrayList<ReserveTableItem> ticketList) {
    	mContext=c;
        inflater = LayoutInflater.from(this.mContext);//getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ticketList=ticketList;
    }

    public int getCount() {
        if (ticketList != null) {
            return Integer.MAX_VALUE;
        } else {
            return 0;
        }
    }

    public Object getItem(int position) {
        if(position >= ticketList.size()) {
            position = position % ticketList.size();
        }
        
        return position;
    }

    public long getItemId(int position) {
        if(position >= ticketList.size()
        		) {
            position = position % ticketList.size()	;
        }
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
        TextView tv = null;
       final ViewHolder mViewHolder;
        if (convertView == null) {
        	mViewHolder=new ViewHolder();
        	convertView = inflater.inflate(R.layout.graph_gallery, null);
            mViewHolder.ticketTypeTv=(TextView) convertView.findViewById(R.id.tickettype);
            mViewHolder.ticketDescTv=(TextView) convertView.findViewById(R.id.ticketDesc);
            mViewHolder.ticketPriceTv=(TextView) convertView.findViewById(R.id.ticketCost);
            mViewHolder.ticketConsumption=(TextView) convertView.findViewById(R.id.ticketConsumption);
            mViewHolder.ticketTotalSeats=(TextView) convertView.findViewById(R.id.totalSeats);
//            mViewHolder.mgalleryContent=(RelativeLayout) convertView.findViewById(R.id.galleryContent);
            mViewHolder.ticketConsumption2=(TextView) convertView.findViewById(R.id.ticketConsumption2);
            
            mViewHolder.lugares=(LinearLayout) convertView.findViewById(R.id.lugares);
            mViewHolder.consumacao=(LinearLayout) convertView.findViewById(R.id.consumacao);
            mViewHolder.consumacao2=(LinearLayout) convertView.findViewById(R.id.consumacao2);
        	convertView.setTag(mViewHolder);
		}else {
			mViewHolder = (ViewHolder)convertView.getTag();
			
		
		}

        if(position >= ticketList.size()
        		) {
            position = position % ticketList.size()
            		;
        }
        
        ReserveTableItem mTicketItem=ticketList.get(position);//[position];
        mViewHolder.ticketTypeTv.setText(mTicketItem.getTableType());
        mViewHolder.ticketDescTv.setText(mTicketItem.getTabCabId());
        mViewHolder.ticketPriceTv.setText("R$"+mTicketItem.getPrice());
        mViewHolder.ticketConsumption.setText(mTicketItem.getConsumption());
        mViewHolder.ticketConsumption2.setText(mTicketItem.getConsumption());
        mViewHolder.ticketTotalSeats.setText(mTicketItem.getNoOfSeat());
        
//        mViewHolder.mgalleryContent.setVisibility(View.GONE);
        mViewHolder.consumacao.setVisibility(View.GONE);
        mViewHolder.consumacao2.setVisibility(View.VISIBLE);
        mViewHolder.lugares.setVisibility(View.GONE);
        

        
        if(mTicketItem.isSlect()){
        	convertView.setBackgroundResource(R.drawable.gallery_selection);
        	
            mViewHolder.consumacao.setVisibility(View.VISIBLE);
            mViewHolder.consumacao2.setVisibility(View.GONE);
            mViewHolder.lugares.setVisibility(View.VISIBLE);
            
        	
//        	 mViewHolder.mgalleryContent.setVisibility(View.VISIBLE);
        }else{
        	convertView.setBackgroundResource(R.drawable.gallerynormal);
        	
            mViewHolder.consumacao.setVisibility(View.GONE);
            mViewHolder.consumacao2.setVisibility(View.VISIBLE);
            mViewHolder.lugares.setVisibility(View.GONE);
        	
//        	 mViewHolder.mgalleryContent.setVisibility(View.GONE);
        }
        return convertView;
    }
    
    public int checkPosition(int position) {
        if(position >= ticketList.size()
        		) {
            position = position % ticketList.size()
            		;
        }
        
        return position;
    }

    
    class ViewHolder{
    	TextView ticketTypeTv;
    	TextView ticketDescTv;
    	TextView ticketPriceTv;
    	TextView ticketConsumption;
    	TextView ticketTotalSeats,ticketConsumption2;
    	RelativeLayout mgalleryContent;
    	LinearLayout lugares,consumacao,consumacao2;
    }
	/*@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}*/
}

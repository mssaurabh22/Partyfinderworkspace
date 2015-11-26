package com.partyfinder.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.partyfinder.model.ReserveTableItem;

public class ReserveTableParser extends DefaultHandler {
	private final String IMG_URL="imgurl";
	private final String FLOOR="floor";
	private final String TABLE_TYPE="vc_table_type";
	private final String CONSUMPTION="vc_Consumption";
	
	private final String PRICE="vc_price";
	private final String EVENT_CODE="vc_event_code";
	private final String CLUB_PLANT="vc_club_plant";
	
	private final String TABLE="vc_tables";
	private final String NO_OF_SEATS="vc_no_of_seats";
	private final String SOLD="vc_sold";
	
	private final String TAB_CAB="vc_tab_cab";
	private final String TAB_CAB_ID="vc_tab_cab_id";
	private final String YCORD="y";
	
	private final String XCORD="x";
	private final String WIDTH="vc_width";
	private final String HEIGHT="vc_height";
	
	private final String DEF_IMG="vc_available_image";
	private final String SELECTED_IMG="vc_selected_image";
	private final String SOLD_IMG="vc_sold_image";
	private final String PENDG_IMG="vc_pending_image";
	
	private StringBuilder tagVal=new StringBuilder();
	private String tableImg;
	private ReserveTableItem mReserveTableItem;
	private ArrayList<ReserveTableItem> reservTableList;
	Bitmap img;
	public ReserveTableParser(){
		reservTableList=new ArrayList<ReserveTableItem>();
	}
	
	
	public ArrayList<ReserveTableItem> getReservTableList() {
		return reservTableList;
	}
	public void setReservTableList(ArrayList<ReserveTableItem> reservTableList) {
		this.reservTableList = reservTableList;
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		tagVal.setLength(0);
		if(qName.equalsIgnoreCase(FLOOR)){
				mReserveTableItem=new ReserveTableItem();
		}
		
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tagVal.append(ch,start,length);
		super.characters(ch, start, length);
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String tempVal=tagVal.toString();
		if(qName.equalsIgnoreCase(FLOOR)){
			mReserveTableItem.setImageUrl(tableImg);
			mReserveTableItem.setFloorBitmap(img);
			reservTableList.add(mReserveTableItem);
		}else if(qName.equalsIgnoreCase(IMG_URL)){
			tableImg=tempVal;
			img=downloadImage(tableImg);
			Log.i("","Downloaded DefaultImg : "+img);
			
		}else if(qName.equalsIgnoreCase(TABLE_TYPE)){
			mReserveTableItem.setTableType(tempVal);
		}else if(qName.equalsIgnoreCase(CONSUMPTION)){
			mReserveTableItem.setConsumption(tempVal);
		}else if(qName.equalsIgnoreCase(PRICE)){
			mReserveTableItem.setPrice(tempVal);
		}else if(qName.equalsIgnoreCase(EVENT_CODE)){
			mReserveTableItem.setEventCode(tempVal);
		}else if(qName.equalsIgnoreCase(CLUB_PLANT)){
			mReserveTableItem.setClubPlant(tempVal);
		}else if(qName.equalsIgnoreCase(TABLE)){
			mReserveTableItem.setTables(tempVal);
		}else if(qName.equalsIgnoreCase(NO_OF_SEATS)){
			mReserveTableItem.setNoOfSeat(tempVal);
		}else if(qName.equalsIgnoreCase(SOLD)){
			mReserveTableItem.setSold(tempVal);
		}else if(qName.equalsIgnoreCase(TAB_CAB)){
			mReserveTableItem.setTabCab(tempVal);
		}else if(qName.equalsIgnoreCase(TAB_CAB_ID)){
			mReserveTableItem.setTabCabId(tempVal);
		}else if(qName.equalsIgnoreCase(YCORD)){
			mReserveTableItem.setyCordinate(tempVal);
		}else if(qName.equalsIgnoreCase(XCORD)){
			mReserveTableItem.setxCordinate(tempVal);
		}else if(qName.equalsIgnoreCase(WIDTH)){
			mReserveTableItem.setWidth(tempVal);
		}else if(qName.equalsIgnoreCase(HEIGHT)){
			mReserveTableItem.setHeight(tempVal);
			
		}else if(qName.equalsIgnoreCase(DEF_IMG)){
			mReserveTableItem.setDefaultImg(tempVal);
			Bitmap img=downloadImage(tempVal);
			Log.i("","Downloaded DefaultImg : "+img);
			mReserveTableItem.setDefaultImage(img);
			
		}else if(qName.equalsIgnoreCase(SELECTED_IMG)){
			mReserveTableItem.setSelectedImg(tempVal);
			Bitmap img=downloadImage(tempVal);
			Log.i("","Downloaded selected Image : "+img);
			mReserveTableItem.setSelectedImage(img);
			
		}else if(qName.equalsIgnoreCase(SOLD_IMG)){
			mReserveTableItem.setSoldImg(tempVal);
			Bitmap img=downloadImage(tempVal);
			Log.i("","Downloaded Sold Img : "+img);
			mReserveTableItem.setSoldImage(img);
			
		}/*else if(qName.equalsIgnoreCase(SOLD_IMG)){
			mReserveTableItem.setPndImg(tempVal);
			Bitmap img=downloadImage(tempVal);
			Log.i("","Downloaded Sold Img : "+img);
			mReserveTableItem.setPendingImage(img);
		}*/
		super.endElement(uri, localName, qName);
	}

	public static Bitmap  downloadImage(String URL){
		Bitmap bmp = null;
		try {
			java.net.URL ImageUrl = new java.net.URL(URL);
			bmp = BitmapFactory.decodeStream(ImageUrl.openConnection().getInputStream());
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", "download image...Bitmap"+e.toString());
		}

		return bmp;
	}

}

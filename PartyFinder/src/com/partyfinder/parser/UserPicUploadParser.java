package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.ReserveTableItem;
import com.partyfinder.model.userPicUploadModel;

public class UserPicUploadParser extends DefaultHandler {
	
	
	private final String ResponseCode="rspCode";
	private final String ResponseMessage="rspMsg";
	private final String TABLE="Table";
	private String tempVal=null;
	private StringBuilder tagVal=new StringBuilder();
	private userPicUploadModel muserPicUploadModel;
	
	public UserPicUploadParser(){
	}
		
	public userPicUploadModel GetUserPicUploadParser(){
		Log.i("", "userPicUploadModel running");
		return muserPicUploadModel;
	}



	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		
		
		tagVal.setLength(0);
		if(qName.equalsIgnoreCase(TABLE)){
			Log.i("", "StartElement running");
			muserPicUploadModel=new userPicUploadModel();
		}
		
		super.startElement(uri, localName, qName, attributes);
	}


	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
		
		tagVal.append(ch,start,length);
		super.characters(ch, start, length);
	}
	
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		String tempVal=tagVal.toString();
		
		if(qName.equalsIgnoreCase(ResponseCode)){
			muserPicUploadModel.setRspCode(tempVal);
			Log.i("", "rsp   code :"+tempVal);
		
		}else if(qName.equalsIgnoreCase(ResponseMessage)){
			muserPicUploadModel.setRspMsg(tempVal);
			Log.i("", "rsp   msg :"+tempVal);
		}
		
		super.endElement(uri, localName, qName);
	}




	
	

}

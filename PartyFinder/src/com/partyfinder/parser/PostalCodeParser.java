package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.partyfinder.model.ActivityFeedItem;
import com.partyfinder.model.PostalCodeModel;
import com.partyfinder.model.UserLoginItem;

public class PostalCodeParser extends DefaultHandler {

	private String XMLHEADER ="xmlcep";

	private String userCep="cep";
	private String userLogradouro="logradouro";
	private String userComplemento="complemento";
	private String userBairro="bairro";
	private String userLocalidae="localidade";
	private String userUf="uf";
	private String userIbge="ibge";
	private String userGia="gia";

	private PostalCodeModel mPostalModel;
	private StringBuilder textItem = new StringBuilder();


public PostalCodeParser(){
	Log.i("", "Postal Parser running : ");
}

	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		textItem.setLength(0);
		if(qName.equalsIgnoreCase(XMLHEADER)){
			//create a new instance of ActivityFeedItem
			mPostalModel = new PostalCodeModel();
		}

//		super.startElement(uri, localName, qName, attributes);
	}
	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		textItem.append(ch,start,length);
		//	super.characters(ch, start, length);
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		String tempVal=textItem.toString();

		if(qName.equalsIgnoreCase(XMLHEADER)){
//			mPostalModel = new PostalCodeModel();
		}else if(qName.equalsIgnoreCase(userCep)){
			Log.i("", "1111  Postal model inside parse :" +tempVal);
			mPostalModel.setUserCep(tempVal);
		}else if(qName.equalsIgnoreCase(userLogradouro)){
			Log.i("", "1111  Postal model inside parse :" +tempVal);
			mPostalModel.setUserLogradouro(tempVal);
		}else if(qName.equalsIgnoreCase(userComplemento)){
			Log.i("", "1111  Postal model inside parse :" +tempVal);
			mPostalModel.setUserComplemento(tempVal);
		}else if(qName.equalsIgnoreCase(userBairro)){
			Log.i("", "1111  Postal model inside parse :" +tempVal);
			mPostalModel.setUserBairro(tempVal);
		}else if(qName.equalsIgnoreCase(userLocalidae)){
			Log.i("", "1111  Postal model inside parse :" +tempVal);
			mPostalModel.setUserLocalidae(tempVal);
		}else if(qName.equalsIgnoreCase(userUf)){
			Log.i("", "1111  Postal model inside parse :" +tempVal);
			mPostalModel.setUserUf(tempVal);
		}else if(qName.equalsIgnoreCase(userIbge)){
			Log.i("", "1111  Postal model inside parse :" +tempVal);
			mPostalModel.setUserIbge(tempVal);
		}else if(qName.equalsIgnoreCase(userGia)){
			Log.i("", "1111  Postal model inside parse :" +tempVal);
			mPostalModel.setUserGia(tempVal);
		}



//		super.endElement(uri, localName, qName);
	}


	public PostalCodeModel getUserInfo() {
		return mPostalModel;
	}
	public void setUserInfo(PostalCodeModel userInfo) {
		this.mPostalModel = userInfo;
	}



}

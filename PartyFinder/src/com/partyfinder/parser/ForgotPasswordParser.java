package com.partyfinder.parser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.partyfinder.model.ForgotPassword;
import android.content.Context;
import android.util.Log;



public class ForgotPasswordParser extends DefaultHandler {
	
	private final String FORGOTPASSWORD="user";
    private ForgotPassword forgtPwd=null;
	private boolean mCurrentElement;
	
	private String sentStatus;
	
	private String TAG="Login parser";
	
	

	public String getSentStatus() {
		return sentStatus;
	}
   public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}

	private String tagValue;
	
	
    public ForgotPasswordParser (Context context){
    	super();
    }
    
    
    
    
    
    
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		mCurrentElement=true;
		tagValue="";
		if(qName.equalsIgnoreCase(FORGOTPASSWORD)){
			
		}
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
	}

	

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		if(mCurrentElement){
			tagValue=new String(ch,start,length);
		}		Log.i("",TAG+"Character : "+tagValue);
		super.characters(ch, start, length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if(qName.equalsIgnoreCase("status")){
			sentStatus=tagValue;
		}
		super.endElement(uri, localName, qName);
	}
	public ForgotPassword getForgtPwd() {
		return forgtPwd;
	}
	public void setForgtPwd(ForgotPassword forgtPwd) {
		this.forgtPwd = forgtPwd;
	}
	
	
	
}

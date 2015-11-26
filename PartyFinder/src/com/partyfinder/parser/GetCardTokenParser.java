package com.partyfinder.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetCardTokenParser extends DefaultHandler{
	String CardToken=null;
public String getSessionId() {
	return CardToken;
}


public void setSessionId(String sessionId) {
	this.CardToken = sessionId;
}

StringBuilder tagVal=new StringBuilder();

@Override
public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {
	// TODO Auto-generated method stub
	tagVal.setLength(0);
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
	if(qName.equalsIgnoreCase("token")){
		CardToken=tempVal;
	}
	super.endElement(uri, localName, qName);
}


}

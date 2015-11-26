package com.partyfinder.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.util.Log;

import com.partyfinder.HttpClient;
import com.partyfinder.AppConstant.PartyFinderConstants;
import com.partyfinder.app.utils.CustomHttpClient;
import com.partyfinder.model.ActivityFeedItem;
import com.partyfinder.model.AddFavoriteVanuesStatus;
import com.partyfinder.model.ConfirmFrndStatus;
import com.partyfinder.model.EditUserInfoItem;
import com.partyfinder.model.EventItem;
import com.partyfinder.model.GatewayRespItems;
import com.partyfinder.model.GuestListStatus;
import com.partyfinder.model.OrderAssignItem;
import com.partyfinder.model.PostalCodeModel;
import com.partyfinder.model.PromoterItem;
import com.partyfinder.model.SagupaoSessionItem;
import com.partyfinder.model.VenuesItem;
import com.partyfinder.model.GetEventAgendaItem;
import com.partyfinder.model.FriendListItem;
import com.partyfinder.model.OrderTransectionDetailsItems;
import com.partyfinder.model.PurchaseTicketItem;
import com.partyfinder.model.RecommendItem;
import com.partyfinder.model.RecommendVenueStatus;
import com.partyfinder.model.RegistrationStatus;
import com.partyfinder.model.RequestedFriendItem;
import com.partyfinder.model.ReserveTableItem;
import com.partyfinder.model.StatusCode;
import com.partyfinder.model.TicketDetailsItem;
import com.partyfinder.model.UserActivityFeeds;
import com.partyfinder.model.UserLoginItem;
import com.partyfinder.model.VanuesLikeStatus;
import com.partyfinder.model.userPicUploadModel;
import com.partyfinder.parser.ActivityFeedParser;
import com.partyfinder.parser.AddFavoriteVanuesParser;
import com.partyfinder.parser.ConfirmFrndRequestParser;
import com.partyfinder.parser.EditUserInfoParser;
import com.partyfinder.parser.EventParser;
import com.partyfinder.parser.FavouriteVenuesParser;
import com.partyfinder.parser.ForgotPasswordParser;
import com.partyfinder.parser.GAtewayRespParser;
import com.partyfinder.parser.GetCardTokenParser;
import com.partyfinder.parser.GetEventAgendaParser;
import com.partyfinder.parser.GetFriendListParser1;
import com.partyfinder.parser.GetFriendRequestParser;
import com.partyfinder.parser.GuestListItemParser;
import com.partyfinder.parser.OrderAsignParser;
import com.partyfinder.parser.OrderDummyHeaderParser;
import com.partyfinder.parser.OrderTransectionDetailsParser;
import com.partyfinder.parser.PostalCodeParser;
import com.partyfinder.parser.PromoterParser;
import com.partyfinder.parser.PurchaseTicketParser;
import com.partyfinder.parser.RecommendParser;
import com.partyfinder.parser.RecommendVenueStatusParser;
import com.partyfinder.parser.RegistrationStatusParser;
import com.partyfinder.parser.ReserveTableParser;
import com.partyfinder.parser.SagupaoCardDetailParser;
import com.partyfinder.parser.SagupaoSessionParser;
import com.partyfinder.parser.StatusCodeParser;
import com.partyfinder.parser.SubmitPromoterResponseParser;
import com.partyfinder.parser.TicketParser;
import com.partyfinder.parser.UpcomingEventParser;
import com.partyfinder.parser.UserLoginParser;
import com.partyfinder.parser.UserPicUploadParser;
import com.partyfinder.parser.UsersActivityFeedsParser;
import com.partyfinder.parser.VanuesLikeParser;
import com.partyfinder.parser.VanuesParser;
import com.partyfinder.parser.WhatsHotParser;
public class ContentManager {

	public static volatile ContentManager contentManager;

	private static  String TAG="Purchase Ticket Activity Class";//        Content Manager Class";
	private UserLoginItem userLoginItem;

	/*static {
		contentManager = new ContentManager();
	}
*/
	/**
	 * Method to get instance of ContentManager
	 * 
	 * @return contentManager
	 */
	public static ContentManager getInstance() {
		if (contentManager == null) {
			contentManager = new ContentManager();
		}
		return contentManager;
	}

	public ContentManager(){

	}


	public static String getXmlFromUrl(String urlString){

		StringBuffer output = new StringBuffer("");
		try{
			InputStream stream = null;
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();

			if(httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
				stream = httpConnection.getInputStream();

				BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
				String s = "";
				while((s= buffer.readLine())!= null){
					output.append(s);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return output.toString();

	}

	//UpcomingEventItems Parsing
	/*public static ArrayList<UpcomingEventItem> parse(InputStream is) throws IOException{
		ArrayList<UpcomingEventItem> mUpcomingEventItem = null;
		//create a XmlReader from SaxParser
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			UpcomingEventParser saxUpcomingEventHandler = new UpcomingEventParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxUpcomingEventHandler);
			//the process starts
			xmlReader.parse(new InputSource(is));
			//get the event list
			mUpcomingEventItem = saxUpcomingEventHandler.getUpcomingEvents();
			Log.d("",TAG+" Event name : "+mUpcomingEventItem.get(0).getEventName());
			Log.d("",TAG+" Event name: "+mUpcomingEventItem.get(1).getEventName());
			Log.d("",TAG+" Event name: "+mUpcomingEventItem.get(2).getEventName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mUpcomingEventItem;

	}*/
	
	public static ArrayList<EventItem> parseUpcomingEventItem(UserLoginItem userLoginItem) throws IOException{
		ArrayList<EventItem> mUpcomingEventItem = null;
		try {
		
		InputStream inputStream;
		StringBuilder url=new StringBuilder();
		url.append(PartyFinderConstants.URL_UPCOMING_EVENT);
		Log.i("",TAG+"xxxxxxxxxxxxxxxxxxxx"+url);
		inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
		
	
			//create a XmlReader from SaxParser
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			UpcomingEventParser saxUpcomingEventHandler=new UpcomingEventParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxUpcomingEventHandler);
			//the process starts
			xmlReader.parse(new InputSource(inputStream));
			//get the event list
			mUpcomingEventItem = saxUpcomingEventHandler.getUpcomingEvents();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mUpcomingEventItem;

	}

	//EventItems Parsing

	public static ArrayList<EventItem> parseEventItem(Object userLoginItem) throws IOException{
		ArrayList<EventItem> mEventItem = null;
		InputStream inputStream;
		StringBuilder url=new StringBuilder();
		url.append(PartyFinderConstants.EVENT_API);
		if(userLoginItem instanceof VenuesItem){
			VenuesItem venueItem=(VenuesItem) userLoginItem;
			url.append("/");
			url.append(venueItem.getVc_company_code());
			Log.i("",TAG+"View Agenda Event Url ..."+url);
		}
//		Log.i("","Event Url ..."+url);
		inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
		//create a XmlReader from SaxParser
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			EventParser saxEventHandler = new EventParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxEventHandler);
			//the process starts
			xmlReader.parse(new InputSource(inputStream));
			//get the event list
			mEventItem = saxEventHandler.getEventItems();

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			Log.i("", "Exception in parseEventItem:  "+e.toString());
			e.printStackTrace();
		}
		return mEventItem;
	}
//Get recommended venue and events details
	public static ArrayList<RecommendItem> parseRecommendedItem(UserLoginItem userLoginItem) throws IOException{
		ArrayList<RecommendItem> mRecommendedItem = null;
		InputStream inputStream;
		StringBuilder url=new StringBuilder();
		url.append(PartyFinderConstants.URL_RECOMMENDED).append(userLoginItem.getVc_user_code());
		Log.i("",TAG+ "Recommended Url : "+url.toString());
		inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
		
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			
			RecommendParser saxHandler = new RecommendParser();
			
			xmlReader.setContentHandler(saxHandler);
			
			xmlReader.parse(new InputSource(inputStream));
			
			mRecommendedItem = saxHandler.getRecommendedItem();

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			Log.i("", "Exception in parse Recommended:  "+e.toString());
			e.printStackTrace();
		}
		return mRecommendedItem;
		
	}
	
	public static GatewayRespItems postMundipaggRequest(String url,String xml) {

		  DefaultHttpClient mHttpClient = new DefaultHttpClient();
		  GatewayRespItems mGateWayItems = null;
	
		  InputStream inputStream = null;
		  int responseCode = 0;
		  try{
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();
				SAXParser saxParser = parserFactory.newSAXParser();
				XMLReader xmlReader = saxParser.getXMLReader();
				GAtewayRespParser mGateWayParser = new GAtewayRespParser();
				xmlReader.setContentHandler(mGateWayParser);
				
		   HttpPost mHttpPost = new HttpPost(url);
		   mHttpPost.addHeader("Content-Type", "application/xml");
		   StringEntity se = new StringEntity(xml,HTTP.UTF_8);
		   mHttpPost.setEntity(se);
		   HttpResponse response = mHttpClient.execute(mHttpPost);
		   
		   responseCode=response.getStatusLine().getStatusCode();
//		   mGateWayItems.setTransectionCode(responseCode);
		   
		   if(responseCode==200){
			   Log.i("",TAG+"RespondCode is 200");
		   InputSource source = new InputSource(response.getEntity().getContent());
		   source.setEncoding("ISO-8859-1");
		   
		   xmlReader.parse(source);
		   mGateWayItems = mGateWayParser.getGateWayResp();
		   mGateWayItems.setTransectionCode(responseCode);
		   Log.i("",TAG+"Mundipagg Response : "+ EntityUtils.toString(response.getEntity()) +", Full response : "+source.toString());
		   }
		   Log.i("",TAG+"Mundipagg Response : "+ EntityUtils.toString(response.getEntity()));
		  }catch(Exception e){
		   Log.i("",PartyFinderConstants.MAIN_EXCEPTION+TAG+"[postInputStreamRequest]"+e.toString());
		  }
		  return mGateWayItems;

		 }

	
	//UserLogin
	public UserLoginItem getLogin(Context ctx,String userName,String password){
		StringBuilder loginUrl=new StringBuilder(PartyFinderConstants.LOGIN_API);
		loginUrl.append("/").append(userName).append("/").append(password);

		
		Log.i("",TAG+" Login Url : "+loginUrl.toString());
		if(PartyFinderConstants.PARSER_ENABLE){
			Log.i("",TAG+" Login Url : "+loginUrl.toString());
		}

		try {

			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = parserFactory.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			UserLoginParser loginHendler=new UserLoginParser();

			xmlReader.setContentHandler(loginHendler);
			URL sourceUrl = new URL(loginUrl.toString().trim());
			URLConnection getXMLDataconnection = sourceUrl.openConnection();
			getXMLDataconnection
			.setConnectTimeout(PartyFinderConstants.CONNECTION_TIMEOUT);


			Reader reader = new InputStreamReader(
					getXMLDataconnection.getInputStream(),"UTF-8");
			InputSource source = new InputSource(reader);
			xmlReader.parse(source);



			userLoginItem=loginHendler.getUserInfo();

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			Log.i("",TAG+"Exception : "+e.toString());
			userLoginItem=null;
		}
		return userLoginItem;


	}
	public String parseforget(Context ctx,String emailId){



		String sentStatus;
		StringBuilder forgotPwdUrl=new StringBuilder(PartyFinderConstants.FORGET_PASSWORD_API);
		forgotPwdUrl.append("/").append(emailId);


		if(PartyFinderConstants.PARSER_ENABLE){
			Log.i("",TAG+" Forget Password Url : "+forgotPwdUrl.toString());
		}

		try {

			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = parserFactory.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();

			ForgotPasswordParser frgtPwdParser=new ForgotPasswordParser(ctx);
			xmlReader.setContentHandler(frgtPwdParser);
			URL sourceUrl = new URL(forgotPwdUrl.toString().trim());
			URLConnection getXMLDataconnection = sourceUrl.openConnection();
			getXMLDataconnection
			.setConnectTimeout(PartyFinderConstants.CONNECTION_TIMEOUT);


			Reader reader = new InputStreamReader(
					getXMLDataconnection.getInputStream());
			InputSource source = new InputSource(reader);
			xmlReader.parse(source);



			sentStatus=frgtPwdParser.getSentStatus();

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			Log.i("",TAG+"Exception : "+e.toString());
			sentStatus=null;
		} 
		return sentStatus;

	}
	/*
	 * ActivityFeedItem parse method
	 * @param InputSteam to parse
	 */
	

	public static ArrayList<ActivityFeedItem> parseActivityFeedItem(UserLoginItem userLoginItem) throws IOException{
		ArrayList<ActivityFeedItem> mActivityFeedItem = null;
		InputStream inputStream;
		StringBuilder url=new StringBuilder();
		url.append(PartyFinderConstants.URL_ACTIVITY_FEED);
		inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
		
		//create a XmlReader from SaxParser
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			ActivityFeedParser saxActivityFeedHandler = new ActivityFeedParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxActivityFeedHandler);
			//the process starts
			xmlReader.parse(new InputSource(inputStream));
			//get the event list
			mActivityFeedItem = saxActivityFeedHandler.getActivityFeed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mActivityFeedItem;

	}
	
	//send frnd request.....
	
	public StatusCode parserSendFriendRequest(String xml){
		StringBuilder url=new StringBuilder();
		StatusCode status = null;
		url.append(PartyFinderConstants.URL_SEND_FRIEND_REQUEST);
		Log.i("",TAG+PartyFinderConstants.INFO+TAG+"[ PARSE SEND FRIEND REQUEST METHOD ]"+url.toString());
		InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
		try{
		XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		StatusCodeParser parser=new StatusCodeParser();
		xmlReader.setContentHandler(parser);
		xmlReader.parse(new InputSource(response));
		status=parser.getStatusCode();
		
		}catch(Exception e){
		
		}
		return status;

	}
	
	//delete friend 
	
	public StatusCode parserDeleteFriend(String xml){
		StringBuilder url=new StringBuilder();
		StatusCode status = null;
		url.append(PartyFinderConstants.URL_DELETE_FRIEND);
		Log.i("",PartyFinderConstants.INFO+TAG+"[ PARSE DELETE FRIEND  METHOD ]"+url.toString());
		InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
		try{
		XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		StatusCodeParser parser=new StatusCodeParser();
		xmlReader.setContentHandler(parser);
		xmlReader.parse(new InputSource(response));
		status=parser.getStatusCode();
		
		}catch(Exception e){
		
		}
		return status;

	}


	/*
	 * Mehtod to get registration status code
	 * @param InputStream response
	 */
	
	public ConfirmFrndStatus parserConfirmFriendRequest(String xml){
		StringBuilder url=new StringBuilder();
		ConfirmFrndStatus status = null;
		url.append(PartyFinderConstants.URL_ACCEPT_FRND);
		Log.i("",PartyFinderConstants.INFO+TAG+"[ PARSE ACCEPT FRIEND REQUEST METHOD ]"+url.toString());
		InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
		try{
		XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		ConfirmFrndRequestParser parser=new ConfirmFrndRequestParser();
		xmlReader.setContentHandler(parser);
		xmlReader.parse(new InputSource(response));
		status=parser.getConfirmStatusCode();
		
		}catch(Exception e){
		
		}
		return status;

	}
	
	
	public ConfirmFrndStatus parserRejectFriendRequest(String xml){
		StringBuilder url=new StringBuilder();
		ConfirmFrndStatus status = null;
		url.append(PartyFinderConstants.URL_REJECT_FRND);
		Log.i("",PartyFinderConstants.INFO+TAG+"[ PARSE REJECT FRIEND REQUEST METHOD ]"+url.toString());
		InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
		try{
		XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		ConfirmFrndRequestParser parser=new ConfirmFrndRequestParser();
		xmlReader.setContentHandler(parser);
		xmlReader.parse(new InputSource(response));
		status=parser.getConfirmStatusCode();
		
		}catch(Exception e){
		
		}
		return status;

	}
	
	
	//recommend venue
	public RecommendVenueStatus parserRecommendedVenue(String xml){
		StringBuilder url=new StringBuilder();
		RecommendVenueStatus status = null;
		url.append(PartyFinderConstants.URL_RECOMMENDED_VENUE);
		Log.i("",TAG+PartyFinderConstants.INFO +url.toString());
		InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
		try{
		XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		RecommendVenueStatusParser parser=new RecommendVenueStatusParser();
		xmlReader.setContentHandler(parser);
		xmlReader.parse(new InputSource(response));
		status=parser.getRecommendVenueStatus();
		
		}catch(Exception e){
		
		}
		return status;

	}
	
	//Vanues Like
	
	public VanuesLikeStatus parserVanueLikeStatus(String xml){
		StringBuilder url=new StringBuilder();
		VanuesLikeStatus status=null;
		url.append(PartyFinderConstants.URL_VANUE_LIKE);
		
//		Log.i("", "Venue Like Url: "+url.toString());
		
		InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			VanuesLikeParser parser=new VanuesLikeParser();
			xmlReader.setContentHandler(parser);
			xmlReader.parse(new InputSource(response));
			status=parser.getVanueLikeStatus();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return status;
		
	}
	// Add fav vanues....
	
	public AddFavoriteVanuesStatus parserAddFavoriteVanuesStatus(String xml){
		StringBuilder url=new StringBuilder();
		AddFavoriteVanuesStatus status=null;
		url.append(PartyFinderConstants.URL_ADD_FAVORITE_VENUE);
		
//		Log.i("", "Add Fav Venue Url: "+url.toString());
		
		InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			AddFavoriteVanuesParser parser=new AddFavoriteVanuesParser();
			xmlReader.setContentHandler(parser);
			xmlReader.parse(new InputSource(response));
			status=parser.getAddFavoriteStatusCode();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return status;
		
	}
	
	
	//Registration old one
	public static RegistrationStatus parseRegistrationStatusCode(InputStream is) throws IOException{
		RegistrationStatus mRegistrationStatusCode = null;
		//create a XmlReader from SaxParser
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			RegistrationStatusParser saxRegStatusCodeHandler = new RegistrationStatusParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxRegStatusCodeHandler);
			//the process starts
			xmlReader.parse(new InputSource(is));
			//get the event list
			mRegistrationStatusCode = saxRegStatusCodeHandler.getRegistrationStatusCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mRegistrationStatusCode;

	}
	
	//Registration
	public static RegistrationStatus parseRegistrationStatusCode(String xml) {
		  RegistrationStatus mRegistrationStatusCode = null;
		 
		  String url = PartyFinderConstants.URL_REGISTRATION;
		  InputStream inputStream = null;
		  //create a XmlReader from SaxParser
		  try {
//			  Log.i("","Prepared Xml : "+xml+" Registration Url : "+url);
		   inputStream=CustomHttpClient.postInputStreamRequest(url, xml);
		   
		   XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		   //create a SaxHandler
		   RegistrationStatusParser saxRegStatusCodeHandler = new RegistrationStatusParser();
		   //store handler in xmlReader
		   xmlReader.setContentHandler(saxRegStatusCodeHandler);
		   //the process starts
		   xmlReader.parse(new InputSource(inputStream));
		   //get the event list
		   mRegistrationStatusCode = saxRegStatusCodeHandler.getRegistrationStatusCode();
		  } catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  return mRegistrationStatusCode;

		 }
	
	
	

	//UserActivityfeed Manager
	public static ArrayList<UserActivityFeeds> parseUserActivityFeedItem(InputStream is) throws IOException{
		ArrayList<UserActivityFeeds> mUserActivityFeedItem = null;
		//create a XmlReader from SaxParser
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			UsersActivityFeedsParser saxActivityFeedHandler = new UsersActivityFeedsParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxActivityFeedHandler);
			//the process starts
			xmlReader.parse(new InputSource(is));
			//get the event list
			mUserActivityFeedItem = saxActivityFeedHandler.getUActivityFeeds();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mUserActivityFeedItem;
	}
	
	public  ArrayList<UserActivityFeeds> parseUserActivityFeedItem1(String userLoginItem) throws IOException{
		ArrayList<UserActivityFeeds> mUserActivityFeeds = null;
		InputStream inputStream = null;
		
		//inputStream).setEncoding("UTF-8");
//		StringBuilder url=new StringBuilder();
		
//		url.append("http://services.partyfinder.com.br/Service1.svc/GetActivityFeeds/U0006");
	//	url.append(PartyFinderConstants.URL_USER_ACTIVITY_FEED).append(userLoginItem.getVc_user_code());
//		Log.i("","urllllllll"+url);
		
		String userCode=userLoginItem.replaceAll("[^a-zA-Z0-9_-]","");
		String url=PartyFinderConstants.URL_USER_ACTIVITY_FEED+userCode;
//		Log.i("","urllllllll"+url);
		
		//Log.d(""," Url ActivityFeeds "+url);
	
		inputStream=CustomHttpClient.getInputStreamRequest(url.toString());
//		String response=CustomHttpClient.getStringRequest(url.toString());
		
//		Log.i("","Input Stream Valve :"+response);
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			UsersActivityFeedsParser saxActivityFeedHandler = new UsersActivityFeedsParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxActivityFeedHandler);
			//the process starts
			Reader reader = new InputStreamReader(
					inputStream,"UTF-8");
			xmlReader.parse(new InputSource(reader));
			//get the event list
			mUserActivityFeeds = saxActivityFeedHandler.getUActivityFeeds();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			Log.i("",PartyFinderConstants.MAIN_EXCEPTION+TAG+"[ parseUserActivityFeedItem ] [Exception] : "+e.toString());
		}
		return mUserActivityFeeds;

	}
	
	

	//Vanues Manager
	public static ArrayList<VenuesItem> parseVenuesItem(UserLoginItem userLoginItem) throws IOException{
		ArrayList<VenuesItem> mVenuesItems = null;
		InputStream inputStream;
		StringBuilder url=new StringBuilder();
		url.append(PartyFinderConstants.URL_VENUES).append(userLoginItem.getVc_user_code());
//		Log.i("","Venues Url ..."+url);
		inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
		//create a XmlReader from SaxParser
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			VanuesParser saxActivityFeedHandler = new VanuesParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxActivityFeedHandler);
			//the process starts
			xmlReader.parse(new InputSource(inputStream));
			//get the event list
			mVenuesItems = saxActivityFeedHandler.getVanuesItem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mVenuesItems;
	}
	//Agenda Manager
	public static ArrayList<GetEventAgendaItem> parseAgendaItems(InputStream is) throws IOException{
		ArrayList<GetEventAgendaItem> mAgendaItems = null;
		//create a XmlReader from SaxParser
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			GetEventAgendaParser saxActivityFeedHandler = new GetEventAgendaParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxActivityFeedHandler);
			//the process starts
			xmlReader.parse(new InputSource(is));
			//get the event list
			mAgendaItems = saxActivityFeedHandler.getEventaAgenda();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mAgendaItems;
	}

	//FavouriteVenues Manager
	public ArrayList<VenuesItem> parseFavouriteItems(UserLoginItem userLoginItem) throws IOException{
		ArrayList<VenuesItem> mFavouriteVenueItems = null;
//		Log.i("",TAG+"parseFavouriteItems calling..");
		InputStream inputStream;
		StringBuilder url=new StringBuilder();
//		Log.i("",TAG+"parseFavouriteItems calling..");
		url.append(PartyFinderConstants.URL_FAVOURITE_VENUES).append(userLoginItem.getVc_user_code());
//		Log.i("",TAG+"parseFavouriteItems calling.."+url.toString());
		inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
		//create a XmlReader from SaxParser
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			FavouriteVenuesParser saxActivityFeedHandler = new FavouriteVenuesParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(saxActivityFeedHandler);
			//the process starts
			xmlReader.parse(new InputSource(inputStream));
			//get the event list
			mFavouriteVenueItems = saxActivityFeedHandler.getFavouriteVanues();
		} catch (Exception e) {
//			Log.i("", "exception...."+e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mFavouriteVenueItems;
	}
	
	
	/*//GetUserFriendList Manager
		public static ArrayList<FriendListItem> parseGetFriendListItems(InputStream is) throws IOException{
			ArrayList<FriendListItem> mGetFriendListItemsList = null;
			//create a XmlReader from SaxParser
			try {
				XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				//create a SaxHandler
				GetFriendListParser1 saxActivityFeedHandler = new GetFriendListParser1();
				//store handler in xmlReader
				xmlReader.setContentHandler(saxActivityFeedHandler);
				//the process starts
				xmlReader.parse(new InputSource(is));
				//get the event list
				//mGetFriendListItemsList = saxActivityFeedHandler.getGetFriendListItem();
				mGetFriendListItemsList = saxActivityFeedHandler.getGetFriendListItem();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mGetFriendListItemsList;
		}*/
		
//		SEARCH FRNDLIST
		public  ArrayList<FriendListItem> parseSearchFriendList(UserLoginItem userItem,String searchText) throws IOException{
			ArrayList<FriendListItem> mGetFriendListItemsList = null;
			//create a XmlReader from SaxParser
			InputStream InputStream;
			StringBuilder url=new StringBuilder();
			url.append(PartyFinderConstants.URL_SEARCHFRND).append(userItem.getVc_user_code()).append("/").append(searchText);
			//url =PartyFinderConstants.URL_SEARCHFRND+userItem.getVc_user_code()+"/"+searchText;
			//InputStream is=HttpClient.getInputStreamRequest(url);
			Log.i("", "Searchend url friend"+url.toString());
			InputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
			try {
				XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				//create a SaxHandler
				GetFriendListParser1 saxActivityFeedHandler = new GetFriendListParser1();
				//store handler in xmlReader
				xmlReader.setContentHandler(saxActivityFeedHandler);
				//the process starts
				xmlReader.parse(new InputSource(InputStream));
				//get the event list
				mGetFriendListItemsList = saxActivityFeedHandler.getGetFriendListItem();
				Log.i("", "Searchend url friend"+mGetFriendListItemsList.size());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mGetFriendListItemsList;
		}
		
		
		
		
		
		
		
		//////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////
		
		
		
		
		public  ArrayList<FriendListItem> parseGetFriendListItems(Object userItem) throws IOException{
			ArrayList<FriendListItem> mGetFriendListItemsList = null;
			//create a XmlReader from SaxParser
			InputStream inputStream;
			UserLoginItem mUserLoginItem;
			FriendListItem mFriendListItem;
			StringBuilder url=new StringBuilder();
			if(userItem instanceof UserLoginItem)
			{
				mUserLoginItem=(UserLoginItem) userItem;
				url.append(PartyFinderConstants.URL_GET_FRINEND_LIST).append(mUserLoginItem.getVc_user_code());
			}else{
				mFriendListItem=(FriendListItem) userItem;
				url.append(PartyFinderConstants.URL_GET_FRINEND_LIST).append(mFriendListItem.getUserCode());
			}
			
//			Log.i("",TAG+"Frn list url"+url.toString());
			inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
		
			try {
				XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				//create a SaxHandler
				GetFriendListParser1 saxActivityFeedHandler = new GetFriendListParser1();
				//store handler in xmlReader
				xmlReader.setContentHandler(saxActivityFeedHandler);
				//the process starts
				xmlReader.parse(new InputSource(inputStream));
				//get the event list
				mGetFriendListItemsList = saxActivityFeedHandler.getGetFriendListItem();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mGetFriendListItemsList;
		}
		
		public  ArrayList<RequestedFriendItem> parseGetRequestFriendList(UserLoginItem userItem) throws IOException{
			ArrayList<RequestedFriendItem> mGetFriendListItemsList = null;
			//create a XmlReader from SaxParser
			InputStream inputStream;
			
			StringBuilder url=new StringBuilder();
			url.append(PartyFinderConstants.URL_GET_FRIEND_REQUEST).append(userItem.getVc_user_code());
			inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
			try {
				XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				//create a SaxHandler
				GetFriendRequestParser saxActivityFeedHandler = new GetFriendRequestParser();
				//store handler in xmlReader
				xmlReader.setContentHandler(saxActivityFeedHandler);
				//the process starts
				xmlReader.parse(new InputSource(inputStream));
				//get the event list
				mGetFriendListItemsList = saxActivityFeedHandler.getGetFriendRequestItem();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mGetFriendListItemsList;
		}
		
		/*
		 * Mehtod to get userEditInfo status code
		 * @param InputStream response
		 */
		
/*		public static RegistrationStatus parseRegistrationStatusCode(String xml) throws IOException{
			  RegistrationStatus mRegistrationStatusCode = null;
			  Log.i("","Prepared Xml : "+xml);
			  String url = PartyFinderConstants.URL_REGISTRATION;
			  InputStream inputStream = null;
			  //create a XmlReader from SaxParser
			  try {
			  
			   inputStream=CustomHttpClient.postInputStreamRequest(url, xml);
			   
			   XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			   //create a SaxHandler
			   RegistrationStatusParser saxRegStatusCodeHandler = new RegistrationStatusParser();
			   //store handler in xmlReader
			   xmlReader.setContentHandler(saxRegStatusCodeHandler);
			   //the process starts
			   xmlReader.parse(new InputSource(inputStream));
			   //get the event list
			   mRegistrationStatusCode = saxRegStatusCodeHandler.getRegistrationStatusCode();
			  } catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			  }
			  return mRegistrationStatusCode;

			 }
		*/
		public static EditUserInfoItem parseEditUserInfoStatus(String xml){
			EditUserInfoItem mUserEditInfoStatus = null;
			Log.i("","Prepared Xml : "+xml);
			
			String url=PartyFinderConstants.URL_UPDATE_USER_INFO;
			InputStream inputStream=null;
			//create a XmlReader from SaxParser
			try {
				
				inputStream=CustomHttpClient.postInputStreamRequest(url, xml);
				
				XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				//create a SaxHandler
				EditUserInfoParser saxRegStatusCodeHandler = new EditUserInfoParser();
				//store handler in xmlReader
				xmlReader.setContentHandler(saxRegStatusCodeHandler);
				//the process starts
				xmlReader.parse(new InputSource(inputStream));
				//get the event list
				mUserEditInfoStatus = saxRegStatusCodeHandler.getEditUserInfoStatusCode();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mUserEditInfoStatus;

		}
		
		
		public static ArrayList<PurchaseTicketItem> parsePurchaseTicketItem(UserLoginItem userLoginItem) throws IOException{
			ArrayList<PurchaseTicketItem> mTicketItems = null;
			InputStream inputStream;
			StringBuilder url=new StringBuilder();
			url.append(PartyFinderConstants.URL_PURCHASE_TICKET);
			Log.i("",TAG+"xxxxxxxxxxxxxxxxxxxx"+url);
			inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
			
			
			try {
				//create a XmlReader from SaxParser
				XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				//create a SaxHandler
				PurchaseTicketParser saxUpcomingEventHandler=new PurchaseTicketParser();
				//store handler in xmlReader
				xmlReader.setContentHandler(saxUpcomingEventHandler);
				//the process starts
				xmlReader.parse(new InputSource(inputStream));
				//get the event list
				mTicketItems = saxUpcomingEventHandler.getPurchaseTIcket();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mTicketItems;

		}
		
/*		
		//GetUserFriendRequest 
				public static ArrayList<GetFriendListItem> parseGetFriendRequest(InputStream is) throws IOException{
					ArrayList<GetFriendListItem> mGetFriendRequestList = null;
					//create a XmlReader from SaxParser
					try {
						XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
						//create a SaxHandler
						GetFriendRequestParser saxActivityFeedHandler = new GetFriendRequestParser();
						//store handler in xmlReader
						xmlReader.setContentHandler(saxActivityFeedHandler);
						//the process starts
						xmlReader.parse(new InputSource(is));
						//get the event list
						mGetFriendRequestList = saxActivityFeedHandler.getGetFriendRequestItem();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return mGetFriendRequestList;
				}*/
		
		public static ArrayList<OrderTransectionDetailsItems> parseGetOrderTransectionDetails(UserLoginItem userLoginItem) throws IOException{
			ArrayList<OrderTransectionDetailsItems> mGetOrderTransectionDetailList = null;
//			Log.i("", TAG+"OrderHistoryFragment is calling....");
			InputStream inputStream;
			StringBuilder url = new StringBuilder();
			url.append(PartyFinderConstants.URL_GET_ORDER_TRANSECTION_DETAILS).append(userLoginItem.getVc_user_code());
//			url.append("http://services.partyfinder.com.br/Service1.svc/GetOrderTransection/U00012");

			
			Log.i("",TAG+ "OrderHistory Url...."+url);
			inputStream=CustomHttpClient.getInputStreamRequest(url.toString());
			try{
				XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				OrderTransectionDetailsParser saxActivityFeedHandler = new  OrderTransectionDetailsParser();
				xmlReader.setContentHandler(saxActivityFeedHandler);
		    xmlReader.parse(new InputSource(inputStream));
				mGetOrderTransectionDetailList = saxActivityFeedHandler.getOrderDetails();		
				
			}
			catch(Exception e)
			{
			e.printStackTrace();
			}
			return mGetOrderTransectionDetailList;
		}
			
		
		
		
		
		
		
		
		
		//Friends Favourite Venues Manager
		public ArrayList<VenuesItem> parseFriendsFavouriteItems(Object userItem){
			ArrayList<VenuesItem> mFavouriteVenueItems = null;
//			Log.i("",TAG+"parseFavouriteItems calling..");
			InputStream inputStream;
			
			FriendListItem mFriendListItem;
			mFriendListItem=(FriendListItem) userItem;
			
			
			StringBuilder url=new StringBuilder();
//			Log.i("",TAG+"parseFavouriteItems calling..");
			url.append(PartyFinderConstants.URL_FAVOURITE_VENUES).append(mFriendListItem.getUserCode());
			Log.i("",TAG+"parseFavouriteItems calling.."+url.toString());
			inputStream=com.partyfinder.app.utils.CustomHttpClient.getInputStreamRequest(url.toString());
			//create a XmlReader from SaxParser
			try {
				XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				//create a SaxHandler
				FavouriteVenuesParser saxActivityFeedHandler = new FavouriteVenuesParser();
				//store handler in xmlReader
				xmlReader.setContentHandler(saxActivityFeedHandler);
				//the process starts
				xmlReader.parse(new InputSource(inputStream));
				//get the event list
				mFavouriteVenueItems = saxActivityFeedHandler.getFavouriteVanues();
			} catch (Exception e) {
//				Log.i("", "exception...."+e.toString());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mFavouriteVenueItems;
		}
		
		
		public ArrayList<ReserveTableItem> parseReserveTable(EventItem mEventItem){
			ArrayList<ReserveTableItem> reservTable=null;
			
			StringBuilder url=new StringBuilder();
			url.append(PartyFinderConstants.URL_RESERVETABLE).append(mEventItem.getVcEventCode());
			Log.i("",TAG+"GET RESERVE TABLE URL : "+url.toString());
//		  String GraphUrl="http://services.pfdev.com.br/service1.svc/GetCabinTableDetails/E00065";
//			InputStream inputStream=HttpClient.getInputStreamRequest(GraphUrl);
		  
			InputStream inputStream=HttpClient.getInputStreamRequest(url.toString());
			try{
				XMLReader xmlReader=SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				ReserveTableParser tableParser=new ReserveTableParser();
				xmlReader.setContentHandler(tableParser);
				xmlReader.parse(new InputSource(inputStream));
				reservTable=tableParser.getReservTableList();
			}catch(Exception e){
				
			}
			return reservTable;
		}

		/*
		public ArrayList<TicketDetailsItem> parseTicketList(EventItem mEventItem){
			ArrayList<TicketDetailsItem> ticketList = null;
			StringBuilder url=new StringBuilder();
			url.append(PartyFinderConstants.GETTICKET_DETAIL).append(mEventItem.getVcEventCode());
			Log.i("",TAG+"GET TICKET DETAILS URL : "+url.toString());
			InputStream inputStream=HttpClient.getInputStreamRequest(url.toString());
			//create a XmlReader from SaxParser
			try{
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			
			TicketParser ticktDetParser=new TicketParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(ticktDetParser);
			//the process starts
			xmlReader.parse(new InputSource(inputStream));
			ticketList=ticktDetParser.getTicketList();
			//get the event list
			}catch(Exception e){
				Log.i("",PartyFinderConstants.MAIN_EXCEPTION+TAG+"[ PARSETICKETLIST METHOD ]"+"[ EXCEPTION :  ]"+e.toString());
			}
			return ticketList;
		}
		*/
		
		public ArrayList<TicketDetailsItem> parseTicketList(EventItem mEventItem){
			ArrayList<TicketDetailsItem> ticketList = null;
			StringBuilder url=new StringBuilder();
			url.append(PartyFinderConstants.GETTICKET_DETAIL).append(mEventItem.getVcEventCode());
//			Log.i("",TAG+"GET TICKET DETAILS URL : "+url.toString());
			InputStream inputStream=HttpClient.getInputStreamRequest(url.toString());
			//create a XmlReader from SaxParser
			try{
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			//create a SaxHandler
			
			TicketParser ticktDetParser=new TicketParser();
			//store handler in xmlReader
			xmlReader.setContentHandler(ticktDetParser);
			//the process starts
			xmlReader.parse(new InputSource(inputStream));
			ticketList=ticktDetParser.getTicketList();
			//get the event list
			}catch(Exception e){
//				Log.i("",PartyFinderConstants.MAIN_EXCEPTION+TAG+"[ PARSETICKETLIST METHOD ]"+"[ EXCEPTION :  ]"+e.toString());
			}
			return ticketList;
		}
		
		/*public ArrayList<WhatsHotItem> parseWhatshot(String url){
			
			ArrayList<WhatsHotItem> whatsHotList = null;
			InputStream is=null;
			try{
			StringBuilder prepareUrl=new StringBuilder();
			prepareUrl.append(url);
			Log.i("","Whats hot url: "+prepareUrl.toString());
			is=CustomHttpClient.getInputStreamRequest(prepareUrl.toString());
			XMLReader xmlReader=SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			WhatsHotParser parser=new WhatsHotParser();
			xmlReader.setContentHandler(parser);
			xmlReader.parse(new InputSource(is));
			whatsHotList=parser.getEventItems();
			}catch(Exception e){
				Log.i("","Whats hot Exception "+e.toString());

			}
			return whatsHotList;
			
		}	
		*/
		
		
		 public ArrayList<EventItem> parseWhatshot(){
			 
		      
		      ArrayList<EventItem> whatsHotList = null;
		      InputStream is=null;
		      try{
		      StringBuilder prepareUrl=new StringBuilder();
		      prepareUrl.append(PartyFinderConstants.URL_WHATS_HOT);
		      Log.i("",TAG+"Whats hot url: "+prepareUrl.toString());
		      is=CustomHttpClient.getInputStreamRequest(prepareUrl.toString());
		      XMLReader xmlReader=SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		      WhatsHotParser parser=new WhatsHotParser();
		      xmlReader.setContentHandler(parser);
		      xmlReader.parse(new InputSource(is));
		      whatsHotList=parser.getEventItems();
		      }catch(Exception e){
//		       Log.i("","Whats hot Exception "+e.toString());

		      }
		      return whatsHotList;
		      
		     }
		 
		 
		 
		 public OrderAssignItem parseAssignOrder(String xml){
			    StringBuilder url=new StringBuilder();
			    OrderAssignItem status = null;
			    url.append(PartyFinderConstants.URL_ASSIGN_ORDER);
			    Log.i("",TAG+PartyFinderConstants.INFO+TAG+"[ PARSE ASSIGN ORDER ]"+url.toString()+" PREPARED XML : "+xml);
			    InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
			    try{
			    XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			    OrderAsignParser parser=new OrderAsignParser();
			    xmlReader.setContentHandler(parser);
			    xmlReader.parse(new InputSource(response));
			    status=parser.getOrderItem();
			    
			    }catch(Exception e){
			       Log.i("","Asign Order Exception "+e.toString());
			    }
			    return status;
			    

			   }
			   
			   
			  public String parseDummyOrderHeader(String url,String xml){
			   StringBuilder strBuilder=new StringBuilder();
			   String status = null;
			   strBuilder.append(url);
			   Log.i("",TAG+"PARSE DUMMY ORDER HEADER "+strBuilder.toString()+"PREPARED XML : "+xml);
			   InputStream response=CustomHttpClient.postInputStreamRequest(strBuilder.toString(), xml);
			   try{
			    XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			    OrderDummyHeaderParser parser=new OrderDummyHeaderParser();
			    xmlReader.setContentHandler(parser);
			    xmlReader.parse(new InputSource(response));
			    status=parser.getStatus();
			    Log.i("",TAG+"PARSE DUMMY ORDER HEADER Asign Order Exception "+status);
			    }catch(Exception e){
//			       Log.i("","Asign Order Exception "+e.toString());
			    }
			//   String success=CustomHttpClient.postInputStreamRequest(strBuilder.toString(), xml);
			   return status;
			   
			  }
			  public String getSagupaoSession(String url){
//			   Log.i("",TAG+"[ GET SAGUPAO SESSION ]"+url);
			   String sagupaoResp = null;
			   InputStream response=CustomHttpClient.postInputStreamRequest(url);
			  
			    
			    try{
			     XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			     SagupaoSessionParser parser=new SagupaoSessionParser();
			     xmlReader.setContentHandler(parser);
			     xmlReader.parse(new InputSource(response));
			     sagupaoResp=parser.getSessionId();
			     
			     }catch(Exception e){
//			        Log.i("","Sagupao Session Exception "+e.toString());
			     } 
			   
			   return sagupaoResp;
			  }
			  
			  public String getCardToken(String url){
				   Log.i("",TAG+"[ GET SAGUPAO SESSION ]"+url);
				   String sagupaoResp = null;
				   InputStream response=CustomHttpClient.postInputStreamRequest(url);
				  
				    
				    try{
				     XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				     GetCardTokenParser parser=new GetCardTokenParser();
				     xmlReader.setContentHandler(parser);
				     xmlReader.parse(new InputSource(response));
				     sagupaoResp=parser.getSessionId();
				     
				     }catch(Exception e){
//				        Log.i("","Sagupao Session Exception "+e.toString());
				     } 
				   
				   return sagupaoResp;
				  }
			  
			  public SagupaoSessionItem getCardDetail(String url){
			   Log.i("",TAG+"[ GET SAGUPAO CARD VERIFICATION ]"+url);
			   SagupaoSessionItem cardDet = null;
			   String response=CustomHttpClient.getStringRequest(url);
			   Log.i("",TAG+"[ GET SAGUPAO CARD VERIFICATION ]"+response);
			   try{
			    SagupaoCardDetailParser parser=new SagupaoCardDetailParser();
			    cardDet=parser.parseSagupaoDet(response);
			   }catch(Exception e){
			    
			   }
			   return cardDet;
			  }
			  
			  
			  
			 public String sendMundipagg(String url,String xml){
			   Log.i("",TAG+"[ SEND MUNDIPAGG ]"+url+" Mundipagg Xml: "+xml);
			   String sagupaoResp = null;
			   CustomHttpClient.postInputStreamRequest(url, xml);
			   try{
			    
			   }catch(Exception e){
			    
			   }
			   return sagupaoResp;
			  }
			  
			  public ArrayList<PromoterItem> parsePromoter(String url,String xml){
			   
			   ArrayList<PromoterItem> proList = null;
			   
			   Log.i("",TAG+"[ SEND MUNDIPAGG ]"+url+" Mundipagg Xml: "+xml);
			   InputStream response=CustomHttpClient.postInputStreamRequest(url, xml);
			   try{
			    XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			    PromoterParser parser=new PromoterParser();
			    xmlReader.setContentHandler(parser);
			    xmlReader.parse(new InputSource(response));
			    proList=parser.getPromList();
			   }catch(Exception e){
			    
			   }
			   return proList;
			  }
			  
			  
				public ArrayList<PromoterItem> parsePromoter(String url) {

					ArrayList<PromoterItem> proList = null;

//					Log.i("", TAG + "[ SEND MUNDIPAGG ]" + url );
					InputStream response = CustomHttpClient
							.getInputStreamRequest(url);
					try {
						XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
								.getXMLReader();
						PromoterParser parser = new PromoterParser();
						xmlReader.setContentHandler(parser);
						xmlReader.parse(new InputSource(response));
						proList = parser.getPromList();
					} catch (Exception e) {

					}
					return proList;
				}
				
				public String submitPromoter(String url,String xml){
//				    StringBuilder url=new StringBuilder();
//				    OrderAssignItem status = null;
//				    url.append(PartyFinderConstants.URL_ASSIGN_ORDER);
				 String status=null;
//				    Log.i("",PartyFinderConstants.INFO+TAG+"[ SUBMIT PROMOTER ]"+url.toString()+" PREPARED XML : "+xml);
				    InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
				    try{
				    XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				    SubmitPromoterResponseParser parser=new SubmitPromoterResponseParser();
				    xmlReader.setContentHandler(parser);
				    xmlReader.parse(new InputSource(response));
				    status=parser.getStatus();
				    
				    }catch(Exception e){
//				       Log.i("","Asign Order Exception "+e.toString());
				    }
					return status;
				  
				    

				   }
				
				public GuestListStatus parseGuestList(String url,String xml){
//				       Log.i("",TAG+"[ GET SAGUPAO SESSION ]"+url);
				       //String guestListResp = null;
				       GuestListStatus mGuestListStatus=null;
				       InputStream response=CustomHttpClient.postInputStreamRequest(url, xml);
//				       Log.i("",TAG +"URL Guest List :"+url+"PREPARED XML : "+xml);
				        
				     try{
				      XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				      GuestListItemParser parser=new GuestListItemParser();
				      xmlReader.setContentHandler(parser);
				      xmlReader.parse(new InputSource(response));
				      mGuestListStatus=parser.getStatus();
				      
				      }catch(Exception e){
				      
				      }
				      return mGuestListStatus;
				      }
				
				public AddFavoriteVanuesStatus parserDeleteRecommendStatus(String url,String xml){
				       //StringBuilder url=new StringBuilder();
				       AddFavoriteVanuesStatus status=null;
				       //url.append(PartyFinderConstants.URL_ADD_FAVORITE_VENUE);
				       
				       //Log.i("", "Add Fav Venue Url: "+url.toString());
//				       Log.i("", "Delete Recommend xml: Url"+url+" Prepared Xml : "+xml);
				       
				       InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
				       try {
				        XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
				        AddFavoriteVanuesParser parser=new AddFavoriteVanuesParser();
				        xmlReader.setContentHandler(parser);
				        xmlReader.parse(new InputSource(response));
				        status=parser.getAddFavoriteStatusCode();
//				        Log.i("","Delete Status : "+status.getStatus());
				        
				       } catch (Exception e) {
				        // TODO: handle exception
				       }
				        
				       return status;
				       
				      }
				
				
				public userPicUploadModel userPicUpload(String xml){
					StringBuilder url=new StringBuilder();
					userPicUploadModel status = null ;
					url.append(PartyFinderConstants.URL_EDIT_PROFILE_PIC);
					Log.i("","Service"+url.toString()+"Xml : "+xml);
					InputStream response=CustomHttpClient.postInputStreamRequest(url.toString(), xml);
					try{
					XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
					UserPicUploadParser parser=new UserPicUploadParser();
					xmlReader.setContentHandler(parser);
					xmlReader.parse(new InputSource(response));
					status=parser.GetUserPicUploadParser();
					
					}catch(Exception e){
					
					}
					return status;

				}
				
				public PostalCodeModel getPostalCodeAddress(String Url){
				PostalCodeModel mModel = null;
				Log.i("", "1111  inside content manager");
					
							try {
								Log.i("",TAG+ "1111  inside content manager try block");
						SAXParserFactory parserFactory = SAXParserFactory.newInstance();
						SAXParser saxParser = parserFactory.newSAXParser();
						XMLReader xmlReader = saxParser.getXMLReader();
						PostalCodeParser mPostalDetail=new PostalCodeParser();

						xmlReader.setContentHandler(mPostalDetail);
						URL sourceUrl = new URL(Url);
						URLConnection getXMLDataconnection = sourceUrl.openConnection();
						getXMLDataconnection
						.setConnectTimeout(PartyFinderConstants.CONNECTION_TIMEOUT);


						Reader reader = new InputStreamReader(
								getXMLDataconnection.getInputStream(),"UTF-8");
						InputSource source = new InputSource(reader);
						xmlReader.parse(source);



						mModel=mPostalDetail.getUserInfo();
						Log.i("", TAG+"11111  model item :" +mModel.getUserBairro());

					} catch (Exception e) {
						// TODO Auto-generated catch block
				Log.i("","Exception : "+e.toString());
				
					}
					return mModel;


				}
				
				  
			
				
		
}

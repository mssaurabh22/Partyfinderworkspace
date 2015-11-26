package com.partyfinder.AppConstant;

public interface PartyFinderConstants {






	//	static final String BASEDOMAIN="http://54.94.128.176/pfs/";

	//LIVE DOMAIN
//		static final String BASEDOMAIN="http://services.partyfinder.com.br/";

	//TEST DOMAIN
	static final String BASEDOMAIN="http://services.pfdev.com.br/";


	/*PartyFinderUser Url*//*PartyFinderUser Url*//*PartyFinderUser Url*//*PartyFinderUser Url*/


	static final String LOGIN_API=BASEDOMAIN+"Service1.svc/VALUSER";//vc_user_Email/Password";
	static final String FORGET_PASSWORD_API=BASEDOMAIN+"Service1.svc/ForgetPass";///vc_user_Email";
	static final String EVENT_API=BASEDOMAIN+"Service1.svc/GETEVENTS";
	static final String URL_ACTIVITY_FEED = BASEDOMAIN+"Service1.svc/GetActivityFeedback";//http://services.pfdev.com.br/Service1.svc/GetActivityFeedback
	static final String URL_UPCOMING_EVENT = BASEDOMAIN+"Service1.svc/GETEVENTS";
	static final String URL_ACCEPT_FRND=BASEDOMAIN+"Service1.svc/AcceptFriend";
	static final String URL_REJECT_FRND=BASEDOMAIN+"Service1.svc/RejectFriend";
	static final String EXTRA_EVENTITEM="eventItem";
	static final String EXTRA_VENUESITEM="venuesItem";
	static final String EXTRA_GET_FRIENDS_ITEM="GetFriendsListItem";
	static final String EXTRA_PROFILE_NAVIGATE="profileNavigate";
	static final String URL_WHATS_HOT=BASEDOMAIN+"Service1.svc/GETHOTEVENTS";
	static final String URL_REGISTRATION=BASEDOMAIN+"Service1.svc/Registration";
	static final String URL_GET_ORDER_TRANSECTION_DETAILS=BASEDOMAIN+"Service1.svc/GetOrderTransection/";
	static final String URL_USER_ACTIVITY_FEED=BASEDOMAIN+"Service1.svc/GetActivityFeeds/"; //append user code http://services.pfdev.com.br/Service1.svc/GetActivityFeeds/append user code
	static final String URL_GETVENUES_AGENDA=BASEDOMAIN+"Service1.svc/GETEVENTS/";//APPEND COMPANY CODE
	static final String URL_VENUES=BASEDOMAIN+"Service1.svc/GETVENUE/";//APPEND USER CODE
	static final String URL_DELETE_RECOMMENDED_ITEM=BASEDOMAIN+"Service1.svc/DeleteRecommended";
	static final String URL_FAVOURITE_VENUES=BASEDOMAIN+"Service1.svc/GETFAVORITEVENUE/"; //APPEND USER CODE
	static final String URL_GET_FRINEND_LIST=BASEDOMAIN+"Service1.svc/GetfriendList/"; //APPEND USER CODE
	static final String URL_GET_FRIEND_REQUEST=BASEDOMAIN+"Service1.svc/GetFriendRequest/";// APPEND USER CODE
	static final String URL_SEARCHFRND=BASEDOMAIN+"Service1.svc/SEARCHUSERFRIENDS/";//USERCODE/PREFIX
	static final String URL_UPDATE_USER_INFO=BASEDOMAIN+"Service1.svc/EDITUSER";//Post Url
	static final String URL_ADD_GUEST_LIST=BASEDOMAIN+"service1.svc/AddGuestList";
	static final String URL_VANUE_LIKE=BASEDOMAIN+"/Service1.svc/VenueLikes";
	static final String URL_ADD_FAVORITE_VENUE=BASEDOMAIN+"Service1.svc/AddFavoriteVenues";
	static final String URL_RECOMMENDED=BASEDOMAIN+"Service1.svc/GetRecommended/";//Get recommended venue and events details
	static final String URL_RECOMMENDED_VENUE=BASEDOMAIN+"Service1.svc/AddRecommended";//post
	static final String URL_SEND_FRIEND_REQUEST=BASEDOMAIN+"Service1.svc/FriendRequest";//POST
	static final String URL_DELETE_FRIEND=BASEDOMAIN+"Service1.svc/RemoveFriend";
	static final String URL_PURCHASE_TICKET="http://services.partyfinder.com.br/service1.svc/GETTICKETDETAILS/E0006";
	static final String URL_RESERVETABLE=BASEDOMAIN+"Service1.svc/GetCabinTableDetails/";
	static final String GETTICKET_DETAIL=BASEDOMAIN+"service1.svc/GETTICKETDETAILS/";
	static final String URL_GET_PROMOTER=BASEDOMAIN+"/Service1.svc/GetClubPromoterDetails/";
	static final String URL_SUMIT_PROMOTER=BASEDOMAIN+"/Service1.svc/UpdateOrderClubPromoter";//submitPromoter
	static final String URL_ASSIGN_ORDER=BASEDOMAIN+"service1.svc/AssignOrderNO";
	static final String URL_ASSIGN_ORDER_HERADER=BASEDOMAIN+"service1.svc/AddOrderDummyHeadDetails";
	static final String URL_EDIT_PROFILE_PIC=BASEDOMAIN+"Service1.svc//SaveProfileImage";



	/*PartyFinderUser Url*//*PartyFinderUser Url*//*PartyFinderUser Url*//*PartyFinderUser Url*/


	static final String PARTY_FINDER_USER_PREF="partyFinderUser";
	static final String LOGIN_PREFRENCE="loginPrefrence";
	static final String GCM_SHAREDKEY="gcmRegistrationId";
	static final String APP_VERSION="appVersion";
	static final String GCM_PUSHEXTRA="pushNotification";
	static final String SENDER_ID ="51970812989";   
	boolean 			PARSER_ENABLE=true;
	int 				CONNECTION_TIMEOUT = 50000;
	static final String MAIN_EXCEPTION="[MAIN EXCEPTION]";
	static final String INFO="[ DATA INFORMATION ]";
	static final String ORDER_STATUS_PENDING1	=	"1";
	static final String ORDER_STATUS_PENDING2	=	"2";
	static final String ORDER_STATUS_CONFIRMED1	=	"3";
	static final String ORDER_STATUS_CONFIRMED2	=	"4";
	static final String ORDER_STATUS_REJECTED1	=	"5";
	static final String ORDER_STATUS_REJECTED2	=	"6";
	static final String ORDER_STATUS_CANCELED	=	"7";
	static final String CARD_PREF="creaditCardPref";
	static final String Cpfhint="(Cpf (000.000.000-00)";



}

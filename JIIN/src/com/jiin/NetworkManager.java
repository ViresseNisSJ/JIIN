package com.jiin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.jiin.menu.ChangePwData;
import com.jiin.menu.CustomerServiceData;
import com.jiin.menu.LogoutData;
import com.jiin.menu.NoticeData;
import com.jiin.menu.SetupData;
import com.jiin.menu.WithdrawData;
import com.jiin.menu.message.MessageContentData;
import com.jiin.menu.message.MessageData;
import com.jiin.myprofile.BasicData;
import com.jiin.myprofile.CertifyData;
import com.jiin.myprofile.OptionData;
import com.jiin.myprofile.qna.QnaData;
import com.jiin.start.LoginData;
import com.jiin.start.PropertyManager;
import com.jiin.start.SignupData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class NetworkManager {
	private static NetworkManager instance;
	public static NetworkManager getInstnace() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	AsyncHttpClient client;
	private NetworkManager() {
		
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
			socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client = new AsyncHttpClient();
			client.setSSLSocketFactory(socketFactory);			
			client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
			client.setTimeout(30000);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
	}
	
	public HttpClient getHttpClient() {
		return client.getHttpClient();
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onFail(int code);
	}
	
	public static final String SERVER = JiinConstant.SERVER_URL;
	public static final String SERVER_S = JiinConstant.SERVER_URL_S ;
	public static final String USERLIST = SERVER+"/user/userList";
	public static final String SET_MY_BASIC_INFORM = SERVER+"/user/setMyBasicInform";
	public static final String SET_MY_OPTION_INFORM = SERVER+"/user/setMyOtherInform";
	public static final String SIGNUP = SERVER_S + "/user/signup";
	public static final String LOGIN = SERVER + "/user/login";
	public static final String NOTICE = SERVER + "/notice";
	public static final String LIKE_USER_LIST = SERVER + "/user/likeUserList";
	public static final String LOGOUT = SERVER + "/user/logout";
	public static final String INQUIRY = SERVER + "/inquiry";
	public static final String WITHDRAW = SERVER + "/user/withdraw";
	public static final String EDIT_PASSWORD = SERVER_S + "/user/editPassword";
	public static final String EVIDENCE = SERVER + "/user/evidence";
	public static final String LIKE_USER = SERVER + "/user/likeUser";
	public static final String UNLIKE_USER = SERVER + "/user/unlikeUser";
	public static final String SEND_MESSAGE = SERVER + "/user/sendMessage";
	public static final String FIND_PASSWORD = SERVER_S + "/user/findPassword";
	public static final String QNA_LIST = SERVER + "/user/qnaList";
	public static final String RESPONSE_QNA = SERVER + "/user/responseQnA";
	public static final String GET_MESSAGE_LIST = SERVER + "/user/getMessageList";
	public static final String GET_MESSAGE = SERVER + "/user/getMessage";
	public static final String USER_INFORM = SERVER + "/user/userInform";
	public static final String SET_SYSTEM_ALARM = SERVER+ "/user/setSystemAlarm";
	public static final String SET_MATCHING_ALARM = SERVER+ "/user/setMatchingAlarm";
	public static final String SET_CHECK_PUBLIC = SERVER+ "/user/setCheckPublic";
	public static final String SET_RELATION = SERVER+ "/user/setRelation";
	public static final String GET_MY_SETTING = SERVER + "/user/getMySetting";
	public static final String MY_INFORM = SERVER + "/user/myInform";
	public static final String SIGNUP_FACEBOOK = SERVER_S + "/user/signupFacebook";
	public static final String LOGIN_FACEBOOK = SERVER_S + "/user/loginFacebook";
	public static final String CHECK_GCM = SERVER + "/user/checkGCM";
	public static final String INVITE = SERVER + "/user/invite";
	public static final String CHECK_CODE = SERVER + "/user/checkCode";
	
	public void getOtherlist(Context context, final OnResultListener<OtherlistData> listener) {
		RequestParams params = new RequestParams();
		
		client.post(context, USERLIST+"Test", params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				OtherlistData result = gson.fromJson(responseString, OtherlistData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
		
	}
	
	public void putMyBasicInform(Context context, String mainPicture, String background, String nickName, int age, String area, String job,
			String university, ArrayList<String> pictures,
			final OnResultListener<BasicData> listener) throws FileNotFoundException{
		RequestParams params = new RequestParams();
		params.put("mainPicture", new File(mainPicture));
		//params.put("pictures", new File(mainPicture));
		if(background!=null){
			params.put("background", new File(background));
		}
		
		for (String p : pictures) {
			params.put("pictures", new File(p));
		}
		params.put("nickName", nickName);
		params.put("age", age);
		params.put("area", area);
		params.put("job", job);
		params.put("university", university);
		
		client.post(context, SET_MY_BASIC_INFORM, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				Gson gson = new Gson();
				BasicData result = gson.fromJson(responseString, BasicData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putMyOptionInform(Context context, String height,int bodyType, int drinking, 
			int smoke, int religion,  ArrayList<Integer> character, ArrayList<Integer> hobbies,
			final OnResultListener<OptionData> onResultListener){
		RequestParams params = new RequestParams();

		
		params.put("height",height);
		params.put("bodyType",bodyType);
		params.put("drinking", drinking);
		params.put("smoke", smoke);
		params.put("religion",religion);
		for(Integer i : hobbies) {
			params.add("hobbies", ""+i);
		}
		for(Integer i : character) {
			params.add("characters", ""+i);
		}
		
		client.post(context, SET_MY_OPTION_INFORM, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				Gson gson = new Gson();
				OptionData result = gson.fromJson(responseString, OptionData.class);
				onResultListener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				onResultListener.onFail(statusCode);
			}
		});
	}
	
	public void putSignup(Context context, String userId, String pw, String gender, String code,
			final OnResultListener<SignupData> listener){
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("pw", pw);
		params.put("gender",gender);
		params.put("code", code);
		params.put("regid", PropertyManager.getInstance().getRegistrationId());
		
		client.post(context, SIGNUP, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				SignupData result = gson.fromJson(responseString, SignupData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putLogin(Context context, String userId, String pw, final OnResultListener<LoginData> listener){
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("pw", pw);
		params.put("regid", PropertyManager.getInstance().getRegistrationId());
		
		client.post(context, LOGIN, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				LoginData result = gson.fromJson(responseString, LoginData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void getNotice(Context context, final OnResultListener<NoticeData> listener) {
		RequestParams params = new RequestParams();
		
		client.get(context, NOTICE, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				NoticeData result = gson.fromJson(responseString, NoticeData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
		
	}
	
	public void getKeep(Context context, final OnResultListener<KeepData> listener) {
		RequestParams params = new RequestParams();
		
		client.post(context, LIKE_USER_LIST, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
		
	}
	
	public void getLogout(Context context, final OnResultListener<LogoutData> listener) {
		RequestParams params = new RequestParams();
		
		client.post(context, LOGOUT, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				LogoutData result = gson.fromJson(responseString, LogoutData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
		
	}
	
	public void putInquiry(Context context, String inquiryType, String content, String date, String userEmail, String picture, final OnResultListener<CustomerServiceData> listener) throws FileNotFoundException{
		RequestParams params = new RequestParams();
		
		params.put("inquiryType",inquiryType);
		params.put("content", content);
		params.put("date", date);
		params.put("userEmail", userEmail);
		params.put("picture", new File(picture));
		
		
		client.post(context, INQUIRY, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				CustomerServiceData result = gson.fromJson(responseString, CustomerServiceData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putWithdraw(Context context, String reason, final OnResultListener<WithdrawData> listener){
		RequestParams params = new RequestParams();
		
		params.put("reason", reason);
		
		
		client.post(context, WITHDRAW, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				WithdrawData result = gson.fromJson(responseString, WithdrawData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putEditPassword(Context context, String currentPassword, String editedPassword, final OnResultListener<ChangePwData> listener){
		RequestParams params = new RequestParams();
		
		params.put("currentPassword", currentPassword);
		params.put("editedPassword", editedPassword);
		
		
		client.post(context, EDIT_PASSWORD, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				ChangePwData result = gson.fromJson(responseString, ChangePwData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putEvidence(Context context, ArrayList<String> evidence, final OnResultListener<CertifyData> onResultListener) throws FileNotFoundException {
		RequestParams params = new RequestParams();
		for (String e : evidence) {
			params.put("evidences", new File(e));
		}
		
		client.post(context, EVIDENCE, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				CertifyData result = gson.fromJson(responseString, CertifyData.class);
				onResultListener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				onResultListener.onFail(statusCode);
			}
		});
	}
	
	public void putKeepbtn(Context context, String requestId, int relationCount, final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		params.put("requestId", requestId);
		params.put("relationCount", relationCount);
		
		
		client.post(context, LIKE_USER, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putPass(Context context, String requestId, final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		params.put("requestId", requestId);
		
		
		client.post(context, UNLIKE_USER, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putSendMessage(Context context, String requestId, String message, final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		params.put("requestId", requestId);
		params.put("message", message);
		
		
		client.post(context, SEND_MESSAGE, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putFindPassword(Context context, String userEmail, final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		params.put("userEmail", userEmail);
		
		
		client.post(context, FIND_PASSWORD, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void getQnaList(Context context, String requestId, final OnResultListener<QnaData> listener){
		RequestParams params = new RequestParams();
		params.put("requestId", requestId);
		
		
		client.post(context, QNA_LIST, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				QnaData result = gson.fromJson(responseString, QnaData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putResponseQna(Context context, String question_id, int myAnswer, ArrayList<Integer> wishAnswer, final OnResultListener<OptionData> listener){
		RequestParams params = new RequestParams();
		params.put("question_id", question_id);
		params.put("myAnswer", myAnswer);
		for(Integer i : wishAnswer) {
			params.add("wishAnswer", ""+i);
		}		
		
		client.post(context, RESPONSE_QNA, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				OptionData result = gson.fromJson(responseString, OptionData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void getMessageList(Context context, final OnResultListener<MessageData> listener){
		RequestParams params = new RequestParams();
		
		
		client.post(context, GET_MESSAGE_LIST, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				MessageData result = gson.fromJson(responseString, MessageData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void getMessage(Context context, String requestId, final OnResultListener<MessageContentData> listener){
		RequestParams params = new RequestParams();
		params.put("requestId", requestId);
		
		
		client.post(context, GET_MESSAGE, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				MessageContentData result = gson.fromJson(responseString, MessageContentData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void getUserInform(Context context, String requestId, final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		params.put("requestId", requestId);
		
		
		client.post(context, USER_INFORM, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putSystemAlarm(Context context, String check,  final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		params.put("check", check);
		
		
		client.post(context, SET_SYSTEM_ALARM, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putMatchingAlarm(Context context, String check,  final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		params.put("check", check);
		
		
		client.post(context, SET_MATCHING_ALARM, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putCheckPublic(Context context, String check,  final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		params.put("check", check);
		
		
		client.post(context, SET_CHECK_PUBLIC, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putRelation(Context context, int relation,  final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		params.put("relation", relation);
		
		
		client.post(context, SET_RELATION, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void getMySetting(Context context, final OnResultListener<SetupData> listener){
		RequestParams params = new RequestParams();
		
		
		client.post(context, GET_MY_SETTING, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				SetupData result = gson.fromJson(responseString, SetupData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void getMyInform(Context context, final OnResultListener<MyData> listener){
		RequestParams params = new RequestParams();
		
		client.post(context, MY_INFORM, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				MyData result = gson.fromJson(responseString, MyData.class);
				MyUserListData.getInstance().setMe(result.data);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putSignupFacebook(Context context, String accessToken, String code, final OnResultListener<SignupData> listener){
		RequestParams params = new RequestParams();
		
		params.put("accessToken",accessToken);
		params.put("regid", PropertyManager.getInstance().getRegistrationId());
		params.put("code",code);
		
		client.post(context, SIGNUP_FACEBOOK, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				SignupData result = gson.fromJson(responseString, SignupData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void putLoginFacebook(Context context, String accessToken, final OnResultListener<LoginData> listener){
		RequestParams params = new RequestParams();
		
		params.put("accessToken",accessToken);
		params.put("regid", PropertyManager.getInstance().getRegistrationId());
		
		
		client.post(context, LOGIN_FACEBOOK, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Log.i("res", responseString);
				Gson gson = new Gson();
				LoginData result = gson.fromJson(responseString, LoginData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void getCheckGCM(Context context, final OnResultListener<GCMData> listener) {
		RequestParams params = new RequestParams();
		
		client.post(context,  CHECK_GCM, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				
				Gson gson = new Gson();
				GCMData result = gson.fromJson(responseString, GCMData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
		
	}

	
	public void getInvite(Context context, final OnResultListener<InviteData> listener) {
		RequestParams params = new RequestParams();
		
		client.post(context,  INVITE, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				
				Gson gson = new Gson();
				InviteData result = gson.fromJson(responseString, InviteData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
		
	}
	
	
	public void putCheckCode(Context context, String code, final OnResultListener<KeepData> listener){
		RequestParams params = new RequestParams();
		
		params.put("code",code);
		
		client.post(context, CHECK_CODE, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {

				Gson gson = new Gson();
				KeepData result = gson.fromJson(responseString, KeepData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
}

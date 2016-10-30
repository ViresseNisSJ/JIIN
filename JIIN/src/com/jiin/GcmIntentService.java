package com.jiin;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jiin.NetworkManager.OnResultListener;
import com.jiin.menu.message.MessageActivity;
import com.jiin.menu.message.MessageContentActivity;
import com.jiin.start.SplashActivity;

public class GcmIntentService extends IntentService {
	private static final String TAG="GcmIntengService";
	public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
//                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
//                sendNotification("Deleted messages on server: " +
//                        extras.toString());
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
            	String time = intent.getStringExtra("time");
            	mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						NetworkManager.getInstnace().getCheckGCM(getApplication(), new OnResultListener<GCMData>() {
							
							@Override
							public void onSuccess(GCMData result) {
								if(result.result.equals("success")){
									sendNotification(result.data);
								}else{
								}
							}
							
							@Override
							public void onFail(int code) {
							}
						});
					}
				});
            	
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(GCMitem item) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        
        

        if(item.gcmType == JiinConstant.DISACCEPT){
        	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), 0);
        	
        	NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.jiin_icon)
            .setContentTitle("JIIN: 가입 승인 거절")
            .setStyle(new NotificationCompat.BigTextStyle()
            .bigText(item.message))
            .setContentText(item.message)
        	.setAutoCancel(true);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }else if(item.gcmType == JiinConstant.ACCEPT){
        	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), 0);
        	
        	NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.jiin_icon)
            .setContentTitle("JIIN: 가입 승인 허용")
            .setStyle(new NotificationCompat.BigTextStyle()
            .bigText(item.message))
            .setContentText(item.message)
        	.setAutoCancel(true);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }else if(item.gcmType == JiinConstant.MESSAGE){
        	//Intent intent = new Intent(this, SplashActivity.class);
        	Intent intent = new Intent(this,SplashActivity.class);
        	intent.putExtra("gcmType", item.gcmType);
        	intent.putExtra("requestId", item.user._id);//requestId는 상대방_id 
        	intent.putExtra("nickName", item.user.nickName);
        	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        	NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.jiin_icon)
            .setContentTitle("JIIN: 쪽지 도착")
            .setStyle(new NotificationCompat.BigTextStyle()
            .bigText(item.user.nickName+"님으로부터 쪽지가 도착했습니다"))
            .setContentText(item.user.nickName+"님으로부터 쪽지가 도착했습니다")
        	.setAutoCancel(true);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
        
    }
}

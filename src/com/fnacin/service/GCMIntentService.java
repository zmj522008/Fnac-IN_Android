package com.fnacin.service;

import static com.fnacin.service.CommonUtilities.SENDER_ID;
import static com.fnacin.service.CommonUtilities.displayMessage;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.fnacin.Activities.Home;
import com.fnacin.Activities.Login;
import com.fnacin.Activities.R;
import com.fnacin.pojo.AuthentificationInfo;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService{
	public GCMIntentService(){
		super(SENDER_ID);
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
System.out.println("TokenID registred error:"+arg1);

		/*AlertDialog.Builder errorDialogBuild = new AlertDialog.Builder(arg0);
		errorDialogBuild.setTitle("Erreur");
		errorDialogBuild.setMessage("Notification désativée");
		errorDialogBuild.setCancelable(true);
		errorDialogBuild.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		AlertDialog dialog = errorDialogBuild.create();
		dialog.show();
		*/
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 String title = intent.getExtras().getString("title");
		 String thematique = intent.getExtras().getString("thematique");
         
	        displayMessage(context, title);
	        // notifies user
	        generateNotification(context,title,thematique);
		
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		// TODO Auto-generated method stub
		// Get Session Id
				AuthentificationInfo authentificationInfo = AuthentificationService.getInstance().getAuthentificationInfo();
				String sessionId = "";
				if (authentificationInfo != null) {
					sessionId = authentificationInfo.getSessionId();
				}
		 displayMessage(context, "Your device registred with Notification");
		System.out.println("Device registered: regId---"+registrationId);
	       // ServerUtilities.register(getBaseContext(), sessionid, arg1);
		ServerUtilities.register(this, sessionId, registrationId);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	 private static void generateNotification(Context context,String tit, String thema) {
	        int icon = R.drawable.ico_fnacin;
	        long when = System.currentTimeMillis();
	        String message = thema+" : "+tit;

	        NotificationManager notificationManager = (NotificationManager)
	                context.getSystemService(Context.NOTIFICATION_SERVICE);
	        Notification notification = new Notification(icon, message, when);
	         
	        //String title = context.getString(R.string.app_name);
	         String title = "Fnac'IN";
	       
	         Intent notificationIntent = new Intent(context, Login.class);
	        // set intent so it does not start a new activity
	        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
	        notification.setLatestEventInfo(context, title, message, intent);
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	         
	        // Play default notification sound
	        notification.defaults |= Notification.DEFAULT_SOUND;
	         
	        // Vibrate if vibrate is enabled
	        notification.defaults |= Notification.DEFAULT_VIBRATE;
	        notificationManager.notify(0, notification);      
	 
	    }

}

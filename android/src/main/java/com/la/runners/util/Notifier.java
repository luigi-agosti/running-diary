package com.la.runners.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.la.runners.R;
import com.la.runners.activity.DashboardActivity;

public class Notifier {

    private static final String NOTIFICATION_TITLE = "Runner diary";
    private static final int NOTIFICATION_ID = 1;
    
    public static final void notifyBlockingProblemFromBackGround(Context context, String message) {
        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.logo;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, message, when);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent notificationIntent = new Intent(context, DashboardActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);        
        notification.setLatestEventInfo(context, NOTIFICATION_TITLE, message, contentIntent);
        nm.notify(NOTIFICATION_ID, notification);
    }
   
    public static final void notifyBlockingProblem(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static final void notifyNotBlockingProblem(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}

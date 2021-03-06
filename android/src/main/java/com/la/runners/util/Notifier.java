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
    
    public static final void notify(Context context, int resourceId, Object...objects) {
        notify(context, context.getString(resourceId, objects));
    }
    
    private static final void notify(Context context, String message) {
        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.notification2;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, message, when);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent notificationIntent = new Intent(context, DashboardActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);        
        notification.setLatestEventInfo(context, NOTIFICATION_TITLE, message, contentIntent);
        nm.notify(NOTIFICATION_ID, notification);
    }
   
    public static final void toastMessage(Context context, int resourceId, Object...objects) {
        Toast.makeText(context, context.getString(resourceId, objects), Toast.LENGTH_LONG).show();
    }

}

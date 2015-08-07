package it_minds.dk.eindberetningmobil_android.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import it_minds.dk.eindberetningmobil_android.BuildConfig;
import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.views.MonitoringActivity;

/**
 * Created by kasper on 18-07-2015.
 * simple helper
 */
public class NotificationHelper {

    public static final int ID = 10;

    /**
     * Creates a notification.
     *
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static Notification createNotification(Context context, String title, String content) {
        Intent intent = new Intent(context, MonitoringActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ID, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setNumber(0);
        builder.setContentIntent(pendingIntent);

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.ic_launcher);
        builder.setLargeIcon(icon);
        if((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)){
            //use siluhett icon
            builder.setSmallIcon(R.drawable.ic_sil);
        }else{
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        Notification notification = builder.build();
        return notification;
    }


}

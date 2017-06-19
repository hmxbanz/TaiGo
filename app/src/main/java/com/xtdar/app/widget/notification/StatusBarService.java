package com.xtdar.app.widget.notification;

/**
 * Created by Administrator on 2015/8/3.
 */
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.xtdar.app.view.activity.MainActivity;
import com.xtdar.app.R;

public class StatusBarService extends IntentService {
    private final static String TAG = "MainActivity";
    public StatusBarService() {
        super("StatusBarService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "开始下载");
        showNotification(false);
        try {
            Thread.sleep(10000);
            showNotification(true);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "下载完成");
    }

    private void showNotification(boolean finish) {
        Notification.Builder notificationBuilder = new Notification.Builder(this);
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationBuilder.setSmallIcon(R.drawable.logo); //设置图标
        notificationBuilder.setTicker("开始下载");
        notificationBuilder.setContentTitle("通知"); //设置标题
        notificationBuilder.setContentText("正在下载中……"); //消息内容
        notificationBuilder.setWhen(System.currentTimeMillis()); //发送时间
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        notificationBuilder.setAutoCancel(true);//打开程序后图标消失

        Intent intent =new Intent (this,MainActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        //用TaskStackBuilder 建的PendingIntent   点击 ucrop_back 按钮可返回指定的“android:parentActivityName”
        //TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);
        //stackBuilder.addParentStack(GuideActivity.class);
        //stackBuilder.addNextIntent(intent);
        //PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT  );

        if (finish) {
            notificationBuilder.setTicker("下载完成");
            notificationBuilder.setContentTitle("通知"); //设置标题
            notificationBuilder.setContentText("下载完成"); //消息内容
        }
        Notification notification1 = notificationBuilder.build();
        notificationManager.notify(0, notification1); // 通过通知管理器发送通知

        // 下边的两个方式可以添加音乐
        // notification.sound =Uri.parse("file:///sdcard/notification/ringer.mp3");
        // notification.sound =Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
        // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
        //notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
    }

}
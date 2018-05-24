package com.android.service;

import com.android.R;
import com.android.assist.ShowMessage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore.Audio;
import android.util.Log;
import android.widget.RemoteViews;

/**
* 消息推送,利用service实现，需要activity启动服务
* 
*/
public class ServiceTest3_service extends Service {

   private String TAG = "-----------";

   private MessageThread messageThread = null;

   // 点击查看
   private Intent messageIntent = null;
   private PendingIntent messagePendingIntent = null;

   // 通知栏消息
   private int messageNotificationID = 1000;
   private Notification messageNotification = null;                              // 是具体的状态栏通知对象，可以设置icon、文字、提示声音、振动等等参数。
   private NotificationManager messageNotificatioManager = null; // 是状态栏通知的管理类，负责发通知、清楚通知等。 
   private RemoteViews contentView = null;

   @Override
   public IBinder onBind(Intent intent) {
       // TODO Auto-generated method stub
       return null;
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
       // 初始化
       // messageNotification = new
       // Notification(R.drawable.icon,"新消息11",System.currentTimeMillis());//*简单消息版本里的*此版本不使用
       messageNotification = new Notification();
       messageNotification.icon = R.drawable.ic_launcher;// 状态栏提示图标
       messageNotification.tickerText = "嘿嘿，测试消息推送";// 状态栏提示消息

       contentView = new RemoteViews(getPackageName(), R.layout.servicetest3);// 消息内容容器
       contentView.setImageViewResource(R.id.imageView3, R.drawable.ic_launcher);// 消息容器界面的图标

       messageNotification.contentView = contentView;// 把消息容器和消息绑定

       // messageNotification.icon = R.drawable.icon;//*简单消息版本里的*此版本不使用
       // messageNotification.tickerText = "新消息11";//*简单消息版本里的*此版本不使用
       // messageNotification.when=System.currentTimeMillis();
       // //*简单消息版本里的*此版本不使用

       // messageNotification.defaults |= Notification.DEFAULT_SOUND;//声音
       // messageNotification.defaults |= Notification.DEFAULT_LIGHTS;//灯
       // messageNotification.defaults |= Notification.DEFAULT_VIBRATE;//震动

       // messageNotification.sound = Uri.parse("file:///sdcard/to.mp3");
       messageNotification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "1");// 选音乐清单的第n首歌做消息声音
       // messageNotification.ledARGB = 0xff00ff00;//灯的颜色
       // messageNotification.ledOnMS = 300; //亮的时间
       // messageNotification.ledOffMS = 1000; //灭的时间
       // messageNotification.flags |= Notification.FLAG_SHOW_LIGHTS;//显示灯

       // long v[]= {0,100,200,300}; //震动频率
       // messageNotification.vibrate = v;
       //

       messageNotification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击消息后,该消息自动退出
       messageNotification.flags |= Notification.FLAG_ONGOING_EVENT;// 在上方运行消息栏中出现
       // messageNotification.flags|=Notification.FLAG_NO_CLEAR;//此消息不会被清除

       messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       messageIntent = new Intent(this, ShowMessage.class);// 点击消息后,要跳转的界面  ( 对应 详细消息的界面 )

       // 开启线程
       messageThread = new MessageThread();// 该线程每10秒,发布一条消息出来
       messageThread.isRunning = true;// 设置为false后,线程跳出循环并结束对
       messageThread.start();
       Log.i(TAG, "startCommand");
       return super.onStartCommand(intent, flags, startId);
   }

   /**
    * 从服务器端获取消息
    */
   class MessageThread extends Thread {
       // 设置为false后,线程跳出循环并结束
       public boolean isRunning = true;

       public void run() {
           while (isRunning) {
               try {

                   String serverMessage = getServerMessage();
                   
                   if (serverMessage != null && !"".equals(serverMessage)) {
                       // 更新通知栏
                       // messageNotification.setLatestEventInfo(MessageService.this,"新消息","哇~有    新消息耶!"+serverMessage,messagePendingIntent);//*简单消息版本里的*此版本不使用

                       contentView.setTextViewText(R.id.textView1, serverMessage);// 设置消息内容

                       messageIntent.putExtra("message", serverMessage);// 为意图添加参数，传递给ShowMessage
                       messagePendingIntent = PendingIntent.getActivity(ServiceTest3_service.this, 0, messageIntent,
                               PendingIntent.FLAG_CANCEL_CURRENT);// 将意图装入 延迟意图
                       messageNotification.contentIntent = messagePendingIntent;// 将延迟意图装入消息
                       messageNotificatioManager.notify(messageNotificationID, messageNotification);// 启动Notification

                       Log.i(TAG, "发出消息");

                       // messageNotificatioManager.cancel(messageNotificationID-1);//新消息来后,消除之前的一条消息(只显示最新消息)
                       // 配置好下条消息的id号
                       messageNotificationID++;
                   }
                   // 休息10秒钟
                   Thread.sleep(10000);
                   // 获取服务器消息
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }
   }

   /**
    * 模仿服务器发送过来的消息，仅作示例
    * 
    * @return 返回服务器要推送的消息，否则如果为空的话，不推送
    */
   public String getServerMessage() {
       Log.i(TAG, "getmessage");
       return "亲, 测试成功啦~~!";

   }

   @Override
   public void onDestroy() {
       // System.exit(0);

       messageThread.isRunning = false;
       // 或者，二选一，推荐使用System.exit(0)，这样进程退出的更干净
       // messageThread.isRunning = false;
       super.onDestroy();
       Log.i(TAG, "destroy");
   }

}

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
* ��Ϣ����,����serviceʵ�֣���Ҫactivity��������
* 
*/
public class ServiceTest3_service extends Service {

   private String TAG = "-----------";

   private MessageThread messageThread = null;

   // ����鿴
   private Intent messageIntent = null;
   private PendingIntent messagePendingIntent = null;

   // ֪ͨ����Ϣ
   private int messageNotificationID = 1000;
   private Notification messageNotification = null;                              // �Ǿ����״̬��֪ͨ���󣬿�������icon�����֡���ʾ�������񶯵ȵȲ�����
   private NotificationManager messageNotificatioManager = null; // ��״̬��֪ͨ�Ĺ����࣬����֪ͨ�����֪ͨ�ȡ� 
   private RemoteViews contentView = null;

   @Override
   public IBinder onBind(Intent intent) {
       // TODO Auto-generated method stub
       return null;
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
       // ��ʼ��
       // messageNotification = new
       // Notification(R.drawable.icon,"����Ϣ11",System.currentTimeMillis());//*����Ϣ�汾���*�˰汾��ʹ��
       messageNotification = new Notification();
       messageNotification.icon = R.drawable.ic_launcher;// ״̬����ʾͼ��
       messageNotification.tickerText = "�ٺ٣�������Ϣ����";// ״̬����ʾ��Ϣ

       contentView = new RemoteViews(getPackageName(), R.layout.servicetest3);// ��Ϣ��������
       contentView.setImageViewResource(R.id.imageView3, R.drawable.ic_launcher);// ��Ϣ���������ͼ��

       messageNotification.contentView = contentView;// ����Ϣ��������Ϣ��

       // messageNotification.icon = R.drawable.icon;//*����Ϣ�汾���*�˰汾��ʹ��
       // messageNotification.tickerText = "����Ϣ11";//*����Ϣ�汾���*�˰汾��ʹ��
       // messageNotification.when=System.currentTimeMillis();
       // //*����Ϣ�汾���*�˰汾��ʹ��

       // messageNotification.defaults |= Notification.DEFAULT_SOUND;//����
       // messageNotification.defaults |= Notification.DEFAULT_LIGHTS;//��
       // messageNotification.defaults |= Notification.DEFAULT_VIBRATE;//��

       // messageNotification.sound = Uri.parse("file:///sdcard/to.mp3");
       messageNotification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "1");// ѡ�����嵥�ĵ�n�׸�����Ϣ����
       // messageNotification.ledARGB = 0xff00ff00;//�Ƶ���ɫ
       // messageNotification.ledOnMS = 300; //����ʱ��
       // messageNotification.ledOffMS = 1000; //���ʱ��
       // messageNotification.flags |= Notification.FLAG_SHOW_LIGHTS;//��ʾ��

       // long v[]= {0,100,200,300}; //��Ƶ��
       // messageNotification.vibrate = v;
       //

       messageNotification.flags |= Notification.FLAG_AUTO_CANCEL;// �����Ϣ��,����Ϣ�Զ��˳�
       messageNotification.flags |= Notification.FLAG_ONGOING_EVENT;// ���Ϸ�������Ϣ���г���
       // messageNotification.flags|=Notification.FLAG_NO_CLEAR;//����Ϣ���ᱻ���

       messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       messageIntent = new Intent(this, ShowMessage.class);// �����Ϣ��,Ҫ��ת�Ľ���  ( ��Ӧ ��ϸ��Ϣ�Ľ��� )

       // �����߳�
       messageThread = new MessageThread();// ���߳�ÿ10��,����һ����Ϣ����
       messageThread.isRunning = true;// ����Ϊfalse��,�߳�����ѭ����������
       messageThread.start();
       Log.i(TAG, "startCommand");
       return super.onStartCommand(intent, flags, startId);
   }

   /**
    * �ӷ������˻�ȡ��Ϣ
    */
   class MessageThread extends Thread {
       // ����Ϊfalse��,�߳�����ѭ��������
       public boolean isRunning = true;

       public void run() {
           while (isRunning) {
               try {

                   String serverMessage = getServerMessage();
                   
                   if (serverMessage != null && !"".equals(serverMessage)) {
                       // ����֪ͨ��
                       // messageNotification.setLatestEventInfo(MessageService.this,"����Ϣ","��~��    ����ϢҮ!"+serverMessage,messagePendingIntent);//*����Ϣ�汾���*�˰汾��ʹ��

                       contentView.setTextViewText(R.id.textView1, serverMessage);// ������Ϣ����

                       messageIntent.putExtra("message", serverMessage);// Ϊ��ͼ��Ӳ��������ݸ�ShowMessage
                       messagePendingIntent = PendingIntent.getActivity(ServiceTest3_service.this, 0, messageIntent,
                               PendingIntent.FLAG_CANCEL_CURRENT);// ����ͼװ�� �ӳ���ͼ
                       messageNotification.contentIntent = messagePendingIntent;// ���ӳ���ͼװ����Ϣ
                       messageNotificatioManager.notify(messageNotificationID, messageNotification);// ����Notification

                       Log.i(TAG, "������Ϣ");

                       // messageNotificatioManager.cancel(messageNotificationID-1);//����Ϣ����,����֮ǰ��һ����Ϣ(ֻ��ʾ������Ϣ)
                       // ���ú�������Ϣ��id��
                       messageNotificationID++;
                   }
                   // ��Ϣ10����
                   Thread.sleep(10000);
                   // ��ȡ��������Ϣ
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }
   }

   /**
    * ģ�·��������͹�������Ϣ������ʾ��
    * 
    * @return ���ط�����Ҫ���͵���Ϣ���������Ϊ�յĻ���������
    */
   public String getServerMessage() {
       Log.i(TAG, "getmessage");
       return "��, ���Գɹ���~~!";

   }

   @Override
   public void onDestroy() {
       // System.exit(0);

       messageThread.isRunning = false;
       // ���ߣ���ѡһ���Ƽ�ʹ��System.exit(0)�����������˳��ĸ��ɾ�
       // messageThread.isRunning = false;
       super.onDestroy();
       Log.i(TAG, "destroy");
   }

}

package com.android.media;   
import java.io.IOException;   

import com.android.ActivityManage;
import com.android.R;

import android.app.Activity;   
import android.media.MediaPlayer;   
import android.media.MediaPlayer.OnCompletionListener;   
import android.media.MediaPlayer.OnErrorListener;   
import android.media.MediaPlayer.OnInfoListener;   
import android.media.MediaPlayer.OnPreparedListener;   
import android.media.MediaPlayer.OnSeekCompleteListener;   
import android.media.MediaPlayer.OnVideoSizeChangedListener;   
import android.os.Bundle;   
import android.os.Environment;   
import android.util.Log;   
import android.view.Display;   
import android.view.SurfaceHolder;   
import android.view.SurfaceView;   
import android.widget.LinearLayout;   
/**  
 * ��ʵ����ʹ��MediaPlayer��ɲ��ţ�ͬʱ����ʹ��SurfaceView��ʵ��  
 *   
 * ��������ʵ��MediaPlayer�кܶ�״̬�仯ʱ�ļ�����  
 *   
 * ʹ��Mediaplayerʱ��Ҳ����ʹ��MediaController�࣬������Ҫʵ��MediaController.mediaController�ӿ�  
 * ʵ��һЩ���Ʒ�����  
 *   
 * Ȼ������controller.setMediaPlayer(),setAnchorView(),setEnabled(),show()�Ϳ����ˣ����ﲻ��ʵ��  
 * @author Administrator  
 *  
 */  
public class VideoPlay2 extends Activity implements OnCompletionListener,OnErrorListener,OnInfoListener,   
    OnPreparedListener, OnSeekCompleteListener,OnVideoSizeChangedListener,SurfaceHolder.Callback{   
    private Display currDisplay;   
    private SurfaceView surfaceView;   
    private SurfaceHolder holder;   
    private MediaPlayer player;   
    private int vWidth,vHeight;   
    //private boolean readyToPlay = false;   
            
    public void onCreate(Bundle savedInstanceState){   
        super.onCreate(savedInstanceState);   
        
        ActivityManage.getInstance().addActivity(this);
        
        this.setContentView(R.layout.videoplay2);   
                    
        surfaceView = (SurfaceView)this.findViewById(R.id.surface);   
        //��SurfaceView���CallBack����   
        holder = surfaceView.getHolder();   
        holder.addCallback(this);   
        //Ϊ�˿��Բ�����Ƶ����ʹ��CameraԤ����������Ҫָ����Buffer����   
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);   
            
        //���濪ʼʵ����MediaPlayer����   
        player = new MediaPlayer();   
        player.setOnCompletionListener(this);   
        player.setOnErrorListener(this);   
        player.setOnInfoListener(this);   
        player.setOnPreparedListener(this);   
        player.setOnSeekCompleteListener(this);   
        player.setOnVideoSizeChangedListener(this);   
        Log.i("Begin:::", "surfaceDestroyed called");   
        //Ȼ��ָ����Ҫ�����ļ���·������ʼ��MediaPlayer   
        String dataPath = VideoPlay1.path;   
        try {   
            player.setDataSource(dataPath);   
            Log.i("Next:::", "surfaceDestroyed called");   
        } catch (IllegalArgumentException e) {   
            e.printStackTrace();   
        } catch (IllegalStateException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
        //Ȼ������ȡ�õ�ǰDisplay����   
        currDisplay = this.getWindowManager().getDefaultDisplay();   
    }   
        
    @Override  
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {   
        // ��Surface�ߴ�Ȳ����ı�ʱ����   
        Log.i("Surface Change:::", "surfaceChanged called");   
    }   
    @Override  
    public void surfaceCreated(SurfaceHolder holder) {   
        // ��SurfaceView�е�Surface��������ʱ�򱻵���   
        //����������ָ��MediaPlayer�ڵ�ǰ��Surface�н��в���   
        player.setDisplay(holder);   
        //��ָ����MediaPlayer���ŵ����������ǾͿ���ʹ��prepare����prepareAsync��׼��������   
        player.prepareAsync();   
            
    }   
    @Override  
    public void surfaceDestroyed(SurfaceHolder holder) {   
            
        Log.i("Surface Destory:::", "surfaceDestroyed called");   
    }   
    @Override  
    public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {   
        // ��video��С�ı�ʱ����   
        //�������������player��source�����ٴ���һ��   
        Log.i("Video Size Change", "onVideoSizeChanged called");   
            
    }   
    @Override  
    public void onSeekComplete(MediaPlayer arg0) {   
        // seek�������ʱ����   
        Log.i("Seek Completion", "onSeekComplete called");   
            
    }   
    @Override  
    public void onPrepared(MediaPlayer player) {   
        // ��prepare��ɺ󣬸÷������������������ǲ�����Ƶ   
            
        //����ȡ��video�Ŀ�͸�   
        vWidth = player.getVideoWidth();   
        vHeight = player.getVideoHeight();   
            
        if(vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()){   
            //���video�Ŀ���߸߳����˵�ǰ��Ļ�Ĵ�С����Ҫ��������   
            float wRatio = (float)vWidth/(float)currDisplay.getWidth();   
            float hRatio = (float)vHeight/(float)currDisplay.getHeight();   
                
            //ѡ����һ����������   
            float ratio = Math.max(wRatio, hRatio);   
                
            vWidth = (int)Math.ceil((float)vWidth/ratio);   
            vHeight = (int)Math.ceil((float)vHeight/ratio);   
                
            //����surfaceView�Ĳ��ֲ���   
            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(vWidth, vHeight));   
                
            //Ȼ��ʼ������Ƶ   
                
            player.start();   
        }   
    }   
    @Override  
    public boolean onInfo(MediaPlayer player, int whatInfo, int extra) {   
        // ��һЩ�ض���Ϣ���ֻ��߾���ʱ����   
        switch(whatInfo){   
        case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:   
            break;   
        case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:     
            break;   
        case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:   
            break;   
        case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:    
            break;   
        }   
        return false;   
    }   
    @Override  
    public boolean onError(MediaPlayer player, int whatError, int extra) {   
        Log.i("Play Error:::", "onError called");   
        switch (whatError) {   
        case MediaPlayer.MEDIA_ERROR_SERVER_DIED:   
            Log.i("Play Error:::", "MEDIA_ERROR_SERVER_DIED");   
            break;   
        case MediaPlayer.MEDIA_ERROR_UNKNOWN:   
            Log.i("Play Error:::", "MEDIA_ERROR_UNKNOWN");   
            break;   
        default:   
            break;   
        }   
        return false;   
    }   
    @Override  
    public void onCompletion(MediaPlayer player) {   
        // ��MediaPlayer������ɺ󴥷�   
        Log.i("Play Over:::", "onComletion called");   
        this.finish();   
    }   
}
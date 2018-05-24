package com.android.viewpager;

import java.util.ArrayList;
import java.util.List;

import com.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ViewPager2 extends Activity { 
  
    private static final String TAG = "test"; 
    private ViewPager viewpager = null; 
    private List<View> list = null; 
    private ImageView[] img = null; 
  
    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.viewpager2); 
        viewpager = (ViewPager) findViewById(R.id.viewpager); 
        list = new ArrayList<View>(); 
        list.add(getLayoutInflater().inflate(R.layout.listview_test1, null)); 
        list.add(getLayoutInflater().inflate(R.layout.baidugpstest1, null)); 
        list.add(getLayoutInflater().inflate(R.layout.edittext_test1, null)); 
  
        img = new ImageView[list.size()]; 
        LinearLayout layout = (LinearLayout) findViewById(R.id.viewGroup); 
        for (int i = 0; i < list.size(); i++) { 
            img[i] = new ImageView(ViewPager2.this); 
            if (0 == i) { 
                img[i].setBackgroundResource(R.drawable.dot_selected); 
            } else { 
                img[i].setBackgroundResource(R.drawable.dot_unselected); 
            } 
            img[i].setPadding(0, 0, 20, 0); 
            layout.addView(img[i]); 
        } 
        viewpager.setAdapter(new ViewPagerAdapter(list)); 
        viewpager.setOnPageChangeListener(new ViewPagerPageChangeListener()); 
    } 
  
    class ViewPagerAdapter extends PagerAdapter { 
  
        private List<View> list = null; 
  
        public ViewPagerAdapter(List<View> list) { 
            this.list = list; 
        } 
  
        @Override
        public int getCount() { 
            return list.size(); 
        } 
  
        @Override
        public Object instantiateItem(ViewGroup container, int position) { 
            container.addView(list.get(position)); 
            return list.get(position); 
        } 
  
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) { 
            container.removeView(list.get(position)); 
        } 
  
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) { 
            return arg0 == arg1; 
        } 
  
    } 
    
    class ViewPagerPageChangeListener implements OnPageChangeListener { 
    	  
        /* 
         * state������ͨ��˵����1��ʱ���ʾ���ڻ�����2��ʱ���ʾ��������ˣ�0��ʱ���ʾʲô��û��������ͣ���ǣ� 
         * �ҵ���Ϊ��1�ǰ���ʱ��0���ɿ���2�����µı�ǩҳ���Ƿ񻬶��� 
         * (���磺��ǰҳ�ǵ�һҳ����������һ������ӡ��2���������ֱ�������˵ڶ�ҳ����ô�ͻ��ӡ��2��)�� 
         * ������Ϊһ��������ǲ�����д��������� 
         */
        @Override
        public void onPageScrollStateChanged(int state) { 
        } 
  
        /* 
         * page�������ƾͿ��ó�����ǰҳ�� positionOffset��λ��ƫ��������Χ[0,1]�� 
         * positionoffsetPixels��λ�����أ���Χ[0,��Ļ���)�� ������Ϊһ��������ǲ�����д��������� 
         */
        @Override
        public void onPageScrolled(int page, float positionOffset, 
                int positionOffsetPixels) { 
        } 
  
        @Override
        public void onPageSelected(int page) { 
            //����ͼ��             
        	for (int i = 0; i < list.size(); i++) { 
                if (page == i) { 
                    img[i].setBackgroundResource(R.drawable.dot_selected); 
                } else { 
                    img[i].setBackgroundResource(R.drawable.dot_unselected); 
                } 
            } 
        } 
    }
    }
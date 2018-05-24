package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.ActivityManage;
import com.android.R;
import com.utils.CommonUtil;
import com.utils.ExceptionDetail;
import com.utils.JdbcDao;
import com.utils.Level3Linkage;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Level3LinkageTest extends Activity {
	
	private Level3Linkage level3Linkage=new Level3Linkage();

	private static TextView message_txt;
	
	private static List<Map<String,String>> base_area;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level3linkage_main);
		
		ActivityManage.getInstance().addActivity(this);

		message_txt = (TextView) this.findViewById(R.id.message_txt);

		Button clickbtn = (Button) this.findViewById(R.id.clickbtn);
		clickbtn.setOnClickListener(listener);
		if(base_area==null){
			//1
//			base_area=null;
			//2
//			base_area=base_area();
			//3
			base_area=test();
		}
	}
	
	private List<Map<String,String>> base_area(){
		List<Map<String,String>> l_area=new ArrayList<Map<String,String>>();
		try {
			JdbcDao jdbcDao=new JdbcDao();
			List<Map> l=jdbcDao.query("select * From base_area where len(preidstr)-len(replace(preidstr,'.',''))>=4 order by len(preidstr)-len(replace(preidstr,'.',''))", null);
			if(l.size()>0){
				List<Map> l_province=new ArrayList<Map>();
				List<Map> l_city=new ArrayList<Map>();
				for(int i=0;i<l.size();i++){
					Map m=l.get(i);
					String[] PreIDStr=((String)m.get("PreIDStr")).replace(".", ";").split(";");
					String ID=String.valueOf(CommonUtil.objectToLong(m.get("ID")));
					String AreaName=(String) m.get("AreaName");
					switch(PreIDStr.length){
					case 4:{
						l_province.add(m);
						loadData(l_area,ID,"",AreaName+"全部",AreaName);
						break;
					}
					case 5:{
						l_city.add(m);
						//.1.2.2149.2150.
						String id_province=PreIDStr[4];
						String name_province=getAreaName(l_province,id_province);
//						if(l_province.size()>0){
//							for(int j=0;j<l_province.size();j++){
//								Map m_province=l_province.get(j);
//								if(id_province.equals(String.valueOf(CommonUtil.objectToLong(m_province.get("ID"))))){
//									name_province=(String) m_province.get("AreaName");
//									break;
//								}
//							}
//						}
						
						loadData(l_area,ID,"",name_province+AreaName,name_province);
						break;
					}
					case 7:{
						//.1.2.2479.2479.3000.3036.  倒数第1个是市，倒数第2个是省
						String id_city=PreIDStr[6];
						String name_city=getAreaName(l_city, id_city);
//						if(l_city.size()>0){
//							for(int j=0;j<l_city.size();j++){
//								Map m_city=l_city.get(j);
//								if(id_city.equals(String.valueOf(CommonUtil.objectToLong(m_city.get("ID"))))){
//									name_city=(String) m_city.get("AreaName");
//									break;
//								}
//							}
//						}
						String id_province=PreIDStr[5];
						String name_province=getAreaName(l_province, id_province);
//						if(l_province.size()>0){
//							for(int j=0;j<l_province.size();j++){
//								Map m_province=l_province.get(j);
//								if(id_province.equals(String.valueOf(CommonUtil.objectToLong(m_province.get("ID"))))){
//									name_province=(String) m_province.get("AreaName");
//									break;
//								}
//							}
//						}
						//id,name_level3,name_level2,name_level1
//						Map<String,String> m_county=new HashMap<String,String>();
//						m_county.put("id", ID);
//						m_county.put("name_level3", AreaName);
//						m_county.put("name_level2", name_city);
//						m_county.put("name_level1", name_province);
//						l_area.add(m_county);
						
						loadData(l_area, ID, AreaName, name_province+name_city, name_province);
						break;
					}
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println(ExceptionDetail.getErrorMessage(e));
		}
		return l_area;
	}
	
	private String getAreaName(List<Map> l,String id){
		String AreaName="";
		if(l.size()>0){
			for(int j=0;j<l.size();j++){
				Map m=l.get(j);
				if(id.equals(String.valueOf(CommonUtil.objectToLong(m.get("ID"))))){
					AreaName=(String) m.get("AreaName");
					break;
				}
			}
		}
		return AreaName;
	}
	
	private List<Map<String,String>> test(){
		List<Map<String,String>> l_area=new ArrayList<Map<String,String>>();
//		Map<String,String> m_county=new HashMap<String,String>();
//		Map<String,String> m_city=new HashMap<String,String>();
//		Map<String,String> m_province=new HashMap<String,String>();
//		m_county.put("id", ID);
//		m_county.put("name_level3", AreaName);
//		m_county.put("name_level2", name_city);
//		m_county.put("name_level1", name_province);
		
//		loadData(l_area,"1","","","广西");
		loadData(l_area,"2","","广东全部","广东");
		
		loadData(l_area,"1","","广西全部","广西");
		loadData(l_area,"11","","南宁","广西");
		loadData(l_area,"111","西乡塘区","南宁","广西");
		return l_area;
	}
	
	private void loadData(List<Map<String,String>> l_area,String id,String name_level3,String name_level2,String name_level1){
		Map<String,String> m=new HashMap<String,String>();
		m.put("id", id);
		m.put("name_level3", name_level3);
		m.put("name_level2", name_level2);
		m.put("name_level1", name_level1);
		l_area.add(m);
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.clickbtn:
				level3Linkage.create(Level3LinkageTest.this,h_leve3Linkage,base_area);
				break;
			}
		}
	};

	private Handler h_leve3Linkage = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				message_txt.setText(msg.obj + "");
				break;
			}
		}
	};

	
}

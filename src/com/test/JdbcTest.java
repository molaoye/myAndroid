package com.test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.android.ActivityManage;
import com.android.R;
import com.utils.DaoException;
import com.utils.ExceptionDetail;
import com.utils.JdbcConn;
import com.utils.JdbcDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class JdbcTest extends Activity implements OnClickListener{

	TextView textView_testJdbcConn;
	TextView textView_testQuery;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActivityManage.getInstance().addActivity(this);
		
		setContentView(R.layout.jdbctest1);
		
		Button button_testJdbcConn=(Button) findViewById(R.id.button_testJdbcConn);
		button_testJdbcConn.setOnClickListener(this);
		Button button_testQuery=(Button) findViewById(R.id.button_testQuery);
		button_testQuery.setOnClickListener(this);
		textView_testJdbcConn=(TextView) findViewById(R.id.textView_testJdbcConn);
		textView_testQuery=(TextView) findViewById(R.id.textView_testQuery);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button_testJdbcConn:{
			if(JdbcConn.testConnection()){
				textView_testJdbcConn.setText("³É¹¦");
			}else{
				textView_testJdbcConn.setText("Ê§°Ü");
			}
			break;
		}
		case R.id.button_testQuery:{
			try {
				JdbcDao jdbcDao=new JdbcDao();
				List<Map> l=jdbcDao.query("select * From base_area where len(preidstr)-len(replace(preidstr,'.',''))>=4 order by len(preidstr)-len(replace(preidstr,'.',''))", null);
				if(l.size()>0){
					String s="";
//					for(int i=0;i<l.size();i++){
						Map<String,Object> m=l.get(0);
						for(Entry<String,Object> e:m.entrySet()){
							s+=e.getKey()+":"+e.getValue()+"\n";
						}
//					}
					textView_testQuery.setText(s);
				}
			} catch (DaoException e) {
				System.out.println(ExceptionDetail.getErrorMessage(e));
			}
			break;
		}
		
		}
	}

}

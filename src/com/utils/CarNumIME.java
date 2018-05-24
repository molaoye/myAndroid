package com.utils;

import com.android.R;
import com.utils.AlertDialogBuilder.CommonDialog1;
import com.utils.AlertDialogBuilder.CustomAlertDialog1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarNumIME extends Activity implements OnClickListener{

	private View View_layout;
	private LinearLayout LinearLayout_include;
	private LinearLayout LinearLayout_letters;
	private LinearLayout LinearLayout_numbers;
	private LinearLayout LinearLayout_provinces1;
	private LinearLayout LinearLayout_provinces2;
	private LinearLayout LinearLayout_provinces3;
	private TextView TextView_carNum;
	
	private CommonDialog1 commonDialog;
	private AlertDialog alertDialog;
	private CustomAlertDialog1 customAlertDialog;
	
	private int province_maxPage=3;
	private int province_minPage=1;
	/**
	 * 顺序不能反
	 */
	private int[] province_pages={1,2,3};
	private int province_currentPage=0;
	
	private Button Button_province;
	private Button Button_letters;
	private Button Button_numbers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
//		setContentView(R.layout.carnumime2);
//		setContentView(R.layout.carnumime3);//自适应屏幕
		setContentView(R.layout.carnumime4);//省名、abc、123按下效果凸显
		
//		View_layout=(View) findViewById(R.id.View_layout);
////		View_layout.set
//		View_layout.inflate(this, R.layout.carnumime_provinces, null);
		
		LinearLayout_include=(LinearLayout) findViewById(R.id.LinearLayout_include);
//		View_layout=LinearLayout_layout.inflate(this, R.layout.carnumime_provinces, null);
////		View_layout.set
		provinces1(R.layout.carnumime_provinces1);
		province_currentPage=province_pages[0];
		
		//1
//		LinearLayout_letters=(LinearLayout) findViewById(R.id.LinearLayout_letters);
//		LinearLayout_numbers=(LinearLayout) findViewById(R.id.LinearLayout_numbers);
//		LinearLayout_provinces1=(LinearLayout) findViewById(R.id.LinearLayout_provinces1);
//		LinearLayout_provinces2=(LinearLayout) findViewById(R.id.LinearLayout_provinces2);
//		LinearLayout_provinces3=(LinearLayout) findViewById(R.id.LinearLayout_provinces3);
		//2
		
		
		Button_province=(Button) findViewById(R.id.Button_province);
		Button_province.setOnClickListener(this);
		Button_letters=(Button) findViewById(R.id.Button_letters);
		Button_letters.setOnClickListener(this);
		Button_numbers=(Button) findViewById(R.id.Button_numbers);
		Button_numbers.setOnClickListener(this);
		
		findViewById(R.id.Button_province_next).setOnClickListener(this);
		findViewById(R.id.Button_province_last).setOnClickListener(this);
		findViewById(R.id.Button_province_previous).setOnClickListener(this);
		findViewById(R.id.Button_province_first).setOnClickListener(this);
		findViewById(R.id.Button_del).setOnClickListener(this);
		findViewById(R.id.Button_close).setOnClickListener(this);
		findViewById(R.id.button_return).setOnClickListener(this);
		findViewById(R.id.button_confirm).setOnClickListener(this);
		TextView_carNum=(TextView) findViewById(R.id.TextView_carNum);
		
	}
	
	private void numbers(){
		Button Button_number_0=(Button) findViewById(R.id.Button_number_0);
		Button_number_0.setOnClickListener(onClick_common);
		Button Button_number_1=(Button) findViewById(R.id.Button_number_1);
		Button_number_1.setOnClickListener(onClick_common);
		Button Button_number_2=(Button) findViewById(R.id.Button_number_2);
		Button_number_2.setOnClickListener(onClick_common);
		Button Button_number_3=(Button) findViewById(R.id.Button_number_3);
		Button_number_3.setOnClickListener(onClick_common);
		Button Button_number_4=(Button) findViewById(R.id.Button_number_4);
		Button_number_4.setOnClickListener(onClick_common);
		Button Button_number_5=(Button) findViewById(R.id.Button_number_5);
		Button_number_5.setOnClickListener(onClick_common);
		Button Button_number_6=(Button) findViewById(R.id.Button_number_6);
		Button_number_6.setOnClickListener(onClick_common);
		Button Button_number_7=(Button) findViewById(R.id.Button_number_7);
		Button_number_7.setOnClickListener(onClick_common);
		Button Button_number_8=(Button) findViewById(R.id.Button_number_8);
		Button_number_8.setOnClickListener(onClick_common);
		Button Button_number_9=(Button) findViewById(R.id.Button_number_9);
		Button_number_9.setOnClickListener(onClick_common);
	}
	
	private void numbers(int layoutId){
		LinearLayout_include_AddView(layoutId);
		numbers();
	}
	
	private void letters(){
		Button Button_letter_ABC=(Button) findViewById(R.id.Button_letter_ABC);
		Button_letter_ABC.setOnClickListener(onClick_common);
		Button Button_letter_DEF=(Button) findViewById(R.id.Button_letter_DEF);
		Button_letter_DEF.setOnClickListener(onClick_common);
		Button Button_letter_GHI=(Button) findViewById(R.id.Button_letter_GHI);
		Button_letter_GHI.setOnClickListener(onClick_common);
		Button Button_letter_JKL=(Button) findViewById(R.id.Button_letter_JKL);
		Button_letter_JKL.setOnClickListener(onClick_common);
		Button Button_letter_MNO=(Button) findViewById(R.id.Button_letter_MNO);
		Button_letter_MNO.setOnClickListener(onClick_common);
		Button Button_letter_PQR=(Button) findViewById(R.id.Button_letter_PQR);
		Button_letter_PQR.setOnClickListener(onClick_common);
		Button Button_letter_STU=(Button) findViewById(R.id.Button_letter_STU);
		Button_letter_STU.setOnClickListener(onClick_common);
		Button Button_letter_VW=(Button) findViewById(R.id.Button_letter_VW);
		Button_letter_VW.setOnClickListener(onClick_common);
		Button Button_letter_XYZ=(Button) findViewById(R.id.Button_letter_XYZ);
		Button_letter_XYZ.setOnClickListener(onClick_common);
	}
	
	private void letters(int layoutId){
		LinearLayout_include_AddView(layoutId);
		letters();
	}
	
	private void provinces1(){
		((Button) findViewById(R.id.Button_province1_AnHui)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_ChongQing)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_FuJian)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_GuangDong)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_GuangXi)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_GuiZhou)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_HuNan)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_JiangXi)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_SiChuan)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_SuZhou)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_YunNan)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province1_ZheJiang)).setOnClickListener(onClick_common);
	}
	
	private void provinces1(int layoutId){
		LinearLayout_include_AddView(layoutId);
		provinces1();
	}
	
	private void provinces2(){
		((Button) findViewById(R.id.Button_province2_BeiJing)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_HeBei)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_HeiLongJiang)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_HeNan)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_HuBei)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_JiLin)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_LiaoNing)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_NeiMengGu)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_ShanDong)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_ShangHai)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_ShanXi)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province2_TianJin)).setOnClickListener(onClick_common);
	}
	
	private void provinces2(int layoutId){
		LinearLayout_include_AddView(layoutId);
		provinces2();
	}
	
	private void provinces3(){
		((Button) findViewById(R.id.Button_province3_AoMen)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province3_GanSu)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province3_HaiNan)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province3_NingXia)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province3_QingHai)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province3_ShanXi)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province3_TaiWan)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province3_XiangGang)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province3_XinJiang)).setOnClickListener(onClick_common);
		((Button) findViewById(R.id.Button_province3_XiZang)).setOnClickListener(onClick_common);
	}
	
	private void provinces3(int layoutId){
		LinearLayout_include_AddView(layoutId);
		provinces3();
	}
	
	private void onClick_numbers(int id){
		String text="";
		switch (id) {
		case R.id.Button_number_0:{
			text="0";
			break;
		}
		case R.id.Button_number_1:{
			text="1";
			break;
		}
		case R.id.Button_number_2:{
			text="2";
			break;
		}
		case R.id.Button_number_3:{
			text="3";
			break;
		}
		case R.id.Button_number_4:{
			text="4";
			break;
		}
		case R.id.Button_number_5:{
			text="5";
			break;
		}
		case R.id.Button_number_6:{
			text="6";
			break;
		}
		case R.id.Button_number_7:{
			text="7";
			break;
		}
		case R.id.Button_number_8:{
			text="8";
			break;
		}
		case R.id.Button_number_9:{
			text="9";
			break;
		}
		}
		carNum(text);
	}
	
	private void onClick_provinces(Button button){
		String text="";
		String buttonText=button.getText().toString();
		boolean b_popupChoose=buttonText.contains("/");
		switch (button.getId()) {
		case R.id.Button_province1_AnHui:{
			text=buttonText;
			break;
		}
		case R.id.Button_province1_ChongQing:{
			text="1";
			break;
		}
		}
		carNum(text);
	}
	
	private void popupChoose(String[] buttonTexts){
		View view=LayoutInflater.from(this).inflate(R.layout.carnumime_popup, null);
		//1
//		commonDialog=new AlertDialogBuilder().CommonDialog1(this);
//		alertDialog=commonDialog.setView(view).show(null);
		//2
		customAlertDialog=new AlertDialogBuilder().new CustomAlertDialog1(this,R.style.dialog_test2,view);
		customAlertDialog.setCanceledOnTouchOutside(true);
		customAlertDialog.show();
		
		Button Button_item1=(Button) view.findViewById(R.id.Button_item1);
		try{
			Button_item1.setOnClickListener(onClick_popupChoose);
			Button_item1.setText(buttonTexts[0]);
		}catch(Exception e){
			System.out.println(ExceptionDetail.getErrorMessage(e));
			Button_item1.setVisibility(View.GONE);
		}
		Button Button_item2=(Button) view.findViewById(R.id.Button_item2);
		try{
			Button_item2.setOnClickListener(onClick_popupChoose);
			Button_item2.setText(buttonTexts[1]);
		}catch(Exception e){
			System.out.println(ExceptionDetail.getErrorMessage(e));
			Button_item2.setVisibility(View.GONE);
		}
		Button Button_item3=(Button) view.findViewById(R.id.Button_item3);
		try{
			Button_item3.setOnClickListener(onClick_popupChoose);
			Button_item3.setText(buttonTexts[2]);
		}catch(Exception e){
			System.out.println(ExceptionDetail.getErrorMessage(e));
			Button_item3.setVisibility(View.GONE);
		}
	}
	
	private OnClickListener onClick_popupChoose=new OnClickListener(){
		@Override
		public void onClick(View v) {
			carNum(((Button) v).getText().toString());
//			alertDialog.dismiss();
			customAlertDialog.dismiss();
		}
	};
	
	private OnClickListener onClick_common=new OnClickListener(){
		@Override
		public void onClick(View v) {
			String buttonText=((Button) v).getText().toString();
			if(buttonText.length()==1){
				carNum(buttonText);
			}else{
				String buttonTexts[]={};
				if(buttonText.contains("/")){
					buttonTexts=buttonText.split("/");
				}else{
					buttonTexts=new String[buttonText.length()];
					for(int i=0;i<buttonText.length();i++){
						buttonTexts[i]=String.valueOf(buttonText.charAt(i));
					}
				}
				popupChoose(buttonTexts);
			}
		}
	};
	
	private void onClick_letters(int id){
		switch (id) {
		case R.id.Button_letter_ABC:{
			popupChoose(new String[]{"A","B","C"});
			break;
		}
		case R.id.Button_letter_DEF:{
			popupChoose(new String[]{"D","E","F"});
			break;
		}
		case R.id.Button_letter_GHI:{
			popupChoose(new String[]{"G","H","I"});
			break;
		}
		case R.id.Button_letter_JKL:{
			popupChoose(new String[]{"J","K","L"});
			break;
		}
		case R.id.Button_letter_MNO:{
			popupChoose(new String[]{"M","N","O"});
			break;
		}
		case R.id.Button_letter_PQR:{
			popupChoose(new String[]{"P","Q","R"});
			break;
		}
		case R.id.Button_letter_STU:{
			popupChoose(new String[]{"S","T","U"});
			break;
		}
		case R.id.Button_letter_VW:{
			popupChoose(new String[]{"V","W"});
			break;
		}
		case R.id.Button_letter_XYZ:{
			popupChoose(new String[]{"X","Y","Z"});
			break;
		}
		}
	}
	
	private void carNum(String text){
		if(TextView_carNum.getText().length()<7){
			TextView_carNum.setText(TextView_carNum.getText()+text);
		}
	}
	
	private void LinearLayout_include_AddView(int layoutId){
		View view=LayoutInflater.from(this).inflate(layoutId, null);
		view.setId(layoutId);
		LinearLayout_include.removeAllViews();
		LinearLayout_include.addView(view);
	}

	@Override
	public void onClick(View v) {
		//1
//		boolean b_provinceAvailable=LinearLayout_provinces1.getVisibility()==View.VISIBLE || 
//				LinearLayout_provinces2.getVisibility()==View.VISIBLE || 
//				LinearLayout_provinces3.getVisibility()==View.VISIBLE;
		//2
//		System.out.println(LinearLayout_include.getChildCount());
//		System.out.println(LinearLayout_include.getChildAt(0).getId());
//		System.out.println(LinearLayout_include.getChildAt(0).getId()==R.layout.carnumime_letters);
//		System.out.println(LinearLayout_include.getChildAt(0).getId()==R.layout.carnumime_numbers);
//		System.out.println(LinearLayout_include.getChildAt(0).getId()==R.layout.carnumime_provinces1);
//		System.out.println(LinearLayout_include.getChildAt(0).getId()==R.layout.carnumime_provinces2);
//		System.out.println(LinearLayout_include.getChildAt(0).getId()==R.layout.carnumime_provinces3);
//		System.out.println(LinearLayout_include.getChildAt(0).getId()==R.id.LinearLayout_include);
		int layoutId=LinearLayout_include.getChildAt(0).getId();
		boolean b_provinceAvailable=
				layoutId==R.layout.carnumime_provinces1 || 
				layoutId==R.layout.carnumime_provinces2 || 
				layoutId==R.layout.carnumime_provinces3;
		
		int id=v.getId();
		switch (id) {
		//1
//		case R.id.Button_province:{
//			LinearLayout_letters.setVisibility(View.GONE);
//			LinearLayout_numbers.setVisibility(View.GONE);
//			LinearLayout_provinces1.setVisibility(View.VISIBLE);
//			provinces1();
//			LinearLayout_provinces2.setVisibility(View.GONE);
//			LinearLayout_provinces3.setVisibility(View.GONE);
//			province_currentPage=province_pages[0];
//			break;
//		}
//		case R.id.Button_letters:{
//			LinearLayout_letters.setVisibility(View.VISIBLE);
//			LinearLayout_numbers.setVisibility(View.GONE);
//			LinearLayout_provinces1.setVisibility(View.GONE);
//			LinearLayout_provinces2.setVisibility(View.GONE);
//			LinearLayout_provinces3.setVisibility(View.GONE);
//			letters();
//			break;
//		}
//		case R.id.Button_numbers:{
//			LinearLayout_letters.setVisibility(View.GONE);
//			LinearLayout_numbers.setVisibility(View.VISIBLE);
//			LinearLayout_provinces1.setVisibility(View.GONE);
//			LinearLayout_provinces2.setVisibility(View.GONE);
//			LinearLayout_provinces3.setVisibility(View.GONE);
//			numbers();
//			break;
//		}
//		case R.id.Button_province_next:{
//			if(b_provinceAvailable){
//				if(province_currentPage!=province_maxPage){
//					province_currentPage++;
//					if(province_currentPage==2){
//						LinearLayout_provinces2.setVisibility(View.VISIBLE);
//						provinces2();
//						LinearLayout_provinces1.setVisibility(View.GONE);
//						LinearLayout_provinces3.setVisibility(View.GONE);
//					}else if(province_currentPage==3){
//						LinearLayout_provinces3.setVisibility(View.VISIBLE);
//						provinces3();
//						LinearLayout_provinces1.setVisibility(View.GONE);
//						LinearLayout_provinces2.setVisibility(View.GONE);
//					}
//				}
//			}
//			break;
//		}
//		case R.id.Button_province_previous:{
//			if(b_provinceAvailable){
//				if(province_currentPage!=province_minPage){
//					province_currentPage--;
//					if(province_currentPage==2){
//						LinearLayout_provinces2.setVisibility(View.VISIBLE);
//						provinces2();
//						LinearLayout_provinces1.setVisibility(View.GONE);
//						LinearLayout_provinces3.setVisibility(View.GONE);
//					}else if(province_currentPage==1){
//						LinearLayout_provinces1.setVisibility(View.VISIBLE);
//						provinces1();
//						LinearLayout_provinces3.setVisibility(View.GONE);
//						LinearLayout_provinces2.setVisibility(View.GONE);
//					}
//				}
//			}
//			break;
//		}
//		case R.id.Button_province_last:{
//			if(b_provinceAvailable){
//				province_currentPage=province_maxPage;
//				LinearLayout_provinces3.setVisibility(View.VISIBLE);
//				provinces3();
//				LinearLayout_provinces1.setVisibility(View.GONE);
//				LinearLayout_provinces2.setVisibility(View.GONE);
//			}
//			break;
//		}
//		case R.id.Button_province_first:{
//			if(b_provinceAvailable){
//				province_currentPage=province_minPage;
//				LinearLayout_provinces1.setVisibility(View.VISIBLE);
//				provinces1();
//				LinearLayout_provinces3.setVisibility(View.GONE);
//				LinearLayout_provinces2.setVisibility(View.GONE);
//			}
//			break;
//		}
		//2
		case R.id.Button_province:{
			Button_province.setBackgroundResource(R.drawable.right_hover_bg);
			Button_province.setTextColor(getResources().getColor(R.color.white));
			Button_letters.setBackgroundResource(R.drawable.right_bg);
			Button_letters.setTextColor(getResources().getColor(R.color.darkgrey));
			Button_numbers.setBackgroundResource(R.drawable.right_bg);
			Button_numbers.setTextColor(getResources().getColor(R.color.darkgrey));
			
			provinces1(R.layout.carnumime_provinces1);
			province_currentPage=province_pages[0];
			break;
		}
		case R.id.Button_letters:{
			Button_letters.setBackgroundResource(R.drawable.right_hover_bg);
			Button_letters.setTextColor(getResources().getColor(R.color.white));
			Button_province.setBackgroundResource(R.drawable.right_bg);
			Button_province.setTextColor(getResources().getColor(R.color.darkgrey));
			Button_numbers.setBackgroundResource(R.drawable.right_bg);
			Button_numbers.setTextColor(getResources().getColor(R.color.darkgrey));
			
			letters(R.layout.carnumime_letters);
			break;
		}
		case R.id.Button_numbers:{
			Button_numbers.setBackgroundResource(R.drawable.right_hover_bg);
			Button_numbers.setTextColor(getResources().getColor(R.color.white));
			Button_letters.setBackgroundResource(R.drawable.right_bg);
			Button_letters.setTextColor(getResources().getColor(R.color.darkgrey));
			Button_province.setBackgroundResource(R.drawable.right_bg);
			Button_province.setTextColor(getResources().getColor(R.color.darkgrey));
			
			numbers(R.layout.carnumime_numbers);
			break;
		}
		case R.id.Button_province_next:{
			if(b_provinceAvailable){
				if(province_currentPage!=province_maxPage){
					province_currentPage++;
					if(province_currentPage==2){
						provinces2(R.layout.carnumime_provinces2);
					}else if(province_currentPage==3){
						provinces3(R.layout.carnumime_provinces3);
					}
				}
			}
			break;
		}
		case R.id.Button_province_previous:{
			if(b_provinceAvailable){
				if(province_currentPage!=province_minPage){
					province_currentPage--;
					if(province_currentPage==2){
						provinces2(R.layout.carnumime_provinces2);
					}else if(province_currentPage==1){
						provinces1(R.layout.carnumime_provinces1);
					}
				}
			}
			break;
		}
		case R.id.Button_province_last:{
			if(b_provinceAvailable){
				province_currentPage=province_maxPage;
				provinces3(R.layout.carnumime_provinces3);
			}
			break;
		}
		case R.id.Button_province_first:{
			if(b_provinceAvailable){
				province_currentPage=province_minPage;
				provinces1(R.layout.carnumime_provinces1);
			}
			break;
		}
			
		case R.id.Button_del:{
			try{
				TextView_carNum.setText(TextView_carNum.getText().subSequence(0, TextView_carNum.getText().length()-1));
			}catch(Exception e){
				System.out.println(ExceptionDetail.getErrorMessage(e));
			}
			break;
		}
		case R.id.Button_close:{
			finish();
			break;
		}
		case R.id.button_return:{
			finish();
			break;
		}
		case R.id.button_confirm:{
			if(TextView_carNum.getText().length()!=7){
				CommonUtil.ToastMsg(this, "车牌号位数不对！", Gravity.CENTER);
			}else{
				//回传结果
				Intent intent=new Intent();//getIntent();
				intent.putExtra("carNum", TextView_carNum.getText());
				setResult(RESULT_OK, intent);
				finish();
			}
			break;
		}
		}
//		onClick_numbers(id);
//		onClick_letters(id);
	}
	
	
	

}

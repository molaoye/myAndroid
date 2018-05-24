package com.utils;

import java.util.ArrayList;

import com.android.R;

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

public class Level3Linkage2 extends Activity {
	/** 微软雅黑默认 */
	private static Typeface typefacenormal = Typeface.create("微软雅黑",
			Typeface.NORMAL);
	/** 微软雅黑粗体 */
	private static Typeface typefacebold = Typeface.create("微软雅黑",
			Typeface.BOLD);

	private AlertDialog alertDialog;
	private String selcityid = "";
	private ArrayList<WeatherAreaModel> weatherAreaModels;
	private WeatherAreaModel nowWeatherAreaModel;
	private Spinner add_province_box;
	private ArrayList<WeatherAreaModel> provinceList = new ArrayList<WeatherAreaModel>();
	private ArrayAdapter<String> provinceAdapter;
	private Spinner add_city_box;
	private ArrayList<WeatherAreaModel> cityList = new ArrayList<WeatherAreaModel>();
	private ArrayAdapter<String> cityAdapter;
	private Spinner add_street_box;
	private ArrayList<WeatherAreaModel> streetList = new ArrayList<WeatherAreaModel>();
	private ArrayAdapter<String> streetAdapter;
	private Button add_address_close;
	private LinearLayout add_address_ok;
	private LinearLayout add_address_cancle;

	private TextView message_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level3linkage_main);
		weatherAreaModels = new WeatherAreaList().GetList();
		selcityid = "101010100";//?
		GetNowWeatherArea(selcityid);

		message_txt = (TextView) this.findViewById(R.id.message_txt);

		Button clickbtn = (Button) this.findViewById(R.id.clickbtn);
		clickbtn.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.clickbtn:
				CreateAddress();
				break;
			}
		}
	};

	/**
	 * 创建
	 * 
	 */
	private void CreateAddress() {
		if (alertDialog != null) {
			if (alertDialog.isShowing()) {
				alertDialog.dismiss();
			}
			alertDialog = null;
		}
		View add_addressdlgView;
		LayoutInflater factory = LayoutInflater.from(this);
		alertDialog = new AlertDialog.Builder(this).setCancelable(false).create();
		add_addressdlgView = factory.inflate(R.layout.level3linkage_sel2, null);
		alertDialog.setView(add_addressdlgView);
		alertDialog.show();
		Window window = alertDialog.getWindow();
		alertDialog.setContentView(R.layout.level3linkage_sel);
		TextView title = (TextView) window.findViewById(R.id.AlertDialogTitle);
		title.setTypeface(typefacenormal);
		title.setText("选择天气预报城市");

		TextView add_province_txt = (TextView) window
				.findViewById(R.id.add_province_txt);
		add_province_box = (Spinner) window.findViewById(R.id.add_province_box);
		add_province_txt.setTypeface(typefacenormal);
		GetProvinceList();

		if (nowWeatherAreaModel != null) {
			BindProvince(nowWeatherAreaModel.areaname1());
		} else {
			BindProvince(null);
		}

		add_province_box
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						String name = (String) add_province_box
								.getSelectedItem();
						WeatherAreaModel model = GetProvinceByName(name);
						GetCityList(model.areaname1());
						if (nowWeatherAreaModel != null) {
							BindCity(nowWeatherAreaModel.areaname2());
						} else {
							BindCity(null);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		TextView add_city_txt = (TextView) window
				.findViewById(R.id.add_city_txt);
		add_city_box = (Spinner) window.findViewById(R.id.add_city_box);
		add_city_txt.setTypeface(typefacenormal);

		add_city_box.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String name = (String) add_city_box.getSelectedItem();
				WeatherAreaModel model = GetCityByName(name);
				GetStreetList(model.areaname2());
				if (nowWeatherAreaModel != null) {
					BindStreet(nowWeatherAreaModel.areaname3());
				} else {
					BindStreet(null);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		TextView add_street_txt = (TextView) window
				.findViewById(R.id.add_street_txt);
		add_street_box = (Spinner) window.findViewById(R.id.add_street_box);
		add_street_txt.setTypeface(typefacenormal);

		add_street_box.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String name = (String) add_street_box.getSelectedItem();
				WeatherAreaModel model = GetStreetByName(name);
				selcityid = model.areaid();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		add_address_close = (Button) window.findViewById(R.id.btn_close);

		add_address_ok = (LinearLayout) window.findViewById(R.id.ok_btn_layout);
		TextView ok_btn_txt = (TextView) window.findViewById(R.id.ok_btn_txt);
		ok_btn_txt.setTypeface(typefacebold);

		add_address_cancle = (LinearLayout) window
				.findViewById(R.id.cancle_btn_layout);
		TextView cancle_btn_txt = (TextView) window
				.findViewById(R.id.cancle_btn_txt);
		cancle_btn_txt.setTypeface(typefacebold);

		add_address_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});

		add_address_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});

		add_address_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GetNowWeatherArea(selcityid);
				alertDialog.dismiss();
			}
		});
	}

	/**
	 * 当前天气城市
	 * 
	 * @param ctiyid
	 */
	private void GetNowWeatherArea(String areaid) {
		for (WeatherAreaModel item : weatherAreaModels) {
			if (item.areaid().equals(areaid)) {
				nowWeatherAreaModel = item;
				break;
			}
		}
		if (nowWeatherAreaModel == null) {
			nowWeatherAreaModel = weatherAreaModels.get(0);//取第一条数据
		}

		Message msg = new Message();
		msg.what = 0;
		msg.obj = "当前选择城市：id:" + nowWeatherAreaModel.areaid() + "，"
				+ nowWeatherAreaModel.areaname1() + "-"
				+ nowWeatherAreaModel.areaname2() + "-"
				+ nowWeatherAreaModel.areaname3();
		messageHandler.sendMessage(msg);
	}

	/**
	 * 根据名称获取一级城市
	 * 
	 * @param name
	 * @return
	 */
	private WeatherAreaModel GetProvinceByName(String name) {
		WeatherAreaModel model = null;
		for (WeatherAreaModel item : provinceList) {
			if (item.areaname1().equals(name)) {
				model = item;
				break;
			}
		}
		return model;
	}

	/**
	 * 根据名称获取二级城市
	 * 
	 * @param name
	 * @return
	 */
	private WeatherAreaModel GetCityByName(String name) {
		WeatherAreaModel model = null;
		for (WeatherAreaModel item : cityList) {
			if (item.areaname2().equals(name)) {
				model = item;
				break;
			}
		}
		return model;
	}

	/**
	 * 根据名称获取三级城市
	 * 
	 * @param name
	 * @return
	 */
	private WeatherAreaModel GetStreetByName(String name) {
		WeatherAreaModel model = null;
		for (WeatherAreaModel item : streetList) {
			if (item.areaname3().equals(name)) {
				model = item;
				break;
			}
		}
		return model;
	}

	/**
	 * 获取一级城市列表
	 */
	private void GetProvinceList() {
		provinceList = new ArrayList<WeatherAreaModel>();
		for (WeatherAreaModel item : weatherAreaModels) {
			if (!checkProvince(item.areaname1())) {
				provinceList.add(item);
			}
		}
	}

	/**
	 * 绑定省
	 * 
	 * @param provincename
	 */
	private void BindProvince(String provincename) {
		ArrayList<String> list = new ArrayList<String>();
		for (WeatherAreaModel item : provinceList) {
			list.add(item.areaname1());
		}
		provinceAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		provinceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		add_province_box.setAdapter(provinceAdapter);
		if (provincename == null) {
			add_province_box.setSelection(0);
		} else {
			for (int i = 0; i < provinceList.size(); i++) {
				if (provincename.equals(provinceList.get(i).areaname1())) {
					add_province_box.setSelection(i);
					break;
				}
			}
		}
	}

	/**
	 * 是否已存在该一级城市
	 * 
	 * @param name
	 * @return
	 */
	private boolean checkProvince(String name) {
		boolean t = false;
		for (WeatherAreaModel item : provinceList) {
			if (item.areaname1().equals(name)) {
				t = true;
				break;
			}
		}
		return t;
	}

	/**
	 * 获取二级城市
	 * 
	 * @param pname
	 */
	private void GetCityList(String pname) {
		cityList = new ArrayList<WeatherAreaModel>();
		for (WeatherAreaModel item : weatherAreaModels) {
			if (item.areaname1().equals(pname)) {
				if (!checkCity(item.areaname2())) {
					cityList.add(item);
				}
			}
		}
	}

	/**
	 * 绑定市
	 * 
	 * @param cityname
	 */
	private void BindCity(String cityname) {
		ArrayList<String> list = new ArrayList<String>();
		for (WeatherAreaModel item : cityList) {
			list.add(item.areaname2());
		}
		cityAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		add_city_box.setAdapter(cityAdapter);
		if (cityname == null) {
			add_city_box.setSelection(0);
		} else {
			for (int i = 0; i < cityList.size(); i++) {
				if (cityname.equals(cityList.get(i).areaname2())) {
					add_city_box.setSelection(i);
					break;
				}
			}
		}
	}

	/**
	 * 是否已存在该二级城市
	 * 
	 * @param name
	 * @return
	 */
	private boolean checkCity(String name) {
		boolean t = false;
		for (WeatherAreaModel item : cityList) {
			if (item.areaname2().equals(name)) {
				t = true;
				break;
			}
		}
		return t;
	}

	/**
	 * 获取三级城市
	 * 
	 * @param pname
	 */
	private void GetStreetList(String pname) {
		streetList = new ArrayList<WeatherAreaModel>();
		for (WeatherAreaModel item : weatherAreaModels) {
			if (item.areaname2().equals(pname)) {
				if (!checkStreet(item.areaname3())) {
					streetList.add(item);
				}
			}
		}
	}

	/**
	 * 绑定区
	 * 
	 * @param streetname
	 */
	private void BindStreet(String streetname) {
		ArrayList<String> list = new ArrayList<String>();
		for (WeatherAreaModel item : streetList) {
			list.add(item.areaname3());
		}
		streetAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		streetAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		add_street_box.setAdapter(streetAdapter);
		if (streetname == null) {
			add_street_box.setSelection(0);
		} else {
			for (int i = 0; i < streetList.size(); i++) {
				if (streetname.equals(streetList.get(i).areaname3())) {
					add_street_box.setSelection(i);
					break;
				}
			}
		}
	}

	/**
	 * 是否已存在该三级城市
	 * 
	 * @param name
	 * @return
	 */
	private boolean checkStreet(String name) {
		boolean t = false;
		for (WeatherAreaModel item : streetList) {
			if (item.areaname3().equals(name)) {
				t = true;
				break;
			}
		}
		return t;
	}

	private Handler messageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				message_txt.setText(msg.obj + "");
				break;
			}
		}
	};

	class WeatherAreaModel {
		private String areaid;
		private String areaname1;
		private String areaname2;
		private String areaname3;

		public WeatherAreaModel() {
			super();
		}

		public WeatherAreaModel(String areaid, String areaname3,
				String areaname2, String areaname1) {
			this.areaid = areaid;
			this.areaname1 = areaname1;
			this.areaname2 = areaname2;
			this.areaname3 = areaname3;
		}

		/**
		 * 地区ID
		 * 
		 * @return
		 */
		public String areaid() {
			return areaid;
		}

		public void Setareaid(String areaid) {
			this.areaid = areaid;
		}

		/**
		 * 一级城市名称
		 * 
		 * @return
		 */
		public String areaname1() {
			return areaname1;
		}

		public void Setareaname1(String areaname1) {
			this.areaname1 = areaname1;
		}

		/**
		 * 二级城市名称
		 * 
		 * @return
		 */
		public String areaname2() {
			return areaname2;
		}

		public void Setareaname2(String areaname2) {
			this.areaname2 = areaname2;
		}

		/**
		 * 三级城市名称
		 * 
		 * @return
		 */
		public String areaname3() {
			return areaname3;
		}

		public void Setareaname3(String areaname3) {
			this.areaname3 = areaname3;
		}
	}

	class WeatherAreaList {
		public ArrayList<WeatherAreaModel> GetList() {
			ArrayList<WeatherAreaModel> WeatherAreaModels = new ArrayList<WeatherAreaModel>();
			// WeatherAreaModels.add(new WeatherAreaModel("101010100", "北京",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010200", "海淀",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010300", "朝阳",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010400", "顺义",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010500", "怀柔",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010600", "通州",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010700", "昌平",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010800", "延庆",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010900", "丰台",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011000", "石景山",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011100", "大兴",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011200", "房山",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011300", "密云",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011400", "门头沟",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011500", "平谷",
			// "北京", "北京"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020100", "上海",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020200", "闵行",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020300", "宝山",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020500", "嘉定",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020600", "南汇",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020700", "金山",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020800", "青浦",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020900", "松江",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101021000", "奉贤",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101021100", "崇明",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101021200", "徐家汇",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101021300", "浦东",
			// "上海", "上海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030100", "天津",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030200", "武清",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030300", "宝坻",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030400", "东丽",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030500", "西青",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030600", "北辰",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030700", "宁河",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030800", "汉沽",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030900", "静海",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101031000", "津南",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101031100", "塘沽",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101031200", "大港",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101031400", "蓟县",
			// "天津", "天津"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040100", "重庆",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040200", "永川",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040300", "合川",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040400", "南川",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040500", "江津",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040600", "万盛",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040700", "渝北",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040800", "北碚",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040900", "巴南",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041000", "长寿",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041100", "黔江",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041300", "万州",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041400", "涪陵",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041500", "开县",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041600", "城口",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041700", "云阳",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041800", "巫溪",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041900", "奉节",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042000", "巫山",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042100", "潼南",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042200", "垫江",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042300", "梁平",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042400", "忠县",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042500", "石柱",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042600", "大足",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042700", "荣昌",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042800", "铜梁",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042900", "璧山",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043000", "丰都",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043100", "武隆",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043200", "彭水",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043300", "綦江",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043400", "酉阳",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043600", "秀山",
			// "重庆", "重庆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050101", "哈尔滨",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050102", "双城",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050103", "呼兰",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050104", "阿城",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050105", "宾县",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050106", "依兰",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050107", "巴彦",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050108", "通河",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050109", "方正",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050110", "延寿",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050111", "尚志",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050112", "五常",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050113", "木兰",
			// "哈尔滨", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050201", "齐齐哈尔",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050202", "讷河",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050203", "龙江",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050204", "甘南",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050205", "富裕",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050206", "依安",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050207", "拜泉",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050208", "克山",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050209", "克东",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050210", "泰来",
			// "齐齐哈尔", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050301", "牡丹江",
			// "牡丹江", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050302", "海林",
			// "牡丹江", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050303", "穆棱",
			// "牡丹江", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050304", "林口",
			// "牡丹江", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050305", "绥芬河",
			// "牡丹江", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050306", "宁安",
			// "牡丹江", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050307", "东宁",
			// "牡丹江", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050401", "佳木斯",
			// "佳木斯", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050402", "汤原",
			// "佳木斯", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050403", "抚远",
			// "佳木斯", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050404", "桦川",
			// "佳木斯", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050405", "桦南",
			// "佳木斯", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050406", "同江",
			// "佳木斯", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050407", "富锦",
			// "佳木斯", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050501", "绥化",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050502", "肇东",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050503", "安达",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050504", "海伦",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050505", "明水",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050506", "望奎",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050507", "兰西",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050508", "青冈",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050509", "庆安",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050510", "绥棱",
			// "绥化", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050601", "黑河",
			// "黑河", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050602", "嫩江",
			// "黑河", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050603", "孙吴",
			// "黑河", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050604", "逊克",
			// "黑河", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050605", "五大连池",
			// "黑河", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050606", "北安",
			// "黑河", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050701", "大兴安岭",
			// "大兴安岭", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050702", "塔河",
			// "大兴安岭", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050703", "漠河",
			// "大兴安岭", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050704", "呼玛",
			// "大兴安岭", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050705", "呼中",
			// "大兴安岭", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050706", "新林",
			// "大兴安岭", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050708", "加格达奇",
			// "大兴安岭", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050801", "伊春",
			// "伊春", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050802", "乌伊岭",
			// "伊春", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050803", "五营",
			// "伊春", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050804", "铁力",
			// "伊春", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050805", "嘉荫",
			// "伊春", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050901", "大庆",
			// "大庆", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050902", "林甸",
			// "大庆", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050903", "肇州",
			// "大庆", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050904", "肇源",
			// "大庆", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050905", "杜尔伯特",
			// "大庆", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051002", "七台河",
			// "七台河", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051003", "勃利",
			// "七台河", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051101", "鸡西",
			// "鸡西", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051102", "虎林",
			// "鸡西", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051103", "密山",
			// "鸡西", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051104", "鸡东",
			// "鸡西", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051201", "鹤岗",
			// "鹤岗", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051202", "绥滨",
			// "鹤岗", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051203", "萝北",
			// "鹤岗", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051301", "双鸭山",
			// "双鸭山", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051302", "集贤",
			// "双鸭山", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051303", "宝清",
			// "双鸭山", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051304", "饶河",
			// "双鸭山", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051305", "友谊",
			// "双鸭山", "黑龙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060101", "长春",
			// "长春", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060102", "农安",
			// "长春", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060103", "德惠",
			// "长春", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060104", "九台",
			// "长春", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060105", "榆树",
			// "长春", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060106", "双阳",
			// "长春", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060201", "吉林",
			// "吉林", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060202", "舒兰",
			// "吉林", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060203", "永吉",
			// "吉林", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060204", "蛟河",
			// "吉林", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060205", "磐石",
			// "吉林", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060206", "桦甸",
			// "吉林", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060301", "延吉",
			// "延边", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060302", "敦化",
			// "延边", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060303", "安图",
			// "延边", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060304", "汪清",
			// "延边", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060305", "和龙",
			// "延边", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060307", "龙井",
			// "延边", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060308", "珲春",
			// "延边", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060309", "图们",
			// "延边", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060401", "四平",
			// "四平", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060402", "双辽",
			// "四平", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060403", "梨树",
			// "四平", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060404", "公主岭",
			// "四平", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060405", "伊通",
			// "四平", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060501", "通化",
			// "通化", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060502", "梅河口",
			// "通化", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060503", "柳河",
			// "通化", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060504", "辉南",
			// "通化", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060505", "集安",
			// "通化", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060506", "通化县",
			// "通化", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060601", "白城",
			// "白城", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060602", "洮南",
			// "白城", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060603", "大安",
			// "白城", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060604", "镇赉",
			// "白城", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060605", "通榆",
			// "白城", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060701", "辽源",
			// "辽源", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060702", "东丰",
			// "辽源", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060703", "东辽",
			// "辽源", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060801", "松原",
			// "松原", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060802", "乾安",
			// "松原", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060803", "前郭",
			// "松原", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060804", "长岭",
			// "松原", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060805", "扶余",
			// "松原", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060901", "白山",
			// "白山", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060902", "靖宇",
			// "白山", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060903", "临江",
			// "白山", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060904", "东岗",
			// "白山", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060905", "长白",
			// "白山", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060906", "抚松",
			// "白山", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060907", "江源",
			// "白山", "吉林"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070101", "沈阳",
			// "沈阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070103", "辽中",
			// "沈阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070104", "康平",
			// "沈阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070105", "法库",
			// "沈阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070106", "新民",
			// "沈阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070201", "大连",
			// "大连", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070202", "瓦房店",
			// "大连", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070203", "金州",
			// "大连", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070204", "普兰店",
			// "大连", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070205", "旅顺",
			// "大连", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070206", "长海",
			// "大连", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070207", "庄河",
			// "大连", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070301", "鞍山",
			// "鞍山", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070302", "台安",
			// "鞍山", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070303", "岫岩",
			// "鞍山", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070304", "海城",
			// "鞍山", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070401", "抚顺",
			// "抚顺", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070402", "新宾",
			// "抚顺", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070403", "清原",
			// "抚顺", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070404", "章党",
			// "抚顺", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070501", "本溪",
			// "本溪", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070502", "本溪县",
			// "本溪", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070504", "桓仁",
			// "本溪", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070601", "丹东",
			// "丹东", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070602", "凤城",
			// "丹东", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070603", "宽甸",
			// "丹东", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070604", "东港",
			// "丹东", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070701", "锦州",
			// "锦州", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070702", "凌海",
			// "锦州", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070704", "义县",
			// "锦州", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070705", "黑山",
			// "锦州", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070706", "北镇",
			// "锦州", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070801", "营口",
			// "营口", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070802", "大石桥",
			// "营口", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070803", "盖州",
			// "营口", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070901", "阜新",
			// "阜新", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070902", "彰武",
			// "阜新", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071001", "辽阳",
			// "辽阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071002", "辽阳县",
			// "辽阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071003", "灯塔",
			// "辽阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071004", "弓长岭",
			// "辽阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071101", "铁岭",
			// "铁岭", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071102", "开原",
			// "铁岭", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071103", "昌图",
			// "铁岭", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071104", "西丰",
			// "铁岭", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071105", "铁法",
			// "铁岭", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071201", "朝阳",
			// "朝阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071203", "凌源",
			// "朝阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071204", "喀左",
			// "朝阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071205", "北票",
			// "朝阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071207", "建平县",
			// "朝阳", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071301", "盘锦",
			// "盘锦", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071302", "大洼",
			// "盘锦", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071303", "盘山",
			// "盘锦", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071401", "葫芦岛",
			// "葫芦岛", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071402", "建昌",
			// "葫芦岛", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071403", "绥中",
			// "葫芦岛", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071404", "兴城",
			// "葫芦岛", "辽宁"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080101", "呼和浩特",
			// "呼和浩特", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080102", "土左旗",
			// "呼和浩特", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080103", "托县",
			// "呼和浩特", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080104", "和林",
			// "呼和浩特", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080105", "清水河",
			// "呼和浩特", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080106", "呼市郊区",
			// "呼和浩特", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080107", "武川",
			// "呼和浩特", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080201", "包头",
			// "包头", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080202", "白云鄂博",
			// "包头", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080203", "满都拉",
			// "包头", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080204", "土右旗",
			// "包头", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080205", "固阳",
			// "包头", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080206", "达茂旗",
			// "包头", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080207", "希拉穆仁",
			// "包头", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080301", "乌海",
			// "乌海", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080401", "集宁",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080402", "卓资",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080403", "化德",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080404", "商都",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080406", "兴和",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080407", "凉城",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080408", "察右前旗",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080409", "察右中旗",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080410", "察右后旗",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080411", "四子王旗",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080412", "丰镇",
			// "乌兰察布", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080501", "通辽",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080502", "舍伯吐",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080503", "科左中旗",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080504", "科左后旗",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080505", "青龙山",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080506", "开鲁",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080507", "库伦",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080508", "奈曼",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080509", "扎鲁特",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080510", "高力板",
			// "兴安盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080511", "巴雅尔吐胡硕",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080601", "赤峰",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080603", "阿鲁旗",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080604", "浩尔吐",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080605", "巴林左旗",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080606", "巴林右旗",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080607", "林西",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080608", "克什克腾",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080609", "翁牛特",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080610", "岗子",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080611", "喀喇沁",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080612", "八里罕",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080613", "宁城",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080614", "敖汉",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080615", "宝国吐",
			// "赤峰", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080701", "鄂尔多斯",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080703", "达拉特",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080704", "准格尔",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080705", "鄂前旗",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080706", "河南",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080707", "伊克乌素",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080708", "鄂托克",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080709", "杭锦旗",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080710", "乌审旗",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080711", "伊金霍洛",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080712", "乌审召",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080713", "东胜",
			// "鄂尔多斯", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080801", "临河",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080802", "五原",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080803", "磴口",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080804", "乌前旗",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080805", "大佘太",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080806", "乌中旗",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080807", "乌后旗",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080808", "海力素",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080809", "那仁宝力格",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080810", "杭锦后旗",
			// "巴彦淖尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080901", "锡林浩特",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080903", "二连浩特",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080904", "阿巴嘎",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080906", "苏左旗",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080907", "苏右旗",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080908", "朱日和",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080909", "东乌旗",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080910", "西乌旗",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080911", "太仆寺",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080912", "镶黄旗",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080913", "正镶白旗",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080914", "正兰旗",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080915", "多伦",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080916", "博克图",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080917", "乌拉盖",
			// "锡林郭勒", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081000", "呼伦贝尔",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081001", "海拉尔",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081002", "小二沟",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081003", "阿荣旗",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081004", "莫力达瓦",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081005", "鄂伦春旗",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081006", "鄂温克旗",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081007", "陈旗",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081008", "新左旗",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081009", "新右旗",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081010", "满洲里",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081011", "牙克石",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081012", "扎兰屯",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081014", "额尔古纳",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081015", "根河",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081016", "图里河",
			// "呼伦贝尔", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081101", "乌兰浩特",
			// "兴安盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081102", "阿尔山",
			// "兴安盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081103", "科右中旗",
			// "兴安盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081104", "胡尔勒",
			// "兴安盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081105", "扎赉特",
			// "兴安盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081106", "索伦",
			// "兴安盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081107", "突泉",
			// "兴安盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081108", "霍林郭勒",
			// "通辽", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081109", "科右前旗",
			// "兴安盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081201", "阿左旗",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081202", "阿右旗",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081203", "额济纳",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081204", "拐子湖",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081205", "吉兰太",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081206", "锡林高勒",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081207", "头道湖",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081208", "中泉子",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081209", "诺尔公",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081210", "雅布赖",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081211", "乌斯泰",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081212", "孪井滩",
			// "阿拉善盟", "内蒙古"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090101", "石家庄",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090102", "井陉",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090103", "正定",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090104", "栾城",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090105", "行唐",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090106", "灵寿",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090107", "高邑",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090108", "深泽",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090109", "赞皇",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090110", "无极",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090111", "平山",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090112", "元氏",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090113", "赵县",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090114", "辛集",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090115", "藁城",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090116", "晋州",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090117", "新乐",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090118", "鹿泉",
			// "石家庄", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090201", "保定",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090202", "满城",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090203", "阜平",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090204", "徐水",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090205", "唐县",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090206", "高阳",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090207", "容城",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090209", "涞源",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090210", "望都",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090211", "安新",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090212", "易县",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090214", "曲阳",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090215", "蠡县",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090216", "顺平",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090217", "雄县",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090218", "涿州",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090219", "定州",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090220", "安国",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090221", "高碑店",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090222", "涞水",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090223", "定兴",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090224", "清苑",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090225", "博野",
			// "保定", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090301", "张家口",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090302", "宣化",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090303", "张北",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090304", "康保",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090305", "沽源",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090306", "尚义",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090307", "蔚县",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090308", "阳原",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090309", "怀安",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090310", "万全",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090311", "怀来",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090312", "涿鹿",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090313", "赤城",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090314", "崇礼",
			// "张家口", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090402", "承德",
			// "承德", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090403", "承德县",
			// "承德", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090404", "兴隆",
			// "承德", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090405", "平泉",
			// "承德", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090406", "滦平",
			// "承德", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090407", "隆化",
			// "承德", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090408", "丰宁",
			// "承德", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090409", "宽城",
			// "承德", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090410", "围场",
			// "承德", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090501", "唐山",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090502", "丰南",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090503", "丰润",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090504", "滦县",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090505", "滦南",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090506", "乐亭",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090507", "迁西",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090508", "玉田",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090509", "唐海",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090510", "遵化",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090511", "迁安",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090512", "曹妃甸",
			// "唐山", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090601", "廊坊",
			// "廊坊", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090602", "固安",
			// "廊坊", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090603", "永清",
			// "廊坊", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090604", "香河",
			// "廊坊", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090605", "大城",
			// "廊坊", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090606", "文安",
			// "廊坊", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090607", "大厂",
			// "廊坊", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090608", "霸州",
			// "廊坊", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090609", "三河",
			// "廊坊", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090701", "沧州",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090702", "青县",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090703", "东光",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090704", "海兴",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090705", "盐山",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090706", "肃宁",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090707", "南皮",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090708", "吴桥",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090709", "献县",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090710", "孟村",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090711", "泊头",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090712", "任丘",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090713", "黄骅",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090714", "河间",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090716", "沧县",
			// "沧州", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090801", "衡水",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090802", "枣强",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090803", "武邑",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090804", "武强",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090805", "饶阳",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090806", "安平",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090807", "故城",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090808", "景县",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090809", "阜城",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090810", "冀州",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090811", "深州",
			// "衡水", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090901", "邢台",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090902", "临城",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090904", "内丘",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090905", "柏乡",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090906", "隆尧",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090907", "南和",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090908", "宁晋",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090909", "巨鹿",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090910", "新河",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090911", "广宗",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090912", "平乡",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090913", "威县",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090914", "清河",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090915", "临西",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090916", "南宫",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090917", "沙河",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090918", "任县",
			// "邢台", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091001", "邯郸",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091002", "峰峰",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091003", "临漳",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091004", "成安",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091005", "大名",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091006", "涉县",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091007", "磁县",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091008", "肥乡",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091009", "永年",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091010", "邱县",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091011", "鸡泽",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091012", "广平",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091013", "馆陶",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091014", "魏县",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091015", "曲周",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091016", "武安",
			// "邯郸", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091101", "秦皇岛",
			// "秦皇岛", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091102", "青龙",
			// "秦皇岛", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091103", "昌黎",
			// "秦皇岛", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091104", "抚宁",
			// "秦皇岛", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091105", "卢龙",
			// "秦皇岛", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091106", "北戴河",
			// "秦皇岛", "河北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100101", "太原",
			// "太原", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100102", "清徐",
			// "太原", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100103", "阳曲",
			// "太原", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100104", "娄烦",
			// "太原", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100105", "古交",
			// "太原", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100106", "尖草坪区",
			// "太原", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100107", "小店区",
			// "太原", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100201", "大同",
			// "大同", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100202", "阳高",
			// "大同", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100203", "大同县",
			// "大同", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100204", "天镇",
			// "大同", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100205", "广灵",
			// "大同", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100206", "灵丘",
			// "大同", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100207", "浑源",
			// "大同", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100208", "左云",
			// "大同", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100301", "阳泉",
			// "阳泉", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100302", "盂县",
			// "阳泉", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100303", "平定",
			// "阳泉", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100401", "晋中",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100402", "榆次",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100403", "榆社",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100404", "左权",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100405", "和顺",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100406", "昔阳",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100407", "寿阳",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100408", "太谷",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100409", "祁县",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100410", "平遥",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100411", "灵石",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100412", "介休",
			// "晋中", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100501", "长治",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100502", "黎城",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100503", "屯留",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100504", "潞城",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100505", "襄垣",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100506", "平顺",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100507", "武乡",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100508", "沁县",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100509", "长子",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100510", "沁源",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100511", "壶关",
			// "长治", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100601", "晋城",
			// "晋城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100602", "沁水",
			// "晋城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100603", "阳城",
			// "晋城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100604", "陵川",
			// "晋城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100605", "高平",
			// "晋城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100606", "泽州",
			// "晋城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100701", "临汾",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100702", "曲沃",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100703", "永和",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100704", "隰县",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100705", "大宁",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100706", "吉县",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100707", "襄汾",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100708", "蒲县",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100709", "汾西",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100710", "洪洞",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100711", "霍州",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100712", "乡宁",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100713", "翼城",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100714", "侯马",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100715", "浮山",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100716", "安泽",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100717", "古县",
			// "临汾", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100801", "运城",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100802", "临猗",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100803", "稷山",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100804", "万荣",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100805", "河津",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100806", "新绛",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100807", "绛县",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100808", "闻喜",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100809", "垣曲",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100810", "永济",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100811", "芮城",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100812", "夏县",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100813", "平陆",
			// "运城", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100901", "朔州",
			// "朔州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100902", "平鲁",
			// "朔州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100903", "山阴",
			// "朔州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100904", "右玉",
			// "朔州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100905", "应县",
			// "朔州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100906", "怀仁",
			// "朔州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101001", "忻州",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101002", "定襄",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101003", "五台县",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101004", "河曲",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101005", "偏关",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101006", "神池",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101007", "宁武",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101008", "代县",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101009", "繁峙",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101010", "五台山",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101011", "保德",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101012", "静乐",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101013", "岢岚",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101014", "五寨",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101015", "原平",
			// "忻州", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101100", "吕梁",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101101", "离石",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101102", "临县",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101103", "兴县",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101104", "岚县",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101105", "柳林",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101106", "石楼",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101107", "方山",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101108", "交口",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101109", "中阳",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101110", "孝义",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101111", "汾阳",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101112", "文水",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101113", "交城",
			// "吕梁", "山西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110101", "西安",
			// "西安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110102", "长安",
			// "西安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110103", "临潼",
			// "西安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110104", "蓝田",
			// "西安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110105", "周至",
			// "西安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110106", "户县",
			// "西安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110107", "高陵",
			// "西安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110200", "咸阳",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110201", "三原",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110202", "礼泉",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110203", "永寿",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110204", "淳化",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110205", "泾阳",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110206", "武功",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110207", "乾县",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110208", "彬县",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110209", "长武",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110210", "旬邑",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110211", "兴平",
			// "咸阳", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110300", "延安",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110301", "延长",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110302", "延川",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110303", "子长",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110304", "宜川",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110305", "富县",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110306", "志丹",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110307", "安塞",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110308", "甘泉",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110309", "洛川",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110310", "黄陵",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110311", "黄龙",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110312", "吴起",
			// "延安", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110401", "榆林",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110402", "府谷",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110403", "神木",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110404", "佳县",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110405", "定边",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110406", "靖边",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110407", "横山",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110408", "米脂",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110409", "子洲",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110410", "绥德",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110411", "吴堡",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110412", "清涧",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110413", "榆阳",
			// "榆林", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110501", "渭南",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110502", "华县",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110503", "潼关",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110504", "大荔",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110505", "白水",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110506", "富平",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110507", "蒲城",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110508", "澄城",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110509", "合阳",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110510", "韩城",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110511", "华阴",
			// "渭南", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110601", "商洛",
			// "商洛", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110602", "洛南",
			// "商洛", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110603", "柞水",
			// "商洛", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110604", "商州",
			// "商洛", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110605", "镇安",
			// "商洛", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110606", "丹凤",
			// "商洛", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110607", "商南",
			// "商洛", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110608", "山阳",
			// "商洛", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110701", "安康",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110702", "紫阳",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110703", "石泉",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110704", "汉阴",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110705", "旬阳",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110706", "岚皋",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110707", "平利",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110708", "白河",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110709", "镇坪",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110710", "宁陕",
			// "安康", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110801", "汉中",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110802", "略阳",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110803", "勉县",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110804", "留坝",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110805", "洋县",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110806", "城固",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110807", "西乡",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110808", "佛坪",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110809", "宁强",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110810", "南郑",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110811", "镇巴",
			// "汉中", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110901", "宝鸡",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110903", "千阳",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110904", "麟游",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110905", "岐山",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110906", "凤翔",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110907", "扶风",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110908", "眉县",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110909", "太白",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110910", "凤县",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110911", "陇县",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110912", "陈仓",
			// "宝鸡", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111001", "铜川",
			// "铜川", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111002", "耀县",
			// "铜川", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111003", "宜君",
			// "铜川", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111004", "耀州",
			// "铜川", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111101", "杨凌",
			// "杨凌", "陕西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120101", "济南",
			// "济南", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120102", "长清",
			// "济南", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120103", "商河",
			// "济南", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120104", "章丘",
			// "济南", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120105", "平阴",
			// "济南", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120106", "济阳",
			// "济南", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120107", "天桥",
			// "济南", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120201", "青岛",
			// "青岛", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120202", "崂山",
			// "青岛", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120204", "即墨",
			// "青岛", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120205", "胶州",
			// "青岛", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120206", "胶南",
			// "青岛", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120207", "莱西",
			// "青岛", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120208", "平度",
			// "青岛", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120301", "淄博",
			// "淄博", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120302", "淄川",
			// "淄博", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120303", "博山",
			// "淄博", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120304", "高青",
			// "淄博", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120305", "周村",
			// "淄博", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120306", "沂源",
			// "淄博", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120307", "桓台",
			// "淄博", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120308", "临淄",
			// "淄博", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120401", "德州",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120402", "武城",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120403", "临邑",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120404", "陵县",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120405", "齐河",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120406", "乐陵",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120407", "庆云",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120408", "平原",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120409", "宁津",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120410", "夏津",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120411", "禹城",
			// "德州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120501", "烟台",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120502", "莱州",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120503", "长岛",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120504", "蓬莱",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120505", "龙口",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120506", "招远",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120507", "栖霞",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120508", "福山",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120509", "牟平",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120510", "莱阳",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120511", "海阳",
			// "烟台", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120601", "潍坊",
			// "潍坊", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120602", "青州",
			// "潍坊", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120603", "寿光",
			// "潍坊", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120604", "临朐",
			// "潍坊", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120605", "昌乐",
			// "潍坊", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120606", "昌邑",
			// "潍坊", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120607", "安丘",
			// "潍坊", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120608", "高密",
			// "潍坊", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120609", "诸城",
			// "潍坊", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120701", "济宁",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120702", "嘉祥",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120703", "微山",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120704", "鱼台",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120705", "兖州",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120706", "金乡",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120707", "汶上",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120708", "泗水",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120709", "梁山",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120710", "曲阜",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120711", "邹城",
			// "济宁", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120801", "泰安",
			// "泰安", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120802", "新泰",
			// "泰安", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120804", "肥城",
			// "泰安", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120805", "东平",
			// "泰安", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120806", "宁阳",
			// "泰安", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120901", "临沂",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120902", "莒南",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120903", "沂南",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120904", "苍山",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120905", "临沭",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120906", "郯城",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120907", "蒙阴",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120908", "平邑",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120909", "费县",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120910", "沂水",
			// "临沂", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121001", "菏泽",
			// "菏泽", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121002", "鄄城",
			// "菏泽", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121003", "郓城",
			// "菏泽", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121004", "东明",
			// "菏泽", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121005", "定陶",
			// "菏泽", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121006", "巨野",
			// "菏泽", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121007", "曹县",
			// "菏泽", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121008", "成武",
			// "菏泽", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121009", "单县",
			// "菏泽", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121101", "滨州",
			// "滨州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121102", "博兴",
			// "滨州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121103", "无棣",
			// "滨州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121104", "阳信",
			// "滨州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121105", "惠民",
			// "滨州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121106", "沾化",
			// "滨州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121107", "邹平",
			// "滨州", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121201", "东营",
			// "东营", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121202", "河口",
			// "东营", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121203", "垦利",
			// "东营", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121204", "利津",
			// "东营", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121205", "广饶",
			// "东营", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121301", "威海",
			// "威海", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121302", "文登",
			// "威海", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121303", "荣成",
			// "威海", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121304", "乳山",
			// "威海", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121305", "成山头",
			// "威海", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121306", "石岛",
			// "威海", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121401", "枣庄",
			// "枣庄", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121402", "薛城",
			// "枣庄", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121403", "峄城",
			// "枣庄", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121404", "台儿庄",
			// "枣庄", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121405", "滕州",
			// "枣庄", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121501", "日照",
			// "日照", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121502", "五莲",
			// "日照", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121503", "莒县",
			// "日照", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121601", "莱芜",
			// "莱芜", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121701", "聊城",
			// "聊城", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121702", "冠县",
			// "聊城", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121703", "阳谷",
			// "聊城", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121704", "高唐",
			// "聊城", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121705", "茌平",
			// "聊城", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121706", "东阿",
			// "聊城", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121707", "临清",
			// "聊城", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121709", "莘县",
			// "聊城", "山东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130101", "乌鲁木齐",
			// "乌鲁木齐", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130103", "小渠子",
			// "乌鲁木齐", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130104", "巴仑台",
			// "乌鲁木齐", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130105", "达坂城",
			// "乌鲁木齐", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130108",
			// "乌鲁木齐牧试站", "乌鲁木齐", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130109", "天池",
			// "乌鲁木齐", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130110", "白杨沟",
			// "乌鲁木齐", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130201", "克拉玛依",
			// "克拉玛依", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130202", "乌尔禾",
			// "克拉玛依", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130203", "白碱滩",
			// "克拉玛依", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130301", "石河子",
			// "石河子", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130302", "炮台",
			// "石河子", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130303", "莫索湾",
			// "石河子", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130401", "昌吉",
			// "昌吉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130402", "呼图壁",
			// "昌吉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130403", "米泉",
			// "昌吉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130404", "阜康",
			// "昌吉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130405", "吉木萨尔",
			// "昌吉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130406", "奇台",
			// "昌吉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130407", "玛纳斯",
			// "昌吉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130408", "木垒",
			// "昌吉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130409", "蔡家湖",
			// "昌吉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130501", "吐鲁番",
			// "吐鲁番", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130502", "托克逊",
			// "吐鲁番", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130503", "克孜勒",
			// "吐鲁番", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130504", "鄯善",
			// "吐鲁番", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130601", "库尔勒",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130602", "轮台",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130603", "尉犁",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130604", "若羌",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130605", "且末",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130606", "和静",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130607", "焉耆",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130608", "和硕",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130610", "巴音布鲁克",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130611", "铁干里克",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130612", "博湖",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130613", "塔中",
			// "巴音郭楞", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130701", "阿拉尔",
			// "阿拉尔", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130801", "阿克苏",
			// "阿克苏", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130802", "乌什",
			// "阿克苏", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130803", "温宿",
			// "阿克苏", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130804", "拜城",
			// "阿克苏", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130805", "新和",
			// "阿克苏", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130806", "沙雅",
			// "阿克苏", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130807", "库车",
			// "阿克苏", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130808", "柯坪",
			// "阿克苏", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130809", "阿瓦提",
			// "阿克苏", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130901", "喀什",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130902", "英吉沙",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130903", "塔什库尔干",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130904", "麦盖提",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130905", "莎车",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130906", "叶城",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130907", "泽普",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130908", "巴楚",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130909", "岳普湖",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130910", "伽师",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130911", "疏附",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130912", "疏勒",
			// "喀什", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131001", "伊宁",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131002", "察布查尔",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131003", "尼勒克",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131004", "伊宁县",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131005", "巩留",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131006", "新源",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131007", "昭苏",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131008", "特克斯",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131009", "霍城",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131010", "霍尔果斯",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131011", "奎屯",
			// "伊犁", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131101", "塔城",
			// "塔城", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131102", "裕民",
			// "塔城", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131103", "额敏",
			// "塔城", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131104", "和布克赛尔",
			// "塔城", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131105", "托里",
			// "塔城", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131106", "乌苏",
			// "塔城", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131107", "沙湾",
			// "塔城", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131108", "和丰",
			// "塔城", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131201", "哈密",
			// "哈密", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131203", "巴里坤",
			// "哈密", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131204", "伊吾",
			// "哈密", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131301", "和田",
			// "和田", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131302", "皮山",
			// "和田", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131303", "策勒",
			// "和田", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131304", "墨玉",
			// "和田", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131305", "洛浦",
			// "和田", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131306", "民丰",
			// "和田", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131307", "于田",
			// "和田", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131401", "阿勒泰",
			// "阿勒泰", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131402", "哈巴河",
			// "阿勒泰", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131405", "吉木乃",
			// "阿勒泰", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131406", "布尔津",
			// "阿勒泰", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131407", "福海",
			// "阿勒泰", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131408", "富蕴",
			// "阿勒泰", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131409", "青河",
			// "阿勒泰", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131501", "阿图什",
			// "克州", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131502", "乌恰",
			// "克州", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131503", "阿克陶",
			// "克州", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131504", "阿合奇",
			// "克州", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131601", "博乐",
			// "博尔塔拉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131602", "温泉",
			// "博尔塔拉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131603", "精河",
			// "博尔塔拉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131606", "阿拉山口",
			// "博尔塔拉", "新疆"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140101", "拉萨",
			// "拉萨", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140102", "当雄",
			// "拉萨", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140103", "尼木",
			// "拉萨", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140104", "林周",
			// "拉萨", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140105", "堆龙德庆",
			// "拉萨", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140106", "曲水",
			// "拉萨", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140107", "达孜",
			// "拉萨", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140108", "墨竹工卡",
			// "拉萨", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140201", "日喀则",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140202", "拉孜",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140203", "南木林",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140204", "聂拉木",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140205", "定日",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140206", "江孜",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140207", "帕里",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140208", "仲巴",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140209", "萨嘎",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140210", "吉隆",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140211", "昂仁",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140212", "定结",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140213", "萨迦",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140214", "谢通门",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140216", "岗巴",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140217", "白朗",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140218", "亚东",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140219", "康马",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140220", "仁布",
			// "日喀则", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140301", "山南",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140302", "贡嘎",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140303", "札囊",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140304", "加查",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140305", "浪卡子",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140306", "错那",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140307", "隆子",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140308", "泽当",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140309", "乃东",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140310", "桑日",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140311", "洛扎",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140312", "措美",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140313", "琼结",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140314", "曲松",
			// "山南", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140401", "林芝",
			// "林芝", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140402", "波密",
			// "林芝", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140403", "米林",
			// "林芝", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140404", "察隅",
			// "林芝", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140405", "工布江达",
			// "林芝", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140406", "朗县",
			// "林芝", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140407", "墨脱",
			// "林芝", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140501", "昌都",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140502", "丁青",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140503", "边坝",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140504", "洛隆",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140505", "左贡",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140506", "芒康",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140507", "类乌齐",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140508", "八宿",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140509", "江达",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140510", "察雅",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140511", "贡觉",
			// "昌都", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140601", "那曲",
			// "那曲", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140602", "尼玛",
			// "那曲", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140603", "嘉黎",
			// "那曲", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140604", "班戈",
			// "那曲", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140605", "安多",
			// "那曲", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140606", "索县",
			// "那曲", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140607", "聂荣",
			// "那曲", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140608", "巴青",
			// "那曲", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140609", "比如",
			// "那曲", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140701", "阿里",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140702", "改则",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140703", "申扎",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140704", "狮泉河",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140705", "普兰",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140706", "札达",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140707", "噶尔",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140708", "日土",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140709", "革吉",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140710", "措勤",
			// "阿里", "西藏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150101", "西宁",
			// "西宁", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150102", "大通",
			// "西宁", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150103", "湟源",
			// "西宁", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150104", "湟中",
			// "西宁", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150201", "海东",
			// "海东", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150202", "乐都",
			// "海东", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150203", "民和",
			// "海东", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150204", "互助",
			// "海东", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150205", "化隆",
			// "海东", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150206", "循化",
			// "海东", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150207", "冷湖",
			// "海东", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150208", "平安",
			// "海东", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150301", "黄南",
			// "黄南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150302", "尖扎",
			// "黄南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150303", "泽库",
			// "黄南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150304", "河南",
			// "黄南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150305", "同仁",
			// "黄南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150401", "海南",
			// "海南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150404", "贵德",
			// "海南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150406", "兴海",
			// "海南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150407", "贵南",
			// "海南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150408", "同德",
			// "海南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150409", "共和",
			// "海南", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150501", "果洛",
			// "果洛", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150502", "班玛",
			// "果洛", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150503", "甘德",
			// "果洛", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150504", "达日",
			// "果洛", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150505", "久治",
			// "果洛", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150506", "玛多",
			// "果洛", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150507", "多县",
			// "果洛", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150508", "玛沁",
			// "果洛", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150601", "玉树",
			// "玉树", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150602", "称多",
			// "玉树", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150603", "治多",
			// "玉树", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150604", "杂多",
			// "玉树", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150605", "囊谦",
			// "玉树", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150606", "曲麻莱",
			// "玉树", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150701", "海西",
			// "海西", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150708", "天峻",
			// "海西", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150709", "乌兰",
			// "海西", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150712", "茫崖",
			// "海西", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150713", "大柴旦",
			// "海西", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150716", "德令哈",
			// "海西", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150801", "海北",
			// "海北", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150802", "门源",
			// "海北", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150803", "祁连",
			// "海北", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150804", "海晏",
			// "海北", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150806", "刚察",
			// "海北", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150901", "格尔木",
			// "格尔木", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150902", "都兰",
			// "格尔木", "青海"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160101", "兰州",
			// "兰州", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160102", "皋兰",
			// "兰州", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160103", "永登",
			// "兰州", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160104", "榆中",
			// "兰州", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160201", "定西",
			// "定西", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160202", "通渭",
			// "定西", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160203", "陇西",
			// "定西", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160204", "渭源",
			// "定西", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160205", "临洮",
			// "定西", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160206", "漳县",
			// "定西", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160207", "岷县",
			// "定西", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160208", "安定",
			// "定西", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160301", "平凉",
			// "平凉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160302", "泾川",
			// "平凉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160303", "灵台",
			// "平凉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160304", "崇信",
			// "平凉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160305", "华亭",
			// "平凉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160306", "庄浪",
			// "平凉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160307", "静宁",
			// "平凉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160308", "崆峒",
			// "平凉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160401", "庆阳",
			// "庆阳", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160402", "西峰",
			// "庆阳", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160403", "环县",
			// "庆阳", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160404", "华池",
			// "庆阳", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160405", "合水",
			// "庆阳", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160406", "正宁",
			// "庆阳", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160407", "宁县",
			// "庆阳", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160408", "镇原",
			// "庆阳", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160409", "庆城",
			// "庆阳", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160501", "武威",
			// "武威", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160502", "民勤",
			// "武威", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160503", "古浪",
			// "武威", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160505", "天祝",
			// "武威", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160601", "金昌",
			// "金昌", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160602", "永昌",
			// "金昌", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160701", "张掖",
			// "张掖", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160702", "肃南",
			// "张掖", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160703", "民乐",
			// "张掖", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160704", "临泽",
			// "张掖", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160705", "高台",
			// "张掖", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160706", "山丹",
			// "张掖", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160801", "酒泉",
			// "酒泉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160803", "金塔",
			// "酒泉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160804", "阿克塞",
			// "酒泉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160805", "瓜州",
			// "酒泉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160806", "肃北",
			// "酒泉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160807", "玉门",
			// "酒泉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160808", "敦煌",
			// "酒泉", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160901", "天水",
			// "天水", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160903", "清水",
			// "天水", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160904", "秦安",
			// "天水", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160905", "甘谷",
			// "天水", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160906", "武山",
			// "天水", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160907", "张家川",
			// "天水", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160908", "麦积",
			// "天水", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161001", "武都",
			// "陇南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161002", "成县",
			// "陇南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161003", "文县",
			// "陇南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161004", "宕昌",
			// "陇南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161005", "康县",
			// "陇南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161006", "西和",
			// "陇南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161007", "礼县",
			// "陇南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161008", "徽县",
			// "陇南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161009", "两当",
			// "陇南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161101", "临夏",
			// "临夏", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161102", "康乐",
			// "临夏", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161103", "永靖",
			// "临夏", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161104", "广河",
			// "临夏", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161105", "和政",
			// "临夏", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161106", "东乡",
			// "临夏", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161107", "积石山",
			// "临夏", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161201", "合作",
			// "甘南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161202", "临潭",
			// "甘南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161203", "卓尼",
			// "甘南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161204", "舟曲",
			// "甘南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161205", "迭部",
			// "甘南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161206", "玛曲",
			// "甘南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161207", "碌曲",
			// "甘南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161208", "夏河",
			// "甘南", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161301", "白银",
			// "白银", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161302", "靖远",
			// "白银", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161303", "会宁",
			// "白银", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161304", "平川",
			// "白银", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161305", "景泰",
			// "白银", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161401", "嘉峪关",
			// "嘉峪关", "甘肃"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170101", "银川",
			// "银川", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170102", "永宁",
			// "银川", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170103", "灵武",
			// "银川", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170104", "贺兰",
			// "银川", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170201", "石嘴山",
			// "石嘴山", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170202", "惠农",
			// "石嘴山", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170203", "平罗",
			// "石嘴山", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170204", "陶乐",
			// "石嘴山", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170206", "大武口",
			// "石嘴山", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170301", "吴忠",
			// "吴忠", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170302", "同心",
			// "吴忠", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170303", "盐池",
			// "吴忠", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170306", "青铜峡",
			// "吴忠", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170401", "固原",
			// "固原", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170402", "西吉",
			// "固原", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170403", "隆德",
			// "固原", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170404", "泾源",
			// "固原", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170406", "彭阳",
			// "固原", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170501", "中卫",
			// "中卫", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170502", "中宁",
			// "中卫", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170504", "海原",
			// "中卫", "宁夏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180101", "郑州",
			// "郑州", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180102", "巩义",
			// "郑州", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180103", "荥阳",
			// "郑州", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180104", "登封",
			// "郑州", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180105", "新密",
			// "郑州", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180106", "新郑",
			// "郑州", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180107", "中牟",
			// "郑州", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180108", "上街",
			// "郑州", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180201", "安阳",
			// "安阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180202", "汤阴",
			// "安阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180203", "滑县",
			// "安阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180204", "内黄",
			// "安阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180205", "林州",
			// "安阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180301", "新乡",
			// "新乡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180302", "获嘉",
			// "新乡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180303", "原阳",
			// "新乡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180304", "辉县",
			// "新乡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180305", "卫辉",
			// "新乡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180306", "延津",
			// "新乡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180307", "封丘",
			// "新乡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180308", "长垣",
			// "新乡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180401", "许昌",
			// "许昌", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180402", "鄢陵",
			// "许昌", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180403", "襄城",
			// "许昌", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180404", "长葛",
			// "许昌", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180405", "禹州",
			// "许昌", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180501", "平顶山",
			// "平顶山", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180502", "郏县",
			// "平顶山", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180503", "宝丰",
			// "平顶山", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180504", "汝州",
			// "平顶山", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180505", "叶县",
			// "平顶山", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180506", "舞钢",
			// "平顶山", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180507", "鲁山",
			// "平顶山", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180508", "石龙",
			// "平顶山", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180601", "信阳",
			// "信阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180602", "息县",
			// "信阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180603", "罗山",
			// "信阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180604", "光山",
			// "信阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180605", "新县",
			// "信阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180606", "淮滨",
			// "信阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180607", "潢川",
			// "信阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180608", "固始",
			// "信阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180609", "商城",
			// "信阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180701", "南阳",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180702", "南召",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180703", "方城",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180704", "社旗",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180705", "西峡",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180706", "内乡",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180707", "镇平",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180708", "淅川",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180709", "新野",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180710", "唐河",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180711", "邓州",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180712", "桐柏",
			// "南阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180801", "开封",
			// "开封", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180802", "杞县",
			// "开封", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180803", "尉氏",
			// "开封", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180804", "通许",
			// "开封", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180805", "兰考",
			// "开封", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180901", "洛阳",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180902", "新安",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180903", "孟津",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180904", "宜阳",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180905", "洛宁",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180906", "伊川",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180907", "嵩县",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180908", "偃师",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180909", "栾川",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180910", "汝阳",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180911", "吉利",
			// "洛阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181001", "商丘",
			// "商丘", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181003", "睢县",
			// "商丘", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181004", "民权",
			// "商丘", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181005", "虞城",
			// "商丘", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181006", "柘城",
			// "商丘", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181007", "宁陵",
			// "商丘", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181008", "夏邑",
			// "商丘", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181009", "永城",
			// "商丘", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181101", "焦作",
			// "焦作", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181102", "修武",
			// "焦作", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181103", "武陟",
			// "焦作", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181104", "沁阳",
			// "焦作", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181106", "博爱",
			// "焦作", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181107", "温县",
			// "焦作", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181108", "孟州",
			// "焦作", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181201", "鹤壁",
			// "鹤壁", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181202", "浚县",
			// "鹤壁", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181203", "淇县",
			// "鹤壁", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181301", "濮阳",
			// "濮阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181302", "台前",
			// "濮阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181303", "南乐",
			// "濮阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181304", "清丰",
			// "濮阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181305", "范县",
			// "濮阳", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181401", "周口",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181402", "扶沟",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181403", "太康",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181404", "淮阳",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181405", "西华",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181406", "商水",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181407", "项城",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181408", "郸城",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181409", "鹿邑",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181410", "沈丘",
			// "周口", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181501", "漯河",
			// "漯河", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181502", "临颍",
			// "漯河", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181503", "舞阳",
			// "漯河", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181601", "驻马店",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181602", "西平",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181603", "遂平",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181604", "上蔡",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181605", "汝南",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181606", "泌阳",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181607", "平舆",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181608", "新蔡",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181609", "确山",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181610", "正阳",
			// "驻马店", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181701", "三门峡",
			// "三门峡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181702", "灵宝",
			// "三门峡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181703", "渑池",
			// "三门峡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181704", "卢氏",
			// "三门峡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181705", "义马",
			// "三门峡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181706", "陕县",
			// "三门峡", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181801", "济源",
			// "济源", "河南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190101", "南京",
			// "南京", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190102", "溧水",
			// "南京", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190103", "高淳",
			// "南京", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190104", "江宁",
			// "南京", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190105", "六合",
			// "南京", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190106", "江浦",
			// "南京", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190107", "浦口",
			// "南京", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190201", "无锡",
			// "无锡", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190202", "江阴",
			// "无锡", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190203", "宜兴",
			// "无锡", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190204", "锡山",
			// "无锡", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190301", "镇江",
			// "镇江", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190302", "丹阳",
			// "镇江", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190303", "扬中",
			// "镇江", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190304", "句容",
			// "镇江", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190305", "丹徒",
			// "镇江", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190401", "苏州",
			// "苏州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190402", "常熟",
			// "苏州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190403", "张家港",
			// "苏州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190404", "昆山",
			// "苏州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190405", "吴中",
			// "苏州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190407", "吴江",
			// "苏州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190408", "太仓",
			// "苏州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190501", "南通",
			// "南通", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190502", "海安",
			// "南通", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190503", "如皋",
			// "南通", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190504", "如东",
			// "南通", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190507", "启东",
			// "南通", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190508", "海门",
			// "南通", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190509", "通州",
			// "南通", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190601", "扬州",
			// "扬州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190602", "宝应",
			// "扬州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190603", "仪征",
			// "扬州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190604", "高邮",
			// "扬州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190605", "江都",
			// "扬州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190606", "邗江",
			// "扬州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190701", "盐城",
			// "盐城", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190702", "响水",
			// "盐城", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190703", "滨海",
			// "盐城", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190704", "阜宁",
			// "盐城", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190705", "射阳",
			// "盐城", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190706", "建湖",
			// "盐城", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190707", "东台",
			// "盐城", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190708", "大丰",
			// "盐城", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190709", "盐都",
			// "盐城", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190801", "徐州",
			// "徐州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190802", "铜山",
			// "徐州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190803", "丰县",
			// "徐州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190804", "沛县",
			// "徐州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190805", "邳州",
			// "徐州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190806", "睢宁",
			// "徐州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190807", "新沂",
			// "徐州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190901", "淮安",
			// "淮安", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190902", "金湖",
			// "淮安", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190903", "盱眙",
			// "淮安", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190904", "洪泽",
			// "淮安", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190905", "涟水",
			// "淮安", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190906", "淮阴区",
			// "淮安", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190908", "淮安区",
			// "淮安", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191001", "连云港",
			// "连云港", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191002", "东海",
			// "连云港", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191003", "赣榆",
			// "连云港", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191004", "灌云",
			// "连云港", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191005", "灌南",
			// "连云港", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191101", "常州",
			// "常州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191102", "溧阳",
			// "常州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191103", "金坛",
			// "常州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191104", "武进",
			// "常州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191201", "泰州",
			// "泰州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191202", "兴化",
			// "泰州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191203", "泰兴",
			// "泰州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191204", "姜堰",
			// "泰州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191205", "靖江",
			// "泰州", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191301", "宿迁",
			// "宿迁", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191302", "沭阳",
			// "宿迁", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191303", "泗阳",
			// "宿迁", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191304", "泗洪",
			// "宿迁", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191305", "宿豫",
			// "宿迁", "江苏"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200101", "武汉",
			// "武汉", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200102", "蔡甸",
			// "武汉", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200103", "黄陂",
			// "武汉", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200104", "新洲",
			// "武汉", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200105", "江夏",
			// "武汉", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200106", "东西湖",
			// "武汉", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200201", "襄阳",
			// "襄阳", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200202", "襄州",
			// "襄阳", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200203", "保康",
			// "襄阳", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200204", "南漳",
			// "襄阳", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200205", "宜城",
			// "襄阳", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200206", "老河口",
			// "襄阳", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200207", "谷城",
			// "襄阳", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200208", "枣阳",
			// "襄阳", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200301", "鄂州",
			// "鄂州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200302", "梁子湖",
			// "鄂州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200401", "孝感",
			// "孝感", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200402", "安陆",
			// "孝感", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200403", "云梦",
			// "孝感", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200404", "大悟",
			// "孝感", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200405", "应城",
			// "孝感", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200406", "汉川",
			// "孝感", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200407", "孝昌",
			// "孝感", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200501", "黄冈",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200502", "红安",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200503", "麻城",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200504", "罗田",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200505", "英山",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200506", "浠水",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200507", "蕲春",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200508", "黄梅",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200509", "武穴",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200510", "团风",
			// "黄冈", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200601", "黄石",
			// "黄石", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200602", "大冶",
			// "黄石", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200603", "阳新",
			// "黄石", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200604", "铁山",
			// "黄石", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200605", "下陆",
			// "黄石", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200606", "西塞山",
			// "黄石", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200701", "咸宁",
			// "咸宁", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200702", "赤壁",
			// "咸宁", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200703", "嘉鱼",
			// "咸宁", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200704", "崇阳",
			// "咸宁", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200705", "通城",
			// "咸宁", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200706", "通山",
			// "咸宁", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200801", "荆州",
			// "荆州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200802", "江陵",
			// "荆州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200803", "公安",
			// "荆州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200804", "石首",
			// "荆州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200805", "监利",
			// "荆州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200806", "洪湖",
			// "荆州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200807", "松滋",
			// "荆州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200901", "宜昌",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200902", "远安",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200903", "秭归",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200904", "兴山",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200905", "宜昌县",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200906", "五峰",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200907", "当阳",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200908", "长阳",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200909", "宜都",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200910", "枝江",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200911", "三峡",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200912", "夷陵",
			// "宜昌", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201001", "恩施",
			// "恩施", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201002", "利川",
			// "恩施", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201003", "建始",
			// "恩施", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201004", "咸丰",
			// "恩施", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201005", "宣恩",
			// "恩施", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201006", "鹤峰",
			// "恩施", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201007", "来凤",
			// "恩施", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201008", "巴东",
			// "恩施", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201101", "十堰",
			// "十堰", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201102", "竹溪",
			// "十堰", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201103", "郧西",
			// "十堰", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201104", "郧县",
			// "十堰", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201105", "竹山",
			// "十堰", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201106", "房县",
			// "十堰", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201107", "丹江口",
			// "十堰", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201108", "茅箭",
			// "十堰", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201109", "张湾",
			// "十堰", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201201", "神农架",
			// "神农架", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201301", "随州",
			// "随州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201302", "广水",
			// "随州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201401", "荆门",
			// "荆门", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201402", "钟祥",
			// "荆门", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201403", "京山",
			// "荆门", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201404", "掇刀",
			// "荆门", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201405", "沙洋",
			// "荆门", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201406", "沙市",
			// "荆州", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201501", "天门",
			// "天门", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201601", "仙桃",
			// "仙桃", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201701", "潜江",
			// "潜江", "湖北"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210101", "杭州",
			// "杭州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210102", "萧山",
			// "杭州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210103", "桐庐",
			// "杭州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210104", "淳安",
			// "杭州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210105", "建德",
			// "杭州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210106", "余杭",
			// "杭州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210107", "临安",
			// "杭州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210108", "富阳",
			// "杭州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210201", "湖州",
			// "湖州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210202", "长兴",
			// "湖州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210203", "安吉",
			// "湖州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210204", "德清",
			// "湖州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210301", "嘉兴",
			// "嘉兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210302", "嘉善",
			// "嘉兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210303", "海宁",
			// "嘉兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210304", "桐乡",
			// "嘉兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210305", "平湖",
			// "嘉兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210306", "海盐",
			// "嘉兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210401", "宁波",
			// "宁波", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210403", "慈溪",
			// "宁波", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210404", "余姚",
			// "宁波", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210405", "奉化",
			// "宁波", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210406", "象山",
			// "宁波", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210408", "宁海",
			// "宁波", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210410", "北仑",
			// "宁波", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210411", "鄞州",
			// "宁波", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210412", "镇海",
			// "宁波", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210501", "绍兴",
			// "绍兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210502", "诸暨",
			// "绍兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210503", "上虞",
			// "绍兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210504", "新昌",
			// "绍兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210505", "嵊州",
			// "绍兴", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210601", "台州",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210603", "玉环",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210604", "三门",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210605", "天台",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210606", "仙居",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210607", "温岭",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210609", "洪家",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210610", "临海",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210611", "椒江",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210612", "黄岩",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210613", "路桥",
			// "台州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210701", "温州",
			// "温州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210702", "泰顺",
			// "温州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210703", "文成",
			// "温州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210704", "平阳",
			// "温州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210705", "瑞安",
			// "温州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210706", "洞头",
			// "温州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210707", "乐清",
			// "温州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210708", "永嘉",
			// "温州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210709", "苍南",
			// "温州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210801", "丽水",
			// "丽水", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210802", "遂昌",
			// "丽水", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210803", "龙泉",
			// "丽水", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210804", "缙云",
			// "丽水", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210805", "青田",
			// "丽水", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210806", "云和",
			// "丽水", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210807", "庆元",
			// "丽水", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210808", "松阳",
			// "丽水", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210809", "景宁",
			// "丽水", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210901", "金华",
			// "金华", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210902", "浦江",
			// "金华", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210903", "兰溪",
			// "金华", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210904", "义乌",
			// "金华", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210905", "东阳",
			// "金华", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210906", "武义",
			// "金华", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210907", "永康",
			// "金华", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210908", "磐安",
			// "金华", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211001", "衢州",
			// "衢州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211002", "常山",
			// "衢州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211003", "开化",
			// "衢州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211004", "龙游",
			// "衢州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211005", "江山",
			// "衢州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211006", "衢江",
			// "衢州", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211101", "舟山",
			// "舟山", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211102", "嵊泗",
			// "舟山", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211104", "岱山",
			// "舟山", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211105", "普陀",
			// "舟山", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211106", "定海",
			// "舟山", "浙江"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220101", "合肥",
			// "合肥", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220102", "长丰",
			// "合肥", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220103", "肥东",
			// "合肥", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220104", "肥西",
			// "合肥", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220201", "蚌埠",
			// "蚌埠", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220202", "怀远",
			// "蚌埠", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220203", "固镇",
			// "蚌埠", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220204", "五河",
			// "蚌埠", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220301", "芜湖",
			// "芜湖", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220302", "繁昌",
			// "芜湖", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220303", "芜湖县",
			// "芜湖", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220304", "南陵",
			// "芜湖", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220401", "淮南",
			// "淮南", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220402", "凤台",
			// "淮南", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220403", "潘集",
			// "淮南", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220501", "马鞍山",
			// "马鞍山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220502", "当涂",
			// "马鞍山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220601", "安庆",
			// "安庆", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220602", "枞阳",
			// "安庆", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220603", "太湖",
			// "安庆", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220604", "潜山",
			// "安庆", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220605", "怀宁",
			// "安庆", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220606", "宿松",
			// "安庆", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220607", "望江",
			// "安庆", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220608", "岳西",
			// "安庆", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220609", "桐城",
			// "安庆", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220701", "宿州",
			// "宿州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220702", "砀山",
			// "宿州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220703", "灵璧",
			// "宿州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220704", "泗县",
			// "宿州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220705", "萧县",
			// "宿州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220801", "阜阳",
			// "阜阳", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220802", "阜南",
			// "阜阳", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220803", "颍上",
			// "阜阳", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220804", "临泉",
			// "阜阳", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220805", "界首",
			// "阜阳", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220806", "太和",
			// "阜阳", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220901", "亳州",
			// "亳州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220902", "涡阳",
			// "亳州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220903", "利辛",
			// "亳州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220904", "蒙城",
			// "亳州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221001", "黄山",
			// "黄山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221002", "黄山区",
			// "黄山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221003", "屯溪",
			// "黄山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221004", "祁门",
			// "黄山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221005", "黟县",
			// "黄山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221006", "歙县",
			// "黄山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221007", "休宁",
			// "黄山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221008", "黄山风景区",
			// "黄山", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221101", "滁州",
			// "滁州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221102", "凤阳",
			// "滁州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221103", "明光",
			// "滁州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221104", "定远",
			// "滁州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221105", "全椒",
			// "滁州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221106", "来安",
			// "滁州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221107", "天长",
			// "滁州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221201", "淮北",
			// "淮北", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221202", "濉溪",
			// "淮北", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221301", "铜陵",
			// "铜陵", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221401", "宣城",
			// "宣城", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221402", "泾县",
			// "宣城", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221403", "旌德",
			// "宣城", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221404", "宁国",
			// "宣城", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221405", "绩溪",
			// "宣城", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221406", "广德",
			// "宣城", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221407", "郎溪",
			// "宣城", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221501", "六安",
			// "六安", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221502", "霍邱",
			// "六安", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221503", "寿县",
			// "六安", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221505", "金寨",
			// "六安", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221506", "霍山",
			// "六安", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221507", "舒城",
			// "六安", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221601", "巢湖",
			// "巢湖", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221602", "庐江",
			// "巢湖", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221603", "无为",
			// "巢湖", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221604", "含山",
			// "巢湖", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221605", "和县",
			// "巢湖", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221701", "池州",
			// "池州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221702", "东至",
			// "池州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221703", "青阳",
			// "池州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221704", "九华山",
			// "池州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221705", "石台",
			// "池州", "安徽"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230101", "福州",
			// "福州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230102", "闽清",
			// "福州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230103", "闽侯",
			// "福州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230104", "罗源",
			// "福州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230105", "连江",
			// "福州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230107", "永泰",
			// "福州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230108", "平潭",
			// "福州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230110", "长乐",
			// "福州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230111", "福清",
			// "福州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230201", "厦门",
			// "厦门", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230202", "同安",
			// "厦门", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230301", "宁德",
			// "宁德", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230302", "古田",
			// "宁德", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230303", "霞浦",
			// "宁德", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230304", "寿宁",
			// "宁德", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230305", "周宁",
			// "宁德", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230306", "福安",
			// "宁德", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230307", "柘荣",
			// "宁德", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230308", "福鼎",
			// "宁德", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230309", "屏南",
			// "宁德", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230401", "莆田",
			// "莆田", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230402", "仙游",
			// "莆田", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230403", "秀屿港",
			// "莆田", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230404", "涵江",
			// "莆田", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230405", "秀屿",
			// "莆田", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230406", "荔城",
			// "莆田", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230407", "城厢",
			// "莆田", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230501", "泉州",
			// "泉州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230502", "安溪",
			// "泉州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230504", "永春",
			// "泉州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230505", "德化",
			// "泉州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230506", "南安",
			// "泉州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230507", "崇武",
			// "泉州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230508", "惠安",
			// "泉州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230509", "晋江",
			// "泉州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230510", "石狮",
			// "泉州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230601", "漳州",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230602", "长泰",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230603", "南靖",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230604", "平和",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230605", "龙海",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230606", "漳浦",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230607", "诏安",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230608", "东山",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230609", "云霄",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230610", "华安",
			// "漳州", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230701", "龙岩",
			// "龙岩", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230702", "长汀",
			// "龙岩", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230703", "连城",
			// "龙岩", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230704", "武平",
			// "龙岩", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230705", "上杭",
			// "龙岩", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230706", "永定",
			// "龙岩", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230707", "漳平",
			// "龙岩", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230801", "三明",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230802", "宁化",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230803", "清流",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230804", "泰宁",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230805", "将乐",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230806", "建宁",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230807", "明溪",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230808", "沙县",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230809", "尤溪",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230810", "永安",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230811", "大田",
			// "三明", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230901", "南平",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230902", "顺昌",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230903", "光泽",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230904", "邵武",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230905", "武夷山",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230906", "浦城",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230907", "建阳",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230908", "松溪",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230909", "政和",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230910", "建瓯",
			// "南平", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101231001", "钓鱼岛",
			// "钓鱼岛", "福建"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240101", "南昌",
			// "南昌", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240102", "新建",
			// "南昌", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240103", "南昌县",
			// "南昌", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240104", "安义",
			// "南昌", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240105", "进贤",
			// "南昌", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240106", "莲塘",
			// "南昌", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240201", "九江",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240202", "瑞昌",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240203", "庐山",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240204", "武宁",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240205", "德安",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240206", "永修",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240207", "湖口",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240208", "彭泽",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240209", "星子",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240210", "都昌",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240212", "修水",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240213", "澎泽",
			// "九江", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240301", "上饶",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240302", "鄱阳",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240303", "婺源",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240305", "余干",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240306", "万年",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240307", "德兴",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240308", "上饶县",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240309", "弋阳",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240310", "横峰",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240311", "铅山",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240312", "玉山",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240313", "广丰",
			// "上饶", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240401", "抚州",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240402", "广昌",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240403", "乐安",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240404", "崇仁",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240405", "金溪",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240406", "资溪",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240407", "宜黄",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240408", "南城",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240409", "南丰",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240410", "黎川",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240411", "东乡",
			// "抚州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240501", "宜春",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240502", "铜鼓",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240503", "宜丰",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240504", "万载",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240505", "上高",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240506", "靖安",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240507", "奉新",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240508", "高安",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240509", "樟树",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240510", "丰城",
			// "宜春", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240601", "吉安",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240602", "吉安县",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240603", "吉水",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240604", "新干",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240605", "峡江",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240606", "永丰",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240607", "永新",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240608", "井冈山",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240609", "万安",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240610", "遂川",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240611", "泰和",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240612", "安福",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240613", "宁冈",
			// "吉安", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240701", "赣州",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240702", "崇义",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240703", "上犹",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240704", "南康",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240705", "大余",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240706", "信丰",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240707", "宁都",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240708", "石城",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240709", "瑞金",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240710", "于都",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240711", "会昌",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240712", "安远",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240713", "全南",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240714", "龙南",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240715", "定南",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240716", "寻乌",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240717", "兴国",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240718", "赣县",
			// "赣州", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240801", "景德镇",
			// "景德镇", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240802", "乐平",
			// "景德镇", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240803", "浮梁",
			// "景德镇", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240901", "萍乡",
			// "萍乡", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240902", "莲花",
			// "萍乡", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240903", "上栗",
			// "萍乡", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240904", "安源",
			// "萍乡", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240905", "芦溪",
			// "萍乡", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240906", "湘东",
			// "萍乡", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241001", "新余",
			// "新余", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241002", "分宜",
			// "新余", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241101", "鹰潭",
			// "鹰潭", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241102", "余江",
			// "鹰潭", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241103", "贵溪",
			// "鹰潭", "江西"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250101", "长沙",
			// "长沙", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250102", "宁乡",
			// "长沙", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250103", "浏阳",
			// "长沙", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250104", "马坡岭",
			// "长沙", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250105", "望城",
			// "长沙", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250201", "湘潭",
			// "湘潭", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250202", "韶山",
			// "湘潭", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250203", "湘乡",
			// "湘潭", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250301", "株洲",
			// "株洲", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250302", "攸县",
			// "株洲", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250303", "醴陵",
			// "株洲", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250305", "茶陵",
			// "株洲", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250306", "炎陵",
			// "株洲", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250401", "衡阳",
			// "衡阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250402", "衡山",
			// "衡阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250403", "衡东",
			// "衡阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250404", "祁东",
			// "衡阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250405", "衡阳县",
			// "衡阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250406", "常宁",
			// "衡阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250407", "衡南",
			// "衡阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250408", "耒阳",
			// "衡阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250409", "南岳",
			// "衡阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250501", "郴州",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250502", "桂阳",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250503", "嘉禾",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250504", "宜章",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250505", "临武",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250507", "资兴",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250508", "汝城",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250509", "安仁",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250510", "永兴",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250511", "桂东",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250512", "苏仙",
			// "郴州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250601", "常德",
			// "常德", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250602", "安乡",
			// "常德", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250603", "桃源",
			// "常德", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250604", "汉寿",
			// "常德", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250605", "澧县",
			// "常德", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250606", "临澧",
			// "常德", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250607", "石门",
			// "常德", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250608", "津市",
			// "常德", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250700", "益阳",
			// "益阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250701", "赫山区",
			// "益阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250702", "南县",
			// "益阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250703", "桃江",
			// "益阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250704", "安化",
			// "益阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250705", "沅江",
			// "益阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250801", "娄底",
			// "娄底", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250802", "双峰",
			// "娄底", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250803", "冷水江",
			// "娄底", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250805", "新化",
			// "娄底", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250806", "涟源",
			// "娄底", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250901", "邵阳",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250902", "隆回",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250903", "洞口",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250904", "新邵",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250905", "邵东",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250906", "绥宁",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250907", "新宁",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250908", "武冈",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250909", "城步",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250910", "邵阳县",
			// "邵阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251001", "岳阳",
			// "岳阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251002", "华容",
			// "岳阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251003", "湘阴",
			// "岳阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251004", "汨罗",
			// "岳阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251005", "平江",
			// "岳阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251006", "临湘",
			// "岳阳", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251101", "张家界",
			// "张家界", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251102", "桑植",
			// "张家界", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251103", "慈利",
			// "张家界", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251104", "武陵源",
			// "张家界", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251201", "怀化",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251203", "沅陵",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251204", "辰溪",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251205", "靖州",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251206", "会同",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251207", "通道",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251208", "麻阳",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251209", "新晃",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251210", "芷江",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251211", "溆浦",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251212", "中方",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251213", "洪江",
			// "怀化", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251401", "永州",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251402", "祁阳",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251403", "东安",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251404", "双牌",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251405", "道县",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251406", "宁远",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251407", "江永",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251408", "蓝山",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251409", "新田",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251410", "江华",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251411", "冷水滩",
			// "永州", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251501", "吉首",
			// "湘西", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251502", "保靖",
			// "湘西", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251503", "永顺",
			// "湘西", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251504", "古丈",
			// "湘西", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251505", "凤凰",
			// "湘西", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251506", "泸溪",
			// "湘西", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251507", "龙山",
			// "湘西", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251508", "花垣",
			// "湘西", "湖南"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260101", "贵阳",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260102", "白云",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260103", "花溪",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260104", "乌当",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260105", "息烽",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260106", "开阳",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260107", "修文",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260108", "清镇",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260109", "小河",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260110", "云岩",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260111", "南明",
			// "贵阳", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260201", "遵义",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260202", "遵义县",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260203", "仁怀",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260204", "绥阳",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260205", "湄潭",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260206", "凤冈",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260207", "桐梓",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260208", "赤水",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260209", "习水",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260210", "道真",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260211", "正安",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260212", "务川",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260213", "余庆",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260214", "汇川",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260215", "红花岗",
			// "遵义", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260301", "安顺",
			// "安顺", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260302", "普定",
			// "安顺", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260303", "镇宁",
			// "安顺", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260304", "平坝",
			// "安顺", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260305", "紫云",
			// "安顺", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260306", "关岭",
			// "安顺", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260401", "都匀",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260402", "贵定",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260403", "瓮安",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260404", "长顺",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260405", "福泉",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260406", "惠水",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260407", "龙里",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260408", "罗甸",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260409", "平塘",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260410", "独山",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260411", "三都",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260412", "荔波",
			// "黔南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260501", "凯里",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260502", "岑巩",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260503", "施秉",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260504", "镇远",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260505", "黄平",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260507", "麻江",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260508", "丹寨",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260509", "三穗",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260510", "台江",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260511", "剑河",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260512", "雷山",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260513", "黎平",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260514", "天柱",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260515", "锦屏",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260516", "榕江",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260517", "从江",
			// "黔东南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260601", "铜仁",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260602", "江口",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260603", "玉屏",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260604", "万山",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260605", "思南",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260607", "印江",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260608", "石阡",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260609", "沿河",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260610", "德江",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260611", "松桃",
			// "铜仁", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260701", "毕节",
			// "毕节", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260702", "赫章",
			// "毕节", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260703", "金沙",
			// "毕节", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260704", "威宁",
			// "毕节", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260705", "大方",
			// "毕节", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260706", "纳雍",
			// "毕节", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260707", "织金",
			// "毕节", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260708", "黔西",
			// "毕节", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260801", "水城",
			// "六盘水", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260802", "六枝",
			// "六盘水", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260804", "盘县",
			// "六盘水", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260901", "兴义",
			// "黔西南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260902", "晴隆",
			// "黔西南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260903", "兴仁",
			// "黔西南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260904", "贞丰",
			// "黔西南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260905", "望谟",
			// "黔西南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260907", "安龙",
			// "黔西南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260908", "册亨",
			// "黔西南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260909", "普安",
			// "黔西南", "贵州"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270101", "成都",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270102", "龙泉驿",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270103", "新都",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270104", "温江",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270105", "金堂",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270106", "双流",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270107", "郫县",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270108", "大邑",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270109", "蒲江",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270110", "新津",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270111", "都江堰",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270112", "彭州",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270113", "邛崃",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270114", "崇州",
			// "成都", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270201", "攀枝花",
			// "攀枝花", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270202", "仁和",
			// "攀枝花", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270203", "米易",
			// "攀枝花", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270204", "盐边",
			// "攀枝花", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270301", "自贡",
			// "自贡", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270302", "富顺",
			// "自贡", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270303", "荣县",
			// "自贡", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270401", "绵阳",
			// "绵阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270402", "三台",
			// "绵阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270403", "盐亭",
			// "绵阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270404", "安县",
			// "绵阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270405", "梓潼",
			// "绵阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270406", "北川",
			// "绵阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270407", "平武",
			// "绵阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270408", "江油",
			// "绵阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270501", "南充",
			// "南充", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270502", "南部",
			// "南充", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270503", "营山",
			// "南充", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270504", "蓬安",
			// "南充", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270505", "仪陇",
			// "南充", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270506", "西充",
			// "南充", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270507", "阆中",
			// "南充", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270601", "达州",
			// "达州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270602", "宣汉",
			// "达州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270603", "开江",
			// "达州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270604", "大竹",
			// "达州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270605", "渠县",
			// "达州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270606", "万源",
			// "达州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270607", "通川",
			// "达州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270608", "达县",
			// "达州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270701", "遂宁",
			// "遂宁", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270702", "蓬溪",
			// "遂宁", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270703", "射洪",
			// "遂宁", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270801", "广安",
			// "广安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270802", "岳池",
			// "广安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270803", "武胜",
			// "广安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270804", "邻水",
			// "广安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270805", "华蓥",
			// "广安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270901", "巴中",
			// "巴中", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270902", "通江",
			// "巴中", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270903", "南江",
			// "巴中", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270904", "平昌",
			// "巴中", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271001", "泸州",
			// "泸州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271003", "泸县",
			// "泸州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271004", "合江",
			// "泸州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271005", "叙永",
			// "泸州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271006", "古蔺",
			// "泸州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271007", "纳溪",
			// "泸州", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271101", "宜宾",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271103", "宜宾县",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271104", "南溪",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271105", "江安",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271106", "长宁",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271107", "高县",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271108", "珙县",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271109", "筠连",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271110", "兴文",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271111", "屏山",
			// "宜宾", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271201", "内江",
			// "内江", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271202", "东兴",
			// "内江", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271203", "威远",
			// "内江", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271204", "资中",
			// "内江", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271205", "隆昌",
			// "内江", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271301", "资阳",
			// "资阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271302", "安岳",
			// "资阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271303", "乐至",
			// "资阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271304", "简阳",
			// "资阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271401", "乐山",
			// "乐山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271402", "犍为",
			// "乐山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271403", "井研",
			// "乐山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271404", "夹江",
			// "乐山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271405", "沐川",
			// "乐山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271406", "峨边",
			// "乐山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271407", "马边",
			// "乐山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271408", "峨眉",
			// "乐山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271409", "峨眉山",
			// "乐山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271501", "眉山",
			// "眉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271502", "仁寿",
			// "眉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271503", "彭山",
			// "眉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271504", "洪雅",
			// "眉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271505", "丹棱",
			// "眉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271506", "青神",
			// "眉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271601", "凉山",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271603", "木里",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271604", "盐源",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271605", "德昌",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271606", "会理",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271607", "会东",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271608", "宁南",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271609", "普格",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271610", "西昌",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271611", "金阳",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271612", "昭觉",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271613", "喜德",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271614", "冕宁",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271615", "越西",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271616", "甘洛",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271617", "雷波",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271618", "美姑",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271619", "布拖",
			// "凉山", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271701", "雅安",
			// "雅安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271702", "名山",
			// "雅安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271703", "荥经",
			// "雅安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271704", "汉源",
			// "雅安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271705", "石棉",
			// "雅安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271706", "天全",
			// "雅安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271707", "芦山",
			// "雅安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271708", "宝兴",
			// "雅安", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271801", "甘孜",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271802", "康定",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271803", "泸定",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271804", "丹巴",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271805", "九龙",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271806", "雅江",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271807", "道孚",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271808", "炉霍",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271809", "新龙",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271810", "德格",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271811", "白玉",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271812", "石渠",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271813", "色达",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271814", "理塘",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271815", "巴塘",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271816", "乡城",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271817", "稻城",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271818", "得荣",
			// "甘孜", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271901", "阿坝",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271902", "汶川",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271903", "理县",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271904", "茂县",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271905", "松潘",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271906", "九寨沟",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271907", "金川",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271908", "小金",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271909", "黑水",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271910", "马尔康",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271911", "壤塘",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271912", "若尔盖",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271913", "红原",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271914", "南坪",
			// "阿坝", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272001", "德阳",
			// "德阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272002", "中江",
			// "德阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272003", "广汉",
			// "德阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272004", "什邡",
			// "德阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272005", "绵竹",
			// "德阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272006", "罗江",
			// "德阳", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272101", "广元",
			// "广元", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272102", "旺苍",
			// "广元", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272103", "青川",
			// "广元", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272104", "剑阁",
			// "广元", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272105", "苍溪",
			// "广元", "四川"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280101", "广州",
			// "广州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280102", "番禺",
			// "广州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280103", "从化",
			// "广州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280104", "增城",
			// "广州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280105", "花都",
			// "广州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280201", "韶关",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280202", "乳源",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280203", "始兴",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280204", "翁源",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280205", "乐昌",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280206", "仁化",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280207", "南雄",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280208", "新丰",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280209", "曲江",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280210", "浈江",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280211", "武江",
			// "韶关", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280301", "惠州",
			// "惠州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280302", "博罗",
			// "惠州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280303", "惠阳",
			// "惠州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280304", "惠东",
			// "惠州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280305", "龙门",
			// "惠州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280401", "梅州",
			// "梅州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280402", "兴宁",
			// "梅州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280403", "蕉岭",
			// "梅州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280404", "大埔",
			// "梅州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280406", "丰顺",
			// "梅州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280407", "平远",
			// "梅州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280408", "五华",
			// "梅州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280409", "梅县",
			// "梅州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280501", "汕头",
			// "汕头", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280502", "潮阳",
			// "汕头", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280503", "澄海",
			// "汕头", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280504", "南澳",
			// "汕头", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280601", "深圳",
			// "深圳", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280701", "珠海",
			// "珠海", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280702", "斗门",
			// "珠海", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280703", "金湾",
			// "珠海", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280800", "佛山",
			// "佛山", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280801", "顺德",
			// "佛山", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280802", "三水",
			// "佛山", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280803", "南海",
			// "佛山", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280804", "高明",
			// "佛山", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280901", "肇庆",
			// "肇庆", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280902", "广宁",
			// "肇庆", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280903", "四会",
			// "肇庆", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280905", "德庆",
			// "肇庆", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280906", "怀集",
			// "肇庆", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280907", "封开",
			// "肇庆", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280908", "高要",
			// "肇庆", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281001", "湛江",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281002", "吴川",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281003", "雷州",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281004", "徐闻",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281005", "廉江",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281006", "赤坎",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281007", "遂溪",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281008", "坡头",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281009", "霞山",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281010", "麻章",
			// "湛江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281101", "江门",
			// "江门", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281103", "开平",
			// "江门", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281104", "新会",
			// "江门", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281105", "恩平",
			// "江门", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281106", "台山",
			// "江门", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281107", "蓬江",
			// "江门", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281108", "鹤山",
			// "江门", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281109", "江海",
			// "江门", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281201", "河源",
			// "河源", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281202", "紫金",
			// "河源", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281203", "连平",
			// "河源", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281204", "和平",
			// "河源", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281205", "龙川",
			// "河源", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281206", "东源",
			// "河源", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281301", "清远",
			// "清远", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281302", "连南",
			// "清远", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281303", "连州",
			// "清远", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281304", "连山",
			// "清远", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281305", "阳山",
			// "清远", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281306", "佛冈",
			// "清远", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281307", "英德",
			// "清远", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281308", "清新",
			// "清远", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281401", "云浮",
			// "云浮", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281402", "罗定",
			// "云浮", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281403", "新兴",
			// "云浮", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281404", "郁南",
			// "云浮", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281406", "云安",
			// "云浮", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281501", "潮州",
			// "潮州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281502", "饶平",
			// "潮州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281503", "潮安",
			// "潮州", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281601", "东莞",
			// "东莞", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281701", "中山",
			// "中山", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281801", "阳江",
			// "阳江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281802", "阳春",
			// "阳江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281803", "阳东",
			// "阳江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281804", "阳西",
			// "阳江", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281901", "揭阳",
			// "揭阳", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281902", "揭西",
			// "揭阳", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281903", "普宁",
			// "揭阳", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281904", "惠来",
			// "揭阳", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281905", "揭东",
			// "揭阳", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282001", "茂名",
			// "茂名", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282002", "高州",
			// "茂名", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282003", "化州",
			// "茂名", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282004", "电白",
			// "茂名", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282005", "信宜",
			// "茂名", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282006", "茂港",
			// "茂名", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282101", "汕尾",
			// "汕尾", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282102", "海丰",
			// "汕尾", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282103", "陆丰",
			// "汕尾", "广东"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282104", "陆河",
			// "汕尾", "广东"));
			WeatherAreaModels.add(new WeatherAreaModel("101290101", "昆明", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290103", "东川", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290104", "寻甸", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290105", "晋宁", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290106", "宜良", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290107", "石林", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290108", "呈贡", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290109", "富民", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290110", "嵩明", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290111", "禄劝", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290112", "安宁", "昆明",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290113", "太华山",
					"昆明", "云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290201", "大理", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290202", "云龙", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290203", "漾濞", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290204", "永平", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290205", "宾川", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290206", "弥渡", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290207", "祥云", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290208", "巍山", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290209", "剑川", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290210", "洱源", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290211", "鹤庆", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290212", "南涧", "大理",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290301", "红河", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290302", "石屏", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290303", "建水", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290304", "弥勒", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290305", "元阳", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290306", "绿春", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290307", "开远", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290308", "个旧", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290309", "蒙自", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290310", "屏边", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290311", "泸西", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290312", "金平", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290313", "河口", "红河",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290401", "曲靖", "曲靖",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290402", "沾益", "曲靖",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290403", "陆良", "曲靖",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290404", "富源", "曲靖",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290405", "马龙", "曲靖",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290406", "师宗", "曲靖",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290407", "罗平", "曲靖",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290408", "会泽", "曲靖",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290409", "宣威", "曲靖",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290501", "保山", "保山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290503", "龙陵", "保山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290504", "施甸", "保山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290505", "昌宁", "保山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290506", "腾冲", "保山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290601", "文山", "文山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290602", "西畴", "文山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290603", "马关", "文山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290604", "麻栗坡",
					"文山", "云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290605", "砚山", "文山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290606", "丘北", "文山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290607", "广南", "文山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290608", "富宁", "文山",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290701", "玉溪", "玉溪",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290702", "澄江", "玉溪",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290703", "江川", "玉溪",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290704", "通海", "玉溪",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290705", "华宁", "玉溪",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290706", "新平", "玉溪",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290707", "易门", "玉溪",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290708", "峨山", "玉溪",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290709", "元江", "玉溪",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290801", "楚雄", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290802", "大姚", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290803", "元谋", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290804", "姚安", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290805", "牟定", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290806", "南华", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290807", "武定", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290808", "禄丰", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290809", "双柏", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290810", "永仁", "楚雄",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290901", "普洱", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290902", "景谷", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290903", "景东", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290904", "澜沧", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290906", "墨江", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290907", "江城", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290908", "孟连", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290909", "西盟", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290911", "镇沅", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101290912", "宁洱", "普洱",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291001", "昭通", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291002", "鲁甸", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291003", "彝良", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291004", "镇雄", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291005", "威信", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291006", "巧家", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291007", "绥江", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291008", "永善", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291009", "盐津", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291010", "大关", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291011", "水富", "昭通",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291101", "临沧", "临沧",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291102", "沧源", "临沧",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291103", "耿马", "临沧",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291104", "双江", "临沧",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291105", "凤庆", "临沧",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291106", "永德", "临沧",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291107", "云县", "临沧",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291108", "镇康", "临沧",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291201", "怒江", "怒江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291203", "福贡", "怒江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291204", "兰坪", "怒江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291205", "泸水", "怒江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291206", "六库", "怒江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291207", "贡山", "怒江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291301", "香格里拉",
					"迪庆", "云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291302", "德钦", "迪庆",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291303", "维西", "迪庆",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291304", "中甸", "迪庆",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291401", "丽江", "丽江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291402", "永胜", "丽江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291403", "华坪", "丽江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291404", "宁蒗", "丽江",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291501", "德宏", "德宏",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291503", "陇川", "德宏",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291504", "盈江", "德宏",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291506", "瑞丽", "德宏",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291507", "梁河", "德宏",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291508", "潞西", "德宏",
					"云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291601", "景洪",
					"西双版纳", "云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291603", "勐海",
					"西双版纳", "云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101291605", "勐腊",
					"西双版纳", "云南"));
			WeatherAreaModels.add(new WeatherAreaModel("101300101", "南宁", "南宁",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300103", "邕宁", "南宁",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300104", "横县", "南宁",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300105", "隆安", "南宁",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300106", "马山", "南宁",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300107", "上林", "南宁",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300108", "武鸣", "南宁",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300109", "宾阳", "南宁",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300201", "崇左", "崇左",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300202", "天等", "崇左",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300203", "龙州", "崇左",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300204", "凭祥", "崇左",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300205", "大新", "崇左",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300206", "扶绥", "崇左",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300207", "宁明", "崇左",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300301", "柳州", "柳州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300302", "柳城", "柳州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300304", "鹿寨", "柳州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300305", "柳江", "柳州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300306", "融安", "柳州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300307", "融水", "柳州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300308", "三江", "柳州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300401", "来宾", "来宾",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300402", "忻城", "来宾",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300403", "金秀", "来宾",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300404", "象州", "来宾",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300405", "武宣", "来宾",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300406", "合山", "来宾",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300501", "桂林", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300503", "龙胜", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300504", "永福", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300505", "临桂", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300506", "兴安", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300507", "灵川", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300508", "全州", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300509", "灌阳", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300510", "阳朔", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300511", "恭城", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300512", "平乐", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300513", "荔浦", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300514", "资源", "桂林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300601", "梧州", "梧州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300602", "藤县", "梧州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300604", "苍梧", "梧州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300605", "蒙山", "梧州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300606", "岑溪", "梧州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300607", "长洲", "梧州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300701", "贺州", "贺州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300702", "昭平", "贺州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300703", "富川", "贺州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300704", "钟山", "贺州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300801", "贵港", "贵港",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300802", "桂平", "贵港",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300803", "平南", "贵港",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300901", "玉林", "玉林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300902", "博白", "玉林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300903", "北流", "玉林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300904", "容县", "玉林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300905", "陆川", "玉林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101300906", "兴业", "玉林",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301001", "百色", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301002", "那坡", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301003", "田阳", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301004", "德保", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301005", "靖西", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301006", "田东", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301007", "平果", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301008", "隆林", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301009", "西林", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301010", "乐业", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301011", "凌云", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301012", "田林", "百色",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301101", "钦州", "钦州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301102", "浦北", "钦州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301103", "灵山", "钦州",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301201", "河池", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301202", "天峨", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301203", "东兰", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301204", "巴马", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301205", "环江", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301206", "罗城", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301207", "宜州", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301208", "凤山", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301209", "南丹", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301210", "都安", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301211", "大化", "河池",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301301", "北海", "北海",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301302", "合浦", "北海",
					"广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301303", "涠洲岛",
					"北海", "广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301401", "防城港",
					"防城港", "广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301402", "上思",
					"防城港", "广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301403", "东兴",
					"防城港", "广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101301405", "防城",
					"防城港", "广西"));
			WeatherAreaModels.add(new WeatherAreaModel("101310101", "海口", "海口",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310201", "三亚", "三亚",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310202", "东方", "东方",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310203", "临高", "临高",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310204", "澄迈", "澄迈",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310205", "儋州", "儋州",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310206", "昌江", "昌江",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310207", "白沙", "白沙",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310208", "琼中", "琼中",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310209", "定安", "定安",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310210", "屯昌", "屯昌",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310211", "琼海", "琼海",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310212", "文昌", "文昌",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310214", "保亭", "保亭",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310215", "万宁", "万宁",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310216", "陵水", "陵水",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310217", "西沙", "西沙",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310220", "南沙", "南沙",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310221", "乐东", "乐东",
					"海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101310222", "五指山",
					"五指山", "海南"));
			WeatherAreaModels.add(new WeatherAreaModel("101320101", "香港", "香港",
					"香港"));
			WeatherAreaModels.add(new WeatherAreaModel("101320103", "新界", "香港",
					"香港"));
			WeatherAreaModels.add(new WeatherAreaModel("101330101", "澳门", "澳门",
					"澳门"));
			WeatherAreaModels.add(new WeatherAreaModel("101330102", "氹仔岛",
					"澳门", "澳门"));
			WeatherAreaModels.add(new WeatherAreaModel("101330103", "路环岛",
					"澳门", "澳门"));
			WeatherAreaModels.add(new WeatherAreaModel("101340101", "台北", "台北",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340102", "桃园", "台北",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340103", "新竹", "台北",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340104", "宜兰", "台北",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340201", "高雄", "高雄",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340202", "嘉义", "高雄",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340203", "台南", "高雄",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340204", "台东", "高雄",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340205", "屏东", "高雄",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340401", "台中", "台中",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340402", "苗栗", "台中",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340403", "彰化", "台中",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340404", "南投", "台中",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340405", "花莲", "台中",
					"台湾"));
			WeatherAreaModels.add(new WeatherAreaModel("101340406", "云林", "台中",
					"台湾"));
			return WeatherAreaModels;
		}
	}
}

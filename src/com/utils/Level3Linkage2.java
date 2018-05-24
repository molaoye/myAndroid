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
	/** ΢���ź�Ĭ�� */
	private static Typeface typefacenormal = Typeface.create("΢���ź�",
			Typeface.NORMAL);
	/** ΢���źڴ��� */
	private static Typeface typefacebold = Typeface.create("΢���ź�",
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
	 * ����
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
		title.setText("ѡ������Ԥ������");

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
	 * ��ǰ��������
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
			nowWeatherAreaModel = weatherAreaModels.get(0);//ȡ��һ������
		}

		Message msg = new Message();
		msg.what = 0;
		msg.obj = "��ǰѡ����У�id:" + nowWeatherAreaModel.areaid() + "��"
				+ nowWeatherAreaModel.areaname1() + "-"
				+ nowWeatherAreaModel.areaname2() + "-"
				+ nowWeatherAreaModel.areaname3();
		messageHandler.sendMessage(msg);
	}

	/**
	 * �������ƻ�ȡһ������
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
	 * �������ƻ�ȡ��������
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
	 * �������ƻ�ȡ��������
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
	 * ��ȡһ�������б�
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
	 * ��ʡ
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
	 * �Ƿ��Ѵ��ڸ�һ������
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
	 * ��ȡ��������
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
	 * ����
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
	 * �Ƿ��Ѵ��ڸö�������
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
	 * ��ȡ��������
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
	 * ����
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
	 * �Ƿ��Ѵ��ڸ���������
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
		 * ����ID
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
		 * һ����������
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
		 * ������������
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
		 * ������������
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
			// WeatherAreaModels.add(new WeatherAreaModel("101010100", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010200", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010300", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010400", "˳��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010500", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010600", "ͨ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010700", "��ƽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010800", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101010900", "��̨",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011000", "ʯ��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011100", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011200", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011300", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011400", "��ͷ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101011500", "ƽ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020100", "�Ϻ�",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020200", "����",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020300", "��ɽ",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020500", "�ζ�",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020600", "�ϻ�",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020700", "��ɽ",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020800", "����",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101020900", "�ɽ�",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101021000", "����",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101021100", "����",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101021200", "��һ�",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101021300", "�ֶ�",
			// "�Ϻ�", "�Ϻ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030100", "���",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030200", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030300", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030400", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030500", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030600", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030700", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030800", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101030900", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101031000", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101031100", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101031200", "���",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101031400", "����",
			// "���", "���"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040100", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040200", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040300", "�ϴ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040400", "�ϴ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040500", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040600", "��ʢ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040700", "�山",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040800", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101040900", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041000", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041100", "ǭ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041300", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041400", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041500", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041600", "�ǿ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041700", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041800", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101041900", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042000", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042100", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042200", "�潭",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042300", "��ƽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042400", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042500", "ʯ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042600", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042700", "�ٲ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042800", "ͭ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101042900", "�ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043000", "�ᶼ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043100", "��¡",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043200", "��ˮ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043300", "�뽭",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043400", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101043600", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050101", "������",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050102", "˫��",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050103", "����",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050104", "����",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050105", "����",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050106", "����",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050107", "����",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050108", "ͨ��",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050109", "����",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050110", "����",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050111", "��־",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050112", "�峣",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050113", "ľ��",
			// "������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050201", "�������",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050202", "ګ��",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050203", "����",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050204", "����",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050205", "��ԣ",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050206", "����",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050207", "��Ȫ",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050208", "��ɽ",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050209", "�˶�",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050210", "̩��",
			// "�������", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050301", "ĵ����",
			// "ĵ����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050302", "����",
			// "ĵ����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050303", "����",
			// "ĵ����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050304", "�ֿ�",
			// "ĵ����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050305", "��Һ�",
			// "ĵ����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050306", "����",
			// "ĵ����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050307", "����",
			// "ĵ����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050401", "��ľ˹",
			// "��ľ˹", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050402", "��ԭ",
			// "��ľ˹", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050403", "��Զ",
			// "��ľ˹", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050404", "�봨",
			// "��ľ˹", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050405", "����",
			// "��ľ˹", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050406", "ͬ��",
			// "��ľ˹", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050407", "����",
			// "��ľ˹", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050501", "�绯",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050502", "�ض�",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050503", "����",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050504", "����",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050505", "��ˮ",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050506", "����",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050507", "����",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050508", "���",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050509", "�찲",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050510", "����",
			// "�绯", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050601", "�ں�",
			// "�ں�", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050602", "�۽�",
			// "�ں�", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050603", "����",
			// "�ں�", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050604", "ѷ��",
			// "�ں�", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050605", "�������",
			// "�ں�", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050606", "����",
			// "�ں�", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050701", "���˰���",
			// "���˰���", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050702", "����",
			// "���˰���", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050703", "Į��",
			// "���˰���", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050704", "����",
			// "���˰���", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050705", "����",
			// "���˰���", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050706", "����",
			// "���˰���", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050708", "�Ӹ����",
			// "���˰���", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050801", "����",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050802", "������",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050803", "��Ӫ",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050804", "����",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050805", "����",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050901", "����",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050902", "�ֵ�",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050903", "����",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050904", "��Դ",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101050905", "�Ŷ�����",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051002", "��̨��",
			// "��̨��", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051003", "����",
			// "��̨��", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051101", "����",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051102", "����",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051103", "��ɽ",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051104", "����",
			// "����", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051201", "�׸�",
			// "�׸�", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051202", "���",
			// "�׸�", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051203", "�ܱ�",
			// "�׸�", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051301", "˫Ѽɽ",
			// "˫Ѽɽ", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051302", "����",
			// "˫Ѽɽ", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051303", "����",
			// "˫Ѽɽ", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051304", "�ĺ�",
			// "˫Ѽɽ", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101051305", "����",
			// "˫Ѽɽ", "������"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060102", "ũ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060103", "�»�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060104", "��̨",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060105", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060106", "˫��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060202", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060203", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060204", "�Ժ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060205", "��ʯ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060206", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060301", "�Ӽ�",
			// "�ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060302", "�ػ�",
			// "�ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060303", "��ͼ",
			// "�ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060304", "����",
			// "�ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060305", "����",
			// "�ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060307", "����",
			// "�ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060308", "����",
			// "�ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060309", "ͼ��",
			// "�ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060401", "��ƽ",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060402", "˫��",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060403", "����",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060404", "������",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060405", "��ͨ",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060501", "ͨ��",
			// "ͨ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060502", "÷�ӿ�",
			// "ͨ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060503", "����",
			// "ͨ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060504", "����",
			// "ͨ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060505", "����",
			// "ͨ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060506", "ͨ����",
			// "ͨ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060601", "�׳�",
			// "�׳�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060602", "���",
			// "�׳�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060603", "��",
			// "�׳�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060604", "����",
			// "�׳�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060605", "ͨ��",
			// "�׳�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060701", "��Դ",
			// "��Դ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060702", "����",
			// "��Դ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060703", "����",
			// "��Դ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060801", "��ԭ",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060802", "Ǭ��",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060803", "ǰ��",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060804", "����",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060805", "����",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060901", "��ɽ",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060902", "����",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060903", "�ٽ�",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060904", "����",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060905", "����",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060906", "����",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101060907", "��Դ",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070103", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070104", "��ƽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070105", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070106", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070202", "�߷���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070203", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070204", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070205", "��˳",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070206", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070207", "ׯ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070301", "��ɽ",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070302", "̨��",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070303", "���",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070304", "����",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070401", "��˳",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070402", "�±�",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070403", "��ԭ",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070404", "�µ�",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070501", "��Ϫ",
			// "��Ϫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070502", "��Ϫ��",
			// "��Ϫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070504", "����",
			// "��Ϫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070602", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070603", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070604", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070701", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070702", "�躣",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070704", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070705", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070706", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070801", "Ӫ��",
			// "Ӫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070802", "��ʯ��",
			// "Ӫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070803", "����",
			// "Ӫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070901", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101070902", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071001", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071002", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071003", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071004", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071102", "��ԭ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071103", "��ͼ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071104", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071105", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071203", "��Դ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071204", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071205", "��Ʊ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071207", "��ƽ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071301", "�̽�",
			// "�̽�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071302", "����",
			// "�̽�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071303", "��ɽ",
			// "�̽�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071401", "��«��",
			// "��«��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071402", "����",
			// "��«��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071403", "����",
			// "��«��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101071404", "�˳�",
			// "��«��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080101", "���ͺ���",
			// "���ͺ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080102", "������",
			// "���ͺ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080103", "����",
			// "���ͺ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080104", "����",
			// "���ͺ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080105", "��ˮ��",
			// "���ͺ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080106", "���н���",
			// "���ͺ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080107", "�䴨",
			// "���ͺ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080201", "��ͷ",
			// "��ͷ", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080202", "���ƶ���",
			// "��ͷ", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080203", "������",
			// "��ͷ", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080204", "������",
			// "��ͷ", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080205", "����",
			// "��ͷ", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080206", "��ï��",
			// "��ͷ", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080207", "ϣ������",
			// "��ͷ", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080301", "�ں�",
			// "�ں�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080401", "����",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080402", "׿��",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080403", "����",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080404", "�̶�",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080406", "�˺�",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080407", "����",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080408", "����ǰ��",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080409", "��������",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080410", "���Һ���",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080411", "��������",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080412", "����",
			// "�����첼", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080501", "ͨ��",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080502", "�Ხ��",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080503", "��������",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080504", "�������",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080505", "����ɽ",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080506", "��³",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080507", "����",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080508", "����",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080509", "��³��",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080510", "������",
			// "�˰���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080511", "���Ŷ��º�˶",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080601", "���",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080603", "��³��",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080604", "�ƶ���",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080605", "��������",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080606", "��������",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080607", "����",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080608", "��ʲ����",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080609", "��ţ��",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080610", "����",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080611", "������",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080612", "���ﺱ",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080613", "����",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080614", "����",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080615", "������",
			// "���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080701", "������˹",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080703", "������",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080704", "׼���",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080705", "��ǰ��",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080706", "����",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080707", "��������",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080708", "���п�",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080709", "������",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080710", "������",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080711", "�������",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080712", "������",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080713", "��ʤ",
			// "������˹", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080801", "�ٺ�",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080802", "��ԭ",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080803", "���",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080804", "��ǰ��",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080805", "����̫",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080806", "������",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080807", "�ں���",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080808", "������",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080809", "���ʱ�����",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080810", "��������",
			// "�����׶�", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080901", "���ֺ���",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080903", "��������",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080904", "���͸�",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080906", "������",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080907", "������",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080908", "���պ�",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080909", "������",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080910", "������",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080911", "̫����",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080912", "�����",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080913", "�������",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080914", "������",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080915", "����",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080916", "����ͼ",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101080917", "������",
			// "���ֹ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081000", "���ױ���",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081001", "������",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081002", "С����",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081003", "������",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081004", "Ī������",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081005", "���״���",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081006", "���¿���",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081007", "����",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081008", "������",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081009", "������",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081010", "������",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081011", "����ʯ",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081012", "������",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081014", "�������",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081015", "����",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081016", "ͼ���",
			// "���ױ���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081101", "��������",
			// "�˰���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081102", "����ɽ",
			// "�˰���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081103", "��������",
			// "�˰���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081104", "������",
			// "�˰���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081105", "������",
			// "�˰���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081106", "����",
			// "�˰���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081107", "ͻȪ",
			// "�˰���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081108", "���ֹ���",
			// "ͨ��", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081109", "����ǰ��",
			// "�˰���", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081201", "������",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081202", "������",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081203", "�����",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081204", "���Ӻ�",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081205", "����̫",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081206", "���ָ���",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081207", "ͷ����",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081208", "��Ȫ��",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081209", "ŵ����",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081210", "�Ų���",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081211", "��˹̩",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101081212", "�Ͼ�̲",
			// "��������", "���ɹ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090101", "ʯ��ׯ",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090102", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090103", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090104", "���",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090105", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090106", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090107", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090108", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090109", "�޻�",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090110", "�޼�",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090111", "ƽɽ",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090112", "Ԫ��",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090113", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090114", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090115", "޻��",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090116", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090117", "����",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090118", "¹Ȫ",
			// "ʯ��ׯ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090201", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090202", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090203", "��ƽ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090204", "��ˮ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090205", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090206", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090207", "�ݳ�",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090209", "�Դ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090210", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090211", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090212", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090214", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090215", "���",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090216", "˳ƽ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090217", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090218", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090219", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090220", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090221", "�߱���",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090222", "�ˮ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090223", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090224", "��Է",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090225", "��Ұ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090301", "�żҿ�",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090302", "����",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090303", "�ű�",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090304", "����",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090305", "��Դ",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090306", "����",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090307", "ε��",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090308", "��ԭ",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090309", "����",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090310", "��ȫ",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090311", "����",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090312", "��¹",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090313", "���",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090314", "����",
			// "�żҿ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090402", "�е�",
			// "�е�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090403", "�е���",
			// "�е�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090404", "��¡",
			// "�е�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090405", "ƽȪ",
			// "�е�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090406", "��ƽ",
			// "�е�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090407", "¡��",
			// "�е�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090408", "����",
			// "�е�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090409", "���",
			// "�е�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090410", "Χ��",
			// "�е�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090501", "��ɽ",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090502", "����",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090503", "����",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090504", "����",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090505", "����",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090506", "��ͤ",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090507", "Ǩ��",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090508", "����",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090509", "�ƺ�",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090510", "��",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090511", "Ǩ��",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090512", "������",
			// "��ɽ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090601", "�ȷ�",
			// "�ȷ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090602", "�̰�",
			// "�ȷ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090603", "����",
			// "�ȷ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090604", "���",
			// "�ȷ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090605", "���",
			// "�ȷ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090606", "�İ�",
			// "�ȷ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090607", "��",
			// "�ȷ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090608", "����",
			// "�ȷ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090609", "����",
			// "�ȷ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090701", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090702", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090703", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090704", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090705", "��ɽ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090706", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090707", "��Ƥ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090708", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090709", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090710", "�ϴ�",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090711", "��ͷ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090712", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090713", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090714", "�Ӽ�",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090716", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090801", "��ˮ",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090802", "��ǿ",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090803", "����",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090804", "��ǿ",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090805", "����",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090806", "��ƽ",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090807", "�ʳ�",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090808", "����",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090809", "����",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090810", "����",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090811", "����",
			// "��ˮ", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090901", "��̨",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090902", "�ٳ�",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090904", "����",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090905", "����",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090906", "¡Ң",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090907", "�Ϻ�",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090908", "����",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090909", "��¹",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090910", "�º�",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090911", "����",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090912", "ƽ��",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090913", "����",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090914", "���",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090915", "����",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090916", "�Ϲ�",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090917", "ɳ��",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101090918", "����",
			// "��̨", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091001", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091002", "���",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091003", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091004", "�ɰ�",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091005", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091006", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091007", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091008", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091009", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091010", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091011", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091012", "��ƽ",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091013", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091014", "κ��",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091015", "����",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091016", "�䰲",
			// "����", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091101", "�ػʵ�",
			// "�ػʵ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091102", "����",
			// "�ػʵ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091103", "����",
			// "�ػʵ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091104", "����",
			// "�ػʵ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091105", "¬��",
			// "�ػʵ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101091106", "������",
			// "�ػʵ�", "�ӱ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100101", "̫ԭ",
			// "̫ԭ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100102", "����",
			// "̫ԭ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100103", "����",
			// "̫ԭ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100104", "¦��",
			// "̫ԭ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100105", "�Ž�",
			// "̫ԭ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100106", "���ƺ��",
			// "̫ԭ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100107", "С����",
			// "̫ԭ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100201", "��ͬ",
			// "��ͬ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100202", "����",
			// "��ͬ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100203", "��ͬ��",
			// "��ͬ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100204", "����",
			// "��ͬ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100205", "����",
			// "��ͬ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100206", "����",
			// "��ͬ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100207", "��Դ",
			// "��ͬ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100208", "����",
			// "��ͬ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100301", "��Ȫ",
			// "��Ȫ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100302", "����",
			// "��Ȫ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100303", "ƽ��",
			// "��Ȫ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100401", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100402", "�ܴ�",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100403", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100404", "��Ȩ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100405", "��˳",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100406", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100407", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100408", "̫��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100409", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100410", "ƽң",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100411", "��ʯ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100412", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100501", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100502", "���",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100503", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100504", "º��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100505", "��ԫ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100506", "ƽ˳",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100507", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100508", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100509", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100510", "��Դ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100511", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100601", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100602", "��ˮ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100603", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100604", "�괨",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100605", "��ƽ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100606", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100701", "�ٷ�",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100702", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100703", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100704", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100705", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100706", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100707", "���",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100708", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100709", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100710", "�鶴",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100711", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100712", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100713", "���",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100714", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100715", "��ɽ",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100716", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100717", "����",
			// "�ٷ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100801", "�˳�",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100802", "���",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100803", "�ɽ",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100804", "����",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100805", "�ӽ�",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100806", "���",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100807", "���",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100808", "��ϲ",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100809", "ԫ��",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100810", "����",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100811", "�ǳ�",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100812", "����",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100813", "ƽ½",
			// "�˳�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100901", "˷��",
			// "˷��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100902", "ƽ³",
			// "˷��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100903", "ɽ��",
			// "˷��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100904", "����",
			// "˷��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100905", "Ӧ��",
			// "˷��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101100906", "����",
			// "˷��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101001", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101002", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101003", "��̨��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101004", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101005", "ƫ��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101006", "���",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101007", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101008", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101009", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101010", "��̨ɽ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101011", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101012", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101013", "��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101014", "��կ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101015", "ԭƽ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101100", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101101", "��ʯ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101102", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101103", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101104", "���",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101105", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101106", "ʯ¥",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101107", "��ɽ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101108", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101109", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101110", "Т��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101111", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101112", "��ˮ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101101113", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110103", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110104", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110105", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110106", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110107", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110200", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110201", "��ԭ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110202", "��Ȫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110203", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110204", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110205", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110206", "�书",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110207", "Ǭ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110208", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110209", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110210", "Ѯ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110211", "��ƽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110300", "�Ӱ�",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110301", "�ӳ�",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110302", "�Ӵ�",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110303", "�ӳ�",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110304", "�˴�",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110305", "����",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110306", "־��",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110307", "����",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110308", "��Ȫ",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110309", "�崨",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110310", "����",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110311", "����",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110312", "����",
			// "�Ӱ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110402", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110403", "��ľ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110404", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110405", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110406", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110407", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110408", "��֬",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110409", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110410", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110411", "�Ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110412", "�彧",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110413", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110501", "μ��",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110502", "����",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110503", "����",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110504", "����",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110505", "��ˮ",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110506", "��ƽ",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110507", "�ѳ�",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110508", "�γ�",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110509", "����",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110510", "����",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110511", "����",
			// "μ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110602", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110603", "��ˮ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110604", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110605", "��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110606", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110607", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110608", "ɽ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110701", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110702", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110703", "ʯȪ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110704", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110705", "Ѯ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110706", "᰸�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110707", "ƽ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110708", "�׺�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110709", "��ƺ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110710", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110801", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110802", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110803", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110804", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110805", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110806", "�ǹ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110807", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110808", "��ƺ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110809", "��ǿ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110810", "��֣",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110811", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110901", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110903", "ǧ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110904", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110905", "�ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110906", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110907", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110908", "ü��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110909", "̫��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110910", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110911", "¤��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101110912", "�²�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111001", "ͭ��",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111002", "ҫ��",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111003", "�˾�",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111004", "ҫ��",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101111101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120101", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120102", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120103", "�̺�",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120104", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120105", "ƽ��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120106", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120107", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120201", "�ൺ",
			// "�ൺ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120202", "��ɽ",
			// "�ൺ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120204", "��ī",
			// "�ൺ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120205", "����",
			// "�ൺ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120206", "����",
			// "�ൺ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120207", "����",
			// "�ൺ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120208", "ƽ��",
			// "�ൺ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120301", "�Ͳ�",
			// "�Ͳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120302", "�ʹ�",
			// "�Ͳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120303", "��ɽ",
			// "�Ͳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120304", "����",
			// "�Ͳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120305", "�ܴ�",
			// "�Ͳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120306", "��Դ",
			// "�Ͳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120307", "��̨",
			// "�Ͳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120308", "����",
			// "�Ͳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120401", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120402", "���",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120403", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120404", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120405", "���",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120406", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120407", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120408", "ƽԭ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120409", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120410", "�Ľ�",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120411", "���",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120501", "��̨",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120502", "����",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120503", "����",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120504", "����",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120505", "����",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120506", "��Զ",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120507", "��ϼ",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120508", "��ɽ",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120509", "Ĳƽ",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120510", "����",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120511", "����",
			// "��̨", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120601", "Ϋ��",
			// "Ϋ��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120602", "����",
			// "Ϋ��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120603", "�ٹ�",
			// "Ϋ��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120604", "����",
			// "Ϋ��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120605", "����",
			// "Ϋ��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120606", "����",
			// "Ϋ��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120607", "����",
			// "Ϋ��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120608", "����",
			// "Ϋ��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120609", "���",
			// "Ϋ��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120701", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120702", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120703", "΢ɽ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120704", "��̨",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120705", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120706", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120707", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120708", "��ˮ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120709", "��ɽ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120710", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120711", "�޳�",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120801", "̩��",
			// "̩��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120802", "��̩",
			// "̩��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120804", "�ʳ�",
			// "̩��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120805", "��ƽ",
			// "̩��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120806", "����",
			// "̩��", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120901", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120902", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120903", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120904", "��ɽ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120905", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120906", "۰��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120907", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120908", "ƽ��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120909", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101120910", "��ˮ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121001", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121002", "۲��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121003", "۩��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121004", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121005", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121006", "��Ұ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121007", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121008", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121009", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121101", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121102", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121103", "���",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121104", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121105", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121106", "մ��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121107", "��ƽ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121201", "��Ӫ",
			// "��Ӫ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121202", "�ӿ�",
			// "��Ӫ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121203", "����",
			// "��Ӫ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121204", "����",
			// "��Ӫ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121205", "����",
			// "��Ӫ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121301", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121302", "�ĵ�",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121303", "�ٳ�",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121304", "��ɽ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121305", "��ɽͷ",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121306", "ʯ��",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121401", "��ׯ",
			// "��ׯ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121402", "Ѧ��",
			// "��ׯ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121403", "ỳ�",
			// "��ׯ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121404", "̨��ׯ",
			// "��ׯ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121405", "����",
			// "��ׯ", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121501", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121502", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121503", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121601", "����",
			// "����", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121701", "�ĳ�",
			// "�ĳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121702", "����",
			// "�ĳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121703", "����",
			// "�ĳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121704", "����",
			// "�ĳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121705", "��ƽ",
			// "�ĳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121706", "����",
			// "�ĳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121707", "����",
			// "�ĳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101121709", "ݷ��",
			// "�ĳ�", "ɽ��"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130101", "��³ľ��",
			// "��³ľ��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130103", "С����",
			// "��³ľ��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130104", "����̨",
			// "��³ľ��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130105", "�����",
			// "��³ľ��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130108",
			// "��³ľ������վ", "��³ľ��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130109", "���",
			// "��³ľ��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130110", "���",
			// "��³ľ��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130201", "��������",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130202", "�ڶ���",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130203", "�׼�̲",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130301", "ʯ����",
			// "ʯ����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130302", "��̨",
			// "ʯ����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130303", "Ī����",
			// "ʯ����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130401", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130402", "��ͼ��",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130403", "��Ȫ",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130404", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130405", "��ľ����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130406", "��̨",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130407", "����˹",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130408", "ľ��",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130409", "�̼Һ�",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130501", "��³��",
			// "��³��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130502", "�п�ѷ",
			// "��³��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130503", "������",
			// "��³��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130504", "۷��",
			// "��³��", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130601", "�����",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130602", "��̨",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130603", "ξ��",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130604", "��Ǽ",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130605", "��ĩ",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130606", "�;�",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130607", "����",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130608", "��˶",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130610", "������³��",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130611", "�������",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130612", "����",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130613", "����",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130701", "������",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130801", "������",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130802", "��ʲ",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130803", "����",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130804", "�ݳ�",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130805", "�º�",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130806", "ɳ��",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130807", "�⳵",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130808", "��ƺ",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130809", "������",
			// "������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130901", "��ʲ",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130902", "Ӣ��ɳ",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130903", "��ʲ�����",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130904", "�����",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130905", "ɯ��",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130906", "Ҷ��",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130907", "����",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130908", "�ͳ�",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130909", "���պ�",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130910", "٤ʦ",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130911", "�踽",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101130912", "����",
			// "��ʲ", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131001", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131002", "�첼���",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131003", "���տ�",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131004", "������",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131005", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131006", "��Դ",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131007", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131008", "�ؿ�˹",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131009", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131010", "������˹",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131011", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131101", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131102", "ԣ��",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131103", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131104", "�Ͳ�������",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131105", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131106", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131107", "ɳ��",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131108", "�ͷ�",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131201", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131203", "������",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131204", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131301", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131302", "Ƥɽ",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131303", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131304", "ī��",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131305", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131306", "���",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131307", "����",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131401", "����̩",
			// "����̩", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131402", "���ͺ�",
			// "����̩", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131405", "��ľ��",
			// "����̩", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131406", "������",
			// "����̩", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131407", "����",
			// "����̩", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131408", "����",
			// "����̩", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131409", "���",
			// "����̩", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131501", "��ͼʲ",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131502", "��ǡ",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131503", "������",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131504", "������",
			// "����", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131601", "����",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131602", "��Ȫ",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131603", "����",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101131606", "����ɽ��",
			// "��������", "�½�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140103", "��ľ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140104", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140105", "��������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140106", "��ˮ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140107", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140108", "ī�񹤿�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140201", "�տ���",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140202", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140203", "��ľ��",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140204", "����ľ",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140205", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140206", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140207", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140208", "�ٰ�",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140209", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140210", "��¡",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140211", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140212", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140213", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140214", "лͨ��",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140216", "�ڰ�",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140217", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140218", "�Ƕ�",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140219", "����",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140220", "�ʲ�",
			// "�տ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140301", "ɽ��",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140302", "����",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140303", "����",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140304", "�Ӳ�",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140305", "�˿���",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140306", "����",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140307", "¡��",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140308", "��",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140309", "�˶�",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140310", "ɣ��",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140311", "����",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140312", "����",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140313", "���",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140314", "����",
			// "ɽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140401", "��֥",
			// "��֥", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140402", "����",
			// "��֥", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140403", "����",
			// "��֥", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140404", "����",
			// "��֥", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140405", "��������",
			// "��֥", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140406", "����",
			// "��֥", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140407", "ī��",
			// "��֥", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140501", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140502", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140503", "�߰�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140504", "��¡",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140505", "��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140506", "â��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140507", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140508", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140509", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140510", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140511", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140602", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140603", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140604", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140605", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140606", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140607", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140608", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140609", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140701", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140702", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140703", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140704", "ʨȪ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140705", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140706", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140707", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140708", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140709", "�Ｊ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101140710", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150101", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150102", "��ͨ",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150103", "��Դ",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150104", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150201", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150202", "�ֶ�",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150203", "���",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150204", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150205", "��¡",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150206", "ѭ��",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150207", "���",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150208", "ƽ��",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150301", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150302", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150303", "���",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150304", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150305", "ͬ��",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150401", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150404", "���",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150406", "�˺�",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150407", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150408", "ͬ��",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150409", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150501", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150502", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150503", "�ʵ�",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150504", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150505", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150506", "���",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150507", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150508", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150601", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150602", "�ƶ�",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150603", "�ζ�",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150604", "�Ӷ�",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150605", "��ǫ",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150606", "������",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150701", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150708", "���",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150709", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150712", "ã��",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150713", "���",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150716", "�����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150801", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150802", "��Դ",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150803", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150804", "����",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150806", "�ղ�",
			// "����", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150901", "���ľ",
			// "���ľ", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101150902", "����",
			// "���ľ", "�ຣ"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160103", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160104", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160202", "ͨμ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160203", "¤��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160204", "μԴ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160205", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160206", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160207", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160208", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160301", "ƽ��",
			// "ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160302", "����",
			// "ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160303", "��̨",
			// "ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160304", "����",
			// "ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160305", "��ͤ",
			// "ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160306", "ׯ��",
			// "ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160307", "����",
			// "ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160308", "���",
			// "ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160402", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160403", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160404", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160405", "��ˮ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160406", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160407", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160408", "��ԭ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160409", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160501", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160502", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160503", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160505", "��ף",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160601", "���",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160602", "����",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160701", "��Ҵ",
			// "��Ҵ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160702", "����",
			// "��Ҵ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160703", "����",
			// "��Ҵ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160704", "����",
			// "��Ҵ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160705", "��̨",
			// "��Ҵ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160706", "ɽ��",
			// "��Ҵ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160801", "��Ȫ",
			// "��Ȫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160803", "����",
			// "��Ȫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160804", "������",
			// "��Ȫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160805", "����",
			// "��Ȫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160806", "�౱",
			// "��Ȫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160807", "����",
			// "��Ȫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160808", "�ػ�",
			// "��Ȫ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160901", "��ˮ",
			// "��ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160903", "��ˮ",
			// "��ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160904", "�ذ�",
			// "��ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160905", "�ʹ�",
			// "��ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160906", "��ɽ",
			// "��ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160907", "�żҴ�",
			// "��ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101160908", "���",
			// "��ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161001", "�䶼",
			// "¤��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161002", "����",
			// "¤��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161003", "����",
			// "¤��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161004", "崲�",
			// "¤��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161005", "����",
			// "¤��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161006", "����",
			// "¤��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161007", "����",
			// "¤��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161008", "����",
			// "¤��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161009", "����",
			// "¤��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161103", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161104", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161105", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161106", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161107", "��ʯɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161202", "��̶",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161203", "׿��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161204", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161205", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161206", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161207", "µ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161208", "�ĺ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161301", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161302", "��Զ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161303", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161304", "ƽ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161305", "��̩",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101161401", "������",
			// "������", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170103", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170104", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170201", "ʯ��ɽ",
			// "ʯ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170202", "��ũ",
			// "ʯ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170203", "ƽ��",
			// "ʯ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170204", "����",
			// "ʯ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170206", "�����",
			// "ʯ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170301", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170302", "ͬ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170303", "�γ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170306", "��ͭϿ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170401", "��ԭ",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170402", "����",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170403", "¡��",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170404", "��Դ",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170406", "����",
			// "��ԭ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170501", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170502", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101170504", "��ԭ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180101", "֣��",
			// "֣��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180102", "����",
			// "֣��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180103", "����",
			// "֣��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180104", "�Ƿ�",
			// "֣��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180105", "����",
			// "֣��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180106", "��֣",
			// "֣��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180107", "��Ĳ",
			// "֣��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180108", "�Ͻ�",
			// "֣��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180202", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180203", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180204", "�ڻ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180205", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180301", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180302", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180303", "ԭ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180304", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180305", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180306", "�ӽ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180307", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180308", "��ԫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180401", "���",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180402", "۳��",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180403", "���",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180404", "����",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180405", "����",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180501", "ƽ��ɽ",
			// "ƽ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180502", "ۣ��",
			// "ƽ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180503", "����",
			// "ƽ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180504", "����",
			// "ƽ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180505", "Ҷ��",
			// "ƽ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180506", "���",
			// "ƽ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180507", "³ɽ",
			// "ƽ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180508", "ʯ��",
			// "ƽ��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180602", "Ϣ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180603", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180604", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180605", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180606", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180607", "�괨",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180608", "��ʼ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180609", "�̳�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180701", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180702", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180703", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180704", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180705", "��Ͽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180706", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180707", "��ƽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180708", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180709", "��Ұ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180710", "�ƺ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180711", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180712", "ͩ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180801", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180802", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180803", "ξ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180804", "ͨ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180805", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180901", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180902", "�°�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180903", "�Ͻ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180904", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180905", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180906", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180907", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180908", "��ʦ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180909", "�ﴨ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180910", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101180911", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181001", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181003", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181004", "��Ȩ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181005", "�ݳ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181006", "�ϳ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181007", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181008", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181009", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181103", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181104", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181106", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181107", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181108", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181201", "�ױ�",
			// "�ױ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181202", "����",
			// "�ױ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181203", "���",
			// "�ױ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181301", "���",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181302", "̨ǰ",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181303", "����",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181304", "���",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181305", "����",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181401", "�ܿ�",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181402", "����",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181403", "̫��",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181404", "����",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181405", "����",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181406", "��ˮ",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181407", "���",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181408", "����",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181409", "¹��",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181410", "����",
			// "�ܿ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181501", "���",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181502", "���",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181503", "����",
			// "���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181601", "פ���",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181602", "��ƽ",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181603", "��ƽ",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181604", "�ϲ�",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181605", "����",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181606", "����",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181607", "ƽ��",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181608", "�²�",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181609", "ȷɽ",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181610", "����",
			// "פ���", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181701", "����Ͽ",
			// "����Ͽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181702", "�鱦",
			// "����Ͽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181703", "�ų�",
			// "����Ͽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181704", "¬��",
			// "����Ͽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181705", "����",
			// "����Ͽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181706", "����",
			// "����Ͽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101181801", "��Դ",
			// "��Դ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190101", "�Ͼ�",
			// "�Ͼ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190102", "��ˮ",
			// "�Ͼ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190103", "�ߴ�",
			// "�Ͼ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190104", "����",
			// "�Ͼ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190105", "����",
			// "�Ͼ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190106", "����",
			// "�Ͼ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190107", "�ֿ�",
			// "�Ͼ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190202", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190203", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190204", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190301", "��",
			// "��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190302", "����",
			// "��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190303", "����",
			// "��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190304", "����",
			// "��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190305", "��ͽ",
			// "��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190402", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190403", "�żҸ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190404", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190405", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190407", "�⽭",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190408", "̫��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190501", "��ͨ",
			// "��ͨ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190502", "����",
			// "��ͨ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190503", "���",
			// "��ͨ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190504", "�綫",
			// "��ͨ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190507", "����",
			// "��ͨ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190508", "����",
			// "��ͨ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190509", "ͨ��",
			// "��ͨ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190602", "��Ӧ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190603", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190604", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190605", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190606", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190701", "�γ�",
			// "�γ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190702", "��ˮ",
			// "�γ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190703", "����",
			// "�γ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190704", "����",
			// "�γ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190705", "����",
			// "�γ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190706", "����",
			// "�γ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190707", "��̨",
			// "�γ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190708", "���",
			// "�γ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190709", "�ζ�",
			// "�γ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190801", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190802", "ͭɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190803", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190804", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190805", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190806", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190807", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190901", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190902", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190903", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190904", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190905", "��ˮ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190906", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101190908", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191001", "���Ƹ�",
			// "���Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191002", "����",
			// "���Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191003", "����",
			// "���Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191004", "����",
			// "���Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191005", "����",
			// "���Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191103", "��̳",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191104", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191201", "̩��",
			// "̩��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191202", "�˻�",
			// "̩��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191203", "̩��",
			// "̩��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191204", "����",
			// "̩��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191205", "����",
			// "̩��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191301", "��Ǩ",
			// "��Ǩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191302", "����",
			// "��Ǩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191303", "����",
			// "��Ǩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191304", "����",
			// "��Ǩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101191305", "��ԥ",
			// "��Ǩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200101", "�人",
			// "�人", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200102", "�̵�",
			// "�人", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200103", "����",
			// "�人", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200104", "����",
			// "�人", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200105", "����",
			// "�人", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200106", "������",
			// "�人", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200202", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200203", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200204", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200205", "�˳�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200206", "�Ϻӿ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200207", "�ȳ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200208", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200301", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200302", "���Ӻ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200401", "Т��",
			// "Т��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200402", "��½",
			// "Т��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200403", "����",
			// "Т��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200404", "����",
			// "Т��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200405", "Ӧ��",
			// "Т��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200406", "����",
			// "Т��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200407", "Т��",
			// "Т��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200501", "�Ƹ�",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200502", "�찲",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200503", "���",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200504", "����",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200505", "Ӣɽ",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200506", "�ˮ",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200507", "ޭ��",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200508", "��÷",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200509", "��Ѩ",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200510", "�ŷ�",
			// "�Ƹ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200601", "��ʯ",
			// "��ʯ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200602", "��ұ",
			// "��ʯ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200603", "����",
			// "��ʯ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200604", "��ɽ",
			// "��ʯ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200605", "��½",
			// "��ʯ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200606", "����ɽ",
			// "��ʯ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200701", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200702", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200703", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200704", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200705", "ͨ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200706", "ͨɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200801", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200802", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200803", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200804", "ʯ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200805", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200806", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200807", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200901", "�˲�",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200902", "Զ��",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200903", "����",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200904", "��ɽ",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200905", "�˲���",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200906", "���",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200907", "����",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200908", "����",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200909", "�˶�",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200910", "֦��",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200911", "��Ͽ",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101200912", "����",
			// "�˲�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201001", "��ʩ",
			// "��ʩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201002", "����",
			// "��ʩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201003", "��ʼ",
			// "��ʩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201004", "�̷�",
			// "��ʩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201005", "����",
			// "��ʩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201006", "�׷�",
			// "��ʩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201007", "����",
			// "��ʩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201008", "�Ͷ�",
			// "��ʩ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201101", "ʮ��",
			// "ʮ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201102", "��Ϫ",
			// "ʮ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201103", "����",
			// "ʮ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201104", "����",
			// "ʮ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201105", "��ɽ",
			// "ʮ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201106", "����",
			// "ʮ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201107", "������",
			// "ʮ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201108", "é��",
			// "ʮ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201109", "����",
			// "ʮ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201201", "��ũ��",
			// "��ũ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201301", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201302", "��ˮ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201402", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201403", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201404", "�޵�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201405", "ɳ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201406", "ɳ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201501", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101201701", "Ǳ��",
			// "Ǳ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210101", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210102", "��ɽ",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210103", "ͩ®",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210104", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210105", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210106", "�ຼ",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210107", "�ٰ�",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210108", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210201", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210202", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210203", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210204", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210301", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210302", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210303", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210304", "ͩ��",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210305", "ƽ��",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210306", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210401", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210403", "��Ϫ",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210404", "��Ҧ",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210405", "�",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210406", "��ɽ",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210408", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210410", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210411", "۴��",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210412", "��",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210501", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210502", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210503", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210504", "�²�",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210505", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210601", "̨��",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210603", "��",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210604", "����",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210605", "��̨",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210606", "�ɾ�",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210607", "����",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210609", "���",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210610", "�ٺ�",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210611", "����",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210612", "����",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210613", "·��",
			// "̨��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210701", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210702", "̩˳",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210703", "�ĳ�",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210704", "ƽ��",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210705", "��",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210706", "��ͷ",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210707", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210708", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210709", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210801", "��ˮ",
			// "��ˮ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210802", "���",
			// "��ˮ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210803", "��Ȫ",
			// "��ˮ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210804", "����",
			// "��ˮ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210805", "����",
			// "��ˮ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210806", "�ƺ�",
			// "��ˮ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210807", "��Ԫ",
			// "��ˮ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210808", "����",
			// "��ˮ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210809", "����",
			// "��ˮ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210901", "��",
			// "��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210902", "�ֽ�",
			// "��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210903", "��Ϫ",
			// "��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210904", "����",
			// "��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210905", "����",
			// "��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210906", "����",
			// "��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210907", "����",
			// "��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101210908", "�Ͱ�",
			// "��", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211001", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211002", "��ɽ",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211003", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211004", "����",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211005", "��ɽ",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211006", "�齭",
			// "����", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211101", "��ɽ",
			// "��ɽ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211102", "����",
			// "��ɽ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211104", "�ɽ",
			// "��ɽ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211105", "����",
			// "��ɽ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101211106", "����",
			// "��ɽ", "�㽭"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220101", "�Ϸ�",
			// "�Ϸ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220102", "����",
			// "�Ϸ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220103", "�ʶ�",
			// "�Ϸ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220104", "����",
			// "�Ϸ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220202", "��Զ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220203", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220204", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220301", "�ߺ�",
			// "�ߺ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220302", "����",
			// "�ߺ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220303", "�ߺ���",
			// "�ߺ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220304", "����",
			// "�ߺ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220402", "��̨",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220403", "�˼�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220501", "��ɽ",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220502", "��Ϳ",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220602", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220603", "̫��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220604", "Ǳɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220605", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220606", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220607", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220608", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220609", "ͩ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220701", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220702", "�ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220703", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220704", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220705", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220801", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220802", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220803", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220804", "��Ȫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220805", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220806", "̫��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220901", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220902", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220903", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101220904", "�ɳ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221001", "��ɽ",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221002", "��ɽ��",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221003", "��Ϫ",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221004", "����",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221005", "����",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221006", "���",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221007", "����",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221008", "��ɽ�羰��",
			// "��ɽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221103", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221104", "��Զ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221105", "ȫ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221106", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221107", "�쳤",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221202", "�Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221301", "ͭ��",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221402", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221403", "캵�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221404", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221405", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221406", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221407", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221501", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221502", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221503", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221505", "��կ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221506", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221507", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221602", "®��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221603", "��Ϊ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221604", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221605", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221701", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221702", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221703", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221704", "�Ż�ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101221705", "ʯ̨",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230103", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230104", "��Դ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230105", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230107", "��̩",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230108", "ƽ̶",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230110", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230111", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230202", "ͬ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230301", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230302", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230303", "ϼ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230304", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230305", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230306", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230307", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230308", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230309", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230402", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230403", "�����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230404", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230405", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230406", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230407", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230501", "Ȫ��",
			// "Ȫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230502", "��Ϫ",
			// "Ȫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230504", "����",
			// "Ȫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230505", "�»�",
			// "Ȫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230506", "�ϰ�",
			// "Ȫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230507", "����",
			// "Ȫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230508", "�ݰ�",
			// "Ȫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230509", "����",
			// "Ȫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230510", "ʯʨ",
			// "Ȫ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230602", "��̩",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230603", "�Ͼ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230604", "ƽ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230605", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230606", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230607", "گ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230608", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230609", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230610", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230701", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230702", "��͡",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230703", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230704", "��ƽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230705", "�Ϻ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230706", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230707", "��ƽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230801", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230802", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230803", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230804", "̩��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230805", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230806", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230807", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230808", "ɳ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230809", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230810", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230811", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230901", "��ƽ",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230902", "˳��",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230903", "����",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230904", "����",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230905", "����ɽ",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230906", "�ֳ�",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230907", "����",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230908", "��Ϫ",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230909", "����",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101230910", "���",
			// "��ƽ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101231001", "���㵺",
			// "���㵺", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240101", "�ϲ�",
			// "�ϲ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240102", "�½�",
			// "�ϲ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240103", "�ϲ���",
			// "�ϲ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240104", "����",
			// "�ϲ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240105", "����",
			// "�ϲ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240106", "����",
			// "�ϲ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240201", "�Ž�",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240202", "���",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240203", "®ɽ",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240204", "����",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240205", "�°�",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240206", "����",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240207", "����",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240208", "����",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240209", "����",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240210", "����",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240212", "��ˮ",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240213", "����",
			// "�Ž�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240301", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240302", "۶��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240303", "��Դ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240305", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240306", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240307", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240308", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240309", "߮��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240310", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240311", "Ǧɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240312", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240313", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240402", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240403", "�ְ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240404", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240405", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240406", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240407", "�˻�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240408", "�ϳ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240409", "�Ϸ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240410", "�质",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240411", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240501", "�˴�",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240502", "ͭ��",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240503", "�˷�",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240504", "����",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240505", "�ϸ�",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240506", "����",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240507", "����",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240508", "�߰�",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240509", "����",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240510", "���",
			// "�˴�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240602", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240603", "��ˮ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240604", "�¸�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240605", "Ͽ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240606", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240607", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240608", "����ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240609", "��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240610", "�촨",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240611", "̩��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240612", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240613", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240701", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240702", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240703", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240704", "�Ͽ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240705", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240706", "�ŷ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240707", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240708", "ʯ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240709", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240710", "�ڶ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240711", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240712", "��Զ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240713", "ȫ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240714", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240715", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240716", "Ѱ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240717", "�˹�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240718", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240801", "������",
			// "������", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240802", "��ƽ",
			// "������", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240803", "����",
			// "������", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240901", "Ƽ��",
			// "Ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240902", "����",
			// "Ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240903", "����",
			// "Ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240904", "��Դ",
			// "Ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240905", "«Ϫ",
			// "Ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101240906", "�涫",
			// "Ƽ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241001", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241002", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241101", "ӥ̶",
			// "ӥ̶", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241102", "�཭",
			// "ӥ̶", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101241103", "��Ϫ",
			// "ӥ̶", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250101", "��ɳ",
			// "��ɳ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250102", "����",
			// "��ɳ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250103", "���",
			// "��ɳ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250104", "������",
			// "��ɳ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250105", "����",
			// "��ɳ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250201", "��̶",
			// "��̶", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250202", "��ɽ",
			// "��̶", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250203", "����",
			// "��̶", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250301", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250302", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250303", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250305", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250306", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250402", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250403", "�ⶫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250404", "�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250405", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250406", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250407", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250408", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250409", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250501", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250502", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250503", "�κ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250504", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250505", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250507", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250508", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250509", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250510", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250511", "��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250512", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250601", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250602", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250603", "��Դ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250604", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250605", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250606", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250607", "ʯ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250608", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250700", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250701", "��ɽ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250702", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250703", "�ҽ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250704", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250705", "�佭",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250801", "¦��",
			// "¦��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250802", "˫��",
			// "¦��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250803", "��ˮ��",
			// "¦��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250805", "�»�",
			// "¦��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250806", "��Դ",
			// "¦��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250901", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250902", "¡��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250903", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250904", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250905", "�۶�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250906", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250907", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250908", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250909", "�ǲ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101250910", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251001", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251002", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251003", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251004", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251005", "ƽ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251006", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251101", "�żҽ�",
			// "�żҽ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251102", "ɣֲ",
			// "�żҽ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251103", "����",
			// "�żҽ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251104", "����Դ",
			// "�żҽ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251203", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251204", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251205", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251206", "��ͬ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251207", "ͨ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251208", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251209", "�»�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251210", "�ƽ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251211", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251212", "�з�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251213", "�齭",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251401", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251402", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251403", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251404", "˫��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251405", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251406", "��Զ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251407", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251408", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251409", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251410", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251411", "��ˮ̲",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251501", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251502", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251503", "��˳",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251504", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251505", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251506", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251507", "��ɽ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101251508", "��ԫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260101", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260102", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260103", "��Ϫ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260104", "�ڵ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260105", "Ϣ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260106", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260107", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260108", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260109", "С��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260110", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260111", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260201", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260202", "������",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260203", "�ʻ�",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260204", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260205", "��̶",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260206", "���",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260207", "ͩ��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260208", "��ˮ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260209", "ϰˮ",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260210", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260211", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260212", "��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260213", "����",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260214", "�㴨",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260215", "�컨��",
			// "����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260301", "��˳",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260302", "�ն�",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260303", "����",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260304", "ƽ��",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260305", "����",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260306", "����",
			// "��˳", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260401", "����",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260402", "��",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260403", "�Ͱ�",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260404", "��˳",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260405", "��Ȫ",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260406", "��ˮ",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260407", "����",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260408", "�޵�",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260409", "ƽ��",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260410", "��ɽ",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260411", "����",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260412", "��",
			// "ǭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260501", "����",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260502", "᯹�",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260503", "ʩ��",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260504", "��Զ",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260505", "��ƽ",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260507", "�齭",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260508", "��կ",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260509", "����",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260510", "̨��",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260511", "����",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260512", "��ɽ",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260513", "��ƽ",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260514", "����",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260515", "����",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260516", "�Ž�",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260517", "�ӽ�",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260601", "ͭ��",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260602", "����",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260603", "����",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260604", "��ɽ",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260605", "˼��",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260607", "ӡ��",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260608", "ʯ��",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260609", "�غ�",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260610", "�½�",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260611", "����",
			// "ͭ��", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260701", "�Ͻ�",
			// "�Ͻ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260702", "����",
			// "�Ͻ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260703", "��ɳ",
			// "�Ͻ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260704", "����",
			// "�Ͻ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260705", "��",
			// "�Ͻ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260706", "��Ӻ",
			// "�Ͻ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260707", "֯��",
			// "�Ͻ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260708", "ǭ��",
			// "�Ͻ�", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260801", "ˮ��",
			// "����ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260802", "��֦",
			// "����ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260804", "����",
			// "����ˮ", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260901", "����",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260902", "��¡",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260903", "����",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260904", "���",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260905", "����",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260907", "����",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260908", "���",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101260909", "�հ�",
			// "ǭ����", "����"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270101", "�ɶ�",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270102", "��Ȫ��",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270103", "�¶�",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270104", "�½�",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270105", "����",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270106", "˫��",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270107", "ۯ��",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270108", "����",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270109", "�ѽ�",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270110", "�½�",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270111", "������",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270112", "����",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270113", "����",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270114", "����",
			// "�ɶ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270201", "��֦��",
			// "��֦��", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270202", "�ʺ�",
			// "��֦��", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270203", "����",
			// "��֦��", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270204", "�α�",
			// "��֦��", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270301", "�Թ�",
			// "�Թ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270302", "��˳",
			// "�Թ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270303", "����",
			// "�Թ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270401", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270402", "��̨",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270403", "��ͤ",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270404", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270405", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270406", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270407", "ƽ��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270408", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270501", "�ϳ�",
			// "�ϳ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270502", "�ϲ�",
			// "�ϳ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270503", "Ӫɽ",
			// "�ϳ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270504", "�",
			// "�ϳ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270505", "��¤",
			// "�ϳ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270506", "����",
			// "�ϳ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270507", "����",
			// "�ϳ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270601", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270602", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270603", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270604", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270605", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270606", "��Դ",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270607", "ͨ��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270608", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270701", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270702", "��Ϫ",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270703", "���",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270801", "�㰲",
			// "�㰲", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270802", "����",
			// "�㰲", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270803", "��ʤ",
			// "�㰲", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270804", "��ˮ",
			// "�㰲", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270805", "����",
			// "�㰲", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270901", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270902", "ͨ��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270903", "�Ͻ�",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101270904", "ƽ��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271001", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271003", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271004", "�Ͻ�",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271005", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271006", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271007", "��Ϫ",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271101", "�˱�",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271103", "�˱���",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271104", "��Ϫ",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271105", "����",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271106", "����",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271107", "����",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271108", "����",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271109", "����",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271110", "����",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271111", "��ɽ",
			// "�˱�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271201", "�ڽ�",
			// "�ڽ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271202", "����",
			// "�ڽ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271203", "��Զ",
			// "�ڽ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271204", "����",
			// "�ڽ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271205", "¡��",
			// "�ڽ�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271301", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271302", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271303", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271304", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271401", "��ɽ",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271402", "��Ϊ",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271403", "����",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271404", "�н�",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271405", "�崨",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271406", "���",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271407", "���",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271408", "��ü",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271409", "��üɽ",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271501", "üɽ",
			// "üɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271502", "����",
			// "üɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271503", "��ɽ",
			// "üɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271504", "����",
			// "üɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271505", "����",
			// "üɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271506", "����",
			// "üɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271601", "��ɽ",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271603", "ľ��",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271604", "��Դ",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271605", "�²�",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271606", "����",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271607", "�ᶫ",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271608", "����",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271609", "�ո�",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271610", "����",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271611", "����",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271612", "�Ѿ�",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271613", "ϲ��",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271614", "����",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271615", "Խ��",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271616", "����",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271617", "�ײ�",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271618", "����",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271619", "����",
			// "��ɽ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271701", "�Ű�",
			// "�Ű�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271702", "��ɽ",
			// "�Ű�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271703", "����",
			// "�Ű�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271704", "��Դ",
			// "�Ű�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271705", "ʯ��",
			// "�Ű�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271706", "��ȫ",
			// "�Ű�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271707", "«ɽ",
			// "�Ű�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271708", "����",
			// "�Ű�", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271801", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271802", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271803", "��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271804", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271805", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271806", "�Ž�",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271807", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271808", "¯��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271809", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271810", "�¸�",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271811", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271812", "ʯ��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271813", "ɫ��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271814", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271815", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271816", "���",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271817", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271818", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271901", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271902", "�봨",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271903", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271904", "ï��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271905", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271906", "��կ��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271907", "��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271908", "С��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271909", "��ˮ",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271910", "�����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271911", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271912", "������",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271913", "��ԭ",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101271914", "��ƺ",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272001", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272002", "�н�",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272003", "�㺺",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272004", "ʲ��",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272005", "����",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272006", "�޽�",
			// "����", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272101", "��Ԫ",
			// "��Ԫ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272102", "����",
			// "��Ԫ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272103", "�ന",
			// "��Ԫ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272104", "����",
			// "��Ԫ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101272105", "��Ϫ",
			// "��Ԫ", "�Ĵ�"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280101", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280102", "��خ",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280103", "�ӻ�",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280104", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280105", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280201", "�ع�",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280202", "��Դ",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280203", "ʼ��",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280204", "��Դ",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280205", "�ֲ�",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280206", "�ʻ�",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280207", "����",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280208", "�·�",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280209", "����",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280210", "䥽�",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280211", "�佭",
			// "�ع�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280301", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280302", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280303", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280304", "�ݶ�",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280305", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280401", "÷��",
			// "÷��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280402", "����",
			// "÷��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280403", "����",
			// "÷��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280404", "����",
			// "÷��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280406", "��˳",
			// "÷��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280407", "ƽԶ",
			// "÷��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280408", "�廪",
			// "÷��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280409", "÷��",
			// "÷��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280501", "��ͷ",
			// "��ͷ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280502", "����",
			// "��ͷ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280503", "�κ�",
			// "��ͷ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280504", "�ϰ�",
			// "��ͷ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280601", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280701", "�麣",
			// "�麣", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280702", "����",
			// "�麣", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280703", "����",
			// "�麣", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280800", "��ɽ",
			// "��ɽ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280801", "˳��",
			// "��ɽ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280802", "��ˮ",
			// "��ɽ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280803", "�Ϻ�",
			// "��ɽ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280804", "����",
			// "��ɽ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280901", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280902", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280903", "�Ļ�",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280905", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280906", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280907", "�⿪",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101280908", "��Ҫ",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281001", "տ��",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281002", "�⴨",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281003", "����",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281004", "����",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281005", "����",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281006", "�࿲",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281007", "��Ϫ",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281008", "��ͷ",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281009", "ϼɽ",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281010", "����",
			// "տ��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281101", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281103", "��ƽ",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281104", "�»�",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281105", "��ƽ",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281106", "̨ɽ",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281107", "�",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281108", "��ɽ",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281109", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281201", "��Դ",
			// "��Դ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281202", "�Ͻ�",
			// "��Դ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281203", "��ƽ",
			// "��Դ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281204", "��ƽ",
			// "��Դ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281205", "����",
			// "��Դ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281206", "��Դ",
			// "��Դ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281301", "��Զ",
			// "��Զ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281302", "����",
			// "��Զ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281303", "����",
			// "��Զ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281304", "��ɽ",
			// "��Զ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281305", "��ɽ",
			// "��Զ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281306", "���",
			// "��Զ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281307", "Ӣ��",
			// "��Զ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281308", "����",
			// "��Զ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281401", "�Ƹ�",
			// "�Ƹ�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281402", "�޶�",
			// "�Ƹ�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281403", "����",
			// "�Ƹ�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281404", "����",
			// "�Ƹ�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281406", "�ư�",
			// "�Ƹ�", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281501", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281502", "��ƽ",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281503", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281601", "��ݸ",
			// "��ݸ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281701", "��ɽ",
			// "��ɽ", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281801", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281802", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281803", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281804", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281901", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281902", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281903", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281904", "����",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101281905", "�Ҷ�",
			// "����", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282001", "ï��",
			// "ï��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282002", "����",
			// "ï��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282003", "����",
			// "ï��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282004", "���",
			// "ï��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282005", "����",
			// "ï��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282006", "ï��",
			// "ï��", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282101", "��β",
			// "��β", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282102", "����",
			// "��β", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282103", "½��",
			// "��β", "�㶫"));
			// WeatherAreaModels.add(new WeatherAreaModel("101282104", "½��",
			// "��β", "�㶫"));
			WeatherAreaModels.add(new WeatherAreaModel("101290101", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290103", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290104", "Ѱ��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290105", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290106", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290107", "ʯ��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290108", "�ʹ�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290109", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290110", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290111", "»Ȱ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290112", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290113", "̫��ɽ",
					"����", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290201", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290202", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290203", "���", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290204", "��ƽ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290205", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290206", "�ֶ�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290207", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290208", "Ρɽ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290209", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290210", "��Դ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290211", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290212", "�Ͻ�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290301", "���", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290302", "ʯ��", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290303", "��ˮ", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290304", "����", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290305", "Ԫ��", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290306", "�̴�", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290307", "��Զ", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290308", "����", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290309", "����", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290310", "����", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290311", "����", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290312", "��ƽ", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290313", "�ӿ�", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290401", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290402", "մ��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290403", "½��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290404", "��Դ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290405", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290406", "ʦ��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290407", "��ƽ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290408", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290409", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290501", "��ɽ", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290503", "����", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290504", "ʩ��", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290505", "����", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290506", "�ڳ�", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290601", "��ɽ", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290602", "����", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290603", "���", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290604", "������",
					"��ɽ", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290605", "��ɽ", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290606", "��", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290607", "����", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290608", "����", "��ɽ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290701", "��Ϫ", "��Ϫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290702", "�ν�", "��Ϫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290703", "����", "��Ϫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290704", "ͨ��", "��Ϫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290705", "����", "��Ϫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290706", "��ƽ", "��Ϫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290707", "����", "��Ϫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290708", "��ɽ", "��Ϫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290709", "Ԫ��", "��Ϫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290801", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290802", "��Ҧ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290803", "Ԫı", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290804", "Ҧ��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290805", "Ĳ��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290806", "�ϻ�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290807", "�䶨", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290808", "»��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290809", "˫��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290810", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290901", "�ն�", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290902", "����", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290903", "����", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290904", "����", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290906", "ī��", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290907", "����", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290908", "����", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290909", "����", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290911", "����", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101290912", "����", "�ն�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291001", "��ͨ", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291002", "³��", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291003", "����", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291004", "����", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291005", "����", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291006", "�ɼ�", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291007", "�罭", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291008", "����", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291009", "�ν�", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291010", "���", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291011", "ˮ��", "��ͨ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291101", "�ٲ�", "�ٲ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291102", "��Դ", "�ٲ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291103", "����", "�ٲ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291104", "˫��", "�ٲ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291105", "����", "�ٲ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291106", "����", "�ٲ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291107", "����", "�ٲ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291108", "��", "�ٲ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291201", "ŭ��", "ŭ��",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291203", "����", "ŭ��",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291204", "��ƺ", "ŭ��",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291205", "��ˮ", "ŭ��",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291206", "����", "ŭ��",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291207", "��ɽ", "ŭ��",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291301", "�������",
					"����", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291302", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291303", "ά��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291304", "�е�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291401", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291402", "��ʤ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291403", "��ƺ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291404", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291501", "�º�", "�º�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291503", "¤��", "�º�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291504", "ӯ��", "�º�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291506", "����", "�º�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291507", "����", "�º�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291508", "º��", "�º�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291601", "����",
					"��˫����", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291603", "�º�",
					"��˫����", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101291605", "����",
					"��˫����", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300101", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300103", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300104", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300105", "¡��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300106", "��ɽ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300107", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300108", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300109", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300201", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300202", "���", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300203", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300204", "ƾ��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300205", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300206", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300207", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300301", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300302", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300304", "¹կ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300305", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300306", "�ڰ�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300307", "��ˮ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300308", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300401", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300402", "�ó�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300403", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300404", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300405", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300406", "��ɽ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300501", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300503", "��ʤ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300504", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300505", "�ٹ�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300506", "�˰�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300507", "�鴨", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300508", "ȫ��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300509", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300510", "��˷", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300511", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300512", "ƽ��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300513", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300514", "��Դ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300601", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300602", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300604", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300605", "��ɽ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300606", "�Ϫ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300607", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300701", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300702", "��ƽ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300703", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300704", "��ɽ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300801", "���", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300802", "��ƽ", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300803", "ƽ��", "���",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300901", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300902", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300903", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300904", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300905", "½��", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101300906", "��ҵ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301001", "��ɫ", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301002", "����", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301003", "����", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301004", "�±�", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301005", "����", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301006", "�ﶫ", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301007", "ƽ��", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301008", "¡��", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301009", "����", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301010", "��ҵ", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301011", "����", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301012", "����", "��ɫ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301101", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301102", "�ֱ�", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301103", "��ɽ", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301201", "�ӳ�", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301202", "���", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301203", "����", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301204", "����", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301205", "����", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301206", "�޳�", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301207", "����", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301208", "��ɽ", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301209", "�ϵ�", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301210", "����", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301211", "��", "�ӳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301301", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301302", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301303", "��޵�",
					"����", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301401", "���Ǹ�",
					"���Ǹ�", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301402", "��˼",
					"���Ǹ�", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301403", "����",
					"���Ǹ�", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101301405", "����",
					"���Ǹ�", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310101", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310201", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310202", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310203", "�ٸ�", "�ٸ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310204", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310205", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310206", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310207", "��ɳ", "��ɳ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310208", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310209", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310210", "�Ͳ�", "�Ͳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310211", "��", "��",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310212", "�Ĳ�", "�Ĳ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310214", "��ͤ", "��ͤ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310215", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310216", "��ˮ", "��ˮ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310217", "��ɳ", "��ɳ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310220", "��ɳ", "��ɳ",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310221", "�ֶ�", "�ֶ�",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101310222", "��ָɽ",
					"��ָɽ", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101320101", "���", "���",
					"���"));
			WeatherAreaModels.add(new WeatherAreaModel("101320103", "�½�", "���",
					"���"));
			WeatherAreaModels.add(new WeatherAreaModel("101330101", "����", "����",
					"����"));
			WeatherAreaModels.add(new WeatherAreaModel("101330102", "���е�",
					"����", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101330103", "·����",
					"����", "����"));
			WeatherAreaModels.add(new WeatherAreaModel("101340101", "̨��", "̨��",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340102", "��԰", "̨��",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340103", "����", "̨��",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340104", "����", "̨��",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340201", "����", "����",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340202", "����", "����",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340203", "̨��", "����",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340204", "̨��", "����",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340205", "����", "����",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340401", "̨��", "̨��",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340402", "����", "̨��",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340403", "�û�", "̨��",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340404", "��Ͷ", "̨��",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340405", "����", "̨��",
					"̨��"));
			WeatherAreaModels.add(new WeatherAreaModel("101340406", "����", "̨��",
					"̨��"));
			return WeatherAreaModels;
		}
	}
}

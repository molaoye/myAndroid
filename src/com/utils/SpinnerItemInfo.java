package com.utils;

import java.util.ArrayList;

/**
 * ʾ��:
 * <br>
 * 1<br>
 * KVBean kVBean=new SpinnerItemInfo().commonKVInfo
                	.setKV(new SpinnerItemInfo().commonKVInfo.setKey("0").setValue("ȫ��"))
                	.setKV(new SpinnerItemInfo().commonKVInfo.setKey("����").setValue("����"))
                	.setKV(new SpinnerItemInfo().commonKVInfo.setKey("��;").setValue("��;"))
                	.setKV(new SpinnerItemInfo().commonKVInfo.setKey("����").setValue("����"))
                	.setKV(new SpinnerItemInfo().commonKVInfo.setKey("ǩ��").setValue("ǩ��"))
                	.setKV(new SpinnerItemInfo().commonKVInfo.setKey("δ�ɳ�").setValue("δ�ɳ�"))
                	.setKV(new SpinnerItemInfo().commonKVInfo.setKey("δ����").setValue("δ����"));
   ArrayAdapter<KVBean> adapter = new ArrayAdapter<KVBean>(GPSMonitor.this, android.R.layout.simple_spinner_item, kVBean);
   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   spinner.setAdapter(adapter);
   <br>
   
   2<br>
   KVBean kVBean=new SpinnerItemInfo().commonKVInfo;
   kVBean.setKV(new SpinnerItemInfo().commonKVInfo.setKey("0").setValue("ȫ��"));
   ArrayAdapter<KVBean> adapter = new ArrayAdapter<KVBean>(GPSMonitor.this, android.R.layout.simple_spinner_item, kVBean);
   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   spinner.setAdapter(adapter);
   <br>
   
   3<br>
   KVBean kVBean=new SpinnerItemInfo().commonKVInfo(new SpinnerItemInfo().new KVBean(CarGPSPhone,CarGPSPhone),
	                		new SpinnerItemInfo().new KVBean(GoodsGPSPhone,GoodsGPSPhone));
   ArrayAdapter<KVBean> adapter = new ArrayAdapter<KVBean>(GPSMonitor.this, android.R.layout.simple_spinner_item, kVBean);
   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   spinner.setAdapter(adapter);
 *
 */
public class SpinnerItemInfo {
	
	public KVBean commonKVInfo=new KVBean();
	
	public KVBean commonKVInfo(KVBean...kvBeans){
		for(KVBean kvBean:kvBeans){
			if(!kvBean.getValue().trim().equals("")){
				commonKVInfo.setKV(kvBean);
			}
		}
		return commonKVInfo;
	}
	
	@SuppressWarnings("serial")
	public class KVBean extends ArrayList<KVBean>{

		private String key;

		private String value;
		
		public KVBean(){
			
		}
		
		public KVBean(String key,String value){
			this.key=key;
			this.value=value;
		}

		public KVBean setKey(String key) {
			this.key = key;
			return this;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public KVBean setValue(String value) {
			this.value = value;
			return this;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return value;
		}
		
		public KVBean setKV(KVBean kVbean){
			commonKVInfo.add(kVbean);
			return commonKVInfo;
		}
		
	}

}

package com.nafshadigital.smartcallassistant.webservice;
import java.util.ArrayList;
import java.util.List;


public class MyWebServiceParameters {
	 List<MyParam> list = new ArrayList<MyParam>();
	 
	 public MyWebServiceParameters() {
		 list = new ArrayList<MyParam>();
	 }
	 
	 public void add(String key, Object value) {
		 list.add(new MyParam(key, value));
	 }
	 
	 public List<MyParam> getParamList() {
		 return list;
	 }
	 
}

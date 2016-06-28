package com.test.apiauto;

import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

public class Param {
	int row; //excel表格行数
	String run; //是否运行
	String method; //请求方法
	String IP; //掊口IP商品
	String url;  //接口url
	String param; //接口参数串
	String headType; //请求头类型
	String headValue; //请求头值
	String Return; //实际返回结果
	String assKey; //json结果中的字段名
	String actResult; //实际返回结果，要比对的结果字段
	String expResult; //预期结果
	String testResult; //测试结果
	ArrayList<String> parName = new ArrayList<>();  //请求参数名集合
	ArrayList<String> parValue = new ArrayList<>();  //请求参数值集合
	
	
	
	public String getExpResult() {
		return expResult;
	}
	public void setExpResult(String expResult) {
		this.expResult = expResult;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	
	public String getRun() {
		return run;
	}
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getParam() {
		return param;
	}

	
	public void setRun(String run) {
		this.run = run;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getReturn() {
		return Return;
	}
	public void setReturn(String return1) {
		Return = return1;
	}
	public String getActResult() {
		return actResult;
	}
	public void setActResult(String actResult) {
		this.actResult = actResult;
	}
	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getAssKey() {
		return assKey;
	}
	public void setAssKey(String assKey) {
		this.assKey = assKey;
	}
	public String getHeadType() {
		return headType;
	}
	public void setHeadType(String headType) {
		this.headType = headType;
	}
	public String getHeadValue() {
		return headValue;
	}
	public void setHeadValue(String headValue) {
		this.headValue = headValue;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	
}

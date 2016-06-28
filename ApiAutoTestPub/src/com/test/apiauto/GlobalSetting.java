package com.test.apiauto;

import java.nio.file.Path;

import org.apache.http.impl.client.CloseableHttpClient;

public class GlobalSetting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	///public static final String Path = "D:/商城接口测试用例.xlsx";
	//用例路径
	public static final String Path = "./case/商城接口测试用例.xlsx";
	public static final String sheetN = "apicase";
	//用例列号
	int CNRun;
	int CNMet;
	int CNIP;
	int CNUrl;
	int CNPar;
	int CNHeadType;
	int CNHeadValue;
	int CNRet;
	int CNAssKey;
	int CNActRes;
	int CNExpRes;
	int CNTesRes;
	//请求头
	String _csrf;  //login 
	String cookie;
	//httpclient连接
	CloseableHttpClient httpClient;
	
	public int getCNRun() {
		return CNRun;
	}
	public void setCNRun(int cNRun) {
		CNRun = cNRun;
	}
	public int getCNMet() {
		return CNMet;
	}
	public void setCNMet(int cNMet) {
		CNMet = cNMet;
	}
	public int getCNUrl() {
		return CNUrl;
	}
	public void setCNUrl(int cNUrl) {
		CNUrl = cNUrl;
	}
	public int getCNPar() {
		return CNPar;
	}
	public void setCNPar(int cNPar) {
		CNPar = cNPar;
	}
	public int getCNRet() {
		return CNRet;
	}
	public void setCNRet(int cNRet) {
		CNRet = cNRet;
	}
	public int getCNActRes() {
		return CNActRes;
	}
	public void setCNActRes(int cNActRes) {
		CNActRes = cNActRes;
	}
	public int getCNExpRes() {
		return CNExpRes;
	}
	public void setCNExpRes(int cNExpRes) {
		CNExpRes = cNExpRes;
	}
	public int getCNTesRes() {
		return CNTesRes;
	}
	public void setCNTesRes(int cNTesRes) {
		CNTesRes = cNTesRes;
	}
	public int getCNAssKey() {
		return CNAssKey;
	}
	public void setCNAssKey(int cNAssKey) {
		CNAssKey = cNAssKey;
	}
	public String get_csrf() {
		return _csrf;
	}
	public void set_csrf(String _csrf) {
		this._csrf = _csrf;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	public int getCNHeadType() {
		return CNHeadType;
	}
	public void setCNHeadType(int cNHeadType) {
		CNHeadType = cNHeadType;
	}
	public int getCNHeadValue() {
		return CNHeadValue;
	}
	public void setCNHeadValue(int cNHeadValue) {
		CNHeadValue = cNHeadValue;
	}
	public int getCNIP() {
		return CNIP;
	}
	public void setCNIP(int cNIP) {
		CNIP = cNIP;
	}
	
	

}

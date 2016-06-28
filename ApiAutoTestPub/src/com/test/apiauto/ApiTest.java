package com.test.apiauto;

import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;

public class ApiTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//初始化Excel全局参数
		GlobalSetting gl = new GlobalSetting();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		gl.setHttpClient(httpClient);  //共用一个连接
		//gl.set_csrf(zetlogin.scrf());
    	ExcelUtil.iniExcPar(gl);  //初始化全局参数--excel列号
    	ZETLoginIni.zetlogin(gl);
    	
/*    	int CNRun = gl.getCNRun();
    	int CNMet = gl.getCNMet();
    	int CNRl = gl.getCNUrl();
    	int CNPar = gl.getCNPar();
    	int CNRet = gl.getCNRet();
    	int CNActRes = gl.getCNActRes();
    	int CNExpRes = gl.getCNExpRes();
    	int CNTesRes = gl.getCNTesRes();
    	System.out.print(CNRun);*/
    	
    	//遍历测试用例存入List<Param>
		List<Param> read = ExcelUtil.read(gl);
		//调用客户端执行所有用例
		for (Param param : read) {
			int RunNum = read.indexOf(param)+1;
			System.out.println("迭代号："+RunNum+" 执行第---------"+param.getRow()+"行用例");
			System.out.println(param.getRun());	
			System.out.println(param.getMethod());	
			System.out.println(param.getUrl());
			System.out.println(param.getParam());
			System.out.println("执行用例ing…………");
			//HttpRequester.sendClient(gl, param);
			System.out.println("param.parName=="+param.parName.get(1));
			String parString = "";
			//System.out.println("==="+parString);
			for(int i=0; i< param.parName.size(); i++){
				if (param.parName.get(i).length()==0) {
					continue;
				}
				if (param.parValue.size()<param.parName.size()) {
					param.parValue.add("");
				}
				parString += param.parName.get(i)+"="+param.parValue.get(i)+"&";
			}
			parString = parString.substring(0, parString.length()-1);
			System.out.println(parString);
			param.setParam(parString);
			
			HttpClientUtil.httpClientSend(gl, param);
			
		}
		
	}



}

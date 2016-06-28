package com.test.apiauto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**httpclient post，get请求工具类
 * @author haha
 *
 */
public class HttpClientUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void httpClientSend(GlobalSetting gl, Param par){
		HttpResponse response = null;
		if (par.getMethod().equals("POST")) {
			response = httpClientPost(gl, par);
		}else if (par.getMethod().equals("GET")) {
			response = httpClietGet(gl, par);
		}
		String responseBodyString = getRequsetBody(response);
		//结果比较
		CompareRes.compare(gl, par, responseBodyString);
		//传给结果断言处理
		//System.out.println(""+responseBodyString);
	}

    
    /**get请求
     * @param url
     */
    public static HttpResponse httpClietGet(GlobalSetting gl, Param par){
    	//
		try {
			//CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpClient httpClient = gl.getHttpClient();
			
	        HttpGet getRequest = new HttpGet(par.getUrl());
	        //getRequest.setURI(new URI(url));
	        HttpResponse response = httpClient.execute(getRequest);
	        return response;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
        
    }
    
    /**post请求
     * @param url
     * @param paramString
     */
    public static HttpResponse httpClientPost(GlobalSetting gl, Param par){
    	System.out.println("进入post请求……");
    	//创建默认的httpClient实例. 
    	//CloseableHttpClient httpClient = HttpClients.createDefault();
    	CloseableHttpClient httpClient = gl.getHttpClient();
    	HttpPost postRequest = new HttpPost(par.getUrl());
        //postRequest.setURI(new URI(url));
        //设置请求头信息
        postRequest.setHeader("X-CSRF-TOKEN", gl.get_csrf());
        postRequest.setHeader("Cookie", gl.getCookie());
        if (! "".equals(par.getHeadValue())) {
        	postRequest.setHeader("UserType", par.getHeadValue());
		}
        //设置请求参数
        try {
			postRequest.setEntity(new UrlEncodedFormEntity(stringToList(par.getParam())));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //发起请求
        System.out.println("executing request " + postRequest.getURI());  
        
        HttpResponse response = null;
		try {
			response = httpClient.execute(postRequest);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //接收响应信息
        return response;
    }
    
    /**查询字符串转list
     * @param parString
     * @return
     */
    public static List<NameValuePair> stringToList(String parString){
    	System.out.println("转请求参数："+parString);
    	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    	String[] arrayStr1 = new String[] {};// 字符数组  
        String[] arrayStr2 = new String[] {};// 字符数组  
        arrayStr1 = parString.split("&");// 字符串转字符数组  
        for (String string1 : arrayStr1) {
     	   //System.out.println(arstring);
            arrayStr2 = string1.split("=");// 字符串转字符数组
//            for (String arstring2 : arrayStr2) {
//         	   System.out.println(arstring2);
// 			}
            String valueString = "";
            if (arrayStr2.length==2) {
 				valueString = arrayStr2[1];
 			}
            nvps.add(new BasicNameValuePair(arrayStr2[0], valueString));
 		}
        return nvps;
    }
    /**response结果
     * @param response  传入
     * @return  返回response 结果字符串
     */
    public static String getRequsetBody(HttpResponse response) {
        StringBuffer sb = new StringBuffer("");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"))) {
            String line;
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("返回信息： "+sb.toString());
        return sb.toString();
    }
}

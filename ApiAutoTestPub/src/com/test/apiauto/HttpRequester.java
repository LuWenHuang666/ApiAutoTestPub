package com.test.apiauto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.util.SystemOutLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HttpRequester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**发起post请求或get请求的客户端
	 * @param gl 全局参数
	 * @param pa 测试用例中 接口请求参数和比较参数
	 * @throws Exception
	 */
	public static void sendClient(GlobalSetting gl,Param pa){
		try {
			//发送的参数
		    String param= pa.getParam();
		    // 建立URL的连接
		    URL serverUrl= new URL(pa.getUrl());
		    URLConnection uct= serverUrl.openConnection();
		    HttpURLConnection hutc=(HttpURLConnection)uct;	
		    
		    //---------------------------------------------
		    //设置连接主机超时（单位：毫秒）  
		    //hutc.setConnectTimeout(100000);  
		    //设置从主机读取数据超时（单位：毫秒） 
		    //hutc.setReadTimeout(100000); 
		    // Post 请求不能使用缓存  
		    hutc.setUseCaches(false);  
		    // 连接后不自动跳转
		    hutc.setInstanceFollowRedirects(false);
		    // 设置通用的请求属性
		    hutc.setRequestProperty("Accept-Charset", "utf-8");
			// hutc.setRequestProperty("User-Agent", "systempatch");
		    hutc.setRequestProperty("Accpet-Encoding", "gzip");
		    hutc.setRequestProperty("X-CSRF-TOKEN", gl.get_csrf());
		    hutc.setRequestProperty("Cookie", "upgradePrompt=true; JSESSIONID=07F9594F37F26AA8159297A52B273186; zteVisitMark=5ae0070c56b640c29f211cc8ac14a936; Hm_lvt_92aa222fdb829a4d0ad2fbd087a383eb=1465465085; Hm_lpvt_92aa222fdb829a4d0ad2fbd087a383eb=1465472971");
		    hutc.setRequestProperty("Referer", "http://hzxbuyer.hsilu.com:8091/login/tologin.html");
		    hutc.setRequestProperty("Origin", "http://hzxbuyer.hsilu.com:8091");


		    // 接口的请求类型
		    String method = pa.getMethod();
		    hutc.setRequestMethod(method);
		    if (method.equals("POST")) {
			    // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在 http正文内，因此需要设为true, 默认情况下是false; 
			    hutc.setDoOutput(true);
			    // 设置是否从httpUrlConnection读入，默认情况下是true
			    hutc.setDoInput(true);	
		    }
		    //hutc.setAllowUserInteraction(true);
		    //----------------------------------------------------

		    hutc.connect();
		    // 开启流，写入数据data  此处getOutputStream会隐含的进行connect连接，从上述openConnection()至此的配置必须要在connect之前完成，
		    if (method.equals("POST")) {
			    OutputStream out=hutc.getOutputStream();
			    out.write(param.getBytes("UTF-8"));
			    out.flush();
			    out.close();
			}
		    
		    //返回的状态码  判断是否接收数据
		    int responseCode = hutc.getResponseCode();
			System.out.println("responseCode    " + responseCode);
			System.out.println("HttpURLConnection.HTTP_OK    " + HttpURLConnection.HTTP_OK);
			System.out.println(hutc.getResponseMessage());
			System.out.println(hutc.getHeaderField("Location"));
			

			
			Map<String, List<String>> headerFields = hutc.getHeaderFields();
            Set<Entry<String, List<String>>> entrySet = headerFields.entrySet();
            Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
            while(iterator.hasNext()) {
                Entry<String, List<String>> next = iterator.next();
                String key=next.getKey();
                List<String> value = next.getValue();
                if(key==null)
                    System.out.println(value.toString());
                else
                    System.out.println(key+":"+value.toString());
            }
            
		    // 获取返回的数据	
		    InputStream ins=hutc.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(ins,"UTF-8"));
		    String sg=reader.readLine();
		    StringBuffer buffer=new StringBuffer();
		    if (sg!= null){
		           buffer.append(sg);
		     }
		    System.out.println("接收返回值:" + buffer);
		    //结果断言
		    CompareRes.compare(gl,pa, buffer.toString());
		    
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("err: "+e.getMessage());
			ExcelUtil.writeCell((pa.getRow()),gl.getCNRet(),e.getMessage());
			ExcelUtil.writeCell((pa.getRow()),gl.getCNTesRes(),"Fail");
		}
	}
	
	/**发起post请求或get请求的客户端
	 * @param gl 全局参数
	 * @param pa 测试用例中 接口请求参数和比较参数
	 * @throws Exception
	 */
	public static HttpURLConnection httpUrlCon(String url,String params,String _csrf,String cookie){
		try {
			//发送的参数
		    String param= params;
		    // 建立URL的连接
		    URL serverUrl= new URL(url);
		    URLConnection uct= serverUrl.openConnection();
		    HttpURLConnection hutc=(HttpURLConnection)uct;	
		    
		    //---------------------------------------------
		    //设置连接主机超时（单位：毫秒）  
		    //hutc.setConnectTimeout(100000);  
		    //设置从主机读取数据超时（单位：毫秒） 
		    //hutc.setReadTimeout(100000); 
		    // Post 请求不能使用缓存  
		    hutc.setUseCaches(false);  
		    // 连接后不自动跳转
		    hutc.setInstanceFollowRedirects(false);
		    // 设置通用的请求属性
		    hutc.setRequestProperty("Accept-Charset", "utf-8");
			// hutc.setRequestProperty("User-Agent", "systempatch");
		    hutc.setRequestProperty("Accpet-Encoding", "gzip");
		    hutc.setRequestProperty("X-CSRF-TOKEN", _csrf);
		    hutc.setRequestProperty("Cookie", cookie);
		    hutc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		    
		    //hutc.setRequestProperty("Referer", "http://hzxbuyer.hsilu.com:8091/login/tologin.html");
		    //hutc.setRequestProperty("Origin", "http://hzxbuyer.hsilu.com:8091");
		    

		    // 接口的请求类型
		    // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在 http正文内，因此需要设为true, 默认情况下是false; 
		    hutc.setDoOutput(true);
		    // 设置是否从httpUrlConnection读入，默认情况下是true
		    hutc.setDoInput(true);	

		    //hutc.setAllowUserInteraction(true);
		    //----------------------------------------------------

		    hutc.connect();
		    // 开启流，写入数据data  此处getOutputStream会隐含的进行connect连接，从上述openConnection()至此的配置必须要在connect之前完成，
		    OutputStream out=hutc.getOutputStream();
		    out.write(param.getBytes("UTF-8"));
		    out.flush();
		    out.close();

		    
		    
		    //返回的状态码  判断是否接收数据
		    int responseCode = hutc.getResponseCode();
			System.out.println("responseCode    " + responseCode);
			System.out.println("HttpURLConnection.HTTP_OK    " + HttpURLConnection.HTTP_OK);
			//System.out.println(hutc.getResponseMessage());
			//System.out.println(hutc.getHeaderField("Location"));
			
			Map<String, List<String>> headerFields = hutc.getHeaderFields();
            Set<Entry<String, List<String>>> entrySet = headerFields.entrySet();
            Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
            while(iterator.hasNext()) {
                Entry<String, List<String>> next = iterator.next();
                String key=next.getKey();
                List<String> value = next.getValue();
                if(key==null)
                    System.out.println(value.toString());
                else
                    System.out.println(key+":"+value.toString());
            }
            
            
            System.out.println("hutc.getResponseMessage():"+hutc.getResponseMessage());
		    // 获取返回的数据	
            InputStream ins=hutc.getInputStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(ins,"UTF-8"));
		    String sg=reader.readLine();
		    StringBuffer buffer=new StringBuffer();
		    if (sg!= null){
		           buffer.append(sg);
		     }
		    System.out.println("接收返回值:" + buffer);
		    //结果断言
		    //CompareRes.compare(gl,pa, buffer.toString());
		    
		    return hutc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("err: "+e.getMessage());
		}
		return null;
	}
	

	/**发起post请求或get请求的客户端
	 * @param gl 全局参数
	 * @param pa 测试用例中 接口请求参数和比较参数
	 * @return 
	 * @throws Exception
	 */
	public static void hClient(String url,String params,String _csrf,String cookie){
		try {
			//创建httpclient对象  
			HttpClient httpClient = new DefaultHttpClient();
		    //创建post方式请求对象  
	        
	        HttpGet request = new HttpGet();
	        request.setURI(new URI(url));
	        HttpResponse response = httpClient.execute(request);
	        
	        //设置header信息  
/*	        httpPost.setHeader("Accept-Charset", "utf-8");  
	        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 
	        httpPost.setHeader("X-CSRF-TOKEN", _csrf);  
	        httpPost.setHeader("Cookie", cookie);  */

	        //取响应内容
	        StringBuffer sb = new StringBuffer("");
	        try (BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"))) {
	            String line;
	            String NL = System.getProperty("line.separator");
	            while ((line = in.readLine()) != null) {
	                sb.append(line + NL);
	            }
	        }
	        String responseBody = sb.toString();
	        //System.out.println("返回信息"+responseBody);
	        Document doc = Jsoup.parse(responseBody);
	        Elements csrf = doc.getElementsByAttributeValue("name", "_csrf");
			String _csrf1 = csrf.attr("content");
			System.out.println("_csrf1="+_csrf1);

	        //遍历输出响应头
			String setcookie = null;
	        for (Header header : response.getAllHeaders()) {
	            //System.out.println(header.getName() + " : " + header.getValue());
	            if (header.getName().equals("Set-Cookie")) {
					setcookie = header.getValue();
				}
	        }
			System.out.println("setcookie="+setcookie);
			
	        HttpPost postRequest = new HttpPost();
	        postRequest.setURI(new URI("http://hzxbuyer.hsilu.com:8091/view/login/loginvalidate"));
	        //设置请求头
	        postRequest.setHeader("Cookie", setcookie);
	        postRequest.setHeader("X-CSRF-TOKEN", _csrf);
	        //设置请求参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("account", "regedit0726"));
	        nvps.add(new BasicNameValuePair("password", "b1a0b8ac4a382341f9fcaa0807dab17f"));
	        nvps.add(new BasicNameValuePair("jCaptcha", ""));
	        postRequest.setEntity(new UrlEncodedFormEntity(nvps));
	        
	        response = httpClient.execute(postRequest);
	        responseBody = getRequsetBody(response);
	        System.out.println("responseBody"+responseBody);
	 
			
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("err: "+e.getMessage());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
        System.out.println("requsetbody");
        return sb.toString();
    }


}

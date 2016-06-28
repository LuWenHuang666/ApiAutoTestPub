package com.test.apiauto;
/**
 * 现实响应头代码
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.common.LengthConverter;

import java.util.Set;
public class ShowHttpHeaders {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String web1="http://hzxbuyer.hsilu.com:8091/view/login/loginvalidate";
        //String web2="http://news.baidu.com/";
        //String web3="https://linux.cn/";
        //String web4="http://www.taobao.com/";
        ArrayList<String> websites=new ArrayList<String>();
        websites.add(web1);
        //websites.add(web2);
        //websites.add(web3);
        //websites.add(web4);
        ShowHttpHeaders showHttpHeaders = new ShowHttpHeaders(websites);
        showHttpHeaders.getHeaders();
	}

    //要查找的网址
    private ArrayList<String> websites;

    /**
     * 构造函数
     * @param websites 网站列表
     */
    public ShowHttpHeaders(ArrayList<String> websites) {
        this.websites=websites;
    }
    
    /**
     * 获取响应头
     * 打印到控制台
     */
    public void getHeaders() {
        if(websites==null) {
            System.err.println("查询网址不能为空！");
            return;
        }
        try {
            for(String website:websites) {
                System.out.println("----------------网站："+website+"HTTP响应头---------------");
                URL url = new URL(website);
                URLConnection connection = url.openConnection();
                Map<String, List<String>> headerFields = connection.getHeaderFields();
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
                System.out.println("");
            }
        } catch ( IOException e) {
            System.err.println("无法查询网址！");
        }
    }
    
    /**
     * 获取响应头
     * 打印到控制台
     */
    public static String getHeaderField(HttpURLConnection connection,String headerField) {
        try {
 
                System.out.println("----------------网站："+"HTTP响应头---------------");
                //URL url = new URL(website);
                //URLConnection connection = url.openConnection();
                Map<String, List<String>> headerFields = connection.getHeaderFields();
                Set<Entry<String, List<String>>> entrySet = headerFields.entrySet();
                Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
                while(iterator.hasNext()) {
                    Entry<String, List<String>> next = iterator.next();
                    String key=next.getKey();
                    List<String> value = next.getValue();
                    if(key==null){
                        System.out.println(value.toString());
                    }else{
                        System.out.println(key+":"+value.toString());
                        if (key.equals(headerField)) {
                        	System.out.println("长度 ===="+value.toString().length());
    						return value.toString().substring(1, value.toString().length()-1);
    					}
                    }
                System.out.println("");
            }
        } catch ( Exception e) {
            System.err.println("无法查询网址！");
        }
		return null;
		
    }
    
    
}

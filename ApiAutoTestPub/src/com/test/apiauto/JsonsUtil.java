package com.test.apiauto;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.json.JSONException;


/**
 * 使用json-lib构造和解析Json数据
 */
public class JsonsUtil {

	public static void main(String[] args) throws JSONException, ParseException {
		// TODO Auto-generated method stub
		String json = "{\"people\":[{\"firstName\":\"Jason\",\"lastName\":\"Hunter\",\"email\":\"bbbb\"},{\"firstName\":\"Elliotte\",\"lastName\":\"Harold\",\"email\":\"cccc\"}]}";
		ParseJson(json,"lastName");
	}

    /**将Bean转换成Map
     * 将Map转换Json数据
     */public static String BuildJson(Param param) throws JSONException {
    	 Map<String, String> map1 = new HashMap<String, String>();
         map1.put("Run", param.getRun());
         map1.put("Method", param.getMethod());
         map1.put("Url", param.getUrl());
         map1.put("Param", param.getParam());
//         map1.put("expResu", param.getExpResu());
    	 // JSON格式数据解析对象
        JSONObject jo = new JSONObject();
        // 将Map转换为JSONArray数据
        JSONArray ja = new JSONArray();
        ja.add(map1);
        System.out.println("JSONArray对象数据格式："+ja.toString());
        jo.put("map", ja);
        System.out.println("最终构造的JSON数据格式："+jo.toString());
        return jo.toString();

    }

    /**
     * 解析Json数据
     * @param jsonString  json串
     * @param key   查询的json中的名称
     * @return
     */
    public static String ParseJson(String jsonString, String key){
    	 String value = null;
    	 
    	 JSONObject jsonObject = JSONObject.fromObject(jsonString);
    	 Iterator<?> it = jsonObject.keys();
    	 while (it.hasNext()) {
			String keyString = it.next().toString();
			if (keyString.equals(key)) {
				value = jsonObject.getString(key);
			}
		 }

    	 
    	 //按json数组解释
        /*JSONArray jsonArray = JSONArray.fromObject(jsonString);
        for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			value = jsonObject.getString(key);
			System.out.println(jsonObject);
		}*/
        return value;
    }
}

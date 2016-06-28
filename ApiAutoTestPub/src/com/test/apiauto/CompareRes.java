package com.test.apiauto;

public class CompareRes {

    /**测试结果比较
     * @param param
     * @param actResult
     */
    public static void compare(GlobalSetting gl,Param par,String Return){
    	
    	int row = par.getRow();  //行号
    	int CNARet = gl.getCNRet();  //返回结果 -列号
        int CNActRes = gl.getCNActRes(); //断言值-列号
        int CNTesRes=gl.getCNTesRes();  //测试结果-列号
      
        String AssKey = par.getAssKey(); //断言字段名

        //返回结果写入Return列
        ExcelUtil.writeCell((row),CNARet,Return);
        //取出断言值
        String ActRes = JsonsUtil.ParseJson(Return, AssKey);
        ExcelUtil.writeCell(row,CNActRes,ActRes);
        
        //判断 实际结果actResult 和  预期结果expResult是否一样  写入测试结果testResult
        if(par.getExpResult().trim().equals(ActRes)){
            ExcelUtil.writeCell(row,CNTesRes,"Pass");
        }else{
            ExcelUtil.writeCell(row,CNTesRes,"Fail");
        }
    }
    
/*    java判断字符串中包含特定字符串方法：
    使用正则表达式进行判断。
  源代码：*/
  	public static boolean containsString(String textString,String keyString) {
  		String str=textString;  //待判断的字符串
  		String reg=".*"+keyString+".*";  //判断字符串中是否含有特定字符串ll
  		System.out.println(str.matches(reg));
  		return str.matches(reg);
  	}

}

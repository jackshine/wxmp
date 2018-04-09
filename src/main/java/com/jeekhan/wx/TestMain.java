package com.jeekhan.wx;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.json.JSONException;
import org.json.JSONObject;

import com.jeekhan.wx.api.AccountQRHandle;
import com.jeekhan.wx.controller.FansAction;

public class TestMain {
	public static void main(String[] args) throws DocumentException, JSONException, IOException {
		testUser();	
	}
	
	
	public static void testUser() throws JSONException {
		FansAction user = new FansAction();

	}
	
	public static void testAccount() throws JSONException, IOException {
		AccountQRHandle handle = new AccountQRHandle();
		JSONObject ret1 = handle.createTempTicket(111, 6000);
		System.out.println("输出：" + ret1);
		JSONObject ret2 = handle.createTempTicket("mfyx2", 6000);
		System.out.println("输出：" + ret2);
		JSONObject ret3 = handle.createTicket(555);
		System.out.println("输出：" + ret3);
		JSONObject ret4 = handle.createTicket("mfyx");
		System.out.println("输出：" + ret4);	
//		String ticket = "gQGv8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyTXV5VFFhLU1icF8xVkZINDFxMUEAAgT5k8RaAwRwFwAA"; 
//		File file = handle.getQRCodeImg(ticket);
//		File tmp = new File("/Users/jeekhan/Pictures/tmp.jpg");
//		org.apache.commons.io.FileUtils.copyFile(file, tmp);
		
		String longUrl = "https://www.mofangyouxuan.com/sdfhjs=sdfsf&ertet=343y5y3i4&df=3rfdsgdfgsfsfsdffsfsgsdfsf&ssdddd=dkjfhgsfslkfbjslkfgshfgsf";
		JSONObject ret5 = handle.getShortUrl(longUrl);
		System.out.println("输出：" + ret5);	
	}

}

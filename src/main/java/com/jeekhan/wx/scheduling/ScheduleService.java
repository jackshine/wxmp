package com.jeekhan.wx.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class ScheduleService {
	
	//  每分钟启动
	//@Scheduled(cron = "0 0/1 * * * ?")
	public void timerToNow(){
		System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	} 
	
	//固定间隔启动（从上次开始计算）
	@Scheduled(fixedRate = 5000)
    public void timerToZZP(){
        System.out.println("ZZP:" + new Random().nextLong() + new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }
	
	//指定间隔启动（从上次结束计算）
    //@Scheduled(initialDelay = 50000,fixedDelay = 50000)
    public void timerToReportCount(){
        for (int i = 0; i < 10; i++){
            System.out.println("<================its" + i + "count===============>" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }
    }

    //@Scheduled(initialDelay = 50000,fixedRate = 6000)
    public void timerToReport(){
        for (int i = 0; i < 10; i++){
            System.out.println("<================delay :" + i + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "count===============>");
        }
    }
    
    

}

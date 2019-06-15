package com.sw.common.utils;

import java.util.Calendar;
import java.util.Date;

public class UserHelper {
	
	public static int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance(); 
		  
        if (cal.before(birthDay)) { 
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!"); 
        } 
        int yearNow = cal.get(Calendar.YEAR); 
        int monthNow = cal.get(Calendar.MONTH); 
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
        cal.setTime(birthDay); 
  
        int yearBirth = cal.get(Calendar.YEAR); 
        int monthBirth = cal.get(Calendar.MONTH); 
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
  
        int age = yearNow - yearBirth; 
  
        if (monthNow <= monthBirth) { 
            if (monthNow == monthBirth) { 
                if (dayOfMonthNow < dayOfMonthBirth) age--; 
            }else{ 
                age--; 
            } 
        } 
        System.out.println("age:"+age); 
        return age;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		int age = UserHelper.getAge(new Date());
		System.out.println(age);
	}

}

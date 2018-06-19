package exeuctionEngine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import config.ActionKeywords;

public class TestSteps {

	public static Method methods[];
	public static ActionKeywords keywords;
	
	private static  void initializeMethods() {
		keywords = new ActionKeywords();
		methods = keywords.getClass().getMethods();
	}
	
	public static void executeStep(String keyword, String pageObject, String data) {
		
		if(null == methods) 
			initializeMethods();
		
		for (Method method : methods) {
			if(method.getName().equalsIgnoreCase(keyword)) {
				try {
					method.invoke(keyword, pageObject, data);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
	}
}

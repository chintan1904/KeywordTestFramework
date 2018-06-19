package exeuctionEngine;

import java.lang.reflect.Method;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;

public class TestSteps {

	public static Method methods[];
	public static ActionKeywords keywords;
	
	private static  void initializeMethods() {
		keywords = new ActionKeywords();
		methods = keywords.getClass().getMethods();
	}
	
	public static void executeStep(int iTestStep) {
		
		if(null == methods) 
			initializeMethods();
		
		String pageObject = ExcelUtils.getCellData(iTestStep, Constants.COL_LOCATOR, Constants.TEST_STEPS_SHEET);
		String data = ExcelUtils.getCellData(iTestStep, Constants.COL_TESTSTEP_DATA, Constants.TEST_STEPS_SHEET);
		String keyword = ExcelUtils.getCellData(iTestStep, Constants.COL_ACTIONKEYWORD, Constants.TEST_STEPS_SHEET);
		
		for (Method method : methods) {
			if(method.getName().equalsIgnoreCase(keyword)) {
				try {
					method.invoke(keyword, pageObject, data);
				} catch (Exception e) {
					DriverScript.failMessage = e.getCause().getMessage();
					Log.error("Class TestSteps | Method " + method.getName() + " | PageObject " + pageObject + " | TestData " + data);
					DriverScript.bResult = false;
				}
			}
		}
	}
}

package exeuctionEngine;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;

public class DriverScript {

	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method methods[];
	public static Properties OR;
	public static boolean bResult;
	public static int iTestStep;
	public static String testStepData;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public static String failMessage;

	public DriverScript() {

		try {
			LoadPropertiesFile();
			setActionKeyword();
			setReportConfiguration();
			setTestSuiteExcelFile();
		} catch (Exception e) {
			Log.error("Class DriverScript | Method DriverScript | Exception Desc --- " + e.getMessage());
		}
	}

	private static void setActionKeyword() {
		try {
			actionKeywords = new ActionKeywords();
			methods = actionKeywords.getClass().getMethods();
		} catch (Exception e) {
			Log.error("Class DriverScript | Method setActionKeyword | Exception Desc --- " + e.getMessage());
		}
	}

	private static void LoadPropertiesFile() {

		try {
			DOMConfigurator.configure("log4j.xml");
			FileInputStream fs = new FileInputStream(Constants.OR_PATH);
			OR = new Properties(System.getProperties());
			OR.load(fs);
		} catch (Exception e) {
			Log.error("Class DriverScript | Method LoadPropertiesFile | Exception Desc --- " + e.getMessage());
		}
	}

	private static void setReportConfiguration() {
		try {
			extent = new ExtentReports(Constants.EXTENT_REPORT_PATH, true);
			extent.loadConfig(new File(Constants.EXTENT_CONFIG_PATH));
		} catch (Exception e) {
			Log.error("Class DriverScript | Method setReportConfiguration | Exception Desc --- " + e.getMessage());
		}
	}

	private static void setTestSuiteExcelFile() {
		try {
			ExcelUtils.setExcelFile(Constants.TEST_SCRIPT_PATH);
		} catch (Exception e) {
			Log.error("Class DriverScript | Method setTestSuiteExcelFile | Exception Desc --- " + e.getMessage());
		}
	}

	public static void main(String[] args) {

		DriverScript startEngine = new DriverScript();
		startEngine.execute_tests();
	}

	public void execute_tests() {

		int iTotalTestCases = ExcelUtils.getRowCount(Constants.TEST_CASES_SHEET);
		for (int iTestCase = 1; iTestCase < iTotalTestCases; iTestCase++) {
			bResult = true;

			String runMode = ExcelUtils.getCellData(iTestCase, Constants.COL_RUNMODE, Constants.TEST_CASES_SHEET);
			String testCaseId = ExcelUtils.getCellData(iTestCase, Constants.COL_TESTCASE_ID,
					Constants.TEST_CASES_SHEET);
			test = extent.startTest(testCaseId);

			if ("yes".equalsIgnoreCase(runMode)) {
				int iTestStepsStart = ExcelUtils.getRowContains(Constants.TEST_STEPS_SHEET, Constants.COL_TESTCASE_ID,
						testCaseId);
				int iTestStepsEnd = ExcelUtils.getTestStepsCount(Constants.TEST_STEPS_SHEET, testCaseId,
						iTestStepsStart);
				Log.startTestCase(testCaseId);
				bResult = true;
				for (iTestStep = iTestStepsStart; iTestStep < iTestStepsEnd; iTestStep++) {
					sPageObject = ExcelUtils.getCellData(iTestStep, Constants.COL_LOCATOR, Constants.TEST_STEPS_SHEET);
					testStepData = ExcelUtils.getCellData(iTestStep, Constants.COL_TESTSTEP_DATA,
							Constants.TEST_STEPS_SHEET);
					sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.COL_ACTIONKEYWORD,
							Constants.TEST_STEPS_SHEET);
					executeAction();
					if (!bResult) {
						break;
					}
				}
				if (bResult) {
					test.log(LogStatus.PASS, "Test Passed");
					//ExcelUtils.setCellData(Constants.TEST_CASES_SHEET, iTestCase, Constants.COL_TESTCASE_RESULT, Constants.KEYWORD_PASS);
				} else {
					//ExcelUtils.setCellData(Constants.TEST_CASES_SHEET, iTestCase, Constants.COL_TESTCASE_RESULT, Constants.KEYWORD_FAIL);
					Log.error("Exception Desc --- " + failMessage);
					test.log(LogStatus.FAIL, failMessage);
				}
			} else {
				test.log(LogStatus.SKIP, "Test Runmode set to NO");
				//ExcelUtils.setCellData(Constants.TEST_CASES_SHEET, iTestCase, Constants.COL_TESTCASE_RESULT, Constants.KEYWORD_SKIP);
			}
			Log.endTestCase(testCaseId);
			extent.endTest(test);
		}
		extent.flush();
		extent.close();
	}

	public static void executeAction() {

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equalsIgnoreCase(sActionKeyword)) {
				try {
					methods[i].invoke(actionKeywords, sPageObject, testStepData);
					//ExcelUtils.setCellData(Constants.TEST_STEPS_SHEET, iTestStep, Constants.COL_TESTSTEP_RESULT, Constants.KEYWORD_PASS);
				} catch (Exception e) {
					failMessage = e.getCause().getMessage();
					Log.error("Class DriverScript | Method " + methods[i].getName() + " | PageObject " + sPageObject + " | TestData " + testStepData);
					//ExcelUtils.setCellData(Constants.TEST_STEPS_SHEET, iTestStep, Constants.COL_TESTSTEP_RESULT, Constants.KEYWORD_FAIL);
					bResult = false;
				}
			}
		}
	}
}
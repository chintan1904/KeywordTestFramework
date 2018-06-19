package config;

public class Constants {

	// Test Script - Excel file
	public static final String TEST_SCRIPT_PATH = "TestSuite/TestCases.xlsx";
	public static final String TEST_SCRIPT_FILE_NAME = "DataEngine.xlsx";
	public static final String OR_PATH = "src/main/java/config/OR.properties";

	// Test Script - Sheets
	public static final String TEST_STEPS_SHEET = "Test Steps";
	public static final String TEST_CASES_SHEET = "Test Cases";
	public static final String TEST_SUMMARY_SHEET = "Test Summary";
	public static final String TEST_PAGE_OBJECTS = "Page Objects";

	// Test Steps - Column position in excel file
	public static final int COL_TESTCASE_ID = 0;
	public static final int COL_TESTSCENARIO_ID = 1;
	public static final int COL_LOCATOR = 3;
	public static final int COL_ACTIONKEYWORD = 4;
	public static final int COL_TESTSTEP_DATA = 5;
	public static final int COL_TESTSTEP_RESULT = 6;

	// Test Cases - Column position in excel file
	public static final int COL_RUNMODE = 2;
	public static final int COL_TESTCASE_RESULT = 3;
	
	//Test Summary - Cell position
	public static final int ROW_BROWSER = 2;
	public static final int COL_BROWER = 1;
			
	//Page Objects - Column position in excel file
	public static final int COL_PAGENAME = 0;
	public static final int COL_PAGEOBJECT = 1;
	public static final int COL_BY = 2;
	public static final int COL_SELECTOR = 3;
	public static final int COL_PARENT = 4;
	
	//Extent Report path
	public static final String EXTENT_REPORT_PATH = "Report/MyReport.html";
	public static final String EXTENT_CONFIG_PATH = "Report/extent-config.xml";
	
	// Test Data
	public static final String BASE_URL = "https://www.phptravels.net/admin";
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	public static final String KEYWORD_SKIP = "SKIP";
}
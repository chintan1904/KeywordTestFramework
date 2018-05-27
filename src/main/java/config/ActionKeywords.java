package config;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static exeuctionEngine.DriverScript.OR;

import org.openqa.selenium.By;

import com.codeborne.selenide.Configuration;
import com.relevantcodes.extentreports.LogStatus;

import utility.Log;
import static exeuctionEngine.DriverScript.test;
public class ActionKeywords {

	public ActionKeywords() {

		Configuration.baseUrl = Constants.BASE_URL;
	}

	public static void navigate(String pageObject, String data) {
		
		logMessage("Navigate to URL " + data);
		open(data);
	}

	public static void enter_text(String pageObject, String data) {
		
		logMessage("Enter value \"" + data + "\" in field \"" + pageObject + "\"");
		$(locator(pageObject)).setValue(data);
	}

	public static void click(String pageObject, String data) {
		
		logMessage("Click Element \"" + pageObject + "\"");
		$(locator(pageObject)).click();
	}

	public static void waitFor(String pageObject, String data) throws InterruptedException {
		
		logMessage("Perform wait operation for 5 Seconds");
		Thread.sleep(5000);
	}

	private static By locator(String sPageObject) {

		String locator = OR.getProperty(sPageObject);
		String[] s = locator.split("::");
		String locateBy = s[0].trim();
		String locateValue = s[1].trim();

		if ("byText".equalsIgnoreCase(locateBy)) {
			return byText(locateValue);
		} else if ("byName".equalsIgnoreCase(locateBy)) {
			return byName(locateValue);
		}
		return null;
	}
	
	private static void logMessage(String message) {
		Log.info(message);
		test.log(LogStatus.INFO, message);
	}
}
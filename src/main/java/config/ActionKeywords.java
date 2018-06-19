package config;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static exeuctionEngine.DriverScript.test;

import com.codeborne.selenide.Configuration;
import com.relevantcodes.extentreports.LogStatus;

import exeuctionEngine.Locator;
import utility.Log;
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
		$(Locator.findElement(pageObject)).setValue(data);
	}

	public static void click(String pageObject, String data) {
		
		logMessage("Click Element \"" + pageObject + "\"");
		$(Locator.findElement(pageObject)).click();
	}

	public static void waitFor(String pageObject, String data) throws InterruptedException {
		
		logMessage("Perform wait operation for 5 Seconds");
		Thread.sleep(5000);
	}

	private static void logMessage(String message) {
		Log.info(message);
		test.log(LogStatus.INFO, message);
	}
}
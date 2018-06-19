package exeuctionEngine;

import org.openqa.selenium.By;
import static com.codeborne.selenide.Selectors.*;
import config.Constants;
import utility.ExcelUtils;

public class Locator {

	public static By findElement(String pageObject) {
		int rowNum = ExcelUtils.getRowContains(Constants.TEST_PAGE_OBJECTS, Constants.COL_PAGEOBJECT, pageObject);
		String by = ExcelUtils.getCellData(rowNum, Constants.COL_BY, Constants.TEST_PAGE_OBJECTS);
		String selector = ExcelUtils.getCellData(rowNum, Constants.COL_SELECTOR, Constants.TEST_PAGE_OBJECTS);
		
		if("xpath".equalsIgnoreCase(by))
			return byXpath(selector);
		else if("css".equalsIgnoreCase(by))
			return byCssSelector(selector);
		else if("name".equalsIgnoreCase(by))
			return byName(selector);
		else if("id".equalsIgnoreCase(by))
			return byId(selector);
		else if("link text".equalsIgnoreCase(by))
			return byLinkText(selector);
		else if("partial link text".equalsIgnoreCase(by))
			return byPartialLinkText(selector);
		else if("text".equalsIgnoreCase(by))
			return byText(selector);
		else
			return byXpath(selector);
	}
}
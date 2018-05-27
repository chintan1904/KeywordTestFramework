package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.Constants;
import exeuctionEngine.DriverScript;

public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFRow Row;
	private static XSSFCell Cell;

	public static void setExcelFile(String path) throws Exception {
		try {
			FileInputStream excelFile = new FileInputStream(path);
			ExcelWBook = new XSSFWorkbook(excelFile);
		} catch (Exception e) {
			Log.error("Class ExcelUtils | Method setExcelFile | Exception desc --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static String getCellData(int rowNum, int colNum, String sheet) {

		try {
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			Cell = ExcelWSheet.getRow(rowNum).getCell(colNum, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			String cellData = Cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			Log.error("Class ExcelUtils | Method getCellData | Exception desc --- " + e.getMessage());
			DriverScript.bResult = false;
			return "";
		}
	}

	public static int getRowCount(String sheet) {
		try {
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			return ExcelWSheet.getLastRowNum() + 1;
		} catch (Exception e) {
			Log.error("Class ExcelUtils | Method getRowCount | Exception desc --- " + e.getMessage());
			DriverScript.bResult = false;
			return 0;
		}
	}

	public static int getRowContains(String sheet, int column, String testCaseId) {
		int i;
		try {
			int rowCount = getRowCount(sheet);
			for (i = 0; i < rowCount; i++) {
				if (testCaseId.equalsIgnoreCase(getCellData(i, column, sheet)))
					break;
			}
			return i;
		} catch (Exception e) {
			Log.error("Class ExcelUtils | Method getRowContains | Exception desc --- " + e.getMessage());
			DriverScript.bResult = false;
			return 0;
		}
	}

	public static int getTestStepsCount(String sheet, String testCaseId, int testStepsStart) {
		int i;
		try {
			for (i = testStepsStart; i < getRowCount(sheet); i++) {
				if (!testCaseId.equalsIgnoreCase(ExcelUtils.getCellData(i, Constants.COL_TESTCASE_ID, sheet))) {
					return i;
				}
			}
			// This code is called for last test case in a worksheet.
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			return ExcelWSheet.getLastRowNum() + 1;

		} catch (Exception e) {
			Log.error("Class ExcelUtils | Method getTestStepsCount | Exception desc --- " + e.getMessage());
			DriverScript.bResult = false;
			return 0;
		}
	}

	/**
	 * Deprecated this method as Results are not longer stored in Excel file.
	 * @param sheet
	 * @param row
	 * @param col
	 * @param result
	 */
	@Deprecated()
	public static void setCellData(String sheet, int row, int col, String result) {

		try {
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			Row = ExcelWSheet.getRow(row);
			Cell = Row.getCell(col, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(col);
				Cell.setCellValue(result);
			} else {
				Cell.setCellValue(result);
			}

			FileOutputStream fileOut = new FileOutputStream(Constants.TEST_SCRIPT_PATH);
			ExcelWBook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			Log.error("Class ExcelUtils | Method setCellData | Exception desc --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
}
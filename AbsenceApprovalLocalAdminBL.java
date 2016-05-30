/*
 * Script Name : AbsenceApprovalLocalAdminBL
 * Created by : Akshay Chouhan (ImpactQA)
 * Created on : 3-Dec-2015
 * Purpose : To approve absence from local admin profile
 */
package com.scripts.businessLogic;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import utils.report.template.LogStatus;
import utils.screenshot.TestUtils;

import com.scripts.baseClass.Driver;
import com.scripts.lib.commonFunction.CommonUtills;
import com.scripts.pageObjectRepository.AbsenceApprovalPL;
import com.scripts.pageObjectRepository.LoginPL;

public class AbsenceApprovalLocalAdminBL extends Driver {

	AbsenceApprovalPL absenceApprovalPage = PageFactory.initElements(driver,
			AbsenceApprovalPL.class);
	TestUtils screenLib = new TestUtils();
	CommonUtills commonLib = new CommonUtills();
	LoginPL loginPage = PageFactory.initElements(driver, LoginPL.class);

	/**
	 * This method is used to approve absence as local admin
	 * 
	 * @param EmpFName
	 *            : (String) First name of the employee
	 * @param EmpLName
	 *            : (String) Last name of the employee
	 * @param Location
	 *            : (String) Location of the employee
	 * @param DateStart
	 *            : (String) Start date of absence
	 * @param DateEnd
	 *            : (String) End date of the absence
	 * @param Reason
	 *            : (String) Reason for the absence
	 * @throws InterruptedException
	 */
	public void approveAbsence(String EmpFName, String EmpLName,
			String Location, String DateStart, String DateEnd, String Reason)
			throws InterruptedException {
		reports.startTest("Approve/Deny Absence");
		commonLib.click(absenceApprovalPage.getTabAbsenceVacancy());
		commonLib.waitForPageToLoad();
		commonLib.click(absenceApprovalPage.getLinkAbsenceApproval());
		commonLib.waitForPageToLoad();
		reports.log(LogStatus.INFO, "ABSENCE APPROVAL",
				"Loading the 'Absence Approval' page");
		if (!commonLib.isDisplayed(absenceApprovalPage.getTxtBoxSearchFrom())) {
			reports.log(LogStatus.FAIL, "ABSENCE APPROVAL",
					"'Search From' text box not appeared");
			reports.attachScreenshot(screenLib.CaptureScreenshot(
					"Absence Approval", driver));
		} else {
			reports.log(LogStatus.PASS, "ABSENCE APPROVAL",
					"'Absence Approval' page loaded successfully");
			reports.attachScreenshot(screenLib.CaptureScreenshot(
					"Absence Approval", driver));
			commonLib.typeText(absenceApprovalPage.getTxtBoxSearchFrom(),
					DateStart);
			commonLib
					.typeText(absenceApprovalPage.getTxtBoxSearchTo(), DateEnd);
			commonLib.click(absenceApprovalPage.getDrpDownLcoation());
			commonLib.waitForPageToLoad();
			List<WebElement> options = absenceApprovalPage.getOptions();
			for (WebElement option : options) {
				if (commonLib.getText(option).equals(Location)) {
					commonLib.click(option);
					break;
				}
			}
			commonLib.click(absenceApprovalPage.getBtnSearch());
			commonLib.waitForPageToLoad();
			List<WebElement> groupColumnHeaders = absenceApprovalPage
					.getGroupColumnHeaders();
			while (groupColumnHeaders.size() >= 1) {
				int i = 0;
				commonLib.click(groupColumnHeaders.get(i));
				groupColumnHeaders = absenceApprovalPage
						.getGroupColumnHeaders();
			}
			List<WebElement> rows = absenceApprovalPage.getRows();
			List<WebElement> columnSelect = absenceApprovalPage
					.getColumnSelect();
			int i = 0;
			commonLib.waitForPageToLoad();
			for (WebElement row : rows) {
				if (commonLib.getText(row).contains(EmpFName)
						&& commonLib.getText(row).contains(EmpLName)
						&& commonLib.getText(row).contains(DateStart)
						&& commonLib.getText(row).contains(DateEnd)
						&& commonLib.getText(row).contains(Reason)
						&& commonLib.getText(row).contains("Pending")) {
					commonLib.click(columnSelect.get(i));
					commonLib.waitForPageToLoad();
					break;
				} else {
					i++;
				}
			}

			commonLib.click(absenceApprovalPage.getBtnApprove());
			commonLib.waitForElementPresent(loginPage.getNotification());
			String notification = commonLib
					.getText(loginPage.getNotification());
			reports.log(LogStatus.INFO, "ABSENCE APPROVAL",
					"Approving absence as Local Admin");
			if (!notification.contains("info")) {
				reports.log(LogStatus.FAIL, "ABSENCE APPROVAL",
						"Absence not approved");
				reports.attachScreenshot(screenLib.CaptureScreenshot(
						"Absence Approval", driver));
			} else {
				reports.log(LogStatus.PASS, "ABSENCE APPROVAL",
						"Absence for date '" + DateStart
								+ "' approved successfully");
				reports.attachScreenshot(screenLib.CaptureScreenshot(
						"Absence Approval", driver));
			}
		}
	}

	/**
	 * This method is used to deny absence as local admin
	 * 
	 * @param EmpFName
	 *            : (String) First name of the employee
	 * @param EmpLName
	 *            : (String) Last name of the employee
	 * @param Location
	 *            : (String) Location of the employee
	 * @param DateStart
	 *            : (String) Start date of absence
	 * @param DateEnd
	 *            : (String) End date of the absence
	 * @param Reason
	 *            : (String) Reason for the absence
	 * @throws InterruptedException
	 */
	public void denyAbsence(String EmpFName, String EmpLName, String Location,
			String DateStart, String DateEnd, String Reason)
			throws InterruptedException {
		commonLib
				.typeText(absenceApprovalPage.getTxtBoxSearchFrom(), DateStart);
		commonLib.typeText(absenceApprovalPage.getTxtBoxSearchTo(), DateEnd);
		commonLib.click(absenceApprovalPage.getDrpDownLcoation());
		commonLib.waitForPageToLoad();
		List<WebElement> options = absenceApprovalPage.getOptions();
		for (WebElement option : options) {
			if (commonLib.getText(option).equals(Location)) {
				commonLib.click(option);
				break;
			}
		}
		commonLib.click(absenceApprovalPage.getBtnSearch());
		commonLib.waitForPageToLoad();
		List<WebElement> groupColumnHeaders = absenceApprovalPage
				.getGroupColumnHeaders();
		while (groupColumnHeaders.size() >= 1) {
			int i = 0;
			commonLib.click(groupColumnHeaders.get(i));
			groupColumnHeaders = absenceApprovalPage.getGroupColumnHeaders();
		}
		List<WebElement> rows = absenceApprovalPage.getRows();
		List<WebElement> columnSelect = absenceApprovalPage.getColumnSelect();
		commonLib.waitForPageToLoad();
		int i = 0;
		for (WebElement row : rows) {
			if (commonLib.getText(row).contains(EmpFName)
					&& commonLib.getText(row).contains(EmpLName)
					&& commonLib.getText(row).contains(DateStart)
					&& commonLib.getText(row).contains(DateEnd)
					&& commonLib.getText(row).contains(Reason)
					&& commonLib.getText(row).contains("Pending")) {
				commonLib.click(columnSelect.get(i));
				commonLib.waitForPageToLoad();
				break;
			} else {
				i++;
			}
		}

		commonLib.click(absenceApprovalPage.getBtnDeny());
		commonLib.waitForElementPresent(loginPage.getNotification());
		String notification = commonLib.getText(loginPage.getNotification());
		reports.log(LogStatus.INFO, "ABSENCE DENIAL",
				"Denial of absence as Local Admin");
		if (!notification.contains("info")) {
			reports.log(LogStatus.FAIL, "ABSENCE DENIAL", "Absence not denied");
			reports.attachScreenshot(screenLib.CaptureScreenshot(
					"Absence Denial", driver));
		} else {
			reports.log(LogStatus.PASS, "ABSENCE DENIAL", "Absence for date '"
					+ DateStart + "' denied successfully");
			reports.attachScreenshot(screenLib.CaptureScreenshot(
					"Absence Denial", driver));
		}

	}

}

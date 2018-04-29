package com.TestDefinitionLayer;

import core.DriverManager;
import org.testng.annotations.Test;
import reporting.ExtentReports.ExtentTestManager;

public class OldReportTest extends DriverManager {

    @Test(priority = 0, description="Invalid Login Scenario with wrong username and password.")
    public void invalidLoginTest_InvalidUserNameInvalidPassword () throws InterruptedException {
        //ExtentReports Description
        ExtentTestManager.getTest().setDescription("Invalid Login Scenario with wrong username and password.");
        ExtentTestManager.startTest("Test1","New Test");
        System.out.print("End Of Test");
        ExtentTestManager.endTest();
    }

}

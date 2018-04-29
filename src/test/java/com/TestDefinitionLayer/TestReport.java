//package com.TestDefinitionLayer;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import org.testng.annotations.Test;
//
//public class TestReport {
//
//
//    public static void main(String[] args) throws Exception {
//        // start reporters
//        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output\\extent.html");
//
//        // create ExtentReports and attach reporter(s)
//        ExtentReports extent = new ExtentReports();
//        extent.attachReporter(htmlReporter);
//
//        // creates a toggle for the given test, adds all log events under it
//        ExtentTest test = extent.createTest("MyFirstTest", "Sample description");
//
//        // log(Status, details)
//        test.log(Status.INFO, "This step shows usage of log(status, details)");
//
//        // info(details)
//        test.info("This step shows usage of info(details)");
//
//        // log with snapshot
//        test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());
//
//        // test with snapshot
//        test.addScreenCaptureFromPath("screenshot.png");
//
//        // calling flush writes everything to the log file
//        extent.flush();
//    }
//}

package com.TestDefinitionLayer;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pageobjects.HomeScreenPage;
import pageobjects.InnerApiDemosPage;
import pageobjects.LogTextBoxPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
public class TC001_Test {
    private static AndroidDriver driver;
    private HomeScreenPage homeScreen;
    private InnerApiDemosPage innerApiDemoScreen;
    private LogTextBoxPage logTextBoxPage;

    @BeforeSuite
    public void DesiredCapabilities() throws MalformedURLException {
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";

        URL url = new URL(URL_STRING);

        File app = new File("ApiDemos-debug.apk");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);

        driver = new AndroidDriver(url, caps);
        homeScreen = new HomeScreenPage(driver);
        innerApiDemoScreen = new InnerApiDemosPage(driver);
        logTextBoxPage = new LogTextBoxPage(driver);
    }


    @Test
    public void test_login() throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        homeScreen.verifyHeader();
        homeScreen.selectTextButton();

        innerApiDemoScreen.verifyHeader();
        innerApiDemoScreen.selectLogTextBoxButton();

        logTextBoxPage.verifyHeader();
        logTextBoxPage.selectAddButton();

        String expectedPanelText = "This is a test";
        String actualPanelText = logTextBoxPage.getPanelText();

        System.out.println("Checking panel text...");

//        TestUtils.outputIfMatchPassOrFail(expectedPanelText, actualPanelText);
        assertThat(actualPanelText,containsString(expectedPanelText));
    }

    @AfterSuite
    public void tearDownAppium() throws Exception {
        System.out.println("\nTearing Down Driver.");
        driver.quit();
    }



//    public class Main {
//        public static void main(String[] args) {
//            // start reporters
//            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
//
//            // create ExtentReports and attach reporter(s)
//            ExtentReports extent = new ExtentReports();
//            extent.attachReporter(htmlReporter);
//
//            // creates a toggle for the given test, adds all log events under it
//            ExtentTest test = extent.createTest("MyFirstTest", "Sample description");
//
//            // log(Status, details)
//            test.log(Status.INFO, "This step shows usage of log(status, details)");
//
//            // info(details)
//            test.info("This step shows usage of info(details)");
//
//            // log with snapshot
//            test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());
//
//            // test with snapshot
//            test.addScreenCaptureFromPath("screenshot.png");
//
//            // calling flush writes everything to the log file
//            extent.flush();
//        }
//    }
}

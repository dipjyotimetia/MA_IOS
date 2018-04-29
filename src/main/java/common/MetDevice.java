package common;

import io.appium.java_client.ios.IOSDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MetDevice {

    private IOSDriver driver = null;
    private boolean mobileDeviceOpenFlag = false;

    private final String CONFIG_FILE = "src/main/resources/config.properties";
    private InputStream inputStream = null;
    private Properties properties = null;
    private final static Logger logger = Logger.getLogger(MetDevice.class);

    public MetDevice(IOSDriver driver) {
        this.driver = driver;
    }

    public enum availableContexts {
        WEBVIEW, NATIVE_APP, VISUAL
    }

    public RemoteWebDriver switchToContext(availableContexts context) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        Map<String, String> params = new HashMap<>();
        params.put("name", context.toString());
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
        return driver;
    }

    public String getCurrentContextHandle() {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        String context = (String) executeMethod.execute(
                DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
        return context;
    }

    public void Reboot() {
        Map<String, Object> params2 = new HashMap<>();
        try {
            driver.executeScript("mobile:handset:reboot", params2);
        } catch (Exception e) {
            driver.executeScript("mobile:handset:reboot", params2);
        }
    }

    public List<String> getContextHandles() {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        List<String> context = (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
        return context;
    }

    public void clickBackButton() {
        Map<String, Object> params1 = new HashMap<>();
        params1.put("keySequence", "BACK");
        driver.executeScript("mobile:presskey", params1);
    }

    public void home() {
        Map<String, Object> params2 = new HashMap<>();
        driver.executeScript("mobile:handset:ready", params2);
    }

    public IOSDriver getDriver() {
        return driver;
    }

    public IOSDriver getDomDriver() {
        switchToContext(availableContexts.WEBVIEW);
        return driver;
    }

    protected void setMobileDeviceOpenFlag(boolean mobileOpenFlag) {
        mobileDeviceOpenFlag = mobileOpenFlag;
    }

    public void closeMobileDevice() {
        if (null != driver) {
            driver.close();
            setMobileDeviceOpenFlag(false);
        }
    }

    public void closeApplication() {
        try {
            Map<String, Object> params1 = new HashMap<>();
            params1.put("name", getAppName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() {
        if (mobileDeviceOpenFlag && null != getDriver()) {
            closeMobileDevice();
        }
    }

    public String getAppName() {
        return lookupProperty(CONFIG_FILE,"appName");
    }

    public void startApplication() throws Exception{
        closeApplication();
        System.out.println("Starting Application");
        try {
            Map<String, Object> params2 = new HashMap<>();
            params2.put("name",getAppName());
            driver.executeScript("mobile:application:open",params2);
            switchToContext(availableContexts.NATIVE_APP);
            Thread.sleep(3000);
            //Extent Report
        }
        catch (Exception e){
            throw new Exception("App Not Opened");
        }
    }

    public void launchApplication() throws Exception{
        System.out.println("Launch Application");
        try {
            Map<String, Object> params2 = new HashMap<>();
            params2.put("name",getAppName());
            driver.executeScript("mobile:application:open",params2);
            switchToContext(availableContexts.NATIVE_APP);
            Thread.sleep(3000);
            //Extent Report
        }
        catch (Exception e){
            throw new Exception("App not launched");
        }
    }

    public void uninstallApplication(){
        try {
            Map<String,Object> params2 = new HashMap<>();
            params2.put("identifier",lookupProperty(CONFIG_FILE,"appIdentifier"));
            driver.executeScript("mobile:application:uninstall",params2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean installApplication(){
        if (lookupProperty(CONFIG_FILE,"snapshot").equals("UAT")){
            uninstallApplication();
        }
        try {
            Map<String,Object> params = new HashMap<>();
            params.put("file",lookupProperty(CONFIG_FILE,"application")
                    +lookupProperty(CONFIG_FILE,"appFileName"));
            params.put("instrument","instrument");
            driver.executeScript("mobile:application:install",params);
            return true;
        }
        catch (Exception e){
            StringBuilder builder = new StringBuilder();
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement traceElement : trace)
                builder.append(traceElement+"\n");
            logger.error("installation of app for execution of class"+ this.getClass().getName()+"failed.>>>>>>>>>>"+e.getMessage()+"Stacktrace"+builder.toString());
        }
        return false;
    }

    public String lookupProperty(String propFileName, String nameOfProperty) {
        properties = new Properties();
        try {
            inputStream = new FileInputStream(propFileName);
            properties.load(inputStream);
            inputStream.close();
            if (properties != null) {
                return properties.getProperty(nameOfProperty);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setWifiOn(){
        NetworkConnection mobileDriver = (NetworkConnection) driver;
        if (mobileDriver.getNetworkConnection() != NetworkConnection.ConnectionType.AIRPLANE_MODE) {
            // enabling Airplane mode
            mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.AIRPLANE_MODE);
        }
        if (mobileDriver.getNetworkConnection() != NetworkConnection.ConnectionType.WIFI) {
            // enabling Airplane mode
            mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.WIFI);
        }
    }
}

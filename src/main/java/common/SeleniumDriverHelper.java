package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class SeleniumDriverHelper {
    protected MetDevice metDevice;
    protected final int DEFAULT_FLUENTWAIT_TIMEOUT = 20;
    private final int DEFAULT_THRESHOLD = 20;
    private InputStream inputStream = null;
    private Properties properties = null;
    static Dictionary<String, Dictionary<String, String>> testData = new Hashtable<String, Dictionary<String, String>>();
    public final String CONFIG_FILE = "src/main/resources/config.properties";

    public SeleniumDriverHelper(MetDevice metDevice) {
        this.metDevice = metDevice;
    }

    public final WebElement fluentWait(final By locator, long Timeout) {
        metDevice.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebElement element = (WebElement) (new WebDriverWait(metDevice.getDriver(), Timeout))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        metDevice.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return element;

    }

    protected List<WebElement> fluentWaitForXpath(final By locator,long Timeout){
        metDevice.getDriver().manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(
                metDevice.getDriver())
                .withTimeout(Duration.ofSeconds(Timeout))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        List<WebElement> webElement = wait.until(new Function<WebDriver, List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver webDriver) {
                return webDriver.findElement(locator);
            }
        });
        metDevice.getDriver().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        return webElement;
    }

    protected final WebElement getElement(By object) throws Exception{
        try {
            return fluentWait(object,DEFAULT_FLUENTWAIT_TIMEOUT);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Element not found");
        }
    }

    protected List<WebElement> getElements(By object) throws Exception{
        try {
            return fluentWaitForXpath(object,DEFAULT_FLUENTWAIT_TIMEOUT);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Element not found");
        }
    }

    protected final WebElement getElement(By object,long elementWaitTime) throws Exception{
        try {
            return fluentWait(object,elementWaitTime);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Element not found");
        }
    }

    public boolean checkElementExists(By fieldValue) throws Exception{
        WebElement elementName = getElement(fieldValue,DEFAULT_FLUENTWAIT_TIMEOUT);
        if (elementName == null){
            return false;
        }
        else {
            return true;
        }
    }

    public void swipeScreen(String startXY,String endXY){
        String startX =(startXY.replace(",",",").split("%"))[0];
        String startY = (startXY.replace(",",",").split("%"))[1];

        String endX =(endXY.replace(",",",").split("%"))[0];
        String endY = (endXY.replace(",",",").split("%"))[1];


    }

    private int getX(int x){
        return (int) ((metDevice.getDriver().manage().window().getSize().height*x)/100);
    }
    private int getY(int y){
        return (int) ((metDevice.getDriver().manage().window().getSize().height*y)/100);
    }

    public void populatePasscodeFiled(String data) throws Exception{
        Thread.sleep(2000);
        metDevice.getDriver().getKeyboard().sendKeys(data);
        Thread.sleep(2000);
    }

    public void dragUpFivePercent(){
        final Map<String,Object> params = new HashMap<>();
        params.put("location","20%,61%,20%,50%,80%,50%,80%,54%");
        params.put("duration","1");
        metDevice.getDriver().executeScript("mobile:touch:drag",params);
    }

}

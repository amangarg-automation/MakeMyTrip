package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver=new ThreadLocal<>();
    private DriverFactory(){}
    public static WebDriver getDriver(String browserName) throws MalformedURLException {
        browserName=browserName.trim().toLowerCase();
        if(driver.get()==null)
        {
            switch(browserName)
            {
                case "chrome":
                    ChromeOptions options=new ChromeOptions();
                    options.addArguments("--incognito");
                    options.addArguments("--start-maximized");
                    options.addArguments("--disable-notifications");
                    Map<String,Integer> pref=new HashMap<>();
                    pref.put("profile.default_content_setting_values.geolocation",2);
                    options.setExperimentalOption("prefs",pref);
                    driver.set(new ChromeDriver(options));
                    driver.get().manage().deleteAllCookies();
                    break;
                case "edge":
                    EdgeOptions edgeOptions=new EdgeOptions();
                    edgeOptions.addArguments("--incognito");
                    edgeOptions.addArguments("--start-maximized");
                    edgeOptions.addArguments("--disable-notifications");
                    driver.set(new EdgeDriver(edgeOptions));
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions=new FirefoxOptions();
                    firefoxOptions.addArguments("-private");
                    driver.set(new FirefoxDriver(firefoxOptions));
                    driver.get().manage().deleteAllCookies();
                    driver.get().manage().window().maximize();
                    break;
                default: throw new RuntimeException("No Browser with name "+browserName+" is found");
            }
        }
        return driver.get();
    }
    public static void closeDriver()
    {
        if(driver.get()!=null)
        {
            driver.get().quit();
        }
        driver.remove();
    }
}

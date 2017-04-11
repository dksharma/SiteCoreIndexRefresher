import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by dks02 on 4/11/2017.
 */
public class SiteCoreCacheRefresher
{
   public static void main(String[] args){
       System.out.println("Refreshing started at " + new Date());
       int cycles = 1000;
       String url =
               //"http://www.google.com";
               "http://kcms/sitecore/admin/cache.aspx";
               //"http://kcms/sitecore";
       String chromeDriverPath = "C:\\A123\\installations\\chromedriver_win32\\chromedriver.exe";

       // Set the system property of the chromedriver executable's location
       System.setProperty("webdriver.chrome.driver", chromeDriverPath);

       Map<String, Object> prefs = new HashMap<String, Object>();
        //To Turns off multiple download warning
       prefs.put("profile.default_content_settings.popups", 0);

       prefs.put( "profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 );

        //Turns off download prompt
       prefs.put("download.prompt_for_download", false);
       prefs.put("credentials_enable_service", false);

       //To Stop Save password propmts
       prefs.put("password_manager_enabled", false);

       ChromeOptions options = new ChromeOptions();
       options.addArguments("chrome.switches","--disable-extensions");

       //To Disable any browser notifications
       options.addArguments("--disable-notifications");

       options.addArguments("--enable-automatic-password-saving");

       //To disable yellow strip info bar which prompts info messages
       options.addArguments("disable-infobars");

       options.setExperimentalOption("prefs", prefs);


       options.addArguments("--test-type");

       DesiredCapabilities cap = DesiredCapabilities.chrome();
       cap.setCapability(ChromeOptions.CAPABILITY, options);
       cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

       // Create an instance of web driver
       WebDriver driver = new ChromeDriver(cap);

       try
       {
           //Open a URL in browser
           driver.get(url);

           // The page asks for user name and password.
           // so I make thread sleep for 3o seconds so that
           // I can put in the user name and password and then
           // let it go in the for loop for refresh cycles.
           // Sleep for 5 seconds
           TimeUnit.SECONDS.sleep(30);

           WebElement element = driver.findElement(By.name("c_refresh"));

           if (element != null)
               for (int i=1; i < cycles; i++)
               {
                   element.click();
                   System.out.println("Clicked " + i + " times at " + new Date());
                   TimeUnit.MINUTES.sleep(10);
                   element = driver.findElement(By.name("c_refresh"));
               }

       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       System.out.println("Refresh Cycle Ended at " + new Date());
       //Close the driver, I hide it because I wanted to see the windows's progress
       //driver.quit();
   }
}

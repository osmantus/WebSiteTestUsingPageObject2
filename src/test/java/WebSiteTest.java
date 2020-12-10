import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class WebSiteTest {

    private WebDriver driver;
    private WebDriverWait driverWait;

    @BeforeMethod
    public void initTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://rozetka.com.ua/");

        driverWait = new WebDriverWait(driver, 30);
    }

    @Test
    public void pageObjectWebSiteTest() throws InterruptedException {
        SiteMainPageLogic mainSite = new SiteMainPageLogic(driver, driverWait).categoriesCallingClick();

        NotebooksPageLogic notebooksPageLogic = mainSite.getNotebooksChapterAndOpenIt().findFirstNotebook();

        notebooksPageLogic.buySelectedNotebook().showBasketWindow();
        notebooksPageLogic.getInfoAboutFirstProductInBasket();

        mainSite.comparePreBoughtNotebookTitle();
    }

    @AfterMethod
    public void closeTest() {
        driver.quit();
    }
}

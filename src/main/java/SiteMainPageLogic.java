import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SiteMainPageLogic {

    private By categories = By.xpath("//a[@class='menu-categories__link']");
    private By notebooksItem = By.xpath("//a[@class='menu__hidden-title']");

    private WebDriver driver;
    private WebDriverWait driverWait;

    private NotebooksPageLogic notebooksPageLogic;

    public SiteMainPageLogic(WebDriver driver, WebDriverWait driverWait) {
        this.driver = driver;
        this.driverWait = driverWait;

        String title = "Интернет-магазин ROZETKA™: официальный сайт самого популярного онлайн-гипермаркета в Украине";
        if (!title.equals(driver.getTitle())) {
            //throw new IllegalStateException("This is not the main page");
        }
    }

    public SiteMainPageLogic categoriesCallingClick() {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(categories));
        driver.findElement(categories).click();
        return this;
    }

    public NotebooksPageLogic getNotebooksChapterAndOpenIt() {

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(notebooksItem));
        driver.findElement(notebooksItem).click();

        notebooksPageLogic = new NotebooksPageLogic(driver, driverWait);
        return notebooksPageLogic;
    }

    public void comparePreBoughtNotebookTitle() throws InterruptedException {
        Notebook selectedNotebook = notebooksPageLogic.getSelectedNotebook();
        Notebook inBasketNotebook = notebooksPageLogic.getInBasketNotebook();
        if (notebooksPageLogic.areDifferentNotebooks(selectedNotebook, inBasketNotebook)) {
            Assert.fail("Наименование товара, добавленного в корзину, не совпадает с товаром на главном экране магазина");
        }
    }
}

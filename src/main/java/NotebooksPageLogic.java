import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class NotebooksPageLogic {

    private By firstNotebookPageElem = By.xpath("//a[@class='goods-tile__heading']");
    private By buyingBtn = By.xpath("//button[contains(@class, 'buy-button goods-tile__buy-button')]");
    private By preBoughtBasketBtn = By.xpath("//a[contains(@class, 'header-actions__button_state_active')]");

    private BasketWindowLogic basketWindow;

    private Notebook selectedNotebook;
    private Notebook inBasketNotebook;

    private WebDriver driver;
    private WebDriverWait driverWait;

    public NotebooksPageLogic(WebDriver driver, WebDriverWait driverWait) {
        this.driver = driver;
        this.driverWait = driverWait;
    }

    private Notebook setNotebookTitle(Notebook notebook, String notebookTitle) {
        if (notebook == null) {
            notebook = new Notebook(notebookTitle);
        } else {
            notebook.setTitle(notebookTitle);
        }
        return notebook;
    }

    public NotebooksPageLogic findFirstNotebook() {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(firstNotebookPageElem));
        WebElement firstNotebookWE = driver.findElement(firstNotebookPageElem);

        String notebookTitle = firstNotebookWE.getAttribute("innerText");
        selectedNotebook = setNotebookTitle(selectedNotebook, notebookTitle);
        return this;
    }

    public Notebook getSelectedNotebook() {
        return selectedNotebook;
    }

    public Notebook getInBasketNotebook() {
        return inBasketNotebook;
    }

    public NotebooksPageLogic buySelectedNotebook() {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(buyingBtn));
        driver.findElement(buyingBtn).click();
        return this;
    }

    public BasketWindowLogic showBasketWindow() {
        if (basketWindow == null) {
            driver.findElement(preBoughtBasketBtn).click();

            basketWindow = new BasketWindowLogic(driver, driverWait);
        }
        return basketWindow;
    }

    public NotebooksPageLogic getInfoAboutFirstProductInBasket() {
        if (basketWindow == null) {
            showBasketWindow();
        }

        if (basketWindow != null) {
            List<WebElement> boughtProductsList = basketWindow.getListOfPreBoughtProducts();
            if (boughtProductsList.size() == 0) {
                Assert.fail("В корзину не было добавлено ни одного товара");
            } else if (boughtProductsList.size() > 1) {
                Assert.fail("В корзину было добавлено больше 1 товара");
            } else {
                WebElement preBoughtNotebook = boughtProductsList.get(0);
                String notebookTitle = basketWindow.getTitleByProduct(preBoughtNotebook);
                //System.out.println("notebookTitle: [" + notebookTitle + "]");
                inBasketNotebook = setNotebookTitle(inBasketNotebook, notebookTitle);
            }

        } else {
            Assert.fail("Окно корзины с товарами, отмеченными для покупки, не отображается перед выбором товара в корзине");
        }
        return this;
    }

    public boolean areDifferentNotebooks(Notebook notebook1, Notebook notebook2) {

        String onGridProductTitle = notebook1.getTitle();
        if (basketWindow.hasJustOneProduct()) {
            int productsCounter = basketWindow.getOneProductCopiesNumber();
            if (productsCounter == 0) {
                Assert.fail("В корзину не было добавлено ни одного экземпляра товара");
            } else if (productsCounter > 1) {
                Assert.fail("В корзину было добавлено больше 1 экземпляра товара");
            } else {
                String inBasketProductTitle = notebook2.getTitle();
                //System.out.println("onGridProductTitle: [" + onGridProductTitle + "]");
                //System.out.println("inBasketProductTitle: [" + inBasketProductTitle + "]");
                if (!onGridProductTitle.equals(inBasketProductTitle)) {
                    return true;
                }
            }
        } else {
            return false;
        }

        return false;
    }
}

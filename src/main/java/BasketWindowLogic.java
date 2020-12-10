import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class BasketWindowLogic {

    private By preBoughtProducts = By.className("cart-list__item");
    private By preBoughtProductsCounterItem = By.xpath("//input[contains(@class, 'cart-counter__input')]");
    private By preBoughtProduct = By.xpath("//a[@class='cart-product__title']");

    private WebDriver driver;
    private WebDriverWait driverWait;

    private List<WebElement> boughtProductsList;

    public BasketWindowLogic(WebDriver driver, WebDriverWait driverWait) {
        this.driver = driver;
        this.driverWait = driverWait;

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(preBoughtProducts));
        List<WebElement> upperList = driver.findElements(preBoughtProducts);
        boughtProductsList = new ArrayList<WebElement>();
        WebElement productTitlePageElem;
        for (WebElement productUpperElem : upperList) {
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(preBoughtProduct));
            productTitlePageElem = productUpperElem.findElement(preBoughtProduct);
            boughtProductsList.add(productTitlePageElem);
        }
    }

    public List<WebElement> getListOfPreBoughtProducts() {
        return boughtProductsList;
    }

    public int getOneProductCopiesNumber() {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(preBoughtProductsCounterItem));
        WebElement preBoughtProductsCounterField = driver.findElement(preBoughtProductsCounterItem);
        String counterAsStr = preBoughtProductsCounterField.getAttribute("value");
        int productsNumber = Integer.parseInt(counterAsStr);

        return productsNumber;
    }

    public String getFirstProductTitle() {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(preBoughtProduct));
        WebElement product = driver.findElement(preBoughtProduct);
        return product.getAttribute("innerText");
    }

    public String getTitleByProduct(WebElement product) {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(preBoughtProduct));
        return product.getAttribute("innerText");
    }

    public boolean hasJustOneProduct() {
        if (boughtProductsList == null) {
            return false;
        } else {
            if (boughtProductsList.size() != 1) {
                return false;
            } else {
                return true;
            }
        }
    }
}

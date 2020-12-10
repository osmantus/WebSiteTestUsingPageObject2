import org.openqa.selenium.WebDriverException;

public class CustomException extends WebDriverException {

    private String exMessage;

    public CustomException(String message) {
        super(message);
        exMessage = message;
    }

    public void printExMessage() {
        System.out.println(exMessage);
    }
}

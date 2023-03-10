import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TownList {

    WebDriver driver;

    @FindBy(css="button[class=\"dp-1h71hub\"][role=\"menuitem\"]:first-child")
    @Getter
    private WebElement menuItem;

    public TownList(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
}

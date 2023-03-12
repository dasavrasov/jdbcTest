import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    WebDriver driver;

    @FindBy(css="div a img[src*=\"logo\"]")
    @Getter
    private WebElement logo;

    @FindBy(xpath="//div[contains(@class,'dp-Group-root')]/a[contains(@href,'information')]/div")
    @Getter
    private WebElement info;

    @FindBy(xpath="//div[contains(.,'Подготовка к полёту')]")
    @Getter
    private WebElement flightInfo;

    @FindBy(xpath="//div[contains(.,'Полезная информация')]")
    @Getter
    private WebElement infoUseful;

    @FindBy(xpath="//div[contains(.,'О компании')]")
    @Getter
    private WebElement about;

    @Getter
    private TicketSearch ticketSearch;
    @Getter
    private BookingSearch bookingSearch;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.ticketSearch=new TicketSearch(driver);
        this.bookingSearch=new BookingSearch(driver);
        PageFactory.initElements(driver,this);
    }

    public String getTitle(){
        return driver.getTitle();
    }
}

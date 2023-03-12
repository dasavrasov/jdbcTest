import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookingSearch {

    WebDriver driver;

    //Номер заказа или билета

    @FindBy(css="input[placeholder=\"Номер заказа или билета\"]")
    @Getter
    private WebElement orderNo;
    //Фамилия клиента

    @FindBy(css="input[placeholder=\"Фамилия клиента\"]")
    @Getter
    private WebElement fio;
    //Кнопка Поиск

    @FindBy(css="button[type=\"submit\"]")
    @Getter
    private WebElement searchBtn;

    @FindBy(xpath="//div[@ng-if='vm.errorMessage'][contains(.,'Заказ с указанными параметрами не найден')]")
    @Getter
    private WebElement errorMessage;

    public BookingSearch(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

}

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TicketSearch {
    WebDriver driver;

    //Откуда
    @FindBy(css="input[placeholder=\"Откуда\"][aria-controls*=\"dropdown\"]")
    @Getter
    private WebElement from;

    // Куда,
    @FindBy(css="input[placeholder=\"Куда\"][aria-controls*=\"dropdown\"]")
    @Getter
    private WebElement to;

    // Дата вылета Туда,
    @FindBy(css="input[id=\":Rbcualmqm:\"][placeholder=\"Туда\"]")
    @Getter
    private WebElement departure;

    // Дата вылета Обратно
    @FindBy(css="input[id=\":Rrcualmqm:\"][placeholder=\"Обратно\"]")
    @Getter
    private WebElement arrival;

    //Кнопка Поиск
    @FindBy(css="button[type=\"submit\"]")
    @Getter
    private WebElement searchBtn;

    //Управление бронированием
    @FindBy(xpath="//div[@class='__mantine-ref-text dp-vbfp0a'][contains(.,'Управление бронированием')]")
    @Getter
    private WebElement booking;

    //Номер заказа или билета
    @FindBy(css="input[placeholder=\"Номер заказа или билета\"]")
    @Getter
    private WebElement orderNo;

    //Фамилия клиента
    @FindBy(css="input[placeholder=\"Фамилия клиента\"]")
    @Getter
    private WebElement fio;

    @FindBy(xpath="//div[@ng-if='vm.errorMessage'][contains(.,'Заказ с указанными параметрами не найден')]")
    @Getter
    private WebElement errorMessage;

    @Getter
    private TownList townList;

    public TicketSearch(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        this.townList=new TownList(driver);
    }
}

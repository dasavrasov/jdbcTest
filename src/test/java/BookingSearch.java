import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;
import lombok.NoArgsConstructor;

import java.time.Duration;


@NoArgsConstructor
public class BookingSearch {
    //Номер заказа или билета
    @Getter
    private SelenideElement orderNo=$("input[placeholder=\"Номер заказа или билета\"]");
    //Фамилия клиента

    @Getter
    private SelenideElement fio=$("input[placeholder=\"Фамилия клиента\"]");
    //Кнопка Поиск

    @Getter
    private SelenideElement searchBtn=$("button[type=\"submit\"]");

    @Getter
    private SelenideElement errorMessage=$x("//div[@ng-if='vm.errorMessage']");

    @Step("Проверка наличия поля <Номер заказа или билета>")
    public void checkOrderNo(){
        orderNo.shouldBe(visible);
    }
    @Step("Проверка наличия поля <Фамилия клиента>")
   public void checkFio(){
        fio.shouldBe(visible);
    }

    @Step("Проверка наличия поля кнопки <Поиск>")
   public void checkSearchBtn(){
       searchBtn.shouldBe(visible);
    }

    @Step("Ввод значения {text} в поле <Номер заказа или билета>")
    public void inputOrderNo(String text){
        orderNo.setValue(text);
    }
    @Step("Ввод значения {text} в поле <Номер заказа или билета>")
    public void inputFio(String text){
        fio.setValue(text);
    }

    @Step("Кликнуть на кнопку <Поиск>")
    public void searchBtnClick(){
        searchBtn.click();
    }

    @Step("На экране отображается текст ошибки {message}")
    public void checkError(String message){
        //ждем пока откроется новая вкладка
        switchTo().window(1, Duration.ofSeconds(30));
        errorMessage.shouldHave(exactText(message));
    }

}

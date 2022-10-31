import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    public static String getDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void firstFormTest() {
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCity());
        String date = getDate(DataGenerator.generateDate());
        SelenideElement data = $("[data-test-id='date'] input");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.click();
        data.setValue(date);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='success-notification'] button").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + date), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void firstFormTestV2() {
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCity());
        LocalDate oneDate = DataGenerator.generateDate();
        String date = getDate(oneDate);
        String secondDate = getDate(oneDate.plusDays(3));
        SelenideElement data = $("[data-test-id='date'] input");
        data.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.click();
        data.setValue(date);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='success-notification'] button").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + date), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $("[data-test-id='success-notification'] button").click();
        data.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        data.click();
        data.setValue(secondDate);
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] button").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] .notification__content").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] button").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + secondDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrderFormTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestForm() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Гаврилов Арсений");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'order-success']")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotBeLatinInName() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Georgiy Artemov");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotBeNumbersInName() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("236589741");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeMoreThanOneLetterInName() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Л");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotBeOnlyDashesInName() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("--");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeDoubleNamesInName() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Петр Янковский-Герасимовский");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'order-success']")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBePlusInPhone() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Болотов Игорь");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("79526548974");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeAnyRegionInPhone() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Болотов Игорь");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+39526548974");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'order-success']")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotBeOnly0InPhone() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Болотов Игорь");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+00000000000");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotBeOnly1InPhone() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Болотов Игорь");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+11111111111");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeCheckedCheckbox() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Болотов Игорь");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+79012345678");
        driver.findElement(By.tagName("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'agreement'].input_invalid")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeLastNameInName() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("Мария");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotBeSymbolsInName() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("%+-*@() ");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeLimitsInName() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= 'name'] input")).sendKeys("ямогууказатьсколькоугодноссимволовкакзначениемоегоимениифамилиибезпробелов");
        driver.findElement(By.cssSelector("[data-test-id= 'phone'] input")).sendKeys("+78695414214");
        driver.findElement(By.cssSelector("[data-test-id= 'agreement']")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= 'name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }
}

package com.cibertec.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("selenium")
public class PizzaSeleniumTest {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    @Test
    void registrarPizzaAdmin() throws Exception {


        driver.get("http://localhost:4200/application/home");


        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@routerlink='/auth/login' and contains(text(),'Login')]")
        )).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")))
                .sendKeys("eve@gmail.com");

        driver.findElement(By.id("password"))
                .sendKeys("123456");

        driver.findElement(By.xpath("//button[@type='submit']")).click();


        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@routerlink='/admin' and contains(text(),'Admin')]")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(),'Admin Panel')]")
        ));

        WebElement catalogo = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-panel-title[contains(text(),'Catalogo')]")
        ));
        js.executeScript("arguments[0].click();", catalogo);

        WebElement pizzas = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@routerlink='/admin/pizzas' and contains(text(),'Pizzas')]")
        ));
        js.executeScript("arguments[0].click();", pizzas);

        wait.until(ExpectedConditions.urlContains("/admin/pizzas"));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Agregar Pizza')]")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[formcontrolname='name']")
        )).sendKeys("Pizza Selenium");

        driver.findElement(By.cssSelector("input[formcontrolname='basePrice']"))
                .sendKeys("20");

        driver.findElement(By.cssSelector("textarea[formcontrolname='description']"))
                .sendKeys("Pizza creada con Selenium Java");


        driver.findElement(By.xpath("//mat-select[@formcontrolname='active']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option//span[contains(text(),'Sí')]")
        )).click();

        URL img = getClass().getClassLoader().getResource("img/mixto.jpg");
        assert img != null;
        Path imgPath = Paths.get(img.toURI());

        WebElement upload = driver.findElement(By.name("image"));
        js.executeScript("arguments[0].style.display='block';", upload);
        upload.sendKeys(imgPath.toFile().getAbsolutePath());

        WebElement guardar = driver.findElement(
                By.xpath("//button//span[contains(text(),'Guardar')]")
        );
        js.executeScript("arguments[0].removeAttribute('disabled');", guardar);
        js.executeScript("arguments[0].click();", guardar);

        boolean existe = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table//td[contains(text(),'Pizza Selenium')]")
        )).isDisplayed();

        assertTrue(existe);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}

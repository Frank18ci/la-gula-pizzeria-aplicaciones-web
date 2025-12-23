package com.cibertec.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

public class PizzaCreatePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public PizzaCreatePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ---------------- LOGIN ---------------- //

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    public void abrirLogin() {
        try {
            driver.get("http://localhost:4200/application/home");
        } catch (Exception ignored) {}

        By[] adminTriggers = new By[]{
                By.xpath("//a[contains(@routerlink,'/admin') or contains(@href,'/admin') or contains(normalize-space(.),'Admin') ]"),
                By.xpath("//button[contains(normalize-space(.),'Admin')]")
        };
        for (By by : adminTriggers) {
            try {
                WebElement admin = wait.until(ExpectedConditions.elementToBeClickable(by));
                admin.click();
                break;
            } catch (Exception ignored) {}
        }

        By[] loginTriggers = new By[]{
                By.xpath("//a[contains(@href,'/login') or contains(@href,'/auth') or contains(@href,'/signin')]"),
                By.xpath("//button[contains(.,'Iniciar sesión') or contains(.,'Iniciar sesion') or contains(.,'Login') or contains(.,'Ingresar')]")
        };
        boolean navigated = false;
        for (By by : loginTriggers) {
            try {
                WebElement trigger = wait.until(ExpectedConditions.elementToBeClickable(by));
                trigger.click();
                navigated = true;
                break;
            } catch (Exception ignored) {}
        }

        if (!navigated) {
            try { driver.get("http://localhost:4200/login"); } catch (Exception ignored) {}
            if (!driver.getCurrentUrl().contains("/login")) {
                driver.get("http://localhost:4200/admin");
            }
        }

        WebElement email = findEmailElement();
        wait.until(ExpectedConditions.visibilityOf(email));
    }

    private WebElement findEmailElement() {
        By[] candidates = new By[]{
                By.id("email"),
                By.cssSelector("input#email"),
                By.cssSelector("input[name='email']"),
                By.cssSelector("input[formcontrolname='email']"),
                By.cssSelector("input[type='email']"),
                By.xpath("//input[contains(@id,'email') or contains(@name,'email') or @type='email' or @formcontrolname='email']")
        };
        for (By by : candidates) {
            try {
                WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(by));
                if (el != null) return el;
            } catch (TimeoutException | NoSuchElementException ignored) {}
        }
        return emailInput;
    }

    public void login(String email, String password) {
        WebElement emailEl = findEmailElement();
        emailEl.clear();
        emailEl.sendKeys(email);

        WebElement passEl = findPasswordElement();
        passEl.clear();
        passEl.sendKeys(password);

        WebElement btn = findLoginButton();
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        wait.until(ExpectedConditions.urlContains("/admin"));
    }

    private WebElement findPasswordElement() {
        By[] candidates = new By[]{
                By.id("password"),
                By.cssSelector("input#password"),
                By.cssSelector("input[name='password']"),
                By.cssSelector("input[formcontrolname='password']"),
                By.cssSelector("input[type='password']"),
                By.xpath("//input[contains(@id,'password') or contains(@name,'password') or @type='password' or @formcontrolname='password']")
        };
        for (By by : candidates) {
            try {
                WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(by));
                if (el != null) return el;
            } catch (TimeoutException | NoSuchElementException ignored) {}
        }
        return passwordInput;
    }

    private WebElement findLoginButton() {
        By[] candidates = new By[]{
                By.cssSelector("button[type='submit']"),
                By.xpath("//button[contains(.,'Iniciar') or contains(.,'Ingresar') or @type='submit']")
        };
        for (By by : candidates) {
            try {
                WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(by));
                if (el != null) return el;
            } catch (TimeoutException | NoSuchElementException ignored) {}
        }
        return loginButton;
    }

    // ---------------- MENU ---------------- //

    @FindBy(xpath = "//mat-panel-title[contains(.,'Catalogo') or contains(.,'Catálogo')]")
    private WebElement catalogoPanel;

    @FindBy(xpath = "//a[contains(@href,'/admin/pizzas') and contains(normalize-space(.),'Pizzas')]")
    private WebElement pizzasOption;

    public void irPaginaPizzas() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(catalogoPanel)).click();
            wait.until(ExpectedConditions.elementToBeClickable(pizzasOption)).click();
        } catch (Exception e) {
            driver.get("http://localhost:4200/admin/pizzas");
        }
        wait.until(ExpectedConditions.urlContains("/admin/pizzas"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Agregar Pizza')]")));
    }

    // ---------------- MODAL ---------------- //

    @FindBy(xpath = "//button[contains(.,'Agregar Pizza')]")
    private WebElement agregarPizzaButton;

    public void abrirDialogCrearPizza() {
        wait.until(ExpectedConditions.elementToBeClickable(agregarPizzaButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("mat-mdc-dialog-container, mat-dialog-container")));
    }

    // ---------------- FORMULARIO ---------------- //

    @FindBy(css = "input[formcontrolname='name']")
    private WebElement nombreInput;

    @FindBy(css = "input[formcontrolname='basePrice']")
    private WebElement precioInput;

    @FindBy(css = "textarea[formcontrolname='description']")
    private WebElement descripcionInput;

    @FindBy(css = "input[type='file'][name='image']")
    private WebElement uploadImageInput;

    @FindBy(xpath = "//mat-select[@formcontrolname='active']")
    private WebElement activoSelect;

    @FindBy(xpath = "//button[normalize-space(.)='Guardar' or contains(normalize-space(.),'Guardar')]")
    private WebElement guardarButton;

    public void escribirNombre(String name) {
        wait.until(ExpectedConditions.visibilityOf(nombreInput)).clear();
        nombreInput.sendKeys(name);
    }

    public void escribirDescripcion(String desc) {
        wait.until(ExpectedConditions.visibilityOf(descripcionInput)).clear();
        descripcionInput.sendKeys(desc);
    }

    public void escribirPrecio(String price) {
        wait.until(ExpectedConditions.visibilityOf(precioInput)).clear();
        precioInput.sendKeys(price);
    }

    public void seleccionarActiva() {
        wait.until(ExpectedConditions.elementToBeClickable(activoSelect)).click();
        WebElement opcionSi = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//mat-option//span[contains(normalize-space(.),'Sí') or contains(normalize-space(.),'Si')]")
                ));
        opcionSi.click();
    }

    public void subirImagenPizza(String fileName) {
        URL resource = getClass().getClassLoader().getResource("img/" + fileName);
        if (resource == null) throw new RuntimeException("Imagen no encontrada en resources/img/");

        try {
            String absPath = Paths.get(resource.toURI()).toFile().getAbsolutePath();
            js.executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';", uploadImageInput);
            uploadImageInput.sendKeys(absPath);
        } catch (Exception e) {
            throw new RuntimeException("Error subiendo la imagen", e);
        }
    }

    public void guardarPizza() {
        try {
            if (guardarButton.getAttribute("disabled") != null) {
                js.executeScript("arguments[0].removeAttribute('disabled');", guardarButton);
            }
        } catch (Exception ignored) {}

        wait.until(webDriver -> {
            try {
                return guardarButton.isEnabled();
            } catch (StaleElementReferenceException ex) {
                return false;
            }
        });

        try {
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", guardarButton);
            js.executeScript("arguments[0].focus();", guardarButton);
        } catch (Exception ignored) {}

        boolean clicked = false;
        for (int i = 0; i < 3 && !clicked; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(guardarButton)).click();
                clicked = true;
            } catch (Exception e) {
                try {
                    js.executeScript("arguments[0].click();", guardarButton);
                    clicked = true;
                } catch (Exception ignored) {}
            }
        }

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("mat-mdc-dialog-container, mat-dialog-container")));
        } catch (Exception e) {
            try {
                driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                Thread.sleep(1000);
            } catch (Exception ignored) {}
        }

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        } catch (Exception ignored) {}
    }

    // ---------------- VALIDACIONES ---------------- //

    @FindBy(css = "simple-snack-bar, .mat-mdc-snack-bar-label")
    private WebElement snackBar;

    public boolean validarSnackBarExito() {
        try {
            String text = wait.until(ExpectedConditions.visibilityOf(snackBar))
                    .getText().toLowerCase();
            return text.contains("creada") || text.contains("guardada") ||
                    text.contains("éxito") || text.contains("exito");
        } catch (Exception e) {
            return false;
        }
    }


    public boolean tablaContienePizza(String nombrePizza) {
        try {
            By fila = By.xpath("//table//tr//*[contains(normalize-space(.),'" + nombrePizza + "')]");
            WebElement celda = wait.until(ExpectedConditions.visibilityOfElementLocated(fila));
            return celda.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
package com.cibertec.steps;

import com.cibertec.pages.PizzaCreatePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class PizzaFormSteps {

    private WebDriver driver;
    private PizzaCreatePage pizza;

    private String storedEmail;
    private String storedPassword;

    @Before("@ui")
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        pizza = new PizzaCreatePage(driver);
    }


    @Given("abro la pagina de login")
    public void abrirLogin() {
        pizza.abrirLogin();
    }

    @When("ingreso el correo {string}")
    public void ingresoCorreo(String email) {
        this.storedEmail = email;
    }

    @When("ingreso la contrasena {string}")
    public void ingresoPassword(String pass) {
        this.storedPassword = pass;
    }

    @When("hago clic en iniciar sesion")
    public void clicLogin() {
        if (this.storedEmail == null) this.storedEmail = "";
        if (this.storedPassword == null) this.storedPassword = "";
        pizza.login(this.storedEmail, this.storedPassword);
    }

    @Then("debo ver el panel de administrador")
    public void deboVerElPanelDeAdministrador() {
        Assertions.assertTrue(driver.getCurrentUrl().contains("/admin"));
    }

    @Then("navego a la seccion de pizzas")
    public void irPizzas() {
        pizza.irPaginaPizzas();
    }

    // ---------------- CREAR PIZZA ---------------- //

    @Given("abro el modal de registro de pizza")
    public void abrirModal() {
        pizza.abrirDialogCrearPizza();
    }

    @When("ingreso nombre {string}")
    public void ingresoNombre(String nombre) {
        pizza.escribirNombre(nombre);
    }

    @When("ingreso descripcion {string}")
    public void ingresoDescripcion(String descripcion) {
        pizza.escribirDescripcion(descripcion);
    }

    @When("ingreso precio {string}")
    public void ingresoPrecio(String precio) {
        pizza.escribirPrecio(precio);
    }

    @When("selecciono la pizza como activa")
    public void seleccionoActiva() {
        pizza.seleccionarActiva();
    }

    @When("subo la imagen {string}")
    public void subirImagen(String fileName) {
        pizza.subirImagenPizza(fileName);
    }

    @When("hago clic en registrar")
    public void clicRegistrar() {
        pizza.guardarPizza();
    }


    @Then("la tabla de pizzas debe mostrar {string}")
    public void tablaMuestraPizza(String nombrePizza) {
        Assertions.assertTrue(pizza.tablaContienePizza(nombrePizza),
                "La tabla no muestra la pizza " + nombrePizza);
    }

    @After("@ui")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
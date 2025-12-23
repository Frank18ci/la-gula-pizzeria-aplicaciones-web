package com.cibertec;

import com.cibertec.dto.PizzaRequest;
import com.cibertec.dto.PizzaResponse;
import com.cibertec.model.Pizza;
import com.cibertec.repository.PizzaRepository;
import com.cibertec.repository.SizeRepository;
import com.cibertec.repository.ToppingRepository;
import com.cibertec.service.impl.PizzaServiceImpl;
import com.cibertec.storage.ImageStorageService;
import com.cibertec.storage.TypeStorageEnum;
import com.cibertec.util.PizzaMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@CucumberContextConfiguration
public class PizzaSteps {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private ToppingRepository toppingRepository;

    @Mock
    private SizeRepository sizeRepository;

    @Mock
    private PizzaMapper pizzaMapper;

    @Mock
    private ImageStorageService imageStorageService;

    @InjectMocks
    private PizzaServiceImpl pizzaService;

    private Pizza pizza;
    private PizzaResponse response;
    private PizzaRequest request;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ---------- OBTENER PIZZA ----------

    @Given("existe una pizza con id {int} y nombre {string}")
    public void existePizza(int id, String nombre) {
        pizza = new Pizza();
        pizza.setId((long) id);
        pizza.setName(nombre);
        pizza.setToppings(new ArrayList<>());
        pizza.setSizes(new ArrayList<>());

        response = PizzaResponse.builder()
                .id((long) id)
                .name(nombre)
                .build();

        when(pizzaRepository.findById((long) id)).thenReturn(Optional.of(pizza));
        when(pizzaMapper.toDto(pizza)).thenReturn(response);
    }

    @When("solicito la pizza con id {int}")
    public void solicitoPizza(int id) {
        response = pizzaService.getPizzaById((long) id);
    }

    @Then("la respuesta debe contener el nombre {string}")
    public void validarNombreRespuesta(String nombreEsperado) {
        assertEquals(nombreEsperado, response.name());
    }

    // ---------- CREAR PIZZA ----------

    @Given("una solicitud de pizza con nombre {string} y precio {int}")
    public void solicitudPizza(String nombre, Integer precio) throws IOException {

        request = PizzaRequest.builder()
                .name(nombre)
                .basePrice(BigDecimal.valueOf(precio))
                .active(true)
                .image(null)
                .build();

        pizza = new Pizza();
        pizza.setName(nombre);

        response = PizzaResponse.builder()
                .id(1L)
                .name(nombre)
                .build();

        when(pizzaMapper.toEntity(
                any(PizzaRequest.class),
                any(ToppingRepository.class),
                any(SizeRepository.class),
                anyString()
        )).thenReturn(pizza);

        doNothing().when(imageStorageService)
                .saveImage(any(MultipartFile.class), anyString(), any(TypeStorageEnum.class));

        when(pizzaRepository.save(any())).thenReturn(pizza);
        when(pizzaMapper.toDto(any())).thenReturn(response);
    }

    @When("registro la pizza")
    public void registroPizza() {
        response = pizzaService.createPizza(request);
    }
}

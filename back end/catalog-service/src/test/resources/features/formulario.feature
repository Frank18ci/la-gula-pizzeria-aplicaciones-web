@ui
Feature: Registro de pizza con Selenium Cucumber
  Como administrador
  Quiero registrar una pizza
  Para validar todo el flujo desde login hasta la creacion
  Background:
    Given abro la pagina de login
    When ingreso el correo "eve@gmail.com"
    And ingreso la contrasena "123456"
    And hago clic en iniciar sesion
    Then debo ver el panel de administrador
    And navego a la seccion de pizzas


  Scenario: Crear una nueva pizza correctamente
    Given abro el modal de registro de pizza
    When ingreso nombre "Hawaiana"
    And ingreso descripcion "Pizza dulce con pina"
    And ingreso precio "25"
    And selecciono la pizza como activa
    And subo la imagen "mixto.jpg"
    And hago clic en registrar
    Then la tabla de pizzas debe mostrar "Hawaiana"

  Scenario: Crear otra pizza correctamente
    Given abro el modal de registro de pizza
    When ingreso nombre "Italiana"
    And ingreso descripcion "Clasica y muy sabrosa"
    And ingreso precio "30"
    And selecciono la pizza como activa
    And subo la imagen "mixto.jpg"
    And hago clic en registrar
    Then la tabla de pizzas debe mostrar "Italiana"

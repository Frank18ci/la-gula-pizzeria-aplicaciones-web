@normal
Feature: Gestion de Pizzas
  Como usuario del sistema
  Quiero gestionar pizzas
  Para poder administrarlas correctamente

  Scenario: Obtener una pizza por su ID
    Given existe una pizza con id 1 y nombre "Margarita"
    When solicito la pizza con id 1
    Then la respuesta debe contener el nombre "Margarita"

  Scenario: Crear una nueva pizza
    Given una solicitud de pizza con nombre "Pepperoni" y precio 25
    When registro la pizza
    Then la respuesta debe contener el nombre "Pepperoni"

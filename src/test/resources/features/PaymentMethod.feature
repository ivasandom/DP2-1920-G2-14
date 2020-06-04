
Feature: Metodos de pagos repetidos
	Como administrador, Quiero que el sistema impida que un cliente pueda añadir dos veces el mismo método de pago.

  
  Scenario: Add valid payment method
    Given I am authenticated as "elenanito"
    When I am on new payment method form
    And I try to save a valid payment method
    Then Payment method is saved

  
  Scenario: Add a duplicated payment method
    Given I am authenticated as "pepegotera"
    When I am on new payment method form
    And I try to save a duplicated payment method
    Then Payment method is not saved

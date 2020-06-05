
Feature: Diagnostico medico
	Como professional, Quiero poder realizar una receta médica para la cita correspondiente, seleccionando el tratamiento.

  Scenario: Try to save a diagnosis without description
    Given I am authenticated as "professional1"
    When I am on consultation page
    And I try to save a diagnosis without description
    Then Diagnosis is not created
    
  Scenario: Try to save a diagnosis with valid data
    Given I am authenticated as "professional1"
    When I am on consultation page
    And I try to save a diagnosis with valid data
    Then Diagnosis is created
	
  

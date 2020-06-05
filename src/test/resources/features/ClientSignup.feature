
Feature: Datos cliente
	Como administrator del sistema, Quiero conocer los datos personales y correo electrónico de los clientes.

  
  Scenario: Try to sign up with valid data
    Given I am on sign up page
    And I try to sign up with valid data
    Then I have been signed up
	
	
  Scenario: Try to sign up without email
    Given I am on sign up page
    And I try to sign up without email
    Then I have not been signed up
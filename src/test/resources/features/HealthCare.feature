
Feature: Asociacion con aseguradora
	Como administrador del sistema, Quiero saber si el cliente que solicita una cita dispone de mutua o aseguradora, si procede.

  
  Scenario: Try to sign up with valid insurance
    Given I am on sign up page
    When I try to sign up with valid insurance
    Then I am signed up with invalid insurance

  
  Scenario: Try to sign up with invalid insurance
    Given I am on sign up page
    When I try to sign up with invalid insurance
    Then I am not signed up with invalid insurance
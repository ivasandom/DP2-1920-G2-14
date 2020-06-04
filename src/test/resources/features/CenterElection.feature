
Feature: Eleccion de centro
	Quiero poder seleccionar en qué centro pedir la cita.

  
  Scenario: Try to select a center with professionals
    Given I am authenticated as "pepegotera"
    When I am on new appointment page
    And I try to select a center with professionals
    Then I find professionals with center
	
  
  Scenario: Try to select a center with no professionals
    Given I am authenticated as "pepegotera"
    When I am on new appointment page
    And I try to select a center with no professionals
    Then I find no professionals with center
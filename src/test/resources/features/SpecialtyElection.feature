
Feature: Busqueda de profesioanles
	Como cliente Quiero tener una lista con los profesionales filtrados por centro y especialidad.

  
  Scenario: Try to find professionals with valid data
    Given I am authenticated as "pepegotera"
    When I am on find professionals page
    And I try to find professionals with valid data
    Then I find professionals

  
  Scenario: Try to find professionals without specialty
    Given I am authenticated as "pepegotera"
    When I am on find professionals page
    And Try to find professionals without specialty
    Then I do not find professionals
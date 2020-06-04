
Feature: Eleccion de horario
	Como cliente, Quiero poder seleccionar el horario de cita del profesional que desee, según disponibilidad.

  
  Scenario: Try to save appointment with valid date
    Given I am authenticated as "pepegotera"
    When I am on new appointment page
    And I try to save appointment with valid date
    Then Appointment is saved
	
  
  Scenario: Try to save appointment with invalid date
    Given I am authenticated as "pepegotera"
    When I am on new appointment page
    And I try to save appointment with invalid date
    Then Appointment is not saved
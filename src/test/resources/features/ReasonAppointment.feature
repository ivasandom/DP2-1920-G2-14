
Feature: Descripcion del motivo de la cita
	Como cliente,	Quiero especificar el motivo por el que realizo mí cita.

  
  Scenario: Try to create an appointment with valid a reason
    Given I am authenticated as "pepegotera"
    When I am on new appointment page
    And I try to create an appointment with valid a reason
    Then Appointment is created with reason

  
  Scenario: Try to create an appointment without a reason
    Given I am authenticated as "pepegotera"
    When I am on new appointment page
    And I try to create an appointment without a reason
    Then Appointment with no reason is not created
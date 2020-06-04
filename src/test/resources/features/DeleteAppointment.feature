
Feature: Gestionar citas
	Quiero gestionar, incluyendo crear o cancelar citas.

  
  Scenario: Try to delete a future appointment
    Given I am authenticated as "pepegotera"
    When I am on appointment detail page
    And I try to delete a future appointment
    Then Appointment is deleted

  
  Scenario: Try to delete a past appointment
    Given I am authenticated as "pepegotera"
    When I am on appointment detail page
    And I try to delete a past appointment
    Then Appointment is not deleted
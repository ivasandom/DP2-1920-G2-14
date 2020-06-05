
Feature: Panel de control
	Quiero tener un panel de control

  
  Scenario: Try to charge a valid amount
    Given I am authenticated as "admin1"
    When I am on bill charge form
    And I try to charge a valid amount
    Then Amount is charged
	
  
  Scenario: Try to charge a invalid amount
    Given I am authenticated as "admin1"
    When I am on bill charge form
    And I try to charge a invalid amount
    Then Amount is not charged
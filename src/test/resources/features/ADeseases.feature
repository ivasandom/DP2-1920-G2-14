
Feature: Ver enfermedades anteriores
	Como médico, Quiero ver el histórico de enfermedades de cada paciente.

  
  Scenario: List deseases
    Given I am authenticated as "professional1"
     When I am on consultation page
    And I try to see previous client deseases
    Then I see previous deseases

  
  Scenario: List no deseases if client does not have any
    Given I am authenticated as "professional1"
     When I am on consultation page
    And I try to see previous client deseases with none
    Then I see no previous deseases
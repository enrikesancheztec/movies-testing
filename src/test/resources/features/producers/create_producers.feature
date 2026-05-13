@producers
Feature: Create producers

  Scenario: Keep save disabled when producer name is empty
    Given I open the create producer page from the producers list
    When I fill producer profile with 50 characters and leave producer name empty
    Then the save producer button should be disabled

  Scenario: Create a valid producer and show it in list
    Given I open the create producer page from the producers list
    When I fill the producer form with a unique valid name and profile
    Then the save producer button should be enabled
    When I save the producer
    Then I should be redirected to the producers list
    And I should find the newly created producer in the list

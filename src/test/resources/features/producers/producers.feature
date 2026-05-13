Feature: Producers
  As a user of the movies application
  I want to navigate producer screens
  So that producer information can be validated through E2E automation

  @producers @list
  Scenario: Producers list page is visible
    Given the user opens the producers list page
    Then the producers list title should be visible

  @producers @details
  Scenario: Producer details link is available for listed producers
    Given the user opens the producers list page
    Then each listed producer should expose a details link

  @producers @create @validation
  Scenario: Save button stays disabled when producer name is empty
    Given the user opens the create producer form
    When the user fills producer profile with 50 characters and keeps name empty
    Then the save producer button should be disabled

  @producers @create
  Scenario: User creates a producer successfully
    Given the user opens the create producer form
    When the user enters valid producer data
    And the user saves the producer
    Then the new producer should be visible in the producers list
@producers
Feature: List producers

  Scenario: Display producers list page text
    Given I open the producers list page
    Then I should see "Producers List" text on the page

  Scenario: Every producer row has a valid details link
    Given I open the producers list page
    Then every listed producer should have a details link ending with its producer id

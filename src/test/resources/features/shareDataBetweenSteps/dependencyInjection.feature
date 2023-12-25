Feature: dependency injection with picocontainer

  @DI
  Scenario: Share data between step definitions
    Given I navigate to  "https://www.google.com"
    When I use String "ball" from step one in stepDefOne to search
    And I want to use data from step one in stepDefTwo and update search with "basket"
    And I want to use data from step two in stepDefOne to search
    Then show all information
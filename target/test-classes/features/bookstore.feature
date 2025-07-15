Feature: Bookstore Search

  Scenario: Search for a book by title
    Given I am on the bookstore homepage
    When I search for "Clean Code"
    Then I should see the book titled "Clean Code"
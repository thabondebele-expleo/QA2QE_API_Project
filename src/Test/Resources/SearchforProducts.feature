Feature: User searches for Products

Scenario: Search for products

Given that the customer is on the Home page
And enters a products name in the search field
When the customer clicks the search icon to search
Then the WebPage should return a list of search results
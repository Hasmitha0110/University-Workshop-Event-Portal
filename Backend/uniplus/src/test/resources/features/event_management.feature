Feature: Event management by admins
  As an admin
  I want to authenticate and manage events
  So that I can inform students about workshops and activities

  Background:
    Given an admin exists with email "admin@uni.lk" and password "secret123"

  Scenario: Successful login returns a JWT
    When I login with email "admin@uni.lk" and password "secret123"
    Then I should receive a JWT token

  Scenario: Create a valid event
    Given I am logged in as "admin@uni.lk" with password "secret123"
    When I create an event with:
      | title       | venue     | eventDate   |
      | AI Workshop | Main Hall | +1 days     |
    Then the response status should be 200

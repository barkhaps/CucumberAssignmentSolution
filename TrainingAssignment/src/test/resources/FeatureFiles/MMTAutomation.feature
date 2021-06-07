Feature: Automation of flight booking with Make My Trip

  Background: The webdriver is initialized and the user is on MMT homepage

  @MMT_Automation
  Scenario Outline: Automation of flight booking with Make My Trip
    Given Click on Flights
    Then Select Trip Type and verify change
      | Trip Type | <tripType> |
    Then Fill journey details and Continue
      | From City        | <fromCity>   |
      | To City          | <toCity>     |
      | Depart Date      | <deptDate>   |
      | Return Date      | <retDate>    |
      | No Of Adults     | <noOfAdult>  |
      | No Of Children   | <noOfChild>  |
      | No Of Infants    | <noOfInfant> |
      | Suggestion Index | <index>      |
    Then Select Airline filter and verify
      | Airline Name | <airLineName> |
    Then Sort Grid and Select Nth Record
      | Sort Column | <sorter>   |
      | Sort Type   | <sortType> |
      | N           | <n>        |
      | Fare Type   | <fareType> |
    Then Switch to Review Page Tab
    And Verify Booking details
    Then Verify Fare Summary Data
    Then Uncheck and verify charity
    Then Select insurance if available and verify
    Then Switch to Home Tab
    And Navigate to Homepage

    @MMT_Automation_Round
    Examples: 
      | tripType   | fromCity | toCity      | deptDate   | retDate    | noOfAdult | noOfChild | noOfInfant | index | airLineName | sorter   | sortType  | n | fareType |
      | round trip | Delhi    | Bhubaneswar | 28-07-2021 | 30-07-2021 |         2 |           |          1 |     1 | IndiGo      | Duration | ascending | 1 | Saver    |

    @MMT_Automation_OneWay
    Examples: 
      | tripType | fromCity | toCity      | deptDate   | retDate | noOfAdult | noOfChild | noOfInfant | index | airLineName | sorter   | sortType   | n | fareType |
      | oneway   | Delhi    | Bhubaneswar | 28-07-2021 |         |         2 |         1 |          1 |     1 | IndiGo      | Duration | descending | 1 | Saver    |

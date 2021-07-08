Feature: App login

Scenario Outline: Verify Add place API functionality

Given Add place payload with "<name>" "<language>" "<address>"
When user calls "AddPlaceAPI" with "POST" https request
Then verify status code is 200
And "status" in response body is "OK"
#And scope is App
And verify place id created is mapped to "<name>" using "GetPlaceAPI"

Examples:
|name	|language|address|
|Vasan  |English |Chennai|
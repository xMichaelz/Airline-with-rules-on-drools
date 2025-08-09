# Airline-with-rules-on-drools

A Spring Boot application that uses the Drools rule engine to evaluate airline passengers based on their profile, flight details, and luggage, applying automated business rules such as seat upgrades, VIP lounge access, luggage restrictions, discounts, and priority check-in; exposes a single REST API endpoint (POST /evaluate) that processes input data and returns the evaluation results according to the 10 rules defined in credit_rules.drl.

An Example Use Case are:
	•	Upgrade Platinum members to business class if the flight is delayed more than 60 minutes.
	•	Deny upgrades for overweight luggage (> 23kg).
	•	Grant priority check-in for seniors (65+).

The rules are:
 	1.	Upgrade to business class if the passenger has Gold or Platinum status and the flight is delayed by more than 60 minutes.
Uses two objects in the condition: Passenger and Flight, since both factors need to be validated. Updates both the Passenger and the EvaluationResult to reflect the change in the response.
	2.	Enable priority check-in for passengers over 65 years old.
Only evaluates the Passenger, since age is its property, and marks priority in both the model and the result for data consistency.
	3.	Apply a 10% discount for light luggage (under 10 kg).
Only the Luggage object is needed; the discount property is updated directly to represent the benefit.
	4.	Deny business class upgrade for passengers with very heavy luggage (over 23 kg).
Requires both Luggage (to check weight) and Passenger (to apply the consequence). Sets upgradeEligible = false in both.
	5.	Assign an emergency exit seat to young adults between 18 and 40 years old who have no specific seat preference.
Validates age and preference together in a single Passenger condition, then updates the seat directly.
	6.	Provide $200 compensation if the flight has an extreme delay (over 3 hours).
Only the Flight object is needed, and the consequence affects only the EvaluationResult.
	7.	Add 500 loyalty points to Gold or Platinum members if the flight lasts more than 5 hours.
Uses two conditions: membership type (Passenger) and flight duration (Flight). Adds to the current loyalty points value.
	8.	Restrict heavy luggage (over 15 kg) on short flights (under 2 hours).
Uses both Luggage and Flight because both conditions are required. Marks the luggage as not allowed in both the model and the result.
	9.	Grant VIP lounge access only to Platinum members.
Involves only the Passenger object and reflects the benefit in both layers.
	10.	Assign a family zone seat if the passenger is traveling with children and has no specific seat request.
Checks two attributes of the same object. Assigns the seat logically to help group families together.

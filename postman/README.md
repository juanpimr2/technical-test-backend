# Postman Collection - Technical Test Inditex

## Import Instructions

1. Open Postman
2. Click **Import** button
3. Select both files:
   - `Technical-Test-Inditex.postman_collection.json`
   - `Technical-Test-Local.postman_environment.json`
4. Select the environment "Technical Test - Local" in the top-right corner

## Running Tests

### Run All Tests (Collection Runner)
1. Click on the collection "Technical Test - Inditex Backend API"
2. Click **Run** button
3. Click **Run Technical Test - Inditex Backend API**
4. See all 6 requests with automated assertions

### Expected Results
- ✅ Test 1: Status 200, Price 35.50 EUR, Price List 1
- ✅ Test 2: Status 200, Price 25.45 EUR, Price List 2 (priority)
- ✅ Test 3: Status 200, Price 35.50 EUR, Price List 1
- ✅ Test 4: Status 200, Price 30.50 EUR, Price List 3 (priority)
- ✅ Test 5: Status 200, Price 38.95 EUR, Price List 4 (priority)
- ✅ Error Case: Status 404

## Prerequisites
- Application running on `http://localhost:8080`
- Start with: `mvn spring-boot:run`

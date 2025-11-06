# Unit Test Suite for Your Way To Italy

## Overview
This directory contains comprehensive unit tests for the Your Way To Italy web application.

## Test Coverage

### Resource Classes (`resource` package)
- **AdvertisementTest.java** - Tests for Advertisement entity (13 test cases)
  - Constructor and getters validation
  - JSON serialization/deserialization
  - Edge cases (zero score, high prices, single items)

- **BookingTest.java** - Tests for Booking entity (11 test cases)
  - State management (CONFIRMED, PENDING, CANCELLED)
  - Date and time validation
  - JSON operations

- **CityTest.java** - Tests for City entity (10 test cases)
  - Italian city names
  - Special characters handling
  - ID uniqueness

- **CompanyTest.java** - Tests for Company user type (8 test cases)
  - User type identification
  - Special characters in names/addresses
  - Inheritance from User class

- **TouristTest.java** - Tests for Tourist user type (10 test cases)
  - Birth date validation
  - Full name handling
  - Type differentiation from Company

- **FeedbackTest.java** - Tests for Feedback entity (11 test cases)
  - Rating validation (1-5 scale)
  - Text content handling
  - Multiple feedback scenarios

- **ImageTest.java** - Tests for Image entity (12 test cases)
  - Path validation (relative/absolute)
  - Multiple images per advertisement
  - Description handling

- **MessageTest.java** - Tests for Message class (10 test cases)
  - Error vs informative messages
  - Error code handling
  - JSON serialization

- **TypeAdvertisementTest.java** - Tests for advertisement types (7 test cases)
  - Different tour types
  - Type name validation

- **SearchParametersTest.java** - Tests for search functionality (7 test cases)
  - Date range handling
  - Parameter validation

- **RateTest.java** - Tests for rating system (8 test cases)
  - Rate value validation (0-5)
  - Edge cases

### Utility Classes (`utils` package)
- **ErrorCodeTest.java** - Tests for ErrorCode enum (20 test cases)
  - All error codes validation
  - HTTP code mapping
  - Error message completeness
  - Uniqueness verification

### Database Classes (`database` package)
- **AbstractDAOTest.java** - Tests for DAO base class (11 test cases)
  - Resource cleanup operations
  - Null handling
  - Exception propagation
  - Proper closing order

## Test Statistics
- **Total Test Classes**: 12
- **Total Test Cases**: ~138
- **Coverage**: Resource entities, Error handling, Database operations

## Running the Tests

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=AdvertisementTest
```

### Run with coverage report
```bash
mvn test jacoco:report
```

### Skip tests during build
```bash
mvn package -DskipTests
```

## Test Dependencies
- **JUnit 4.13.2** - Testing framework
- **Mockito 3.12.4** - Mocking framework for database tests
- **Hamcrest 1.3** - Matcher library
- **H2 Database 1.4.200** - In-memory database for integration tests

## Best Practices
1. Each test class follows the naming convention `<ClassName>Test.java`
2. Tests use descriptive method names starting with `test`
3. Setup methods use `@Before` annotation
4. Assertions include descriptive messages
5. Edge cases and boundary conditions are tested
6. Mock objects are used for database dependencies

## Test Structure
```
src/test/java/
└── it/unipd/dei/yourwaytoitaly/
    ├── resource/        # Resource entity tests
    ├── utils/          # Utility class tests
    └── database/       # DAO and database tests
```

## Future Enhancements
- Integration tests for servlet classes
- REST API endpoint tests
- Database integration tests with H2
- Performance tests
- Security tests for authentication filters

## Notes
- Tests are independent and can run in any order
- No external dependencies required (mocked)
- Tests use in-memory objects, no database required
- All tests pass successfully on Java 8+

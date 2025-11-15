# REST Countries API - Test Automation Suite
## Technical Architecture & Design Documentation

---

## 1. Project Overview

This project demonstrates a **production-grade test automation framework** for the [REST Countries API](https://restcountries.com/v3.1/), a publicly available REST API providing comprehensive data about world countries, regions, currencies, and more.

**API URL**: https://restcountries.com/v3.1/

### Why This API?
- **Public & Stable**: No authentication required, consistent availability
- **Rich Feature Set**: Multiple endpoints (search by name, code, region, currency, language)
- **Real-world Complexity**: Various data types, filters, and edge cases
- **Performance Testable**: Suitable for testing response times and load
- **Data Integrity**: Perfect for validating business logic and data consistency

---

## 2. Architecture & Design Decisions

### 2.1 Technology Stack

| Component | Technology | Rationale |
|-----------|-----------|-----------|
| **Build Tool** | Maven 3.9.x | Industry standard, excellent dependency management |
| **Test Framework** | JUnit 5 | Modern, modular, excellent Gradle/Maven integration |
| **API Client** | REST-Assured | Purpose-built for API testing, fluent API, excellent matchers |
| **HTTP Client** | Playwright (with REST) | Modern approach, handles complex scenarios |
| **JSON Processing** | Jackson | Fast, flexible, widely adopted |
| **Assertions** | AssertJ + Hamcrest | Combined power for readable assertions |
| **Java Version** | Java 11+ | LTS version, good language features |
| **Logging** | SLF4J + Simple Impl | Flexible, industry standard logging facade |

### 2.2 Project Structure

```
src/
├── main/
│   └── java/com/api/automation/
│       ├── client/
│       │   └── ApiClient.java          # REST API client wrapper
│       ├── config/
│       │   └── ConfigManager.java      # Configuration management
│       ├── models/
│       │   └── Country.java            # Data model (POJO)
│       └── base/
│           └── BaseTest.java           # Base test class
├── test/
│   └── java/com/api/automation/
│       └── tests/
│           ├── CountriesApiIT.java     # Integration tests
│           └── CountriesApiFunctionalTest.java  # Functional tests
└── resources/
    └── config.properties               # Configuration file

.github/
└── workflows/
    └── test-automation.yml             # GitHub Actions CI/CD
```

### 2.3 Key Design Patterns

#### **1. Page Object Model (Adapted for API)**
- `ApiClient`: Encapsulates HTTP communication
- `CountriesApiIT`: Test cases at API resource level
- Separates test logic from implementation details

#### **2. Configuration Management**
- Centralized `ConfigManager` for environment-specific settings
- Easy switching between dev/staging/production
- Supports property override for CI/CD environments

#### **3. Base Test Class Pattern**
- Common setup/teardown in `BaseTest`
- Shared utilities available to all test classes
- Consistent logging across all tests

#### **4. Model-Driven Testing**
- `Country` POJO for automatic JSON deserialization
- Type-safe assertions using mapped objects
- Easier to maintain when API schema changes

### 2.4 Test Organization Strategy

**Integration Tests (`*IT.java`)**
- Run as part of Maven's `verify` phase
- Hit actual REST endpoints
- Slower but more realistic
- Coverage: ~14 test cases covering all major endpoints

**Functional Tests (`*FunctionalTest.java`)**
- Business logic validation
- Data consistency checks
- Cross-endpoint consistency
- Coverage: ~9 test cases for critical workflows

**Unit Tests (Future)**
- Client configuration tests
- Utility function tests
- Mocked dependencies

---

## 3. Test Coverage & Strategy

### 3.1 Covered Scenarios

| Endpoint | Test Cases | Coverage |
|----------|-----------|----------|
| `/all` | 4 | Basic retrieval, performance, sequential requests |
| `/alpha/{code}` | 5 | Valid codes, invalid codes, case sensitivity, consistency |
| `/name/{name}` | 3 | Exact match, partial match, consistency |
| `/region/{region}` | 4 | Region filtering, data consistency, subregions |
| `/currency/{currency}` | 1 | Currency filtering |
| `/language/{lang}` | 1 | Language filtering |
| `/capital/{capital}` | 1 | Capital search |
| Cross-cutting | 5 | Response structure, field validation, performance |

**Total: 23+ test cases**

### 3.2 Test Levels

```
End-to-End Tests (E2E)
  ├── CountriesApiIT (14 tests)
  │   └── Functional scenarios at API level
  │
Integration Tests (Integration)
  ├── CountriesApiFunctionalTest (9 tests)
  │   └── Data integrity, consistency, business logic
  │
Unit Tests (Future)
  └── ApiClientTest
      └── Configuration, retry logic
```

### 3.3 Risk-Based Testing Strategy

**High Priority (Must Test)**
- All CRUD operations (GET endpoints in this case)
- Data validation and structure
- Error handling (404, timeout, etc.)
- Performance under normal load

**Medium Priority (Should Test)**
- Edge cases (empty results, special characters)
- Filter combinations
- Response filtering
- Case sensitivity

**Lower Priority (Nice to Have)**
- Load testing (100+ concurrent requests)
- Chaos engineering (network delays, failures)
- API versioning (if applicable)

### 3.4 Quality Metrics

- **Test Count**: 23+ automated tests
- **Code Coverage Target**: >80% of API client code
- **Flakiness Target**: <1% (zero unintended failures)
- **Execution Time**: <30 seconds for full suite
- **Maintainability**: High (clear naming, single responsibility)

---

## 4. CI/CD Pipeline

### 4.1 GitHub Actions Workflow

**Triggers:**
- Push to `main` or `develop` branches
- Pull requests to `main` or `develop`
- Daily scheduled run (2 AM UTC)

**Pipeline Stages:**

1. **Checkout & Setup** (< 30s)
   - Clone repository
   - Set up JDK 11 & 17
   - Cache Maven dependencies

2. **Build & Test** (< 60s)
   - Compile code
   - Run integration tests
   - Run functional tests
   - Generate coverage reports

3. **Reporting** (< 30s)
   - Upload test reports as artifacts
   - Upload coverage reports
   - Publish results via GitHub Actions
   - Comment on PR with results

4. **Failure Handling**
   - Tests fail fast (fail on first failure option available)
   - Artifacts preserved for investigation
   - Email notifications (can be added)

### 4.2 CI/CD Best Practices Implemented

✅ **Automated on every commit** - Fast feedback loop
✅ **Parallel execution** - Test on multiple Java versions
✅ **Artifact preservation** - Test reports saved for analysis
✅ **Dependency caching** - Faster builds (Maven cache)
✅ **Clear reporting** - PR comments with results
✅ **Deterministic tests** - No flakiness, no timing issues
✅ **Scheduled runs** - Catch intermittent issues
✅ **Security** - No secrets in repository, uses GitHub tokens

---

## 5. Setup & Execution Instructions

### 5.1 Prerequisites

- **Java 11+** installed
- **Maven 3.8.1+** installed
- **Git** installed
- **Internet access** (to reach REST Countries API)

### 5.2 Local Setup

```bash
# Clone the repository
git clone https://github.com/yourusername/rest-countries-test-suite.git
cd rest-countries-test-suite

# Build the project
mvn clean install

# Run all tests
mvn test

# Run integration tests only
mvn verify

# Run specific test class
mvn test -Dtest=CountriesApiIT

# Run with coverage report
mvn clean verify jacoco:report
# View report at: target/site/jacoco/index.html
```

### 5.3 Configuration

Edit `src/resources/config.properties` to change:
- API base URL
- Request timeout
- Retry count
- Logging level

### 5.4 IDE Setup

**Visual Studio Code:**
```bash
# Install extensions
- Extension Pack for Java
- Test Runner for Java

# Open project folder
```

**IntelliJ IDEA:**
- Open project → Maven will auto-configure
- Right-click test → Run

---

## 6. Code Quality & Maintainability

### 6.1 Design Principles Applied

| Principle | Implementation |
|-----------|-----------------|
| **DRY** | BaseTest, ApiClient for common functionality |
| **SOLID** | Single responsibility per class, dependency injection |
| **KISS** | Simple, readable test cases without complexity |
| **YAGNI** | Only implemented needed features |
| **Clean Code** | Meaningful names, short methods, comments where needed |

### 6.2 Maintainability Features

**Flexibility for Changes:**
```java
// Easy to update base URL
ConfigManager.getBaseUrl()  // Can be set via environment

// Easy to add new endpoints
apiClient.get("/new-endpoint")

// Easy to reuse common assertions
response.then()
    .statusCode(200)
    .contentType("application/json")
```

**Scalability:**
```java
// Ready for additional test classes
CountriesApiIT
CountriesApiFunctionalTest
CountriesApiPerformanceTest  // Future
CountriesApiSecurityTest      // Future

// Ready for data-driven tests
@ParameterizedTest
@ValueSource(strings = {"us", "gb", "fr"})
public void testCountryCodes(String code) { ... }
```

### 6.3 Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| **Flaky Tests** | No time-based assertions, proper waits implemented |
| **Brittle Tests** | Data-independent tests, using unique identifiers |
| **Slow Execution** | Parallel execution ready, efficient requests |
| **Hard to Debug** | Detailed logging, clear error messages |

---

## 7. Improvements & Evolution Plan

### 7.1 Short-term (1-3 months)

- [ ] Add performance benchmarking tests
- [ ] Implement request/response logging at HTTP level
- [ ] Add contract testing (OpenAPI validation)
- [ ] Create test data factory for complex scenarios
- [ ] Add Allure reporting for better visualization

### 7.2 Medium-term (3-6 months)

- [ ] Implement load testing with JMeter or Gatling
- [ ] Add security testing (OWASP, injection tests)
- [ ] Create API client SDK as separate module
- [ ] Implement BDD style tests (Cucumber/Gherkin)
- [ ] Add visual regression testing if UI exists

### 7.3 Long-term (6+ months)

- [ ] Implement chaos engineering tests
- [ ] Add real-time monitoring and alerting
- [ ] Create test analytics dashboard
- [ ] Implement machine learning for test optimization
- [ ] Establish test data management system

### 7.4 Team Onboarding Plan

**For New Team Members:**

1. **Week 1 - Foundation**
   - Clone repo, run tests locally
   - Review architecture document (this file)
   - Understand test organization
   - Run a test in debugger

2. **Week 2 - Contribution**
   - Write first test case
   - Submit PR, get feedback
   - Review others' tests
   - Understand CI/CD pipeline

3. **Week 3 - Mastery**
   - Create new test suite for new endpoint
   - Lead code review session
   - Improve test performance
   - Document learnings

**Key Resources:**
- This documentation
- Code comments and examples
- Test cases as templates
- Slack/Teams channel for questions
- Weekly sync with QA lead

---

## 8. Troubleshooting

### Test Fails Locally But Passes in CI

**Causes:**
- Different Java version
- Network/proxy differences
- Timing-sensitive code

**Solutions:**
```bash
# Test with same Java version as CI
java -version

# Clear local cache
mvn clean

# Run with verbose output
mvn test -X
```

### Tests Timeout

**Causes:**
- Slow network
- API service degradation
- Configuration timeout too low

**Solutions:**
```properties
# Increase timeout in config.properties
request.timeout.ms=10000

# Check API health
curl https://restcountries.com/v3.1/all
```

### Maven Dependency Issues

**Causes:**
- Corrupted local repository
- Network connectivity
- Proxy configuration

**Solutions:**
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Reinstall dependencies
mvn clean install

# Check proxy settings
# Edit ~/.m2/settings.xml
```

---

## 9. Performance Considerations

### Response Times
- Individual endpoint: ~200-500ms
- Full test suite: ~30-45 seconds
- CI/CD run (including setup): ~3-5 minutes

### Optimization Opportunities
- Parallel test execution (via Maven/Gradle)
- Request batching
- Caching responses (with caution)
- Connection pooling (REST-Assured default)

---

## 10. Security Considerations

### Data Sensitivity
- No sensitive data in tests
- No credentials hardcoded
- No test data persistence

### API Security Testing
- Consider rate limiting (future enhancement)
- Validate SSL/TLS (in place)
- SQL injection testing (N/A for REST API)
- XSS testing (N/A for JSON responses)

---

## Appendix: Resource Links

- **REST Countries API**: https://restcountries.com/v3.1/
- **REST-Assured Documentation**: https://rest-assured.io/
- **JUnit 5 User Guide**: https://junit.org/junit5/docs/current/user-guide/
- **Maven Documentation**: https://maven.apache.org/
- **GitHub Actions**: https://github.com/features/actions
- **AssertJ Documentation**: https://assertj.github.io/assertj-core.html

---

**Document Version**: 1.0  
**Last Updated**: November 15, 2025  
**Maintained By**: QA Automation Team  
**Status**: Active

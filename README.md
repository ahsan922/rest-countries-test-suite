# REST Countries API - Test Automation Suite## Getting Started



A **production-grade test automation framework** demonstrating best practices for API testing, CI/CD integration, and quality assurance.Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.



## 🎯 Quick Start## Folder Structure



```bashThe workspace contains two folders by default, where:

# Clone repository

git clone https://github.com/yourusername/rest-countries-test-suite.git- `src`: the folder to maintain sources

cd rest-countries-test-suite- `lib`: the folder to maintain dependencies



# Build projectMeanwhile, the compiled output files will be generated in the `bin` folder by default.

mvn clean install

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

# Run all tests

mvn verify## Dependency Management



# View coverage reportThe `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

open target/site/jacoco/index.html
```

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Setup Instructions](#setup-instructions)
- [Running Tests](#running-tests)
- [CI/CD Pipeline](#cicd-pipeline)
- [Test Coverage](#test-coverage)
- [Documentation](#documentation)
- [Contributing](#contributing)

---

## 🔍 Overview

This project automates testing for the [**REST Countries API**](https://restcountries.com/v3.1/) - a public REST API providing comprehensive country information (geography, currencies, languages, borders, etc.).

### Why This API?
- ✅ **Public & Stable** - No authentication needed, consistent availability
- ✅ **Rich Features** - Multiple endpoints, filtering, search capabilities
- ✅ **Real-world Complexity** - Suitable for demonstrating professional test practices

### Key Highlights
- 🎯 **23+ Automated Tests** covering critical workflows
- ✅ **GitHub Actions CI/CD** with automated execution and reporting
- 📚 **Comprehensive Documentation** (Architecture, Strategy, Setup)
- 🏗️ **Scalable Architecture** ready to grow to 500+ tests
- 🔧 **Production-ready Code** with logging, error handling, configuration
- 📊 **Quality Metrics** including code coverage and performance tracking

---

## ✨ Features

### Test Automation
- **23+ Integration Tests** - Covering all major API endpoints
- **9+ Functional Tests** - Validating business logic and data consistency
- **Parameterized Tests** - DRY approach with @ParameterizedTest
- **Error Handling** - 404, timeout, and malformed response tests
- **Performance Testing** - Response time validation

### Technology Stack
- **Framework**: JUnit 5 with REST-Assured
- **Build**: Maven 3.8+
- **CI/CD**: GitHub Actions
- **Language**: Java 11+
- **Code Coverage**: Jacoco

### Best Practices
- ✅ Test isolation (no interdependencies)
- ✅ Deterministic execution (no flakiness)
- ✅ Clear naming and documentation
- ✅ Configuration management
- ✅ Comprehensive logging

---

## 🏗️ Architecture

See [ARCHITECTURE.md](./ARCHITECTURE.md) for complete technical documentation.

### Project Structure

```
rest-countries-test-suite/
├── src/
│   ├── main/java/com/api/automation/
│   │   ├── client/
│   │   │   └── ApiClient.java              # REST API client wrapper
│   │   ├── config/
│   │   │   └── ConfigManager.java          # Configuration management
│   │   ├── models/
│   │   │   └── Country.java                # Data model (POJO)
│   │   └── base/
│   │       └── BaseTest.java               # Base test class
│   ├── test/java/com/api/automation/
│   │   └── tests/
│   │       ├── CountriesApiIT.java         # Integration tests (14 tests)
│   │       └── CountriesApiFunctionalTest.java  # Functional tests (9 tests)
│   └── resources/
│       └── config.properties               # Configuration
├── .github/
│   └── workflows/
│       └── test-automation.yml             # GitHub Actions CI/CD
├── pom.xml
├── ARCHITECTURE.md
├── TEST_STRATEGY.md
└── README.md
```

---

## 🚀 Setup Instructions

### Prerequisites

- **Java 11+** (LTS recommended)
- **Maven 3.8.1+**
- **Git**

### Quick Setup

```bash
# 1. Clone repository
git clone https://github.com/yourusername/rest-countries-test-suite.git
cd rest-countries-test-suite

# 2. Build project
mvn clean install

# 3. Run all tests
mvn verify

# 4. View coverage report
mvn jacoco:report
open target/site/jacoco/index.html
```

See [ARCHITECTURE.md](./ARCHITECTURE.md) for detailed setup instructions (Docker, VS Code, IntelliJ, etc.).

---

## 🧪 Running Tests

### All Tests
```bash
mvn verify
```

### Specific Tests
```bash
# Integration tests only
mvn verify -Dtest=*IT

# Specific test class
mvn test -Dtest=CountriesApiIT

# Specific test method
mvn test -Dtest=CountriesApiIT#testGetAllCountries

# With coverage
mvn clean verify jacoco:report
```

### Advanced Options
```bash
# Debug mode
mvn test -X

# Parallel execution
mvn test -DparallelClasses=true -DthreadCount=3

# Skip tests
mvn clean install -DskipTests
```

---

## 🔄 CI/CD Pipeline

GitHub Actions automatically runs tests on:
- **Push to** `main` or `develop` branches
- **Pull Requests** to `main` or `develop`
- **Daily Schedule** (2 AM UTC)

**View Results**: GitHub Repo → Actions tab

**Pipeline Includes**:
- Automated test execution
- Coverage report generation
- Test result artifacts
- PR comments with results

---

## 📊 Test Coverage

### Current Coverage (v1.0)

| Category | Tests | Coverage |
|----------|-------|----------|
| **Integration** | 14 | All major endpoints |
| **Functional** | 9 | Data consistency, business logic |
| **Total** | **23** | **~80% of API surface** |

### Endpoints Tested
✅ GET /all - All countries  
✅ GET /alpha/{code} - Search by code  
✅ GET /name/{name} - Search by name  
✅ GET /region/{region} - Filter by region  
✅ GET /currency/{currency} - Filter by currency  
✅ GET /lang/{language} - Filter by language  
✅ GET /capital/{capital} - Search by capital  
✅ Data validation and error handling

---

## 📚 Documentation

**Required Reading:**

1. **[ARCHITECTURE.md](./ARCHITECTURE.md)** ⭐ **START HERE**
   - Technical architecture and design decisions
   - Complete setup instructions
   - Troubleshooting guide
   - ~300 lines, comprehensive

2. **[TEST_STRATEGY.md](./TEST_STRATEGY.md)** 📋 **READ SECOND**
   - Quality and testing strategy
   - Risk analysis and mitigation
   - Maintenance and scalability plan
   - Test coverage roadmap
   - ~400 lines, strategic

3. **[README.md](./README.md)** (this file)
   - Quick start and overview
   - Common commands
   - Troubleshooting FAQs

---

## 🐛 Troubleshooting

### Tests Fail Locally
```bash
# Update Maven cache
mvn clean install -U

# Check Java version
java -version

# Verify API connectivity
curl https://restcountries.com/v3.1/all

# Run with debug
mvn test -X
```

### Slow Execution
```bash
# Check network
ping restcountries.com

# Increase timeout in src/resources/config.properties
request.timeout.ms=10000

# Run in parallel
mvn test -DparallelClasses=true
```

### Dependency Issues
```bash
# Clear cache
rm -rf ~/.m2/repository

# Rebuild
mvn clean install
```

See [ARCHITECTURE.md](./ARCHITECTURE.md) for more troubleshooting tips.

---

## 🤝 Contributing

### Add a New Test

1. Choose test class or create new one
2. Write test following existing patterns:

```java
@Test
@DisplayName("Descriptive test name")
public void testSomething() {
    Response response = apiClient.get("/endpoint");
    response.then()
            .statusCode(200)
            .body("field", notNullValue());
}
```

3. Run locally: `mvn test -Dtest=YourTestClass`
4. Verify coverage: `mvn jacoco:report`
5. Create PR for review

### Commit Message Format
```
feat: Add tests for currency filtering
fix: Handle null borders in country model
refactor: Extract common assertions
docs: Update README with examples
```

---

## 📞 Support

- 📖 Check documentation first
- 🔍 Search existing GitHub issues
- 📝 Create new issue with details
- 💬 Contact maintainers for help

---

## 📄 License

MIT License - See LICENSE file

---

## 🎓 Resources

- [REST-Assured Documentation](https://rest-assured.io/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Documentation](https://maven.apache.org/)
- [GitHub Actions Guide](https://docs.github.com/en/actions)
- [REST Countries API](https://restcountries.com/)

---

## ✅ Quick Verification

After setup:
```bash
mvn clean verify     # Should pass all 23 tests in ~30s
mvn jacoco:report   # Should show ~80% coverage
```

---

**Last Updated**: November 15, 2025  
**Version**: 1.0.0  
**Maintained By**: QA Automation Team

**Ready to get started?** Clone the repo and run `mvn verify`! 🚀

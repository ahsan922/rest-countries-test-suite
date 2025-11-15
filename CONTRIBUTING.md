# Contribution Guidelines

## Welcome!

Thank you for your interest in contributing to the REST Countries API Test Automation Suite! This document provides guidelines and instructions for contributing.

## Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally
3. **Create a feature branch** (`git checkout -b feature/your-feature`)
4. **Make your changes**
5. **Commit with clear messages**
6. **Push to your fork**
7. **Create a Pull Request**

## Development Setup

```bash
# Clone your fork
git clone https://github.com/YOUR_USERNAME/rest-countries-test-suite.git
cd rest-countries-test-suite

# Add upstream remote
git remote add upstream https://github.com/ORIGINAL_OWNER/rest-countries-test-suite.git

# Install dependencies
mvn clean install

# Run tests to verify setup
mvn verify
```

## Code Standards

### Java Code Style

- **Language**: Java 11+
- **Indentation**: 4 spaces
- **Line Length**: Max 120 characters
- **Naming**:
  - Classes: `PascalCase`
  - Methods: `camelCase`
  - Constants: `UPPER_SNAKE_CASE`
  - Test methods: Descriptive, lowercase with underscores

### Example Test

```java
@Test
@DisplayName("Get all countries should return success response")
public void testGetAllCountries() {
    // Arrange
    String endpoint = "/all";
    
    // Act
    Response response = apiClient.get(endpoint);
    
    // Assert
    response.then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    
    Country[] countries = response.as(Country[].class);
    assertTrue(countries.length > 0);
    logger.info("Retrieved {} countries", countries.length);
}
```

## Commit Message Format

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types
- `feat`: New test or feature
- `fix`: Bug fix
- `refactor`: Code refactoring
- `docs`: Documentation
- `chore`: Build/tooling changes
- `test`: Test improvements

### Examples
```
feat(api): Add tests for capital endpoint filtering
fix(models): Handle null borders in Country model
refactor(base): Extract common assertions to BaseTest
docs: Update README with setup instructions
test: Improve flaky test resilience with retries
chore(maven): Upgrade REST-Assured to 5.4.0
```

## Testing Requirements

All contributions must include:

1. **Passing Tests**
   ```bash
   mvn verify -DskipFailingTests=false
   ```

2. **Code Coverage**
   - New code should maintain >80% coverage
   ```bash
   mvn jacoco:report
   ```

3. **No Warnings**
   ```bash
   mvn clean compile -Wall
   ```

4. **Style Compliance**
   ```bash
   mvn checkstyle:check  # (if configured)
   ```

## Pull Request Process

1. **Update README.md** - If adding new features
2. **Update documentation** - Update ARCHITECTURE.md or TEST_STRATEGY.md if relevant
3. **Test locally**
   ```bash
   mvn clean verify jacoco:report
   ```
4. **Push branch** to your fork
5. **Create PR** with descriptive title and body
6. **Link issues** if PR addresses GitHub issues
7. **Address feedback** from reviewers
8. **Merge** after approval

### PR Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] New test
- [ ] Bug fix
- [ ] Documentation
- [ ] Refactoring
- [ ] Performance improvement

## Related Issues
Fixes #123

## Test Coverage
- [ ] Unit tests added
- [ ] Integration tests added
- [ ] Coverage maintained (>80%)

## Checklist
- [ ] Code follows style guidelines
- [ ] Documentation updated
- [ ] No new warnings
- [ ] Tests pass locally
- [ ] Ready for review
```

## Testing Guidelines

### When Adding Tests

1. **Test One Thing**: Each test should verify one behavior
2. **Clear Names**: Use `@DisplayName` for clarity
3. **Good Assertions**: Use specific matchers, not generic asserts
4. **No Dependencies**: Tests should run independently
5. **Proper Setup/Teardown**: Use `@BeforeEach` and `@AfterEach`
6. **Document Complex Logic**: Add comments explaining "why"

### Test Organization

```
Integration Tests (CountriesApiIT.java)
├── Basic operations (GET, search)
├── Error handling
└── Edge cases

Functional Tests (CountriesApiFunctionalTest.java)
├── Data consistency
├── Business logic
└── Cross-endpoint validation
```

## Documentation

### Code Comments

Good:
```java
// Convert response to Country objects for type-safe assertions
Country[] countries = response.as(Country[].class);
```

Bad:
```java
// Get countries
Country[] countries = response.as(Country[].class);
```

### Javadoc

Public methods should have Javadoc:

```java
/**
 * Execute a GET request to the specified endpoint.
 *
 * @param endpoint the API endpoint (e.g., "/all", "/alpha/us")
 * @return the HTTP response
 * @throws RequestException if the request fails
 */
public Response get(String endpoint) {
    // Implementation
}
```

### Update Documentation

- **Code changes** → Update inline comments
- **New features** → Update ARCHITECTURE.md
- **Quality changes** → Update TEST_STRATEGY.md
- **New setup steps** → Update README.md

## Reporting Issues

### Bug Reports

Include:
- Clear description of the issue
- Steps to reproduce
- Expected behavior
- Actual behavior
- Logs/screenshots if applicable
- Environment (Java version, Maven version, OS)

### Feature Requests

Include:
- Clear description of desired feature
- Why it would be valuable
- Suggested implementation (optional)
- Acceptance criteria

## Community

- Be respectful and inclusive
- Assume good intentions
- Provide constructive feedback
- Help others learn
- Celebrate contributions

## Questions?

- Check [ARCHITECTURE.md](./ARCHITECTURE.md) for technical questions
- Check [TEST_STRATEGY.md](./TEST_STRATEGY.md) for quality questions
- Create an issue for general questions
- Email maintainers for urgent concerns

## Review Process

- Maintainers will review PRs within 2-3 business days
- Feedback will be constructive and educational
- Please address feedback or ask clarifying questions
- Once approved, a maintainer will merge the PR

## Acknowledgments

Thank you for contributing to making this project better! All contributors will be recognized in our CONTRIBUTORS.md file.

---

**Happy contributing!** 🚀

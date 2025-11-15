# Setup & Execution Guide

## Prerequisites Checklist

Before starting, verify you have:

- [ ] Java 11 or higher installed
- [ ] Maven 3.8.1 or higher installed
- [ ] Git installed
- [ ] Stable internet connection
- [ ] Text editor or IDE (VS Code, IntelliJ, Eclipse)

## Verification Steps

### 1. Check Java Installation

```bash
java -version
```

Expected output (version 11 or higher):
```
openjdk version "11.0.15" 2021-10-19
OpenJDK Runtime Environment (build 11.0.15+10-Ubuntu-0ubuntu0.20.04.1)
```

**If not installed:**
- Windows: Download from [adoptopenjdk.net](https://adoptopenjdk.net/)
- macOS: `brew install openjdk@11`
- Linux: `sudo apt-get install openjdk-11-jdk`

### 2. Check Maven Installation

```bash
mvn --version
```

Expected output (version 3.8.1 or higher):
```
Apache Maven 3.8.1 (...)
Maven home: /usr/local/opt/maven/libexec
```

**If not installed:**
- Windows: Download from [maven.apache.org](https://maven.apache.org/download.cgi)
- macOS: `brew install maven`
- Linux: `sudo apt-get install maven`

### 3. Check Git Installation

```bash
git --version
```

Expected output (version 2.x or higher):
```
git version 2.34.1
```

**If not installed:**
- Visit [git-scm.com](https://git-scm.com/)

## Installation Steps

### Option 1: Local Machine Setup (Recommended for Development)

#### Step 1: Clone Repository

```bash
# Choose a directory for your projects
cd ~/projects

# Clone the repository
git clone https://github.com/yourusername/rest-countries-test-suite.git
cd rest-countries-test-suite
```

#### Step 2: Install Dependencies

```bash
# This downloads and compiles the project, installs all dependencies
mvn clean install -DskipTests

# This should complete in 2-5 minutes depending on internet speed
# Watch for: [INFO] BUILD SUCCESS
```

#### Step 3: Run All Tests

```bash
# Execute the complete test suite
mvn verify

# Expected output (15-30 seconds):
# [INFO] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
```

#### Step 4: Generate Coverage Report

```bash
# Generate code coverage report
mvn jacoco:report

# View the report (choose one based on your OS)
# macOS:
open target/site/jacoco/index.html

# Windows:
start target/site/jacoco/index.html

# Linux:
firefox target/site/jacoco/index.html
```

### Option 2: Docker Setup (For CI/CD or Consistent Environments)

```bash
# Build Docker image with JDK and Maven
docker build -t rest-countries-tests .

# Run tests inside container
docker run --rm \
  -v $(pwd):/app \
  -w /app \
  rest-countries-tests \
  mvn clean verify

# For Windows, use:
docker run --rm ^
  -v %cd%:/app ^
  -w /app ^
  rest-countries-tests ^
  mvn clean verify
```

### Option 3: VS Code Setup

#### Install Extensions

1. Open VS Code
2. Go to Extensions (Ctrl+Shift+X / Cmd+Shift+X)
3. Search for and install:
   - "Extension Pack for Java" by Microsoft
   - "Test Runner for Java" by Microsoft
4. Restart VS Code

#### Open Project

```bash
# Open the project in VS Code
code .
```

#### Run Tests from UI

1. Explorer → Explorer (Ctrl+Shift+E)
2. Navigate to `src/test/java/com/api/automation/tests/`
3. Right-click test file → "Run Tests"
4. Results appear in the Test Explorer panel

### Option 4: IntelliJ IDEA Setup

#### Open Project

1. Launch IntelliJ
2. File → Open
3. Select `rest-countries-test-suite` folder
4. Click "Open"

#### Configure JDK

1. File → Project Structure
2. Project → SDK → Select JDK 11+
3. Click "OK"

#### Run Tests

1. Right-click test file → Run
2. Or right-click specific test method → Run

#### View Coverage

1. Run with coverage: Right-click → Run with Coverage
2. View report in Coverage tool window

---

## Verification That Everything Works

After setup, run this complete verification sequence:

```bash
# 1. Verify build succeeds
mvn clean install
# Expected: BUILD SUCCESS

# 2. Run all tests
mvn test
# Expected: Tests run: 23, Failures: 0, Errors: 0

# 3. Run integration tests
mvn verify -Dtest=*IT
# Expected: Tests run: 14, Failures: 0, Errors: 0

# 4. Run functional tests  
mvn verify -Dtest=*FunctionalTest
# Expected: Tests run: 9, Failures: 0, Errors: 0

# 5. Check coverage
mvn jacoco:report
# Expected: Coverage report at target/site/jacoco/index.html

# ✅ All steps succeeded = Ready to contribute!
```

---

## Common Issues & Fixes

### Issue: "Java not found"

**Solution:**
```bash
# Verify Java installation
java -version

# If not found, install Java (example for Ubuntu):
sudo apt-get update
sudo apt-get install openjdk-11-jdk

# Set JAVA_HOME (if needed)
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
```

### Issue: "mvn: command not found"

**Solution:**
```bash
# Verify Maven installation
mvn --version

# If not found, install Maven (example for Ubuntu):
sudo apt-get install maven

# Set M2_HOME (if needed)
export M2_HOME=/usr/share/maven
export PATH=$M2_HOME/bin:$PATH
```

### Issue: Tests timeout or fail with network error

**Solution:**
```bash
# Check API connectivity
curl https://restcountries.com/v3.1/all

# Increase timeout
# Edit src/resources/config.properties
# Change: request.timeout.ms=5000 → request.timeout.ms=10000

# Retry tests
mvn clean test
```

### Issue: Maven dependencies not downloading

**Solution:**
```bash
# Clear local repository
rm -rf ~/.m2/repository

# Force re-download
mvn clean install -U

# Check proxy settings if behind corporate proxy
# Edit ~/.m2/settings.xml to add proxy configuration
```

### Issue: Port 8080 already in use

**Solution:**
```bash
# Find process using port
# Windows:
netstat -ano | findstr :8080

# macOS/Linux:
lsof -i :8080

# Kill process
# Windows:
taskkill /PID <PID> /F

# macOS/Linux:
kill -9 <PID>
```

### Issue: Out of memory

**Solution:**
```bash
# Increase Maven heap size
export MAVEN_OPTS=-Xmx1024m

# Then run tests
mvn verify
```

---

## Running Specific Tests

### Run Single Test Class

```bash
mvn test -Dtest=CountriesApiIT
```

### Run Single Test Method

```bash
mvn test -Dtest=CountriesApiIT#testGetAllCountries
```

### Run Tests Matching Pattern

```bash
# Run all tests ending with "IT"
mvn verify -Dtest=*IT

# Run all tests containing "validation"
mvn verify -Dtest=*Validation*
```

### Run with Different Options

```bash
# Verbose output
mvn test -X

# Skip test compilation
mvn test -DskipTests=false -o

# Parallel execution (3 threads)
mvn test -DparallelClasses=true -DthreadCount=3

# Stop on first failure
mvn test -DfailIfNoTests=true
```

---

## Continuous Integration Setup

### GitHub Actions

Tests run automatically on:
- Push to `main` or `develop`
- Pull requests to `main` or `develop`
- Daily at 2 AM UTC

**View Results:**
1. Go to your GitHub repository
2. Click "Actions" tab
3. Select workflow run
4. View test results and artifacts

### Local CI Simulation

```bash
# Run complete pipeline locally
mvn clean verify jacoco:report

# This simulates what GitHub Actions does:
# 1. Clean build
# 2. Run all tests
# 3. Generate coverage reports
# 4. Verify build success
```

---

## Setting Up Your Fork (For Contributors)

```bash
# 1. Fork on GitHub (click Fork button)

# 2. Clone your fork
git clone https://github.com/YOUR_USERNAME/rest-countries-test-suite.git
cd rest-countries-test-suite

# 3. Add upstream remote
git remote add upstream https://github.com/ORIGINAL_OWNER/rest-countries-test-suite.git

# 4. Create feature branch
git checkout -b feature/your-feature

# 5. Make changes and test locally
mvn clean verify

# 6. Commit
git add .
git commit -m "feat: Your feature description"

# 7. Push to your fork
git push origin feature/your-feature

# 8. Create Pull Request on GitHub
```

---

## Performance Expectations

| Task | Expected Time |
|------|----------------|
| Install Maven | 5-10 min |
| Clone repo | < 1 min |
| First build | 3-5 min |
| Second build | 30-60 sec (cached) |
| Run all tests | 30-45 sec |
| Generate coverage | 5-10 sec |
| Full CI cycle | 3-5 min |

---

## Support

If you're stuck:

1. **Check README.md** - Quick overview
2. **Check ARCHITECTURE.md** - Technical details
3. **Check TEST_STRATEGY.md** - Quality aspects
4. **Search GitHub Issues** - Your issue may be documented
5. **Create new issue** - With clear description and logs

---

## Next Steps

After successful setup:

1. ✅ Read [README.md](./README.md) - Project overview
2. ✅ Read [ARCHITECTURE.md](./ARCHITECTURE.md) - Technical details
3. ✅ Read [TEST_STRATEGY.md](./TEST_STRATEGY.md) - Quality strategy
4. ✅ Review test examples in code
5. ✅ Write your first test
6. ✅ Create Pull Request

---

**Ready?** Run `mvn verify` and see your first test results! 🚀

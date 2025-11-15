# CI/CD Pipeline Deployment Guide

## Overview

This project uses **GitHub Actions** for continuous integration and continuous delivery. Tests run automatically on every push and pull request.

## Pipeline Architecture

```
┌─────────────┐
│  Developer  │ Pushes code
└──────┬──────┘
       │
       ▼
┌──────────────────────────┐
│  GitHub Repository       │
│  (main, develop, PR)     │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────────────────┐
│  GitHub Actions Workflow Trigger     │
│  test-automation.yml                 │
└──────┬───────────────────────────────┘
       │
       ├─► Job 1: Test on Java 11
       │   ├─ Checkout
       │   ├─ Setup JDK
       │   ├─ Cache Maven
       │   ├─ Run Tests
       │   ├─ Generate Report
       │   └─ Upload Artifacts
       │
       └─► Job 2: Test on Java 17
           ├─ Checkout
           ├─ Setup JDK
           ├─ Cache Maven
           ├─ Run Tests
           ├─ Generate Report
           └─ Upload Artifacts
```

## Workflow File

Location: `.github/workflows/test-automation.yml`

### Triggers

```yaml
on:
  push:
    branches: [ main, develop ]           # Run on push
  pull_request:
    branches: [ main, develop ]           # Run on PR
  schedule:
    - cron: '0 2 * * *'                  # Daily at 2 AM UTC
```

### Workflow Stages

#### 1. Checkout Code
```yaml
- name: Checkout code
  uses: actions/checkout@v3
```
Clones repository to runner

#### 2. Setup JDK
```yaml
- name: Set up JDK ${{ matrix.java-version }}
  uses: actions/setup-java@v3
  with:
    java-version: ${{ matrix.java-version }}
    distribution: 'temurin'
    cache: maven
```
Sets up Java environment and caches Maven dependencies

#### 3. Run Tests
```yaml
- name: Run integration tests
  run: mvn clean verify -DskipTests=false
```
Executes all tests

#### 4. Generate Reports
```yaml
- name: Generate test report
  if: always()
  run: mvn surefire-report:report
```
Creates test reports (runs even if tests fail)

#### 5. Upload Artifacts
```yaml
- name: Upload test results
  if: always()
  uses: actions/upload-artifact@v3
  with:
    name: test-results-java-${{ matrix.java-version }}
    path: target/surefire-reports/
```
Saves test results for download

---

## Matrix Testing

Tests run on multiple Java versions simultaneously:

```yaml
strategy:
  matrix:
    java-version: [ '11', '17' ]
```

**Why?**
- Ensures compatibility across Java versions
- Parallel execution (faster feedback)
- Catches version-specific issues

**Expected Behavior:**
- Tests run on Java 11 AND Java 17
- Both must pass for workflow to succeed
- Each takes ~3-5 minutes

---

## Setting Up in Your Repository

### Prerequisites
- GitHub account
- Repository forked or created
- GitHub Actions enabled (usually default)

### Step 1: Add Workflow File

The workflow file is already included at `.github/workflows/test-automation.yml`

### Step 2: Enable Actions

1. Go to your GitHub repository
2. Click "Settings"
3. Select "Actions" → "General"
4. Select "Allow all actions and reusable workflows"
5. Click "Save"

### Step 3: Verify Setup

```bash
# Push to trigger workflow
git add .
git commit -m "Initial commit"
git push origin main
```

### Step 4: Monitor Execution

1. Go to your repository
2. Click "Actions" tab
3. Select the running workflow
4. View real-time output

---

## Workflow Status

### Check Status

**In Repository:**
- Go to "Actions" tab
- View list of workflow runs
- Click to see details

**In PR:**
- Checks appear in PR conversation
- Shows pass/fail status
- Link to full workflow run

**In Commit:**
- Green check = All tests passed
- Red X = Tests failed
- Yellow dot = Still running

### Status Badge

Add to README for visibility:

```markdown
![Tests](https://github.com/username/repo/workflows/Test%20Automation/badge.svg)
```

---

## Monitoring & Debugging

### View Test Results

1. Go to workflow run
2. Click "Summary"
3. Scroll to "Annotations" section
4. See failed tests with details

### Download Artifacts

1. Go to workflow run
2. Scroll to "Artifacts" section
3. Download desired report:
   - `test-results-java-11`
   - `test-results-java-17`
   - `coverage-report-java-11`
   - `coverage-report-java-17`

### View Logs

1. Go to workflow run
2. Click specific job
3. Expand steps to see full logs
4. Search for errors

### Common Log Locations

```
[INFO] BUILD SUCCESS              ← Good
[INFO] BUILD FAILURE              ← Bad
[ERROR] ...                        ← Error details
[WARN] ...                         ← Warnings
Tests run: X, Failures: 0         ← Test summary
```

---

## Environment Variables

### Built-in GitHub Variables

```bash
GITHUB_REPOSITORY     # owner/repo
GITHUB_REF           # branch name
GITHUB_SHA           # commit hash
GITHUB_ACTOR         # username
GITHUB_RUN_ID        # workflow run ID
```

### Custom Variables

To add custom variables:

1. Go to Settings → Secrets and variables → Variables
2. Click "New repository variable"
3. Add name and value
4. Use in workflow: `${{ vars.VARIABLE_NAME }}`

### Setting Secrets (if needed in future)

1. Go to Settings → Secrets and variables → Secrets
2. Click "New repository secret"
3. Use in workflow: `${{ secrets.SECRET_NAME }}`

---

## Performance Optimization

### Current Optimizations

✅ **Maven Caching**
- Caches Maven dependencies
- Reduces build time from 5 min to 30 sec on subsequent runs

✅ **Parallel Testing**
- Tests run on multiple Java versions simultaneously
- Provides coverage while maintaining speed

✅ **Efficient Checkout**
- Shallow clone (single commit depth)
- Reduces download time

### Potential Future Optimizations

- [ ] Build matrix for different OS (Windows, macOS)
- [ ] Container caching for Docker builds
- [ ] Test result caching
- [ ] Artifact retention policies

---

## Troubleshooting

### Workflow Not Running

**Issue:** Workflow doesn't trigger on push

**Solution:**
```bash
# Verify trigger conditions
# 1. Check file modified (.github/workflows/test-automation.yml)?
# 2. Verify branch name (main or develop)?
# 3. Check Actions are enabled in Settings?

# Force trigger by making a commit
git add .
git commit -m "Trigger workflow" --allow-empty
git push
```

### Tests Failing in CI But Passing Locally

**Issue:** Green locally, red in CI

**Causes:**
- Different Java version
- Network/proxy differences
- File path issues (Windows vs Linux)
- Timing issues

**Solution:**
```bash
# Test with CI's exact Java version
java -version

# Run full CI simulation
mvn clean verify jacoco:report

# Check for absolute vs relative paths
# Use File.separator for cross-platform paths

# Add network timeout debugging
mvn test -X
```

### Slow Pipeline

**Issue:** Workflow takes too long

**Solution:**
```bash
# Check which step is slow in logs
# 1. Setup: Should be <1 min (with caching)
# 2. Build: Should be 30-60 sec
# 3. Tests: Should be 30-45 sec
# 4. Reports: Should be 5-10 sec

# To speed up:
# - Ensure Maven cache is working
# - Reduce test count (move slow tests to separate job)
# - Use parallel execution
```

### Out of Memory

**Issue:** "Exception in thread 'main' java.lang.OutOfMemoryError"

**Solution:**
```yaml
# In workflow file, increase heap size
- name: Run tests
  run: |
    export MAVEN_OPTS=-Xmx1024m
    mvn verify
```

### Artifact Upload Fails

**Issue:** "File not found" when uploading artifacts

**Solution:**
```yaml
# Ensure artifact path exists
# Check exact path from test output:
# - target/surefire-reports/ (test reports)
# - target/site/jacoco/ (coverage reports)

# Use wildcard if needed
- name: Upload results
  uses: actions/upload-artifact@v3
  with:
    path: target/surefire-reports/**
```

---

## Notifications

### GitHub Notifications

By default, GitHub sends notifications:
- When workflow fails
- When PR check fails
- When workflow completes (if subscribed)

### Email Notifications

1. Go to Settings → Notifications
2. Configure when to receive notifications
3. Choose delivery method

### Slack Integration (Optional)

To add Slack notifications:

```yaml
- name: Notify Slack
  if: failure()
  uses: slackapi/slack-github-action@v1
  with:
    webhook-url: ${{ secrets.SLACK_WEBHOOK }}
    payload: |
      {
        "text": "Test suite failed",
        "repository": "${{ github.repository }}"
      }
```

Setup:
1. Create Slack Incoming Webhook
2. Add to Settings → Secrets → SLACK_WEBHOOK
3. Add step to workflow

---

## Branch Policies

### Recommended Protection Rules

1. Go to Settings → Branches
2. Click "Add branch protection rule"
3. Branch name: `main`
4. Enable:
   - [ ] Require status checks to pass
   - [ ] Require all status checks to pass
   - [ ] Require branches to be up to date before merging
   - [ ] Require code reviews before merging
   - [ ] Require review from code owner
   - [ ] Require status checks to pass before merging

This ensures:
- All tests must pass before merge
- At least one code review required
- Protection from accidental bad merges

---

## Scaling the Pipeline

### Add More Test Scenarios

```yaml
# Run tests with different configurations
strategy:
  matrix:
    java-version: [ '11', '17', '21' ]
    os: [ ubuntu-latest, windows-latest ]
    test-suite: [ integration, functional, performance ]
```

### Run Custom Commands

```yaml
- name: Run performance tests
  run: |
    mvn verify -Dtest=*Performance* \
               -DthreadCount=10 \
               -DparallelClasses=true
```

### Deploy After Success

```yaml
- name: Deploy to staging
  if: success() && github.ref == 'refs/heads/main'
  run: |
    # Your deployment commands here
```

---

## Best Practices

✅ **Always use caching** - Speeds up builds significantly

✅ **Run tests on multiple Java versions** - Catch compatibility issues

✅ **Keep workflow files version controlled** - Track changes in git

✅ **Use matrix strategy** - Test multiple configurations efficiently

✅ **Add clear step names** - Easier to debug and understand logs

✅ **Use if: always()** - Generate reports even if tests fail

✅ **Document custom steps** - Help team understand workflow

✅ **Review logs regularly** - Catch issues early

✅ **Monitor artifact storage** - GitHub has limited storage

✅ **Use branch protection** - Enforce quality standards

---

## Quick Reference

### Common Commands in Workflow

```bash
# Build project
mvn clean install -DskipTests

# Run all tests
mvn verify

# Run specific tests
mvn test -Dtest=CountriesApiIT

# Generate coverage
mvn jacoco:report

# Generate test reports
mvn surefire-report:report

# With debugging
mvn test -X

# Parallel execution
mvn test -DparallelClasses=true -DthreadCount=3
```

### Workflow Exit Codes

| Code | Meaning | Action |
|------|---------|--------|
| 0 | Success | Workflow passes ✅ |
| 1 | Failure | Workflow fails ❌ |
| Other | Error | Environment issue ⚠️ |

---

## Support & Help

- **GitHub Actions Docs**: https://docs.github.com/en/actions
- **Workflow Syntax**: https://docs.github.com/en/actions/using-workflows/workflow-syntax-for-github-actions
- **Status Checks**: https://docs.github.com/en/rest/commits/statuses
- **Artifacts**: https://docs.github.com/en/actions/managing-workflow-runs/persisting-workflow-data-using-artifacts

---

**Last Updated**: November 15, 2025  
**Version**: 1.0.0

Ready to deploy? Push your code and watch the magic! ✨

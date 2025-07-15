@echo off
echo ========================================
echo API Test Suite - Detailed Execution Report
echo ========================================

echo.
echo Starting test execution with detailed reporting...
echo.

REM Clean previous reports
if exist "target\allure-results" rmdir /s /q "target\allure-results"
if exist "target\detailed-reports" rmdir /s /q "target\detailed-reports"
if exist "target\surefire-reports" rmdir /s /q "target\surefire-reports"

REM Run tests with detailed reporting
echo Running tests with Maven...
call mvn clean test -Dtest=TestNGApiRunner -DsuiteXmlFile=testng.xml

echo.
echo ========================================
echo Test Execution Complete
echo ========================================

REM Generate Allure report
echo.
echo Generating Allure report...
call allure generate target\allure-results --clean -o target\allure-report

REM Display summary
echo.
echo ========================================
echo EXECUTION SUMMARY
echo ========================================

if exist "target\surefire-reports\testng-results.xml" (
    echo Test Results XML: target\surefire-reports\testng-results.xml
)

if exist "target\detailed-reports" (
    echo Detailed Reports: target\detailed-reports\
    for %%f in (target\detailed-reports\*.txt) do echo   - %%f
)

if exist "target\allure-report" (
    echo Allure Report: target\allure-report\index.html
)

echo.
echo ========================================
echo REPORT LOCATIONS
echo ========================================
echo 1. Allure HTML Report: target\allure-report\index.html
echo 2. TestNG Reports: target\surefire-reports\
echo 3. Detailed Text Reports: target\detailed-reports\
echo 4. Cucumber Reports: target\cucumber-reports.html
echo.

REM Open Allure report if available
if exist "target\allure-report\index.html" (
    echo Opening Allure report...
    start "" "target\allure-report\index.html"
)

echo.
echo Test execution completed with detailed reporting!
pause 
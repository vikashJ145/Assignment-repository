package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestExecutionReporter extends TestListenerAdapter {
    
    private Map<String, Integer> testResults = new HashMap<>();
    private StringBuilder detailedReport = new StringBuilder();
    
    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
        detailedReport.append("=== TEST EXECUTION REPORT ===\n");
        detailedReport.append("Start Time: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
        detailedReport.append("Test Suite: ").append(testContext.getName()).append("\n\n");
    }
    
    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        updateTestResults("PASSED");
        logTestResult(tr, "PASSED");
        attachTestDetails(tr, "Test Passed Successfully");
    }
    
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        updateTestResults("FAILED");
        logTestResult(tr, "FAILED");
        attachTestDetails(tr, "Test Failed");
        attachFailureDetails(tr);
    }
    
    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        updateTestResults("SKIPPED");
        logTestResult(tr, "SKIPPED");
        attachTestDetails(tr, "Test Skipped");
    }
    
    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        generateSummaryReport(testContext);
        saveDetailedReport();
    }
    
    private void updateTestResults(String status) {
        testResults.put(status, testResults.getOrDefault(status, 0) + 1);
    }
    
    private void logTestResult(ITestResult tr, String status) {
        String testName = tr.getMethod().getMethodName();
        String className = tr.getTestClass().getName();
        long duration = tr.getEndMillis() - tr.getStartMillis();
        
        detailedReport.append(String.format("[%s] %s.%s - Duration: %dms\n", 
            status, className, testName, duration));
        
        if (tr.getThrowable() != null) {
            detailedReport.append("Error: ").append(tr.getThrowable().getMessage()).append("\n");
        }
        detailedReport.append("\n");
    }
    
    @Attachment(value = "Test Details", type = "text/plain")
    public String attachTestDetails(ITestResult tr, String message) {
        StringBuilder details = new StringBuilder();
        details.append("Test Name: ").append(tr.getMethod().getMethodName()).append("\n");
        details.append("Test Class: ").append(tr.getTestClass().getName()).append("\n");
        details.append("Status: ").append(message).append("\n");
        details.append("Duration: ").append(tr.getEndMillis() - tr.getStartMillis()).append("ms\n");
        
        if (tr.getParameters() != null && tr.getParameters().length > 0) {
            details.append("Parameters: ");
            for (Object param : tr.getParameters()) {
                details.append(param).append(" ");
            }
            details.append("\n");
        }
        
        return details.toString();
    }
    
    @Attachment(value = "Failure Details", type = "text/plain")
    public String attachFailureDetails(ITestResult tr) {
        StringBuilder failureDetails = new StringBuilder();
        failureDetails.append("Test Failed: ").append(tr.getMethod().getMethodName()).append("\n");
        failureDetails.append("Class: ").append(tr.getTestClass().getName()).append("\n");
        
        if (tr.getThrowable() != null) {
            failureDetails.append("Exception: ").append(tr.getThrowable().getClass().getSimpleName()).append("\n");
            failureDetails.append("Message: ").append(tr.getThrowable().getMessage()).append("\n");
            
            // Add stack trace
            failureDetails.append("Stack Trace:\n");
            for (StackTraceElement element : tr.getThrowable().getStackTrace()) {
                failureDetails.append("\t").append(element.toString()).append("\n");
            }
        }
        
        return failureDetails.toString();
    }
    
    private void generateSummaryReport(ITestContext testContext) {
        detailedReport.append("=== EXECUTION SUMMARY ===\n");
        detailedReport.append("Total Tests: ").append(testContext.getAllTestMethods().length).append("\n");
        detailedReport.append("Passed: ").append(testResults.getOrDefault("PASSED", 0)).append("\n");
        detailedReport.append("Failed: ").append(testResults.getOrDefault("FAILED", 0)).append("\n");
        detailedReport.append("Skipped: ").append(testResults.getOrDefault("SKIPPED", 0)).append("\n");
        detailedReport.append("Success Rate: ").append(calculateSuccessRate()).append("%\n");
        detailedReport.append("End Time: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
        
        // Attach summary to Allure
        attachSummaryReport();
    }
    
    private double calculateSuccessRate() {
        int total = testResults.values().stream().mapToInt(Integer::intValue).sum();
        int passed = testResults.getOrDefault("PASSED", 0);
        return total > 0 ? (double) passed / total * 100 : 0.0;
    }
    
    @Attachment(value = "Execution Summary", type = "text/plain")
    public String attachSummaryReport() {
        return detailedReport.toString();
    }
    
    private void saveDetailedReport() {
        try {
            String reportDir = "target/detailed-reports";
            new File(reportDir).mkdirs();
            
            String fileName = "detailed-execution-report-" + 
                new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + ".txt";
            
            File reportFile = new File(reportDir, fileName);
            try (FileWriter writer = new FileWriter(reportFile)) {
                writer.write(detailedReport.toString());
            }
            
            System.out.println("Detailed execution report saved to: " + reportFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save detailed report: " + e.getMessage());
        }
    }
} 
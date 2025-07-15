package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookstorePage extends BasePage {

    public WebDriver openHomePage() {
        driver = getDriver();
        driver.get("https://demoqa.com/books");  // sample URL
        return driver;
    }

    public void searchBook(String title) {
        WebElement searchBox = driver.findElement(By.id("searchBox"));
        searchBox.sendKeys(title);
    }

    public boolean isBookDisplayed(String title) {
        return driver.getPageSource().contains(title);
    }
}
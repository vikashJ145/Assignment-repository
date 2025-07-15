package stepdefs;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.BookstorePage;

import static org.testng.Assert.assertTrue;

public class BookstoreSteps {
    WebDriver driver;
    BookstorePage bookstore;

    @Given("I am on the bookstore homepage")
    public void openHomePage() {
        bookstore = new BookstorePage();
        driver = bookstore.openHomePage();
    }

    @When("I search for {string}")
    public void searchBook(String title) {
        bookstore.searchBook(title);
    }

    @Then("I should see the book titled {string}")
    public void verifyBookTitle(String title) {
        assertTrue(bookstore.isBookDisplayed(title));
    }
}

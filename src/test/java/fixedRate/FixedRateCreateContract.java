package fixedRate;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FixedRateCreateContract {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//div[normalize-space()='create contract']")
	WebElement submitButton;

	public FixedRateCreateContract(WebDriver driver) {
		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
		// Defining Explicit Wait
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Click on Submit button
	public void clickNext() {
		wait.until(ExpectedConditions.elementToBeClickable(submitButton));
		submitButton.click();
	}

}

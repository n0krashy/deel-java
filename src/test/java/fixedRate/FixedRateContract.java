package fixedRate;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FixedRateContract {

	WebDriver driver;
	WebDriverWait wait;
	final String URL = "https://app.letsdeel.com/contract/";

	@FindBy(css = ".button.heap-fixed-client-review-sign")
	WebElement reviewSignButton;

	public FixedRateContract(WebDriver driver) {
		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
		// Defining Explicit Wait
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		waitForLoading();
	}

	public String url(){
		return URL;
	}

	public void waitForLoading() {
		wait.until(ExpectedConditions.elementToBeClickable(reviewSignButton));
	}
}

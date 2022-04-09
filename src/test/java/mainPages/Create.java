package mainPages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Create {
	WebDriver driver;
	WebDriverWait wait;
	final String URL = "https://app.letsdeel.com/create";

	@FindBy(css = "a[class='anchor heap-start-fixed-contract'] div[class='box text-center height-100 contract-selector']")
	WebElement fixedRateButton;

	@FindBy(css = "a[class='anchor heap-start-milestone-contract'] div[class='box text-center height-100 contract-selector']")
	WebElement milestoneButtton;

	@FindBy(css = "a[class='anchor heap-start-payg-contract'] div[class='box text-center height-100 contract-selector']")
	WebElement payAsYouGoButton;

	public Create(WebDriver driver) {
		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
		// Defining Explicit Wait
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		waitForPageToLoad();
	}

	public String url(){
		return URL;
	}

	// Click on fixed term button
	public void clickFixedRate() {
		fixedRateButton.click();
	}

	// Click on milestone button
	public void clickMilestone() {
		milestoneButtton.click();
	}

	// Click on Pay As You Go button
	public void clickPayAsYouGo() {
		payAsYouGoButton.click();
	}
	
	public void waitForPageToLoad() {
		wait.until(ExpectedConditions.elementToBeClickable(fixedRateButton));
	}

}

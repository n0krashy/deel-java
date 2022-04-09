package fixedRate;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FixedRatePayment {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(name = "rate")
	WebElement paymentRateElement;

	@FindBy(css = "div[data-qa='currency-select']")
	WebElement currencyElement;

	@FindBy(xpath = "//div[contains(text(),'Monthly')]")
	WebElement paymentFrequency;

	@FindBy(xpath = "//button[@type='submit' and contains(@class, 'submit-payments-details')]")
	WebElement submitButton;

	public FixedRatePayment(WebDriver driver) {
		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
		// Defining Explicit Wait
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		waitForPageToLoad();
	}
	
	public void setPayment(String payment) {
		paymentRateElement.sendKeys(payment);
	}

	public void setCurrency(String currency) {
		currencyElement.click();
		driver.findElement(By.xpath("//div[contains(text(),'" + currency + "')]")).click();
	}

	public void setFrequency(String frequency) {
		paymentFrequency.click();
		driver.findElement(By.xpath("//div[contains(text(),'" + frequency + "')]")).click();
	}

	public void fillMandatoryFields(String rate, String currency, String frequency){
		// set payment rate to 1000
		setPayment(rate);
		// select GBP in currency
		setCurrency(currency);
		// change payment frequency to Weekly
		setFrequency(frequency);
	}
	
	// Click on Submit button
	public void clickNext() {
		submitButton.click();
	}

	public void waitForPageToLoad() {
		wait.until(ExpectedConditions.elementToBeClickable(paymentRateElement));
	}
}

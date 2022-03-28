package fixedRate;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import assists.Calendar;

public class FixedRateBasicInfo {
	WebDriver driver;
	WebDriverWait wait;

	@FindBy(name = "name")
	WebElement contractNameElement;

	@FindBy(css = "div[data-qa='contractor-tax-residence'] div[class='deel-ui__select__input-container']")
	WebElement taxCountryElement;

	@FindBy(css = "div[data-qa='contractor-tax-residence-province']")
	WebElement taxStateElement;

	@FindBy(css = "textarea[name='scope']")
	WebElement scopeElement;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement submitButton;
	
	@FindBy(xpath = "//div[@class='deel-ui__calendar-input-container__input_content_value']")
	WebElement calendarElement;

	public FixedRateBasicInfo(WebDriver driver) {
		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
		// Defining Explicit Wait
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));
	}

	// set Contract Name
	public void setContractName(String contractName) {
		contractNameElement.sendKeys(contractName);
	}

	// set scope
	public void setScope(String scope) {
		scopeElement.sendKeys(scope);
	}

	public void setTaxCountry(String country) {
		taxCountryElement.click();
		driver.findElement(By.xpath("//div[contains(text(),'" + country + "')]")).click();
	}

	public void setTaxState(String state) {
		wait.until(ExpectedConditions.elementToBeClickable(taxStateElement));
		taxStateElement.click();
		driver.findElement(By.xpath("//div[contains(text(),'" + state + "')]")).click();
	}
	
	public void setCalendar() {
		Calendar calendar = new Calendar (driver);
		calendar.clickCalendar(calendarElement);
	}

	// Click on Submit button
	public void clickNext() {
		submitButton.click();
	}

	public void waitForPageToLoad() {
		wait.until(ExpectedConditions.elementToBeClickable(contractNameElement));
	}

}

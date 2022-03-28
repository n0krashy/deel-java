package fixedRate;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FixedRateSpecialClause {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//div[normalize-space()='next']")
	WebElement submitButton;
	
	@FindBy(css = "button[data-qa='add-a-special-clause']")
	WebElement addSpecialClauseButton;
	
	@FindBy(css = ".textarea.pt-4.pr-7.pl-7.resizable.pb-4")
	WebElement specialClauseTextField;

	public FixedRateSpecialClause(WebDriver driver) {
		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
		// Defining Explicit Wait
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void clickAddSpecialClause() {
		wait.until(ExpectedConditions.elementToBeClickable(addSpecialClauseButton));
		addSpecialClauseButton.click();
	}
	
	public void writeSpecialClause(String specialClause) {
		specialClauseTextField.sendKeys(specialClause);
	}

	// Click on Submit button
	public void clickNext() {
		submitButton.click();
	}

}

package mainPages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
	WebDriver driver;
	WebDriverWait wait;
	final String URL = "https://app.letsdeel.com/";

	@FindBy(xpath = "//button[@class='button button-close']")
	WebElement closeButton;

	@FindBy(xpath = "//a[@id='CybotCookiebotDialogBodyButtonDecline']")
	WebElement acceptCookiesButton;

	@FindBy(xpath = "//p[normalize-space()='Create A Contract']")
	WebElement createButton;
	
	@FindBy(xpath = "//div[normalize-space()='view my contracts']")
	WebElement viewMyContractsButton;

	public Home(WebDriver driver) {
		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
		// Defining Explicit Wait
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		waitForViewContractsButtonToBeVisible();
	}

	// Click on create contract button
	public void closeWhatIsNew() {
		closeButton.click();
	}

	// Click on accept cookies button
	public void acceptCookies() {
		acceptCookiesButton.click();
	}

	// Click on create contract button
	public void createContract() {
		createButton.click();
	}

	public void goToCreate() {
		// close what is new window
		this.closeWhatIsNew();
		// accept cookies
		this.acceptCookies();
		// Click create button
		this.createContract();
	}

	public String url(){
		return URL;
	}

	public void waitForViewContractsButtonToBeVisible() {
		// Waiting until the logged in user's name becomes visible
		wait.until(ExpectedConditions.elementToBeClickable(viewMyContractsButton));
	}

}

package contracts;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MilestoneTest {

	static WebDriver driver;
	static WebDriverWait wait;
	static JavascriptExecutor jse;
	String contracrtTypeButtonSelector, contractNameSelector, currencySelector, clauseSelector, createButtonSelector,
			nextButtonSelector;

	@BeforeClass
	public static void initializeSelenium() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		jse = (JavascriptExecutor) driver;
	}

	@Test(priority = 0)
	void login() {
		// Navigate to the URL page
		driver.get("https://app.letsdeel.com/login");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Type your email']")));
		// enter email
		driver.findElement(By.xpath("//input[@placeholder='Type your email']")).sendKeys("14p8144@eng.asu.edu.eg");
		// enter password
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("@tKyTTPBB5Y4RRZ");
		// click sign in
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		// wait for nextpage to load
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='view my contracts']")));
		assertEquals("https://app.letsdeel.com/", driver.getCurrentUrl());
	}

	@Test(priority = 1)
	void goToCreate() {
		// accept cookies
		driver.findElement(By.xpath("//a[@id='CybotCookiebotDialogBodyButtonDecline']")).click();
		// click on create a contract
		driver.findElement(By.xpath("//p[normalize-space()='Create A Contract']")).click();
		// assert that next page is reached
		contracrtTypeButtonSelector = "a[class='anchor heap-start-milestone-contract'] div[class='box text-center height-100 contract-selector']";
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(contracrtTypeButtonSelector)));
		assertEquals("https://app.letsdeel.com/create", driver.getCurrentUrl());
	}

	@Test(priority = 2)
	void selectContractType() {
		// choose contract type
		driver.findElement(By.cssSelector(contracrtTypeButtonSelector)).click();
		// assert that next page is reached
		contractNameSelector = "input[name='name']";
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(contractNameSelector)));
		assertEquals("https://app.letsdeel.com/create/milestone", driver.getCurrentUrl());
	}

	@Test(priority = 3)
	void fillTheFirstPage() {
		// set contract name
		driver.findElement(By.cssSelector(contractNameSelector)).sendKeys("My contract");
		// choose tax country
		driver.findElement(
				By.cssSelector("div[data-qa='contractor-tax-residence'] div[class='deel-ui__select__input-container']"))
				.click();
		driver.findElement(By.xpath("//div[contains(text(),'United States')]")).click();
		// choose tax state
		driver.findElement(By.cssSelector("div[data-qa='contractor-tax-residence-province']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Colorado')]")).click();
		// write scope
		driver.findElement(By.cssSelector("textarea[name='scope']")).sendKeys("My scope");
		// scroll down
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		// find calendar
		WebElement calendarElement = driver
				.findElement(By.xpath("//div[@class='deel-ui__calendar-input-container__input_content_value']"));
		// perform calendar click as it is not click-able
		Actions builder = new Actions(driver);
		Action mouseOverAdmin = builder.moveToElement(calendarElement).build();
		mouseOverAdmin.perform();
		calendarElement.click();
		// get all visible days list
		List<WebElement> allDaysList = driver.findElements(By.xpath(
				"//button[contains(@class, 'react-calendar__tile') and contains(@class, 'react-calendar__month-view__days__day')]"));
		// get today
		WebElement today = driver.findElement(By.xpath(
				"//button[@class='react-calendar__tile react-calendar__tile--now react-calendar__tile--active react-calendar__tile--range react-calendar__tile--rangeStart react-calendar__tile--rangeEnd react-calendar__tile--rangeBothEnds react-calendar__month-view__days__day']"));
		// select previous day
		handleCalendar(allDaysList, today);

		// click next
		driver.findElement(By.xpath("//button[@type='submit' and contains(@class, 'submit-general-info')]")).click();

		// wait for next page to be loaded
		currencySelector = "div[data-qa='currency-select']";
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(currencySelector)));
	}

	@Test(priority = 4)
	void fillTheSecondPage() {
		// select GBP in currency
		driver.findElement(By.cssSelector(currencySelector)).click();
		driver.findElement(By.xpath("//div[contains(text(),'GBP - British Pound')]")).click();
		
		driver.findElement(By.xpath("//input[@data-qa='milestone-description']")).sendKeys("First Milestone");
		driver.findElement(By.xpath("//input[@data-qa='milestone-amount']")).sendKeys("100");

		// click next
		driver.findElement(By.xpath("//button[@data-qa='next' and contains(@class, 'heap-create-contract-milestone-payment-details-next')]")).click();

		// wait for next page to be loaded
		nextButtonSelector = "//button[@data-qa='next' and contains(@class, 'submit-extras heap-create-contract-milestone-benefits-extras-next')]";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nextButtonSelector)));
	}

	@Test(priority = 5)
	void addSpecialClause() {
		// click next
		driver.findElement(
				By.xpath(nextButtonSelector))
				.click();

		// wait for next page to be loaded
		createButtonSelector = "//div[normalize-space()='create contract']";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(createButtonSelector)));
	}

	@Test(priority = 6)
	void createContract() {
		// click on create button
		driver.findElement(By.xpath(createButtonSelector)).click();
		
		//assert contract is created and it's page has loaded
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='review & sign']")));
		assertTrue(driver.getCurrentUrl().contains("https://app.letsdeel.com/contract/"));
	}
	
	@AfterClass
    public static void TearDownClass()
    {
		driver.quit(); // close the browser
    }

	void handleCalendar(List<WebElement> allDaysList, WebElement today) {
		int count = allDaysList.size();
		WebElement prevElement = null, currElement, lastElement;
		boolean firstDayInCalendar = false;
		for (int i = 0; i < allDaysList.size(); i++) {
			currElement = allDaysList.get(i);
			if (currElement.getText().equals(today.getText())) {
				// check if today is the first day in calendar, then we need to switch back
				// months to the previous month
				if (i == 0) {
					firstDayInCalendar = true;
				} else {
					// choose yesterday directly
					firstDayInCalendar = false;
					prevElement = allDaysList.get(i - 1);
				}
				break;
			}
		}

		// if today is the first day in the calendar month, swipe back to the month before and select the last element
		if (firstDayInCalendar) {
			driver.findElement(By.cssSelector(
					"button[class='react-calendar__navigation__arrow react-calendar__navigation__prev-button']"))
					.click();
			allDaysList = driver.findElements(By.xpath(
					"//button[contains(@class, 'react-calendar__tile') and contains(@class, 'react-calendar__month-view__days__day')]"));
			lastElement = allDaysList.get(count - 1);
			lastElement.click();
		} else {
			// else select the previous day directly
			prevElement.click();
		}
	}
}

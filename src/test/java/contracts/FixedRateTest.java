package contracts;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import fixedRate.FixedRateBasicInfo;
import fixedRate.FixedRateContract;
import fixedRate.FixedRateCreateContract;
import fixedRate.FixedRateFirstPayment;
import fixedRate.FixedRatePayment;
import fixedRate.FixedRateSpecialClause;
import io.github.bonigarcia.wdm.WebDriverManager;
import mainPages.Create;
import mainPages.Home;
import mainPages.Login;

public class FixedRateTest {

	Login loginPage;
	Home home;
	Create create;
	FixedRateBasicInfo fixedRateBasicInfo;
	FixedRatePayment fixedRatePaymentPage;
	static WebDriver driver;
	static WebDriverWait wait;
	static JavascriptExecutor jse;
	String fixedRateButtonSelector, contractNameSelector, paymentSelector, clauseSelector, createButtonSelector,
			nextButtonSelector;

	@BeforeClass(description = "Configure web driver before test")
	public static void initializeSelenium() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		jse = (JavascriptExecutor) driver;
		// Navigate to the URL page
		driver.get("https://app.letsdeel.com/login");
	}

	@Test(priority = 0)
	void login() {
		loginPage = new Login(driver);
		//Verify login page title
	    String loginPageTitle = loginPage.getLoginTitle();
	    assertTrue(loginPageTitle.contains("Deel - Payroll for remote teams"));
		loginPage.login("14p8144@eng.asu.edu.eg", "@tKyTTPBB5Y4RRZ");
	}

	@Test(priority = 1)
	void goToCreate() {
		home = new Home(driver);
		// wait for page to load
		home.waitForViewContractsButtonToBeVisible();
		// assert that we are in homepage
		assertEquals("https://app.letsdeel.com/", driver.getCurrentUrl());
		// go to create a contract
		home.goToCreate();
	}

	@Test(priority = 2)
	void goToFixedRate() {
		// choose fixed rate
		create = new Create(driver);
		create.waitForPageToLoad();
		// assert that we are in contract creation page
		assertEquals("https://app.letsdeel.com/create", driver.getCurrentUrl());
		// go to fixed rate contract
		create.clickFixedRate();
	}

	@Test(priority = 3)
	void fillTheFirstPage() {
		fixedRateBasicInfo = new FixedRateBasicInfo(driver);
		fixedRateBasicInfo.waitForPageToLoad();
		assertEquals("https://app.letsdeel.com/create/fixed", driver.getCurrentUrl());
		fixedRateBasicInfo.setContractName("My contract");
		fixedRateBasicInfo.setTaxCountry("United States");
		fixedRateBasicInfo.setTaxState("Colorado");
		fixedRateBasicInfo.setScope("My Scope");
		// scroll down
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		// set Calendar
		fixedRateBasicInfo.setCalendar();
		// click next
		fixedRateBasicInfo.clickNext();

		// wait for next page to be loaded
		paymentSelector = "//input[@name='rate']";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(paymentSelector)));
	}

	@Test(priority = 4)
	void fillPaymentPage() {
		fixedRatePaymentPage = new FixedRatePayment(driver);
		fixedRatePaymentPage.waitForPageToLoad();
		// set payment rate to 1000
		fixedRatePaymentPage.setPayment("1000");
		// select GBP in currency
		fixedRatePaymentPage.setCurrency("GBP - British Pound");
		// change payment frequency to Weekly
		fixedRatePaymentPage.setFrequency("Weekly");

		// click next
		fixedRatePaymentPage.clickNext();
	}

	@Test(priority = 5)
	void skipFirstPaymentPage() {
		FixedRateFirstPayment fixedRateFirstPayment = new FixedRateFirstPayment(driver);
		// click next
		fixedRateFirstPayment.clickNext();
	}

	@Test(priority = 6)
	void addSpecialClause() {
		FixedRateSpecialClause fixedRateSpecialClause = new FixedRateSpecialClause(driver);
		// click on add a special clause button
		fixedRateSpecialClause.clickAddSpecialClause();
		// write a clause
		fixedRateSpecialClause.writeSpecialClause("This is special clause.");
		// click next
		fixedRateSpecialClause.clickNext();
	}

	@Test(priority = 7)
	void createContract() {
		FixedRateCreateContract fixedRateCreateContract = new FixedRateCreateContract(driver);
		fixedRateCreateContract.clickNext();
	}
	
	@Test(priority = 8)
	void reachedContractPage() {
		FixedRateContract fixedRateContract = new FixedRateContract(driver);
		fixedRateContract.waitForLoading();
		
		//assert contract is created and it's page has loaded
		assertTrue(driver.getCurrentUrl().contains("https://app.letsdeel.com/contract/"));
	}
	
	@AfterClass
    public static void TearDownClass()
    {
		driver.quit(); // close the browser
    }

}

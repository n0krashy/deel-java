package contracts;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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

	static Login loginPage;
	static Home home;
	static Create create;
	static FixedRateBasicInfo fixedRateBasicInfo;
	static FixedRatePayment fixedRatePaymentPage;
	static WebDriver driver;
	static WebDriverWait wait;
	static JavascriptExecutor jse;
	String contractType = "Fixed rate";
	String contractName = "New Fixed rate";
	String country = "United States";
	String state = "Colorado";
	String scope = "My scope";
	String rate = "1000";
	String currency = "GBP - British Pound";
	String frequency = "Weekly";
	String specialClause = "This is special Clause!";
	String email = "14p8144@eng.asu.edu.eg";
	String password = "@tKyTTPBB5Y4RRZ";


	@BeforeClass(description = "Configure web driver before test")
	public static void initializeSelenium() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		jse = (JavascriptExecutor) driver;
	}

	@Test()
	void navigateToLoginPage() {
		loginPage = new Login(driver);
		assertEquals(loginPage.title(), driver.getTitle());
	}

	@Test(priority = 1)
	void login(){
		loginPage.login(email, password);
		home = new Home(driver);
		// assert that we are in homepage
		assertEquals(home.url(), driver.getCurrentUrl());
	}

	@Test(priority = 2)
	void goToCreate() {
		// go to create a contract
		home.goToCreate();
		create = new Create(driver);
		// assert that we are in contract creation page
		assertEquals(create.url(), driver.getCurrentUrl());
	}

	@Test(priority = 3)
	void goToFixedRate() {
		// go to fixed rate contract
		create.clickFixedRate();
		fixedRateBasicInfo = new FixedRateBasicInfo(driver);
		assertEquals(fixedRateBasicInfo.url(), driver.getCurrentUrl());
	}

	@Test(priority = 4)
	void fillTheFirstPage() {
		fixedRateBasicInfo.fillMandatoryFields(contractName, country, state, scope, jse);
		fixedRateBasicInfo.clickNext();
	}

	@Test(priority = 5)
	void fillPaymentPage() {
		fixedRatePaymentPage = new FixedRatePayment(driver);
		fixedRatePaymentPage.fillMandatoryFields(rate, currency, frequency);
		fixedRatePaymentPage.clickNext();
	}

	@Test(priority = 6)
	void skipFirstPaymentPage() {
		FixedRateFirstPayment fixedRateFirstPayment = new FixedRateFirstPayment(driver);
		// click next
		fixedRateFirstPayment.clickNext();
	}

	@Test(priority = 7)
	void addSpecialClause() {
		FixedRateSpecialClause fixedRateSpecialClause = new FixedRateSpecialClause(driver);
		// click on add a special clause button
		fixedRateSpecialClause.clickAddSpecialClause();
		// write a clause
		fixedRateSpecialClause.writeSpecialClause(specialClause);
		// click next
		fixedRateSpecialClause.clickNext();
	}

	@Test(priority = 8)
	void createContract() {
		FixedRateCreateContract fixedRateCreateContract = new FixedRateCreateContract(driver);
		fixedRateCreateContract.clickNext();
	}
	
	@Test(priority = 9)
	void reachedContractPage() {
		FixedRateContract fixedRateContract = new FixedRateContract(driver);
		//assert contract is created and it's page has loaded
		assertTrue(driver.getCurrentUrl().contains(fixedRateContract.url()));
	}
	
	@AfterClass
    public static void TearDownClass()
    {
		driver.quit(); // close the browser
    }

}

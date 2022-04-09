package mainPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
	WebDriver driver;
	WebDriverWait wait;
	By emailXpath = By.xpath("//input[@placeholder='Type your email']");
	By passwordXpath = By.xpath("//input[@name='password']");
	By submitXpath = By.xpath("//button[@type='submit']");
    private final String PAGE_URL = "https://app.letsdeel.com/login";

	public Login(WebDriver driver) {
		this.driver = driver;
        driver.get(PAGE_URL);
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));
	}
	
	public String title() {
        wait.until(ExpectedConditions.elementToBeClickable(emailXpath));
		return driver.getTitle();
	}
	
	//Set email in textbox
    public void setEmail(String email){
        driver.findElement(emailXpath).sendKeys(email);
    }
    
    //Set password in password textbox
    public void setPassword(String password){
         driver.findElement(passwordXpath).sendKeys(password);
    }

    //Click on login button
    public void clickLogin(){
            driver.findElement(submitXpath).click();
    }
    
    public void login(String email,String password){
        wait.until(ExpectedConditions.elementToBeClickable(emailXpath));
        //Fill email
        this.setEmail(email);
        //Fill password
        this.setPassword(password);
        //Click Login button
        this.clickLogin();        
    }
    
}

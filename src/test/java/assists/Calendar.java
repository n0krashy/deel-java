package assists;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class Calendar {
	WebDriver driver;
	
	By todayXpath = By.cssSelector("button[class*='react-calendar__tile--now react-calendar__tile--active']");
	By allDaysXpath = By.cssSelector("button[class*='react-calendar__month-view__days__day']");
	By previousMonthButtonXpath = By.cssSelector("button[class='react-calendar__navigation__arrow react-calendar__navigation__prev-button']");

	public Calendar(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickCalendar(WebElement calendarElement) {
		Actions builder = new Actions(driver);
		Action mouseOverAdmin = builder.moveToElement(calendarElement).build();
		mouseOverAdmin.perform();
		calendarElement.click();
		getPreviousDay();
	}

	public List<WebElement> getAllVisibleDays() {
		return driver.findElements(allDaysXpath);
	}

	public WebElement today(){
		return driver.findElement(todayXpath);
	}

	public WebElement previousMonthButton(){
		return driver.findElement(previousMonthButtonXpath);
	}

	public void getPreviousDay() {
		WebElement yesterday = null;
		List<WebElement> allDaysList = getAllVisibleDays();
		for (int i = 0; i < allDaysList.size(); i++) {
			if(allDaysList.get(i).equals(today())){
				if (i == 0){
					previousMonthButton().click();
					allDaysList = getAllVisibleDays();
					int lastDayIndex = allDaysList.size()-1;
					yesterday = allDaysList.get(lastDayIndex);
				} else {
					yesterday = allDaysList.get(i - 1);
				}
				break;
			}
		}
		assert yesterday != null;
		yesterday.click();
	}

}

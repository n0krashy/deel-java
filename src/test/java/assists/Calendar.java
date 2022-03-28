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
	
	By todayXpath = By.xpath("//button[@class='react-calendar__tile react-calendar__tile--now react-calendar__tile--active react-calendar__tile--range react-calendar__tile--rangeStart react-calendar__tile--rangeEnd react-calendar__tile--rangeBothEnds react-calendar__month-view__days__day']");
	By allDaysXpath = By.xpath("//button[contains(@class, 'react-calendar__tile') and contains(@class, 'react-calendar__month-view__days__day')]");
	By previousMonthButtonXpath = By.cssSelector("button[class='react-calendar__navigation__arrow react-calendar__navigation__prev-button']");
	By previousMonthDaysXpath = By.xpath("//button[contains(@class, 'react-calendar__tile') and contains(@class, 'react-calendar__month-view__days__day')]");
	
	public Calendar(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickCalendar(WebElement calendarElement) {
		Actions builder = new Actions(driver);
		Action mouseOverAdmin = builder.moveToElement(calendarElement).build();
		mouseOverAdmin.perform();
		calendarElement.click();
		getAllVisibleDays();
	}

	public void getAllVisibleDays() {
		// get all visible days list
		List<WebElement> allDaysList = driver.findElements(allDaysXpath);
		// get today
		WebElement today = driver.findElement(todayXpath);
		getPreviousDay(allDaysList, today);
	}

	public void getPreviousDay(List<WebElement> allDaysList, WebElement today) {
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

		// if today is the first day in the calendar month, swipe back to the month
		// before and select the last element
		if (firstDayInCalendar) {
			driver.findElement(previousMonthButtonXpath).click();
			allDaysList = driver.findElements(previousMonthDaysXpath);
			lastElement = allDaysList.get(count - 1);
			lastElement.click();
		} else {
			// else select the previous day directly
			prevElement.click();
		}
	}

}

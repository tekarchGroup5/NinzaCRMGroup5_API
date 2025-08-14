package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Listeners;

import listeners.ListenersCRM;
import utils.ActionUtils;
@Listeners(ListenersCRM.class)
public class CRM_ProductsPage extends BasePage {

	
	@FindBy(xpath = "//h2")
	private WebElement ProductsHeaderEle;
	
	

	public CRM_ProductsPage(WebDriver driver) {
		super(driver);

	}


	public String getPageTitle(WebDriver driver) {

		return driver.getTitle();

	}

	public String getTextFromElement() {

		return getTextFromElement(ProductsHeaderEle);
	}

	
}

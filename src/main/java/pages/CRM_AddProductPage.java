package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import listeners.ListenersCRM;
import utils.WaitUtils;

@Listeners(ListenersCRM.class)
public class CRM_AddProductPage extends BasePage {

	@FindBy(xpath = "//h3")
	private WebElement AddProductPageHeaderEle;
	@FindBy(xpath = "//input[@name='productId']")
	private WebElement ProductIDTextBxEle;
	@FindBy(xpath = "//input[@name='productName']")
	private WebElement AddProductNameTxtBxEle;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement addButtonEle;
	@FindBy(xpath = "//select[@name='productCategory']")
	private WebElement selectCategoryEle;
	@FindBy(xpath = "//input[@name='quantity']")
	private WebElement quantityEle;
	@FindBy(xpath = "//input[@name='price']")
	private WebElement pricePerUnitEle;
	@FindBy(xpath = "//select[@name='vendorId']")
	private WebElement selectVendorIdEle;
	@FindBy(className = "Toastify__toast-body")
	private WebElement successToastMsgEle;
	@FindBy(xpath = "//ul[@class='pagination']//a")
	private List<WebElement> pages;

	@FindBy(xpath = "//select[@class='form-control']")
	private WebElement SearchDrpdwn;
	@FindBy(xpath = "//input[@placeholder='Search by product Name']")
	private WebElement SearchTextBxEle;
	@FindBy(xpath = "//a[@class='edit']")
	private WebElement editButtonEle;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement updateButtonEle;
	@FindBy(xpath = "//a[@class='delete']")
	private WebElement deleteButtonEle;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement deleteConfirmButtonEle;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement cancelConfirmButtonEle;
	@FindBy(tagName = "form")
	private WebElement formEle;
	
	
	public CRM_AddProductPage(WebDriver driver) {
		super(driver);
	}

	public String getTextFromElement() {

		return getTextFromElement(AddProductPageHeaderEle);
	}

	public String verifyValueFromTextBox() {

		return getValueFrmField(ProductIDTextBxEle);

		// return ProductIDTextBxEle.getAttribute("value");
	}

	public String verifyValueFrmQuantity() {

		// return quantityEle.getAttribute("value");
		return getValueFrmField(quantityEle);
	}

	public String verifyPricePerUnit() {

		// return quantityEle.getAttribute("value");
		return getValueFrmField(pricePerUnitEle);
	}

	public void enterProductName(String name) {

		enterText(AddProductNameTxtBxEle, name);

	}

	public void clickOnAdd() {
		addButtonEle.click();
	}

	public String errorProductNameValidationMsg() {
		// String validationMessage=errorValidationMsg(AddProductNameTxtBxEle);
		// String validationMessage =
		// AddProductNameTxtBxEle.getAttribute("validationMessage");
		String validationMessage = errorValidationMsg(AddProductNameTxtBxEle);
		System.out.println("ErrorMessage is: " + validationMessage);

		return validationMessage;

	}

	public String errorSelectCategoryValidationMsg() {

		String validationMessage = errorValidationMsg(selectCategoryEle);
		System.out.println("ErrorMessage is: " + validationMessage);

		return validationMessage;

	}

	public String errorQunatityValidationMsg() {

		String validationMessage = errorValidationMsg(quantityEle);
		System.out.println("ErrorMessage is: " + validationMessage);

		return validationMessage;

	}

	public String errorPriceValidationMsg() {

		String validationMessage = errorValidationMsg(pricePerUnitEle);
		System.out.println("ErrorMessage is: " + validationMessage);

		return validationMessage;

	}

	public String errorVendorValidationMsg() {

		String validationMessage = errorValidationMsg(selectVendorIdEle);
		System.out.println("ErrorMessage is: " + validationMessage);

		return validationMessage;

	}

	public void selectCategory(int data) {
		selectIndex(selectCategoryEle, data);
	}

	public void selectVendorByIndex(int data) {
		selectIndex(selectVendorIdEle, data);
	}

	public void selectVendorByValue(String data) {
		selectValue(selectVendorIdEle, data);
	}

	public void enterQuantiy(String qnty) {
		quantityEle.sendKeys(qnty);
		// enterText(quantityEle, qnty);

	}

	public void enterPrice(String price) {
		// pricePerUnitEle.sendKeys(price);

		enterText(pricePerUnitEle, price);

	}

	public String successMsg() {

		WaitUtils.explicitlyWaitForVisibility(driver, successToastMsgEle);
		return getTextFromElement(successToastMsgEle);

	}

	public int verifyCategoryDrpDwn() {
		return checkDropDownOptions(selectCategoryEle);
	}

	public int verifyVendorDrpDwn() {

		WaitUtils.explicitlyWaitForVisibility(driver, selectVendorIdEle);
		return checkDropDownOptions(selectVendorIdEle);
	}

	public void selectSearchProductByName(String value) {

		selectValue(SearchDrpdwn, value);

	}

	public void searchProductName(String name) {

		WaitUtils.explicitlyWaitForVisibility(driver, SearchTextBxEle);
		enterText(SearchTextBxEle, name);

	}

	public void clickOnEdit() {
		WaitUtils.explicitlyWaitForVisibility(driver, editButtonEle);
		editButtonEle.click();

	}
	public void clickOnUpdate() {
		WaitUtils.explicitlyWaitForVisibility(driver, updateButtonEle);
		updateButtonEle.click();

	}
	public void clickOnDelete() {
		WaitUtils.explicitlyWaitForVisibility(driver, deleteButtonEle);
		deleteButtonEle.click();

	}
	public void clickOnDeleteConfirm() {
		WaitUtils.explicitlyWaitForVisibility(driver, deleteConfirmButtonEle);
		deleteConfirmButtonEle.click();

	}
	public  boolean checkProductIDInputBoxReadOnly() {
		boolean isDisabled;
		return  isDisabled = AddProductPageHeaderEle.isEnabled();

	}


	public boolean isProductPresentInTable(String productName) {
		for (WebElement page : pages) {
			page.click();
			// Thread.sleep(1000); // Or use WebDriverWait to wait for table update
			WaitUtils.explicitlyWaitPresenceOfTheElement(driver, pages);
			if (driver.findElements(By.xpath("//td[text()='" + productName + "']")).size() > 0) {
				System.out.println("✅ Found product on page: " + page.getText());
				return true;
			}
		}
		return false;

	}

}

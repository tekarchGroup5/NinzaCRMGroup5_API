package pages;


import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static utils.ActionUtils.mouseHover;

public class CRM_HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(CRM_HomePage.class);

    public CRM_HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "navbarNav")
    public WebElement navigationBar;

    @FindBy(xpath = "//div[@class=\"nav-link\"]")
    public WebElement adminConsole;

    @FindBy(xpath = "//div[normalize-space()='Create User']")
    public WebElement createUser;

    @FindBy(xpath = "//a[normalize-space()='Campaigns']")
    public WebElement campaigns;

    @FindBy(xpath = "//a[normalize-space()='Contacts']")
    public WebElement contacts;

    @FindBy(xpath = "//a[normalize-space()='Leads']")
    public WebElement leads;

    @FindBy(xpath = "//a[normalize-space()='Opportunities']")
    public WebElement opportunities;

    @FindBy(xpath = "//a[normalize-space()='Products']")
    public WebElement products;

    @FindBy(xpath = "//a[normalize-space()='Quotes']")
    public WebElement quotes;

    @FindBy(xpath = "//a[normalize-space()='Purchase Order']")
    public WebElement purchaseOrder;

    @FindBy(xpath = "//a[normalize-space()='Sales Order']")
    public WebElement salesOrder;

    @FindBy(xpath = "//a[normalize-space()='Invoices']")
    public WebElement invoice;
    
    @FindBy(xpath = "//span[normalize-space()='Add Product']") //priyanka
	private WebElement addProductButton;

    
    
    public boolean isHomePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            logger.debug("Checking if user is on CRM Home Page");
            wait.until(ExpectedConditions.visibilityOf(navigationBar));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public CRM_CreateUsersPage navigateToCreateUser(){
       /* Actions actions = new Actions(driver);
        actions.moveToElement(adminConsole).perform();*/
        mouseHover(driver, adminConsole);
        createUser.click();
        return new CRM_CreateUsersPage(driver);
    }
    /**
     * Clicks on the Campaigns link and navigates to the CRM_CampaignsPage.
     */
    public CRM_CampaignsPage clickCampaigns() {
        campaigns.click();
        logger.debug("Campaigns link clicked");
        return new CRM_CampaignsPage(driver);
    }
    
    public CRM_CreateUsersPage clickUsers() {
        campaigns.click();
        logger.debug("Campaigns link clicked");
        return new CRM_CreateUsersPage(driver);
    }

    public CRM_ContactsPage clickContacts() {
        contacts.click();
        logger.debug("Contacts link clicked");
        return new CRM_ContactsPage(driver);
    }

    public CRM_LeadsPage clickLeads() throws InterruptedException {
        leads.click();
        logger.debug("Leads link clicked");
        
        Thread.sleep(2000); // Wait for the page to load completely
        return new CRM_LeadsPage(driver);
    }
    

    public CRM_OpportunitiesPage clickOpportunities() {
        opportunities.click();
        logger.debug("Opportunities link clicked");
        return new CRM_OpportunitiesPage(driver);
    }

    public CRM_ProductsPage clickProducts() {
        products.click();
        logger.debug("Products link clicked");
        return new CRM_ProductsPage(driver);
    }

    public CRM_AddProductPage clickAddProduct() {//priyanka
    	addProductButton.click();
        logger.debug("Products link clicked");
        return new CRM_AddProductPage(driver);
    }
    public CRM_QuotesPage clickQuotes() {
        quotes.click();
        logger.debug("Quotes link clicked");
        return new CRM_QuotesPage(driver);
    }

    public CRM_PurchaseOrderPage clickPurchaseOrder() {
        purchaseOrder.click();
        logger.debug("Purchase Order link clicked");
        return new CRM_PurchaseOrderPage(driver);
    }

    public CRM_SalesOrderPage clickSalesOrder() {
        salesOrder.click();
        logger.debug("Sales Order link clicked");
        return new CRM_SalesOrderPage(driver);
    }

    public CRM_InvoicePage clickInvoice() {
        invoice.click();
        logger.debug("Invoice link clicked");
        return new CRM_InvoicePage(driver);
    }
}
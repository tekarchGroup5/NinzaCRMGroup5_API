package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CRM_CreateUsersPage;
import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class Crm_CreateUserTest extends BaseTest {
    @DataProvider(name = "CreateUserData")
    public Object[][] getCreateUserData() {
        String excelPath = "src/main/java/testData/UserTestData.xlsx";
        String sheetName = "CreateUser";

        List<Map<String, String>> dataList = ExcelUtils.getTestData(excelPath, sheetName);

        Object[][] data = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);
        }
        return data;
    }

    @Test(dataProvider = "CreateUserData")
    public void CUserWithValidFields(Map<String, String> data) throws InterruptedException{
        logger.info("Create User with valid fields testcase 1 Started");
        CRM_CreateUsersPage createUserPage=hp.navigateToCreateUser();

        String userFullName = data.get("UserFullName1");
        String mobileNum = data.get("MobileNumber");
        String emailId = data.get("Email");
        String userName = data.get("UserName");
        String password = data.get("Password");
        String message = data.get("Message");

        createUserPage.enterUserFullName(userFullName);
        createUserPage.enterMobileNum(mobileNum);
        createUserPage.enterEmail(emailId);
        logger.info("Email entered successfully");
        createUserPage.enterUserName(userName);
        createUserPage.enterPassword(password);
        logger.info("Password entered successfully");
        createUserPage.clickCreateUserButton();
        logger.info("clicked on create user button successfully");
        //String actualMsg = createUserPage.verifyUserCreated();
        //System.out.println(actualMsg);

        Assert.assertTrue(createUserPage.verifyUserCreated(userName), "User creation failed!");
        System.out.println("====Successfull+=="+ message);
       // logger.info("Create User Successfull testcase1");
    }

}


package randomDataGenerator;

import com.github.javafaker.Faker;

import api_POJOS.Campaign_POJO;
import api_POJOS.CreateContactClassic_POJO;


public class CreateContact {
	
	private static Faker faker = new Faker();
	public static CreateContactClassic_POJO generateRandomContact() {
		CreateContactClassic_POJO contact = new CreateContactClassic_POJO();
		contact.setContactName(faker.name().fullName());
		contact.setDepartment(faker.company().profession());
		contact.setEmail(faker.internet().emailAddress());
		contact.setMobile(faker.phoneNumber().cellPhone());
		contact.setOfficePhone(faker.phoneNumber().phoneNumber());
		contact.setOrganizationName(faker.company().name());
		contact.setTitle(faker.job().title());
		
	     // Nested campaign
        Campaign_POJO campaign = new Campaign_POJO();
        campaign.setCampaignName(faker.company().name() + " Campaign");
        campaign.setCampaignStatus(faker.options().option("ongoing", "completed", "planned"));
        campaign.setTargetSize(faker.number().numberBetween(10, 500));
        contact.setCampaign(campaign);
	return contact;
		
	}
	public static CreateContactClassic_POJO generateMandtoryContact() {
	   
	    CreateContactClassic_POJO contact = new CreateContactClassic_POJO();
	    contact.setContactName(faker.name().fullName());
	    contact.setEmail(faker.internet().emailAddress());
	    contact.setMobile(faker.phoneNumber().cellPhone());
	    contact.setOrganizationName(faker.company().name());
	    contact.setTitle(faker.job().title());
	    return contact;
	}
	public static CreateContactClassic_POJO generateMissingFieldContact() {
		   
	    CreateContactClassic_POJO contact = new CreateContactClassic_POJO();
	   
	    contact.setEmail(faker.internet().emailAddress());
	    contact.setMobile(faker.phoneNumber().cellPhone());
	    contact.setDepartment(faker.company().profession());
	    contact.setOfficePhone(faker.phoneNumber().phoneNumber());
	    
	    return contact;
	}
	
	

}

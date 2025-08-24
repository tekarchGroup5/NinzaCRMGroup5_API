package randomDataGenerator;

import com.github.javafaker.Faker;

import api_POJOS.Campaign_POJO;
import api_POJOS.CreateLeadClassic_POJO;

public class CreateLeadRandomTestData {
	private static Faker faker = new Faker();

    public static CreateLeadClassic_POJO generateRandomLead() {
        CreateLeadClassic_POJO lead = new CreateLeadClassic_POJO();
       
        
 
        
        lead.setName(faker.name().fullName());
        lead.setCompany(faker.company().name());
        lead.setEmail(faker.internet().emailAddress());
        lead.setPhone(faker.phoneNumber().cellPhone());
        lead.setCity(faker.address().city());
        lead.setCountry(faker.address().country());
        lead.setDescription(faker.lorem().sentence());
        lead.setIndustry(faker.company().industry());
        lead.setNoOfEmployees(faker.number().numberBetween(10, 1000));
        lead.setWebsite(faker.internet().url());
        lead.setRating("Hot"); // or faker.options().option("Hot", "Warm", "Cold")
        lead.setSecondaryEmail(faker.internet().emailAddress());
        lead.setLeadSource(faker.options().option("Web", "Phone", "Email"));
        lead.setLeadStatus(faker.options().option("New", "Contacted", "Qualified"));
        lead.setAddress(faker.address().streetAddress());
        lead.setPostalCode(faker.number().numberBetween(10000, 99999));
        lead.setAnnualRevenue(faker.number().numberBetween(10000, 1000000));
        lead.setAssignedTo(faker.name().fullName());

        // Nested campaign
        Campaign_POJO campaign = new Campaign_POJO();
        campaign.setCampaignName(faker.company().name() + " Campaign");
        campaign.setCampaignStatus(faker.options().option("ongoing", "completed", "planned"));
        campaign.setTargetSize(faker.number().numberBetween(10, 500));
        lead.setCampaign(campaign);

        return lead;
    }
}


package randomDataGenerator;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.ZoneId;

import com.github.javafaker.Faker;

import api_POJOS.Campaign_POJO;
import api_POJOS.LeadPayload;
import api_POJOS.OpportunitiesPojo;

public class CreateOpportunityRandomTestData {

private static Faker faker = new Faker();

public static OpportunitiesPojo generateRandomOpportunity() {
	OpportunitiesPojo opp = new OpportunitiesPojo();
	
	opp.setAmount(String.valueOf(faker.number().numberBetween(10000,1000000)));
	opp.setAssignedTo(faker.name().fullName());
	opp.setBusinessType(faker.options().option("New Business","Existing Business","Partnership"));
	opp.setDescription(faker.lorem().sentence());
	String closeDate = faker.date().future(90, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ISO_DATE);
	opp.setExpectedCloseDate(closeDate);
    opp.setNextStep(faker.lorem().word());
    opp.setOpportunityName(faker.company().catchPhrase());
    opp.setProbability(String.valueOf(faker.number().numberBetween(10, 100)));
    opp.setSalesStage(faker.options().option("Prospecting", "Qualification", "Negotiation", "Closed Won", "Closed Lost"));

	return opp;
}
}

package randomDataGenerator;



import com.github.javafaker.Faker;

public class CreateUser {
	
	
	private static Faker faker = new Faker();

    public static String getEmpName() {
        return faker.name().fullName();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getMobileNumber() {
        return faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 10);
    }

    public static String getUsername() {
        return faker.name().username();
    }

    public static String getPassword() {
        return faker.internet().password(6, 20, true, true);
    }
    
    
    
    
    
}


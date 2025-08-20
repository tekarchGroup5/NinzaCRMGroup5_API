package api_POJOS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Generates default constructor
@AllArgsConstructor // Generates all-args constructor
@JsonIgnoreProperties(ignoreUnknown = true) // Ignores extra fields in JSON
public class CreateLead_POJO {

	private String leadId;       // if your API returns it
    private String name;
    private String company;
    private String email;
    private String phone;
    private String city;
    private String country;
    private String description;
    private String industry;
    private Integer noOfEmployees;  // changed to Integer
    private String website;
    private String rating;
    private String secondaryEmail;
    private String leadSource;
    private String leadStatus;
    private String address;
    private Integer postalCode;     // changed to Integer
    private Integer annualRevenue;  // changed to Integer
    private String assignedTo;
	public Object getLeadId() {
		// TODO Auto-generated method stub
		return null;
	}
}

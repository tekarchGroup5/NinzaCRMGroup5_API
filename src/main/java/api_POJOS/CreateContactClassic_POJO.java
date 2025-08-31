package api_POJOS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateContactClassic_POJO {

	private String contactName;
	private String department;
	private String email;
	private String mobile;
	private String officePhone;
	private String organizationName;
	private String title;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// Default constructor
	public CreateContactClassic_POJO() {
	}

	// Getters and Setters
	private String contactId;

	public String getContactId() {
		return contactId;
	}

	// Nested campaign object
	private Campaign_POJO campaign;

	public Campaign_POJO getCampaign() {
		return campaign;
	}
	
	public void setCampaign(Campaign_POJO campaign) {
	    this.campaign = campaign;
	}

}

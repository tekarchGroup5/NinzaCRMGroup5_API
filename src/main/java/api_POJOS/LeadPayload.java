package api_POJOS;

public class LeadPayload {
	 private String name;
	    private String company;
	    private String email;
	    private String phone;
	    private String city;
	    private String country;
	    private String description;
	    private String industry;
	    private int noOfEmployees;
	    private String website;
	    private String rating;
	    private String secondaryEmail;
	    private String leadSource;
	    private String leadStatus;
	    private String address;
	    private int postalCode;
	    private int annualRevenue;
	    private String assignedTo;
	    private Campaign campaign; // Nested campaign object

	    // Nested class for campaign
	    public static class Campaign {
	        private String campaignId;
	        private String campaignName;
	        private String campaignStatus;
	        private String description;
	        private String expectedCloseDate;
	        private String targetAudience;
	        private int targetSize;

	        // Getters and setters
	        public String getCampaignId() { return campaignId; }
	        public void setCampaignId(String campaignId) { this.campaignId = campaignId; }

	        public String getCampaignName() { return campaignName; }
	        public void setCampaignName(String campaignName) { this.campaignName = campaignName; }

	        public String getCampaignStatus() { return campaignStatus; }
	        public void setCampaignStatus(String campaignStatus) { this.campaignStatus = campaignStatus; }

	        public String getDescription() { return description; }
	        public void setDescription(String description) { this.description = description; }

	        public String getExpectedCloseDate() { return expectedCloseDate; }
	        public void setExpectedCloseDate(String expectedCloseDate) { this.expectedCloseDate = expectedCloseDate; }

	        public String getTargetAudience() { return targetAudience; }
	        public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }

	        public int getTargetSize() { return targetSize; }
	        public void setTargetSize(int targetSize) { this.targetSize = targetSize; }
	    }

	    // Getters and setters for LeadPayload
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getCompany() { return company; }
	    public void setCompany(String company) { this.company = company; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getPhone() { return phone; }
	    public void setPhone(String phone) { this.phone = phone; }

	    public String getCity() { return city; }
	    public void setCity(String city) { this.city = city; }

	    public String getCountry() { return country; }
	    public void setCountry(String country) { this.country = country; }

	    public String getDescription() { return description; }
	    public void setDescription(String description) { this.description = description; }

	    public String getIndustry() { return industry; }
	    public void setIndustry(String industry) { this.industry = industry; }

	    public int getNoOfEmployees() { return noOfEmployees; }
	    public void setNoOfEmployees(int noOfEmployees) { this.noOfEmployees = noOfEmployees; }

	    public String getWebsite() { return website; }
	    public void setWebsite(String website) { this.website = website; }

	    public String getRating() { return rating; }
	    public void setRating(String rating) { this.rating = rating; }

	    public String getSecondaryEmail() { return secondaryEmail; }
	    public void setSecondaryEmail(String secondaryEmail) { this.secondaryEmail = secondaryEmail; }

	    public String getLeadSource() { return leadSource; }
	    public void setLeadSource(String leadSource) { this.leadSource = leadSource; }

	    public String getLeadStatus() { return leadStatus; }
	    public void setLeadStatus(String leadStatus) { this.leadStatus = leadStatus; }

	    public String getAddress() { return address; }
	    public void setAddress(String address) { this.address = address; }

	    public int getPostalCode() { return postalCode; }
	    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

	    public int getAnnualRevenue() { return annualRevenue; }
	    public void setAnnualRevenue(int annualRevenue) { this.annualRevenue = annualRevenue; }

	    public String getAssignedTo() { return assignedTo; }
	    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

	    public Campaign getCampaign() { return campaign; }
	    public void setCampaign(Campaign campaign) { this.campaign = campaign; }
	}



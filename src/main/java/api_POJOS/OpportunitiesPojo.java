package api_POJOS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpportunitiesPojo {
	private String amount;
	private String assignedTo;
	private String businessType;
	private String description;
	private String expectedCloseDate;
	private LeadPayload lead;
	private String nextStep;
	private String opportunityId;
	private String opportunityName;
	private String probability;
	private String salesStage;
	private Campaign_POJO campaign;
	
	public OpportunitiesPojo(){
		
	}
	
	@JsonCreator
	public OpportunitiesPojo(@JsonProperty("amount")String amount,@JsonProperty("assignedTo") String assignedTo, @JsonProperty("businessType")String businessType,@JsonProperty("description") String description,
			@JsonProperty("expectedCloseDate")String expectedCloseDate,@JsonProperty("lead") LeadPayload lead,@JsonProperty("nextStep") String nextStep,@JsonProperty("opportunityId") String opportunityId,@JsonProperty("opportunityName") String opportunityName,
			@JsonProperty("probability")String probability, @JsonProperty("salesStage")String salesStage,@JsonProperty("campaign") Campaign_POJO campaign) {
		this.amount = amount;
		this.assignedTo = assignedTo;
		this.businessType = businessType;
		this.description = description;
		this.expectedCloseDate = expectedCloseDate;
		this.lead = lead;
		this.nextStep = nextStep;
		this.opportunityId = opportunityId;
		this.opportunityName = opportunityName;
		this.probability = probability;
		this.salesStage = salesStage;
		this.campaign = campaign;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpectedCloseDate() {
		return expectedCloseDate;
	}

	public void setExpectedCloseDate(String expectedCloseDate) {
		this.expectedCloseDate = expectedCloseDate;
	}

	public LeadPayload getLead() {
		return lead;
	}

	public void setLead(LeadPayload lead) {
		this.lead = lead;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public String getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}

	public String getOpportunityName() {
		return opportunityName;
	}

	public void setOpportunityName(String opportunityName) {
		this.opportunityName = opportunityName;
	}

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}

	public String getSalesStage() {
		return salesStage;
	}

	public void setSalesStage(String salesStage) {
		this.salesStage = salesStage;
	}

	public Campaign_POJO getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign_POJO campaign) {
		this.campaign = campaign;
	}

}

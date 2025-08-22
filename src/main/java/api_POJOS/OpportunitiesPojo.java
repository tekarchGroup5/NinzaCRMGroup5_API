package api_POJOS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

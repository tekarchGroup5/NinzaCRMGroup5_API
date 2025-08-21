package api_POJOS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Campaign_POJO {
	private String campaignId;
	private String campaignName;
	private String campaignStatus;
	private String description;
	private String expectedCloseDate;
	private String targetAudience;
	private int targetSize;

	// Default constructor
	public Campaign_POJO() {
	}

	// Getters and setters
	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(String campaignStatus) {
		this.campaignStatus = campaignStatus;
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

	public String getTargetAudience() {
		return targetAudience;
	}

	public void setTargetAudience(String targetAudience) {
		this.targetAudience = targetAudience;
	}

	public int getTargetSize() {
		return targetSize;
	}

	public void setTargetSize(int targetSize) {
		this.targetSize = targetSize;
	}
}

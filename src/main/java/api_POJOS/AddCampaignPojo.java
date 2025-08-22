package api_POJOS;    //for Bhakti

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCampaignPojo {
	
	private String campaignId;
	private String campaignName;
	private String campaignStatus;
	private String description;
	private String expectedCloseDate;
	private String targetAudience;
	private String targetSize;
		
	
	public AddCampaignPojo(String campaignId, String campaignName, String campaignStatus, String targetSize, String expectedCloseDate, String targetAudience, String description) {
		this.campaignId = campaignId;
		this.campaignName = campaignName;
		this.campaignStatus = campaignStatus;
		this.targetSize = targetSize;
		this.expectedCloseDate = expectedCloseDate;
		this.targetAudience = targetAudience;
		this.description = description;
		
	}
	
	public String getCampaignId() {
		return campaignId;
	}
	
	
	public String getCampaignName() {
		return campaignName;
	}
	
	
	public String getCampaignStatus() {
		return campaignStatus;
	}	


	public String gettargetSize() {
		return targetSize;
	}
	
	
	public String getExpectedCloseDate() {
		return expectedCloseDate;
	}	


	public String getTargetAudience() {
		return targetAudience;
	}
	
	
	public String getDescription() {
		return description;
	}
		

}

package api_POJOS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
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
}

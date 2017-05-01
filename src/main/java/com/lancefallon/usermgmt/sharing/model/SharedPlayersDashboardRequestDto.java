package com.lancefallon.usermgmt.sharing.model;

import java.util.Date;

public class SharedPlayersDashboardRequestDto {

	private Date fromDate;
	private Boolean hasViewed;
	private Integer waitTimeSeconds = 0;

	public SharedPlayersDashboardRequestDto() {
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Boolean getHasViewed() {
		return hasViewed;
	}

	public void setHasViewed(Boolean hasViewed) {
		this.hasViewed = hasViewed;
	}

	public Integer getWaitTimeSeconds() {
		return waitTimeSeconds;
	}

	public void setWaitTimeSeconds(Integer waitTimeSeconds) {
		this.waitTimeSeconds = waitTimeSeconds;
	}

}

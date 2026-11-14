package com.PowerDistributionApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePowerFeedDTO {

	private Integer ownerId;

	private Integer powerFeedId;

	private Integer powerFeedStatus;
	
	private Integer nextOrPrevious;
}


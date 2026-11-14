package com.PowerDistributionApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePowerDistributionUnitDTO {

	private Integer ownerId;

	private Integer powerDistributionUnitId;

	private Integer powerDistributionUnitStatus;
	
	private Integer nextOrPrevious;
}


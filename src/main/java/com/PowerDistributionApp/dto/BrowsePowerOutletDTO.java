package com.PowerDistributionApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePowerOutletDTO {

	private Integer ownerId;

	private Integer powerOutletId;

	private Integer powerOutletStatus;
	
	private Integer nextOrPrevious;
}


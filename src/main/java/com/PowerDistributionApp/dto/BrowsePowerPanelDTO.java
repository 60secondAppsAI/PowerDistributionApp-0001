package com.PowerDistributionApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePowerPanelDTO {

	private Integer ownerId;

	private Integer powerPanelId;

	private Integer powerPanelStatus;
	
	private Integer nextOrPrevious;
}


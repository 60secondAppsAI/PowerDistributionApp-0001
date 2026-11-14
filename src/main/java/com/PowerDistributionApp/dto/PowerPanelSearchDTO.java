package com.PowerDistributionApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PowerPanelSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer powerPanelId;
	
	private Integer pduId;
	
	private String name;
	
	private Double voltage;
	
	private Double maxAmps;
	
}

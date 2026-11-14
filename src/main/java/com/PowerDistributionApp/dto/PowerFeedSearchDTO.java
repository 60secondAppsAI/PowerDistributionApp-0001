package com.PowerDistributionApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PowerFeedSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer powerFeedId;
	
	private Integer pduId;
	
	private String feedName;
	
	private String feedType;
	
	private Double capacityKw;
	
}

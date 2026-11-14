package com.PowerDistributionApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PowerDistributionUnitSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer pduId;
	
	private String name;
	
	private String locationCode;
	
	private Double capacityKw;
	
	private String status;
	
	private Integer powerDistributionUnitId;
	
}

package com.PowerDistributionApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PowerDistributionUnitDTO {

	private Integer pduId;

	private String name;

	private String locationCode;

	private Double capacityKw;

	private String status;

	private Integer powerDistributionUnitId;






}

package com.PowerDistributionApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PowerPanelDTO {

	private Integer powerPanelId;

	private Integer pduId;

	private String name;

	private Double voltage;

	private Double maxAmps;






}

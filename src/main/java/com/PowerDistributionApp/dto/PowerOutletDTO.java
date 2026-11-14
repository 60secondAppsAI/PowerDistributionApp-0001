package com.PowerDistributionApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PowerOutletDTO {

	private Integer powerOutletId;

	private Integer powerPanelId;

	private String outletNumber;

	private String status;

	private Integer connectedAssetId;






}

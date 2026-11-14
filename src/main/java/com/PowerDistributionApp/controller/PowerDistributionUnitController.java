package com.PowerDistributionApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.PowerDistributionApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.PowerDistributionApp.domain.PowerDistributionUnit;
import com.PowerDistributionApp.dto.PowerDistributionUnitDTO;
import com.PowerDistributionApp.dto.PowerDistributionUnitSearchDTO;
import com.PowerDistributionApp.dto.PowerDistributionUnitPageDTO;
import com.PowerDistributionApp.service.PowerDistributionUnitService;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/powerDistributionUnit")
@RestController
public class PowerDistributionUnitController {

	private final static Logger logger = LoggerFactory.getLogger(PowerDistributionUnitController.class);

	@Autowired
	PowerDistributionUnitService powerDistributionUnitService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PowerDistributionUnit> getAll() {

		List<PowerDistributionUnit> powerDistributionUnits = powerDistributionUnitService.findAll();
		
		return powerDistributionUnits;	
	}

	@GetMapping(value = "/{powerDistributionUnitId}")
	@ResponseBody
	public PowerDistributionUnitDTO getPowerDistributionUnit(@PathVariable Integer powerDistributionUnitId) {
		
		return (powerDistributionUnitService.getPowerDistributionUnitDTOById(powerDistributionUnitId));
	}

 	@RequestMapping(value = "/addPowerDistributionUnit", method = RequestMethod.POST)
	public ResponseEntity<?> addPowerDistributionUnit(@RequestBody PowerDistributionUnitDTO powerDistributionUnitDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerDistributionUnitService.addPowerDistributionUnit(powerDistributionUnitDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/powerDistributionUnits")
	public ResponseEntity<PowerDistributionUnitPageDTO> getPowerDistributionUnits(PowerDistributionUnitSearchDTO powerDistributionUnitSearchDTO) {
 
		return powerDistributionUnitService.getPowerDistributionUnits(powerDistributionUnitSearchDTO);
	}	

	@RequestMapping(value = "/updatePowerDistributionUnit", method = RequestMethod.POST)
	public ResponseEntity<?> updatePowerDistributionUnit(@RequestBody PowerDistributionUnitDTO powerDistributionUnitDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerDistributionUnitService.updatePowerDistributionUnit(powerDistributionUnitDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}

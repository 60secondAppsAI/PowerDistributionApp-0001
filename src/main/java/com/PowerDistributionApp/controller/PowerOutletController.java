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

import com.PowerDistributionApp.domain.PowerOutlet;
import com.PowerDistributionApp.dto.PowerOutletDTO;
import com.PowerDistributionApp.dto.PowerOutletSearchDTO;
import com.PowerDistributionApp.dto.PowerOutletPageDTO;
import com.PowerDistributionApp.service.PowerOutletService;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/powerOutlet")
@RestController
public class PowerOutletController {

	private final static Logger logger = LoggerFactory.getLogger(PowerOutletController.class);

	@Autowired
	PowerOutletService powerOutletService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PowerOutlet> getAll() {

		List<PowerOutlet> powerOutlets = powerOutletService.findAll();
		
		return powerOutlets;	
	}

	@GetMapping(value = "/{powerOutletId}")
	@ResponseBody
	public PowerOutletDTO getPowerOutlet(@PathVariable Integer powerOutletId) {
		
		return (powerOutletService.getPowerOutletDTOById(powerOutletId));
	}

 	@RequestMapping(value = "/addPowerOutlet", method = RequestMethod.POST)
	public ResponseEntity<?> addPowerOutlet(@RequestBody PowerOutletDTO powerOutletDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerOutletService.addPowerOutlet(powerOutletDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/powerOutlets")
	public ResponseEntity<PowerOutletPageDTO> getPowerOutlets(PowerOutletSearchDTO powerOutletSearchDTO) {
 
		return powerOutletService.getPowerOutlets(powerOutletSearchDTO);
	}	

	@RequestMapping(value = "/updatePowerOutlet", method = RequestMethod.POST)
	public ResponseEntity<?> updatePowerOutlet(@RequestBody PowerOutletDTO powerOutletDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerOutletService.updatePowerOutlet(powerOutletDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}

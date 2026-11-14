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

import com.PowerDistributionApp.domain.PowerFeed;
import com.PowerDistributionApp.dto.PowerFeedDTO;
import com.PowerDistributionApp.dto.PowerFeedSearchDTO;
import com.PowerDistributionApp.dto.PowerFeedPageDTO;
import com.PowerDistributionApp.service.PowerFeedService;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/powerFeed")
@RestController
public class PowerFeedController {

	private final static Logger logger = LoggerFactory.getLogger(PowerFeedController.class);

	@Autowired
	PowerFeedService powerFeedService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PowerFeed> getAll() {

		List<PowerFeed> powerFeeds = powerFeedService.findAll();
		
		return powerFeeds;	
	}

	@GetMapping(value = "/{powerFeedId}")
	@ResponseBody
	public PowerFeedDTO getPowerFeed(@PathVariable Integer powerFeedId) {
		
		return (powerFeedService.getPowerFeedDTOById(powerFeedId));
	}

 	@RequestMapping(value = "/addPowerFeed", method = RequestMethod.POST)
	public ResponseEntity<?> addPowerFeed(@RequestBody PowerFeedDTO powerFeedDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerFeedService.addPowerFeed(powerFeedDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/powerFeeds")
	public ResponseEntity<PowerFeedPageDTO> getPowerFeeds(PowerFeedSearchDTO powerFeedSearchDTO) {
 
		return powerFeedService.getPowerFeeds(powerFeedSearchDTO);
	}	

	@RequestMapping(value = "/updatePowerFeed", method = RequestMethod.POST)
	public ResponseEntity<?> updatePowerFeed(@RequestBody PowerFeedDTO powerFeedDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerFeedService.updatePowerFeed(powerFeedDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}

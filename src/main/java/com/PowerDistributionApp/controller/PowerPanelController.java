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

import com.PowerDistributionApp.domain.PowerPanel;
import com.PowerDistributionApp.dto.PowerPanelDTO;
import com.PowerDistributionApp.dto.PowerPanelSearchDTO;
import com.PowerDistributionApp.dto.PowerPanelPageDTO;
import com.PowerDistributionApp.service.PowerPanelService;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/powerPanel")
@RestController
public class PowerPanelController {

	private final static Logger logger = LoggerFactory.getLogger(PowerPanelController.class);

	@Autowired
	PowerPanelService powerPanelService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PowerPanel> getAll() {

		List<PowerPanel> powerPanels = powerPanelService.findAll();
		
		return powerPanels;	
	}

	@GetMapping(value = "/{powerPanelId}")
	@ResponseBody
	public PowerPanelDTO getPowerPanel(@PathVariable Integer powerPanelId) {
		
		return (powerPanelService.getPowerPanelDTOById(powerPanelId));
	}

 	@RequestMapping(value = "/addPowerPanel", method = RequestMethod.POST)
	public ResponseEntity<?> addPowerPanel(@RequestBody PowerPanelDTO powerPanelDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerPanelService.addPowerPanel(powerPanelDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/powerPanels")
	public ResponseEntity<PowerPanelPageDTO> getPowerPanels(PowerPanelSearchDTO powerPanelSearchDTO) {
 
		return powerPanelService.getPowerPanels(powerPanelSearchDTO);
	}	

	@RequestMapping(value = "/updatePowerPanel", method = RequestMethod.POST)
	public ResponseEntity<?> updatePowerPanel(@RequestBody PowerPanelDTO powerPanelDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerPanelService.updatePowerPanel(powerPanelDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}

package com.PowerDistributionApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.PowerDistributionApp.dao.GenericDAO;
import com.PowerDistributionApp.service.GenericService;
import com.PowerDistributionApp.service.impl.GenericServiceImpl;
import com.PowerDistributionApp.dao.PowerOutletDAO;
import com.PowerDistributionApp.domain.PowerOutlet;
import com.PowerDistributionApp.dto.PowerOutletDTO;
import com.PowerDistributionApp.dto.PowerOutletSearchDTO;
import com.PowerDistributionApp.dto.PowerOutletPageDTO;
import com.PowerDistributionApp.dto.PowerOutletConvertCriteriaDTO;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;
import com.PowerDistributionApp.service.PowerOutletService;
import com.PowerDistributionApp.util.ControllerUtils;


@Service
public class PowerOutletServiceImpl extends GenericServiceImpl<PowerOutlet, Integer> implements PowerOutletService {

    private final static Logger logger = LoggerFactory.getLogger(PowerOutletServiceImpl.class);

	@Autowired
	PowerOutletDAO powerOutletDao;

	

	@Override
	public GenericDAO<PowerOutlet, Integer> getDAO() {
		return (GenericDAO<PowerOutlet, Integer>) powerOutletDao;
	}
	
	public List<PowerOutlet> findAll () {
		List<PowerOutlet> powerOutlets = powerOutletDao.findAll();
		
		return powerOutlets;	
		
	}

	public ResultDTO addPowerOutlet(PowerOutletDTO powerOutletDTO, RequestDTO requestDTO) {

		PowerOutlet powerOutlet = new PowerOutlet();

		powerOutlet.setPowerOutletId(powerOutletDTO.getPowerOutletId());

		powerOutlet.setPowerPanelId(powerOutletDTO.getPowerPanelId());

		powerOutlet.setOutletNumber(powerOutletDTO.getOutletNumber());

		powerOutlet.setStatus(powerOutletDTO.getStatus());

		powerOutlet.setConnectedAssetId(powerOutletDTO.getConnectedAssetId());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		powerOutlet = powerOutletDao.save(powerOutlet);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PowerOutlet> getAllPowerOutlets(Pageable pageable) {
		return powerOutletDao.findAll(pageable);
	}

	public Page<PowerOutlet> getAllPowerOutlets(Specification<PowerOutlet> spec, Pageable pageable) {
		return powerOutletDao.findAll(spec, pageable);
	}

	public ResponseEntity<PowerOutletPageDTO> getPowerOutlets(PowerOutletSearchDTO powerOutletSearchDTO) {
	
			Integer powerOutletId = powerOutletSearchDTO.getPowerOutletId(); 
 			Integer powerPanelId = powerOutletSearchDTO.getPowerPanelId(); 
 			String outletNumber = powerOutletSearchDTO.getOutletNumber(); 
 			String status = powerOutletSearchDTO.getStatus(); 
 			Integer connectedAssetId = powerOutletSearchDTO.getConnectedAssetId(); 
 			String sortBy = powerOutletSearchDTO.getSortBy();
			String sortOrder = powerOutletSearchDTO.getSortOrder();
			String searchQuery = powerOutletSearchDTO.getSearchQuery();
			Integer page = powerOutletSearchDTO.getPage();
			Integer size = powerOutletSearchDTO.getSize();

	        Specification<PowerOutlet> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, powerOutletId, "powerOutletId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, powerPanelId, "powerPanelId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, outletNumber, "outletNumber"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			
			spec = ControllerUtils.andIfNecessary(spec, connectedAssetId, "connectedAssetId"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("outletNumber")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<PowerOutlet> powerOutlets = this.getAllPowerOutlets(spec, pageable);
		
		//System.out.println(String.valueOf(powerOutlets.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(powerOutlets.getTotalPages()));
		
		List<PowerOutlet> powerOutletsList = powerOutlets.getContent();
		
		PowerOutletConvertCriteriaDTO convertCriteria = new PowerOutletConvertCriteriaDTO();
		List<PowerOutletDTO> powerOutletDTOs = this.convertPowerOutletsToPowerOutletDTOs(powerOutletsList,convertCriteria);
		
		PowerOutletPageDTO powerOutletPageDTO = new PowerOutletPageDTO();
		powerOutletPageDTO.setPowerOutlets(powerOutletDTOs);
		powerOutletPageDTO.setTotalElements(powerOutlets.getTotalElements());
		return ResponseEntity.ok(powerOutletPageDTO);
	}

	public List<PowerOutletDTO> convertPowerOutletsToPowerOutletDTOs(List<PowerOutlet> powerOutlets, PowerOutletConvertCriteriaDTO convertCriteria) {
		
		List<PowerOutletDTO> powerOutletDTOs = new ArrayList<PowerOutletDTO>();
		
		for (PowerOutlet powerOutlet : powerOutlets) {
			powerOutletDTOs.add(convertPowerOutletToPowerOutletDTO(powerOutlet,convertCriteria));
		}
		
		return powerOutletDTOs;

	}
	
	public PowerOutletDTO convertPowerOutletToPowerOutletDTO(PowerOutlet powerOutlet, PowerOutletConvertCriteriaDTO convertCriteria) {
		
		PowerOutletDTO powerOutletDTO = new PowerOutletDTO();

		powerOutletDTO.setPowerOutletId(powerOutlet.getPowerOutletId());

		powerOutletDTO.setPowerPanelId(powerOutlet.getPowerPanelId());

		powerOutletDTO.setOutletNumber(powerOutlet.getOutletNumber());

		powerOutletDTO.setStatus(powerOutlet.getStatus());

		powerOutletDTO.setConnectedAssetId(powerOutlet.getConnectedAssetId());
		
		return powerOutletDTO;
	}

	public ResultDTO updatePowerOutlet(PowerOutletDTO powerOutletDTO, RequestDTO requestDTO) {
		
		PowerOutlet powerOutlet = powerOutletDao.getById(powerOutletDTO.getPowerOutletId());
		
		powerOutlet.setPowerOutletId(ControllerUtils.setValue(powerOutlet.getPowerOutletId(), powerOutletDTO.getPowerOutletId()));
		
		powerOutlet.setPowerPanelId(ControllerUtils.setValue(powerOutlet.getPowerPanelId(), powerOutletDTO.getPowerPanelId()));
		
		powerOutlet.setOutletNumber(ControllerUtils.setValue(powerOutlet.getOutletNumber(), powerOutletDTO.getOutletNumber()));
		
		powerOutlet.setStatus(ControllerUtils.setValue(powerOutlet.getStatus(), powerOutletDTO.getStatus()));
		
		powerOutlet.setConnectedAssetId(ControllerUtils.setValue(powerOutlet.getConnectedAssetId(), powerOutletDTO.getConnectedAssetId()));

        powerOutlet = powerOutletDao.save(powerOutlet);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PowerOutletDTO getPowerOutletDTOById(Integer powerOutletId) {
	
		PowerOutlet powerOutlet = powerOutletDao.getById(powerOutletId);
		
		PowerOutletConvertCriteriaDTO convertCriteria = new PowerOutletConvertCriteriaDTO();
		return(this.convertPowerOutletToPowerOutletDTO(powerOutlet,convertCriteria));
	}

}

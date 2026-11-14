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
import com.PowerDistributionApp.dao.PowerDistributionUnitDAO;
import com.PowerDistributionApp.domain.PowerDistributionUnit;
import com.PowerDistributionApp.dto.PowerDistributionUnitDTO;
import com.PowerDistributionApp.dto.PowerDistributionUnitSearchDTO;
import com.PowerDistributionApp.dto.PowerDistributionUnitPageDTO;
import com.PowerDistributionApp.dto.PowerDistributionUnitConvertCriteriaDTO;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;
import com.PowerDistributionApp.service.PowerDistributionUnitService;
import com.PowerDistributionApp.util.ControllerUtils;


@Service
public class PowerDistributionUnitServiceImpl extends GenericServiceImpl<PowerDistributionUnit, Integer> implements PowerDistributionUnitService {

    private final static Logger logger = LoggerFactory.getLogger(PowerDistributionUnitServiceImpl.class);

	@Autowired
	PowerDistributionUnitDAO powerDistributionUnitDao;

	

	@Override
	public GenericDAO<PowerDistributionUnit, Integer> getDAO() {
		return (GenericDAO<PowerDistributionUnit, Integer>) powerDistributionUnitDao;
	}
	
	public List<PowerDistributionUnit> findAll () {
		List<PowerDistributionUnit> powerDistributionUnits = powerDistributionUnitDao.findAll();
		
		return powerDistributionUnits;	
		
	}

	public ResultDTO addPowerDistributionUnit(PowerDistributionUnitDTO powerDistributionUnitDTO, RequestDTO requestDTO) {

		PowerDistributionUnit powerDistributionUnit = new PowerDistributionUnit();

		powerDistributionUnit.setPduId(powerDistributionUnitDTO.getPduId());

		powerDistributionUnit.setName(powerDistributionUnitDTO.getName());

		powerDistributionUnit.setLocationCode(powerDistributionUnitDTO.getLocationCode());

		powerDistributionUnit.setCapacityKw(powerDistributionUnitDTO.getCapacityKw());

		powerDistributionUnit.setStatus(powerDistributionUnitDTO.getStatus());

		powerDistributionUnit.setPowerDistributionUnitId(powerDistributionUnitDTO.getPowerDistributionUnitId());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		powerDistributionUnit = powerDistributionUnitDao.save(powerDistributionUnit);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PowerDistributionUnit> getAllPowerDistributionUnits(Pageable pageable) {
		return powerDistributionUnitDao.findAll(pageable);
	}

	public Page<PowerDistributionUnit> getAllPowerDistributionUnits(Specification<PowerDistributionUnit> spec, Pageable pageable) {
		return powerDistributionUnitDao.findAll(spec, pageable);
	}

	public ResponseEntity<PowerDistributionUnitPageDTO> getPowerDistributionUnits(PowerDistributionUnitSearchDTO powerDistributionUnitSearchDTO) {
	
			Integer pduId = powerDistributionUnitSearchDTO.getPduId(); 
 			String name = powerDistributionUnitSearchDTO.getName(); 
 			String locationCode = powerDistributionUnitSearchDTO.getLocationCode(); 
  			String status = powerDistributionUnitSearchDTO.getStatus(); 
 			Integer powerDistributionUnitId = powerDistributionUnitSearchDTO.getPowerDistributionUnitId(); 
 			String sortBy = powerDistributionUnitSearchDTO.getSortBy();
			String sortOrder = powerDistributionUnitSearchDTO.getSortOrder();
			String searchQuery = powerDistributionUnitSearchDTO.getSearchQuery();
			Integer page = powerDistributionUnitSearchDTO.getPage();
			Integer size = powerDistributionUnitSearchDTO.getSize();

	        Specification<PowerDistributionUnit> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, pduId, "pduId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, locationCode, "locationCode"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			
			spec = ControllerUtils.andIfNecessary(spec, powerDistributionUnitId, "powerDistributionUnitId"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("locationCode")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<PowerDistributionUnit> powerDistributionUnits = this.getAllPowerDistributionUnits(spec, pageable);
		
		//System.out.println(String.valueOf(powerDistributionUnits.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(powerDistributionUnits.getTotalPages()));
		
		List<PowerDistributionUnit> powerDistributionUnitsList = powerDistributionUnits.getContent();
		
		PowerDistributionUnitConvertCriteriaDTO convertCriteria = new PowerDistributionUnitConvertCriteriaDTO();
		List<PowerDistributionUnitDTO> powerDistributionUnitDTOs = this.convertPowerDistributionUnitsToPowerDistributionUnitDTOs(powerDistributionUnitsList,convertCriteria);
		
		PowerDistributionUnitPageDTO powerDistributionUnitPageDTO = new PowerDistributionUnitPageDTO();
		powerDistributionUnitPageDTO.setPowerDistributionUnits(powerDistributionUnitDTOs);
		powerDistributionUnitPageDTO.setTotalElements(powerDistributionUnits.getTotalElements());
		return ResponseEntity.ok(powerDistributionUnitPageDTO);
	}

	public List<PowerDistributionUnitDTO> convertPowerDistributionUnitsToPowerDistributionUnitDTOs(List<PowerDistributionUnit> powerDistributionUnits, PowerDistributionUnitConvertCriteriaDTO convertCriteria) {
		
		List<PowerDistributionUnitDTO> powerDistributionUnitDTOs = new ArrayList<PowerDistributionUnitDTO>();
		
		for (PowerDistributionUnit powerDistributionUnit : powerDistributionUnits) {
			powerDistributionUnitDTOs.add(convertPowerDistributionUnitToPowerDistributionUnitDTO(powerDistributionUnit,convertCriteria));
		}
		
		return powerDistributionUnitDTOs;

	}
	
	public PowerDistributionUnitDTO convertPowerDistributionUnitToPowerDistributionUnitDTO(PowerDistributionUnit powerDistributionUnit, PowerDistributionUnitConvertCriteriaDTO convertCriteria) {
		
		PowerDistributionUnitDTO powerDistributionUnitDTO = new PowerDistributionUnitDTO();

		powerDistributionUnitDTO.setPduId(powerDistributionUnit.getPduId());

		powerDistributionUnitDTO.setName(powerDistributionUnit.getName());

		powerDistributionUnitDTO.setLocationCode(powerDistributionUnit.getLocationCode());

		powerDistributionUnitDTO.setCapacityKw(powerDistributionUnit.getCapacityKw());

		powerDistributionUnitDTO.setStatus(powerDistributionUnit.getStatus());

		powerDistributionUnitDTO.setPowerDistributionUnitId(powerDistributionUnit.getPowerDistributionUnitId());
		
		return powerDistributionUnitDTO;
	}

	public ResultDTO updatePowerDistributionUnit(PowerDistributionUnitDTO powerDistributionUnitDTO, RequestDTO requestDTO) {
		
		PowerDistributionUnit powerDistributionUnit = powerDistributionUnitDao.getById(powerDistributionUnitDTO.getPowerDistributionUnitId());
		
		powerDistributionUnit.setPduId(ControllerUtils.setValue(powerDistributionUnit.getPduId(), powerDistributionUnitDTO.getPduId()));
		
		powerDistributionUnit.setName(ControllerUtils.setValue(powerDistributionUnit.getName(), powerDistributionUnitDTO.getName()));
		
		powerDistributionUnit.setLocationCode(ControllerUtils.setValue(powerDistributionUnit.getLocationCode(), powerDistributionUnitDTO.getLocationCode()));
		
		powerDistributionUnit.setCapacityKw(ControllerUtils.setValue(powerDistributionUnit.getCapacityKw(), powerDistributionUnitDTO.getCapacityKw()));
		
		powerDistributionUnit.setStatus(ControllerUtils.setValue(powerDistributionUnit.getStatus(), powerDistributionUnitDTO.getStatus()));
		
		powerDistributionUnit.setPowerDistributionUnitId(ControllerUtils.setValue(powerDistributionUnit.getPowerDistributionUnitId(), powerDistributionUnitDTO.getPowerDistributionUnitId()));

        powerDistributionUnit = powerDistributionUnitDao.save(powerDistributionUnit);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PowerDistributionUnitDTO getPowerDistributionUnitDTOById(Integer powerDistributionUnitId) {
	
		PowerDistributionUnit powerDistributionUnit = powerDistributionUnitDao.getById(powerDistributionUnitId);
		
		PowerDistributionUnitConvertCriteriaDTO convertCriteria = new PowerDistributionUnitConvertCriteriaDTO();
		return(this.convertPowerDistributionUnitToPowerDistributionUnitDTO(powerDistributionUnit,convertCriteria));
	}

}

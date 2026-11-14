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
import com.PowerDistributionApp.dao.PowerFeedDAO;
import com.PowerDistributionApp.domain.PowerFeed;
import com.PowerDistributionApp.dto.PowerFeedDTO;
import com.PowerDistributionApp.dto.PowerFeedSearchDTO;
import com.PowerDistributionApp.dto.PowerFeedPageDTO;
import com.PowerDistributionApp.dto.PowerFeedConvertCriteriaDTO;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;
import com.PowerDistributionApp.service.PowerFeedService;
import com.PowerDistributionApp.util.ControllerUtils;


@Service
public class PowerFeedServiceImpl extends GenericServiceImpl<PowerFeed, Integer> implements PowerFeedService {

    private final static Logger logger = LoggerFactory.getLogger(PowerFeedServiceImpl.class);

	@Autowired
	PowerFeedDAO powerFeedDao;

	

	@Override
	public GenericDAO<PowerFeed, Integer> getDAO() {
		return (GenericDAO<PowerFeed, Integer>) powerFeedDao;
	}
	
	public List<PowerFeed> findAll () {
		List<PowerFeed> powerFeeds = powerFeedDao.findAll();
		
		return powerFeeds;	
		
	}

	public ResultDTO addPowerFeed(PowerFeedDTO powerFeedDTO, RequestDTO requestDTO) {

		PowerFeed powerFeed = new PowerFeed();

		powerFeed.setPowerFeedId(powerFeedDTO.getPowerFeedId());

		powerFeed.setPduId(powerFeedDTO.getPduId());

		powerFeed.setFeedName(powerFeedDTO.getFeedName());

		powerFeed.setFeedType(powerFeedDTO.getFeedType());

		powerFeed.setCapacityKw(powerFeedDTO.getCapacityKw());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		powerFeed = powerFeedDao.save(powerFeed);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PowerFeed> getAllPowerFeeds(Pageable pageable) {
		return powerFeedDao.findAll(pageable);
	}

	public Page<PowerFeed> getAllPowerFeeds(Specification<PowerFeed> spec, Pageable pageable) {
		return powerFeedDao.findAll(spec, pageable);
	}

	public ResponseEntity<PowerFeedPageDTO> getPowerFeeds(PowerFeedSearchDTO powerFeedSearchDTO) {
	
			Integer powerFeedId = powerFeedSearchDTO.getPowerFeedId(); 
 			Integer pduId = powerFeedSearchDTO.getPduId(); 
 			String feedName = powerFeedSearchDTO.getFeedName(); 
 			String feedType = powerFeedSearchDTO.getFeedType(); 
  			String sortBy = powerFeedSearchDTO.getSortBy();
			String sortOrder = powerFeedSearchDTO.getSortOrder();
			String searchQuery = powerFeedSearchDTO.getSearchQuery();
			Integer page = powerFeedSearchDTO.getPage();
			Integer size = powerFeedSearchDTO.getSize();

	        Specification<PowerFeed> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, powerFeedId, "powerFeedId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, pduId, "pduId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, feedName, "feedName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, feedType, "feedType"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("feedName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("feedType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<PowerFeed> powerFeeds = this.getAllPowerFeeds(spec, pageable);
		
		//System.out.println(String.valueOf(powerFeeds.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(powerFeeds.getTotalPages()));
		
		List<PowerFeed> powerFeedsList = powerFeeds.getContent();
		
		PowerFeedConvertCriteriaDTO convertCriteria = new PowerFeedConvertCriteriaDTO();
		List<PowerFeedDTO> powerFeedDTOs = this.convertPowerFeedsToPowerFeedDTOs(powerFeedsList,convertCriteria);
		
		PowerFeedPageDTO powerFeedPageDTO = new PowerFeedPageDTO();
		powerFeedPageDTO.setPowerFeeds(powerFeedDTOs);
		powerFeedPageDTO.setTotalElements(powerFeeds.getTotalElements());
		return ResponseEntity.ok(powerFeedPageDTO);
	}

	public List<PowerFeedDTO> convertPowerFeedsToPowerFeedDTOs(List<PowerFeed> powerFeeds, PowerFeedConvertCriteriaDTO convertCriteria) {
		
		List<PowerFeedDTO> powerFeedDTOs = new ArrayList<PowerFeedDTO>();
		
		for (PowerFeed powerFeed : powerFeeds) {
			powerFeedDTOs.add(convertPowerFeedToPowerFeedDTO(powerFeed,convertCriteria));
		}
		
		return powerFeedDTOs;

	}
	
	public PowerFeedDTO convertPowerFeedToPowerFeedDTO(PowerFeed powerFeed, PowerFeedConvertCriteriaDTO convertCriteria) {
		
		PowerFeedDTO powerFeedDTO = new PowerFeedDTO();

		powerFeedDTO.setPowerFeedId(powerFeed.getPowerFeedId());

		powerFeedDTO.setPduId(powerFeed.getPduId());

		powerFeedDTO.setFeedName(powerFeed.getFeedName());

		powerFeedDTO.setFeedType(powerFeed.getFeedType());

		powerFeedDTO.setCapacityKw(powerFeed.getCapacityKw());
		
		return powerFeedDTO;
	}

	public ResultDTO updatePowerFeed(PowerFeedDTO powerFeedDTO, RequestDTO requestDTO) {
		
		PowerFeed powerFeed = powerFeedDao.getById(powerFeedDTO.getPowerFeedId());
		
		powerFeed.setPowerFeedId(ControllerUtils.setValue(powerFeed.getPowerFeedId(), powerFeedDTO.getPowerFeedId()));
		
		powerFeed.setPduId(ControllerUtils.setValue(powerFeed.getPduId(), powerFeedDTO.getPduId()));
		
		powerFeed.setFeedName(ControllerUtils.setValue(powerFeed.getFeedName(), powerFeedDTO.getFeedName()));
		
		powerFeed.setFeedType(ControllerUtils.setValue(powerFeed.getFeedType(), powerFeedDTO.getFeedType()));
		
		powerFeed.setCapacityKw(ControllerUtils.setValue(powerFeed.getCapacityKw(), powerFeedDTO.getCapacityKw()));

        powerFeed = powerFeedDao.save(powerFeed);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PowerFeedDTO getPowerFeedDTOById(Integer powerFeedId) {
	
		PowerFeed powerFeed = powerFeedDao.getById(powerFeedId);
		
		PowerFeedConvertCriteriaDTO convertCriteria = new PowerFeedConvertCriteriaDTO();
		return(this.convertPowerFeedToPowerFeedDTO(powerFeed,convertCriteria));
	}

}

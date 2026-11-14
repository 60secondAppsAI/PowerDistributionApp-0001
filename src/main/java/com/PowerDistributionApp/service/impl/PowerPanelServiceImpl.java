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
import com.PowerDistributionApp.dao.PowerPanelDAO;
import com.PowerDistributionApp.domain.PowerPanel;
import com.PowerDistributionApp.dto.PowerPanelDTO;
import com.PowerDistributionApp.dto.PowerPanelSearchDTO;
import com.PowerDistributionApp.dto.PowerPanelPageDTO;
import com.PowerDistributionApp.dto.PowerPanelConvertCriteriaDTO;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;
import com.PowerDistributionApp.service.PowerPanelService;
import com.PowerDistributionApp.util.ControllerUtils;


@Service
public class PowerPanelServiceImpl extends GenericServiceImpl<PowerPanel, Integer> implements PowerPanelService {

    private final static Logger logger = LoggerFactory.getLogger(PowerPanelServiceImpl.class);

	@Autowired
	PowerPanelDAO powerPanelDao;

	

	@Override
	public GenericDAO<PowerPanel, Integer> getDAO() {
		return (GenericDAO<PowerPanel, Integer>) powerPanelDao;
	}
	
	public List<PowerPanel> findAll () {
		List<PowerPanel> powerPanels = powerPanelDao.findAll();
		
		return powerPanels;	
		
	}

	public ResultDTO addPowerPanel(PowerPanelDTO powerPanelDTO, RequestDTO requestDTO) {

		PowerPanel powerPanel = new PowerPanel();

		powerPanel.setPowerPanelId(powerPanelDTO.getPowerPanelId());

		powerPanel.setPduId(powerPanelDTO.getPduId());

		powerPanel.setName(powerPanelDTO.getName());

		powerPanel.setVoltage(powerPanelDTO.getVoltage());

		powerPanel.setMaxAmps(powerPanelDTO.getMaxAmps());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		powerPanel = powerPanelDao.save(powerPanel);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PowerPanel> getAllPowerPanels(Pageable pageable) {
		return powerPanelDao.findAll(pageable);
	}

	public Page<PowerPanel> getAllPowerPanels(Specification<PowerPanel> spec, Pageable pageable) {
		return powerPanelDao.findAll(spec, pageable);
	}

	public ResponseEntity<PowerPanelPageDTO> getPowerPanels(PowerPanelSearchDTO powerPanelSearchDTO) {
	
			Integer powerPanelId = powerPanelSearchDTO.getPowerPanelId(); 
 			Integer pduId = powerPanelSearchDTO.getPduId(); 
 			String name = powerPanelSearchDTO.getName(); 
   			String sortBy = powerPanelSearchDTO.getSortBy();
			String sortOrder = powerPanelSearchDTO.getSortOrder();
			String searchQuery = powerPanelSearchDTO.getSearchQuery();
			Integer page = powerPanelSearchDTO.getPage();
			Integer size = powerPanelSearchDTO.getSize();

	        Specification<PowerPanel> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, powerPanelId, "powerPanelId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, pduId, "pduId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<PowerPanel> powerPanels = this.getAllPowerPanels(spec, pageable);
		
		//System.out.println(String.valueOf(powerPanels.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(powerPanels.getTotalPages()));
		
		List<PowerPanel> powerPanelsList = powerPanels.getContent();
		
		PowerPanelConvertCriteriaDTO convertCriteria = new PowerPanelConvertCriteriaDTO();
		List<PowerPanelDTO> powerPanelDTOs = this.convertPowerPanelsToPowerPanelDTOs(powerPanelsList,convertCriteria);
		
		PowerPanelPageDTO powerPanelPageDTO = new PowerPanelPageDTO();
		powerPanelPageDTO.setPowerPanels(powerPanelDTOs);
		powerPanelPageDTO.setTotalElements(powerPanels.getTotalElements());
		return ResponseEntity.ok(powerPanelPageDTO);
	}

	public List<PowerPanelDTO> convertPowerPanelsToPowerPanelDTOs(List<PowerPanel> powerPanels, PowerPanelConvertCriteriaDTO convertCriteria) {
		
		List<PowerPanelDTO> powerPanelDTOs = new ArrayList<PowerPanelDTO>();
		
		for (PowerPanel powerPanel : powerPanels) {
			powerPanelDTOs.add(convertPowerPanelToPowerPanelDTO(powerPanel,convertCriteria));
		}
		
		return powerPanelDTOs;

	}
	
	public PowerPanelDTO convertPowerPanelToPowerPanelDTO(PowerPanel powerPanel, PowerPanelConvertCriteriaDTO convertCriteria) {
		
		PowerPanelDTO powerPanelDTO = new PowerPanelDTO();

		powerPanelDTO.setPowerPanelId(powerPanel.getPowerPanelId());

		powerPanelDTO.setPduId(powerPanel.getPduId());

		powerPanelDTO.setName(powerPanel.getName());

		powerPanelDTO.setVoltage(powerPanel.getVoltage());

		powerPanelDTO.setMaxAmps(powerPanel.getMaxAmps());
		
		return powerPanelDTO;
	}

	public ResultDTO updatePowerPanel(PowerPanelDTO powerPanelDTO, RequestDTO requestDTO) {
		
		PowerPanel powerPanel = powerPanelDao.getById(powerPanelDTO.getPowerPanelId());
		
		powerPanel.setPowerPanelId(ControllerUtils.setValue(powerPanel.getPowerPanelId(), powerPanelDTO.getPowerPanelId()));
		
		powerPanel.setPduId(ControllerUtils.setValue(powerPanel.getPduId(), powerPanelDTO.getPduId()));
		
		powerPanel.setName(ControllerUtils.setValue(powerPanel.getName(), powerPanelDTO.getName()));
		
		powerPanel.setVoltage(ControllerUtils.setValue(powerPanel.getVoltage(), powerPanelDTO.getVoltage()));
		
		powerPanel.setMaxAmps(ControllerUtils.setValue(powerPanel.getMaxAmps(), powerPanelDTO.getMaxAmps()));

        powerPanel = powerPanelDao.save(powerPanel);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PowerPanelDTO getPowerPanelDTOById(Integer powerPanelId) {
	
		PowerPanel powerPanel = powerPanelDao.getById(powerPanelId);
		
		PowerPanelConvertCriteriaDTO convertCriteria = new PowerPanelConvertCriteriaDTO();
		return(this.convertPowerPanelToPowerPanelDTO(powerPanel,convertCriteria));
	}

}

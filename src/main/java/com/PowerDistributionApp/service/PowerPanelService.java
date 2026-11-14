package com.PowerDistributionApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PowerDistributionApp.domain.PowerPanel;
import com.PowerDistributionApp.dto.PowerPanelDTO;
import com.PowerDistributionApp.dto.PowerPanelSearchDTO;
import com.PowerDistributionApp.dto.PowerPanelPageDTO;
import com.PowerDistributionApp.dto.PowerPanelConvertCriteriaDTO;
import com.PowerDistributionApp.service.GenericService;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PowerPanelService extends GenericService<PowerPanel, Integer> {

	List<PowerPanel> findAll();

	ResultDTO addPowerPanel(PowerPanelDTO powerPanelDTO, RequestDTO requestDTO);

	ResultDTO updatePowerPanel(PowerPanelDTO powerPanelDTO, RequestDTO requestDTO);

    Page<PowerPanel> getAllPowerPanels(Pageable pageable);

    Page<PowerPanel> getAllPowerPanels(Specification<PowerPanel> spec, Pageable pageable);

	ResponseEntity<PowerPanelPageDTO> getPowerPanels(PowerPanelSearchDTO powerPanelSearchDTO);
	
	List<PowerPanelDTO> convertPowerPanelsToPowerPanelDTOs(List<PowerPanel> powerPanels, PowerPanelConvertCriteriaDTO convertCriteria);

	PowerPanelDTO getPowerPanelDTOById(Integer powerPanelId);



	
}

package com.PowerDistributionApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PowerDistributionApp.domain.PowerOutlet;
import com.PowerDistributionApp.dto.PowerOutletDTO;
import com.PowerDistributionApp.dto.PowerOutletSearchDTO;
import com.PowerDistributionApp.dto.PowerOutletPageDTO;
import com.PowerDistributionApp.dto.PowerOutletConvertCriteriaDTO;
import com.PowerDistributionApp.service.GenericService;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PowerOutletService extends GenericService<PowerOutlet, Integer> {

	List<PowerOutlet> findAll();

	ResultDTO addPowerOutlet(PowerOutletDTO powerOutletDTO, RequestDTO requestDTO);

	ResultDTO updatePowerOutlet(PowerOutletDTO powerOutletDTO, RequestDTO requestDTO);

    Page<PowerOutlet> getAllPowerOutlets(Pageable pageable);

    Page<PowerOutlet> getAllPowerOutlets(Specification<PowerOutlet> spec, Pageable pageable);

	ResponseEntity<PowerOutletPageDTO> getPowerOutlets(PowerOutletSearchDTO powerOutletSearchDTO);
	
	List<PowerOutletDTO> convertPowerOutletsToPowerOutletDTOs(List<PowerOutlet> powerOutlets, PowerOutletConvertCriteriaDTO convertCriteria);

	PowerOutletDTO getPowerOutletDTOById(Integer powerOutletId);



	
}

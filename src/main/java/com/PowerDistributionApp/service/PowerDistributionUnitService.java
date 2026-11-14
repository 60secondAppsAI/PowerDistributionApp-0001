package com.PowerDistributionApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PowerDistributionApp.domain.PowerDistributionUnit;
import com.PowerDistributionApp.dto.PowerDistributionUnitDTO;
import com.PowerDistributionApp.dto.PowerDistributionUnitSearchDTO;
import com.PowerDistributionApp.dto.PowerDistributionUnitPageDTO;
import com.PowerDistributionApp.dto.PowerDistributionUnitConvertCriteriaDTO;
import com.PowerDistributionApp.service.GenericService;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PowerDistributionUnitService extends GenericService<PowerDistributionUnit, Integer> {

	List<PowerDistributionUnit> findAll();

	ResultDTO addPowerDistributionUnit(PowerDistributionUnitDTO powerDistributionUnitDTO, RequestDTO requestDTO);

	ResultDTO updatePowerDistributionUnit(PowerDistributionUnitDTO powerDistributionUnitDTO, RequestDTO requestDTO);

    Page<PowerDistributionUnit> getAllPowerDistributionUnits(Pageable pageable);

    Page<PowerDistributionUnit> getAllPowerDistributionUnits(Specification<PowerDistributionUnit> spec, Pageable pageable);

	ResponseEntity<PowerDistributionUnitPageDTO> getPowerDistributionUnits(PowerDistributionUnitSearchDTO powerDistributionUnitSearchDTO);
	
	List<PowerDistributionUnitDTO> convertPowerDistributionUnitsToPowerDistributionUnitDTOs(List<PowerDistributionUnit> powerDistributionUnits, PowerDistributionUnitConvertCriteriaDTO convertCriteria);

	PowerDistributionUnitDTO getPowerDistributionUnitDTOById(Integer powerDistributionUnitId);



	
}

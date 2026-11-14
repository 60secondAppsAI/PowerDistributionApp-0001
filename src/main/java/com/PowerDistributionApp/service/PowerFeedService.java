package com.PowerDistributionApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.PowerDistributionApp.domain.PowerFeed;
import com.PowerDistributionApp.dto.PowerFeedDTO;
import com.PowerDistributionApp.dto.PowerFeedSearchDTO;
import com.PowerDistributionApp.dto.PowerFeedPageDTO;
import com.PowerDistributionApp.dto.PowerFeedConvertCriteriaDTO;
import com.PowerDistributionApp.service.GenericService;
import com.PowerDistributionApp.dto.common.RequestDTO;
import com.PowerDistributionApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PowerFeedService extends GenericService<PowerFeed, Integer> {

	List<PowerFeed> findAll();

	ResultDTO addPowerFeed(PowerFeedDTO powerFeedDTO, RequestDTO requestDTO);

	ResultDTO updatePowerFeed(PowerFeedDTO powerFeedDTO, RequestDTO requestDTO);

    Page<PowerFeed> getAllPowerFeeds(Pageable pageable);

    Page<PowerFeed> getAllPowerFeeds(Specification<PowerFeed> spec, Pageable pageable);

	ResponseEntity<PowerFeedPageDTO> getPowerFeeds(PowerFeedSearchDTO powerFeedSearchDTO);
	
	List<PowerFeedDTO> convertPowerFeedsToPowerFeedDTOs(List<PowerFeed> powerFeeds, PowerFeedConvertCriteriaDTO convertCriteria);

	PowerFeedDTO getPowerFeedDTOById(Integer powerFeedId);



	
}

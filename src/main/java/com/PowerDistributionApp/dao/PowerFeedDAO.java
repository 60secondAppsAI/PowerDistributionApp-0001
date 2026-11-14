package com.PowerDistributionApp.dao;

import java.util.List;
import java.util.Date;

import com.PowerDistributionApp.dao.GenericDAO;
import com.PowerDistributionApp.domain.PowerFeed;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface PowerFeedDAO extends GenericDAO<PowerFeed, Integer> {
  
	List<PowerFeed> findAll();
	


}


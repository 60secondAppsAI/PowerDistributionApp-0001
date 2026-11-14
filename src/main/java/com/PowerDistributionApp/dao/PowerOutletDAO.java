package com.PowerDistributionApp.dao;

import java.util.List;
import java.util.Date;

import com.PowerDistributionApp.dao.GenericDAO;
import com.PowerDistributionApp.domain.PowerOutlet;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface PowerOutletDAO extends GenericDAO<PowerOutlet, Integer> {
  
	List<PowerOutlet> findAll();
	


}


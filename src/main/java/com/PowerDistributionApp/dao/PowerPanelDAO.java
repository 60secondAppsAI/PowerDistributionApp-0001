package com.PowerDistributionApp.dao;

import java.util.List;
import java.util.Date;

import com.PowerDistributionApp.dao.GenericDAO;
import com.PowerDistributionApp.domain.PowerPanel;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface PowerPanelDAO extends GenericDAO<PowerPanel, Integer> {
  
	List<PowerPanel> findAll();
	


}


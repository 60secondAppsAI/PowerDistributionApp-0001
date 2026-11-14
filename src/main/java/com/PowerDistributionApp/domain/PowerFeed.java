package com.PowerDistributionApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="power_feeds")
@Getter @Setter @NoArgsConstructor
public class PowerFeed {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="power_feed_id")
	private Integer powerFeedId;
    
  	@Column(name="pdu_id")
	private Integer pduId;
    
  	@Column(name="feed_name")
	private String feedName;
    
  	@Column(name="feed_type")
	private String feedType;
    
  	@Column(name="capacity_kw")
	private Double capacityKw;
    

}

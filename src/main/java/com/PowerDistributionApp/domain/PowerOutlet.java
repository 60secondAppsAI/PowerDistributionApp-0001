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
@Table(name="power_outlets")
@Getter @Setter @NoArgsConstructor
public class PowerOutlet {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="power_outlet_id")
	private Integer powerOutletId;
    
  	@Column(name="power_panel_id")
	private Integer powerPanelId;
    
  	@Column(name="outlet_number")
	private String outletNumber;
    
  	@Column(name="status")
	private String status;
    
  	@Column(name="connected_asset_id")
	private Integer connectedAssetId;
    

}

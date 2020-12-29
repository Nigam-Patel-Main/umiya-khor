package com.softhub.umiyakhor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shop")
@Getter
@Setter
@Where(clause = "is_deleted=0")
public class ShopVo extends CommonVo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Column(name = "name")
	public String name;

	@Column(name = "mobile_number")
	public String mobileNumber;
	
	@OneToOne
	@JoinColumn(name = "district_id")
	public DistrictVo districtVo;
	
	@OneToOne
	@JoinColumn(name = "village_id")
	public VillageVo villageVo;
	
	@Column(name = "address")
	public String address;
}

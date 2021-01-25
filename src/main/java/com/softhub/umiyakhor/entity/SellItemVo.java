package com.softhub.umiyakhor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sell_item")
@Getter
@Setter
public class SellItemVo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductVo productVo;

	@Column(name = "qty")
	private Double qty;

	@Column(name = "price")
	private Double price;

	@Column(name = "total_amount")
	private Double totalAmount;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "sell_id")
	private SellVo sellVo;

}

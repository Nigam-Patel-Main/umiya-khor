package com.softhub.umiyakhor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "purchase_item")
@Getter
@Setter
@Where(clause = "is_deleted=0")
public class PurchaseItemVo extends CommonVo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductVo productVo;
	
	@Column(name = "qty")
	private Double qty;
	
	@Column(name = "product_amount")
	private Double productAmount;
	
	@Column(name = "total_amount")
	private Double totalAmount;
	
	@ManyToOne
	@JoinColumn(name = "purchase_id")
	private PurchaseVo purchaseVo;
	
}

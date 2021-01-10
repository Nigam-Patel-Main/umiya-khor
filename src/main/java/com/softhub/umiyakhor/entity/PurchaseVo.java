package com.softhub.umiyakhor.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@Where(clause = "is_deleted=0")
public class PurchaseVo extends CommonVo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "purchase_date")
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private Date purchaseDate;

	@ManyToOne
	@JoinColumn(name = "shop_id")
	private ShopVo shopVo;

	private Double productAmount;
	private Double expenseAmount;

	private Double totalAmaount;

	@OneToMany(mappedBy = "purchaseVo")
	private List<PurchaseItemVo> purchaseItemVos;

	@OneToMany(mappedBy = "purchaseVo")
	private List<ExpenseItemVo> expenseItemVos;

}

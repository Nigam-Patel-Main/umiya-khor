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
@Table(name = "expense_item")
@Getter
@Setter
@Where(clause = "is_deleted=0")
public class ExpenseItemVo extends CommonVo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "expense_category_id")
	private ExpenseCategoryVo expenseCategoryVo;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Double price;

	@ManyToOne
	@JoinColumn(name = "purchase_id")
	private PurchaseVo purchaseVo;

}

package com.softhub.umiyakhor.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "sell")
@Getter
@Setter
@Where(clause = "is_deleted=0")
public class SellVo extends CommonVo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "sell_date")
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private Date sellDate;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private CustomerVo customerVo;

	private Double productAmount;
	private Double expenseAmount;

	private Double totalAmount;

	@OneToMany(mappedBy = "sellVo", cascade = CascadeType.ALL)
	private List<SellItemVo> sellItemVos;

	@OneToMany(mappedBy = "sellVo", cascade = CascadeType.ALL)
	private List<ExpenseItemVo> expenseItemVos;

}

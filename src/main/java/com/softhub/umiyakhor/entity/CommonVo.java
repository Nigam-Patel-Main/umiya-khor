package com.softhub.umiyakhor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class CommonVo {
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "deleted_date")
	private Date deletedDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "deleted_by")
	private String deletedBy;
	
	
	@Column(name = "update_by")
	private String updatedBy;
	
	@Column(name = "is_deleted")
	private int isDeleted=0;
	
}

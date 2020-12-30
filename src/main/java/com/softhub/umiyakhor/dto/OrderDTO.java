package com.softhub.umiyakhor.dto;

import java.util.List;

public class OrderDTO {
	private List<OrderItemDTO> orderItemDTOs;

	public List<OrderItemDTO> getOrderItemDTOs() {
		return orderItemDTOs;
	}

	public void setOrderItemDTOs(List<OrderItemDTO> orderItemDTOs) {
		this.orderItemDTOs = orderItemDTOs;
	}
	
	
}

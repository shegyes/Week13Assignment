package com.promineotech.jeep.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Order {

	private Long orderPK;
	private Customer customer;
	private Jeep model;
	private Color color;
	private Engine engine;
	private Tire tire;
	private List<Option> options;
	private BigDecimal price;
	  
	  @JsonIgnore
	  public Long getOrderPK() {
	    return orderPK;
	  }
	
}

package com.liyang.domain.product;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="product_act")
public class ProductAct extends AbstractWorkflowAct<ProductState,ProductWorkflow> {

	public ProductAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	public ProductAct(){
		super();
	}
	
}

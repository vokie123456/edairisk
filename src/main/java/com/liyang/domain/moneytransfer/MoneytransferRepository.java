package com.liyang.domain.moneytransfer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface MoneytransferRepository extends WorkflowEntityRepository<Moneytransfer> {
	//根据persondid找到账户
}
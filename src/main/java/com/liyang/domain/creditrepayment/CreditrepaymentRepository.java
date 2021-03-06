package com.liyang.domain.creditrepayment;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.creditrepayplan.Creditrepayplan;
import com.liyang.domain.loan.Loan;

public interface CreditrepaymentRepository extends WorkflowEntityRepository<Creditrepayment> ,SpecificationQueryRepository<Creditrepayment>{
	//根据creditcardId找到信用实际还款记录
	@Query("select r from Creditrepayment r inner join r.creditcard a where a.id=:creditcardId")
	public Page<Creditrepayment> getCreditRepaymentByCreditcard(@Param("creditcardId")Integer creditcardId,Pageable pageable);
	

	//根据LoanId找到某笔贷款的实际还款记录
	@Query("select r from Creditrepayment r inner join r.loan a where a.id=:loanId")
	public Page<Creditrepayment> getCreditRepaymentByLoan(@Param("loanId")Integer loanId,Pageable pageable);
	
	@Query("select r from Creditrepayment r inner join r.loan a where a.id=:loanId")
	public List<Creditrepayment> getCreditRepaymentsByLoan(@Param("loanId")Integer loanId);
	
	@Query("select c from Creditrepayment c  where c.creditrepayplan.id=:creditrepayplanId and  c.state.stateCode=:stateCode")
	public Page<Creditrepayment> findByCreditrepayplanAndState(@Param("creditrepayplanId") Integer creditrepayplanId,@Param("stateCode") String stateCode,Pageable pageable);

	//根据还款计划表id查询实际还款记录
	//@Query("select r from Creditrepayment r inner join r.loan a where a.id=:loanId")	
	@Query("from Creditrepayment where id=(select max(r.id) from Creditrepayment r inner join r.creditrepayplan c where c.id=:planId)")
	public  Creditrepayment getCreditRepaymentByCreditrepayplan(@Param("planId") Integer planId);
	
	@Query("select c from Creditrepayment c where c.information like :loanSn")
	public List<Creditrepayment> findByLoanSnLike(@Param("loanSn") String loanSn);
	
	@Query("select c from Creditrepayment c where c.state.stateCode = 'ENABLED' and datediff(now(),c.createdAt) = :days")
	public List<Creditrepayment> findByCreatedAt(@Param("days") int days);

}
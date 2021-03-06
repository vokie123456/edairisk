package com.liyang.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.datetransform.DateTransform;
import com.liyang.domain.quota.Quota;
import com.liyang.dto.QuotaInfo;
import com.liyang.service.QuotaService;
import com.liyang.util.PageInfo;
import com.liyang.util.ResponseBody;

@RestController
@RequestMapping("/quota")
public class QuotaController {   
	
	@Autowired 
	QuotaService quotaService;
	
	//申请提额
	@RequestMapping("/quotaApplication")
	public ResponseBody savesaveQuoteApplication(Integer creditcardId,String quotaAmount ){
		
		quotaService.saveQuoteApplication(creditcardId, quotaAmount);
		return new ResponseBody("申请调额已经提交！");
	}
	
	//提额通过
	@RequestMapping("/quotaApplicationAdotp")
	public ResponseBody quotaApplicationAdotp(Integer id){
		
		quotaService.quotaApplicationAdotp(id);
		return new ResponseBody("调额成功！");
	}
	
	//提额未通过
	@RequestMapping("/quotaApplicationNotAdotp")
	public ResponseBody quotaApplicationNotAdotp(Integer id, String auditRemark){
		
		quotaService.quotaApplicationNotAdotp(id,auditRemark);
		return new ResponseBody("调额申请未通过！");
	}
	
	
	/**
	 * 提额列表
	 * @param stateCode
	 * @param personName
	 * @param applyEnterpriseName
	 * @param applyMobile
	 * @param pageable
	 * @return
	 */
	@GetMapping("/list")
	public ResponseBody getQuotaList(String stateCode,String personName,Quota quota,DateTransform dateTransform,Pageable pageable){
	
		Page<Quota> quotaPage = quotaService.getQuotaList(stateCode, personName,quota,dateTransform,pageable);
		Integer  number = quotaPage.getNumber();
		Integer  size = quotaPage.getSize();
		long  totalElements = quotaPage.getTotalElements();
		Integer  totalPages = quotaPage.getTotalPages();
		
		List<QuotaInfo> quotaInfoInfoList = new ArrayList<>();
		for (Quota Qu : quotaPage) {
			
			QuotaInfo quotaInfo  = new QuotaInfo();
			BeanUtils.copyProperties(Qu, quotaInfo);
			quotaInfo.setPersonName(Qu.getPerson().getName());				
			quotaInfoInfoList.add(quotaInfo);	
			
		}
		//自定义的page对象
		PageInfo pageInfo = new PageInfo(number, size, totalElements, totalPages, quotaInfoInfoList);
		//返回的最外层对象
		ResponseBody  responseBody = new ResponseBody(pageInfo);
		return responseBody;
	}
	
	@PostMapping("/queryQuotaRecord")
	public ResponseBody queryONeQuotaRecord(@PathParam("creditcardId") String creditcardId){		
		int  result = quotaService.queryQuotaRecord(creditcardId);
		return new ResponseBody(result);
	}

}

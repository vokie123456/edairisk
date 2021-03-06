package com.liyang.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrdermdbtInfo {
	
	private Integer id;
	/*身份证*/
	private String applyIdentityNo;
	/*真实姓名*/
	private String realName;
	/*手机号码*/
	private String applyMobile;	
	/*状态*/
	private String stateLabel;
	/*地址*/
	private String applyEnterpriseAddress;
	/*是否分配*/
	private Boolean isDistribution;
	/*创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date createdAt;
	/*最后修改时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date lastModifiedAt;
	/*业务员*/
	private String serviceName;
	/*公司名称*/
	private  String  companyName;
	/*经营区域*/
	private String applyEnterpriseReigionName;
	/*订单编号单号*/
	private  String orderNo;
	/*禁用备注*/
	private String disabledRemark;
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getApplyMobile() {
		return applyMobile;
	}

	public void setApplyMobile(String applyMobile) {
		this.applyMobile = applyMobile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApplyIdentityNo() {
		return applyIdentityNo;
	}

	public void setApplyIdentityNo(String applyIdentityNo) {
		this.applyIdentityNo = applyIdentityNo;
	}

	public String getStateLabel() {
		return stateLabel;
	}

	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}

	public String getApplyEnterpriseAddress() {
		return applyEnterpriseAddress;
	}

	public void setApplyEnterpriseAddress(String applyEnterpriseAddress) {
		this.applyEnterpriseAddress = applyEnterpriseAddress;
	}

	public Boolean getIsDistribution() {
		return isDistribution;
	}

	public void setIsDistribution(Boolean isDistribution) {
		this.isDistribution = isDistribution;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getApplyEnterpriseReigionName() {
		return applyEnterpriseReigionName;
	}

	public void setApplyEnterpriseReigionName(String applyEnterpriseReigionName) {
		this.applyEnterpriseReigionName = applyEnterpriseReigionName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDisabledRemark() {
		return disabledRemark;
	}

	public void setDisabledRemark(String disabledRemark) {
		this.disabledRemark = disabledRemark;
	}
	
	
}

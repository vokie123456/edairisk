package com.liyang.domain.quota;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liyang.annotation.Info;
import com.liyang.annotation.PersonField;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.person.Person;
import com.liyang.domain.product.Product;
import com.liyang.domain.user.User;

/**
 * @author by
 *
 */
@Entity
@Table(name = "quota")
@Cacheable
@Info(label="调整额度",placeholder="",tip="",help="",secret="")
public class Quota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Info(label="id",placeholder="",tip="",help="",secret="")						
	private Integer id;
	
	@JoinColumn(name="product_id")
	@OneToOne
	@Info(label="产品ID",placeholder="",tip="",help="",secret="")	
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "creditcard_id")
	@Info(label="信用卡id",placeholder="",tip="",help="",secret="")
	private Creditcard creditcard;
	
	//不同的产品都是用这一个字段
	@Info(label="产品申请记录id",placeholder="",tip="",help="",secret="")
	@Column(name = "product_record_id")
	private Integer productRecordId;
	
	@Column(name = "product_label")
	@Info(label="产品名称",placeholder="",tip="",help="",secret="")
	private String productLabel;
	
	@Column(name = "state_code")
	@Info(label="状态",placeholder="",tip="",help="",secret="")
	private String stateCode;
	
	@Column(name="quota_amount",precision=19,scale=6)
	@Info(label="额度",placeholder="",tip="",help="",secret="")	
	private BigDecimal quotaAmount;
	
	@Column(name="application_time")
	@Info(label="申请时间",placeholder="",tip="",help="",secret="")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date applicationTime;
	
	@JoinColumn(name = "application_user")
	@OneToOne
	@Info(label="申请人",placeholder="",tip="",help="",secret="")	
	private User applicationUser;
	
	@Column(name="quota_number")
	@Info(label="订单单号",placeholder="",tip="",help="",secret="")
	private String quotaNumber;
	
	@Column(name = "apply_enterprise_name")
	@Info(label="网点名称",placeholder="",tip="",help="",secret="")
	private String applyEnterpriseName;	
	
	@OneToOne
	@JoinColumn(name = "apply_person_id")
	@Info(label="自然人",placeholder="",tip="",help="",secret="")	
	private Person person;

	@Column(name = "apply_mobile")
	@Info(label="申请人手机号",placeholder="",tip="",help="",secret="")
	private String applyMobile;
	
	@Column(name = "apply_daypickexpress")
	@Info(label="日收件量",placeholder="",tip="",help="",secret="")
	private Integer applyDayPickExpress;//日收件量
	
	@Column(name = "apply_daypatchexpress")
	@Info(label="日派件量",placeholder="",tip="",help="",secret="") 
	private Integer applyDayPatchExpress;//日派件量
	
	@Column(name="current_amount",precision=19,scale=6)
	@Info(label="当前额度",placeholder="",tip="",help="",secret="")	
	private BigDecimal currentAmount;
	
	@Column(name = "audit_remark")
	@Info(label="审核备注",placeholder="",tip="",help="",secret="")
	private String AuditRemark;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getProductRecordId() {
		return productRecordId;
	}

	public void setProductRecordId(Integer productRecordId) {
		this.productRecordId = productRecordId;
	}

	public String getProductLabel() {
		return productLabel;
	}

	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public User getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(User applicationUser) {
		this.applicationUser = applicationUser;
	}
	
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getQuotaNumber() {
		return quotaNumber;
	}

	public void setQuotaNumber(String quotaNumber) {
		this.quotaNumber = quotaNumber;
	}	
	
	public String getApplyEnterpriseName() {
		return applyEnterpriseName;
	}

	public void setApplyEnterpriseName(String applyEnterpriseName) {
		this.applyEnterpriseName = applyEnterpriseName;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getApplyMobile() {
		return applyMobile;
	}

	public void setApplyMobile(String applyMobile) {
		this.applyMobile = applyMobile;
	}

	public Integer getApplyDayPickExpress() {
		return applyDayPickExpress;
	}

	public void setApplyDayPickExpress(Integer applyDayPickExpress) {
		this.applyDayPickExpress = applyDayPickExpress;
	}

	public Integer getApplyDayPatchExpress() {
		return applyDayPatchExpress;
	}

	public void setApplyDayPatchExpress(Integer applyDayPatchExpress) {
		this.applyDayPatchExpress = applyDayPatchExpress;
	}
	
	public BigDecimal getQuotaAmount() {
		return quotaAmount;
	}

	public void setQuotaAmount(BigDecimal quotaAmount) {
		this.quotaAmount = quotaAmount;
	}

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Creditcard getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(Creditcard creditcard) {
		this.creditcard = creditcard;
	}

	public String getAuditRemark() {
		return AuditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		AuditRemark = auditRemark;
	}	
	

	@Override
	public String toString() {
		return "Quota [id=" + id + ", product=" + product + ", creditcard=" + creditcard + ", productRecordId="
				+ productRecordId + ", productLabel=" + productLabel + ", stateCode=" + stateCode + ", quotaAmount="
				+ quotaAmount + ", applicationTime=" + applicationTime + ", applicationUser=" + applicationUser
				+ ", quotaNumber=" + quotaNumber + ", applyEnterpriseName=" + applyEnterpriseName + ", person=" + person
				+ ", applyMobile=" + applyMobile + ", applyDayPickExpress=" + applyDayPickExpress
				+ ", applyDayPatchExpress=" + applyDayPatchExpress + ", currentAmount=" + currentAmount
				+ ", AuditRemark=" + AuditRemark + "]";
	}
	
}

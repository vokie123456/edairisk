package com.liyang.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.Enum.OrderTypeEnum;
import com.liyang.domain.account.AccountRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.company.Company;
import com.liyang.domain.company.CompanyRepository;
import com.liyang.domain.company.CompanyStateRepository;
import com.liyang.domain.creditcard.Creditcard;
import com.liyang.domain.creditcard.CreditcardRepository;
import com.liyang.domain.creditcard.CreditcardStateRepository;
import com.liyang.domain.datetransform.DateTransform;
import com.liyang.domain.ordermdbt.Ordermdbt;
import com.liyang.domain.ordermdbt.OrdermdbtAct;
import com.liyang.domain.ordermdbt.OrdermdbtActRepository;
import com.liyang.domain.ordermdbt.OrdermdbtFile;
import com.liyang.domain.ordermdbt.OrdermdbtLog;
import com.liyang.domain.ordermdbt.OrdermdbtLogRepository;
import com.liyang.domain.ordermdbt.OrdermdbtRepository;
import com.liyang.domain.ordermdbt.OrdermdbtState;
import com.liyang.domain.ordermdbt.OrdermdbtStateRepository;
import com.liyang.domain.ordermdbt.OrdermdbtWorkflow;
import com.liyang.domain.ordermdbt.OrdermdbtWorkflowRepository;
import com.liyang.domain.orderwdsjsh.Orderwdsjsh;
import com.liyang.domain.person.Person;
import com.liyang.domain.person.PersonRepository;
import com.liyang.domain.person.PersonStateRepository;
import com.liyang.domain.product.ProductRepository;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ProductUtil;
import com.liyang.util.ReturnObject;
import com.liyang.util.IdcardUtil;
@Service
@Order(34)
public class OrdermdbtService extends AbstractWorkflowService<com.liyang.domain.ordermdbt.Ordermdbt, OrdermdbtWorkflow, com.liyang.domain.ordermdbt.OrdermdbtAct, OrdermdbtState, OrdermdbtLog, OrdermdbtFile>{
	@Autowired
	OrdermdbtActRepository ordermdbtActRepository;
	
	@Autowired
	OrdermdbtStateRepository ordermdbtStateRepository;
	
	@Autowired
	OrdermdbtLogRepository  ordermdbtLogRepository;
	
	@Autowired
	OrdermdbtRepository  ordermdbtRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
    @Autowired
    WlqzWechatPubService wechatPubService;
    
    @Value("${spring.wlqz.wechat.OPEN_ACC_APPLY}")
    private String OPEN_ACC_APPLY;
	@Value("${spring.wlqz.wechat.OPEN_ACC_SUCCESS}")
	private String OPEN_ACC_SUCCESS;
	
	@Autowired
	OrdermdbtWorkflowRepository ordermdbtWorkflowRepository;
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	CompanyStateRepository companyStateRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CreditcardStateRepository creditcardStateRepository;
	@Autowired
	CreditcardRepository creditcardRepository;
	@Autowired
	private XinGePushService xinGePushService;
	@Autowired
	SmsService smsService;
    @Autowired
    DateTransform dateTransform;
	@Override
	@PostConstruct 
	public void sqlInit() {
		
			long findAll = ordermdbtActRepository.count();
	        if(findAll == 0 ){
			OrdermdbtAct save1 = ordermdbtActRepository.save(new OrdermdbtAct("创建","create",10,ActGroup.UPDATE));
			OrdermdbtAct save2 = ordermdbtActRepository.save(new OrdermdbtAct("读取","read",20,ActGroup.READ));
			OrdermdbtAct save3 = ordermdbtActRepository.save(new OrdermdbtAct("更新","update",30,ActGroup.UPDATE));
			OrdermdbtAct save4 = ordermdbtActRepository.save(new OrdermdbtAct("删除","delete",40,ActGroup.UPDATE));
			OrdermdbtAct save5 = ordermdbtActRepository.save(new OrdermdbtAct("自己的列表","listOwn",50,ActGroup.READ));
			OrdermdbtAct save6 = ordermdbtActRepository.save(new OrdermdbtAct("部门的列表","listOwnDepartment",60,ActGroup.READ));
			OrdermdbtAct save7 = ordermdbtActRepository.save(new OrdermdbtAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70,ActGroup.READ));
			OrdermdbtAct save8 = ordermdbtActRepository.save(new OrdermdbtAct("通知其他人","noticeOther",80,ActGroup.NOTICE));
			OrdermdbtAct save9 = ordermdbtActRepository.save(new OrdermdbtAct("通知操作者","noticeActUser",90,ActGroup.NOTICE));
			OrdermdbtAct save10 = ordermdbtActRepository.save(new OrdermdbtAct("通知展示人","noticeShowUser",100,ActGroup.NOTICE));
			
	
			OrdermdbtState newState =new OrdermdbtState("已创建",0,"CREATED");
			newState = ordermdbtStateRepository.save(newState);
			OrdermdbtState enableState = new OrdermdbtState("有效",100,"ENABLED");
			enableState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7).stream().collect(Collectors.toSet()));
			ordermdbtStateRepository.save(enableState);
			ordermdbtStateRepository.save(new OrdermdbtState("无效",200,"DISABLED"));
			ordermdbtStateRepository.save(new OrdermdbtState("删除",300,"DELETED"));
			
		}
		
	}

	@Override
	public LogRepository<OrdermdbtLog> getLogRepository() {
		// TODO Auto-generated method stub
		return ordermdbtLogRepository;
	}

	@Override
	public AuditorEntityRepository<Ordermdbt> getAuditorEntityRepository() {
		
		return ordermdbtRepository;
	}


	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new Ordermdbt().setLogRepository(ordermdbtLogRepository);
		
	}

	@Override
	public OrdermdbtLog getLogInstance() {
		// TODO Auto-generated method stub
		return new OrdermdbtLog();
	}

	@Override
	public ActRepository<OrdermdbtAct> getActRepository() {
		// TODO Auto-generated method stub
		return ordermdbtActRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityService() {
		new Ordermdbt().setService(this);
		
	}
	@Override
	public OrdermdbtFile getFileLogInstance() {
		// TODO Auto-generated method stub
		return new OrdermdbtFile();
	}
	@Autowired
	PersonStateRepository personStateRepository;
	@Autowired
	UserRepository userRepository;
	
	/**
     * 主管 为贷款人分配业务人员 notice id=1  将这个用户分配到id为1的业务员下
     *
     * @param
     */
    public void doDistribution(Ordermdbt ordermdbt) {
    	ordermdbt.setServiceName(userRepository.findOne(ordermdbt.getServiceId()).getNickname());
    	ordermdbt.setServiceUser(userRepository.findOne(ordermdbt.getServiceId()));
    	ordermdbt.setIsDistribution(true);
        System.out.println("--------------" + ordermdbt.getServiceId());
    }
	
    /**
     * 提交申请动作  一次只能允许一次提交，待提交成功后才能进行下一次申请
     *
     * @param Ordermdbt
     */
    public void doCreate(Ordermdbt ordermdbt) {
    	
    	ordermdbt.setWorkflow(ordermdbtWorkflowRepository.findOne(1));
    	ordermdbt.setOrderNo(ProductUtil.modify(ordermdbt.getProduct().getLabel(), OrderTypeEnum.ORDER));
    	Ordermdbt findByApplyMobile =ordermdbtRepository.findByApplyMobile(ordermdbt.getApplyMobile());
        if (null != findByApplyMobile) {
            throw new FailReturnObject(6657, "面单白条订单相同手机号" + ordermdbt.getApplyMobile() + "已存在，请使用不同手机号", ReturnObject.Level.WARNING);
        }
        Ordermdbt findByApplyIdentityNo = ordermdbtRepository.findByApplyIdentityNo(ordermdbt.getApplyIdentityNo());
        Boolean isIdcard  =  IdcardUtil.isIdcard(ordermdbt.getApplyIdentityNo());
        if(!isIdcard){
        	throw new FailReturnObject(1326, "面单白条订单身份证号" + ordermdbt.getApplyIdentityNo() + "无效的身份证号码，请使用正确的身份证", ReturnObject.Level.WARNING);         	
        }
        if (null != findByApplyIdentityNo) {
            throw new FailReturnObject(6657, "面单白条订单相同身份证号" + ordermdbt.getApplyIdentityNo() + "已存在，请使用不同身份证", ReturnObject.Level.WARNING);
        }
        Page<Ordermdbt> ordermdbts = ordermdbtRepository.listOwn(null);
        //获取当前用户已经申请了的面单白条
        List<Ordermdbt> list = ordermdbts.getContent();
        //判断面单白条状态 
        if (ordermdbts != null && list.size() != 0) {
            for (Ordermdbt exitOrdermdbt : list) {

                if (!exitOrdermdbt.isStateEnable()) {
                    System.out.println(exitOrdermdbt.getApplyIdentityNo() + ":" + exitOrdermdbt.getState().getStateCode() + ":" + exitOrdermdbt.getId());
                    throw new FailReturnObject(199, "前一个申请还在审核中！", ReturnObject.Level.WARNING);
                }
            }
        }
        smsService.sendCommitMessage(ordermdbt.getOrderNo(), "产品申请", "面单白条");
    }
    
    //驿贷通过
    public void doAdopt(Ordermdbt ordermdbt) {
    	//创建人（当前用户）
        User user = ordermdbt.getCreatedBy();       
        //user.setCompanyRole("manager");
        if (user == null) {
            throw new FailReturnObject(499, "没有创建人", ReturnObject.Level.WARNING);
        }
        System.out.println("doAdopt");
        //1.面单白条关联person,用户关联person         
        String identity = ordermdbt.getApplyIdentityNo();
        Person person = personRepository.findByIdentity(identity);
        //无person创建person 
        if (person == null) {
            person = new Person();
            person = wechatPubService.setPersonField(person, ordermdbt);
            person.setState(personStateRepository.findByStateCode("ENABLED"));
            person.setAccountStatus("NORMAL");
            wechatPubService.pushOpenAccMessage(user, "审核通过", person.getName(), person.getTelephone(), OPEN_ACC_APPLY, ordermdbt.getComment());
        }
        personRepository.save(person);
        //设置申请人
        ordermdbt.setUser(user);
        user.setPerson(person);
        userRepository.save(user);
        //设置person 
        ordermdbt.setPerson(person);  
        
        //2.修改公司状态为有效
        Company company =  ordermdbt.getCompany();
        //判断公司为空
        if(company == null){
        	throw new FailReturnObject(1317, "请先完善公司信息", ReturnObject.Level.WARNING); 
        }
        company.setState(companyStateRepository.findByStateCode("ENABLED"));
        //关联公司申请人
        company.setUser(user);
        company.setPerson(person);
        companyRepository.save(company);       
        //3.创建信用卡,并关联person,设置有效
        Creditcard creditcard = new Creditcard();
        //手机号
        creditcard.setCreditcardIdentity(ordermdbt.getApplyMobile());
        //总额度
        creditcard.setCreditGrant(new BigDecimal(ordermdbt.getApplyAmount()));
        //剩余额度
        creditcard.setCreditBalance(new BigDecimal(ordermdbt.getApplyAmount()));
		//信用卡关联person 
		creditcard.setPerson(person);
		creditcard.setProduct(productRepository.findFirstByLabel("面单白条"));
		//信用卡关联company 
		creditcard.setCompany(company);
		creditcard.setState(creditcardStateRepository.findByStateCode("ENABLED"));
		creditcardRepository.save(creditcard);
		if (ordermdbt!=null) {
			ordermdbt.setDebtorCreditcard(creditcard);
		}
		
		
		//4、保存面单白条
		//ordermdbt.setState(ordermdbtStateRepository.findByStateCode("ENABLED"));
		//生成唯一的申请单号
		ordermdbtRepository.save(ordermdbt);		
		String content = xinGePushService.getMdbtPassMessage(ordermdbt);
		xinGePushService.pushAppMessage(ordermdbt.getCreatedBy(), ordermdbt.getProduct(), "审核进度通知", content, 2);
		//wechatPubService.pushOpenAccMessage(ordermdbt.getCreatedBy(), "审核通过", ordermdbt.getRealName(), ordermdbt.getApplyMobile(), OPEN_ACC_APPLY, content);
    }
    
    //驿贷未通过
    public void doFailed(Ordermdbt ordermdbt) {
        wechatPubService.pushOpenAccMessage(ordermdbt.getCreatedBy(), "审核未通过", ordermdbt.getRealName(), ordermdbt.getApplyMobile(), OPEN_ACC_APPLY, ordermdbt.getComment());
        String content = xinGePushService.getYiDaiFailedContent(ordermdbt.getProduct().getLabel());
        xinGePushService.pushAppMessage(ordermdbt.getCreatedBy(), ordermdbt.getProduct(), "审核进度通知", content,1);       
    }
    
    //根据条件动态查询ordermdbt并分页
    public Page<Ordermdbt> getOrdermdbt(Ordermdbt ordermdbt,DateTransform dateTransform,String stateCode,String companyName,Integer serviceUserId,Pageable pageable){ 
    	
	    GenericQueryExpSpecification<Ordermdbt> queryExpression = new GenericQueryExpSpecification<Ordermdbt>();
	    queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", stateCode, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("realName", ordermdbt.getRealName(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyMobile", ordermdbt.getApplyMobile(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("company.companyName", companyName, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("orderNo", ordermdbt.getOrderNo(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("serviceUser.id", serviceUserId, true));
    	queryExpression.add(QueryExpSpecificationBuilder.gt("createdAt", dateTransform.getCreateDateTime(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.lt("createdAt", dateTransform.getEndDateTime(), true));	
		
		return ordermdbtRepository.findAll(queryExpression,pageable);	
    }
    
    //根据条件动态查询ordermdbt并分页
    public Page<Ordermdbt> getCommissionerOrdermdbtList(Ordermdbt ordermdbt,DateTransform dateTransform,String stateCode,String companyName,Pageable pageable){ 
    	
	    GenericQueryExpSpecification<Ordermdbt> queryExpression = new GenericQueryExpSpecification<Ordermdbt>();
	    queryExpression.add(QueryExpSpecificationBuilder.eq("state.stateCode", stateCode, true));
	    queryExpression.add(QueryExpSpecificationBuilder.eq("realName", ordermdbt.getRealName(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("applyMobile", ordermdbt.getApplyMobile(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("serviceUser.id", ordermdbt.getServiceUser().getId(), true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("company.companyName", companyName, true));
		queryExpression.add(QueryExpSpecificationBuilder.eq("orderNO", ordermdbt.getOrderNo(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.gt("createdAt", dateTransform.getCreateDateTime(), true));
    	queryExpression.add(QueryExpSpecificationBuilder.lt("createdAt", dateTransform.getEndDateTime(), true));		
		
		return ordermdbtRepository.findAll(queryExpression,pageable);	
    }
    
	//修改身份证
	public void doChangeID(Ordermdbt ordermdbt){			
		Ordermdbt mdbt = ordermdbtRepository.findByApplyIdentityNoAndState(ordermdbt.getApplyIdentityNo());
		if(mdbt != null){
			throw new FailReturnObject(1331, "该身份证已经被使用！", ReturnObject.Level.WARNING);
		}
	}
     
}

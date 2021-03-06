package main.CRM.bean;

import org.json.JSONObject;

public class Subscriber {

	String name = "";
	String birthday = "";
	String idTaxid = "";
	String phone = "";
	String email = "";
	String permanentAddress = "";
	String billingAddress = "";
	String agency = "";
	String remark = "";
	String type = "";
	String createtime = "";
	String updatetime = "";
	
	//20170317 add
	String passportId = "";
	String passportName = "";
	
	String s2tMsisdn = "";
	String chtMsisdn = "";
	String serviceId = "";
	
	String chair = "";
	String chairId = "";
	
	String s2tIMSI = "";
	PricePlanID privePlanId = null;
	
	String status = "";
	
	String activatedDate = "";
	String canceledDate = "";
	
	String seq="";
	
	String homeIMSI="";
	
	
	String nowS2tActivated = "0";
	
	public Subscriber(){
		
	}
	
	public Subscriber(JSONObject j) {
		super();
		
		for(String name:JSONObject.getNames(j)){
			if("name".equalsIgnoreCase(name)){
				this.name = j.getString(name);
			}else if("birthday".equalsIgnoreCase(name)){
				this.birthday = j.getString(name);
			}else if("idTaxid".equalsIgnoreCase(name)){
				this.idTaxid = j.getString(name);
			}else if("phone".equalsIgnoreCase(name)){
				this.phone = j.getString(name);
			}else if("email".equalsIgnoreCase(name)){
				this.email = j.getString(name);
			}else if("permanentAddress".equalsIgnoreCase(name)){
				this.permanentAddress = j.getString(name);
			}else if("billingAddress".equalsIgnoreCase(name)){
				this.billingAddress = j.getString(name);
			}else if("agency".equalsIgnoreCase(name)){
				this.agency = j.getString(name);
			}else if("remark".equalsIgnoreCase(name)){
				this.remark = j.getString(name);
			}else if("type".equalsIgnoreCase(name)){
				this.type = j.getString(name);
			}else if("createtime".equalsIgnoreCase(name)){
				this.createtime = j.getString(name);
			}else if("updatetime".equalsIgnoreCase(name)){
				this.updatetime = j.getString(name);
			}else if("s2tMsisdn".equalsIgnoreCase(name)){
				this.s2tMsisdn = j.getString(name);
			}else if("chtMsisdn".equalsIgnoreCase(name)){
				this.chtMsisdn = j.getString(name);
			}else if("serviceId".equalsIgnoreCase(name)){
				this.serviceId = j.getString(name);
			}else if("chair".equalsIgnoreCase(name)){
				this.chair = j.getString(name);
			}else if("chairId".equalsIgnoreCase(name)){
				this.chairId = j.getString(name);
			}else if("seq".equalsIgnoreCase(name)){
				this.seq = j.getString(name);
			}else if("homeIMSI".equalsIgnoreCase(name)){
				this.homeIMSI = j.getString(name);
			}else if("passportId".equalsIgnoreCase(name)){
				this.passportId = j.getString(name);
			}else if("passportName".equalsIgnoreCase(name)){
				this.passportName = j.getString(name);
			}
		};
		
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIdTaxid() {
		return idTaxid;
	}
	public void setIdTaxid(String idTaxid) {
		this.idTaxid = idTaxid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getS2tMsisdn() {
		return s2tMsisdn;
	}
	public void setS2tMsisdn(String s2tMsisdn) {
		this.s2tMsisdn = s2tMsisdn;
	}
	public String getChtMsisdn() {
		return chtMsisdn;
	}
	public void setChtMsisdn(String chtMsisdn) {
		this.chtMsisdn = chtMsisdn;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getChair() {
		return chair;
	}

	public void setChair(String chair) {
		this.chair = chair;
	}

	public String getChairId() {
		return chairId;
	}

	public void setChairId(String chairId) {
		this.chairId = chairId;
	}

	public String getS2tIMSI() {
		return s2tIMSI;
	}

	public void setS2tIMSI(String s2tIMSI) {
		this.s2tIMSI = s2tIMSI;
	}

	public PricePlanID getPrivePlanId() {
		return privePlanId;
	}

	public void setPrivePlanId(PricePlanID privePlanId) {
		this.privePlanId = privePlanId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActivatedDate() {
		return activatedDate;
	}

	public void setActivatedDate(String activatedDate) {
		this.activatedDate = activatedDate;
	}

	public String getCanceledDate() {
		return canceledDate;
	}

	public void setCanceledDate(String canceledDate) {
		this.canceledDate = canceledDate;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getHomeIMSI() {
		return homeIMSI;
	}

	public void setHomeIMSI(String homeIMSI) {
		this.homeIMSI = homeIMSI;
	}

	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

	public String getPassportName() {
		return passportName;
	}

	public void setPassportName(String passportName) {
		this.passportName = passportName;
	}

	public String getNowS2tActivated() {
		return nowS2tActivated;
	}

	public void setNowS2tActivated(String nowS2tActivated) {
		this.nowS2tActivated = nowS2tActivated;
	}

	
	
	
	

	
}

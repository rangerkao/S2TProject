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
	
	String s2tMsisdn = "";
	String chtMsisdn = "";
	String serviceId = "";
	
	String chair = "";
	String chairID = "";
	
	String s2tIMSI = "";
	String privePlanId = "";
	
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
			}else if("chairID".equalsIgnoreCase(name)){
				this.chairID = j.getString(name);
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

	public String getChairID() {
		return chairID;
	}

	public void setChairID(String chairID) {
		this.chairID = chairID;
	}

	public String getS2tIMSI() {
		return s2tIMSI;
	}

	public void setS2tIMSI(String s2tIMSI) {
		this.s2tIMSI = s2tIMSI;
	}

	public String getPrivePlanId() {
		return privePlanId;
	}

	public void setPrivePlanId(String privePlanId) {
		this.privePlanId = privePlanId;
	}
	
	

	
}

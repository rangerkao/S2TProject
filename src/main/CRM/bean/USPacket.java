package main.CRM.bean;
/**
 * 美國流量包 Bean
 * @author ranger.kao
 *
 */
public class USPacket {

	String serviceid;
	String startDate;
	String endDate;
	String createTime;
	String cancelTime;
	
	String alerted;

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		if(cancelTime==null)
			cancelTime = "";
		
		this.cancelTime = cancelTime;
	}

	public String getAlerted() {
		return alerted;
	}

	public void setAlerted(String alerted) {
		this.alerted = alerted;
	}
	
	
	
	
}

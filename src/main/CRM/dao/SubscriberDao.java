package main.CRM.dao;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.CRM.bean.Subscriber;
import main.common.dao.BaseDao;

public class SubscriberDao extends BaseDao{

	public SubscriberDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Subscriber> queryListById(String id) throws SQLException{
		List<Subscriber> result = new ArrayList<Subscriber>();
		result = queryList("WHERE A.ID_TAXID='"+id+"' ");
		closeConnection();
		return result;
	}
	
	public List<Subscriber> queryListByName(String name) throws SQLException{
		List<Subscriber> result = new ArrayList<Subscriber>();
		result = queryList("WHERE A.NAME Like '%"+processEncodeData(name,"BIG5","ISO-8859-1")+"%' ");
		closeConnection();
		return result;
	}
	
	private List<Subscriber> queryList(String condition) throws SQLException{
		List<Subscriber> result = new ArrayList<Subscriber>();
		
		String sql = "SELECT A.ID_TAXID ID,A.NAME,A.PERMANENT_ADDRESS FROM CRM_SUBSCRIBER A "+condition;
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()){
			Subscriber s = new Subscriber();
			s.setIdTaxid(rs.getString("ID"));
			s.setName(processEncodeData(rs.getString("NAME"),"ISO-8859-1","BIG5"));
			s.setPermanentAddress(processEncodeData(rs.getString("PERMANENT_ADDRESS"),"ISO-8859-1","BIG5"));
			result.add(s);
		}		
		rs.close();
		st.close();
		return result;
	}
	
	public Subscriber queryDataById(String id) throws SQLException{
		Subscriber result = new Subscriber();
		
		String sql = "SELECT A.ID_TAXID ID,A.NAME,A.BIRTHDAY,A.PHONE,A.EMAIL,A.PERMANENT_ADDRESS,A.BILLING_ADDRESS,A.AGENCY,A.REMARK, "
				+ "to_char(A.CREATETIME,'yyy/MM/dd hh24:mi:ss') CREATETIME,to_char(A.UPDATETIME,'yyy/MM/dd hh24:mi:ss') UPDATETIME,A.TYPE "
				+ "FROM CRM_SUBSCRIBER A WHERE A.ID_TAXID='"+id+"' ";
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()){

			result.setName(processEncodeData(rs.getString("NAME"),"ISO-8859-1","BIG5"));
			result.setIdTaxid(processData(rs.getString("ID")));
			result.setBirthday(processData(rs.getString("BIRTHDAY")));
			result.setPhone(processData(rs.getString("PHONE")));
			result.setEmail(processData(rs.getString("EMAIL")));
			result.setPermanentAddress(processEncodeData(rs.getString("PERMANENT_ADDRESS"),"ISO-8859-1","BIG5"));
			result.setBillingAddress(processEncodeData(rs.getString("BILLING_ADDRESS"),"ISO-8859-1","BIG5"));
			result.setAgency(processEncodeData(rs.getString("AGENCY"),"ISO-8859-1","BIG5"));
			result.setRemark(processEncodeData(rs.getString("REMARK"),"ISO-8859-1","BIG5"));
			result.setCreatetime(processData(rs.getString("CREATETIME")));
			result.setUpdatetime(processData(rs.getString("UPDATETIME")));
			result.setType(processData(rs.getString("TYPE")));

		}
		rs.close();
		st.close();
		closeConnection();
		return result;
	}
	public String processData(String data){
		return (data==null?" ":data);
	}
	public String processEncodeData(String data,String sCharSet,String dCharSet){
		if(data==null)
			data=" ";
		
		try {
			data = new String(data.getBytes(sCharSet),dCharSet);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
}

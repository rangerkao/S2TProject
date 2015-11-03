package main.CRM.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.CRM.bean.QosBean;


public class QosDao extends CRMBaseDao {

	public QosDao() throws Exception {
		super();
	}

	//查詢列表
	public List<QosBean> queryQosList() throws SQLException{
		String sql=
				"SELECT A.PROVISIONID,A.IMSI,A.MSISDN,A.PLAN,A.ACTION,A.RESPONSE_CODE,A.RESULT_CODE,to_char(A.CERATE_TIME,'yyyyMMdd hh24:mi:ss') ctime "
				+ "FROM QOS_PROVISION_LOG A ";

		List<QosBean> list=new ArrayList<QosBean>();
		
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			System.out.println("Execute SQL :"+sql);
			while(rs.next()){
				QosBean qosdata =new QosBean();
				String rc = rs.getString("RESULT_CODE");
				if(rc!=null && rc.contains("RETURN_CODE=0")){
					rc="成功";
				}else{
					rc="";
				}
				
				String rc2 = rs.getString("RESPONSE_CODE");
				if(rc2!=null && rc2.contains("200")){
					rc2="正常";
				}else{
					rc2="";
				}
				qosdata.setProvisionID(rs.getInt("PROVISIONID"));
				qosdata.setImsi(rs.getString("IMSI"));
				qosdata.setMsisdn(rs.getString("MSISDN"));
				qosdata.setPlan(rs.getString("PLAN"));
				qosdata.setAction(rs.getString("ACTION"));
				qosdata.setResultCode(rc);
				qosdata.setReturnCode(rc2);
				qosdata.setCreateTime(rs.getString("ctime"));
				list.add(qosdata);
			}
		} finally{
			try {
				if(st!=null)
					st.close();
				if(rs!=null)
					rs.close();
			} catch (Exception e) {
			}
		}
		closeConnection();
			
		return list;
		
	}
	
	//查詢列表
	public List<QosBean> queryQosList(String imsi,String msisdn) throws SQLException{
		String sql=
				"SELECT A.PROVISIONID,A.IMSI,A.MSISDN,A.PLAN,A.ACTION,A.RESPONSE_CODE,A.RESULT_CODE,to_char(A.CERATE_TIME,'yyyyMMdd hh24:mi:ss') ctime "
				+ "FROM QOS_PROVISION_LOG A "
				+ "WHERE 1=1 "
				+ (imsi!=null && !"".equals(imsi) ? "AND A.IMSI like '"+imsi+"'" : "")
				+ (msisdn!=null && !"".equals(msisdn) ? "AND A.MSISDN like '"+msisdn+"'" : "");

		List<QosBean> list=new ArrayList<QosBean>();
		
		Statement st = conn.createStatement();
		ResultSet rs=st.executeQuery(sql);
		System.out.println("Execute SQL :"+sql);
		while(rs.next()){
			QosBean qosdata =new QosBean();
			
			String rc = rs.getString("RESULT_CODE");
			if(rc!=null && rc.contains("RETURN_CODE=0")){
				rc="成功";
			}else{
				rc="";
			}
			
			String rc2 = rs.getString("RESPONSE_CODE");
			if(rc2!=null && rc2.contains("200")){
				rc2="正常";
			}else{
				rc2="";
			}
			
			qosdata.setProvisionID(rs.getInt("PROVISIONID"));
			qosdata.setImsi(rs.getString("IMSI"));
			qosdata.setMsisdn(rs.getString("MSISDN"));
			qosdata.setPlan(rs.getString("PLAN"));
			qosdata.setAction(rs.getString("ACTION"));
			qosdata.setResultCode(rc);
			qosdata.setReturnCode(rc2);
			qosdata.setCreateTime(rs.getString("ctime"));
			list.add(qosdata);
		}
		st.close();
		rs.close();
		closeConnection();
		return list;
		
	}
	
}

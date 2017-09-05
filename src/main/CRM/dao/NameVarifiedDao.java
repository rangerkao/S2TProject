package main.CRM.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.CRM.bean.NameVarified;
import main.CRM.bean.NameVarifiedSet;

@Repository
public class NameVarifiedDao extends CRMBaseDao{

	public NameVarifiedDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<NameVarified>  queeryNameVarifiedData(String vln) throws Exception{
		//NameVarifiedSet result = new NameVarifiedSet();
		List<NameVarified> list = new ArrayList<NameVarified>();
		
//		String sql = "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %H:%i:%s') TIME,A.send_date,A.msisdn,A.vln,A.status "
//				+ "from CRM_DB.CRM_NAME_VERIFIED A "
//				+ "where A.vln = '"+cp.getVln()+"' "
				/*+ "union "
				+ "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %H:%i:%s') TIME,A.send_date,A.msisdn,A.vln,A.status "
				+ "from CRM_DB.CRM_NAME_VERIFIED A "
				+ "where A.msisdn in (select msisdn from CRM_DB.CRM_NAME_VERIFIED where serviceid = "+cp.getServiceid()+" )  "
				+ "union "
				+ "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %H:%i:%s') TIME,A.send_date,A.msisdn,A.vln,A.status "
				+ "from CRM_DB.CRM_NAME_VERIFIED A "
				+ "where A.vln in (select vln from CRM_DB.CRM_NAME_VERIFIED where serviceid = "+cp.getServiceid()+" )  "
				+ "union "
				+ "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %H:%i:%s') TIME,A.send_date,A.msisdn,A.vln,A.status "
				+ "from CRM_DB.CRM_NAME_VERIFIED A "
				+ "where A.msisdn = '"+cp.getMsisdn()+"' "
				+ "union "
				+ "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %H:%i:%s') TIME,A.send_date,A.msisdn,A.vln,A.status "
				+ "from CRM_DB.CRM_NAME_VERIFIED A "
				+ "where A.vln = '"+cp.getVln()+"'  "*/
//				+ "order by status desc,TIME desc ";
				//+ "and status = '1' ";
		
		
		String sql =""
				+ "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %H:%i:%s') TIME,A.send_date,A.msisdn,A.vln,A.status ,IFNULL(B.cou,0) cou "
				+ "from CRM_DB.CRM_NAME_VERIFIED A left outer join (select id,count(1) cou from CRM_DB.CRM_NAME_VERIFIED  where status = 1  group by id) B on   A.id = B.id  "
				+ "where A.vln = '"+vln+"' "
				+ "order by status desc,TIME desc ";
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn =getConn3();
			st = conn.createStatement();
			System.out.println("sql : "+sql);
			rs = st.executeQuery(sql);
			while(rs.next()){
				NameVarified c = new NameVarified();
				c.setServiceid(processData(rs.getString("SERVICEID")));
				c.setName(processData(rs.getString("NAME")));
				c.setId(processData(rs.getString("ID")));
				c.setType(processData(rs.getString("TYPE")));
				c.setRemark(processData(rs.getString("REMARK")));
				c.setCreateTime(processData(rs.getString("TIME")));
				c.setSendDate(processData(rs.getString("send_date")));
				c.setVln(processData(rs.getString("vln")));
				c.setMsisdn(processData(rs.getString("msisdn")));
				c.setStatus(rs.getString("status"));
				c.setUsedCount(rs.getString("cou"));
				list.add(c);
			}
			
			/*if(list.size()!=0){
				NameVarified c = list.remove(0);
				if("1".equals(c.getStatus())&& cp.getMsisdn().equals(c.getMsisdn())){
					result.setCurrent(c);
				}else{
					list.add(0, c);
				}
				
				result.setHistory(list);
			}
			
			if(result.getCurrent()!=null){
				sql = " select count(1) CD  from CRM_DB.CRM_NAME_VERIFIED where status = '1' and id = '"+result.getCurrent().getId()+"' ";
				System.out.println("sql : "+sql);
				rs = st.executeQuery(sql);
				if(rs.next()){
					result.getCurrent().setUsedCount(rs.getString("CD"));
				}
			}
			*/
			
		
		}finally{
			try {
				if(st!=null) st.close();
				if(rs!=null) rs.close();
				
			} catch (Exception e) { }
			closeConn3(conn);//closeConnection();
		}
		
		
		
		return list;
	}
	
	public List<NameVarified>  queeryNameVarifiedDataSameMsisdn(String msisdn) throws Exception{
		List<NameVarified> list = new ArrayList<NameVarified>();
		String sql =""
				+ "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %H:%i:%s') TIME,A.send_date,A.msisdn,A.vln,A.status ,IFNULL(B.cou,0) cou "
				+ "from CRM_DB.CRM_NAME_VERIFIED A left outer join (select id,count(1) cou from CRM_DB.CRM_NAME_VERIFIED  where status = 1  group by id) B on   A.id = B.id  "
				+ "where A.msisdn = '"+msisdn+"' "
				+ "order by status desc,TIME desc ";
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn =getConn3();
			st = conn.createStatement();
			System.out.println("sql : "+sql);
			rs = st.executeQuery(sql);
			while(rs.next()){
				NameVarified c = new NameVarified();
				c.setServiceid(processData(rs.getString("SERVICEID")));
				c.setName(processData(rs.getString("NAME")));
				c.setId(processData(rs.getString("ID")));
				c.setType(processData(rs.getString("TYPE")));
				c.setRemark(processData(rs.getString("REMARK")));
				c.setCreateTime(processData(rs.getString("TIME")));
				c.setSendDate(processData(rs.getString("send_date")));
				c.setVln(processData(rs.getString("vln")));
				c.setMsisdn(processData(rs.getString("msisdn")));
				c.setStatus(rs.getString("status"));
				c.setUsedCount(rs.getString("cou"));
				list.add(c);
			}
		
		}finally{
			try {
				if(st!=null) st.close();
				if(rs!=null) rs.close();
				
			} catch (Exception e) { }
			closeConn3(conn);//closeConnection();
		}
		return list;
	}
	
	public List<NameVarified> queeryNameVarifiedData(String input,String type) throws Exception{
		List<NameVarified> result = new ArrayList<NameVarified>();
		
		String sql = "select A.SERVICEID,A.NAME,A.ID,A.TYPE,A.REMARK,DATE_FORMAT(A.create_time,'%Y/%m/%d %H:%i:%s') TIME,A.send_date,A.msisdn,A.vln,A.status "
				+ "from CRM_DB.CRM_NAME_VERIFIED A "
				+ ("1".equals(type)?"where A.SERVICEID = "+input+" ":" ")
				+ ("2".equals(type)?"where A.NAME like '%"+input+"%' ":" ")
				+ ("3".equals(type)?"where A.ID like '%"+input+"%' ":" ")
				+ ("4".equals(type)?"where A.TYPE like '%"+input+"%' ":" ")
				+ ("5".equals(type)?"where A.send_date like '%"+input+"%' ":" ")
				+ ("6".equals(type)?"where A.msisdn like '%"+input+"%' ":" ")
				+ ("7".equals(type)?"where A.vln like '%"+input+"%' ":" ")
				+ "order by status desc,TIME desc ";
				//+ "and status = '1' ";
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn =getConn3();
			st = conn.createStatement();
			System.out.println("sql : "+sql);
			rs = st.executeQuery(sql);
			while(rs.next()){
				NameVarified c = new NameVarified();
				c.setServiceid(processData(rs.getString("SERVICEID")));
				c.setName(processData(rs.getString("NAME")));
				c.setId(processData(rs.getString("ID")));
				c.setType(processData(rs.getString("TYPE")));
				c.setRemark(processData(rs.getString("REMARK")));
				c.setCreateTime(processData(rs.getString("TIME")));
				c.setSendDate(processData(rs.getString("send_date")));
				c.setVln(processData(rs.getString("vln")));
				c.setMsisdn(processData(rs.getString("msisdn")));
				c.setStatus(rs.getString("status"));
				result.add(c);
			}
			
		
		}finally{
			try {
				if(st!=null) st.close();
				if(rs!=null) rs.close();
				
			} catch (Exception e) { }
			closeConn3(conn);//closeConnection();
		}
		return result;
	}
	
	public List<String> queryVLN(String serviceId) throws Exception{
		List<String> result = new ArrayList<String>(); 

		String sql = "SELECT A.VLN "
				+ "FROM VLNNUMBER A "
				+ "WHERE A.status = 1 AND A.SERVICEID = '"+serviceId+"' ";
				
		//String s2tIMSI = null,chtMsisdn = null;
		Statement st = null;
		ResultSet rs = null;
		Connection conn = getConn1();
		try {
			
			st = conn.createStatement();
			//System.out.println("sql:"+sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				result.add(rs.getString("VLN"));
			}
			
		} finally{
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
			} catch (Exception e) {
			}
			closeConn1(conn);
			//closeConnection();
		}
		return result;
	}
	
	public String  updateNameVarifiedData(NameVarified c) throws Exception{

		//查詢現行(status=1)的serviceID與中國號對應，如果不相符則新增資料
		String sql ;
		
		Statement st = null,st2 = null;
		//ResultSet rs = null;
		Connection conn = null,conn2 = null;
		boolean insertData = true;
		String result = "Success update {{num}} data!";
		try {
			conn =getConn3();
			//conn2 = getConn1();
			st = conn.createStatement();
			//st2 = conn2.createStatement();
			
			
			/* sql ="select count(1) CD from CRM_DB.CRM_NAME_VERIFIED where serviceid = "+c.getServiceid()+"  and status = '1' ";
			System.out.println("sql : "+sql);
			rs = st.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt("CD")!=0){
					insertData = false;
				}
			}
			rs.close();*/
			
			/*String vln = null,msisdn = null;
			//查詢中華門號
			sql = "select A.SERVICEID,B.VLN,A.Followmenumber "
					+ "from FOLLOWMEDATA A,VLNNUMBER B "
					+ "where A.serviceid = B.serviceid and B.VLN like '861%' and B.VLNTYPE = 1 and a.serviceid = "+c.getServiceid()+" ";
			
			rs = st2.executeQuery(sql);
			
			if(rs.next()){
				vln = rs.getString("VLN");
				msisdn = rs.getString("Followmenumber");
			}
			
			if(vln==null || "".equals(vln)||msisdn==null||"".equals(msisdn)){
				throw new Exception("查無有效的中國號或門號");
			}
			
			8525609nnnn->861841103nnnn
			8526640nnnn->861391048nnnn
			8526947nnnn->861391037nnn
			
			rs.close();*/
			
			/*if(insertData){
				//將過去的資料status設為0
				sql = "update CRM_DB.CRM_NAME_VERIFIED set status = '0' where serviceid = "+c.getServiceid()+" ";
				System.out.println("sql : "+sql);
				st.executeUpdate(sql);
				
				//insert 新資料
				sql = "insert into CRM_DB.CRM_NAME_VERIFIED(serviceid,name,id,type,remark,msisdn,vln) "
					+ "values("+c.getServiceid()+",'"+c.getName()+"','"+c.getId()+"','"+c.getType()+"','"+processData(c.getRemark()).trim()+"','"+c.getMsisdn()+"','"+c.getVln()+"')";
			}
			else*/
			
			
			
			sql = "update CRM_DB.CRM_NAME_VERIFIED set serviceid = "+c.getServiceid()+",name='"+c.getName()+"',id='"+c.getId()+"',type='"+c.getType()
			+"',remark='"+processData(c.getRemark()).trim()+"',msisdn='"+c.getMsisdn()+"'  "
					+ "where vln='"+c.getVln()+"'  and status = '1' ";
			
			System.out.println("sql : "+sql);
			result.replace("{{num}}", String.valueOf(st.executeUpdate(sql)));
			
		}finally{
			try {
				if(st!=null) st.close();
				//if(rs!=null) rs.close();

			} catch (Exception e) { }
			closeConn3(conn);//closeConnection();
		}

		return result;
	}
	
	public String  addNameVarifiedData(NameVarified c) throws Exception{

		String  sql;
		
		String sendDate = null;
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn =getConn3();
			st = conn.createStatement();
			
			sql = "select A.send_date DATE from CRM_DB.CRM_NAME_VERIFIED A where A.id = '"+c.getId()+"' and A.name = '"+c.getName()+"' and A.vln = '"+c.getVln()+"' and status = '1' ";
			System.out.println("sql : "+sql);
			rs =  st.executeQuery(sql);
			if(rs.next()){
				sendDate = rs.getString("DATE");
			}
			
			
			sql = "update CRM_DB.CRM_NAME_VERIFIED set status = '0' where vln = '"+c.getVln()+"' ";
			System.out.println("sql : "+sql);
			st.executeUpdate(sql);
			
			
			sql = "insert into CRM_DB.CRM_NAME_VERIFIED(serviceid,name,id,type,remark,msisdn,vln,send_date) "
					+ "values("+c.getServiceid()+",'"+c.getName()+"','"+c.getId()+"','"+c.getType()+"','"+processData(c.getRemark()).trim()+"','"+c.getMsisdn()+"','"+c.getVln()+"',"+(sendDate==null?null:" '"+sendDate+"' ")+")";
			System.out.println("sql : "+sql);
			st.executeUpdate(sql);
			
		}finally{
			try {
				if(st!=null) st.close();
				if(rs!=null) rs.close();
			} catch (Exception e) { }
			closeConn3(conn);//closeConnection();
		}

		return "SUCCESS";
	}
}

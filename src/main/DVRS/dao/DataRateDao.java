package main.DVRS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.DVRS.bean.DataRate;
import main.common.dao.BaseDao;
@Repository
public class DataRateDao extends BaseDao {
	
	public DataRateDao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

		//QueryList
		public List<DataRate> queryDataRateList() throws Exception{
			String sql=
					"SELECT A.PRICEPLANID , D.NAME||'('||D.ALIASES||')' PRICEPLANNAME,A.MCCMNC,B.COUNTRY, B.NETWORK, "
					+ "A.RATE, A.CHARGEUNIT, A.CURRENCY, (case when A.DAYCAP ='-1' then 'NA' else to_char(A.DAYCAP) END ) DAYCAP "
					+ "FROM HUR_DATA_RATE A, HUR_MCCMNC B, PRICEPLAN C ,PRICEPLAN_DETAIL D "
					+ "WHERE A.PRICEPLANID=C.PRICEPLANID AND A.MCCMNC=B.MCCMNC AND A.PRICEPLANID=D.PRICEPLANID "
					+ "AND A.PRICEPLANID=139 "
					+ "ORDER BY A.MCCMNC";
			
					/*"SELECT A.PRICEPLANID,A.MCCMNC,A.RATE,A.CHARGEUNIT,A.CURRENCY "
					+ "FROM HUR_DATA_RATE A";*/
			List<DataRate> list=new ArrayList<DataRate>();
			
				Statement st = getConn1().createStatement();
				ResultSet rs=st.executeQuery(sql);
				
				while(rs.next()){
					DataRate datarate =new DataRate();
					datarate.setPricePlanId(processEncodeData(rs.getString("PRICEPLANID"),"ISO-8859-1","BIG5"));
					datarate.setPricePlanName(processEncodeData(rs.getString("PRICEPLANNAME"),"ISO-8859-1","BIG5"));
					datarate.setMccmnc(rs.getString("MCCMNC"));
					datarate.setCountry(rs.getString("COUNTRY"));
					datarate.setNetWork(rs.getString("NETWORK"));
					datarate.setRate(rs.getDouble("RATE"));
					datarate.setChargeunit(rs.getLong("CHARGEUNIT"));
					datarate.setCurrency(rs.getString("CURRENCY"));
					datarate.setDayCap(rs.getString("DAYCAP"));
					list.add(datarate);
				}
				st.close();
				rs.close();	
				//closeConnection();
			return list;
			
		}

}

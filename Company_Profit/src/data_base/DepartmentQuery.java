package data_base;

import java.sql.*;

import exceptions.NameException;
import model.Company;
import model.Department;

public class DepartmentQuery {

	private static final String QUERY_DEPARTMENT = "SELECT * from DepartmentTable";

	public void loadDepartmentFromDB(Connection conn, Company comp) throws SQLException, NameException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(QUERY_DEPARTMENT);
		while (rs.next()) {
			comp.addDepartment(rs.getString("DName"), rs.getBoolean("Syncable"), rs.getBoolean("Changeable"),
					rs.getInt("StartingHr"), rs.getInt("EndingHr"));
		}
	}

	public void writeDepartment(Department d, Connection conn) throws SQLException, NameException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * From DepartmentTable where DName = '" + d.getName() + "'");
		if (rs.next() == false) {
			PreparedStatement pst1 = conn.prepareStatement("insert INTO DepartmentTable Values  (?, ?, ?, ?, ?, ?, ?)");
			pst1.setString(1, d.getName());
			pst1.setInt(2, d.getNumOfEmployees());
			pst1.setInt(3, d.getNumOfRoles());
			pst1.setBoolean(4, d.isChangable());
			pst1.setBoolean(5, d.isSyncable());
			pst1.setInt(6, d.getStartingHr());
			pst1.setInt(7, d.getEndingHr());
			pst1.execute();
		}
	}

	public void updateDepartment(Department d, Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs1 = stmt.executeQuery("select * from departmenttable where DName = " + "'" + d.getName() + "'");
		rs1.next();
		String set = "";
		if (d.getNumOfEmployees() != rs1.getInt("NumOfEmployees")) {
			set += "NumOfEmployees = " + d.getNumOfEmployees();
			if (d.isChangable() != rs1.getBoolean("Changeable") || d.isSyncable() != rs1.getBoolean("Syncable") || d.getNumOfRoles() != rs1.getInt("NumOfRoles") || d.getStartingHr() != rs1.getInt("StartingHr")) {
				set += ", ";
			}
		}
		if (d.isChangable() != rs1.getBoolean("Changeable")) {
			set += "Changeable = " + d.isChangable();
			if (d.getNumOfRoles() != rs1.getInt("NumOfRoles") || d.isSyncable() != rs1.getBoolean("Syncable") || d.getStartingHr() != rs1.getInt("StartingHr")) {
				set += ", ";
			}
		}
		if (d.isSyncable() != rs1.getBoolean("Syncable")) {
			set += "Syncable = " + d.isSyncable() ;
			if (d.getNumOfRoles() != rs1.getInt("NumOfRoles") || d.getStartingHr() != rs1.getInt("StartingHr")) 
				set += ", " ;
		}
		if (d.getNumOfRoles() != rs1.getInt("NumOfRoles")) {
			set += "NumOfRoles = " + d.getNumOfRoles();
			if(d.getStartingHr() != rs1.getInt("StartingHr"))
				set += ", " ;
		}
		if(d.getStartingHr() != rs1.getInt("StartingHr")){
			set +=  "StartingHr = " + d.getStartingHr() + ", EndingHr = " + d.getEndingHr();
		}

		

		if(set != ""){
			PreparedStatement pst = conn
				.prepareStatement("Update departmentTable Set " + set + " where DName = " + "'" + d.getName() + "'");
			pst.execute();
		}
	}
}

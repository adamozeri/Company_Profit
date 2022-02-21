package data_base;

import java.sql.*;

import model.Company;
import model.Department;
import model.Role;

public class RoleQuery {

	private static final String QUERY_ROLE = "SELECT * from Roletable Natural join departmentroletable;";

	public void loadRoles(Connection conn, Company comp) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs1 = stmt.executeQuery(QUERY_ROLE);

		while (rs1.next()) {
			String name = rs1.getString("RName");
			String dName = rs1.getString("DName");
			Department tempDep = null;
			for (int i = 0; i < comp.getNumOfDepartments(); i++) {
				if (comp.getAllDepartments().elementAt(i).getName().equals(dName)) {
					tempDep = comp.getAllDepartments().elementAt(i);
					break;
				}
			}
			comp.addRole(name, rs1.getBoolean("Changeable"), tempDep);
		}
		rs1.close();
	}

	public void writeRole(Role r, Connection conn) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * from Roletable where RName = '" + r.getName() + "'");
		if (rs.next() == false) {
			PreparedStatement pst1 = conn.prepareStatement("insert INTO RoleTable Values  (?, ?, ?)");
			pst1.setString(1, r.getName());
			pst1.setInt(2, r.getNumOfEmployees());
			pst1.setBoolean(3, r.isChangable());
			pst1.execute();
			
			writeDepartmentRole(r, conn);
		}
	}

	public void writeDepartmentRole(Role r, Connection conn) throws Exception{
		
		PreparedStatement pst1 = conn.prepareStatement("insert INTO DepartmentRoleTable Values (?, ?)");
		pst1.setString(1, r.getDepartment().getName());
		
		pst1.setString(2, r.getName());
		pst1.execute();	
		
		PreparedStatement pst2 = conn.prepareStatement(
				"Update DepartmentTable set NumOfRoles = " + r.getDepartment().getNumOfEmployees() + " where DName = (?)");
		
		pst2.setString(1, r.getDepartment().getName());

		pst2.execute();	
		
	}

	public void updateRole(Role r, Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs1 = stmt.executeQuery(
				"select * from Roletable Natural join departmentroletable where RName = '" + r.getName() + "'");
		rs1.next();
		String set = "";
		if (r.getNumOfEmployees() != rs1.getInt("NumOfEmployees")) {
			set += "NumOfEmployees = " + r.getNumOfEmployees();
			if (r.isChangable() != rs1.getBoolean("Changeable")) {
				set += ", ";
			}
		}
		if (r.isChangable() != rs1.getBoolean("Changeable")) {
			set += "Changeable = " + r.isChangable();
		}
		PreparedStatement pst = conn
				.prepareStatement("Update roletable Set " + set + " where RName = '" + r.getName() + "'");
		pst.execute();
	}
}

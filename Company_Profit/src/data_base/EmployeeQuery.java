package data_base;

import java.sql.*;

import model.BasicSalaryEmployee;
import model.Company;
import model.Department;
import model.Employee;
import model.HourlySalaryEmployee;
import model.BasicAndSalesEmployee;
import model.Employee.eEmployeeType;
import model.Role;

public class EmployeeQuery {

	private static final String QUERY_BASIC_EMPS = "SELECT * from EmployeeTable NATURAL JOIN BasicEmployeeTable";
	private static final String QUERY_HOURLY_EMPS = "SELECT * from EmployeeTable NATURAL JOIN HourlyEmployeeTable";
	private static final String QUERY_SALES_EMPS = "SELECT * from EmployeeTable NATURAL JOIN SalesEmployeeTable";

	public void loadAllEmployees(Connection conn, Company comp) throws Exception {
		loadBasicEmployee(conn, comp);
		loadHourlyEmployee(conn, comp);
		loadSalesEmployee(conn, comp);

	}

	public void writeEmployee(Connection conn, Employee e) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * from EmployeeTable where EID = " + "'" + e.getId() + "'");
		if (rs.next() == false) {
			PreparedStatement pst = conn.prepareStatement("insert INTO EmployeeTable Values (?, ?, ?)");
			pst.setString(1, e.getId());
			String fname = e.getName().split(" ")[0];
			String lname = e.getName().split(" ")[1];
			pst.setString(2, fname);
			pst.setString(3, lname);
			pst.execute();

			if (e instanceof BasicSalaryEmployee) {
				PreparedStatement pst1 = conn.prepareStatement("insert INTO BasicEmployeeTable Values  (?, ?)");
				pst1.setString(1, e.getId());
				pst1.setDouble(2, ((BasicSalaryEmployee) e).getMonthSalary());
				pst1.execute();
			}

			else if (e instanceof HourlySalaryEmployee) {
				PreparedStatement pst2 = conn.prepareStatement("insert INTO HourlyEmployeeTable(EID, Hourly_Payment, Working_Hours) Values (?,?,?)");
				pst2.setString(1, e.getId());
				pst2.setDouble(2, ((HourlySalaryEmployee) e).getHourlyPayment());
				pst2.setInt(3, HourlySalaryEmployee.MONTH_WORK_HR);
				pst2.execute();
			}

			else {
				PreparedStatement pst3 = conn.prepareStatement("insert INTO SalesEmployeeTable(EID, Month_Salary , Sales) Values  (?, ?, ?)");
				pst3.setString(1, e.getId());
				pst3.setDouble(2, ((BasicAndSalesEmployee) e).getMonthSalary());
				pst3.setDouble(3, ((BasicAndSalesEmployee) e).getSales());
				pst3.execute();
			}
			
		
			writeToPreference(conn, e);
			
			writeToDepartmentEmployee(conn, e);
			
			writeToRoleEmployee(conn, e);
			
		} 
	}

	public void writeToPreference(Connection conn, Employee e) throws SQLException {
		PreparedStatement pst1 = conn.prepareStatement("insert into PreferenceTable Values	 (?,?,?,?)");
		pst1.setString(1, e.getId());
		pst1.setInt(2, e.getStartingHr());
		pst1.setInt(3, e.getEndingHr());
		pst1.setInt(4, e.getPreference().getType().ordinal());
		pst1.execute();
	}

	public void writeToDepartmentEmployee(Connection conn, Employee e) throws SQLException {
		PreparedStatement pst1 = conn.prepareStatement("insert into DepartmentEmployeeTable Values (?,?)");
		pst1.setString(1, e.getDepartment().getName());
		pst1.setString(2, e.getId());
		PreparedStatement pst2 = conn.prepareStatement(
				"Update DepartmentTable set NumOfEmployees = " + e.getDepartment().getNumOfEmployees() + " where DName = (?)");
		pst2.setString(1, e.getDepartment().getName());
		pst1.execute();
		pst2.execute();
	}

	public void writeToRoleEmployee(Connection conn, Employee e) throws SQLException {
		PreparedStatement pst1 = conn.prepareStatement("insert into RoleEmployeeTable Values (?,?)");
		pst1.setString(1, e.getId());
		pst1.setString(2, e.getRole().getName());
		PreparedStatement pst2 = conn.prepareStatement(
				"Update Roletable set NumOfEmployees = " + e.getRole().getNumOfEmployees() + " where RName = (?)");
		pst2.setString(1, e.getRole().getName());
		pst1.execute();
		pst2.execute();

	}

	public void loadBasicEmployee(Connection conn, Company comp) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs1 = stmt.executeQuery(QUERY_BASIC_EMPS);

		while (rs1.next()) {
			BasicSalaryEmployee newBasicEmployee = new BasicSalaryEmployee();
			String name = rs1.getString("First_Name") + " " + rs1.getString("Last_Name");
			String id = rs1.getString("EID");
			Double mSalary = rs1.getDouble("Month_Salary");
			getGeneralEmployee(newBasicEmployee, id, conn, comp);
			comp.addEmployee(name, newBasicEmployee.getPreference(), newBasicEmployee.getRole(),
					newBasicEmployee.getDepartment(), id, mSalary, 0, eEmployeeType.BASIC);
		}
		rs1.close();
	}

	public void loadHourlyEmployee(Connection conn, Company comp) throws Exception {
		Statement stmt = conn.createStatement();

		ResultSet rs1 = stmt.executeQuery(QUERY_HOURLY_EMPS);

		while (rs1.next()) {
			HourlySalaryEmployee newHourlyEmployee = new HourlySalaryEmployee();
			String name = rs1.getString("First_Name") + " " + rs1.getString("Last_Name");
			String id = rs1.getString("EID");
			Double hourlyPayment = rs1.getDouble("Hourly_Payment");
			getGeneralEmployee(newHourlyEmployee, id, conn, comp);
			comp.addEmployee(name, newHourlyEmployee.getPreference(), newHourlyEmployee.getRole(),
					newHourlyEmployee.getDepartment(), id, hourlyPayment, 0, eEmployeeType.HOURLY_PAYMENT);
		}
		rs1.close();
	}

	public void loadSalesEmployee(Connection conn, Company comp) throws Exception {
		Statement stmt = conn.createStatement();

		ResultSet rs1 = stmt.executeQuery(QUERY_SALES_EMPS);

		while (rs1.next()) {
			BasicAndSalesEmployee newBnSEmployee = new BasicAndSalesEmployee();
			String name = rs1.getString("First_Name") + " " + rs1.getString("Last_Name");
			String id = rs1.getString("EID");
			Double mSalary = rs1.getDouble("Month_Salary");
			Double sales = rs1.getDouble("Sales");
			getGeneralEmployee(newBnSEmployee, id, conn, comp);
			comp.addEmployee(name, newBnSEmployee.getPreference(), newBnSEmployee.getRole(),
					newBnSEmployee.getDepartment(), id, mSalary, sales, eEmployeeType.BASIC_AND_SALES);
		}
		rs1.close();
	}

	public void getGeneralEmployee(Employee newEmployee, String id, Connection conn, Company comp) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs2 = stmt.executeQuery("SELECT * FROM Preferencetable WHERE EID = " + "'" + id + "'");
		rs2.next();
		newEmployee.setPreference(rs2.getInt("StartingHr"), rs2.getInt("EndingHr"), rs2.getInt("PID"));
		rs2 = stmt.executeQuery("SELECT * FROM DepartmentEmployeeTable WHERE EID = " + "'" + id + "'");
		rs2.next();
		String dName = rs2.getString("DName");
		Department tempDep = null;
		Role tempRole = null;
		for (int i = 0; i < comp.getNumOfDepartments(); i++) {
			if (comp.getAllDepartments().elementAt(i).getName().equals(dName)) {
				tempDep = comp.getAllDepartments().elementAt(i);
				break;
			}
		}
		if (tempDep != null)
			newEmployee.setDepartment(tempDep);
		rs2 = stmt.executeQuery("SELECT * FROM roleemployeetable WHERE EID = " + "'" + id + "'");
		rs2.next();
		String rName = rs2.getString("RName");
		for (int i = 0; i < comp.getNumOfRoles(); i++) {
			if (comp.getAllRoles().elementAt(i).getName().equals(rName)) {
				tempRole = comp.getAllRoles().elementAt(i);
				break;
			}
		}
		if (tempRole != null)
			newEmployee.setRole(tempRole);
		stmt.close();
	}

	public void updateBasicEmployee(Connection conn, BasicSalaryEmployee e) throws SQLException {
		updateEmployeePref(conn, e);
		Statement stmt = conn.createStatement();
		ResultSet rs1 = stmt.executeQuery("select * from basicEmployeetable where EID = " + "'" + e.getId() + "'");
		String set = "";
		rs1.next();
		if (e.getMonthSalary() != rs1.getDouble("Month_Salary")) {
			set += "Month_Salary = " + e.getMonthSalary();
		}
		if(set != ""){
			PreparedStatement pst = conn
					.prepareStatement("Update basicEmployeetable Set " + set + " where EID = " + "'" + e.getId() + "'");
				pst.execute();
		}
	}

	public void updateHourlyPayment(Connection conn, HourlySalaryEmployee e) throws SQLException {
		updateEmployeePref(conn, e);
		Statement stmt = conn.createStatement();
		ResultSet rs1 = stmt.executeQuery("select * from HourlyEmployeeTable where EID = " + "'" + e.getId() + "'");
		String set = "";
		rs1.next();

		if (e.getHourlyPayment() != rs1.getDouble("Hourly_Payment")) {
			set += "Hourly_Payment = " + e.getHourlyPayment();
		}
		if(set != ""){
			PreparedStatement pst = conn
				.prepareStatement("Update HourlyEmployeeTable Set " + set + " where EID = " + "'" + e.getId() + "'");
			pst.execute();	
		}
	}

	public void updateSalesEmployee(Connection conn, BasicAndSalesEmployee e) throws SQLException {
		updateEmployeePref(conn, e);
		Statement stmt = conn.createStatement();
		ResultSet rs1 = stmt.executeQuery("select * from SalesEmployeeTable where EID = " + "'" + e.getId() + "'");
		String set = "";
		rs1.next();
		if (e.getMonthSalary() != rs1.getDouble("Month_Salary")) {
			set += "Month_Salary = " + e.getMonthSalary();
			if (e.getSales() != rs1.getDouble("Sales")) {
				set += ", ";
			}
		}
		if (e.getSales() != rs1.getDouble("Sales")) {
			set += "Sales = " + e.getSales();
		}
		if(set != ""){
			PreparedStatement pst = conn
					.prepareStatement("Update SalesEmployeeTable Set " + set + " where EID = " + "'" + e.getId() + "'");
			pst.execute();
		}
	}
	
	public void updateEmployeePref(Connection conn, Employee e) throws SQLException {
		Statement stmt = conn.createStatement();
		
		ResultSet rs1 = stmt.executeQuery("select * from PreferenceTable where EID = " + "'" + e.getId() + "'");
		
		String set = "";
		rs1.next();
		if (e.getPreference().getType().ordinal() != rs1.getInt("PID")) {
			set += "PID = " + e.getPreference().getType().ordinal();
			if (e.getPreference().getStartingHr() != rs1.getInt("StartingHr")) {
				set += ", ";
			}
		}
		if (e.getPreference().getEndingHr() != rs1.getInt("EndingHr")) {
			set += "EndingHr = " + e.getPreference().getEndingHr() + ", " + "StartingHr = " + e.getPreference().getStartingHr();
		}

		if(set != ""){
			PreparedStatement pst = conn
				.prepareStatement("Update PreferenceTable Set " + set + " where EID = " + "'" + e.getId() + "'");
		
			pst.execute();	
		}
		
	}

	public void deleteEmployee(Connection conn, Employee e) throws SQLException {
		PreparedStatement pst = conn.prepareStatement("delete from Preferencetable where EID = " + "'" + e.getId() + "'");
		pst.execute();
		
		pst = conn.prepareStatement("delete from RoleEmployeetable where EID = " + "'" + e.getId() + "'");
		pst.execute();

		pst = conn.prepareStatement("delete from DepartmentEmployeeTable where EID = " + "'" + e.getId() + "'");
		pst.execute();
		if (e instanceof BasicSalaryEmployee) {
			pst = conn.prepareStatement("delete from basicEmployeetable where EID = " + "'" + e.getId() + "'");
		}
		if (e instanceof HourlySalaryEmployee) {
			pst = conn.prepareStatement("delete from hourlyemployeetable where EID = " + "'" + e.getId() + "'");
		}
		if (e instanceof BasicAndSalesEmployee) {
			pst = conn.prepareStatement("delete from salesemployeetable where EID = " + "'" + e.getId() + "'");
		}
		pst.execute();
		pst = conn.prepareStatement("delete from employeetable where EID = " + "'" + e.getId() + "'");
		pst.execute();
		pst.close();
	}
}

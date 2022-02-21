package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import data_base.DepartmentQuery;
import data_base.EmployeeQuery;
import data_base.RoleQuery;
import exceptions.NameException;
import listeners.CompanyListenable;
import model.Employee.eEmployeeType;
import model.Preference.eType;

public class Company{

	private transient Vector<CompanyListenable> allListeners;
	private Vector<Department> allDepartments;
	private Vector<Employee> allEmployees;
	private Vector<Role> allRoles;
	private int numOfDepartments;
	private int numOfEmployees;
	private int numOfRoles;
	private double avgProfit;
	private static EmployeeQuery eQuery = new EmployeeQuery();
	private static DepartmentQuery dQuery = new DepartmentQuery();
	private static RoleQuery rQuery = new RoleQuery();

	private Connection conn;

	public Company(Connection conn) {
		this.allListeners = new Vector<CompanyListenable>();
		this.allDepartments = new Vector<Department>();
		this.allEmployees = new Vector<Employee>();
		this.allRoles = new Vector<Role>();
		this.numOfDepartments = 0;
		this.numOfEmployees = 0;
		this.numOfRoles = 0;
		this.conn = conn;
	}

	public void registerListener(CompanyListenable l) {
		allListeners.add(l);
	}

	public boolean addEmployee(String name, Preference preference, Role role, Department department, String id,
			double salary, double sales, eEmployeeType type) throws Exception{

		for (int i = 0; i < numOfEmployees; i++) {
			if (allEmployees.elementAt(i).getId().equals(id)) {
				return false;
			}
		}
		Employee newEmployee = null;
		if (type == eEmployeeType.BASIC) {
			newEmployee = new BasicSalaryEmployee(name, preference, role, department, id, salary, type);
			allEmployees.add(newEmployee);
		} else if (type == eEmployeeType.BASIC_AND_SALES) {
			newEmployee = new BasicAndSalesEmployee(name, preference, role, department, id, salary, sales, type);
			allEmployees.add(newEmployee);
		} else {
			newEmployee = new HourlySalaryEmployee(name, preference, role, department, id, salary, type);
			allEmployees.add(newEmployee);
		}
		numOfEmployees++;
		for (int i = 0; i < numOfRoles; i++) {
			if (allRoles.elementAt(i).equals(role)) {
				allRoles.elementAt(i).addEmployee(newEmployee);
			}
		}
		for (int i = 0; i < numOfDepartments; i++) {
			if (allDepartments.elementAt(i).equals(department)) {
				allDepartments.elementAt(i).addEmployee(newEmployee);
				allDepartments.elementAt(i).change();
				allDepartments.elementAt(i).sync(allDepartments.elementAt(i).getStartingHr(),
						allDepartments.elementAt(i).getEndingHr());
			}
		}
		eQuery.writeEmployee(conn, newEmployee);
		return true;
	}
	
	public void setAvgProfit(double avgProfit) throws Exception {
		if (avgProfit >= 1000000) {
			throw new Exception("Average profit must be under 7 digits.");
		}
		if (avgProfit < 0) {
			throw new Exception("Average profit must be a positive number.");
		}
		this.avgProfit = avgProfit;
	}

	public boolean addDepartment(String name, boolean isSyncable, boolean isChangable, int startingHr, int endingHr)
			throws NameException, SQLException {
		for (int i = 0; i < numOfDepartments; i++) {
			if (allDepartments.elementAt(i).getName().toLowerCase().equals(name.toLowerCase())) {
				return false;
			}
		}
		Department newDepartment = new Department(name, isSyncable, isChangable, startingHr, endingHr);
		allDepartments.add(newDepartment);
		numOfDepartments++;
		dQuery.writeDepartment(newDepartment, conn);
		return true;
	}

	public boolean addRole(String name, boolean isChangable, Department department) throws Exception {
		for (int i = 0; i < numOfRoles; i++) {
			if (allRoles.elementAt(i).getName().toLowerCase().equals(name.toLowerCase())) {
				return false;
			}
		}
		Role newRole = new Role(name, isChangable, department);
		allRoles.add(newRole);
		numOfRoles++;
		for (int i = 0; i < numOfDepartments; i++) {
			if (allDepartments.elementAt(i).equals(department)) {
				allDepartments.elementAt(i).addRole(newRole);
			}
		}
		rQuery.writeRole(newRole, conn);
		return true;
	}

	public String printDepartments() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < numOfDepartments; i++) {
			sb.append(allDepartments.elementAt(i)).append("\n\n");
		}
		return sb.toString();
	}

	public String printRoles() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < numOfRoles; i++) {
			sb.append(allRoles.elementAt(i)).append("\n\n");
		}
		return sb.toString();
	}

	public String printEmployees() {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < numOfEmployees; i++) {
			sb.append(allEmployees.elementAt(i)).append("\n\n");
		}
		return sb.toString();
	}

	public Vector<Department> getAllDepartments() {
		return allDepartments;
	}

	public Vector<Role> getAllRoles() {
		return allRoles;
	}

	public Vector<Employee> getAllEmployees() {
		return allEmployees;
	}

	public void setAllEmployees(Vector<Employee> allEmployees) {
		this.allEmployees = allEmployees;
	}

	public int getNumOfDepartments() {
		return numOfDepartments;
	}

	public void setNumOfDepartments(int numOfDepartments) {
		this.numOfDepartments = numOfDepartments;
	}

	public int getNumOfEmployees() {
		return numOfEmployees;
	}

	public void setNumOfEmployees(int numOfEmployees) {
		this.numOfEmployees = numOfEmployees;
	}

	public int getNumOfRoles() {
		return numOfRoles;
	}

	public void setNumOfRoles(int numOfRoles) {
		this.numOfRoles = numOfRoles;
	}

	public double getAvgProfit() {
		return avgProfit;
	}

	public void setAllRoles(Vector<Role> allRoles) {
		this.allRoles = allRoles;
	}

	public void setAllDepartments(Vector<Department> allDepartments) {
		this.allDepartments = allDepartments;
	}

	public void deleteEmployee(Employee e) throws SQLException{
		this.numOfEmployees--;
		e.getDepartment().setNumOfEmployees(e.getDepartment().getNumOfEmployees() - 1);
		e.getRole().setNumOfEmployees(e.getRole().getNumOfEmployees() - 1);
		dQuery.updateDepartment(e.getDepartment(), conn);
		rQuery.updateRole(e.getRole(), conn);
		eQuery.deleteEmployee(conn, e);
		this.allEmployees.remove(e);
	}
	
	public void updateRoles(String name, boolean isChangable) throws SQLException {
		Role tempR = null;
		for (int i = 0; i < numOfRoles; i++) {
			if (allRoles.elementAt(i).getName().equals(name)) {
				allRoles.elementAt(i).setChangable(isChangable);
				tempR = allRoles.elementAt(i);
				if (!allRoles.elementAt(i).getDepartment().isSyncable()) {
					allRoles.elementAt(i).change();
					break;
				}
			}
		}
		System.out.println(tempR);
		rQuery.updateRole(tempR, conn);
	}

	public void updateEmployee(Employee e) throws SQLException {
		if(e instanceof BasicSalaryEmployee){
			eQuery.updateBasicEmployee(conn,(BasicSalaryEmployee) e);
		}
		else if(e instanceof BasicAndSalesEmployee){
		
			eQuery.updateSalesEmployee(conn,(BasicAndSalesEmployee) e);
		}
		else if(e instanceof HourlySalaryEmployee){
			eQuery.updateHourlyPayment(conn, (HourlySalaryEmployee)e);
		}

	}

	public void updateDepartments(String name, boolean isChangable, boolean isSyncable, int strHr, int endingHr) throws SQLException {
		Department tempD = null;
		for (int i = 0; i < numOfDepartments; i++) {
			if (allDepartments.elementAt(i).getName().equals(name)) {
				allDepartments.elementAt(i).setSyncable(isSyncable);
				allDepartments.elementAt(i).setChangable(isChangable);
				if (isSyncable) {
					allDepartments.elementAt(i).setStartingHr(strHr);
					allDepartments.elementAt(i).setEndingHr(endingHr);
					allDepartments.elementAt(i).sync(allDepartments.elementAt(i).getStartingHr(),
							allDepartments.elementAt(i).getEndingHr());
					allDepartments.elementAt(i).setChangable(false);
				} else if (isChangable) {
					allDepartments.elementAt(i).change();
				}
				tempD = allDepartments.elementAt(i);
				break;
			}
		}
		dQuery.updateDepartment(tempD, conn);
	}

	public String employeeRevenue() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < numOfEmployees; i++) {
			allEmployees.elementAt(i).employeeRevenue();
			sb.append(i + 1).append(") ").append("Name: ").append(allEmployees.elementAt(i).getName())
					.append("\nPrefernce:  ");
			if (allEmployees.elementAt(i).getPreference().getType() != eType.Home) {
				sb.append(allEmployees.elementAt(i).getPreference()).append("\nActual work hours: ")
						.append(allEmployees.elementAt(i).getStartingHr()).append(":00 - ")
						.append(allEmployees.elementAt(i).getEndingHr()).append(":00");
			} else {
				sb.append(allEmployees.elementAt(i).getPreference().getType());
			}
			sb.append("\nProfit Hours: ").append(allEmployees.elementAt(i).getHourProfit()).append("\n\n");
		}
		return sb.toString();
	}

	public String departmentRevenue() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < numOfDepartments; i++) {
			sb.append(i + 1).append(") ").append(allDepartments.elementAt(i).getName()).append("\n");
			double departmentProfit = 0;
			for (int j = 0; j < allDepartments.elementAt(i).getNumOfEmployees(); j++) {
				allDepartments.elementAt(i).getAllEmployees().elementAt(j).employeeRevenue();
				departmentProfit += allDepartments.elementAt(i).getAllEmployees().elementAt(j).getHourProfit();
			}
			sb.append("Profit Hours: ").append(departmentProfit).append("\n\n");
		}
		return sb.toString();
	}

	public String companyRevenue() {
		StringBuffer sb = new StringBuffer();
		double companyRevenue = 0;
		for (int i = 0; i < numOfEmployees; i++) {
			allEmployees.elementAt(i).employeeRevenue();
			companyRevenue += allEmployees.elementAt(i).getHourProfit() * this.avgProfit;
		}
		sb.append(companyRevenue).append("$");
		return sb.toString();
	}
	
	public void loadDB() throws Exception {
		dQuery.loadDepartmentFromDB(conn, this);
		rQuery.loadRoles(conn, this);
		eQuery.loadAllEmployees(conn, this);
	}	
	

	public void setValues(Company c1) throws Exception {
		setAllDepartments(c1.getAllDepartments());
		setAllRoles(c1.getAllRoles());
		setAllEmployees(c1.getAllEmployees());
		setNumOfDepartments(c1.getNumOfDepartments());
		setNumOfRoles(c1.getNumOfRoles());
		setNumOfEmployees(c1.getNumOfEmployees());
		setAvgProfit(c1.getAvgProfit());
	}
	
	public Connection getConn() {
		return this.conn;
	}
}

package controller;

import java.sql.SQLException;
import java.util.Vector;

import exceptions.NameException;
import listeners.CompanyListenable;
import listeners.ViewListenable;
import model.Company;
import model.Department;
import model.Employee;
import model.Employee.eEmployeeType;
import model.Preference;
import model.Role;
import view.View;

public class Controller implements ViewListenable, CompanyListenable{
	
	private Company theModel;
	private View theView;
	
	public Controller(Company model, View view) {
		this.theModel = model;
		this.theView = view;
		
		theModel.registerListener(this);
		theView.registerListener(this);
	}
	
	public String viewShowEmployees(){
		return theModel.printEmployees();
	}


	@Override
	public String showEmployees() {
		return theModel.printEmployees();
	}

	@Override
	public boolean addEmployee(String name, Preference preference, Role role, Department department, String id, double salary, double sales, eEmployeeType type) throws NameException, Exception {
		return theModel.addEmployee(name, preference, role, department, id, salary, sales, type);
	}
	
	@Override
	public Vector<Department> getAllDepartments(){
		return theModel.getAllDepartments();
	}

	@Override
	public boolean addRole (String name, Boolean isChangable, Department department) throws Exception {
	return theModel.addRole(name, isChangable, department);
	}

	@Override
	public boolean addDepartment(String name, boolean isSyncable, boolean isChangable, int startingHr, int endingHr) throws NameException, SQLException {
		return theModel.addDepartment(name, isSyncable, isChangable, startingHr, endingHr);
	}

	@Override
	public String showRoles() {
		return theModel.printRoles();
	}

	@Override
	public String showDeparments() {
		return theModel.printDepartments();
	}

	@Override
	public void updateRoles(String name, boolean isChangable) throws SQLException {
		theModel.updateRoles(name, isChangable);
	}

	@Override
	public Vector<Role> getAllRoles() {
		return theModel.getAllRoles();
	}

	@Override
	public void updateDepartments(String name, boolean isChangable, boolean isSyncable, int strHr, int endingHr) throws SQLException {
		theModel.updateDepartments(name, isChangable, isSyncable, strHr, endingHr);
	}

	@Override
	public String showEmployeeRevenue() {
		return theModel.employeeRevenue();
	}

	@Override
	public String showDepartmentRevenue() {
		return theModel.departmentRevenue();
	}

	@Override
	public String showCompanyRevenue() {
		return theModel.companyRevenue();
	}
	
	@Override
	public void setAvgProfit(double avgProfit) throws Exception {
		theModel.setAvgProfit(avgProfit);
	}

	public void loadFromDB()throws Exception{
		theModel.loadDB();
	}

	@Override
	public void deleteEmployee(Employee e) throws SQLException {
		theModel.deleteEmployee(e);
	}

	@Override
	public void updateEmployee(Employee e) throws SQLException {
		theModel.updateEmployee(e);
	}

	@Override
	public Vector<Employee> getEmployess() {
		return theModel.getAllEmployees();
	}

	@Override
	public void closeConn() throws SQLException {
		theModel.getConn().close();
	}

}

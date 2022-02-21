package listeners;

import java.sql.SQLException;
import java.util.Vector;
import exceptions.NameException;
import model.Department;
import model.Employee;
import model.Employee.eEmployeeType;
import model.Preference;
import model.Role;

public interface ViewListenable {
	boolean addEmployee(String name, Preference preference, Role role, Department department, String id, double salary,
			double sales, eEmployeeType type) throws NameException, Exception;

	boolean addRole(String name, Boolean isChangable, Department department) throws NameException, Exception;

	boolean addDepartment(String name, boolean isSyncable, boolean isChangable, int startingHr, int endingHr)
			throws NameException, SQLException;

	Vector<Department> getAllDepartments();

	Vector<Role> getAllRoles();

	String showEmployees();

	String showRoles();

	String showDeparments();

	void updateRoles(String name, boolean isChangable) throws SQLException;

	void updateDepartments(String name, boolean isChangable, boolean isSyncable, int strHr, int endingHr) throws SQLException;

	String showEmployeeRevenue();

	String showDepartmentRevenue();

	String showCompanyRevenue();

	void setAvgProfit(double avgProfit) throws Exception;

	void loadFromDB()throws Exception;

	void deleteEmployee(Employee e)throws SQLException;	

	Vector<Employee> getEmployess();

	void updateEmployee(Employee e)throws SQLException;
	
	void closeConn() throws SQLException;

}

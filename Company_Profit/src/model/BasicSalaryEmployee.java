package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BasicSalaryEmployee extends Employee implements Serializable {

	private double monthSalary;
	
	public BasicSalaryEmployee(String name, Preference preference, Role role, Department department, String id,
			double monthSalary, eEmployeeType type) throws Exception {
		super(name, preference, role, department, id, type);
		setMonthSalary(monthSalary);
	}

	public BasicSalaryEmployee(){

	}

	public void setMonthSalary(double monthSalary) throws Exception {
		if (monthSalary < 0) {
			throw new Exception("Salary must be positive");
		}
		if (monthSalary > 1000000) {
			throw new Exception("Salary must be under 7 digits");
		}
		this.monthSalary = monthSalary;
	}
	
	public double getMonthSalary() {
		return monthSalary;
	}
	@Override
	public String toString() {
		return super.toString() + "\nSalary: " + monthSalary + "$";
	}
}

package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HourlySalaryEmployee extends Employee implements Serializable {

	public static final int MONTH_WORK_HR = 160;
	private double monthSalary;
	private double hourlyPayment;

	public HourlySalaryEmployee(String name, Preference preference, Role role, Department department, String id,
			double hourlyPayment, eEmployeeType type) throws Exception {
		super(name, preference, role, department, id, type);
		this.hourlyPayment = hourlyPayment;
		setHourlyPayment(hourlyPayment);
		this.monthSalary = hourlyPayment * MONTH_WORK_HR;
	}


	public HourlySalaryEmployee(){
		
	}
	public double getHourlyPayment(){
		return hourlyPayment;
	}

	public void setHourlyPayment(double hourlyPayment) throws Exception {
		if (hourlyPayment < 0) {
			throw new Exception("Hourly Payment must be positive");
		}
		if (hourlyPayment > 10000) {
			throw new Exception("Hourly Payment must be under 5 digits");
		}
		this.hourlyPayment = hourlyPayment;
		setMonthSalary(hourlyPayment);
	}
	
	public void setMonthSalary(double monthSalary) throws Exception {
		this.monthSalary = this.hourlyPayment * MONTH_WORK_HR;
	}
	
	public double getMonthSalary() {
		return monthSalary;
	}
	@Override
	public String toString() {
		return super.toString() + "\nSalary: " + monthSalary + "$";
	}
}

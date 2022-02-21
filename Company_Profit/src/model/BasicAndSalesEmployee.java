package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BasicAndSalesEmployee extends Employee implements Serializable{

	private double monthSalary;
	private double sales;
	private double percentOfSales;

	public BasicAndSalesEmployee(String name, Preference preference, Role role, Department department, String id,
			double monthSalary, double sales, eEmployeeType type) throws Exception {
		super(name, preference, role, department, id, type);
		setSales(sales);
		setPercentOfSales(this.sales);
		setMonthSalary(monthSalary);
	}
	
	public BasicAndSalesEmployee(){
		
	}

	public void setMonthSalary(double monthSalary) throws Exception {
		if (monthSalary < 0) {
			throw new Exception("Salary must be positive");
		}
		if (monthSalary > 1000000) {
			throw new Exception("Salary must be under 7 digits");
		}
		this.monthSalary = monthSalary + percentOfSales;
	}

	public double getPercentOfSales() {
		return percentOfSales;
	}
	
	public double getMonthSalary() {
		return monthSalary;
	}
	
	public double getSales() {
		return sales;
	}
	
	public void setSales(double sales) throws Exception {
		if (sales < 0) {
			throw new Exception("Sales must be positive");
		}
		if (sales > 1000000) {
			throw new Exception("Sales must be under 7 digits");
		}
		this.sales = sales;
	}

	public void setPercentOfSales(double sales) {
		this.percentOfSales = (sales / 100);
	}
	

	@Override
	public String toString() {
		return super.toString() + "\nSalary: " + monthSalary + "$" + "\nBonus from Sales: " + percentOfSales + "$";
	}
}

package model;

import java.io.Serializable;
import java.util.Vector;

import exceptions.NameException;

@SuppressWarnings("serial")
public class Role implements Syncable, Changable, Serializable {

	private Vector<Employee> allEmployees;
	private int numOfEmployees;
	private String name;
	private boolean isChangable;
	private Department department;

	public Role(String name, boolean isChangable, Department department) throws NameException {
		this.allEmployees = new Vector<Employee>();
		setName(name);
		this.isChangable = isChangable;
		this.numOfEmployees = 0;
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws NameException {
		String temp = name.toLowerCase();
		for (int i = 0; i < temp.length(); i++) {
			if ((temp.charAt(i) != ' ' && temp.charAt(i) < 'a') || temp.charAt(i) > 'z') {
				throw new NameException();
			}
		}
		this.name = name;
	}

	public void addEmployee(Employee newEmployee) {
		allEmployees.add(newEmployee);
		numOfEmployees++;
	}

	public Department getDepartment() {
		return department;
	}

	public int getNumOfEmployees(){
		return this.numOfEmployees;
	}

	public boolean isChangable() {
		return isChangable;
	}

	public void setChangable(boolean isChangable) {
		this.isChangable = isChangable;
	}

	@Override
	public boolean change() {
		if (isChangable) {
			for (int i = 0; i < numOfEmployees; i++) {
				allEmployees.elementAt(i).change();
			}
			return true;
		}
		else {
			for (int i = 0; i < numOfEmployees; i++) {
				allEmployees.elementAt(i).setStartingHr(8);
				allEmployees.elementAt(i).setEndingHr(17);
				allEmployees.elementAt(i).setHourProfit(0);
			}
		}
		return false;
	}

	@Override
	public boolean sync(int startingHr, int endingTime) {
		for (int i = 0; i < numOfEmployees; i++) {
			allEmployees.elementAt(i).sync(startingHr, endingTime);
		}
		return true;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Role)) {
			return false;
		}
		Role tempE = (Role) other;
		return this.name.toLowerCase().equals(tempE.name.toLowerCase());
	}

	public void setNumOfEmployees(int num){
		this.numOfEmployees = num;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Role: ").append(name).append("\nCan be changed: ").append(isChangable).append("\n")
				.append("Employees: \n");
		for (int i = 0; i < numOfEmployees; i++) {
			sb.append("\t").append(i + 1).append(") ").append(allEmployees.elementAt(i).getName()).append("\n");
		}
		return sb.toString();
	}

}

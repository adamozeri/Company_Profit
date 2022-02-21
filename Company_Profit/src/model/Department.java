package model;

import java.io.Serializable;
import java.util.Vector;

import exceptions.NameException;

@SuppressWarnings("serial")
public class Department implements Syncable, Changable, Serializable {

	private Vector<Role> allRoles;
	private Vector<Employee> allEmployees;
	private String name;
	private int numOfRoles;
	private int numOfEmployees;
	private boolean isSyncable;
	private boolean isChangable;
	private int startingHr;
	private int endingHr;

	public Department(String name, boolean isSyncable, boolean isChangable, int startingHr, int endingHr)
			throws NameException {
		setName(name);
		this.allRoles = new Vector<Role>();
		this.allEmployees = new Vector<Employee>();
		this.numOfRoles = 0;
		this.numOfEmployees = 0;
		this.isChangable = isChangable;
		this.isSyncable = isSyncable;
		this.startingHr = startingHr;
		this.endingHr = endingHr;
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

	public boolean isSyncable() {
		return isSyncable;
	}

	public boolean isChangable() {
		return isChangable;
	}

	public int getStartingHr() {
		return startingHr;
	}

	public int getEndingHr() {
		return endingHr;
	}

	public void setStartingHr(int startingHr) {
		this.startingHr = startingHr;
	}

	public void setEndingHr(int endingHr) {
		this.endingHr = endingHr;
	}

	@Override
	public boolean change() {
		if (isChangable) {
			for (int i = 0; i < numOfRoles; i++) {
				allRoles.elementAt(i).change();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean sync(int startingHr, int endingTime) {
		if (isSyncable) {
			for (int i = 0; i < numOfRoles; i++) {
				allRoles.elementAt(i).sync(startingHr, endingTime);
			}
			return true;
		}
		return false;
	}

	public Vector<Role> getAllRoles() {
		return allRoles;
	}

	public void setSyncable(boolean isSyncable) {
		this.isSyncable = isSyncable;
	}

	public void setChangable(boolean isChangable) {
		this.isChangable = isChangable;
	}

	public int getNumOfRoles() {
		return numOfRoles;
	}

	public int getNumOfEmployees() {
		return numOfEmployees;
	}

	public Vector<Employee> getAllEmployees() {
		return allEmployees;
	}

	public Vector<Employee> getEmployees() {
		return allEmployees;
	}

	public void addRole(Role newRole) {
		allRoles.add(newRole);
		numOfRoles++;
	}

	public void setNumOfEmployees(int num){
		this.numOfEmployees = num;
	}

	public void addEmployee(Employee newEmployee) {
		allEmployees.add(newEmployee);
		numOfEmployees++;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Department)) {
			return false;
		}
		Department tempE = (Department) other;
		return this.name.toLowerCase().equals(tempE.name.toLowerCase());
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Department: ").append(name).append("\nCan be changed: ").append(isChangable).append("\nSync: ")
				.append(isSyncable);
		if (isSyncable) {
			sb.append(" Work Time: ").append(startingHr).append(":00 - ").append(endingHr).append(":00");
		}
		sb.append("\n").append("Roles:\n");
		for (int i = 0; i < numOfRoles; i++) {
			sb.append("\t").append(i + 1).append(") ").append(allRoles.elementAt(i).getName()).append("\n");
		}
		sb.append("\nEmployees:\n");
		for (int i = 0; i < numOfEmployees; i++) {
			sb.append("\t").append(i + 1).append(") ").append(allEmployees.elementAt(i).getName()).append("\n");
		}
		return sb.toString();
	}

}

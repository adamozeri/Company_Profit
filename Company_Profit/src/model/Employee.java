package model;

import java.io.Serializable;
import exceptions.NameException;
import model.Preference.eType;


@SuppressWarnings("serial")
public abstract class Employee implements Syncable, Changable, Serializable {

	public enum eEmployeeType {
		BASIC, BASIC_AND_SALES, HOURLY_PAYMENT
	}

	protected eEmployeeType type;

	protected String id;
	protected String name;
	protected Preference preference;
	protected int startingHr;
	protected int endingHr;
	protected double hourProfit;
	protected Role role;
	protected Department department;

	public Employee(String name, Preference preference, Role role, Department department, String id, eEmployeeType type)
			throws NameException, Exception {
		setName(name);
		this.preference = preference;
		this.startingHr = 8;
		this.endingHr = 17;
		this.hourProfit = 0;
		this.role = role;
		this.department = department;
		setId(id);
		this.type = type;

	}

	public Employee(){
	}
	
	
	public eEmployeeType getEmployeeType() {
		return this.type; 
	}
	@Override
	public boolean change() {
		setStartingHr(preference.getStartingHr());
		setEndingHr(preference.getEndingHr());
		return true;
	}

	@Override
	public boolean sync(int startingHr, int endingTime) {
		setStartingHr(startingHr);
		setEndingHr(endingTime);
		employeeRevenue();
		return true;
	}

	public void setHourProfit(double hourProfit) {
		this.hourProfit = hourProfit;
	}

	public void setName(String name) throws NameException {
		if(name.split(" ").length <= 1)
			throw new NameException("Must have first name and last name");
		String temp = name.toLowerCase();
		for (int i = 0; i < temp.length(); i++) {
			if ((temp.charAt(i) != ' ' && temp.charAt(i) < 'a') || temp.charAt(i) > 'z') {
				throw new NameException();
			}
		}
		this.name = name;
	}

	public void setId(String id) throws Exception {
		if (id.length() != 9) {
			throw new Exception("Invalid ID input. please enter 9 digits.");
		}
		for (int i = 0; i < id.length(); i++) {
			if (!(id.charAt(i) >= '0' && id.charAt(i) <= '9')) {
				throw new Exception("ID must be numbers.");
			}
		}
		this.id = id;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Department getDepartment() {
		return department;
	}

	public Preference getPreference() {
		return preference;
	}

	public int getStartingHr() {
		return startingHr;
	}

	public void setStartingHr(int startingHr) {
		this.startingHr = startingHr;
	}

	public int getEndingHr() {
		return endingHr;
	}

	public void setEndingHr(int endingHr) {
		this.endingHr = endingHr;
	}

	public void setRole(Role role){
	    this.role = role;
	}

	public double getHourProfit() {
		return hourProfit;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Role getRole() {
		return role;
	}
	
	public void setPreference(int startHr, int endHr,int type) {
		Preference newPreference = new Preference(startHr,endHr,eType.values()[type]);
		this.preference = newPreference;
	}

	public void employeeRevenue() {
		int preferredHour = preference.getStartingHr();
		if (preference.getType() == eType.Home && !department.isSyncable()) {
			hourProfit = 0.8; // 0.1*8
		} else {
			int[] day = new int[24];
			for (int i = 0; i < 9; i++) {
				if (i != 5) {
					if (startingHr + i < 24) {
						day[startingHr + i] = -1;
					} else {
						day[(startingHr + i) - 24] = -1;
					}
				} else {
					if (startingHr + i < 24) {
						day[startingHr + i] = 0;
					} else {
						day[(startingHr + i) - 24] = 0;
					}
				}
			}
			for (int i = 8; i < 17; i++) {
				day[i] = 0;
			}
			for (int i = 0; i < 9; i++) {
				if (preferredHour + i < 24) {
					day[preferredHour + i] *= -1;
				} else {
					day[(preferredHour + i) - 24] *= -1;
				}
			}
			double tempHourProfit = 0;
			for (int i = 0; i < day.length; i++) {
				tempHourProfit += day[i];
			}
			hourProfit = tempHourProfit * 0.2;
		}
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Employee)) {
			return false;
		}
		Employee tempE = (Employee) other;
		return this.id.equals(tempE.id);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Name: ");
		sb.append(name).append(", ID: ").append(id).append("\nDepartment: ").append(department.getName())
				.append("\nRole: ").append(role.getName()).append("\nPreference: ").append(preference);
		return sb.toString();
	}
}

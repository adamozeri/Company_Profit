package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Preference implements Serializable {

	public enum eType {
		Early, Late, Regular, Home
	}

	private int startingHr;
	private int endingHr;
	private eType type;

	public Preference(int startingHr, int endingHr, eType type) {
		this.startingHr = startingHr;
		this.endingHr = endingHr;
		this.type = type;
	}

	public eType getType() {
		return type;
	}

	public int getStartingHr() {
		return startingHr;
	}

	public int getEndingHr() {
		return endingHr;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (type != eType.Home) {
			sb.append("Starting Hour: ").append(startingHr).append(":00").append(", Ending Hour: ").append(endingHr)
					.append(":00, ").append("Work Type: ");
		}
		sb.append(type);
		return sb.toString();
	}

}

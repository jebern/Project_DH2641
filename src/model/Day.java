package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Day extends Observable {
	 
	// the start of the agenda in min, counted from midnight
	private int start;
	
	private List<Activity> activities = new ArrayList<Activity>();
	
	public Day(int hour, int min) {
		start = hour*60 + min;
	}
	
	public int getEnd() {
		return getStart() + (int) getTotalLength();
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
		setChanged();
		notifyObservers();
	}
	
	public ArrayList<Activity> getActs() {
		return (ArrayList<Activity>) activities;
	}

	// removes an activity from the specific position
	public void removeActs(int pos) {
		activities.remove(pos);
		setChanged();
		notifyObservers(pos);
	}
	
	// adds an activity to specific position
	public void addActs(int pos, Activity act){
		if(pos > activities.size()) {
			pos = activities.size();
		}
		activities.add(pos, act);
		Object[] o = {pos, act};
		setChanged();
		notifyObservers(o);
	}

	// returns the total length of the acitivities in a day in minutes
	public double getTotalLength() {
		double result = 0;
		for(Activity act:activities) {
			result += act.getLength();
		}
		return result;
	}
	
	// returns the length (in minutes) of activities of certain type
	public double getLengthByType(int type) {
		double result = 0;
		for(Activity act:activities) {
			if(act.getType() == type) {
				result += act.getLength();
			}
		}
		return result;
	}
}
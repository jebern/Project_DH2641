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
		notifyObservers("StartChanged");
	}
	
	public ArrayList<Activity> getActs() {
		return (ArrayList<Activity>) activities;
	}

	// removes an activity from the specific position
	public Activity removeActs(int pos) {
		Activity a = activities.remove(pos);
		setChanged();
		notifyObservers("ActivityChange remove "+pos);
		return a;
	}
	
	// adds an activity to specific position
	public int addActs(Activity act,int position){
		if(position > activities.size()) {
			position = activities.size();
		}
		activities.add(position, act);
		setChanged();
		notifyObservers("ActivityChange");
		return position;
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

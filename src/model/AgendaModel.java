package model;

import java.util.ArrayList;
import java.util.Observable;

public class AgendaModel extends Observable {

	private ArrayList<Day> days = new ArrayList<Day>();
	private ArrayList<Activity> parkedActivities = new ArrayList<Activity>();
	
	public AgendaModel() {
		
	}
	
	public ArrayList<Day> getDays() {
		return days;
	}
	
	public ArrayList<Activity> getParked() {
		return parkedActivities;
	}
	
	public Day addDay(int startHour, int startMin) {
		Day d = new Day(startHour, startMin);
		days.add(d);
		setChanged();
		notifyObservers(d);
		return d;
	}
	
	public void delDay(Day d) {
		int pos = days.indexOf(d);
		days.remove(d);
		setChanged();
		notifyObservers("day "+pos);
	}

	// add an activity to parked activities
	public void addParkedActivity(int i, Activity act) {
		parkedActivities.add(i, act);
		Object[] o = {i,act};
		setChanged();
		notifyObservers(o);
	}
	
	// remove an activity on provided position from parked activites 
	public void removeParkedActivity(int pos) {
		parkedActivities.remove(pos);
		setChanged();
		notifyObservers("act "+pos);
	}
	
	public static AgendaModel getModelWithExampleData() {
		AgendaModel model = new AgendaModel();
		
		Day d = model.addDay(8,30);
		d.addActs(0, new Activity("Introduction","Intro to the meeting",10,1));
		d.addActs(1, new Activity("Idea 1","Presenting idea 1",30,1));
		d.addActs(2, new Activity("Working in groups","Working on business model for idea 1",35,2));
		d.addActs(3, new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3));
		d.addActs(4, new Activity("Coffee break","Time for some coffee",20,4));
		d.addActs(5, new Activity("Coffee break","Time for some coffee",20,4));
		d.addActs(6, new Activity("Coffee break","Time for some coffee",20,4));
		d.addActs(7, new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3));
		d.addActs(8, new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3));
		d.addActs(9, new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3));
		d.addActs(10, new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3));
		
		return model;
	}
}

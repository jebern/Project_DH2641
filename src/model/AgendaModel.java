package model;

import java.util.ArrayList;
import java.util.Observable;

public class AgendaModel extends Observable {

	private ArrayList<Day> days = new ArrayList<Day>();
	private ArrayList<Activity> parkedActivites = new ArrayList<Activity>();
	
	public AgendaModel() {
		
	}
	
	public ArrayList<Day> getDays() {
		return days;
	}
	
	public ArrayList<Activity> getParked() {
		return parkedActivites;
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
		notifyObservers(pos);
	}

	// add an activity to parked activities
	public void addParkedActivity(int i, Activity act) {
		parkedActivites.add(i, act);
		setChanged();
		notifyObservers();
	}
	
	// adds a newly created activity and sends it to the views
	public void addNewParkedActivity(int i, Activity act) {
		parkedActivites.add(i, act);
		setChanged();
		notifyObservers(act);
	}
	
	// remove an activity on provided position from parked activites 
	public Activity removeParkedActivity(int pos) {
		Activity a = parkedActivites.remove(pos);
		setChanged();
		notifyObservers("remove "+pos);
		return a;
	}
	
//	public static AgendaModel getModelWithExampleData() {
//		AgendaModel model = new AgendaModel();
//		
//		Day d = model.addDay(8,30);
//		model.addActivity(new Activity("Introduction","Intro to the meeting",10,1),d,0);
//		model.addActivity(new Activity("Idea 1","Presenting idea 1",30,1),d,1);
//		model.addActivity(new Activity("Working in groups","Working on business model for idea 1",35,2),d,2);
//		model.addActivity(new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3),d,3);
//		model.addActivity(new Activity("Coffee break","Time for some coffee",20,4),d,4);
//		model.addActivity(new Activity("Coffee break","Time for some coffee",20,4),d,4);
//		model.addActivity(new Activity("Coffee break","Time for some coffee",20,4),d,4);
//		model.addActivity(new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3),d,3);
//		model.addActivity(new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3),d,3);
//		model.addActivity(new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3),d,3);
//		model.addActivity(new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3),d,3);
//		
//		return model;
//	}
}

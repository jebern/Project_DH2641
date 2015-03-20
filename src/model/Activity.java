package model;

public class Activity {

	private String name;
	private String description;		
	private int length; //in minutes
	private int type;
	
	// The possible types of the activity
	public static final int PRESENTATION = 1;
	public static final int GROUP_WORK = 2;
	public static final int DISCUSSION = 3;
	public static final int BREAK = 4;
	
	public Activity(String name, String description, int length, int type) {
		this.name = name;
		this.description = description;
		this.length = length;
		this.type = type;
	}
	
	public String getTypeString() {
		if(type == PRESENTATION) {
			return "Presentation";
		}
		if(type == GROUP_WORK) {
			return "Group work";
		}
		if(type == DISCUSSION) {
			return "Discussion";
		}
		return "Break";
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public int getLength() {
		return length;
	}

	public int getType() {
		return type;
	}
}
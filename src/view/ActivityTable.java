package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Activity;
import model.AgendaModel;
import model.Day;

public class ActivityTable extends JTable {

	private static final long serialVersionUID = 1L;
	public ArrayList<Activity> acts;
	public Object model;
	private DefaultTableModel tableModel;

	public ActivityTable(ArrayList<Activity> act, Object model) {
		this.model = model;
		this.acts = new ArrayList<Activity>();
		setSelectionModel(new ForcedListSelectionModel());
		setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		setSize(350, 500);
		setMinimumSize(new Dimension(350, 500));
		setMaximumSize(new Dimension(350, 800));
		setPreferredSize(new Dimension(350, 700));
		tableModel = new DefaultTableModel(0, 1);
		setModel(tableModel);
		setDragEnabled(true);
		setDropMode(DropMode.INSERT);
		setTransferHandler(new ActivityTableController());
		for (Activity a: act) {
			addActsToTable(a);
		}
	}
	
	public int getTypeInt(String s) {
		if (s.equals("Presentation")) {
			return Activity.PRESENTATION;
		}
		if (s.equals("Group work")) {
			return Activity.GROUP_WORK;
		}
		if (s.equals("Discussion")) {
			return Activity.DISCUSSION;
		}
		return Activity.BREAK;
	}
	
	// adds a newly created activity to the table
	public void addActsToTable(Activity a) {
		acts.add(a);
		Object[] newRow = { a.getLength() + "min " + a.getTypeString()
				+ ": " + a.getName() };
		tableModel.addRow(newRow);
	}

	public void addActs(int idx, String a) {
		String[] s = a.split(": ");
		Activity act = new Activity(s[1],
				" ",
				Integer.parseInt(s[0].split("min ")[0]),
				getTypeInt(s[0].split("min ")[1]));
		acts.add(act);
		if (model instanceof AgendaModel) {
			AgendaModel m = (AgendaModel) model;
			m.addParkedActivity(idx, act);
		} else {
			Day m = (Day) model;
			m.addActs(act, idx);
		}
	}

	public Activity removeActs(int i) {
		acts.remove(i);
		validate();
		if (model instanceof AgendaModel) {
			AgendaModel m = (AgendaModel) model;
			return m.removeParkedActivity(i);
		} else {
			Day m = (Day) model;
			return m.removeActs(i);
		}
	}
	
	public class ForcedListSelectionModel extends DefaultListSelectionModel {

		private static final long serialVersionUID = 1L;

		public ForcedListSelectionModel () {
	        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    }

	    @Override
	    public void clearSelection() {
	    }

	    @Override
	    public void removeSelectionInterval(int index0, int index1) {
	    }
	}
}
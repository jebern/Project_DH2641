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
	public Object model;
	private DefaultTableModel tableModel;
	public String id;

	public ActivityTable(ArrayList<Activity> act, Object model, String id) {
		this.model = model;
		this.id = id;
		setSelectionModel(new ForcedListSelectionModel());
		setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		setSize(350, 500);
		setMinimumSize(new Dimension(350, 500));
		setMaximumSize(new Dimension(350, 800));
		setPreferredSize(new Dimension(350, 700));
		tableModel = new DefaultTableModel(0, 1) {
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		setModel(tableModel);
		setDragEnabled(true);
		setDropMode(DropMode.INSERT);
		setTransferHandler(new ActivityTableController());
		for (Activity a: act) {
			addActs(-1, a);
		}
	}

	public void addActs(int i, Activity a) {
		Object[] newRow = { a.getLength() + "min " + a.getTypeString()
				+ ": " + a.getName() };
		if(i == -1) {
			tableModel.addRow(newRow);
		} else {
			tableModel.insertRow(i, newRow);			
		}
	}

	public void removeActs(int i) {
		tableModel.removeRow(i);
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
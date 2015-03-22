package view;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSource;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Activity;
import model.AgendaModel;
import model.Day;

class ActivityTableController extends TransferHandler {

	private static final long serialVersionUID = 1L;
	private final DataFlavor flavor;
	private int fromRow = -1;
	private int toRow = -1;
	private int addRow = 0;
	private Object[] dndobjects = new Object[1];
	private ActivityTable fromTable = null;

	public ActivityTableController() {
		flavor = new ActivationDataFlavor(Object[].class,
				DataFlavor.javaJVMLocalObjectMimeType, "ObjectArray");
	}

	@Override
	public boolean canImport(TransferHandler.TransferSupport info) {
		ActivityTable t = (ActivityTable) info.getComponent();
		if (info.isDrop() && info.isDataFlavorSupported(flavor)) {
			t.setCursor(DragSource.DefaultMoveDrop);
			return true;
		}
		t.setCursor(DragSource.DefaultMoveNoDrop);
		return false;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		fromTable = (ActivityTable) c;		
		DefaultTableModel model = (DefaultTableModel) fromTable.getModel();
		fromRow = fromTable.getSelectedRow();
		dndobjects[0] = model.getValueAt(fromRow, 0);
		return new DataHandler(dndobjects, flavor.getMimeType());
	}

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport info) {
		ActivityTable toTable = (ActivityTable) info.getComponent();
		JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();
		toRow = dl.getRow();
		if(fromTable != null) {
			addRow = 1;
		}
		toTable.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		try {
			Object[] values = (Object[]) info.getTransferable()
					.getTransferData(flavor);
			String[] s = ((String) values[0]).split(": ");
			Activity act = new Activity(s[1], " ", Integer.parseInt(s[0]
					.split("min ")[0]), getTypeInt(s[0].split("min ")[1]));
			if (toTable.model instanceof AgendaModel) {
				AgendaModel m = (AgendaModel) toTable.model;
				m.addParkedActivity(toRow, act);
			} else {
				Day m = (Day) toTable.model;
				m.addActs(toRow, act);
			}
			toTable.getSelectionModel().addSelectionInterval(toRow, toRow);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void exportDone(JComponent c, Transferable t, int act) {
		if (act == MOVE) {
			removerows(c);
		}
		fromRow = -1;
		toRow = -1;
		addRow = 0;
		fromTable = null;
	}

	private void removerows(JComponent src) {
		if (fromRow != -1) {
			src.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if (fromRow >= toRow) {
				fromRow += addRow;
			}
			if (fromTable.model instanceof AgendaModel) {
				AgendaModel m = (AgendaModel) fromTable.model;
				m.removeParkedActivity(fromRow);
			} else {
				Day m = (Day) fromTable.model;
				m.removeActs(fromRow);
			}
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
}
package view;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSource;
import java.util.Vector;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class ActivityTableController extends TransferHandler {

	private static final long serialVersionUID = 1L;
	private int rowIdx = -1;
	private int index = -1; 
	private int rows = 0; 
	private final DataFlavor flavor;
	private Object[] dndobjects = new Object[1];
	private JComponent original = null;

	public ActivityTableController() {
		flavor = new ActivationDataFlavor(Object[].class,
				DataFlavor.javaJVMLocalObjectMimeType, "ObjectArray");
	}
	
	@Override
	public boolean canImport(TransferHandler.TransferSupport info) {
		JTable t = (JTable) info.getComponent();
		if (info.isDrop() && info.isDataFlavorSupported(flavor)) {
			t.setCursor(DragSource.DefaultMoveDrop);
			return true;
		}
		t.setCursor(DragSource.DefaultMoveNoDrop);
		return false;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		original = c;
		JTable table = (JTable) c;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		rowIdx = table.getSelectedRow();
		dndobjects[0] = model.getDataVector().elementAt(rowIdx);
		return new DataHandler(dndobjects,
				flavor.getMimeType());
	}

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport info) {
		ActivityTable at = (ActivityTable) info.getComponent();
		JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();
		DefaultTableModel model = (DefaultTableModel) at.getModel();
		int i = dl.getRow();
		int max = model.getRowCount();
		if (i < 0 || i > max) {
			i = max;
		}
		index = i;

		at.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		try {
			Object[] values = (Object[]) info.getTransferable()
					.getTransferData(flavor);
			if (original == at) {
				rows = 1;
			}
				int idx = i++;
				Vector v = (Vector) values[0];
				model.insertRow(idx, v);
				at.addActs(idx, (String) v.elementAt(0));
				at.getSelectionModel().addSelectionInterval(idx, idx);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void exportDone(JComponent c, Transferable t, int act) {
		if(act == MOVE) {
			removerows(c);			
		}
		rowIdx = -1;
		rows = 0;
		index = -1;

	}

	private void removerows(JComponent src) {
		if (rowIdx != -1) {
			ActivityTable tabel = (ActivityTable) src;
			src.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			DefaultTableModel model = (DefaultTableModel) tabel.getModel();
			if (rows > 0) {
				if (rowIdx >= index) {
					rowIdx += rows;
				}
			}
			model.removeRow(rowIdx);
			tabel.removeActs(rowIdx);
		}
	}
}

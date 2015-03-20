package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import view.DayView;

import model.AgendaModel;
import model.Day;

public class DayViewController implements DocumentListener, ActionListener {
	
	private DayView view;
	private Day d;
	private AgendaModel m;
	
	public DayViewController(DayView view, Day d, AgendaModel m) {
		this.view = view;
		this.d = d;
		this.m = m;
		view.time.getDocument().addDocumentListener(this);
		view.remove.addActionListener(this);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		newStartTime();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		newStartTime();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		newStartTime();
	}
	
	private void newStartTime() {
		String[] s = view.time.getText().split(":");
		if(s.length == 2) {
			try {
				int i = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
				d.setStart(i);	
				view.message.setText("");
			} catch(NumberFormatException e) {
				view.message.setText("Wrong format!");
			}
		} else {
			view.message.setText("Wrong format!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		m.delDay(d);
	}
}

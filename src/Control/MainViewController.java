package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AddView;
import view.MainView;

import model.AgendaModel;

public class MainViewController implements ActionListener {
	private MainView view;
	private AgendaModel model;

	public MainViewController(MainView view, AgendaModel model) {
		this.view = view;
		this.model = model;
		view.addAct.addActionListener(this);
		view.addDay.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.addAct) {
			new AddView(model);
		}
		if (e.getSource() == view.addDay) {
			model.addDay(8, 0);
		}
	}
}
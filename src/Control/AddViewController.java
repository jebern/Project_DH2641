package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.AddView;

import model.Activity;
import model.AgendaModel;

public class AddViewController implements ActionListener {

	private AddView view;
	private AgendaModel model;

	public AddViewController(AddView view, AgendaModel model) {
		this.model = model;
		this.view = view;
		view.save.addActionListener(this);
		view.cancel.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.save) {
			try {
				if(!view.name.getText().equals("")) {
					model.addNewParkedActivity(model.getParked().size(), new Activity(view.name.getText(),
							view.desc.getText(), Integer.parseInt(view.min
									.getText()), view.select.getSelectedIndex() + 1));
					
					view.dispose();					
				} else {
					JOptionPane.showMessageDialog(view,
							"You have to fill in the name field!");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(view,
						"The length has to be an integer value");
			}
		}
		if (e.getSource() == view.cancel) {
			view.dispose();
		}
	}

}

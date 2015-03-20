package view;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Control.MainViewController;

import model.*;

public class MainView extends JFrame implements Observer{
	
	private static final long serialVersionUID = 1L;
	public JButton addAct;
	public JButton addDay;
	private AgendaModel model;
	private ActivityTable acts;
	private JPanel dayPanel;
	private GridBagConstraints c;
	public static final Color RED = new Color(227,27,27);
	public static final Color GREEN = new Color(133, 191, 130);

	public static void main(String[] args) {
		new MainView();
	}
	
	public MainView() {
		setSize(1200, 900);
		setTitle("Meeting Agenda");
		model = new AgendaModel();
		model.addObserver(this);
		getContentPane().add(createContent());
		new MainViewController(this, model);
		setVisible(true);
	}
	
	public JScrollPane createContent() {
		JPanel p = new JPanel();
		JScrollPane scroll = new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p.setBorder(new EmptyBorder(50, 50, 50, 50));
		p.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		JPanel col1 = new JPanel();
		col1.setLayout(new BorderLayout());
		addAct = new JButton("+ Add activity");
		addAct.setBackground(GREEN);
		col1.add(addAct, BorderLayout.NORTH);
		acts = new ActivityTable(model.getParked(), model);
		col1.add(acts, BorderLayout.CENTER);
		
		c.gridx = GridBagConstraints.RELATIVE;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,50);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1;
		c.weightx = 0;
		p.add(col1, c);
		dayPanel = new JPanel();
		dayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		for(Day d: model.getDays()) {
			addDay(d);		
		}
		p.add(dayPanel, c);
		c.weightx = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		addDay = new JButton("+ Add day");
		addDay.setBackground(GREEN);
		p.add(addDay, c);
		
		return scroll;
	}
	
	private void addDay(Day d) {
		dayPanel.add(new DayView(d, model));
	}
	
	private void delDay(int pos) {
		dayPanel.remove(pos);
	}

	@Override
	public void update(Observable arg0, Object message) {
		if(message instanceof Activity) {
			acts.addActsToTable((Activity) message);
		} else if (message instanceof Integer) {
			delDay((Integer) message);
		} else if (message instanceof Day) {
			addDay((Day) message);							
		}
		validate();	
		repaint();
	}
}

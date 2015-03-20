package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Control.AddViewController;
import model.AgendaModel;

public class AddView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public JButton save;
	public JButton cancel;
	
	public JTextField name;
	public JTextField min;
	public JComboBox select;
	public JTextArea desc;
	
	public AddView(AgendaModel model) {
		setSize(500, 500);
		setTitle("Add Activity");
		cancel = new JButton("Cancel");
		cancel.setBackground(Color.RED);
		save = new JButton("Save");
		save.setBackground(MainView.GREEN);
		new AddViewController(this, model);
		
		JPanel p = new JPanel();
		p.setBorder(new EmptyBorder(50, 50, 50, 50));
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JPanel row1 = new JPanel();
		row1.setLayout(new FlowLayout(FlowLayout.LEFT));
		row1.add(new JLabel("Name:   "));
		name = new JTextField();
		name.setPreferredSize(new Dimension(100,20));
		row1.add(name);
		p.add(row1);
		
		JPanel row2 = new JPanel();
		row2.setLayout(new FlowLayout(FlowLayout.LEFT));
		row2.add(new JLabel("Length: "));
		min = new JTextField();
		min.setPreferredSize(new Dimension(100,20));
		row2.add(min);
		row2.add(new JLabel("min"));
		p.add(row2);
		
		String[] alt = {"Presentation","Group work","Discussion", "Break"};
		select = new JComboBox(alt);
		p.add(select);
		
		desc = new JTextArea("Description");
		desc.setBorder(BorderFactory.createLineBorder(Color.black));
		p.add(desc);
		
		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(save);
		p.add(buttons);
		
		this.getContentPane().add(p);
		this.setVisible(true);
	}
}

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import Control.DayViewController;
import model.Activity;
import model.AgendaModel;
import model.Day;

public class DayView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	public JTextField time;
	public JLabel end;
	public JLabel total;
	public JButton remove;
	public JLabel message;
	private Day d;
	private ActivityTable acts;
	private JPanel percentage;
	
	public static final Color PRES_COLOR = new Color(177, 207, 174);
	public static final Color GROUP_COLOR = new Color(138, 152, 162);
	public static final Color DISC_COLOR = new Color(210, 185, 152);
	public static final Color BREAK_COLOR = new Color(206, 149, 152);

	public DayView(Day d, AgendaModel m, String id) {
		this.d = d;
		acts = new ActivityTable(d.getActs(), d, id);
		d.addObserver(this);
		remove = new JButton("Delete day");
		remove.setBackground(MainView.RED);
		time = new JTextField(getStartTime());
		end = new JLabel(getEndTime());
		total = new JLabel("Total time: " + (int) d.getTotalLength() + " min");
		message = new JLabel("");
		new DayViewController(this, d, m);

		setLayout(new BorderLayout());
		JPanel top = new JPanel();
		top.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		top.add(remove, c);

		JPanel row1 = new JPanel();
		row1.setLayout(new FlowLayout(FlowLayout.LEFT));
		row1.add(new JLabel("Start time: "));
		time.setColumns(5);
		row1.add(time);
		row1.add(message);
		top.add(row1, c);

		end.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		top.add(end, c);
		total.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		top.add(total, c);

		percentage = new JPanel();
		percentage.setLayout(new BoxLayout(percentage, BoxLayout.Y_AXIS));
		calcPer();
		top.add(percentage, c);
		this.add(top, BorderLayout.NORTH);
		this.add(acts, BorderLayout.CENTER);
	}
	
	private String getEndTime() {
		String s = "End time: kl "
				+ String.format("%02d", ((d.getEnd() / 60) % 24)) + ":"
				+ String.format("%02d", (d.getEnd() % 60));
		return s;
	}

	private String getStartTime() {
		String s = String.format("%02d", ((d.getStart() / 60) % 24)) + ":"
				+ String.format("%02d", (d.getStart() % 60));
		return s;
	}

	private void calcPer() {
		percentage.removeAll();
		percentage.setSize(340, 50);

		// Break limit line
		JPanel row = new JPanel();
		row.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(
				(int) (percentage.getSize().width * 0.3), 10));
		top.setMinimumSize(new Dimension(
				(int) (percentage.getSize().width * 0.3), 10));
		top.setBackground(BREAK_COLOR);
		row.add(top);
		percentage.add(row);

		// Percentage of each activity type
		JPanel main = new JPanel();
		main.setLayout(new FlowLayout(FlowLayout.RIGHT));
		int w = percentage.getWidth();
		double[] per = {
				d.getLengthByType(Activity.PRESENTATION) / d.getTotalLength()
						* w,
				d.getLengthByType(Activity.GROUP_WORK) / d.getTotalLength() * w,
				d.getLengthByType(Activity.DISCUSSION) / d.getTotalLength() * w,
				d.getLengthByType(Activity.BREAK) / d.getTotalLength() * w };
		Color[] colors = { PRES_COLOR, GROUP_COLOR,
				DISC_COLOR, BREAK_COLOR };
		for (int i = 0; i < per.length; i++) {
			JPanel part = new JPanel();
			part.setPreferredSize(new Dimension((int) per[i], 50));
			part.setMinimumSize(new Dimension((int) per[i], 50));
			part.setBackground(colors[i]);
			main.add(part);
		}
		percentage.add(main);
	}

	@Override
	public void update(Observable arg0, Object message) {
		if(message instanceof Integer) {
			total.setText("Total time: " + (int) d.getTotalLength() + " min");
			int pos =(int) message;
			acts.removeActs(pos);
			calcPer();
			revalidate();
			repaint();
			validate();	
		} else if (message instanceof Object[]) {
			total.setText("Total time: " + (int) d.getTotalLength() + " min");
			Object[] o = (Object[]) message;
			acts.addActs((int) o[0], (Activity) o[1]);
			calcPer();
			revalidate();
			repaint();
			validate();	
		}
		end.setText(getEndTime());
	}
}

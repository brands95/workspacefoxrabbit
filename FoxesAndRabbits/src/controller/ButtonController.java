package controller;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Simulator;

public class ButtonController extends AbstractController implements ActionListener {
	
	private static final long serialVersionUID = 1001367793627540454L;
	private JButton step1Btn, step100Btn, runBtn, stopBtn, resetBtn;
	
	public ButtonController(Simulator sim) {
		super(sim);
	}
	
	public JPanel getButtons() {
		step1Btn = new JButton("Step 1");
		step1Btn.setPreferredSize(new Dimension(90, 25));
		step1Btn.setMinimumSize(new Dimension(90, 10));
		step1Btn.setMaximumSize(new Dimension(90, 25));
		step1Btn.addActionListener(this);
		
		step100Btn = new JButton("Step 100");
		step100Btn.setPreferredSize(new Dimension(90, 25));
		step100Btn.setMinimumSize(new Dimension(90, 10));
		step100Btn.setMaximumSize(new Dimension(90, 25));
		step100Btn.addActionListener(this);
		
		runBtn = new JButton("Run");
		runBtn.setPreferredSize(new Dimension(90,25));
		runBtn.setMinimumSize(new Dimension(90,10));
		runBtn.setMaximumSize(new Dimension(90,25));
		runBtn.addActionListener(this);
        
		stopBtn = new JButton("Stop");
		stopBtn.setPreferredSize(new Dimension(90,25));
		stopBtn.setMinimumSize(new Dimension(90,10));
		stopBtn.setMaximumSize(new Dimension(90,25));
		stopBtn.addActionListener(this);
        
        resetBtn = new JButton("Reset");
        resetBtn.setPreferredSize(new Dimension(90,25));
        resetBtn.setMinimumSize(new Dimension(90,10));
        resetBtn.setMaximumSize(new Dimension(90,25));
        resetBtn.addActionListener(this);
		
		JPanel btnsLeft = new JPanel();
		btnsLeft.setBorder(new EmptyBorder(6, 6, 6, 6));
		btnsLeft.setLayout(new BoxLayout(btnsLeft, BoxLayout.PAGE_AXIS));
		
		btnsLeft.add(step1Btn);
		btnsLeft.add(step100Btn);
		btnsLeft.add(runBtn);
		btnsLeft.add(stopBtn);
		btnsLeft.add(resetBtn);		
		
		return btnsLeft;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == step1Btn) {
			sim.simulate(1);
		}
		if (e.getSource() == step100Btn) {
			sim.simulate(100);
		}		
		if (e.getSource() == runBtn) {
			sim.start();
		}
		if (e.getSource() == stopBtn) {
			sim.stop();
		}
		if (e.getSource() == resetBtn) {
			sim.reset();
		}
	}
	
	@Override
	public JMenuBar getMenu() {
		return null;
	}
	
}

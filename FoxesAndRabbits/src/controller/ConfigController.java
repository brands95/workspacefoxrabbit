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

public class ConfigController extends AbstractController implements ActionListener {
	
	private static final long serialVersionUID = -4932878602825909593L;
	private JButton addBtn, removeBtn;
	
	public ConfigController(Simulator sim) {
		super(sim);
	}
	
	@Override
	public JPanel getButtons() {
		addBtn = new JButton("Add");
		addBtn.setPreferredSize(new Dimension(90, 25));
		addBtn.setMinimumSize(new Dimension(90, 10));
		addBtn.setMaximumSize(new Dimension(90, 25));
		addBtn.addActionListener(this);
		
		removeBtn = new JButton("Remove");
		removeBtn.setPreferredSize(new Dimension(90, 25));
		removeBtn.setMinimumSize(new Dimension(90, 10));
		removeBtn.setMaximumSize(new Dimension(90, 25));
		removeBtn.addActionListener(this);
		
		JPanel btnsLeft = new JPanel();
		btnsLeft.setBorder(new EmptyBorder(6, 6, 6, 6));
		btnsLeft.setLayout(new BoxLayout(btnsLeft, BoxLayout.PAGE_AXIS));
		
		btnsLeft.add(addBtn);
		btnsLeft.add(removeBtn);
		
		return btnsLeft;
	}
	
	@Override
	public JMenuBar getMenu() {
		return null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO
	}

}

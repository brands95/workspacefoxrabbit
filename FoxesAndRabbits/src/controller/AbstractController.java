package controller;

import javax.swing.*;

import model.Simulator;

public abstract class AbstractController extends JPanel {
	
	private static final long serialVersionUID = -7113981188624133735L;
	protected Simulator sim;
	
	public AbstractController(Simulator sim) {
		this.sim = sim;
	}
	
	public abstract JPanel getButtons();
	public abstract JMenuBar getMenu();

}

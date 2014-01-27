package main;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.*;
import model.*;
import view.*;

public class SimulationRunner extends JFrame {
	
	private static final long serialVersionUID = 9122126579297218136L;
	private Simulator sim;
	private AbstractView simview;
	private AbstractController btnController;
	private AbstractController menuController;
	
	public static void main(String[] args) {
		
		new SimulationRunner();
	}
	
	public SimulationRunner() {
		
		sim = new Simulator();
		simview = new SimulatorView(sim);
		simview.setColor(Rabbit.class, Color.yellow);
		simview.setColor(Fox.class, Color.red);
		simview.setColor(Gorilla.class, Color.black);
		simview.setColor(Hunter.class, Color.blue);
		simview.setColor(Grass.class, Color.green);
		
		btnController = new ButtonController(sim);
		menuController = new MenuController(sim);
		
		setTitle("Vossen en Konijnen");
		setSize(800, 800);
		setLocation(100, 50);
		
		JPanel container = (JPanel)getContentPane();
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(6, 6, 6, 6));
		
		setJMenuBar(menuController.getMenu());
		
		container.add(simview.getField(), BorderLayout.CENTER);
		container.add(btnController.getButtons(), BorderLayout.WEST);
		pack();
		setVisible(true);
		
		sim.reset();
	}

}

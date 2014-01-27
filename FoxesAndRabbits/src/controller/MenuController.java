package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFrame;

import view.AbstractView;
import view.GraphView;

import model.Simulator;
import model.Hunter;
import model.Gorilla;
import model.Fox;
import model.Rabbit;

public class MenuController extends AbstractController implements ActionListener {
	
	private static final long serialVersionUID = -2699052580073235247L;
	private JMenuItem quitItem, configItem, graphItem, histoItem;
	
	public MenuController(Simulator sim) {
		super(sim);
	}
	
	@Override
	public JPanel getButtons() {
		return null;
	}
	
	@Override
	public JMenuBar getMenu() {
		JMenuBar menubar = new JMenuBar();
		
		JMenu menu1 = new JMenu("Opties");
		JMenu menu2 = new JMenu("Views");
		JMenu menuHelp = new JMenu("Help");
		
		quitItem = new JMenuItem("Exit");
		configItem = new JMenuItem("Config");
		graphItem = new JMenuItem("Graph");
		histoItem = new JMenuItem("Histogram");
		
		quitItem.addActionListener(this);
		configItem.addActionListener(this);
		graphItem.addActionListener(this);
		histoItem.addActionListener(this);
		
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.add(menuHelp);
		
		menu1.add(quitItem);
		menu1.add(configItem);
		menu2.add(graphItem);
		menu2.add(histoItem);
		
		return menubar;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quitItem) {
			quit();
		}
		if (e.getSource() == configItem) {
			config();
		}
		if (e.getSource() == graphItem) {
			graphView();
		}
	}
	
	private void quit() {
		System.exit(0);
	}
	
	private void config() {
		JFrame config = new JFrame("Config");
		
		AbstractController configC = new ConfigController(sim);
		
		JPanel container = (JPanel)config.getContentPane();
		
		container.add(configC.getButtons());
		
		config.pack();
		config.setVisible(true);
	}
	
	private void graphView() {
		JFrame config = new JFrame("GraphView");
		
		AbstractView graphView = new GraphView(sim);
		graphView.setColor(Rabbit.class, Color.YELLOW);
		graphView.setColor(Fox.class, Color.RED);
		graphView.setColor(Gorilla.class, Color.BLACK);
		graphView.setColor(Hunter.class, Color.BLUE);
		
		JPanel container = (JPanel)config.getContentPane();
		
		container.add(graphView.getField());
		
		config.pack();
		config.setVisible(true);
	}

}

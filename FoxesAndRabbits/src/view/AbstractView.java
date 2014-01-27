package view;

import java.awt.Color;
import javax.swing.JPanel;

import model.*;

public abstract class AbstractView extends JPanel {
	
	private static final long serialVersionUID = 796898350190651804L;
	protected Simulator sim;
	
	public AbstractView(Simulator sim) {
		this.sim = sim;
		sim.addView(this);
	}
	
	public abstract void setColor(Class<?> animalClass, Color color);
	
	public abstract void showStatus();
	
	public abstract JPanel getField();

}

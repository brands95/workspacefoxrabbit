package model;

import java.util.ArrayList;
import java.util.List;

import view.AbstractView;

public abstract class AbstractModel {
	
	private List<AbstractView> views;
	
	public AbstractModel() {
		views = new ArrayList<AbstractView>();
	}
	
	public void addView(AbstractView view) {
		views.add(view);
	}
	
	public void statusUpdate() {
		for(AbstractView a : views) {
			a.showStatus();
		}
	}
	
	public abstract void reset();

}

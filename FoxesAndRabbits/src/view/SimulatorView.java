package view;

import java.awt.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

import model.*;
/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class SimulatorView extends AbstractView
{
	private static final long serialVersionUID = -5130476236112770424L;

	// Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;
    
    // A map for storing colors for participants in the simulation
    private Map<Class<?>, Color> colors;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(Simulator sim) {
    	
    	super(sim);
     	colors = new LinkedHashMap<Class<?>, Color>();
     	fieldView = new FieldView(100, 100);
    }

    public JPanel getField() {
    	
    	stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
    	population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
    	
    	JPanel right = new JPanel();
    	right.setLayout(new BorderLayout());
    	right.add(stepLabel, BorderLayout.NORTH);
    	right.add(fieldView, BorderLayout.CENTER);
    	right.add(population, BorderLayout.SOUTH);
    	right.setBorder(BorderFactory.createLineBorder(Color.black));
    	
    	return right;
    }
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class<?> animalClass, Color color)
    {
        colors.put(animalClass, color);
    }

    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class<?> animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus()
    {
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + sim.getStep());
        sim.getFieldStats().reset();
        
        fieldView.preparePaint();

        for(int row = 0; row < sim.getField().getDepth(); row++) {
            for(int col = 0; col < sim.getField().getWidth(); col++) {
                Object animal = sim.getField().getObjectAt(row, col);
                if(animal != null) {
                    sim.getFieldStats().incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        
        sim.getFieldStats().countFinished();
        
        population.setText(POPULATION_PREFIX + sim.getFieldStats().getPopulationDetails(sim.getField()));
        fieldView.repaint();
    }

/**
 * Provide a graphical view of a rectangular field. This is 
 * a nested class (a class defined inside a class) which
 * defines a custom component for the user interface. This
 * component displays the field.
 * This is rather advanced GUI stuff - you can ignore this 
 * for your project if you like.
 */
private class FieldView extends JPanel
{
	private static final long serialVersionUID = 5409020011000208394L;

	private final int GRID_VIEW_SCALING_FACTOR = 6;

    private int gridWidth, gridHeight;
    private int xScale, yScale;
    Dimension size;
    private Graphics g;
    private Image fieldImage;

    /**
     * Create a new FieldView component.
     */
    public FieldView(int height, int width)
    {
        gridHeight = height;
        gridWidth = width;
        size = new Dimension(0, 0);
    }

    /**
     * Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                             gridHeight * GRID_VIEW_SCALING_FACTOR);
    }

    /**
     * Prepare for a new round of painting. Since the component
     * may be resized, compute the scaling factor again.
     */
    public void preparePaint()
    {
        if(! size.equals(getSize())) {  // if the size has changed...
            size = getSize();
            fieldImage = fieldView.createImage(size.width, size.height);
            g = fieldImage.getGraphics();

            xScale = size.width / gridWidth;
            if(xScale < 1) {
                xScale = GRID_VIEW_SCALING_FACTOR;
            }
            yScale = size.height / gridHeight;
            if(yScale < 1) {
                yScale = GRID_VIEW_SCALING_FACTOR;
            }
        }
    }
    
    /**
     * Paint on grid location on this field in a given color.
     */
    public void drawMark(int x, int y, Color color)
    {
        g.setColor(color);
        g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
    }

    /**
     * The field view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g)
    {
        if(fieldImage != null) {
            Dimension currentSize = getSize();
            if(size.equals(currentSize)) {
                g.drawImage(fieldImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    }
}
}

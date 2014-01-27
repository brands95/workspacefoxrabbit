package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Map;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

import javax.swing.JPanel;

import model.*;

public class GraphView extends AbstractView
{
	private static final long	serialVersionUID	= 8408351482084593575L;
	private static final Color LIGHT_GRAY = new Color(0, 0, 0, 40);
	private Map<Class<?>, Color> colors;
	private static GraphPanel graph;
	private Set<Class<?>> classes;
	
	public GraphView(Simulator brain) {
		super(brain);
		classes = new HashSet<Class<?>>();
        colors = new HashMap<Class<?>, Color>();
	}


	@Override
	public void setColor(Class<?> animalClass, Color color) {
		colors.put(animalClass, color);
        classes = colors.keySet();
	}
	

	@Override
	public void showStatus() {
		graph.update(sim.getStep(), sim.getField(), sim.getFieldStats());
	}

	@Override
	public JPanel getField() {
		graph = new GraphPanel(200, 200, 500);
		
		JPanel graphView = new JPanel();
		//graphView.setLayout(new BorderLayout());
        graphView.add(graph, BorderLayout.CENTER);
        //stepLabel = new JLabel("");
        //countLabel = new JLabel(" ");
        //graphView.add(stepLabel, BorderLayout.NORTH);
        //graphView.add(countLabel, BorderLayout.SOUTH);

      

        return graphView;
	}
	
	/**
     * Nested class: a component to display the graph.
     */
    class GraphPanel extends JComponent
    {
        
		private static final long	serialVersionUID	= -93224324839379620L;

		private static final double SCALE_FACTOR = 0.8;

        // An internal image buffer that is used for painting. For
        // actual display, this image buffer is then copied to screen.
        private BufferedImage graphImage;
        private int lastVal1, lastVal2, lastVal3, lastVal4;
        private int yMax;

        /**
         * Create a new, empty GraphPanel.
         */
        public GraphPanel(int width, int height, int startMax)
        {
            graphImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            clearImage();
            lastVal1 = height;
            lastVal2 = height;
            yMax = startMax;
        }

        /**
         * Indicate a new simulation run on this panel.
         */
        public void newRun()
        {
            int height = graphImage.getHeight();
            int width = graphImage.getWidth();

            Graphics g = graphImage.getGraphics();
            g.copyArea(4, 0, width-4, height, -4, 0);            
            g.setColor(Color.BLACK);
            g.drawLine(width-4, 0, width-4, height);
            g.drawLine(width-2, 0, width-2, height);
            lastVal1 = height;
            lastVal2 = height;
            lastVal3 = height;
            lastVal4 = height;
            repaint();
        }

        /**
         * Dispay a new point of data.
         */
        public void update(int step, Field field, FieldStats stats)
        {
            if (classes.size() >= 2) {
                Iterator<Class<?>> it = classes.iterator();
                Class<?> class1 = it.next();
                Class<?> class2 = it.next();
                Class<?> class3 = it.next();
                Class<?> class4 = it.next();
                

                stats.reset();
                int count1 = sim.getFieldStats().getCount(field, class1);
                int count2 = sim.getFieldStats().getCount(field, class2);
                int count3 = sim.getFieldStats().getCount(field, class3);
                int count4 = sim.getFieldStats().getCount(field, class4);

                Graphics g = graphImage.getGraphics();

                int height = graphImage.getHeight();
                int width = graphImage.getWidth();

                // move graph one pixel to left
                g.copyArea(1, 0, width-1, height, -1, 0);

                // calculate y, check whether it's out of screen. scale down if necessary.
                int y = height - ((height * count1) / yMax) - 1;
                while (y<0) {
                    scaleDown();
                    y = height - ((height * count1) / yMax) - 1;
                }
                g.setColor(LIGHT_GRAY);
                g.drawLine(width-2, y, width-2, height);
                g.setColor(colors.get(class1));
                g.drawLine(width-3, lastVal1, width-2, y);
                lastVal1 = y;

                y = height - ((height * count2) / yMax) - 1;
                while (y<0) {
                    scaleDown();
                    y = height - ((height * count2) / yMax) - 1;
                }
                g.setColor(LIGHT_GRAY);
                g.drawLine(width-2, y, width-2, height);
                g.setColor(colors.get(class2));
                g.drawLine(width-3, lastVal2, width-2, y);
                lastVal2 = y;
                
                y = height - ((height * count3) / yMax) - 1;
                while (y<0) {
                    scaleDown();
                    y = height - ((height * count3) / yMax) - 1;
                }
                g.setColor(LIGHT_GRAY);
                g.drawLine(width-2, y, width-2, height);
                g.setColor(colors.get(class3));
                g.drawLine(width-3, lastVal3, width-2, y);
                lastVal3 = y;
                
                y = height - ((height * count4) / yMax) - 1;
                while (y<0) {
                    scaleDown();
                    y = height - ((height * count4) / yMax) - 1;
                }
                g.setColor(LIGHT_GRAY);
                g.drawLine(width-2, y, width-2, height);
                g.setColor(colors.get(class4));
                g.drawLine(width-3, lastVal4, width-2, y);
                lastVal4 = y;
                
                repaint();

                //stepLabel.setText("" + step);
                //countLabel.setText(stats.getPopulationDetails(field));
            }
        }

        /**
         * Scale the current graph down vertically to make more room at the top.
         */
        public void scaleDown()
        {
            Graphics g = graphImage.getGraphics();
            int height = graphImage.getHeight();
            int width = graphImage.getWidth();

            BufferedImage tmpImage = new BufferedImage(width, (int)(height*SCALE_FACTOR), 
                                                       BufferedImage.TYPE_INT_RGB);
            Graphics2D gtmp = (Graphics2D) tmpImage.getGraphics();

            gtmp.scale(1.0, SCALE_FACTOR);
            gtmp.drawImage(graphImage, 0, 0, null);

            int oldTop = (int) (height * (1.0-SCALE_FACTOR));

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, oldTop);
            g.drawImage(tmpImage, 0, oldTop, null);

            yMax = (int) (yMax / SCALE_FACTOR);
            lastVal1 = oldTop + (int) (lastVal1 * SCALE_FACTOR);
            lastVal2 = oldTop + (int) (lastVal2 * SCALE_FACTOR);

            repaint();
        }

        /**
         * Cause immediate update of the panel.
         */
        public void repaintNow()
        {
            paintImmediately(0, 0, graphImage.getWidth(), graphImage.getHeight());
        }

        /**
         * Clear the image on this panel.
         */
        public void clearImage()
        {
            Graphics g = graphImage.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, graphImage.getWidth(), graphImage.getHeight());
            repaint();
        }

        // The following methods are redefinitions of methods
        // inherited from superclasses.

        /**
         * Tell the layout manager how big we would like to be.
         * (This method gets called by layout managers for placing
         * the components.)
         * 
         * @return The preferred dimension for this component.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(graphImage.getWidth(), graphImage.getHeight());
        }

        /**
         * This component is opaque.
         */
        public boolean isOpaque()
        {
            return true;
        }

        /**
         * This component needs to be redisplayed. Copy the internal image 
         * to screen. (This method gets called by the Swing screen painter 
         * every time it want this component displayed.)
         * 
         * @param g The graphics context that can be used to draw on this component.
         */
        public void paintComponent(Graphics g)
        {
            
            if(graphImage != null) {
                g.drawImage(graphImage, 0, 0, null);
            }
        }
    }
}

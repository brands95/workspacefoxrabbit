import java.awt.*;

class Histogram extends Canvas {
  static final double aspectRatio=0.8;
  boolean dirty=true;
  int histWidth;
  int histHeight;
  int boxWidth;
  int boxHeight;
  double loLim;    // lowest box holds all below this
  double hiLim;    // highest box holds all above this
  int count;       // number of boxes between limits
  int xCount;      // number of boxes across the bottom
  double interval;
  int [] tally;

  public Histogram(double low, double high, int count) {
    loLim=low;
    hiLim=high;
    this.count=count;
    xCount=count+2;
    interval=(high-low)/count;
    tally=new int[xCount];
    return;
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void paint(Graphics g) {
    int i,j,t;

    if (dirty || histWidth!=size().width) {
      dirty=false;
      histWidth=size().width;
      histHeight=size().height;
      boxWidth=(int)(histWidth/(xCount));
      boxHeight=(int)(boxWidth*aspectRatio);
      g.clearRect(0,0,histWidth-1,histHeight-1);
      g.setColor(Color.blue);
      g.drawRect(0,0,histWidth-1,histHeight-1);
      g.setColor(Color.black);
    }  // endif new draw is required

    /* Draw entire histogram */
    for (i=1; i<xCount-1; i++) {
      t=tally[i];
      if (t>0) {
        for (j=0; j<t; j++) {
          g.drawRect(i*boxWidth, histHeight-(j+1)*boxHeight,
                     boxWidth-1, boxHeight-1);
        }
      }
    }  // end draw histogram
    /* Do outliers in red */
    g.setColor(Color.red);
    t=tally[0];
    if (t>0) {
      for (j=0; j<t; j++) {
        g.drawRect(0, histHeight-(j+1)*boxHeight,
                   boxWidth-1, boxHeight-1);
      }
    }
    t=tally[xCount-1];
    if (t>0) {
      for (j=0; j<t; j++) {
        g.drawRect((xCount-1)*boxWidth, histHeight-(j+1)*boxHeight,
                   boxWidth-1, boxHeight-1);
      }
    }  // end upper outliers
  }  // end paint

  public void add(double value) {
    int position;
    position=(int)((value-loLim+interval)/interval);
    if (position<0) {
      position=0;
    } else if (position>=xCount) {
      position=xCount-1;
    }
    tally[position]++;
    repaint();
    return;
  }

  public void clear() {
    for (int i=0; i<xCount; i++) tally[i]=0;
    dirty=true;
    repaint();
    return;
  }

  // end class Histogram

  public static void main(String[] args)
  {
	new Histogram(0, 10, 3);
  }
}
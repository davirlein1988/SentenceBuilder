import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A panel that can draw Koch Curves, Sierpinski Triangles and Sierpinski Carpets,
 * with a set of controls where the user can select the type of fractal to be
 * drawn and the level of recursion.  There is also a set of checkboxes that
 * determine which of the 9 possible sections are included in a Sierpinski Carpet.
 */
public class Fractals1 extends JPanel {
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Fractal Fun 1");
		window.setContentPane( new Fractals1() );
		window.pack();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation( (screensize.width - window.getWidth())/2, (screensize.height - window.getHeight())/2 );
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	/**
	 * A class representing the display area where the fractals are drawn.
	 */
	private class Display extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int level = recursionLevel.getSelectedIndex() + 1;
			int w = getWidth();
			int h = getHeight();
			if (selectKoch.isSelected()) {
				g.setColor(Color.RED);
				drawKochCurve(g, level, 10, h/2, w-10, h/2);
			}
			else if (selectTriangle.isSelected()) {
				g.setColor(Color.BLUE);
				drawSierpinskiTriangle(g, level, w/2, 10, 20, h-50, w-20, h-50);
			}
			else {
				g.setColor(new Color(0,180,0));
				boolean[][] showSection = new boolean[3][3];
				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++)
						showSection[i][j] = carpetDesignBox[i][j].isSelected();
				drawSierpinskiCarpet(g, level, showSection, 20, 20, w-20, h-20);
			}
		}
	}
	

	private JRadioButton selectKoch;     // When selected, a Koch Curve is drawn.
	private JRadioButton selectTriangle; // When selected, a Sierpinski Triangle is drawn.
	private JRadioButton selectCarpet;   // When selected, a Sierpinski Carpet is drawn.

	private JCheckBox[][] carpetDesignBox;  // carpetDesignBox[i][j] tells whether to include
	                                        // the section in row i, column j in the carpet,
	                                        // for i and j from 0 to 3.  These checkboxes are
	                                        // enabled only if Sierpinski Carpets are selected.
	                 

	private Display display;           // The display area.
	private JComboBox recursionLevel;  // For selecting the level of recursion.
	
	/**
	 * Set up the user interface, and install a listener to repaint the display
	 * whenever one of the settings is changed.  Also, the recursion level is set
	 * back to 1 when a new type of fractal is selected.
	 */
	public Fractals1() {
		
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean carpet = selectCarpet.isSelected();
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++)
						carpetDesignBox[i][j].setEnabled(carpet);
				}
				if (evt.getSource() instanceof JRadioButton)
					recursionLevel.setSelectedIndex(0);
				display.repaint();
			}
		};
		
		display = new Display();
		display.setBackground( Color.WHITE );
		display.setPreferredSize( new Dimension(600, 600) );
		
		JPanel carpetDesign = new JPanel();
		carpetDesign.setBackground(Color.WHITE);
		carpetDesign.setLayout(new GridLayout(3,3,5,5));
		carpetDesign.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		carpetDesignBox = new JCheckBox[3][3];
		for (int i = 0; i< 3; i++) {
			for (int j = 0; j < 3; j++) {
				carpetDesignBox[i][j] = new JCheckBox();
				carpetDesignBox[i][j].addActionListener( listener );
				carpetDesignBox[i][j].setEnabled(false);
				carpetDesignBox[i][j].setSelected(true);
				carpetDesign.add(carpetDesignBox[i][j]);
			}
		}
		carpetDesignBox[1][1].setSelected(false);
		
		JPanel fractalSelect = new JPanel();
		fractalSelect.setBackground(Color.WHITE);
		fractalSelect.setLayout(new GridLayout(3,1,5,5));
		fractalSelect.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		ButtonGroup group = new ButtonGroup();
		selectKoch = new JRadioButton("Koch Curve");
		selectKoch.addActionListener(listener);
		group.add(selectKoch);
		fractalSelect.add(selectKoch);
		selectTriangle = new JRadioButton("Sierpinski Triangle");
		selectTriangle.addActionListener(listener);
		group.add(selectTriangle);
		fractalSelect.add(selectTriangle);
		selectCarpet = new JRadioButton("Sierpinski Carpet");
		selectCarpet.addActionListener(listener);
		group.add(selectCarpet);
		fractalSelect.add(selectCarpet);
		selectKoch.setSelected(true);
		
		recursionLevel = new JComboBox();
		recursionLevel.addItem("1 level of recursion");
		for (int i = 2; i <= 8; i++)
			recursionLevel.addItem( i + " levels of recursion");
		recursionLevel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		recursionLevel.addActionListener(listener);
		
		Box left = Box.createVerticalBox();
		left.setBackground(Color.WHITE);
		left.setOpaque(true);
		left.add(recursionLevel);
		left.add(Box.createVerticalStrut(15));
		left.add(fractalSelect);
		left.add(Box.createVerticalStrut(15));
		left.add(carpetDesign);
		left.add(Box.createVerticalGlue());
		
		setLayout(new BorderLayout(3,3));
		setBackground(Color.DARK_GRAY);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		add(display, BorderLayout.CENTER);
		add(left, BorderLayout.WEST);
		
	}

	
	/**
	 * Draw a Koch curve with a given level of recursion from
	 * the point A=(ax,ay) to the point B=(bx,by).  The level should
	 * be greater than or equal to 1.  A curve of level 1
	 * is just a straight line.
	 */
	void drawKochCurve(Graphics g, int level, double ax, double ay, 
			double bx, double by) {
		if (level <= 1) {
			g.drawLine((int)ax,(int)ay,(int)bx,(int)by);
		}
		else {
			
			double sqrt3 = Math.sqrt(3);

			/* Draw level-1 Koch curves from A to C, from C to
			 * D, from D to E, and from E to B, where C, D, E
			 * are computed as follows. */

			double cx = (2*ax + bx)/3;  // C is 1/3 of the way from A to B
			double cy = (2*ay + by)/3;

			double ex = (ax + 2*bx)/3;  // E is 1/3 of the way from B to A
			double ey = (ay + 2*by)/3;

			double hx = (ax + bx)/2; // Half-way point from A to B (not used in drawing)
			double hy = (ay + by)/2;

			double dx = hx + sqrt3*(ey-hy);  // D is the 3rd vertex of an equilateral triangle with C and E
			double dy = hy - sqrt3*(ex-hx);

			drawKochCurve(g, level-1, ax, ay, cx, cy);  // Draw the four Koch curves
			drawKochCurve(g, level-1, cx, cy, dx, dy);
			drawKochCurve(g, level-1, dx, dy, ex, ey);
			drawKochCurve(g, level-1, ex, ey, bx, by);

		}
	}

	/**
	 *  Draw a Sierpinski Trinagle with a given recursion level, with vertices at
	 *  the points A=(ax,ay), B=(bx,by), and C=(cx,cy).  For recursion level 1,
	 *  a triangle is drawn with these vertices.  For recursion level greater than 1,
	 *  three smaller Sierpinski Triangles, with recursion level one smaller, are
	 *  drawn.
	 */
	public void drawSierpinskiTriangle(Graphics g, double level, 
			             double ax, double ay, double bx, double by, double cx, double cy) {
		g.drawString( "Triangle level " + level + " not implementd.", 20, 30);
		
	}

	/**
	 * Draw a Sierpinski Carpet with a a given recursion level and with vertices at
	 * (x1,y1), (x1,y2), (x2,y1), and (x2,y2).  (That is, x1, y1, x2, y2 give oppisit
	 * corners of the rectangle where the carpet is drawn. For recursion level 1,
	 * a rectangle is drawn.  For recursion level greater than 1, several Sierpinski
	 * Carpets with recursion level one smaller are drawn.  There are 9 possible
	 * smaller carpets that can be drawn, arranged in 3 rows of 3.  The array
	 * showSection[][] tells which of these 9 small carpets to draw -- the one
	 * in row i, column j is drawn if showSection[i][j] is true.
	 */
	public void drawSierpinskiCarpet(Graphics g, double level, boolean[][] showSection, 
			                                   double x1, double y1, double x2, double y2) {
		g.drawString( "Carpet level " + level + " not implementd.", 20, 30);
	}

}

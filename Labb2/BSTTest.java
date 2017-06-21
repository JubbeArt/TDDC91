import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Scanner;
import java.io.*;

public class BSTTest extends JPanel implements ActionListener {
	public static final long serialVersionUID = 3L;
	/* The binary tree */
	private BST tree = null;

	/* The node location of the tree */
	private HashMap<Node, Rectangle> nodeLocations = null;

	/* The sizes of the subtrees */
	private HashMap<Node, Dimension> subtreeSizes = null;

	/* Do we need to calculate locations */
	static private boolean dirty = true;

	/* Space between nodes */
	private int parent2child = 20, child2child = 30;

	/* Helpers */
	private Dimension empty = new Dimension(0, 0);

	private FontMetrics fm = null;

	public BSTTest(BST tree) {
		this.tree = tree;
		nodeLocations = new HashMap<Node, Rectangle>();
		subtreeSizes = new HashMap<Node, Dimension>();
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	// calculate node locations
	private void calculateLocations() {
		nodeLocations.clear();
		subtreeSizes.clear();
		Node root = tree.getRoot();
		if (root != null) {
			calculateSubtreeSize(root);
			calculateLocation(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
		}
	}

	// calculate the size of a subtree rooted at n
	private Dimension calculateSubtreeSize(Node n) {
		if (n == null)
			return new Dimension(0, 0);
		Dimension ld = calculateSubtreeSize(n.left);
		Dimension rd = calculateSubtreeSize(n.right);
		int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
		int w = ld.width + child2child + rd.width;
		Dimension d = new Dimension(w, h);
		subtreeSizes.put(n, d);
		return d;
	}

	// calculate the location of the nodes in the subtree rooted at n
	private void calculateLocation(Node n, int left, int right, int top) {
		if (n == null)
			return;
		Dimension ld = (Dimension) subtreeSizes.get(n.left);
		if (ld == null)
			ld = empty;
		Dimension rd = (Dimension) subtreeSizes.get(n.right);
		if (rd == null)
			rd = empty;
		int center = 0;
		if (right != Integer.MAX_VALUE)
			center = right - rd.width - child2child / 2;
		else if (left != Integer.MAX_VALUE)
			center = left + ld.width + child2child / 2;
		int width = fm.stringWidth("" + n.key);
		Rectangle r = new Rectangle(center - width / 2 - 3, top, width + 6, fm
				.getHeight());
		nodeLocations.put(n, r);
		calculateLocation(n.left, Integer.MAX_VALUE, center - child2child / 2,
				top + fm.getHeight() + parent2child);
		calculateLocation(n.right, center + child2child / 2, Integer.MAX_VALUE,
				top + fm.getHeight() + parent2child);
	}

	// draw the tree using the pre-calculated locations
	private void drawTree(Graphics2D g, Node n, int px, int py, int yoffs) {
		if (n == null)
			return;
		Rectangle r = (Rectangle) nodeLocations.get(n);
		g.draw(r);
		g.drawString("" + n.key, r.x + 3, r.y + yoffs);
		if (px != Integer.MAX_VALUE)
			g.drawLine(px, py, r.x + r.width / 2, r.y);
		drawTree(g, n.left, r.x + r.width / 2, r.y + r.height, yoffs);
		drawTree(g, n.right, r.x + r.width / 2, r.y + r.height, yoffs);
	}

	public void paint(Graphics g) {
		super.paint(g);
		fm = g.getFontMetrics();
		// if node locations not calculated
		if (dirty) {
			calculateLocations();
			dirty = false;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(getWidth() / 2, parent2child);
		drawTree(g2d, tree.getRoot(), Integer.MAX_VALUE, Integer.MAX_VALUE, fm
				.getLeading()
				+ fm.getAscent());
		fm = null;
	}

	static void printMenu() {
		final String newline = System.getProperty("line.separator");

		String strMenu = "+--- Binary trees ---" + newline;
		strMenu += "r : Reset tree" + newline;
		strMenu += "i : Insert string" + newline;
		strMenu += "f : Find string" + newline;
		strMenu += "d : Delete string" + newline;
		strMenu += "p : traverse Preorder" + newline;
		strMenu += "l : traverse Levelorder" + newline;
		strMenu += "q : Quit program" + newline;
		strMenu += "h : show this text" + newline;
		System.out.print(strMenu);
	}

	static char getChar(Scanner in) {
		String tmp;

		do {
			tmp = in.nextLine();
		} while (tmp.length() != 1);

		return tmp.charAt(0);
	}

	static int getInt(Scanner in) {
		int tmp;

		while (!in.hasNextInt()) {
			in.nextLine();
		}

		tmp = in.nextInt();
		in.nextLine();

		return tmp;
	}

	static String getString(Scanner in) {
		return in.nextLine();
	}

	/**
	 * Fetch the entire contents of a text file, and return it in a String.
	 */
	static public String getContents(File aFile) {

		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time

			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null; // not declared within while loop

				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return contents.toString();
	}

	public static void main(String[] args) {

		
		//myTest();
		///return;
		if (args.length != 0) { // check command line flags

			System.out.println("Usage: java BSTTest ");

		} else { // no flag
			BST tree = new BST();

			JFrame f = new JFrame("BST");
			f.getContentPane().add(new BSTTest(tree));
			// create and add an event handler for window closing event
			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			f.setBounds(50, 50, 600, 400);
			f.setVisible(true);

			char c;
			int k;
			String v;
			Scanner in = new Scanner(System.in);
			printMenu();
			for (;;) {
				System.out.print("lab > ");
				System.out.flush();
				c = getChar(in);

				switch (c) {
				case 'r':
					tree = new BST();
					f.dispose();
					f = new JFrame("BST");
					f.getContentPane().add(new BSTTest(tree));
					// create and add an event handler for window closing event
					f.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							System.exit(0);
						}
					});
					f.setBounds(50, 50, 600, 400);
					f.setVisible(true);
					break;
				case 'i':
					System.out.print("Insert key: ");
					System.out.flush();
					k = getInt(in);
					System.out.print("Insert value: ");
					System.out.flush();
					v = getString(in);
					tree.insert(k, v);
					System.out
							.print("Inserted key=" + k + " value=" + v + "\n");
					System.out.flush();
					dirty = true;
					f.repaint();
					break;
				case 'd':
					System.out.print("Delete key: ");
					System.out.flush();
					k = getInt(in);
					tree.remove(k);
					dirty = true;
					f.repaint();
					break;
				case 'f':
					System.out.print("Find key: ");
					System.out.flush();
					k = getInt(in);
					String str = tree.find(k);
					if (str != null)
						System.out.print("Found key:" + k + " with value:"
								+ str + "\n");
					else
						System.out.print("Key:" + k + " not found\n");
					break;
				case 'q':
					System.out.println("Program terminated.");
					System.exit(0);
					break;
				case 'p':
					PreorderIterator po = tree.preorder();
					while (po.hasNext()) {
						Node tmp = po.next();
						System.out.println("Key=" + tmp.key + " Value="
								+ tmp.val);
					}
					break;
				case 'l':
					LevelorderIterator lo = tree.levelorder();
					while (lo.hasNext()) {
						Node tmp = lo.next();
						System.out.println("Key=" + tmp.key + " Value="
								+ tmp.val);
					}
					break;
				case 'h':
					printMenu();
					break;
				default:
					System.out.print("**** Sorry, No menu item named '");
					System.out.print(c + "'\n");
					System.out.flush();
				}
			}
		}
	}
	
	public static void  myTest() {
		final int nums = 50;
		final int gap = 11;	
		final char[] tests = new char[nums];
		Random generator = new Random(19580427);
		BST tree = new BST();

		for (int i = gap; i != 0; i = (i + gap) % nums) {
		  final char ch = (char) (33 + generator.nextInt(59));
		  tree.insert(i,String.valueOf(ch));
		  tests[i] = ch;
		}
		
		//Här kollar vi storlek och form på trädet

		//Vi tar bort element
		for (int i = 1; i < nums; i += 2) {
		  tree.remove(i);
		}
		
		//	Här kollar vi storlek och form på trädet

		//Vi tar bort samma element igen och kollar storlek och form på trädet.

		//Vi kontrollerar sedan att rätt element finns kvar i trädet

		for (int i = 2; i < nums; i += 2) {
		  if(!tree.find(i).equals(String.valueOf(tests[i])))
			  System.out.println("find: " + tree.find(i) + " || tst: " + tests[i]);		  
		}

		//Sedan kontrollerar vi att de borttagna elementen verkligen är borta

		for (int i = 1; i < nums; i += 2) {
		  System.out.println(tree.find(i));
		  System.out.println(tree.find(i));
			if(tree.find(i) == null)
			  System.out.println("You lost, not null");
		}
/*
		//För att sedan sätta in dem igen
		for (int i = 1; i < nums; i += 2) {
		  tree.insert(i,String.valueOf(tests[i]));
		}

		//Här kollar vi storlek och form på trädet

		//Sedan tar vi bort de andra elementen i stället

		for (int i = 2; i < nums; i += 2) {
		  tree.remove(i);
		} */

		//Här gör vi en sista kontroll av storlek och form på trädet.
	}
}

/*
 * @author Jerry Xia
 * ID 260917329
 * 
 * This program creates a GUI, in the form of a calculator, that allows
 * the user to perform operations with doubles and integers, and returns the
 * answer in the form of an integer.
 */

/* I didn't know whether to use 'x' or '*' for the multiplication sign, as the text needed to display uses * but
 * the calculator uses an x. In this code I used an asterisk (*) to display multiplication which is the same in the
 * JCalc.java class
 */

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import acm.gui.TableLayout;
import acm.program.Program;


public class JCalcGUI extends Program {

	/*
	 * creating default variables
	 * @param in - JTextField that displays the text entered in by the user
	 * @param out - JTextField that displays the final result from the program
	 * @param outWasAppeared - boolean that indicates when the out JTextField has text
	 * @param infix - String that is displayed to the user in JTextField 'in' and is used for
	 * the postFix algorithm in the JCalc.java class
	 * @param resultCalc - double that is displayed in the 'out' JTextField
	 */
	
	JTextField in = new JTextField("");
	JTextField out = new JTextField("");
	boolean outWasAppeared;
	String infix = "";
	double resultCalc;
		
	
		//init() method which creates the calculator GUI
		public void init() {
			setSize(1000, 1000);	//sets size
			setLayout(new TableLayout(8, 4));	//creates an 8 x 4 table that includes all operators and operands
		
			in.setEditable(false);	//user cannot directly edit text through the GUI
			
			//to add the JTextFields we need the object itself and a constraint, namely in the form of String
			add(in, "gridwidth = 4 height = 25");
			add(out,"gridwidth = 4 height = 25");
		
			//initializing instance variables for button sizes, as well as button_label array list
			String BUTTON_LENGTH = "65";
			String BUTTON_WIDTH = "95";
			//array list created with operators and C function
			//the labels are in this order to simulate the appearance of an actual calculator
			String button_label[]= {"C","(",")","/","7","8","9","*","4","5","6","-","1","2","3","+","0",".","^","="};
		
			//String constraint used for button width and height
			String constraint = "width=" + BUTTON_WIDTH + " height=" + BUTTON_LENGTH;
		
			//for loop that sets up the operators and operands
			for (int i = 0; i < button_label.length; i++) {
		
				//creates new JButton with data in array list
				JButton cur_button = new JButton(button_label[i]);
				
				//sets the current button to font Arial, with size 20
				cur_button.setFont(new Font("Arial", Font.LAYOUT_NO_START_CONTEXT, 20));
				cur_button.addActionListener(this);	//adds action listener
				//adds the button
				add(cur_button, constraint);
		
			}
		}	//end of init() method
		
		//sets up the actionPerformed method
		public void actionPerformed(ActionEvent e) {
			
			//if = is selected by the user
			if(e.getActionCommand()=="=") {
				//creates new JCalc, which contains the postFix method which is then used
				JCalc calculation = new JCalc();

				resultCalc = calculation.postFix(infix);
				//prints out the text
				out.setText(""+resultCalc);
				//because text is displayed, boolean outWasAppeared = true
				outWasAppeared = true;
			}
			//else if Clear (C) is selected, text is cleared as well as the string that was created
			else if(e.getActionCommand()=="C") {
				in.setText("");
				infix = "";
				out.setText("");
				
			}
			//for all other cases (numbers, operators, etc.)
			else
			{
				if(outWasAppeared)	//if there is nothing on the output display
				{
					//set outWasAppeared equal to false
					outWasAppeared = false;
					//set the text to blank
					in.setText("");	
					//infix is re-initialized
					infix = "";
					//set out text to blank
					out.setText("");
				}

				//user input adds onto string infix
				infix += e.getActionCommand();
				//input text is displayed on in JTextField
				in.setText(""+infix);
			}
		}
		
}

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PasswordChecker {

	// UI code referenced from stackoverflow!
	// You can view the code here! --> https://stackoverflow.com/questions/7130902/how-to-use-jprogressbar-as-password-strength-meter-it-should-change-color-and-va
    private JPanel mainPanel = new JPanel();
    private JPasswordField field = new JPasswordField(50);
    private JLabel label = new JLabel();
    private JLabel labelLength = new JLabel();
    private JProgressBar progressBar = new JProgressBar(0, 50);

    public PasswordChecker() {
        field.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            private void updateLabel(DocumentEvent e) {
            	// main addition here as we are creating requirements and COLOR to our bar
            	// Create array (Map, or List could work as well) to keep track of our character types
                  int[] chars = new int[4];

                String text = field.getText();//just example getText() is Depreciated !!!
                label.setText(text);
                labelLength.setText("The password must have at least two of every type character "
                		+ "(lower & uppercase, numbers & specialchars");
                // if the text length is 0, you haven't even attempted to create a password. No need for color.
                if (text.length() < 1) {
                    progressBar.setValue(0);
                    // else we continue our requirements
                } else {
                	// loop through each character and add to the array
                	// if one of the indices in the array are EMPTY automatically RED
                	// if there's at ONLY one in one of the types its YELLOW
                	// all of them FILLED in means it's GREEN
                	
                	// loop through each character in our string
                	for (int i = 0; i < text.length(); i++) {
                		
                		char character = text.charAt(i);
                		// use ascii to determine what type of character the character is!
                		int charactersNum = (int) character;
                		
                		//lowercase letters
                		if  (charactersNum >= 97 && charactersNum <= 122){
                			chars[0]++;
                		}
                		// numbers
                		else if (charactersNum >= 48 && charactersNum <= 57) {
                			chars[1]++;
                		}
                		// uppercase
                		else if (charactersNum >= 65 && charactersNum <= 90) {
                			chars[2]++;
                		}
                		// rest are special characters
                		else {
                			chars[3]++;
                		}
                	}
                	
                    // if each character requirement is fulfilled by 2, its green
                    if (chars[0] >= 2 && chars[1] >= 2 && chars[2] >= 2 && chars[3] >= 2) {
                    	progressBar.setForeground(Color.green);
                    	progressBar.setValue(50);

                    }
                    
                    else {
                    	// else if any type character has at least one, we have to check the rest of the characters as well
                    	if (chars[0] > 0 || chars[1] > 0 || chars[2] > 0 || chars[3] > 0) {
                        	for (int i = 0; i < chars.length; i++) {
                        		if (chars[i] == 0) {
                        			// if we find that one of the type characters still has 0, make it red!
                                	progressBar.setForeground(Color.red);
                                	progressBar.setValue(10);
                        			break;
                        		}
                        		// else the password still only has one type character and is yellow!
                        		else if (chars[i] == 1 && chars[(i+1)%4] >= 1) {
                        			progressBar.setForeground(Color.yellow);
                                	progressBar.setValue(25);
                        		}
                        	}
                        }
                    }
                    
                }
            }
        });
        
        mainPanel.setLayout(new GridLayout(4, 0, 10, 0));
        mainPanel.add(field);
        mainPanel.add(label);
        mainPanel.add(labelLength);
        mainPanel.add(progressBar);
        progressBar.setBackground(Color.white);
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    private static void createAndShowUI() {
        JFrame frame = new JFrame("Password Strength Checker for TLT");
        frame.getContentPane().add(new PasswordChecker().getComponent());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowUI();
            }
        });
    }
}

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SpringLayout;

/**
 * @author Jade Gorton Last Edited: 27/11/2013 1:51 PM Last Code Edited: SetMode
 * Function Created program ready for release.
 */
public class Java2 extends Frame implements ActionListener, WindowListener {

    /**
     * @param args the command line arguments
     */
    /*
     * Declaration of int global variables handle various requirements, like handling
     * Total Number of text fields
     * Close Button
     */
    private int totalY = 25;
    private int totalX = 12;
    /*
     * Variable TextField[] fields Holds TextField Objects in an Array
     * So TextFields instead refereed too using names is instead
     * Created using array Instead, This allowed quicker
     * Automation of locate a text field function by
     * Running function in For Loop Where length of loop
     * Is length of TextFieldArray. Also Reason storing TextFieldName
     * Using Previous Java Project I was able include code from similar project
     * Project did not however require Table like structure
     * So instead 2 dimensional array was just singular
     */
    private TextField[][] fields = new TextField[totalY][totalX];
    /*
     * Intializing Global Variables for Buttons as varible names
     * Buttons where more diffiuclt Automate using Arrays
     * Since Buttons Events I assume need be Array Index
     * close button is simple and only exits the program
     * No other functionality was nneded be included.
     */
    private Button close;
    /*
     * Intializing Global Variables for String holding name for text file
     * Holding information needed put into the table
     */
    private String textName = "Answers.csv";

    /**
     * Entry point to the class and application.
     *
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        Java2 frame = new Java2();
        frame.run();
    }

    /**
     * Main function that runs all other functions required Run the program
     */
    private void run() {
        setBounds(0, 0, 1820, 800);
        setTitle("Java Assignment By Jade Robert Gorton");
        this.addWindowListener(this);
        startLayout();
        SetScore();
        SetAverage();
        SetMode();
        setVisible(true);
    }

    /**
     * Controlling method for setting the layout, calls setLayout Start Data
     * that both require spring layout
     */
    private void startLayout() {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        startData(layout);
        close = new Button("Close");
        add(close);
        close.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, close, 225 + 20 * totalY, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, close, 100 + 25 * totalY, SpringLayout.NORTH, this);
        readFile(textName);
    }

    /**
     * Method with low coupling and high cohesion for adding individual
     * textboxes: - reduces overall code, especially in the LocateTextFields
     * method. - makes this method re-usable with minimal adjustment as it is
     * moved from one program to another. This function requires Text Field
     * Layout, Text Field Object, width, x position, y position Returns
     * myTextField the Text Field Object This function controls creation of new
     * Text Field by organize code in reusable Manner Code creates new labels on
     * screen for naming text fields combo boxes. This function was reused start
     * Data, was method transformed into what needed modifying locateTextField
     * from Previous project Modifying it simply in double for loop Add Text
     * Fields in Array, in table layout
     */
    private void startData(SpringLayout layout) {
        for (int y = 0; y < totalY; y++) {
            for (int x = 0; x < totalX; x++) {
                fields[y][x] = new TextField(12);
                if (x == totalX - 1 || y == totalY - 1) {
                    fields[y][x].setBackground(new Color(153, 255, 153));
                    fields[y][x].setEditable(false);
                }

                if ((y == totalY - 2 || y == totalY - 1) && (x != totalX && y != totalY - 1 || x == totalX - 1)/*&& (y != totalY && y > 2)*/ || x == 0 && y != totalY - 1) {
                    fields[y][x].setBackground(new Color(255, 255, 153));
                    fields[y][x].setEditable(false);
                }
                if ((y != 1 || x != totalX) && (y != totalY - 2 && (y != 2)) && (y != 1 || x != totalX - 1)) {
                    add(fields[y][x]);
                    layout.putConstraint(SpringLayout.WEST, fields[y][x], x * 110 + 1, SpringLayout.WEST, this);
                    layout.putConstraint(SpringLayout.NORTH, fields[y][x], y * 25 + 1, SpringLayout.NORTH, this);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            System.exit(0);
        }
    }

    /**
     * This function goes through number
     * For each student set score
     * Compare student answer with correct answer
     * Add +1 too score if true
     * Once all answers checked set the score
     * @return void
     */
    public void SetScore() {
        int score = 0;
        for (int y = 1; y < totalY - 2; y++) {
            for (int x = 1; x < totalX - 1; x++) {
                String text = fields[y][x].getText();
                if (text.equalsIgnoreCase(fields[1][x].getText())) {

                    score++;
                }
            }
            fields[y][totalX - 1].setText(String.valueOf(score));
            score = 0;
        }
    }

    /**
     * This function goes through number
     * Count all students results
     * And print average
     * And set average for text field assign for average
     * @return void
     */
    public void SetAverage() {
        int score = 0;
        String text = null;
        for (int y = 3; y < totalY - 2; y++) {
            text = fields[y][totalX - 1].getText();
            score += Integer.parseInt(text);
        }
        double avg = (score + 0.0) / (totalY - 5);
        fields[totalY - 1][totalX - 1].setText(String.valueOf(avg));
    }

    /**
     * This function goes through number
     * This function Gets mode from string array and returns string value
     * it compares X value of the array with i
     * Each element is compared too all other
     * and counts each element, once loop is finished,
     * sets most common string once new max count has happened.
     * @return Return most common value in the array
     */
    public String GetMode(String[] strArray) {
        int count;
        count = 0;
        int Max = 0;
        int Index = 0;
        String ItemOne, ItemTwo;
        for (int x = 0; x < strArray.length; x++) {
            for (int i = 0; i < strArray.length; i++) {
                ItemOne = strArray[x];
                ItemTwo = strArray[i];
                if (ItemOne.compareToIgnoreCase(ItemTwo) == 0) {
                    count++;
                }
                if (count > Max) {
                    Max = count;
                    Index = x;
                }
            }
            count = 0;
        }
        return strArray[Index];
    }

    /**
     * This function goes through number This function calls, Mean find Most
     * common value and Sets Mode too the table. Array go through Table data
     * that needed only find out answers Creates Array too pass for GetMode
     * Function Z, is index for array. XY are textfields in table where we
     * getting data
     * this function sets each text field in mode row.
     */
    public void SetMode() {
        String[] strArrColumn = new String[totalY - 5];
        fields[totalY - 1][0].setText("Mode");
        int z = 0;
        for (int x = 1; x < totalX - 1; x++) {
            for (int y = 3; y < totalY - 2; y++) {
                if (fields[y][x].getText() != null || fields[y][x].getText() != "") {
                    strArrColumn[z] = fields[y][x].getText();
                    z++;
                }
            }
            z = 0;
            String StrTemp = GetMode(strArrColumn);
            fields[totalY - 1][x].setText((StrTemp != null ? StrTemp : "Null"));
        }
    }

    /**
     * Read in the data from the data file - Emissions.txt for starters
     * Answers.csv - one line at a time and Everything not stored in arrays just
     * set text for table manage table text layout.
     * Using Short Hand If, I looped through, creating Concat two Strings create first Row that
     * Labels Questions for answer
     */
    private void readFile(String fileName) {
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            int y = 1;
            String[] Questions = new String[12];
            fields[0][0].setText(Questions[0]);
            
            for (int i = 0; i < Questions.length; i++) {
                Questions[i] = (y != 0 && i == 0) ? "Qns" : (y != 0 && i == totalX - 1) ? "Results" : "Q" + i;
                fields[0][i].setText(Questions[i]);
            }

            while ((line = br.readLine()) != null) {
                String temp[] = line.split(",");
                for (int x = 0; x < temp.length; x++) {
                    if ((y != 1 || x != totalX) && (y != totalY - 2 && (y != 2)) && (y != 1 || x != totalX - 1)) {
                        fields[y][x].setText(temp[x]);
                    } else {
                        y = y + 1;
                        x = -1;
                    }
                }
                y++;
            }
        } catch (Exception e) {
        }


    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}

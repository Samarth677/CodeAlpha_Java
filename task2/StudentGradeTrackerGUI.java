import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentGradeTrackerGUI extends JFrame {

    private JTextField gradeInputField;
    private JButton addButton, calculateButton;
    private JTextArea gradeListArea;
    private ArrayList<Double> grades;

    public StudentGradeTrackerGUI() {
        // Set up the frame
        setTitle("Student Grade Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        grades = new ArrayList<>();

        // Grade input section
        JPanel inputPanel = new JPanel();
        gradeInputField = new JTextField(10);
        addButton = new JButton("Add Grade");
        inputPanel.add(new JLabel("Enter Grade:"));
        inputPanel.add(gradeInputField);
        inputPanel.add(addButton);
        add(inputPanel, BorderLayout.NORTH);

        // Area to display the list of grades
        gradeListArea = new JTextArea(10, 30);
        gradeListArea.setEditable(false);
        add(new JScrollPane(gradeListArea), BorderLayout.CENTER);

        // Button to calculate and show results
        calculateButton = new JButton("Calculate Results");
        add(calculateButton, BorderLayout.SOUTH);

        // Action listener to add grades
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String gradeText = gradeInputField.getText();
                try {
                    double grade = Double.parseDouble(gradeText);
                    if (grade >= 0 && grade <= 100) {
                        grades.add(grade);
                        gradeListArea.append("Grade: " + grade + "\n");
                        gradeInputField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a grade between 0 and 100.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric grade.");
                }
            }
        });

        // Action listener to calculate and show results in a pop-up window
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (grades.size() > 0) {
                    double average = computeAverage(grades);
                    double highest = findHighest(grades);
                    double lowest = findLowest(grades);

                    // Show the results in a pop-up window
                    JOptionPane.showMessageDialog(null, "Results:\n" +
                            "Average Grade: " + average + "\n" +
                            "Highest Grade: " + highest + "\n" +
                            "Lowest Grade: " + lowest);
                } else {
                    JOptionPane.showMessageDialog(null, "No grades entered yet.");
                }
            }
        });
    }

    // Method to compute the average grade
    public static double computeAverage(ArrayList<Double> grades) {
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    // Method to find the highest grade
    public static double findHighest(ArrayList<Double> grades) {
        double highest = grades.get(0);
        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }
        return highest;
    }

    // Method to find the lowest grade
    public static double findLowest(ArrayList<Double> grades) {
        double lowest = grades.get(0);
        for (double grade : grades) {
            if (grade < lowest) {
                lowest = grade;
            }
        }
        return lowest;
    }

    public static void main(String[] args) {
        // Run the GUI application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentGradeTrackerGUI().setVisible(true);
            }
        });
    }
}

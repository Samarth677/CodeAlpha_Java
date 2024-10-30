import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class PopUpChatbotGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField userInputField;
    private JButton sendButton;
    private HashMap<String, String> responses; // Keyword-based responses

    public PopUpChatbotGUI() {
        // Set up the frame
        setTitle("AI Chatbot");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // User input panel
        JPanel inputPanel = new JPanel();
        userInputField = new JTextField(20);
        sendButton = new JButton("Send");
        inputPanel.add(userInputField);
        inputPanel.add(sendButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Initialize responses
        initializeResponses();

        // Action listener for the send button
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserInput();
            }
        });

        // Optional: send message on Enter key press
        userInputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUserInput();
            }
        });
    }

    // Method to initialize keyword-based responses
    private void initializeResponses() {
        responses = new HashMap<>();
        responses.put("hello", "Hello! How can I help you today?");
        responses.put("how are you", "I'm just a computer program, but thanks for asking!");
        responses.put("what is your name", "I am an advanced chatbot created to assist you.");
        responses.put("bye", "Goodbye! Have a great day!");
    }

    // Method to handle user input
    private void handleUserInput() {
        String userInput = userInputField.getText().trim();
        if (!userInput.isEmpty()) {
            chatArea.append("You: " + userInput + "\n");
            userInputField.setText(""); // Clear the input field

            // Get the chatbot response
            String response = getChatbotResponse(userInput.toLowerCase());
            chatArea.append("Chatbot: " + response + "\n");
            showPopUp("Chatbot", response); // Show the response in a pop-up window
        }
    }

    // Method to get the chatbot's response based on user input
    private String getChatbotResponse(String userInput) {
        // Check for mathematical expressions
        if (isMathExpression(userInput)) {
            return evaluateMathExpression(userInput);
        }

        // Check for keyword-based responses
        for (String keyword : responses.keySet()) {
            if (userInput.contains(keyword)) {
                return responses.get(keyword);
            }
        }

        return "I'm sorry, I didn't understand that.";
    }

    // Method to check if the input is a mathematical expression
    private boolean isMathExpression(String input) {
        return input.matches(".*\\d+\\s*\\+\\s*\\d+.*") || // Addition
               input.matches(".*\\d+\\s*-\\s*\\d+.*") || // Subtraction
               input.matches(".*\\d+\\s*\\*\\s*\\d+.*") || // Multiplication
               input.matches(".*\\d+\\s*/\\s*\\d+.*"); // Division
    }

    // Method to evaluate a mathematical expression
    private String evaluateMathExpression(String expression) {
        try {
            String[] parts;
            double result = 0;

            // Handle addition
            if (expression.contains("+")) {
                parts = expression.split("\\+");
                result = Double.parseDouble(parts[0].trim()) + Double.parseDouble(parts[1].trim());
                return "Result: " + result;
            }
            // Handle subtraction
            else if (expression.contains("-")) {
                parts = expression.split("-");
                result = Double.parseDouble(parts[0].trim()) - Double.parseDouble(parts[1].trim());
                return "Result: " + result;
            }
            // Handle multiplication
            else if (expression.contains("*")) {
                parts = expression.split("\\*");
                result = Double.parseDouble(parts[0].trim()) * Double.parseDouble(parts[1].trim());
                return "Result: " + result;
            }
            // Handle division
            else if (expression.contains("/")) {
                parts = expression.split("/");
                double divisor = Double.parseDouble(parts[1].trim());
                if (divisor != 0) {
                    result = Double.parseDouble(parts[0].trim()) / divisor;
                    return "Result: " + result;
                } else {
                    return "Error: Division by zero.";
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return "Error: Invalid expression.";
        }
        return "I'm sorry, I didn't understand that.";
    }

    // Method to show pop-up messages
    private void showPopUp(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PopUpChatbotGUI().setVisible(true);
            }
        });
    }
}

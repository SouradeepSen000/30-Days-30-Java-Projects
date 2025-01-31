import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator {
    private JFrame frame;
    private JTextField num1Field, num2Field, resultField;
    private JComboBox<String> operationBox;
    
    public SimpleCalculator() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        frame = new JFrame("Calculator");
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Input fields panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("First Number:"), gbc);
        gbc.gridx = 1;
        num1Field = new JTextField(15);
        num1Field.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(num1Field, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Second Number:"), gbc);
        gbc.gridx = 1;
        num2Field = new JTextField(15);
        num2Field.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(num2Field, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Operation:"), gbc);
        gbc.gridx = 1;
        String[] operations = {"Addition", "Subtraction", "Multiplication", "Division", "Remainder"};
        operationBox = new JComboBox<>(operations);
        operationBox.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(operationBox, gbc);
        
        // Result panel
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setBackground(new Color(66, 134, 244));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 0;
        resultPanel.add(new JLabel("Result:"), gbc);
        gbc.gridx = 1;
        resultField = new JTextField(15);
        resultField.setFont(new Font("Arial", Font.PLAIN, 14));
        resultField.setEditable(false);
        resultField.setBackground(Color.WHITE);
        resultPanel.add(resultField, gbc);
        
        // Add panels to main panel
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(inputPanel, gbc);
        gbc.gridy = 1;
        mainPanel.add(calculateButton, gbc);
        gbc.gridy = 2;
        mainPanel.add(resultPanel, gbc);
        
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void calculate() {
        try {
            double a = Double.parseDouble(num1Field.getText());
            double b = Double.parseDouble(num2Field.getText());
            double result = 0;
            
            int choice = operationBox.getSelectedIndex();
            
            switch(choice) {
                case 0: // Addition
                    result = a + b;
                    break;
                case 1: // Subtraction
                    result = a - b;
                    break;
                case 2: // Multiplication
                    result = a * b;
                    break;
                case 3: // Division
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result = a / b;
                    break;
                case 4: // Remainder
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result = a % b;
                    break;
            }
            
            resultField.setText(String.format("%.2f", result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(frame, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleCalculator();
            }
        });
    }
}
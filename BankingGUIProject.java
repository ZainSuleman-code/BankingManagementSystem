import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankingGUIProject extends JFrame implements ActionListener {

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel;
    JTextField usernameField;
    JPasswordField passwordField;
    JLabel statusLabel;
    JLabel welcomeLabel;
    JLabel dateTimeLabel;
    double balance = 5000.00;
    List<String> transactions = new ArrayList<>();
    String currentUser = "";
    String accountNumber = "ACC-" + LocalDate.now().toString().replace("-", "") + "-001";

    Color backgroundColor = new Color(34, 40, 49);
    Color cardColor = new Color(57, 62, 70);
    Color buttonColor = new Color(0, 173, 181);
    Color hoverColor = new Color(0, 140, 150);
    Color textColor = new Color(238, 238, 238);
    Color errorColor = new Color(255, 87, 87);
    Color labelTextColor = new Color(180, 180, 180); // New darker label color

    Font mainFont = new Font("Segoe UI", Font.PLAIN, 16);
    Font titleFont = new Font("Segoe UI", Font.BOLD, 24);

    public BankingGUIProject() {
        setTitle("Banking Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Look and Feel not set.");
        }

        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createDashboardPanel(), "Dashboard");

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Secure Bank Portal", SwingConstants.CENTER);
        title.setFont(titleFont);
        title.setForeground(buttonColor);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(labelTextColor);
        userLabel.setFont(mainFont);

        usernameField = new JTextField(18);
        usernameField.setFont(mainFont);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(labelTextColor);
        passLabel.setFont(mainFont);

        passwordField = new JPasswordField(18);
        passwordField.setFont(mainFont);

        JButton loginBtn = new JButton("Login");
        loginBtn.setMaximumSize(new Dimension(120, 35));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButton(loginBtn, "Login to your account");

        statusLabel = new JLabel(" ");
        statusLabel.setFont(mainFont);
        statusLabel.setForeground(errorColor);

        JPanel loginButtonWrapper = new JPanel();
        loginButtonWrapper.setBackground(backgroundColor);
        loginButtonWrapper.add(loginBtn);

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = y; gbc.gridx = 0;
        panel.add(userLabel, gbc); gbc.gridx = 1;
        panel.add(usernameField, gbc); y++;

        gbc.gridy = y; gbc.gridx = 0;
        panel.add(passLabel, gbc); gbc.gridx = 1;
        panel.add(passwordField, gbc); y++;

        gbc.gridy = y++; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(loginButtonWrapper, gbc);

        gbc.gridy = y;
        panel.add(statusLabel, gbc);

        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);

        welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(titleFont);
        welcomeLabel.setForeground(buttonColor);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        JLabel accountInfo = new JLabel("Account #: " + accountNumber, SwingConstants.CENTER);
        accountInfo.setFont(mainFont);
        accountInfo.setForeground(textColor);
        accountInfo.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));

        dateTimeLabel = new JLabel(" ", SwingConstants.CENTER);
        dateTimeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        dateTimeLabel.setForeground(textColor);
        dateTimeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(cardColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 180, 20, 180));

        String[][] actions = {
            {"Check Balance", "View your current account balance"},
            {"Deposit", "Add money to your account"},
            {"Withdraw", "Withdraw money from your account"},
            {"View Transactions", "Check your recent transactions"},
            {"Account Summary", "Detailed account overview"},
            {"Logout", "Logout and return to login screen"}
        };

        for (String[] item : actions) {
            JButton button = new JButton(item[0]);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(300, 45));
            styleButton(button, item[1]);
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(15));
        }

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(accountInfo, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(dateTimeLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void styleButton(JButton button, String tooltip) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(hoverColor.darker(), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setToolTipText(tooltip);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(buttonColor);
            }
        });

        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand().trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        switch (action) {
            case "Login":
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    statusLabel.setText("Fields cannot be empty!");
                } else if (username.equalsIgnoreCase("admin") && password.equals("1234")) {
                    currentUser = "Admin";
                    welcomeLabel.setText("Welcome, " + currentUser + "!");
                    dateTimeLabel.setText("Date: " + formatter.format(LocalDateTime.now()) + " | Account: " + accountNumber);
                    cardLayout.show(mainPanel, "Dashboard");
                    statusLabel.setText("");
                } else {
                    statusLabel.setText("Invalid Username or Password!");
                }
                break;

            case "Check Balance":
                JOptionPane.showMessageDialog(this, "Current Balance: $" + balance, "Balance Inquiry", JOptionPane.INFORMATION_MESSAGE);
                break;

            case "Deposit":
                String depositInput = JOptionPane.showInputDialog(this, "Enter deposit amount:");
                if (depositInput != null) {
                    try {
                        double amount = Double.parseDouble(depositInput);
                        if (amount > 0) {
                            int confirm = JOptionPane.showConfirmDialog(this, "Confirm deposit of $" + amount + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                balance += amount;
                                transactions.add(formatter.format(LocalDateTime.now()) + " - Deposited: $" + amount);
                                JOptionPane.showMessageDialog(this, "Deposited $" + amount, "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;

            case "Withdraw":
                String withdrawInput = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");
                if (withdrawInput != null) {
                    try {
                        double amount = Double.parseDouble(withdrawInput);
                        if (amount > 0 && amount <= balance) {
                            int confirm = JOptionPane.showConfirmDialog(this, "Confirm withdrawal of $" + amount + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                balance -= amount;
                                transactions.add(formatter.format(LocalDateTime.now()) + " - Withdrew: $" + amount);
                                JOptionPane.showMessageDialog(this, "Withdrawn $" + amount, "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid or insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;

            case "View Transactions":
                StringBuilder history = new StringBuilder("Last 5 Transactions:\n\n");
                if (transactions.isEmpty()) {
                    history.append("No transactions yet.");
                } else {
                    int start = Math.max(transactions.size() - 5, 0);
                    for (int i = start; i < transactions.size(); i++) {
                        history.append(transactions.get(i)).append("\n");
                    }
                }
                JOptionPane.showMessageDialog(this, history.toString(), "Mini Statement", JOptionPane.INFORMATION_MESSAGE);
                break;

            case "Account Summary":
                String summary = "Account Summary:\n\n" +
                        "Name: " + currentUser + "\n" +
                        "Account Number: " + accountNumber + "\n" +
                        "Balance: $" + balance + "\n" +
                        "Date: " + formatter.format(LocalDateTime.now());
                JOptionPane.showMessageDialog(this, summary, "Account Summary", JOptionPane.INFORMATION_MESSAGE);
                break;

            case "Logout":
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    usernameField.setText("");
                    passwordField.setText("");
                    currentUser = "";
                    cardLayout.show(mainPanel, "Login");
                }
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankingGUIProject::new);
    }
}

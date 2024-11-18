package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginApp {

    private static ArrayList<User> userList = new ArrayList<>(); 
    private static JFrame loginFrame, mainFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginApp::showLogin); 
    }

    private static void showLogin() {
        loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 350);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null); 

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 248, 255));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Bienvenido");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userLabel = new JLabel("Usuario:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Contraseña:");
        JPasswordField passField = new JPasswordField(15);
        passField.setEchoChar('@'); 

        JButton loginButton = new JButton("Iniciar sesión");
        JButton registerButton = new JButton("Registrarse");

        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.setBackground(new Color(100, 149, 237));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        registerButton.setBackground(new Color(60, 179, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);

        
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Debe ingresar su usuario y contraseña.");
                return;
            }

            boolean found = false;
            for (User user : userList) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    found = true;
                    loginFrame.dispose();
                    showMainScreen();
                    break;
                }
            }

            if (!found) {
                errorLabel.setText("Usuario o contraseña incorrectos");
                errorLabel.setText("");
            }
        });

        registerButton.addActionListener(e -> {
            loginFrame.dispose();
            showRegister();
        });

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(errorLabel);

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    private static void showRegister() {
        JFrame registerFrame = new JFrame("Registro");
        registerFrame.setSize(400, 500);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Registro de Usuario");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField surnameField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JPasswordField confirmPasswordField = new JPasswordField(15);

        JButton submitButton = new JButton("Registrar");
        submitButton.setBackground(new Color(60, 179, 113));
        submitButton.setForeground(Color.WHITE);

        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);

        submitButton.addActionListener(e -> {
            String username = usernameField.getText();
            String name = nameField.getText();
            String surname = surnameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || name.isEmpty() || surname.isEmpty() ||
                phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                errorLabel.setText("Todos los campos son obligatorios.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                errorLabel.setText("Las contraseñas no coinciden.");
                return;
            }

            userList.add(new User(username, name, surname, phone, email, password));
            JOptionPane.showMessageDialog(registerFrame, "Registro exitoso.");
            registerFrame.dispose();
            showLogin();
        });

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("Usuario:"));
        panel.add(usernameField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nameField);
        panel.add(new JLabel("Apellido:"));
        panel.add(surnameField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(phoneField);
        panel.add(new JLabel("Correo:"));
        panel.add(emailField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(passwordField);
        panel.add(new JLabel("Confirmar Contraseña:"));
        panel.add(confirmPasswordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(submitButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(errorLabel);

        registerFrame.add(panel);
        registerFrame.setVisible(true);
    }

    private static void showMainScreen() {
        mainFrame = new JFrame("Usuarios Registrados");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        String[] columnNames = {"Nombre", "Teléfono", "Correo"};
        String[][] data = userList.stream()
                .map(user -> new String[]{user.getName(), user.getPhone(), user.getEmail()})
                .toArray(String[][]::new);
        JTable userTable = new JTable(data, columnNames);

        JButton logoutButton = new JButton("Cerrar sesión");
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);

        logoutButton.addActionListener(e -> {
            mainFrame.dispose();
            showLogin();
        });

        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);
        panel.add(logoutButton, BorderLayout.SOUTH);

        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }
}

class User {
    private String username, name, surname, phone, email, password;

    public User(String username, String name, String surname, String phone, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

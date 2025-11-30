import java.awt.*;
import javax.swing.*;

public class WelcomePage extends JFrame {
    private final JProgressBar progress = new JProgressBar(0, 100);
    private Timer loaderTimer = null;

    public WelcomePage() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 480); // Splash size
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Gradient background
        JPanel center = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth(), h = getHeight();
                g2.setPaint(new GradientPaint(0, 0, new Color(18, 113, 186), 0, h, new Color(14, 64, 120)));
                g2.fillRect(0, 0, w, h);
                g2.dispose();
            }
        };
        center.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel title = new JLabel("Bag-ang Dental Service");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        center.add(title, gbc);

        gbc.gridy++;
        JLabel subtitle = new JLabel("Quality Care • Friendly Service • Trusted Professionals");
        subtitle.setForeground(new Color(220, 235, 255));
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, 14f));
        center.add(subtitle, gbc);

        add(center, BorderLayout.CENTER);

        // Progress bar
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        progress.setStringPainted(true);
        progress.setForeground(new Color(14, 64, 120));
        bottom.add(progress, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        loaderTimer = new Timer(25, e -> {
            int value = progress.getValue() + 1;
            progress.setValue(value);
            progress.setString("Loading " + value + "%");
            if (value >= 100) {
                loaderTimer.stop();
                Timer t = new Timer(300, ev -> openLogin());
                t.setRepeats(false);
                t.start();
            }
        });
    }

    private void openLogin() {
        Rectangle bounds = getBounds(); // capture splash frame size & position
        dispose();
        SwingUtilities.invokeLater(() -> {
            LoginPage login = new LoginPage();
            login.setBounds(bounds); // same size & position
            login.setVisible(true);
        });
    }

    public void showWelcome() {
        setVisible(true);
        loaderTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage().showWelcome());
    }
}

// ---------------- LOGIN PAGE ----------------

class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Bag-ang Dental Clinic Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 30));

        // Logo
        ImageIcon logoIcon = new ImageIcon("assets/Dental_Logo.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setBounds(295, -10, 250, 200); // adjusted for larger frame
        add(logoLabel);

        // Title
        JLabel title = new JLabel("Bag-ang Dental Services", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(100, 200, 255));
        title.setBounds(200, 130, 450, 30);
        add(title);

        // Tagline
        JLabel tagline = new JLabel("<html><center>Expert care. Lasting confidence. A new smile begins here.</center></html>", SwingConstants.CENTER);
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tagline.setForeground(Color.LIGHT_GRAY);
        tagline.setBounds(200, 160, 450, 40);
        add(tagline);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(250, 220, 80, 25);
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(340, 220, 250, 25);
        add(emailField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(250, 260, 80, 25);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(340, 260, 250, 25);
        add(passwordField);

        // Remember Me
        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setBounds(245, 300, 150, 25);
        rememberMe.setBackground(new Color(30, 30, 30));
        rememberMe.setForeground(Color.WHITE);
        add(rememberMe);

        // Forgot Password
       JButton forgotPassword = new JButton("Forgot password?");
        forgotPassword.setBounds(465, 300, 150, 25); // same Y, shifted X
        forgotPassword.setBorderPainted(false);
        forgotPassword.setContentAreaFilled(false);
        forgotPassword.setForeground(new Color(100, 200, 255));
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(forgotPassword);

        // Login Button
        JButton loginButton = new JButton("LOGIN AS USER");
        loginButton.setBounds(340, 330, 160, 35);
        loginButton.setBackground(new Color(100, 200, 255));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(loginButton);

        // Sign Up Prompt
        JLabel signupPrompt = new JLabel("Don't have an Account? Sign up here.", SwingConstants.CENTER);
        signupPrompt.setBounds(250, 370, 350, 25);
        signupPrompt.setForeground(new Color(100, 200, 255));
        signupPrompt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(signupPrompt);

        // Login Action
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            JOptionPane.showMessageDialog(this, "Login attempt:\nEmail: " + email + "\nPassword: " + password);
            // TODO: Add database validation here
        });
    }
}

import javax.swing.*;   // Import Swing components (JFrame, JPanel, JLabel, JProgressBar, etc.)
import java.awt.*;      // Import AWT classes (Color, Graphics, Layouts)

/**
 * Welcome/Splash screen for Bag-ang Dental Service.
 * Shows a gradient background, title, subtitle, and an animated loading bar.
 * Title fades in for a smoother visual effect.
 * After loading completes, it will try to open LandingPage.
 */
public class WelcomePage extends JFrame {
    private final JProgressBar progress = new JProgressBar(0, 100); // Progress bar
    private Timer loaderTimer = null; // Timer for loading animation

    public WelcomePage() {
        setUndecorated(true); // Remove window decorations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app when closed
        setSize(850, 480); // Window size
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout()); // Main layout

        // Center panel with gradient background
        JPanel center = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth(), h = getHeight();
                Color c1 = new Color(18, 113, 186); // Top gradient color
                Color c2 = new Color(14, 64, 120);  // Bottom gradient color
                g2.setPaint(new GradientPaint(0, 0, c1, 0, h, c2)); // Vertical gradient
                g2.fillRect(0, 0, w, h); // Fill background
                g2.dispose();
            }
        };
        center.setLayout(new GridBagLayout()); // Center content
        center.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30)); // Padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.CENTER;

        // Title label (will fade in)
        JLabel title = new JLabel("Bag-ang Dental Service");
        title.setForeground(new Color(255, 255, 255, 0)); // Start fully transparent
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        center.add(title, gbc);

        // Subtitle label
        gbc.gridy++;
        JLabel subtitle = new JLabel("Quality Care • Friendly Service • Trusted Professionals");
        subtitle.setForeground(new Color(220, 235, 255));
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, 14f));
        center.add(subtitle, gbc);

        add(center, BorderLayout.CENTER);

        // Bottom panel with progress bar
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        progress.setStringPainted(true);
        progress.setForeground(new Color(14, 64, 120));
        progress.setPreferredSize(new Dimension(200, 20));
        bottom.add(progress, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // Loader timer: animates progress bar from 0 to 100
        loaderTimer = new Timer(25, e -> {
            int value = progress.getValue() + 1;
            progress.setValue(value);
            progress.setString("Loading " + value + "%");
            if (value >= 100) {
                loaderTimer.stop();
                // short pause then open landing
                Timer t = new Timer(300, ev -> openLanding());
                t.setRepeats(false);
                t.start();
            }
        });

        // Apply system look & feel (optional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignored) {}

        // Fade-in effect for title
        new Thread(() -> {
            try {
                for (int alpha = 0; alpha <= 255; alpha += 15) {
                    final Color fadeColor = new Color(255, 255, 255, alpha);
                    SwingUtilities.invokeLater(() -> title.setForeground(fadeColor));
                    Thread.sleep(50); // Delay between steps
                }
            } catch (InterruptedException ignored) {}
        }).start();
    }

    // Method to open LandingPage after splash completes
    private void openLanding() {
        dispose(); // Close splash
        SwingUtilities.invokeLater(() -> {
            try {
                new LandingPage().setVisible(true);
            } catch (Throwable ex) {
                JOptionPane.showMessageDialog(null,
                        "Welcome complete. Implement LandingPage to continue.",
                        "Welcome",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Show splash screen and start loader
    public void showWelcome() {
        setVisible(true);
        loaderTimer.start();
    }

    // Main method: entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomePage w = new WelcomePage();
            w.showWelcome();
        });
    }
}

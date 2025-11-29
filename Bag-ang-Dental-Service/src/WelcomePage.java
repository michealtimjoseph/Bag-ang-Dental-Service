import javax.swing.*;   // Import Swing components (JFrame, JPanel, JLabel, JProgressBar, etc.)
import java.awt.*;      // Import AWT classes (Color, Graphics, Layouts)

/**
 * Clean Welcome/Splash screen for Bag-ang Dental Service.
 * Shows a gradient background, title, subtitle, and an animated loading bar.
 * After loading completes, it will try to open LandingPage.
 */
public class WelcomePage extends JFrame {
    // Progress bar at the bottom of the splash screen
    private final JProgressBar progress = new JProgressBar(0, 100);

    // Timer that animates the progress bar
    private Timer loaderTimer = null;

    // Constructor: sets up the splash screen window
    public WelcomePage() {
        setUndecorated(true); // Remove window decorations (title bar, close button)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app when closed
        setSize(850, 480); // Set window size
        setLocationRelativeTo(null); // Center window on screen
        setLayout(new BorderLayout()); // Use BorderLayout for main layout

        // Center panel with gradient background, title, and subtitle
        JPanel center = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Call default painting
                Graphics2D g2 = (Graphics2D) g.create(); // Create graphics copy
                int w = getWidth(), h = getHeight(); // Get panel width and height
                Color c1 = new Color(18, 113, 186); // Top gradient color
                Color c2 = new Color(14, 64, 120);  // Bottom gradient color
                g2.setPaint(new GradientPaint(0, 0, c1, 0, h, c2)); // Vertical gradient
                g2.fillRect(0, 0, w, h); // Fill background with gradient
                g2.dispose(); // Dispose graphics copy
            }
        };
        center.setLayout(new GridBagLayout()); // Use GridBagLayout for centered content
        center.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30)); // Add padding

        // Constraints for placing components in the center panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6); // Spacing between components
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.CENTER; // Center alignment

        // Title label
        JLabel title = new JLabel("Bag-ang Dental Service"); // App title text
        title.setForeground(Color.WHITE); // White text color
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f)); // Bold, larger font
        center.add(title, gbc); // Add title to center panel

        // Subtitle label
        gbc.gridy++; // Move to next row
        JLabel subtitle = new JLabel("Quality Care • Friendly Service • Trusted Professionals"); // Subtitle text
        subtitle.setForeground(new Color(220, 235, 255)); // Light blue text color
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, 14f)); // Smaller plain font
        center.add(subtitle, gbc); // Add subtitle to center panel

        add(center, BorderLayout.CENTER); // Add center panel to frame

        // Bottom panel with progress bar
        JPanel bottom = new JPanel(new BorderLayout()); // Panel for progress bar
        bottom.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); // Padding
        progress.setStringPainted(true); // Show percentage text on progress bar
        progress.setForeground(new Color(14, 64, 120)); // Progress bar color
        progress.setPreferredSize(new Dimension(200, 20)); // Size of progress bar
        bottom.add(progress, BorderLayout.CENTER); // Add progress bar to bottom panel
        add(bottom, BorderLayout.SOUTH); // Add bottom panel to frame

        // Loader timer: animates progress bar from 0 to 100
        loaderTimer = new Timer(25, e -> {
            int value = progress.getValue() + 1; // Increase progress value
            progress.setValue(value); // Update progress bar
            progress.setString("Loading " + value + "%"); // Show text percentage
            if (value >= 100) { // When progress reaches 100%
                loaderTimer.stop(); // Stop loader timer
                // Short pause then open LandingPage
                Timer t = new Timer(300, ev -> openLanding()); // Delay 300ms
                t.setRepeats(false); // Run only once
                t.start(); // Start timer
            }
        });

        // Apply system look & feel (optional, makes UI match OS style)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignored) {}
    }

    // Method to open LandingPage after splash completes
    private void openLanding() {
        dispose(); // Close splash screen
        SwingUtilities.invokeLater(() -> { // Run on Event Dispatch Thread
            try {
                new LandingPage().setVisible(true); // Show LandingPage
            } catch (Throwable ex) {
                JOptionPane.showMessageDialog(null,
                        "Welcome complete. Implement LandingPage to continue.",
                        "Welcome",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Method to show splash screen and start loader
    public void showWelcome() {
        setVisible(true); // Show splash window
        loaderTimer.start(); // Start progress animation
    }

    // Main method: entry point of program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // Run on Event Dispatch Thread
            WelcomePage w = new WelcomePage(); // Create splash screen
            w.showWelcome(); // Show splash screen
        });
    }
}

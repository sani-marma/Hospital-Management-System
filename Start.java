// Start.java - Main class to run Hospital Management System
import Gui.HospitalGUI;
import javax.swing.*;

public class Start {
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set look and feel: " + e.getMessage());
        }

        // Run GUI
        SwingUtilities.invokeLater(() -> {
            try {
                HospitalGUI gui = new HospitalGUI();
                gui.setVisible(true);
                System.out.println("Hospital Management System started successfully!");
            } catch (Exception e) {
                System.err.println("Error starting application: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error starting Hospital Management System:\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
// Gui/HospitalGUI.java
package Gui;

import Entity.Doctor;
import Entity.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class HospitalGUI extends JFrame {
    // Data lists
    private List<Doctor> doctors;
    private List<Patient> patients;

    // GUI components
    private JTabbedPane tabbedPane;
    private JTable doctorTable, patientTable;
    private DefaultTableModel doctorModel, patientModel;
    private JTextArea logArea;
    private JTextField searchField;
    private JComboBox<String> searchCombo;

    // Doctor form fields
    private JTextField doctorIdField, doctorNameField, doctorAgeField, doctorGenderField;
    private JTextField doctorPhoneField, doctorSpecField, doctorDeptField;

    // Patient form fields
    private JTextField patientIdField, patientNameField, patientAgeField, patientGenderField;
    private JTextField patientPhoneField, patientBloodField, patientDiseaseField, patientDoctorField;

    public HospitalGUI() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();

        initGUI();
        addSampleData();
        updateTables();
        addLog("System started successfully");
    }

    private void initGUI() {
        setTitle("Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // Header with logo and search
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo and title
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(70, 130, 180));

        JLabel logoLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon("Images/hospital.jpeg");
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            logoLabel.setText("🏥");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 30));
            logoLabel.setForeground(Color.WHITE);
        }

        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        logoPanel.add(logoLabel);
        logoPanel.add(titleLabel);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(new Color(70, 130, 180));

        searchCombo = new JComboBox<>(new String[]{"Search Doctor", "Search Patient"});
        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");

        searchPanel.add(searchCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Main content
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tabbed pane
        tabbedPane = new JTabbedPane();

        // Doctor tab
        JPanel doctorPanel = createDoctorPanel();
        tabbedPane.addTab("Doctors", doctorPanel);

        // Patient tab
        JPanel patientPanel = createPatientPanel();
        tabbedPane.addTab("Patients", patientPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Activity log
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Activity Log"));
        logPanel.setPreferredSize(new Dimension(300, 0));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

        JScrollPane logScroll = new JScrollPane(logArea);
        logPanel.add(logScroll, BorderLayout.CENTER);

        JButton clearLogBtn = new JButton("Clear Log");
        clearLogBtn.addActionListener(e -> {
            logArea.setText("");
            addLog("Log cleared");
        });
        logPanel.add(clearLogBtn, BorderLayout.SOUTH);

        mainPanel.add(logPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Search button action
        searchBtn.addActionListener(e -> performSearch());

        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private JPanel createDoctorPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table
        String[] columns = {"ID", "Name", "Age", "Gender", "Phone", "Specialization", "Department"};
        doctorModel = new DefaultTableModel(columns, 0);
        doctorTable = new JTable(doctorModel);
        doctorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        doctorTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedDoctor();
            }
        });

        JScrollPane tableScroll = new JScrollPane(doctorTable);
        tableScroll.setPreferredSize(new Dimension(0, 250));

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Doctor Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Form fields
        doctorIdField = new JTextField(10);
        doctorNameField = new JTextField(15);
        doctorAgeField = new JTextField(10);
        doctorGenderField = new JTextField(10);
        doctorPhoneField = new JTextField(15);
        doctorSpecField = new JTextField(15);
        doctorDeptField = new JTextField(15);

        // Add form components
        addFormField(formPanel, "ID:", doctorIdField, gbc, 0, 0);
        addFormField(formPanel, "Name:", doctorNameField, gbc, 2, 0);
        addFormField(formPanel, "Age:", doctorAgeField, gbc, 0, 1);
        addFormField(formPanel, "Gender:", doctorGenderField, gbc, 2, 1);
        addFormField(formPanel, "Phone:", doctorPhoneField, gbc, 0, 2);
        addFormField(formPanel, "Specialization:", doctorSpecField, gbc, 2, 2);
        addFormField(formPanel, "Department:", doctorDeptField, gbc, 0, 3);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");

        addBtn.addActionListener(e -> addDoctor());
        updateBtn.addActionListener(e -> updateDoctor());
        deleteBtn.addActionListener(e -> deleteDoctor());
        clearBtn.addActionListener(e -> clearDoctorForm());

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        formPanel.add(btnPanel, gbc);

        panel.add(tableScroll, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table
        String[] columns = {"ID", "Name", "Age", "Gender", "Phone", "Blood Group", "Disease", "Doctor"};
        patientModel = new DefaultTableModel(columns, 0);
        patientTable = new JTable(patientModel);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        patientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedPatient();
            }
        });

        JScrollPane tableScroll = new JScrollPane(patientTable);
        tableScroll.setPreferredSize(new Dimension(0, 250));

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Patient Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Form fields
        patientIdField = new JTextField(10);
        patientNameField = new JTextField(15);
        patientAgeField = new JTextField(10);
        patientGenderField = new JTextField(10);
        patientPhoneField = new JTextField(15);
        patientBloodField = new JTextField(10);
        patientDiseaseField = new JTextField(15);
        patientDoctorField = new JTextField(15);

        // Add form components
        addFormField(formPanel, "ID:", patientIdField, gbc, 0, 0);
        addFormField(formPanel, "Name:", patientNameField, gbc, 2, 0);
        addFormField(formPanel, "Age:", patientAgeField, gbc, 0, 1);
        addFormField(formPanel, "Gender:", patientGenderField, gbc, 2, 1);
        addFormField(formPanel, "Phone:", patientPhoneField, gbc, 0, 2);
        addFormField(formPanel, "Blood Group:", patientBloodField, gbc, 2, 2);
        addFormField(formPanel, "Disease:", patientDiseaseField, gbc, 0, 3);
        addFormField(formPanel, "Doctor:", patientDoctorField, gbc, 2, 3);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");

        addBtn.addActionListener(e -> addPatient());
        updateBtn.addActionListener(e -> updatePatient());
        deleteBtn.addActionListener(e -> deletePatient());
        clearBtn.addActionListener(e -> clearPatientForm());

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        formPanel.add(btnPanel, gbc);

        panel.add(tableScroll, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addFormField(JPanel panel, String label, JTextField field, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = x + 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    private void addSampleData() {
        doctors.add(new Doctor(1, "Dr. Smith", 45, "Male", "123-456-7890", "Cardiology", "Heart Dept"));
        doctors.add(new Doctor(2, "Dr. Johnson", 38, "Female", "234-567-8901", "Pediatrics", "Child Dept"));

        patients.add(new Patient(101, "Alice Brown", 30, "Female", "555-0101", "O+", "Fever", "Dr. Smith"));
        patients.add(new Patient(102, "Bob Davis", 25, "Male", "555-0102", "A-", "Cold", "Dr. Johnson"));
    }

    private void performSearch() {
        String query = searchField.getText().toLowerCase().trim();
        if (query.isEmpty()) return;

        String type = (String) searchCombo.getSelectedItem();

        if (type.equals("Search Doctor")) {
            tabbedPane.setSelectedIndex(0);
            for (int i = 0; i < doctorTable.getRowCount(); i++) {
                String name = doctorTable.getValueAt(i, 1).toString().toLowerCase();
                if (name.contains(query)) {
                    doctorTable.setRowSelectionInterval(i, i);
                    addLog("Found doctor: " + doctorTable.getValueAt(i, 1));
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Doctor not found!");
        } else {
            tabbedPane.setSelectedIndex(1);
            for (int i = 0; i < patientTable.getRowCount(); i++) {
                String name = patientTable.getValueAt(i, 1).toString().toLowerCase();
                if (name.contains(query)) {
                    patientTable.setRowSelectionInterval(i, i);
                    addLog("Found patient: " + patientTable.getValueAt(i, 1));
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Patient not found!");
        }
    }

    private void addDoctor() {
        try {
            Doctor doctor = new Doctor(
                    Integer.parseInt(doctorIdField.getText()),
                    doctorNameField.getText(),
                    Integer.parseInt(doctorAgeField.getText()),
                    doctorGenderField.getText(),
                    doctorPhoneField.getText(),
                    doctorSpecField.getText(),
                    doctorDeptField.getText()
            );
            doctors.add(doctor);
            updateTables();
            clearDoctorForm();
            addLog("Added doctor: " + doctor.getName());
            JOptionPane.showMessageDialog(this, "Doctor added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateDoctor() {
        int row = doctorTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to update!");
            return;
        }

        try {
            Doctor doctor = new Doctor(
                    Integer.parseInt(doctorIdField.getText()),
                    doctorNameField.getText(),
                    Integer.parseInt(doctorAgeField.getText()),
                    doctorGenderField.getText(),
                    doctorPhoneField.getText(),
                    doctorSpecField.getText(),
                    doctorDeptField.getText()
            );
            doctors.set(row, doctor);
            updateTables();
            clearDoctorForm();
            addLog("Updated doctor: " + doctor.getName());
            JOptionPane.showMessageDialog(this, "Doctor updated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteDoctor() {
        int row = doctorTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this doctor?");
        if (confirm == JOptionPane.YES_OPTION) {
            Doctor doctor = doctors.get(row);
            doctors.remove(row);
            updateTables();
            clearDoctorForm();
            addLog("Deleted doctor: " + doctor.getName());
            JOptionPane.showMessageDialog(this, "Doctor deleted successfully!");
        }
    }

    private void addPatient() {
        try {
            Patient patient = new Patient(
                    Integer.parseInt(patientIdField.getText()),
                    patientNameField.getText(),
                    Integer.parseInt(patientAgeField.getText()),
                    patientGenderField.getText(),
                    patientPhoneField.getText(),
                    patientBloodField.getText(),
                    patientDiseaseField.getText(),
                    patientDoctorField.getText()
            );
            patients.add(patient);
            updateTables();
            clearPatientForm();
            addLog("Added patient: " + patient.getName());
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updatePatient() {
        int row = patientTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to update!");
            return;
        }

        try {
            Patient patient = new Patient(
                    Integer.parseInt(patientIdField.getText()),
                    patientNameField.getText(),
                    Integer.parseInt(patientAgeField.getText()),
                    patientGenderField.getText(),
                    patientPhoneField.getText(),
                    patientBloodField.getText(),
                    patientDiseaseField.getText(),
                    patientDoctorField.getText()
            );
            patients.set(row, patient);
            updateTables();
            clearPatientForm();
            addLog("Updated patient: " + patient.getName());
            JOptionPane.showMessageDialog(this, "Patient updated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deletePatient() {
        int row = patientTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this patient?");
        if (confirm == JOptionPane.YES_OPTION) {
            Patient patient = patients.get(row);
            patients.remove(row);
            updateTables();
            clearPatientForm();
            addLog("Deleted patient: " + patient.getName());
            JOptionPane.showMessageDialog(this, "Patient deleted successfully!");
        }
    }

    private void loadSelectedDoctor() {
        int row = doctorTable.getSelectedRow();
        if (row != -1) {
            Doctor doctor = doctors.get(row);
            doctorIdField.setText(String.valueOf(doctor.getId()));
            doctorNameField.setText(doctor.getName());
            doctorAgeField.setText(String.valueOf(doctor.getAge()));
            doctorGenderField.setText(doctor.getGender());
            doctorPhoneField.setText(doctor.getPhone());
            doctorSpecField.setText(doctor.getSpecialization());
            doctorDeptField.setText(doctor.getDepartment());
        }
    }

    private void loadSelectedPatient() {
        int row = patientTable.getSelectedRow();
        if (row != -1) {
            Patient patient = patients.get(row);
            patientIdField.setText(String.valueOf(patient.getId()));
            patientNameField.setText(patient.getName());
            patientAgeField.setText(String.valueOf(patient.getAge()));
            patientGenderField.setText(patient.getGender());
            patientPhoneField.setText(patient.getPhone());
            patientBloodField.setText(patient.getBloodGroup());
            patientDiseaseField.setText(patient.getDisease());
            patientDoctorField.setText(patient.getDoctorName());
        }
    }

    private void clearDoctorForm() {
        doctorIdField.setText("");
        doctorNameField.setText("");
        doctorAgeField.setText("");
        doctorGenderField.setText("");
        doctorPhoneField.setText("");
        doctorSpecField.setText("");
        doctorDeptField.setText("");
    }

    private void clearPatientForm() {
        patientIdField.setText("");
        patientNameField.setText("");
        patientAgeField.setText("");
        patientGenderField.setText("");
        patientPhoneField.setText("");
        patientBloodField.setText("");
        patientDiseaseField.setText("");
        patientDoctorField.setText("");
    }

    private void updateTables() {
        // Update doctor table
        doctorModel.setRowCount(0);
        for (Doctor doctor : doctors) {
            Object[] row = {
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getAge(),
                    doctor.getGender(),
                    doctor.getPhone(),
                    doctor.getSpecialization(),
                    doctor.getDepartment()
            };
            doctorModel.addRow(row);
        }

        // Update patient table
        patientModel.setRowCount(0);
        for (Patient patient : patients) {
            Object[] row = {
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getPhone(),
                    patient.getBloodGroup(),
                    patient.getDisease(),
                    patient.getDoctorName()
            };
            patientModel.addRow(row);
        }
    }

    private void addLog(String message) {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String timeStamp = now.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        logArea.append("[" + timeStamp + "] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}
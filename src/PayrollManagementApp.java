import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Login {
    // Verification
    public boolean signIn(String username, String password, boolean isAdmin) {
        if (isAdmin) {
            return username.equals("admin") && password.equals("admin123");
        } else {
            return username.equals("employee") && password.equals("employee123");
        }
    }
}

class LoginPane extends JPanel {
    //Login Pane
    private JTextField userlastNameField;
    private JPasswordField passwordField;
    private JCheckBox adminCheckBox;
    private JButton loginButton;

    private Login login;

    public LoginPane() {
        login = new Login();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 150)); // Set preferred size for the panel

        JPanel inputPanel = new JPanel(new GridLayout(4, 1));
        inputPanel.add(new JLabel("Username:"));
        userlastNameField = new JTextField(15);
        inputPanel.add(userlastNameField);
        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        inputPanel.add(passwordField);

        adminCheckBox = new JCheckBox("Admin Login");
        inputPanel.add(adminCheckBox);

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size for the button
        loginButton.addActionListener(e -> {
            String username = userlastNameField.getText();
            String password = new String(passwordField.getPassword());
            boolean isAdmin = adminCheckBox.isSelected();
            if (login.signIn(username, password, isAdmin)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Open the main application window if login is successful
                JFrame mainFrame = new PayrollManagementApp(isAdmin);
                mainFrame.setVisible(true);
                SwingUtilities.getWindowAncestor(this).dispose(); // Close the login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

public class PayrollManagementApp extends JFrame implements ActionListener {
    //Payroll App
    private boolean isAdmin;
    private Path filePath; // Path variable to store the path to the CSV file

    private JButton manageLeaveRequestsButton, requestLeaveButton, generatePayrollReportButton,
            runPayrollButton, modifyTaxRatesButton, generatePaySlipButton, clockInButton, clockOutButton, updateEmployeeDetailsButton;
    private JLabel Welcome;


    public PayrollManagementApp(boolean isAdmin) {
        this.isAdmin = isAdmin;

        setTitle("Payroll Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300)); // Set preferred size for the frame

        String filePathString = "C:\\Users\\galil\\MYFILES\\SCHOOL\\2ND YEAR\\Term 2\\OOP\\inheritance\\employee details.csv";
        filePath = (Path) Paths.get(filePathString);


        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));
        panel.setPreferredSize(new Dimension(450, 250)); // Set preferred size for the panel


        if (isAdmin) {
            // Only admin functionalities

            Welcome = new JLabel("Welcome Admin!");
            panel.add(Welcome);

            Welcome = new JLabel(" ");
            panel.add(Welcome);

            manageLeaveRequestsButton = new JButton("Manage Leave Requests");
            manageLeaveRequestsButton.addActionListener(this);
            manageLeaveRequestsButton.setPreferredSize(new Dimension(180, 30)); // Set preferred size for the button
            panel.add(manageLeaveRequestsButton);


            generatePayrollReportButton = new JButton("Generate Payroll Report");
            generatePayrollReportButton.addActionListener(this);
            generatePayrollReportButton.setPreferredSize(new Dimension(180, 30)); // Set preferred size for the button
            panel.add(generatePayrollReportButton);

            runPayrollButton = new JButton("Run Payroll");
            runPayrollButton.addActionListener(this);
            runPayrollButton.setPreferredSize(new Dimension(180, 30)); // Set preferred size for the button
            panel.add(runPayrollButton);

            modifyTaxRatesButton = new JButton("Modify Tax Rates");
            modifyTaxRatesButton.addActionListener(this);
            modifyTaxRatesButton.setPreferredSize(new Dimension(180, 30)); // Set preferred size for the button
            panel.add(modifyTaxRatesButton);

            updateEmployeeDetailsButton = new JButton("Update Employee Details");
            updateEmployeeDetailsButton.addActionListener(this);
            updateEmployeeDetailsButton.setPreferredSize(new Dimension(180, 30)); // Set preferred size for the button
            panel.add(updateEmployeeDetailsButton);
        } else {
            // Only employee functionalities

            Welcome = new JLabel("Welcome!");
            panel.add(Welcome);

            Welcome = new JLabel(" ");
            panel.add(Welcome);

            generatePaySlipButton = new JButton("Generate Payslip");
            generatePaySlipButton.addActionListener(this);
            generatePaySlipButton.setPreferredSize(new Dimension(180, 30)); // Set preferred size for the button
            panel.add(generatePaySlipButton);

            requestLeaveButton = new JButton("Request Leave");
            requestLeaveButton.addActionListener(this);
            requestLeaveButton.setPreferredSize(new Dimension(180, 30)); // Set preferred size for the button
            panel.add(requestLeaveButton);

            clockInButton = new JButton("Clock In");
            clockInButton.addActionListener(this);
            clockInButton.setPreferredSize(new Dimension(180, 30)); // Set preferred size for the button
            panel.add(clockInButton);

            clockOutButton = new JButton("Clock Out");
            clockOutButton.addActionListener(this);
            clockOutButton.setPreferredSize(new Dimension(180, 30)); // Set preferred size for the button
            panel.add(clockOutButton);
        }

        add(panel);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.getContentPane().add(new LoginPane());
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
            loginFrame.pack();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Manage Leave Requests
        if (e.getSource() == manageLeaveRequestsButton) {
            JFrame dialogFrame = new JFrame("Manage Leave Requests");
            dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dialogFrame.setSize(300, 200);

            JPanel dialogPanel = new JPanel();
            dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

            JButton viewLeaveRequestsButton = new JButton("View Leave Requests");
            JButton approveLeaveButton = new JButton("Approve Leave");
            JButton denyLeaveButton = new JButton("Deny Leave");

            viewLeaveRequestsButton.addActionListener(e1 -> {
                // Logic to view leave requests
                JOptionPane.showMessageDialog(dialogFrame, "Viewing leave requests...");
            });

            approveLeaveButton.addActionListener(e1 -> {
                // Logic to approve leave
                JOptionPane.showMessageDialog(dialogFrame, "Leave approved!");
            });

            denyLeaveButton.addActionListener(e1 -> {
                // Logic to deny leave
                JOptionPane.showMessageDialog(dialogFrame, "Leave denied!");
            });

            dialogPanel.add(viewLeaveRequestsButton);
            dialogPanel.add(approveLeaveButton);
            dialogPanel.add(denyLeaveButton);

            dialogFrame.add(dialogPanel);
            dialogFrame.setLocationRelativeTo(this);
            dialogFrame.setVisible(true);

        } else if (e.getSource() == generatePayrollReportButton) {
            // Generate Payroll Report
            // Prompt user to specify pay period via date selection
            JPanel datePanel = new JPanel();
            datePanel.setLayout(new GridLayout(3, 2));

            JTextField startDateField = new JTextField(10);
            JTextField endDateField = new JTextField(10);

            datePanel.add(new JLabel("Start Date (MM/DD/YYYY):"));
            datePanel.add(startDateField);
            datePanel.add(new JLabel("End Date (MM/DD/YYYY):"));
            datePanel.add(endDateField);

            int result = JOptionPane.showConfirmDialog(this, datePanel, "Select Pay Period", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Retrieve start and end dates entered by user
                String startDateStr = startDateField.getText();
                String endDateStr = endDateField.getText();

                // Generate payroll report based on selected pay period
                String payrollReport = generatePayrollReport(startDateStr, endDateStr);

                // Provide option to download the report
                int downloadOption = JOptionPane.showConfirmDialog(this, "Payroll report generated. Do you want to download it?", "Download Report", JOptionPane.YES_NO_OPTION);
                if (downloadOption == JOptionPane.YES_OPTION) {
                    // Logic to download the report
                    // For demonstration purposes, simply display the report content in a message dialog
                    JOptionPane.showMessageDialog(this, "Payroll Report:\n" + payrollReport);
                }
            }
        } else if (e.getSource() == runPayrollButton) {
            // Prompt user to specify pay period via date selection
            JPanel datePanel = new JPanel();
            datePanel.setLayout(new GridLayout(3, 2));

            JTextField startDateField = new JTextField(10);
            JTextField endDateField = new JTextField(10);

            datePanel.add(new JLabel("Start Date (MM/DD/YYYY):"));
            datePanel.add(startDateField);
            datePanel.add(new JLabel("End Date (MM/DD/YYYY):"));
            datePanel.add(endDateField);

            int result = JOptionPane.showConfirmDialog(this, datePanel, "Select Pay Period", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Retrieve start and end dates entered by user
                String startDateStr = startDateField.getText();
                String endDateStr = endDateField.getText();

                // Allow selection of employees to run payroll for
                String[] employees = {"Employee 1", "Employee 2", "Employee 3"};
                JCheckBox[] checkboxes = new JCheckBox[employees.length];
                for (int i = 0; i < employees.length; i++) {
                    checkboxes[i] = new JCheckBox(employees[i]);
                }

                JPanel employeePanel = new JPanel(new GridLayout(0, 1));
                for (JCheckBox checkbox : checkboxes) {
                    employeePanel.add(checkbox);
                }

                int runOption = JOptionPane.showConfirmDialog(this, employeePanel, "Select employees to run payroll for:", JOptionPane.OK_CANCEL_OPTION);
                if (runOption == JOptionPane.OK_OPTION) {
                    // Logic to run payroll for selected employees based on specified pay period
                    // For demonstration purposes, simply display a message with selected employees and pay period
                    StringBuilder message = new StringBuilder();
                    message.append("Running payroll for selected employees:\n");
                    for (JCheckBox checkbox : checkboxes) {
                        if (checkbox.isSelected()) {
                            message.append(checkbox.getText()).append("\n");
                        }
                    }
                    message.append("Pay Period: ").append(startDateStr).append(" to ").append(endDateStr);
                    JOptionPane.showMessageDialog(this, message.toString());

                    // Print something after running payroll
                    System.out.println("Payroll successfully processed.");
                }
            }
        } else if (e.getSource() == updateEmployeeDetailsButton) {
            // Placeholder for the file path of the CSV file
            String filePath = "C:\\Users\\galil\\MYFILES\\SCHOOL\\2ND YEAR\\Term 2\\OOP\\inheritance\\Copy of MotorPH Employee Data - Employee Details.csv";

            // Create a dialog for updating employee details
            JFrame updateEmployeeDialog = new JFrame("Update Employee Details");
            updateEmployeeDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            updateEmployeeDialog.setSize(300, 200);

            JPanel updatePanel = new JPanel (new GridLayout(3, 2));;

            JButton viewButton = new JButton("View Employee");
            JButton editButton = new JButton("Edit Employee");
            JButton deleteButton = new JButton("Delete Employee");
            JButton addButton = new JButton("Add Employee");

            Dimension buttonSize = new Dimension(200, 30);
            viewButton.setPreferredSize(buttonSize);
            editButton.setPreferredSize(buttonSize);
            deleteButton.setPreferredSize(buttonSize);
            addButton.setPreferredSize(buttonSize);

            viewButton.addActionListener(e1 -> {
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            editButton.addActionListener(e1 -> {
                JFrame editEmployeeDialog = new JFrame("Edit Employee");
                editEmployeeDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editEmployeeDialog.setSize(400, 500);

                JPanel editPanel = new JPanel(new GridLayout(20, 2));

                JLabel idLabel = new JLabel("Employee ID:");
                JTextField idField = new JTextField();
                editPanel.add(idLabel);
                editPanel.add(idField);

                JLabel lNlabel = new JLabel("Last Name:");
                JTextField lastNameField = new JTextField();
                editPanel.add(lNlabel);
                editPanel.add(lastNameField);

                JLabel fNlabel = new JLabel("First Name:");
                JTextField firstNameField = new JTextField();
                editPanel.add(fNlabel);
                editPanel.add(firstNameField);

                JLabel birthdayLabel = new JLabel("Birthday:");
                JTextField birthdayField = new JTextField();
                editPanel.add(birthdayLabel);
                editPanel.add(birthdayField);

                JLabel addressLabel = new JLabel("Address:");
                JTextField addressField = new JTextField();
                editPanel.add(addressLabel);
                editPanel.add(addressField);

                JLabel pNumberLabel = new JLabel("Phone Number:");
                JTextField pNumberField = new JTextField();
                editPanel.add(pNumberLabel);
                editPanel.add(pNumberField);

                JLabel sssLabel = new JLabel("SSS #:");
                JTextField sssField = new JTextField();
                editPanel.add(sssLabel);
                editPanel.add(sssField);

                JLabel philHealthLabel = new JLabel("PhilHealth #:");
                JTextField philHealthField = new JTextField();
                editPanel.add(philHealthLabel);
                editPanel.add(philHealthField);

                JLabel TINLabel = new JLabel("TIN #:");
                JTextField TINField = new JTextField();
                editPanel.add(TINLabel);
                editPanel.add(TINField);

                JLabel pagIbigLabel = new JLabel("Pag Ibig #:");
                JTextField pagIbigField = new JTextField();
                editPanel.add(pagIbigLabel);
                editPanel.add(pagIbigField);

                JLabel statusLabel = new JLabel("Status:");
                JTextField statusField = new JTextField();
                editPanel.add(statusLabel);
                editPanel.add(statusField);

                JLabel positionLabel = new JLabel("Position:");
                JTextField positionField = new JTextField();
                editPanel.add(positionLabel);
                editPanel.add(positionField);

                JLabel supervisorLabel = new JLabel("Immediate Supervisor:");
                JTextField supervisorField = new JTextField();
                editPanel.add(supervisorLabel);
                editPanel.add(supervisorField);

                JLabel basicSalaryLabel = new JLabel("Basic Salary:");
                JTextField basicSalaryField = new JTextField();
                editPanel.add(basicSalaryLabel);
                editPanel.add(basicSalaryField);

                JLabel riceSubsidyLabel = new JLabel("Rice Subsidy:");
                JTextField riceSubsidyField = new JTextField();
                editPanel.add(riceSubsidyLabel);
                editPanel.add(riceSubsidyField);

                JLabel phoneAllowanceLabel = new JLabel("Phone Allowance:");
                JTextField phoneAllowanceField = new JTextField();
                editPanel.add(phoneAllowanceLabel);
                editPanel.add(phoneAllowanceField);

                JLabel clothingAllowanceLabel = new JLabel("Clothing Allowance:");
                JTextField clothingAllowanceField = new JTextField();
                editPanel.add(clothingAllowanceLabel);
                editPanel.add(clothingAllowanceField);

                JLabel grossSemiMonthlyLabel = new JLabel("Gross Semi-Monthly Rate:");
                JTextField grossSemiMonthlyField = new JTextField();
                editPanel.add(grossSemiMonthlyLabel);
                editPanel.add(grossSemiMonthlyField);

                JLabel hourlyRateLabel = new JLabel("Hourly Rate:");
                JTextField hourlyRateField = new JTextField();
                editPanel.add(hourlyRateLabel);
                editPanel.add(hourlyRateField);

                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(saveEvent -> {
                    // Retrieve entered employee details
                    String id = idField.getText();
                    String lName = lastNameField.getText();
                    String fName = firstNameField.getText();
                    String bday = birthdayField.getText();
                    String address = addressField.getText();
                    String phoneNumber = pNumberField.getText();
                    String sssN = sssField.getText();
                    String philHealthN = philHealthField.getText();
                    String tinN = TINField.getText();
                    String pagIbigN = pagIbigField.getText();
                    String status = statusField.getText();
                    String position = positionField.getText();
                    String supervisor = supervisorField.getText();
                    String basicSalary = basicSalaryField.getText();
                    String riceSubsidy = riceSubsidyField.getText();
                    String phoneAllowance = phoneAllowanceField.getText();
                    String clothingAllowance = clothingAllowanceField.getText();
                    String grossSemiMonthly = grossSemiMonthlyField.getText();
                    String hourlyRate = hourlyRateField.getText();


                    // Convert the filePath string to a Path object

                    Path path = Paths.get(filePath);

                    // Read all lines from the CSV file, update the appropriate line with the edited details
                    // and write all the lines back to the CSV file

                    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                        List<String> updatedLines = new ArrayList<>();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split(","); // Assuming CSV format with comma-separated values
                            if (parts.length >= 19 && parts[0].equals(id)) {
                                // Update the line with the edited details
                                line = id + "," + lName + "," + fName + "," + bday + "," + address + "," + phoneNumber + "," + sssN + "," + philHealthN + ","
                                        + tinN + "," + pagIbigN + "," + status + "," + position + "," + supervisor + "," + basicSalary + "," + riceSubsidy
                                        + "," + phoneAllowance + "," + clothingAllowance + "," + grossSemiMonthly + "," + hourlyRate;
                            }
                            updatedLines.add(line);
                        }

                        // Write the updated lines back to the CSV file
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                            for (String updatedLine : updatedLines) {
                                writer.write(updatedLine);
                                writer.newLine();
                            }
                            JOptionPane.showMessageDialog(editEmployeeDialog, "Employee details updated successfully.");
                            editEmployeeDialog.dispose(); // Close the dialog after saving
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                editPanel.add(saveButton);

                editEmployeeDialog.add(editPanel);
                editEmployeeDialog.setLocationRelativeTo(updateEmployeeDialog);
                editEmployeeDialog.setVisible(true);
            });

            deleteButton.addActionListener(e1 -> {
                // Prompt the user to enter the ID of the employee to delete
                String employeeIDToDelete = JOptionPane.showInputDialog("Enter the ID of the employee to delete:");

                if (employeeIDToDelete != null && !employeeIDToDelete.isEmpty()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        List<String> lines = new ArrayList<>();
                        String line;
                        boolean employeeFound = false;
                        while ((line = reader.readLine()) != null) {
                            // Process each line of the CSV file
                            // Split the line into fields
                            String[] parts = line.split(",");
                            if (parts.length > 0 && parts[0].equals(employeeIDToDelete)) {
                                // Skip the line if it matches the employee ID to delete
                                employeeFound = true;
                            } else {
                                // Add the line to the list if it doesn't match the employee ID
                                lines.add(line);
                            }
                        }

                        if (employeeFound) {
                            // Write the updated lines to the file, effectively deleting the specified employee
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                                for (String updatedLine : lines) {
                                    writer.write(updatedLine);
                                    writer.newLine();
                                }
                                JOptionPane.showMessageDialog(updateEmployeeDialog, "Employee deleted successfully.");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(updateEmployeeDialog, "Employee with ID " + employeeIDToDelete + " not found.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(updateEmployeeDialog, "Please enter a valid employee ID.");
                }
            });

            addButton.addActionListener(e1 -> {
                // Logic to add a new employee
                JFrame addEmployeeDialog = new JFrame("Add Employee");
                addEmployeeDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addEmployeeDialog.setSize(400, 500);

                JPanel addPanel = new JPanel(new GridLayout(20, 2));


                JLabel idLabel = new JLabel("Employee ID:");
                JTextField idField = new JTextField();
                addPanel.add(idLabel);
                addPanel.add(idField);

                JLabel lNlabel = new JLabel("Last Name:");
                JTextField lastNameField = new JTextField();
                addPanel.add(lNlabel);
                addPanel.add(lastNameField);

                JLabel fNlabel = new JLabel("First Name:");
                JTextField firstNameField = new JTextField();
                addPanel.add(fNlabel);
                addPanel.add(firstNameField);

                JLabel birthdayLabel = new JLabel("Birthday:");
                JTextField birthdayField = new JTextField();
                addPanel.add(birthdayLabel);
                addPanel.add(birthdayField);

                JLabel addressLabel = new JLabel("Address:");
                JTextField addressField = new JTextField();
                addPanel.add(addressLabel);
                addPanel.add(addressField);

                JLabel pNumberLabel = new JLabel("Phone Number:");
                JTextField pNumberField = new JTextField();
                addPanel.add(pNumberLabel);
                addPanel.add(pNumberField);

                JLabel sssLabel = new JLabel("SSS #:");
                JTextField sssField = new JTextField();
                addPanel.add(sssLabel);
                addPanel.add(sssField);

                JLabel philHealthLabel = new JLabel("PhilHealth #:");
                JTextField philHealthField = new JTextField();
                addPanel.add(philHealthLabel);
                addPanel.add(philHealthField);

                JLabel TINLabel = new JLabel("TIN #:");
                JTextField TINField = new JTextField();
                addPanel.add(TINLabel);
                addPanel.add(TINField);

                JLabel pagIbigLabel = new JLabel("Pag Ibig #:");
                JTextField pagIbigField = new JTextField();
                addPanel.add(pagIbigLabel);
                addPanel.add(pagIbigField);

                JLabel statusLabel = new JLabel("Status:");
                JTextField statusField = new JTextField();
                addPanel.add(statusLabel);
                addPanel.add(statusField);

                JLabel positionLabel = new JLabel("Position:");
                JTextField positionField = new JTextField();
                addPanel.add(positionLabel);
                addPanel.add(positionField);

                JLabel supervisorLabel = new JLabel("Immediate Supervisor:");
                JTextField supervisorField = new JTextField();
                addPanel.add(supervisorLabel);
                addPanel.add(supervisorField);

                JLabel basicSalaryLabel = new JLabel("Basic Salary:");
                JTextField basicSalaryField = new JTextField();
                addPanel.add(basicSalaryLabel);
                addPanel.add(basicSalaryField);

                JLabel riceSubsidyLabel = new JLabel("Rice Subsidy:");
                JTextField riceSubsidyField = new JTextField();
                addPanel.add(riceSubsidyLabel);
                addPanel.add(riceSubsidyField);

                JLabel phoneAllowanceLabel = new JLabel("Phone Allowance:");
                JTextField phoneAllowanceField = new JTextField();
                addPanel.add(phoneAllowanceLabel);
                addPanel.add(phoneAllowanceField);

                JLabel clothingAllowanceLabel = new JLabel("Clothing Allowance:");
                JTextField clothingAllowanceField = new JTextField();
                addPanel.add(clothingAllowanceLabel);
                addPanel.add(clothingAllowanceField);

                JLabel grossSemiMonthlyLabel = new JLabel("Gross Semi-Monthly Rate:");
                JTextField grossSemiMonthlyField = new JTextField();
                addPanel.add(grossSemiMonthlyLabel);
                addPanel.add(grossSemiMonthlyField);

                JLabel hourlyRateLabel = new JLabel("Hourly Rate:");
                JTextField hourlyRateField = new JTextField();
                addPanel.add(hourlyRateLabel);
                addPanel.add(hourlyRateField);

                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(saveEvent -> {
                    // Retrieve entered employee details
                    String id = idField.getText();
                    String lName = lastNameField.getText();
                    String fName = firstNameField.getText();
                    String bday = birthdayField.getText();
                    String address = addressField.getText();
                    String phoneNumber = pNumberField.getText();
                    String sssN = sssField.getText();
                    String philHealthN = philHealthField.getText();
                    String tinN = TINField.getText();
                    String pagIbigN = pagIbigField.getText();
                    String status = statusField.getText();
                    String position = positionField.getText();
                    String supervisor = supervisorField.getText();
                    String basicSalary = basicSalaryField.getText();
                    String riceSubsidy = riceSubsidyField.getText();
                    String phoneAllowance = phoneAllowanceField.getText();
                    String clothingAllowance = clothingAllowanceField.getText();
                    String grossSemiMonthly = grossSemiMonthlyField.getText();
                    String hourlyRate = hourlyRateField.getText();
                    // Retrieve other employee details from their respective text fields

                    // Concatenate the employee details into a CSV format string
                    String newEmployeeDetails = id + "," + lName + "," + fName + "," + bday + "," + address + "," + phoneNumber + "," + sssN + "," + philHealthN + ","
                            + tinN + "," + pagIbigN + "," + status + "," + position + "," + supervisor + "," + basicSalary + "," + riceSubsidy
                            + "," + phoneAllowance + "," + clothingAllowance + "," + grossSemiMonthly + "," + hourlyRate;;

                    // Convert the filePath string to a Path object
                    Path path = Paths.get(filePath);

                    // Write the new employee details to the CSV file by overwriting the last line
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
                        writer.write(newEmployeeDetails);
                        writer.newLine();
                        JOptionPane.showMessageDialog(addEmployeeDialog, "Employee added successfully.");
                        addEmployeeDialog.dispose(); // Close the dialog after saving
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                addPanel.add(saveButton);

                addEmployeeDialog.add(addPanel);
                addEmployeeDialog.setLocationRelativeTo(updateEmployeeDialog);
                addEmployeeDialog.setVisible(true);
            });

            // Add buttons to the panel
            updatePanel.add(viewButton);
            updatePanel.add(editButton);
            updatePanel.add(deleteButton);
            updatePanel.add(addButton);



            updateEmployeeDialog.add(updatePanel);
            updateEmployeeDialog.setLocationRelativeTo(this);
            updateEmployeeDialog.setVisible(true);



        } else if (e.getSource() == modifyTaxRatesButton) {
            // Create a dialog for modifying tax rates
            JFrame modifyTaxRatesDialog = new JFrame("Modify Tax Rates");
            modifyTaxRatesDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            modifyTaxRatesDialog.setSize(300, 200);

            JPanel modifyPanel = new JPanel();
            modifyPanel.setLayout(new GridLayout(5, 2));

            // Add labels and text fields for tax rates
            JLabel philHealthLabel = new JLabel("PhilHealth Rate:");
            JTextField philHealthField = new JTextField();
            modifyPanel.add(philHealthLabel);
            modifyPanel.add(philHealthField);

            JLabel sssLabel = new JLabel("SSS Rate:");
            JTextField sssField = new JTextField();
            modifyPanel.add(sssLabel);
            modifyPanel.add(sssField);

            JLabel pagIbigLabel = new JLabel("PagIbig Rate:");
            JTextField pagIbigField = new JTextField();
            modifyPanel.add(pagIbigLabel);
            modifyPanel.add(pagIbigField);

            JLabel withholdingLabel = new JLabel("Withholding Rate:");
            JTextField withholdingField = new JTextField();
            modifyPanel.add(withholdingLabel);
            modifyPanel.add(withholdingField);

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e1 -> {
                // Retrieve entered tax rates
                String philHealthRate = philHealthField.getText();
                String sssRate = sssField.getText();
                String pagIbigRate = pagIbigField.getText();
                String withholdingRate = withholdingField.getText();

                // Perform save operation (You can add your logic here)

                JOptionPane.showMessageDialog(modifyTaxRatesDialog, "Tax rates saved successfully.");
                modifyTaxRatesDialog.dispose(); // Close the dialog after saving
            });

            modifyPanel.add(saveButton);

            modifyTaxRatesDialog.add(modifyPanel);
            modifyTaxRatesDialog.setLocationRelativeTo(this);
            modifyTaxRatesDialog.setVisible(true);
        } else if (e.getSource() == generatePaySlipButton) {
            // Prompt user to specify pay period via date selection
            JPanel datePanel = new JPanel();
            datePanel.setLayout(new GridLayout(3, 2));

            JTextField startDateField = new JTextField(10);
            JTextField endDateField = new JTextField(10);

            datePanel.add(new JLabel("Start Date (MM/DD/YYYY):"));
            datePanel.add(startDateField);
            datePanel.add(new JLabel("End Date (MM/DD/YYYY):"));
            datePanel.add(endDateField);

            int result = JOptionPane.showConfirmDialog(this, datePanel, "Select Pay Period", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Retrieve start and end dates entered by the user
                String startDateStr = startDateField.getText();
                String endDateStr = endDateField.getText();

                // Generate payslip for the specified pay period
                String payslip = generatePayslip(startDateStr, endDateStr);

                // Display the payslip in a message dialog
                JOptionPane.showMessageDialog(this, "Payslip for period " + startDateStr + " to " + endDateStr + ":\n" + payslip);
            }
        } else if (e.getSource() == requestLeaveButton) {
            // Create a dialog for requesting leave
            JPanel leavePanel = new JPanel(new GridLayout(3, 2));

            JTextField dateField = new JTextField(10);
            JTextField hoursField = new JTextField(10);

            leavePanel.add(new JLabel("Leave Date (MM/DD/YYYY):"));
            leavePanel.add(dateField);
            leavePanel.add(new JLabel("Number of Hours:"));
            leavePanel.add(hoursField);

            int result = JOptionPane.showConfirmDialog(this, leavePanel, "Request Leave", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Retrieve leave date and hours entered by the employee
                String leaveDate = dateField.getText();
                String hours = hoursField.getText();

                JOptionPane.showMessageDialog(this, "Leave requested for " + leaveDate + " for " + hours + " hours.");
            }
        }


    }


    private String generatePayrollReport(String startDateStr, String endDateStr) {

        // For demonstration purposes, simply return a dummy report
        return "Payroll report for period " + startDateStr + " to " + endDateStr;
    }

    private String generatePayslip(String startDateStr, String endDateStr) {

        // For demonstration purposes, simply return a dummy payslip
        StringBuilder payslip = new StringBuilder();
        payslip.append("Employee Name: John Doe\n");
        payslip.append("Pay Period: ").append(startDateStr).append(" to ").append(endDateStr).append("\n");
        payslip.append("Total Hours Worked: 80\n");
        payslip.append("Hourly Rate: $15\n");
        payslip.append("Total Salary: $1200\n");

        return payslip.toString();
    }

}

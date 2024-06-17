package employee.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalculateSalary extends JFrame implements ActionListener {

    Choice monthChoice, yearChoice;
    JButton viewButton, saveButton, back;
    JTable table;
    String role;
    
    public CalculateSalary(String role) {
    	this.role = role;
        setLayout(null);

        JLabel heading = new JLabel("Calculate Salary");
        heading.setBounds(320, 20, 200, 30);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(heading);

        JLabel labelMonth = new JLabel("Month:");
        labelMonth.setBounds(50, 80, 50, 30);
        add(labelMonth);

        monthChoice = new Choice();
        monthChoice.setBounds(120, 80, 80, 30);
        for (int i = 1; i <= 12; i++) {
            monthChoice.add(String.valueOf(i));
        }
        add(monthChoice);

        JLabel labelYear = new JLabel("Year:");
        labelYear.setBounds(220, 80, 50, 30);
        add(labelYear);

        yearChoice = new Choice();
        yearChoice.setBounds(290, 80, 80, 30);
        for (int i = 2020; i <= 2030; i++) {
            yearChoice.add(String.valueOf(i));
        }
        add(yearChoice);

        viewButton = new JButton("View");
        viewButton.setBounds(390, 80, 80, 30);
        viewButton.addActionListener(this);
        add(viewButton);

        saveButton = new JButton("Save to Database");
        saveButton.setBounds(490, 80, 150, 30);
        saveButton.addActionListener(this);
        add(saveButton);

        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 150, 880, 600);
        add(jsp);
        
        back = new JButton("Back");
        back.setBounds(660, 80, 100, 30);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);
        
        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewButton) {
            int month = Integer.parseInt(monthChoice.getSelectedItem());
            int year = Integer.parseInt(yearChoice.getSelectedItem());
            displaySalaryDetails(month, year);
        } else if (ae.getSource() == saveButton) {
            saveSalaryDetailsToDatabase();
        } else if (ae.getSource() == back){
        	setVisible(false);
            new Home(role);
        }
    }

    public void displaySalaryDetails(int month, int year) {
        try {
            Conn c = new Conn();
            String query = "SELECT e.empId, e.name, c.salary, c.bonus, c.tax, c.working_days FROM employee e JOIN contract c ON e.empId = c.user_id";
            Statement statement1 = c.c.createStatement();
            ResultSet rs = statement1.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Salary", "Bonus", "Tax", "Days Worked", "Penalty", "Calculated Salary"}, 0);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            while (rs.next()) {
                String empId = rs.getString("empId");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                double tax = rs.getDouble("tax");
                int workingDays = rs.getInt("working_days");

                // Calculate days worked and penalty
                int daysWorked = 0;
                double penalty = 0;
                String timekeepingQuery = "SELECT * FROM timekeeping WHERE user_id = " + empId + " AND MONTH(date) = " + month + " AND YEAR(date) = " + year;
                Statement statement2 = c.c.createStatement();
                ResultSet rsTimekeeping = statement2.executeQuery(timekeepingQuery);

                while (rsTimekeeping.next()) {
                    Date startTime = timeFormat.parse(rsTimekeeping.getString("start_time"));
                    Date endTime = timeFormat.parse(rsTimekeeping.getString("end_time"));

                    long duration = endTime.getTime() - startTime.getTime();
                    long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

                    // Apply penalties
                    if (diffInHours < 8 && startTime.after(timeFormat.parse("08:00:00"))) {
                        penalty += 1; // Penalty of 1 unit
                    } else if (diffInHours >= 8) {
                        daysWorked++;
                    }
                }
                rsTimekeeping.close();
                statement2.close();

                // Calculate the final salary
                double calculatedSalary = salary * daysWorked / workingDays + bonus - tax - penalty;
                calculatedSalary = Math.round(calculatedSalary); // Round the calculated salary
                model.addRow(new Object[]{empId, name, salary, bonus, tax, daysWorked, penalty, calculatedSalary});
            }
            rs.close();
            statement1.close();

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSalaryDetailsToDatabase() {
        try {
            Conn c = new Conn();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                String empId = (String) model.getValueAt(i, 0);
                double bonus = Double.parseDouble(model.getValueAt(i, 3).toString());
                double tax = Double.parseDouble(model.getValueAt(i, 4).toString());
                int workDay = Integer.parseInt(model.getValueAt(i, 5).toString());
                double penalty = Double.parseDouble(model.getValueAt(i, 6).toString());
                double calculatedSalary = Double.parseDouble(model.getValueAt(i, 7).toString());
                int month = Integer.parseInt(monthChoice.getSelectedItem());
                int year = Integer.parseInt(yearChoice.getSelectedItem());

                String query = "INSERT INTO salary (empId, bonus, tax, work_day, penalty, calculated_salary, month, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, empId);
                ps.setDouble(2, bonus);
                ps.setDouble(3, tax);
                ps.setInt(4, workDay);
                ps.setDouble(5, penalty);
                ps.setDouble(6, Math.round(calculatedSalary)); // Round the calculated salary before saving
                ps.setInt(7, month);
                ps.setInt(8, year);

                ps.executeUpdate();
                ps.close();
            }
            JOptionPane.showMessageDialog(null, "Salary details saved to database successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CalculateSalary("Admin");
    }
}

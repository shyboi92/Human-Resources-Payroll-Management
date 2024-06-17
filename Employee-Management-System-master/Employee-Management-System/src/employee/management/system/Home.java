package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    JButton calculate, timekeeping, employManagement;
    String role;

    Home(String role) {
        this.role = role;
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 630);
        add(image);
        
        JLabel heading = new JLabel("Employee Management System");
        heading.setBounds(620, 20, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        image.add(heading);
        
        calculate = new JButton("Calculate Salary");
        calculate.setBounds(650, 80, 150, 40);
        calculate.addActionListener(this);
        
        timekeeping = new JButton("Timekeeping");
        timekeeping.setBounds(820, 80, 150, 40);
        timekeeping.addActionListener(this);
        
        employManagement = new JButton("Employ Management");
        employManagement.setBounds(650, 150, 150, 40);
        employManagement.addActionListener(this);
        
        if (role.equals("Admin")) {
            image.add(calculate);
            image.add(timekeeping);
            image.add(employManagement);
        } else if (role.equals("tpnhansu")) {
            image.add(employManagement);
        } else if (role.equals("ketoan")) {
            image.add(calculate);
            image.add(timekeeping);
        }
        
        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == calculate) {
            setVisible(false);
            new CalculateSalary(role);
        } else if (ae.getSource() == timekeeping) {
            setVisible(false);
            new Timekeeping(role);
        } else if (ae.getSource() == employManagement) {
            setVisible(false);
            new EmployManagement(role);
        }
    }

    public static void main(String[] args) {
        new Home("Admin");
    }
}

package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Profile extends JFrame implements ActionListener{

    JButton view, add, update, remove, calculate, timekeeping, Profile, back;
    String role;
    Profile(String role) {
        this.role = role;
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 630);
        add(image);
        
        JLabel heading = new JLabel("Profile Management");
        heading.setBounds(620, 20, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        image.add(heading);
        
        add = new JButton("Add Employee");
        add.setBounds(650, 80, 150, 40);
        add.addActionListener(this);
        image.add(add);
        
        view = new JButton("View Employees");
        view.setBounds(820, 80, 150, 40);
        view.addActionListener(this);
        image.add(view);
        
        update = new JButton("Update Employee");
        update.setBounds(650, 140, 150, 40);
        update.addActionListener(this);
        image.add(update);
        
        remove = new JButton("Remove Employee");
        remove.setBounds(820, 140, 150, 40);
        remove.addActionListener(this);
        image.add(remove);
        
        back = new JButton("Back");
        back.setBounds(650, 200, 200, 40);
        back.addActionListener(this);
        image.add(back);
        
        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            setVisible(false);
            new AddEmployee(role);
        } else if (ae.getSource() == view) {
            setVisible(false);
            new ViewEmployee(role);
        } else if (ae.getSource() == update) {
            setVisible(false);
            new ViewEmployee(role);
        } else if (ae.getSource() == remove){
        	setVisible(false);
        	new RemoveEmployee(role);
        }else	{
            setVisible(false);
            new EmployManagement(role);
        }
    }

    public static void main(String[] args) {
        new Profile("Admin");
    }
}

package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployManagement extends JFrame implements ActionListener{

    JButton profile, update, remove, calculate, timekeeping, contract, back;
    String role;
    
    EmployManagement(String role) {
        this.role = role;
    	
        setLayout(null);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 630);
        add(image);
//        getContentPane().setBackground(Color.WHITE);
//        setLayout(null);
        
        JLabel heading = new JLabel("Employ Management");
        heading.setBounds(620, 20, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        image.add(heading);
        
        profile= new JButton("Profile");
        profile.setBounds(650, 80, 150, 40);
        profile.addActionListener(this);
        image.add(profile);
        
        contract = new JButton("Contract");
        contract.setBounds(820, 80, 150, 40);
        contract.addActionListener(this);
        image.add(contract);
        
        back = new JButton("Back");
        back.setBounds(650, 140, 150, 40);
        back.addActionListener(this);
        image.add(back);
        
        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == profile) {
            setVisible(false);
            new Profile(role);
        } else if (ae.getSource() == contract) {
            setVisible(false);
            new Contract(role);
        }else	{
            setVisible(false);
            new Home(role);
        }
    }

    public static void main(String[] args) {
        new EmployManagement("Admin");
    }
}

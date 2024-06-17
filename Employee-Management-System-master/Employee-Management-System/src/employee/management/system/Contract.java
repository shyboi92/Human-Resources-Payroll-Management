package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Contract extends JFrame implements ActionListener{

    JButton view, add, update, remove, calculate, timekeeping, contract, back;
    String role;
    Contract(String role) {
        this.role = role;
        setLayout(null);
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Contract Management");
        heading.setBounds(350, 20, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        add(heading);
        
        add = new JButton("Add Contract");
        add.setBounds(175, 80, 200, 40);
        add.addActionListener(this);
        add(add);
        
        view = new JButton("View Contract");
        view.setBounds(525, 80, 200, 40);
        view.addActionListener(this);
        add(view);
        
        update = new JButton("Update Contract");
        update.setBounds(175, 140, 200, 40);
        update.addActionListener(this);
        add(update);
        
        back = new JButton("Back");
        back.setBounds(175, 200, 200, 40);
        back.addActionListener(this);
        add(back);
        
        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            setVisible(false);
            new AddContract(role);
        } else if (ae.getSource() == view) {
            setVisible(false);
            new ViewContract(role);
        } else if (ae.getSource() == update) {
            setVisible(false);
            new ViewContract(role);
        }else	{
            setVisible(false);
            new EmployManagement(role);
        }
    }

    public static void main(String[] args) {
        new Contract("Admin");
    }
}

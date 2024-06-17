package employee.management.system;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;
import java.text.SimpleDateFormat;
import java.awt.event.*;
import java.sql.*;

public class AddContract extends JFrame implements ActionListener {
    
    JTextField UserID, Salary, tfbonus, tfworkday, tfdayoff, tftax;
    JDateChooser StartDate, EndDate;
    JComboBox<String> tfjobpos;
    JButton add, back;
    String role;
    AddContract(String role) {
    	this.role = role;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Add Contract Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);
        
        JLabel labelui = new JLabel("user_id");
        labelui.setBounds(50, 150, 150, 30);
        labelui.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelui);
        
        UserID = new JTextField();
        UserID.setBounds(200, 150, 150, 30);
        add(UserID);
        
        JLabel labelsa = new JLabel("Salary");
        labelsa.setBounds(400, 150, 150, 30);
        labelsa.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsa);
        
        Salary = new JTextField();
        Salary.setBounds(600, 150, 150, 30);
        add(Salary);
        
        JLabel labelsd = new JLabel("Start Date");
        labelsd.setBounds(50, 200, 150, 30);
        labelsd.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsd);
        
        StartDate = new JDateChooser();
        StartDate.setBounds(200, 200, 150, 30);
        add(StartDate);
        
        JLabel labeled = new JLabel("End Date");
        labeled.setBounds(400, 200, 150, 30);
        labeled.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeled);
        
        EndDate = new JDateChooser();
        EndDate.setBounds(600, 200, 150, 30);
        add(EndDate);
        
        JLabel labelbonus = new JLabel("Bonus");
        labelbonus.setBounds(50, 250, 150, 30);
        labelbonus.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelbonus);
        
        tfbonus = new JTextField();
        tfbonus.setBounds(200, 250, 150, 30);
        add(tfbonus);
        
        JLabel labelworkday = new JLabel("Working days");
        labelworkday.setBounds(50, 300, 150, 30);
        labelworkday.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelworkday);
        
        tfworkday = new JTextField();
        tfworkday.setBounds(200, 300, 150, 30);
        add(tfworkday);
        
        JLabel labeldayoff = new JLabel("Number of days off");
        labeldayoff.setBounds(400, 300, 150, 30);
        labeldayoff.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldayoff);
        
        tfdayoff = new JTextField();
        tfdayoff.setBounds(600, 300, 150, 30);
        add(tfdayoff);
        
        JLabel labeljobpos = new JLabel("Job Position");
        labeljobpos.setBounds(400, 250, 150, 30);
        labeljobpos.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeljobpos);
        
        String[] courses = { "Intern", "Junior", "Middle-Senior", "Senior" };
        tfjobpos = new JComboBox<>(courses);
        tfjobpos.setBackground(Color.WHITE);
        tfjobpos.setBounds(600, 250, 150, 30);
        add(tfjobpos);
        
        JLabel labeltax = new JLabel("Tax");
        labeltax.setBounds(50, 400, 150, 30);
        labeltax.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeltax);
        
        tftax = new JTextField();
        tftax.setBounds(200, 400, 150, 30);
        add(tftax);
        
        add = new JButton("Add Details");
        add.setBounds(250, 550, 150, 40);
        add.addActionListener(this);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add(add);
        
        back = new JButton("Back");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);
        
        setSize(900, 700);
        setLocation(300, 50);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            try {
                int user_id = Integer.parseInt(UserID.getText());
                double salary = Double.parseDouble(Salary.getText());
                double bonus = Double.parseDouble(tfbonus.getText());
                int working_days = Integer.parseInt(tfworkday.getText());
                int day_off = Integer.parseInt(tfdayoff.getText());
                double tax = Double.parseDouble(tftax.getText());
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String start_date = sdf.format(StartDate.getDate());
                String end_date = sdf.format(EndDate.getDate());
                String job_position = (String) tfjobpos.getSelectedItem();
                
                Conn conn = new Conn();
                
                String checkUserQuery = "SELECT COUNT(*) FROM employee WHERE empId = " + user_id;
                ResultSet rs = conn.s.executeQuery(checkUserQuery);
                if (rs.next() && rs.getInt(1) > 0) {
                    String query = "INSERT INTO contract (user_id, salary, start_date, end_date, bonus, job_position, working_days, day_off, tax) " +
                                   "VALUES ('"+user_id+"', '"+salary+"', '"+start_date+"', '"+end_date+"', '"+bonus+"', '"+job_position+"', '"+working_days+"', '"+day_off+"', '"+tax+"')";
                    conn.s.executeUpdate(query);
                    
                    JOptionPane.showMessageDialog(null, "Details added successfully");
                    setVisible(false);
                    new Home(role);
                } else {
                    JOptionPane.showMessageDialog(null, "User ID does not exist in the employee table.");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Contract(role);
        }
    }

    public static void main(String[] args) {
        new AddContract("Admin");
    }
}

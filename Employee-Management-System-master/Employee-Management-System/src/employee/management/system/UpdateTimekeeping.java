package employee.management.system;

import java.awt.*;
import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import java.awt.event.*;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateTimekeeping extends JFrame implements ActionListener{
    
    JTextField tfeducation, tffname, tfstart_time, tfend_time, tfaadhar, tfemail, tfdesignation;
    JDateChooser tfdate;
    JSpinner spStartTime, spEndTime;
    JLabel lblempId;
    JButton add, back;
    String empId, role;
    
    UpdateTimekeeping(String empId, String role) {
        this.empId = empId;
        this.role= role;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Update Timekeeping Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);
        
        JLabel labeldate = new JLabel("Date");
        labeldate.setBounds(400, 200, 150, 30);
        labeldate.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldate);
        
        tfdate = new JDateChooser();
        tfdate.setBounds(600, 200, 150, 30);
        add(tfdate);
        
        JLabel labelstart_time = new JLabel("Start time");
        labelstart_time.setBounds(50, 250, 150, 30);
        labelstart_time.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelstart_time);
        
        spStartTime = createTimeSpinner();
        spStartTime.setBounds(200, 250, 150, 30);
        add(spStartTime);
        
        JLabel labelend_time = new JLabel("End time");
        labelend_time.setBounds(400, 250, 150, 30);
        labelend_time.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelend_time);
        
        spEndTime = createTimeSpinner();
        spEndTime.setBounds(600, 250, 150, 30);
        add(spEndTime);
        
        try {
            Conn c = new Conn();
            String query = "select * from timekeeping where user_id = '"+empId+"'";
            ResultSet rs = c.s.executeQuery(query);
            while(rs.next()) {
            	tfdate.setDate(rs.getDate("date"));
                spStartTime.setValue(rs.getTime("start_time"));
                spEndTime.setValue(rs.getTime("end_time"));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        add = new JButton("Update Details");
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
    
    private JSpinner createTimeSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
        spinner.setEditor(editor);
        return spinner;
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
        	Date date = tfdate.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(date);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String start_time = timeFormat.format(spStartTime.getValue());
            String end_time = timeFormat.format(spEndTime.getValue());
            
            try {
                Conn conn = new Conn();
                String query = "insert into timekeeping (user_id, date, start_time, end_time) values ('" + empId + "', '" + dateString + "', '" + start_time + "', '" + end_time + "')";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Details updated successfully");
                setVisible(false);
                new Timekeeping(role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Timekeeping(role);
        }
    }

    public static void main(String[] args) {
        new UpdateTimekeeping("","Admin");
    }
}

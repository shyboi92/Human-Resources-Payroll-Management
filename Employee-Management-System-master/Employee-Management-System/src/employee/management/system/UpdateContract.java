package employee.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateContract extends JFrame implements ActionListener {
    
    JTextField tfsalary, tfbonus, tftax, tfstartDate, tfendDate, tfjobPosition, tfworkingDays, tfdayOff;
    JLabel lblcontractId;
    JButton update, back;
    String contractId, role;
    
    UpdateContract(String contractId, String role) {
        this.contractId = contractId;
        this.role = role;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Update Contract Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);
        
        JLabel labelsalary = new JLabel("Salary");
        labelsalary.setBounds(50, 150, 150, 30);
        labelsalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsalary);
        
        tfsalary = new JTextField();
        tfsalary.setBounds(200, 150, 150, 30);
        add(tfsalary);
        
        JLabel labelbonus = new JLabel("Bonus");
        labelbonus.setBounds(400, 150, 150, 30);
        labelbonus.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelbonus);
        
        tfbonus = new JTextField();
        tfbonus.setBounds(600, 150, 150, 30);
        add(tfbonus);
        
        JLabel labeltax = new JLabel("Tax");
        labeltax.setBounds(50, 200, 150, 30);
        labeltax.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeltax);
        
        tftax = new JTextField();
        tftax.setBounds(200, 200, 150, 30);
        add(tftax);
        
        JLabel labelstartDate = new JLabel("Start Date");
        labelstartDate.setBounds(400, 200, 150, 30);
        labelstartDate.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelstartDate);
        
        tfstartDate = new JTextField();
        tfstartDate.setBounds(600, 200, 150, 30);
        add(tfstartDate);
        
        JLabel labelendDate = new JLabel("End Date");
        labelendDate.setBounds(50, 250, 150, 30);
        labelendDate.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelendDate);
        
        tfendDate = new JTextField();
        tfendDate.setBounds(200, 250, 150, 30);
        add(tfendDate);
        
        JLabel labeljobPosition = new JLabel("Job Position");
        labeljobPosition.setBounds(400, 250, 150, 30);
        labeljobPosition.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeljobPosition);
        
        tfjobPosition = new JTextField();
        tfjobPosition.setBounds(600, 250, 150, 30);
        add(tfjobPosition);
        
        JLabel labelworkingDays = new JLabel("Working Days");
        labelworkingDays.setBounds(50, 300, 150, 30);
        labelworkingDays.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelworkingDays);
        
        tfworkingDays = new JTextField();
        tfworkingDays.setBounds(200, 300, 150, 30);
        add(tfworkingDays);
        
        JLabel labeldayOff = new JLabel("Day Off");
        labeldayOff.setBounds(400, 300, 150, 30);
        labeldayOff.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldayOff);
        
        tfdayOff = new JTextField();
        tfdayOff.setBounds(600, 300, 150, 30);
        add(tfdayOff);
        
        JLabel labelcontractId = new JLabel("Contract ID");
        labelcontractId.setBounds(50, 400, 150, 30);
        labelcontractId.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelcontractId);
        
        lblcontractId = new JLabel();
        lblcontractId.setBounds(200, 400, 150, 30);
        lblcontractId.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblcontractId);
        
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM contract WHERE contract_id = ?";
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, contractId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tfsalary.setText(rs.getString("salary"));
                tfbonus.setText(rs.getString("bonus"));
                tftax.setText(rs.getString("tax"));
                tfstartDate.setText(rs.getString("start_date"));
                tfendDate.setText(rs.getString("end_date"));
                tfjobPosition.setText(rs.getString("job_position"));
                tfworkingDays.setText(rs.getString("working_days"));
                tfdayOff.setText(rs.getString("day_off"));
                lblcontractId.setText(rs.getString("contract_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        update = new JButton("Update Details");
        update.setBounds(250, 550, 150, 40);
        update.addActionListener(this);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        add(update);
        
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
        if (ae.getSource() == update) {
            String salary = tfsalary.getText();
            String bonus = tfbonus.getText();
            String tax = tftax.getText();
            String startDate = tfstartDate.getText();
            String endDate = tfendDate.getText();
            String jobPosition = tfjobPosition.getText();
            String workingDays = tfworkingDays.getText();
            String dayOff = tfdayOff.getText();
            
            try {
                Conn conn = new Conn();
                String query = "UPDATE contract SET salary = ?, bonus = ?, tax = ?, start_date = ?, end_date = ?, job_position = ?, working_days = ?, day_off = ? WHERE contract_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setDouble(1, Double.parseDouble(salary));
                pstmt.setDouble(2, Double.parseDouble(bonus));
                pstmt.setDouble(3, Double.parseDouble(tax));
                pstmt.setDate(4, Date.valueOf(startDate));
                pstmt.setDate(5, Date.valueOf(endDate));
                pstmt.setString(6, jobPosition);
                pstmt.setInt(7, Integer.parseInt(workingDays));
                pstmt.setInt(8, Integer.parseInt(dayOff));
                pstmt.setString(9, contractId);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Details updated successfully");
                setVisible(false);
                new Home(role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Contract(role);
        }
    }

    public static void main(String[] args) {
        new UpdateContract("", "Admin");
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// custom exception for unavailable doctor
class DoctorNotAvailableException extends Exception {
    public DoctorNotAvailableException(String message) {
        super(message);
    }
}

// appointment class
class Appointment {
    String patient;
    String doctor;

    Appointment(String p, String d) {
        patient = p;
        doctor = d;
    }
}

public class HospitalSystem extends JFrame implements ActionListener {

    JTextField patientField;
    JComboBox<String> doctorBox;
    JButton checkButton, bookButton, displayButton;
    JTextArea displayArea;
    JLabel statusLabel;

    HashMap<String,String> doctorStatus = new HashMap<>();
    ArrayList<Appointment> appointments = new ArrayList<>();

    public HospitalSystem() {

        setTitle("Hospital Management System");
        setSize(500,450);
        setLayout(new FlowLayout());

        // doctor availability
        doctorStatus.put("Dr. Ravi","Available");
        doctorStatus.put("Dr. Meena","Available");
        doctorStatus.put("Dr. Arjun","Available");

        patientField = new JTextField(15);

        doctorBox = new JComboBox<>(new String[]{
                "Dr. Ravi",
                "Dr. Meena",
                "Dr. Arjun"
        });

        checkButton = new JButton("Check Availability");
        bookButton = new JButton("Book Appointment");
        displayButton = new JButton("Display Appointments");

        statusLabel = new JLabel("Doctor status here");

        displayArea = new JTextArea(10,35);

        add(new JLabel("Patient Name:"));
        add(patientField);

        add(new JLabel("Select Doctor:"));
        add(doctorBox);

        add(checkButton);
        add(bookButton);
        add(displayButton);

        add(statusLabel);
        add(displayArea);

        checkButton.addActionListener(this);
        bookButton.addActionListener(this);
        displayButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String doctor = (String)doctorBox.getSelectedItem();

        // check doctor availability
        if(e.getSource() == checkButton){

            int count = 0;
            for(Appointment a : appointments){
                if(a.doctor.equals(doctor)) count++;
            }

            if(count >= 3){
                statusLabel.setText(doctor + " is NOT available (full)");
            } else {
                statusLabel.setText(doctor + " is Available");
            }
        }

        // book appointment
        if(e.getSource() == bookButton){

            String patient = patientField.getText();

            try{
                if(patient.trim().isEmpty()){
                    throw new Exception("Enter patient name");
                }

                // count current appointments for this doctor
                int count = 0;
                for(Appointment a : appointments){
                    if(a.doctor.equals(doctor)) count++;
                }

                if(count >= 3){
                    throw new DoctorNotAvailableException("Doctor appointment limit reached");
                }

                appointments.add(new Appointment(patient, doctor));
                JOptionPane.showMessageDialog(this,"Appointment Booked");

                patientField.setText("");

            } catch(DoctorNotAvailableException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }

        // display appointments
        if(e.getSource() == displayButton){

            displayArea.setText("");

            for(Appointment a : appointments){
                displayArea.append(
                        "Patient: " + a.patient +
                        " | Doctor: " + a.doctor + "\n");
            }
        }
    }

    public static void main(String[] args){
        new HospitalSystem();
    }
}
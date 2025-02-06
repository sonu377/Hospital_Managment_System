package hospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class hospitalManagementSystem {
    private static final String url ="jdbc:mysql://127.0.0.1:3306/hospital";
    private static final String user = "root";
    private static final String password = "System@1234";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
          e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url,user,password);
        Patients p = new Patients(connection,sc);
        Doctors d = new Doctors(connection);
        while(true){
            System.out.println("hospital management system");

            System.out.println("1. Add patient");
            System.out.println("2. view patient");
            System.out.println("3. view Doctors");
            System.out.println("4. Book Apointments");
            System.out.println("5. Exit");
            System.out.println("enter your choice");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    //add patient
                    p.addpatients();
                    System.out.println("");

                case 2:
                    //view patient
                    p.Viewpaties();
                    System.out.println("");

                case 3:
                    d.ViewDoctors();
                        //view doctors
                    System.out.println("");
                case 4:

                    //book appointment
                    boookAppointment(p,d,connection,sc);

                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void boookAppointment(Patients  patient, Doctors doctors,Connection con,Scanner sc){
        System.out.println("Enter patient ID");
        int patientID = sc.nextInt();
        System.out.println("Enter doctor ID");
        int doctorID = sc.nextInt();
        System.out.println("enter appointmentdate (yyyy-MM-dd) ");
        String appointmentDate = sc.next();
        if (patient.getpatientsById(patientID)&&doctors.getDOctorById(doctorID)){
            if(checkDoctorsAvailibility(doctorID,appointmentDate,con)){
String appointmentQuery="INSERT INTO appointments (patient_id,doctor_id,appointment_date) VALUES (?,?,?)";
            try {
                PreparedStatement preparedStatement =con.prepareStatement(appointmentQuery);
                preparedStatement.setInt(1,patientID);
                preparedStatement.setInt(2,doctorID);
                preparedStatement.setString(3,appointmentDate);
int affectedRows = preparedStatement.executeUpdate();
if(affectedRows > 0){
    System.out.println("Appointment book successfully");
}else {
    System.out.println("Appointment book failed");
}

            }catch (SQLException e){
                e.printStackTrace();
            }
            } else{
                System.out.println("appointment not available");
            }
        }else{
            System.out.println("Patient and doctors does not exist");
        }
    }
    public static boolean checkDoctorsAvailibility(int doctorID, String appointmentDate, Connection conn){
String query = "SELECT COUNT (*) FROM appointments WHERE patient_id = ? AND appointment_date = ?";
try{
    PreparedStatement preparedStatement =conn.prepareStatement(query);
    preparedStatement.setInt(1,doctorID);
    preparedStatement.setString(2,appointmentDate);
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()){
        int cout = resultSet.getInt(1);
        if(cout==0){}
        return true;

    }else {
        return false;
    }
}catch (SQLException e){
    e.printStackTrace();
}
return false;
    }
}

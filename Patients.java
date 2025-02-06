package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
    private Connection connection;
    private Scanner scanner;
    Patients(Connection connection, Scanner scanner) {
this.connection = connection;
this.scanner = scanner;

    }
public void addpatients() {
    System.out.println("enter the name of the patient");
    String name = scanner.nextLine();
    System.out.println("enter the age of the patient");
    int age = scanner.nextInt();
    System.out.println("enter the gender of the patient");
    String gender = scanner.nextLine();
    try {
        String query= "INSEERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setString(3, gender);
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("paties added successfully");
        }else {
            System.out.println("paties not added");
        }
    } catch (SQLException e) {
e.printStackTrace();
    }
}
public void Viewpaties() {
        String query = "SELECT * FROM paties";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("patients: ");
            System.out.println("+-----------+-----------------------+--------------+---------------+");
            System.out.println("| paties id | name                   |age           |gender         ");
            System.out.println("+-----------+-----------------------+--------------+---------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|-%12s|-%25s|-%14s|-%15s|\n", id, name, age, gender);
                System.out.println("+-----------+-----------------------+--------------+---------------+");

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public boolean getpatientsById(int id    ) {
        String query = "SELECT * FROM paties WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }else {
                return false;
            }
           } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

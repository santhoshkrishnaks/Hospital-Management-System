package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addPatient(){
        System.out.print("Enter Patient Name: ");
        String name=scanner.next();
        System.out.print("Enter Patient Age");
        int age= scanner.nextInt();
        System.out.print("Enter Patient Gender");
        String gender=scanner.next();
        try {
            String query = "Insert into patients(name,age,gender) values(?,?,?);";
            PreparedStatement prep=connection.prepareStatement(query);
            prep.setString(1,name);
            prep.setInt(2,age);
            prep.setString(3,gender);
            int rows=prep.executeUpdate();
            if(rows>0) {
              System.out.println("Patient Added Successfully");
            }
            else {
                System.out.println("Failed to Add the Patient");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewPatient() {
        String query="Select * from patients";
        try {
            PreparedStatement prep=connection.prepareStatement(query);
            ResultSet resultSet=prep.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getPatientByName(String name) {
        String query = "Select * from patients where name=?";
        try {
            PreparedStatement prep=connection.prepareStatement(query);
            prep.setString(1,name);
            ResultSet resultSet=prep.executeQuery();
            if(resultSet.next()){
                System.out.println("+------------+--------------------+----------+------------+");
                System.out.println("| Patient Id | Name               | Age      | Gender     |");
                System.out.println("+------------+--------------------+----------+------------+");
                int id = resultSet.getInt("id");
                String name1 = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name1, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }
            else{
                System.out.println("Patient Not Found");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

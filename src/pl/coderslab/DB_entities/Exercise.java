package pl.coderslab.DB_entities;

import pl.coderslab.Connection.DbManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Exercise {
    private int id=0;
    private String title;
    private String description;

    //constructor 1
    public Exercise(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    //constructor2
    public Exercise() {
    }

    //setters and getters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    //methods
    public void saveToDB(){

        if(this.id==0){
            try {
                String sql="INSERT into exercise (title, description) VALUES (?,?)";
                String generatedColumns[] = {"ID"};
                PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql,generatedColumns);

                pstm.setString(1, this.title);
                pstm.setString(2, this.description);
                pstm.executeUpdate();
                ResultSet rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }

                System.out.println("Item "+ this.id+" " + this.title + " successfully added");

            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else {
            try{
                String sql = "UPDATE exercise SET title=?, description=? where id = ?";
                PreparedStatement preparedStatement;
                preparedStatement = DbManager.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setString(1, this.title);
                preparedStatement.setString(2, this.description);
                preparedStatement.setInt(3, this.id);
                preparedStatement.executeUpdate();

            }catch (SQLException e){e.printStackTrace();}
        }
    }

    public void delete(){
        String sql="DELETE FROM exercise WHERE id=?";

        try {
            PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setInt(1,this.id);
            pstm.executeUpdate();
            this.id=0;
            System.out.println("Item removed from DB");
            System.out.println("\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Exercise loadbyId(int id){
        String sql="SELECT * FROM exercise where id=?";

        try {
            PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setInt(1,id);
            ResultSet rs=pstm.executeQuery();

            if(rs.next()){
                Exercise exc=new Exercise();
                exc.id=rs.getInt(1);
                exc.title=rs.getString(2);
                exc.description=rs.getString(3);
                return exc;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Exercise> loadAll(){
        String sql="SELECT * FROM exercise";
        try {
            ArrayList<Exercise> exList=new ArrayList<>();
            PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs=pstm.executeQuery();

            while (rs.next()){
                Exercise exc=new Exercise();
                exc.id=rs.getInt(1);
                exc.title=rs.getString(2);
                exc.description=rs.getString(3);
                exList.add(exc);
            }
            return exList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Exercise{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + '}';
    }


    public static Exercise createExercise(Scanner scanner){
        Exercise ex=new Exercise();
        System.out.println("Enter title");
        ex.setTitle(scanner.nextLine());
        System.out.println("Enter description");
        ex.setDescription(scanner.nextLine());

        return  ex;
    }

}


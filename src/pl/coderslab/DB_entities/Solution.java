package pl.coderslab.DB_entities;

import pl.coderslab.Connection.DbManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
    private int id=0;
    private String created;
    private String updated;
    private String description;
    private int exercise_id;
    private int user_id;

    //constructor 1
    public Solution(String created, String updated, String description, int exercise_id, int user_id) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise_id = exercise_id;


    }

    //constructor 2
    public Solution() {
    }


    //getters and setters
    public int getId() {
        return id;
    }

    public String getUpdated() {
        return updated;
    }

    public String getDescription() {
        return description;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Solution{" + "id=" + getId() + ", created='" + created + '\'' + ", updated='" + updated + '\'' + ", description='" + description + '\'' + ", exercise_id=" + exercise_id + ", user_id=" + user_id + '}';
    }


    public void saveToDB(){
        if(getId()==0){
            try {
                String sql="INSERT into solution (created, exercise_id, user_id) VALUES (?,?,?)";
                String generatedColumns[] = {"ID"};
                PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql,generatedColumns);
                pstm.setString(1, this.created);
                pstm.setInt(2, this.exercise_id);
                pstm.setInt(3, this.user_id);
                pstm.executeUpdate();
                ResultSet rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            try{
                String sql="UPDATE solution SET created=?, exercise_id=?, user_id=? where id=?";
                PreparedStatement pstm;
                pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
                pstm.setString(1, this.created);
                pstm.setInt(2, this.exercise_id);
                pstm.setInt(3, this.user_id);
                pstm.setInt(4,this.id);
                pstm.executeUpdate();
                System.out.println("Item "+ id+ " successfully updated");
            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }

    }

    public static Solution loadById(int id){
        try {
            String sql="SELECT * FROM solution WHERE id=?";
            PreparedStatement pstm;
            pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setInt(1,id);
            ResultSet rs=pstm.executeQuery();

            if(rs.next()){
                Solution solution=new Solution();
                solution.id=rs.getInt("id");
                solution.created=rs.getString("created");
                solution.updated=rs.getString("updated");
                solution.description=rs.getString("description");
                solution.exercise_id=rs.getInt("exercise_id");
                solution.user_id=rs.getInt("user_id");

                return solution;
            }
        } catch (SQLException e) {
            e.printStackTrace();}
        return null;
    }


    public static ArrayList<Solution> loadAll(){
        String sql="SELECT * FROM solution";
        try {
            ArrayList<Solution> solutionsList=new ArrayList<>();
            PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs=pstm.executeQuery();

            while (rs.next()){
                Solution solution=new Solution();
                solution.id=rs.getInt("id");
                solution.created=rs.getString("created");
                solution.updated=rs.getString("updated");
                solution.description=rs.getString("description");
                solution.exercise_id=rs.getInt("exercise_id");
                solution.user_id=rs.getInt("user_id");

                solutionsList.add(solution);
            }
            return solutionsList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Solution> loadAllByUserId(int userId){
        String sql="SELECT * FROM solution JOIN exercise ON exercise.id=solution.exercise_id WHERE user_id=?";
        ArrayList<Solution> output=new ArrayList<>();
        try {
            PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setInt(1,userId);
            ResultSet rs=pstm.executeQuery();


            while (rs.next()){
                Solution s=new Solution();
                s.id=rs.getInt("id");
                s.created=rs.getString("created");
                s.updated=rs.getString("updated");
                s.description=rs.getString("description");
                s.exercise_id=rs.getInt("exercise_id");
                s.user_id=rs.getInt("user_id");

                output.add(s);
            }
            return output;



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Exercise> loadAllByExerciseId(int exerciseId){
        String sql="SELECT * FROM users JOIN solution ON users.id =solution.user_id " +
                "JOIN exercise ON exercise.id=solution.exercise_id WHERE exercise.id=?;";

        try {
            PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setInt(1, exerciseId);
            ResultSet rs=pstm.executeQuery();

            ArrayList<Exercise> exArr=new ArrayList<>();

            while (rs.next()){
                Exercise ex=new Exercise();
                ex.setId(rs.getInt("id"));
                ex.setTitle(rs.getString("title"));
                ex.setDescription(rs.getString("description"));
                exArr.add(ex);
            }

            return exArr;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateSolution(Scanner scanner, int exercise_id){
        String date=LocalDate.now().toString();
        setUpdated(date);
        System.out.println("Enter description");
        setDescription(scanner.nextLine());


        try{
            String sql="UPDATE solution SET updated=?, description=? where exercise_id=?";
            PreparedStatement pstm;
            pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1, this.updated);
            pstm.setString(2, this.description);
            pstm.setInt(3, exercise_id);

            pstm.executeUpdate();
            System.out.println("Item "+ id+ " successfully updated");
        }
        catch (SQLException e){
            e.printStackTrace();
        }


    }





}



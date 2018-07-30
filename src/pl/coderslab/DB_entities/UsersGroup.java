package pl.coderslab.DB_entities;

import pl.coderslab.Connection.DbManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersGroup {
    private int id=0;
    private String name;

    //setters

    public void setName(String name) {
        this.name = name;
    }

    //constructor 1
    public UsersGroup(String name) {
        setName(name);
    }

    //constructor 1
    public UsersGroup() {
    }

    public void saveToDB(){
        if(this.id==0) {
            try {
                String sql = "INSERT INTO user_group (name) VALUES (?)";
                String generatedColumns[] = {"ID"};
                PreparedStatement pstm;
                pstm = DbManager.getInstance().getConnection().prepareStatement(sql,generatedColumns);
                pstm.setString(1, this.name);
                pstm.executeUpdate();


                ResultSet rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }

                System.out.println("Item " + this.id + " " + this.name + " successfully added");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {try{
            String sql="UPDATE user_group SET name=? where id=?";

            PreparedStatement pstm;
            pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,this.name);
            pstm.setInt(2,this.id);
            pstm.executeUpdate();
            System.out.println("Item "+ id+ " "+name+ " successfully updated");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        }

    }


    public void delete(){
        String sql="DELETE FROM user_group WHERE id=?";
        PreparedStatement pstm;
        try {
            pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setInt(1, this.id);
            pstm.executeUpdate();
            this.id=0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UsersGroup loadById(int id){
        try {
            String sql="SELECT * FROM user_group WHERE id=?";
            PreparedStatement pstm;
            pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setInt(1,id);
            ResultSet rs=pstm.executeQuery();

            if(rs.next()){
                UsersGroup group=new UsersGroup();
                group.id=rs.getInt(1);
                group.name=rs.getString(2);
                return group;
            }
        } catch (SQLException e) {
            e.printStackTrace();}
        return null;
    }

    public static ArrayList<UsersGroup> loadAllGroups(){
        String sql="SELECT * FROM user_group";
        try {
            ArrayList<UsersGroup> tmpList=new ArrayList<>();
            PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs=pstm.executeQuery();

            while (rs.next()){
                UsersGroup group=new UsersGroup();
                group.id=rs.getInt(1);
                group.name=rs.getString(2);
                tmpList.add(group);
            }
            return tmpList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//
//    public static UsersGroup createGroup(Scanner scanner){
//        UsersGroup group=new UsersGroup();
//        System.out.println("Enter name");
//        group.setName(scanner.nextLine());
//
//        return  group;
//    }

    @Override
    public String toString() {
        return "UsersGroup{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

}

package pl.coderslab.DB_entities;

import org.mindrot.BCrypt;
import pl.coderslab.Connection.DbManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    //class fields
    private int id=0;
    private String name;
    private String  username;
    private String password;
    private String email;
    private int user_group_id=0;

    //constructor 1
    public User(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        setPassword(password);
        this.email = email;

    }

    //constructor 2
    public User() {
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password =BCrypt.hashpw(password,BCrypt.gensalt());
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroup(int group) {
        this.user_group_id = group;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    //Active Record
    public void saveToDB(){
        //insert/update
        if(this.id==0){
            try {
            String sql = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
            String generatedColumns[] = {"ID"};
            PreparedStatement preparedStatement;
            preparedStatement = DbManager.getInstance().getConnection()
                    .prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            //preparedStatement.setInt(4, this.user_group_id);
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
                }
            }
            catch (SQLException e) {
                    e.printStackTrace();
                }

        } else {
            try{
                String sql = "UPDATE users SET username=?, email=?, password=? where id = ?";
                PreparedStatement preparedStatement;
                preparedStatement = DbManager.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setString(1, this.username);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                //preparedStatement.setInt(4, this.user_group_id);
                preparedStatement.setInt(4, this.id);
                preparedStatement.executeUpdate();
            }catch (SQLException e){e.printStackTrace();}
        }
    }


    public void delete(){
        //DELETE na bazie i zamienia id na 0
        try {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement;
            preparedStatement = DbManager.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id=0;
        }catch (SQLException e){e.printStackTrace();}
    }



    public static User loadById(int id){
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pstm;
            pstm = DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                User user = new User();
                user.id = rs.getInt("id");
                user.username = rs.getString("username");
                user.email = rs.getString("email");
                user.password = rs.getString("password");
                return user;
            }
        }catch (SQLException e){e.printStackTrace();}
        return null;
    }

    public static ArrayList<User> loadAll() {
        try {
            ArrayList<User> users = new ArrayList<>();
            String sql = "SELECT * FROM users";
            PreparedStatement pstm;
            pstm = DbManager.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.username = rs.getString("username");
                user.email = rs.getString("email");
                user.password = rs.getString("password");
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<User> loadAllByGroupId(int groupId){
        String sql="SELECT * FROM user_group JOIN users on user_group.id=users.user_group_id WHERE user_group_id=?";
        try {
            PreparedStatement pstm=DbManager.getInstance().getConnection().prepareStatement(sql);
            pstm.setInt(1,groupId);
            ResultSet rs=pstm.executeQuery();

            ArrayList<User> array=new ArrayList<>();
            while (rs.next()){
                User user = new User();
                user.id = rs.getInt("id");
                user.username = rs.getString("username");
                user.email = rs.getString("email");
                array.add(user);

            }
            return array;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", username='" + username + '\'' +
                ", password='" + password + '\'' + ", email='" + email + '\'' + ", group=" + user_group_id + '}';
    }


    public static User createUser(Scanner scanner){
        User user=new User();
        System.out.println("Enter name");
        user.setName(scanner.nextLine());
        System.out.println("Enter user name");
        user.username=scanner.nextLine();
        System.out.println("Enter password");
        user.setPassword(scanner.nextLine());
        System.out.println("Enter mail");
        user.email=scanner.nextLine();
//        System.out.println("Enter group id");
//        user.setGroup(UserManager.getNumber());

        return  user;
    }

    public static User modifyUser(User user, Scanner scanner){
        System.out.println("Enter name");
        user.setName(scanner.nextLine());
        System.out.println("Enter user name");
        user.username=scanner.nextLine();
        System.out.println("Enter password");
        user.setPassword(scanner.nextLine());
        System.out.println("Enter mail");
        user.email=scanner.nextLine();
//        System.out.println("Enter group id");
//        user.setGroup(UserManager.getNumber());

        return  user;
    }
}


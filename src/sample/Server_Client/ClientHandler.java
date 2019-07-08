package sample.Server_Client;

import sample.ConnectionClass;
import sample.Session_Id;
import sample.PasswordUtils;

import java.io.*;
import java.net.Socket;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ClientHandler implements Runnable{

    private Socket client;
    private ObjectInputStream objinp;
    private ObjectOutputStream objotp;
    private DataInputStream datainp;
    private DataOutputStream dataotp;
    private String name;
    private ConnectionClass ConnectiontoStudent = new ConnectionClass();
    private Connection connection = ConnectiontoStudent.getConnection();
    private PasswordUtils check = new PasswordUtils();

    public ClientHandler(Socket client,String name,ObjectOutputStream objotp,ObjectInputStream objinp,DataOutputStream dataotp,DataInputStream datainp){
        this.datainp = datainp;
        this.dataotp = dataotp;
        this.name = name;
        this.client = client;
        this.objinp = objinp;
        this.objotp = objotp;
    }

    @Override
    public void run(){
        String type = "";
        while(true){
            try{
                String recieved = datainp.readUTF();
                if(recieved.equalsIgnoreCase("Exit")){
                    System.out.println("Client Connection Terminating");
                    this.client.close();
                    break;
                }
                switch(recieved){
                    case "Login":
                        System.out.println("Login was requested");
                        type =  datainp.readUTF();
                        System.out.println(type);

                        if(type.equalsIgnoreCase("Student")){
                            this.dataotp.writeBoolean(StudentSignin());

                        }else{
                            System.out.println("hello");
                            this.dataotp.writeBoolean(TeacherSignin());
                        }
                        break;

                    case "Signup":
                        System.out.println("Signup was requested");
                        type =  datainp.readUTF();
                        if(type.equalsIgnoreCase("Student")){
                            this.dataotp.writeBoolean(StudentSignup());

                        }else{
                            this.dataotp.writeBoolean(TeacherSignup());
                        }
                        break;

                    case "Name":
                        System.out.println("Name was requested");
                        this.dataotp.writeUTF(getName());
                        System.out.println("Name is hiad ");
                        break;

                    case "getSubjects":
                        System.out.println("Get Subjects was requested");
                        this.objotp.writeObject(getSubjects());
                        break;
                    case "addSubjects":
                        System.out.println("Add Subjects was requested");
                        boolean k = addSubject();
                        System.out.println("knda cringe bro:"+k);
                        this.dataotp.writeBoolean(k);
                        break;
                    case "addQuestion":
                        System.out.println("Get Questions was requested");
                        this.objotp.writeObject(addQuestion());
                        break;
                    case "createTest":
                        System.out.println("Create test was requested");
                        this.dataotp.writeBoolean(createTest());
                        break;
                    case "getSubjectId":
                        System.out.println("Get Subject Id was requested");
                        this.dataotp.writeUTF(getSubjectId());
                        break;
                    case "createSection":
                        System.out.println("Create Section was requested");
                        this.dataotp.writeBoolean(createSection());
                        break;
                    case "getSections":
                        System.out.println("Get Sections was requested");
                        this.objotp.writeObject(getSections());
                        break;
                    case "getQuestions":
                        System.out.println("Get Questions was requested");
                        this.objotp.writeObject(getQuestions());
                        break;
                    case "getTime":
                        System.out.println("Get time was requested");
                        this.dataotp.writeUTF(getTime());
                        break;
                    case "addMarks":
                        System.out.println("Add Marks was requested");
                        this.dataotp.writeBoolean(addMarks());
                        break;
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        try{
            objotp.close();
            objinp.close();
            dataotp.close();
            datainp.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private boolean addMarks(){
        try{
            String test_id = datainp.readUTF();
            String student_id = datainp.readUTF();
            String marks = datainp.readUTF();
            String teacher_id = getTeacherId(test_id);
            if(teacher_id.equalsIgnoreCase("")){
                return false;
            }
            String query = "INSERT INTO `results`(`Test_Id`, `Student_Id`, `Marks`, `Teacher_Id`) VALUES ('"+ test_id +"',"
                    + student_id + "," + marks + "," + teacher_id + ");";
            Statement statement =connection.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException | IOException e) {
            System.out.println(e);
            return false;
        }
    }

    private String getTeacherId(String test_id){
        try{
            String query = "SELECT `Teacher_Id` FROM `tests` WHERE `Test_Id`='" + test_id +"';";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                String s = rs.getString("Teacher_Id");
                return s;
            }
            return "";
        }
        catch(SQLException e){
            e.printStackTrace();
            return "";
        }
    }

    private String getTime(){
        try{
            String section_id = datainp.readUTF();
            String test_id = datainp.readUTF();
            String query = "SELECT `Time` from `sections` where `Section_id`=" + section_id + " AND `Test_Id`=" +
                    test_id + ";";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            int t = rs.getInt("Time");
            return Integer.toString(t);
        }catch(IOException|SQLException e){
            e.printStackTrace();
            return "0";
        }
    }

    private Object getQuestions(){
        try{
            String section_id = this.datainp.readUTF();
            String test_id = this.datainp.readUTF();
            List<String[]> questions = new ArrayList<String[]>();
            String query = "SELECT * FROM `questions` WHERE `Section_Id` ='" + section_id + "' AND `Test_Id` ='" +
                    test_id + "';";
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                String s[] = new String[6];
                s[0] = rs.getString("Question");
                s[1] = rs.getString("A");
                s[2] = rs.getString("B");
                s[3] = rs.getString("C");
                s[4] = rs.getString("D");
                s[5] = rs.getString("Answer");
                questions.add(s);
            }
            return questions;
        }
        catch(IOException| SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private Object getSections(){
        try{
            String test_id = this.datainp.readUTF();
            List<String> sections = new ArrayList<>();
            String query = "SELECT `Section_Id` FROM `sections` where `Test_Id` ='" + test_id + "';";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                sections.add(rs.getString("Section_Id"));
            }
            return sections;
        }
        catch(SQLException | IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private boolean createSection(){
        try{

            String name = datainp.readUTF();
            String number_of_questions = datainp.readUTF();
            String time = datainp.readUTF();
            String test_id = datainp.readUTF();
            String section_id = datainp.readUTF();
            String query = "INSERT INTO `sections` (`Name`,`Test_Id`,`Section_Id`,`Number_of_Questions`,`Time`) VALUES ('"
                    + name + "','" + test_id + "','" + section_id + "','" + number_of_questions +
                    "','" + time + "');";
            Statement st =  connection.createStatement();
            st.execute(query);
            return true;


        }
        catch(IOException | SQLException e){

            e.printStackTrace();
            return false;
        }
    }

    private String getSubjectId(){
        try{
            String subject = datainp.readUTF();
            String teacher_id = datainp.readUTF();
            String query = "SELECT `Subject_Id` from `subjects` WHERE `Teacher_Id`='" + teacher_id + "' AND `Name`= '" + subject + "';";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            String s = "";
            while(rs.next()){
                s = s + rs.getString("Subject_Id");
                return s;
            }
            return s;
        }catch(SQLException|IOException e){
            e.printStackTrace();
            return null;
        }

    }

    private boolean createTest(){
        try{
            String name = datainp.readUTF();
            String test_id = datainp.readUTF();
            int sections = datainp.readInt();
            String subject_id = datainp.readUTF();
            String teacher_id = datainp.readUTF();
            String query = "INSERT INTO `tests` (`Name`,`Subject_Id`,`Test_Id`,`No_of_Sections`,`Teacher_Id`) VALUES ('"
                    + name + "','" + subject_id + "','" + test_id + "','" + sections + "','" + teacher_id +"');";
            Statement st = connection.createStatement();
            st.execute(query);
            return true;
        }
        catch(SQLException|IOException e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean StudentSignup(){
        try{
            String name = this.datainp.readUTF();
            String email = this.datainp.readUTF();
            String reg_no = this.datainp.readUTF();
            String pass = this.datainp.readUTF();
            String salt = this.datainp.readUTF();
            String query = "INSERT INTO `students` (`Name`,`Email`,`Registration_No`,`Password`,`Salt`) VALUES ('"+name+
                    "','"+email+"','"+reg_no+"','"+pass+"','"+salt+"')";
            Statement st = connection.createStatement();
            st.execute(query);
            return true;
        }catch(IOException | SQLException e){
            System.out.println(e);
        }
        return false;
    }

    private boolean TeacherSignup(){
        try{
            String name = this.datainp.readUTF();
            String email = this.datainp.readUTF();
            String id = this.datainp.readUTF();
            String pass = this.datainp.readUTF();
            String salt = this.datainp.readUTF();
            String query = "INSERT INTO `teachers` (`Name`,`Email`,`Id`,`Password`,`Salt`) VALUES ('"+name+
                    "','"+email+"','"+id+"','"+pass+"','"+salt+"')";
            Statement st = connection.createStatement();
            st.execute(query);
            return true;
        }catch(IOException | SQLException e){
            System.out.println(e);
        }
        return false;
    }

    private boolean TeacherSignin(){
        try{
            String Id = datainp.readUTF();
            String password = datainp.readUTF();
            String query = "SELECT `Password`,`Salt` From `teachers` WHERE `Id`="+Id+";";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            String Spassword = "";
            String Salt = "";
            while(rs.next()){
                Spassword = rs.getString("Password");
                Salt = rs.getString("Salt");
                if(check.verifyUserPassword(password,Spassword,Salt)){
                    return true;
                }
            }
            return false;
        }
        catch(IOException|SQLException| InvalidKeySpecException e){
            e.printStackTrace();
            return false;
        }
    }
    private boolean StudentSignin(){
        try{
            String reg_no = datainp.readUTF();
            String password = datainp.readUTF();
            String query = "SELECT `Password`,`Salt` From `students` WHERE `Registration_No`="+reg_no+";";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            String Spassword = "";
            String Salt = "";
            while(rs.next()){
                Spassword = rs.getString("Password");
                Salt = rs.getString("Salt");
                if(check.verifyUserPassword(password,Spassword,Salt)){
                    return true;
                }
            }
            return false;
        }
        catch(IOException|SQLException| InvalidKeySpecException e){
            e.printStackTrace();
            return false;
        }
    }

    private String getName(){
        try {

            String name = "";
            String type = datainp.readUTF();
            String table = "";
            String key = "";
            if (type.equalsIgnoreCase("Teacher")) {
                table = "teachers";
                key = "Id";
            } else {
                table = "students";
                key = "Registration_No";
            }
            System.out.println("Key is "+ key+"table is "+table);
            String query = "SELECT `Name` from "+table+" where "+key+"="+datainp.readUTF()+";";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                name = rs.getString("Name");

            }
            return name;

        }
        catch(SQLException|IOException e){
            e.printStackTrace();
            return null;
        }
    }
    private Object getSubjects(){
        try{
            List<String> subjects = new ArrayList<>();
            String id = datainp.readUTF();
            String query = "SELECT `Name` from `subjects` where `Teacher_Id`="+id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                subjects.add(rs.getString("Name"));
            }
            return subjects;

        }catch(SQLException|IOException e){
            e.printStackTrace();
            return null;
        }
    }
    private boolean addSubject(){
        try{
            String name = this.datainp.readUTF();
            String SessionId = this.datainp.readUTF();
            String SubjectId = this.datainp.readUTF();

            String query = "INSERT INTO `subjects`(`Name`,`Teacher_Id`,`Subject_Id`) VALUES ('"+name+"','"+SessionId+"','"
                    +SubjectId+"');";
            Statement st = connection.createStatement();
            st.executeUpdate(query);

            return true;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(true);
            return false;
        }
    }

    private  boolean addQuestion(){
        try{
            String section_Id = this.datainp.readUTF();
            String question = this.datainp.readUTF();
            String optionA = this.datainp.readUTF();
            String optionB = this.datainp.readUTF();
            String optionC = this.datainp.readUTF();
            String optionD = this.datainp.readUTF();
            String answer = this.datainp.readUTF();
            String question_No = this.datainp.readUTF();
            String test = this.datainp.readUTF();
            String query = "INSERT INTO `questions` (`Test_Id`,`Section_Id`,`Question_No`,`Question`,`A`,`B`,`C`,`D`,`Answer`) VALUES "+
                    "('"+test+"','"+section_Id+"','"+question_No+"','"+question+"','"+optionA+"','"+optionB+
                    "','"+optionC+"','"+optionD+"','"+answer+"');";
            Statement st = connection.createStatement();
            st.execute(query);
            return true;
        }catch(IOException|SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}

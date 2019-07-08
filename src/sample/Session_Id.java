package sample;

public class Session_Id {
    private static String user_id;
    private static String test_id;
    private static int no_of_questions;
    private static int no_of_sections;
    private static String section_id;
    private static int marks;
    private static String[] sections;

    public static String[] getSections(){
        return Session_Id.sections;
    }
    public static void setSections(String []sections){
        Session_Id.sections = sections;
    }
    public static int getMarks(){
        return Session_Id.marks;

    }
    public static void setMarks(int marks){
        Session_Id.marks = marks;
    }
    public static void setUsername(String username){
        Session_Id.user_id= username;
    }

    public static String getUsername(){
        return Session_Id.user_id;
    }
    public static String getTest_id(){
        return Session_Id.test_id;
    }
    public static void setTest_id(String test_id){
        Session_Id.test_id = test_id;
    }
    public static void setNo_of_questions(int no_of_questions){
        Session_Id.no_of_questions = no_of_questions;
    }
    public static int getNo_of_questions(){
        return Session_Id.no_of_questions;
    }
    public static int getNo_of_sections(){
        return Session_Id.no_of_sections;
    }
    public static void setNo_of_sections(int no_of_sections){
        Session_Id.no_of_sections = no_of_sections;
    }
    public static String getSection_id(){
        return Session_Id.section_id;
    }
    public static void setSection_id(String section_id){
        Session_Id.section_id = section_id;
    }
}

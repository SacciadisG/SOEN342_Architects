import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static int o = 0;
    static int p = 0;
    static Scanner s = new Scanner(System.in);
    static String menu = "\n Welcome to your task manager \n"
                         + "what would you like to do? \n"
                         + "Press 1 to create a task \n"
                         + "Press 2 to update an existing task \n"
                         + "Press 3 to create a project \n"
                         + "Press 4 to update an existing project \n"
                         + "Press 5 to link a task to a project \n"
                         + "Press 6 to exit \n";
    static String ty = "thank you!";
    static String desc = "would you like to provide a description?\n" +
                                  "press 1 for yes \n" +
                                  "press 2 for no \n";

    static String due = "would you like to provide a due date \n" +
                        "press 1 for yes \n" +
                        "press 2 for no \n";


    public static void main(String[] args) {
        String url = "jdbc:sqlite:my.db";

        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        boolean i = true;
        while(i){
            System.out.println(menu);
            o = Integer.parseInt((s.next()));
            if(o == 1){
                //create task
                System.out.println(desc);
                p = Integer.parseInt((s.next()));
            }
            else if(o == 2){
                //update task
            }
            else if (o == 3){
                //create project
            }
            else if (o ==3){
                //update project
            }
            else if (o == 4){
                //link task to project
            }
            else if (o == 5){
                System.out.println(ty);
                break;
                //exit
            }





        }

    }
}
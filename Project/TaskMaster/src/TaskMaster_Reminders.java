package Project.TaskMaster.src;

//Java util libraries
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Java io libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//Java time libraries
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//CSV Parser libraries
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class TaskMaster_Reminders implements Comparable<TaskMaster_Reminders> {
    private String name;
    private String description;
    private LocalDate Date; 
    private String date; 

    //A global TaskMaster_Reminders List object for storage purposes
    public static List<TaskMaster_Reminders> reminderHolder = new ArrayList<TaskMaster_Reminders>();

    /*LocalDate Constuctor*/
    public TaskMaster_Reminders(String name, String description, LocalDate date){
        this.name = name;
        this.description = description;
        this.Date = date;  
    }

    //String date Contuctor 
    public TaskMaster_Reminders(String name, String description, String date){
        this.name = name;
        this.description = description;
        this.date = date;
    }

    /* BEGIN CLASS METHODS */

    /* 
     * Searches for reminder from storage
     * @param name is the name of the reminder
     */
    public TaskMaster_Reminders getReminder(String name) {
        return null;
    }

    /*
     * Creates user filled reminder / stores and reloads reminders
     * @TODO FINISH
     */
    public static void CreateReminder(TaskMaster_Reminders inputtedReminder){
        if (reminderHolder.isEmpty()){
            System.out.println("EMPTY");
        }

        reminderHolder.add(inputtedReminder);
        System.out.println("DONE INPUTTING");
    }


    public static void DeleteReminder(){

    }

    /*
     * Deletes all reminders from storage / reloads
     */
   static public List<TaskMaster_Reminders> FlushReminders(){
        List<TaskMaster_Reminders> loaded_Rem = new ArrayList<TaskMaster_Reminders>(); 
        File file = new File("./reminder.csv") ;
        try {
            new FileOutputStream(file, false).close();
            return loaded_Rem; 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //reloads list 
    }

    /*
     * Sorts reminders by date
     * Uses Collection sort and CompareTo functions to sort reminders in-list by LocalDate value  
     */
    public static List<TaskMaster_Reminders> SortReminders(List<TaskMaster_Reminders> input){
        Collections.sort(input);
        return input; 
    }

    /*
     * User determines if they completed the task
     * TODO FINISH
     */
    public static void resolveReminders(){

    }

    /*
     * Grabs quote from quote file 
     * @TODO FINISH
     */
    public static String generateQuote(){
        return "ADD QUOTES";
    }

    /*
     * Calculates completion percentage to select quote category
     * @TODO FINISH
     */
    public static int determineQuote(){
        return 0;
    }

    /**
     * Stores loaded reminders list to save file 
     * @param loaded_Rem Loaded Reminder List 
     */
    static public void StoreReminders(List<TaskMaster_Reminders> loaded_Rem){
        //trys to create and write csv file 
        try{
            //temp variables for reminder buffer
            TaskMaster_Reminders temp ; 
            List<String[]> rem_data = new ArrayList<String[]>();

            //creates csv file 
            File file = new File("./reminder.csv") ;

            //creates csv writer 
            FileWriter outputfile;
            CSVWriter writer;
            
            if(file.exists()){
                outputfile = new FileWriter(file, true);
                writer = new CSVWriter(outputfile);
            }else{
                System.out.println("CREATING A NEW CSV FILE...");
                outputfile = new FileWriter(file);
                writer = new CSVWriter(outputfile);
                String[] headerData = {"Reminder Title", "Reminder Description", "Reminder Date"};
                writer.writeNext(headerData, false);;
            }
            
            
            //loads each reminder in list 
            for (int i = 0; i < loaded_Rem.size(); i++){

                //loads current reminder
                temp = loaded_Rem.get(i); 

                //converts reminder into string array and adds to reminder buffer
                String[] curr_remData = {temp.name, temp.description, temp.Date.toString()}; 
                rem_data.add(curr_remData);
            }
            //writes buffer to file in csv format 
            writer.writeAll(rem_data,false);

            //closes writer 
            writer.close();
            return;
            }
            //throws expection if cant write file 
            catch (IOException e) {e.printStackTrace();return;}
    }


    /**
     * Load reminders into app list from save file 
     * @return Reminder list loaded from save file 
     */
    static public List<TaskMaster_Reminders> LoadReminders(){
      
        //trys to read csv file 
        try{
            //temp variables for return reminder list  
            List<TaskMaster_Reminders> loaded_Rem = new ArrayList<TaskMaster_Reminders>(); 
            
            //loads file and creates CSV reader 
            File file = new File("./reminder.csv") ;
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);

            //string array to store 
            String[] curr_rem;

            //loads csv rows to string array until EOF 
            while ((curr_rem = csvReader.readNext()) != null) {

                //creates reminder from csv row
                TaskMaster_Reminders curr_load = new TaskMaster_Reminders(curr_rem[0], curr_rem[1], curr_rem[2]);
                
                //adds reminder to return list
                loaded_Rem.add(curr_load);
            }  

            //closes csv reader 
             csvReader.close();

             //returns reminder list 
             return loaded_Rem; 
        }  
        //Throws error if cant read file  
        catch (IOException e) {e.printStackTrace(); return null;}
    } 

    @Override
    public int compareTo(TaskMaster_Reminders o) {
        return this.Date.compareTo(o.Date);
    }

    //toString function using LocalDate
    public String toStringLocalDate(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Reminder: " + this.name + "\nDescription: " + this.description + "\tDue at: " + Date.format(format);
    }

    @Override
    public String toString(){
        return "Reminder: " + this.name + "\tDescription: " + this.description + "\tDue at: " + this.date;
    }

} //end class

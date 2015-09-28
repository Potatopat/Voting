import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;

public class ReadFile
{
    private Scanner fileScan, lineScan;
    private String fileName;
    private Scanner scan = new Scanner (System.in);
    private String line, lastName, firstName;
    private int categories = 10;
    private int textID, graduatingClass, studentID;
    private String[] votes = new String[30];
    public ReadFile(String textFileName)
    {
        fileName = textFileName;
    }
    
    public void checkID(int sID) throws IOException
    {
        fileScan = new Scanner (new FileReader(fileName));  
        studentID = sID;
        line = fileScan.nextLine();
        do
        {
            line = fileScan.nextLine();
            lineScan = new Scanner (line);
            lineScan.useDelimiter("\t");
            
            lastName = lineScan.next();
            firstName = lineScan.next();
            textID = Integer.parseInt(lineScan.next());
                
            String gc = lineScan.next();
            if (!gc.equals(""))
                graduatingClass = Integer.parseInt(gc);
            else
                graduatingClass = -1;
            
            //String done = lineScan.next();
        }while (textID != studentID && fileScan.hasNext());
    }
    
    public boolean Found()
    {
        return (textID == studentID);
    }
    
    public boolean Found(int sID)
    {
        return (textID == sID);
    }
    
    public String returnLastName()
    {
        return lastName;
    }
    
    public String returnFirstName()
    {
        return firstName;
    }
    
    public int returnID()
    {
        return textID;
    }
    
    public int returnGradClass()
    {
        return graduatingClass;
    }
    
    public String[] returnVotes()
    {
        return votes;
    }
}
import java.util.*;

public class VotingTerminal
{
    private static String readFileName = "Students.txt", writeFileName = "Results.txt";
    private static String lastName, firstName, gender;
    private static int ID, ethnicity, graduatingClass, rank, classSize, presidentVote;
    private static Scanner scan = new Scanner (System.in);
    private static ReadFile student = new ReadFile(readFileName);
    private static WriteFile studentWriter;
    private static ReadFile doubleVotes = new ReadFile(writeFileName);
    
    public static void main (String[] args)
    {
        do
        {
            System.out.print("\f");
            System.out.print("Please enter your ID number: ");
            ID = scan.nextInt();
            
            try
            {
                doubleVotes.checkID(ID);
            }
            catch (Exception e){
            }
            
            try
            {
                student.checkID(ID);
            }
            catch (Exception e){}
            
            if (student.Found() && !doubleVotes.Found())
            {
                getStudentData();
                voteForPresident();
                //studentWriter = new WriteFile(writeFileName, lastName, firstName, ID, gender, ethnicity, graduatingClass, rank, classSize, presidentVote);
                //studentWriter = new WriteFile(writeFileName, student);
                
            }
            else
            {
                System.out.println("Student does not exist");
            }
        }while (ID != -987654);
        
    }
    
    public static void getStudentData()
    {
        lastName = student.returnLastName();
        firstName = student.returnFirstName();
        graduatingClass = student.returnGradClass();
    }
    
    public static void voteForPresident()
    {
        do
        {
            System.out.println("\fHello " + firstName + " " + lastName);
            System.out.print("\nWho do you want to vote for President:\n\n1. Barack Obama\n2. Mitt Romney\n\nVote: ");
            presidentVote = scan.nextInt();
        }while (presidentVote < 1 && presidentVote > 2);
    }
}
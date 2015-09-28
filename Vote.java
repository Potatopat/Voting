import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Vote
{
    public static boolean running = true;
    public static int ID = 0, num = 0,                                                                                       a = -2468;
    public static String studentId = "";
    public static JOptionPane j = new JOptionPane();
    public static String readFileName = "Students.txt", writeFileName = "Results.txt";
    public static ReadFile student = new ReadFile(readFileName);
    public static ReadFile doubleVotes = new ReadFile(writeFileName);
    public static int SOMENUMBERHAHAHA = a;    
    public static String[] Questions = new String[4000];
    
    public static void main(String[] args)
    {
        enterId();
    }
    
    public static void enterId()
    {
        do
        {
            Questions[num] = j.showInputDialog("What is your ID number?");
            studentId = Questions[num];
            try{
                ID = Integer.parseInt(studentId);                
            }
            catch(NumberFormatException nFE){
                ID = 0;
            }
            if (ID == SOMENUMBERHAHAHA)
                System.exit(0);

            try
            {
                doubleVotes.checkID(ID);
            }
            catch (Exception e){ }

            try
            {
                student.checkID(ID);
            }
            catch (Exception e){ }
            //}
            
        }while(!student.Found() || doubleVotes.Found(ID) || studentId.equals("") || studentId == null);
        VotingGUI voting = new VotingGUI(student);
            
        voting.display();
            
        num++;
    }
}

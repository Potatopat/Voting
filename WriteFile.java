import java.io.*;
public class WriteFile
{
    BufferedWriter out;
    String read;
    int linenum = 3;
    
    public WriteFile(String textFileName, ReadFile student, String[] answers){
        try{
            out = new BufferedWriter(new FileWriter(textFileName, true));
            String phrase = "" + student.returnLastName() + "\t" + student.returnFirstName() + "\t" + student.returnID() + "\t" + student.returnGradClass();
            for (int x = 0; x < answers.length; x++)
            {
                if (answers[x] != null)
                {
                    phrase += "\t" + answers[x];
                }
            }
            
            out.write(phrase);
            out.newLine();
            out.close();
        }catch(IOException e){
            System.out.println("There was a problem:" + e);
        }
    }    
}

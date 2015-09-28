import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;

public class ReadQuestions
{
    private Scanner fileScan, lineScan;
    private String fileName;
    private String[] questions = new String[1000];
    
    public ReadQuestions(String textFileName)
    {
        fileName = textFileName;
    }
    
    public String[] returnQuestions() throws IOException
    {
        fileScan = new Scanner (new FileReader(fileName));
        int counter = 0;
        do
        {
            questions[counter] = fileScan.nextLine();
            counter++;
        }while (fileScan.hasNext());
        return questions;
    }
}
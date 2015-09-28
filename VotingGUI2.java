import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VotingGUI2 implements ActionListener
{
    private int width = 400, length = 800, j = 0, ID, numQuestions = 0;
    private JFrame frame;
    private JPanel panel;
    private boolean[] responded = new boolean[20];
    private JLabel[] question = new JLabel[20];
    private String[][] answerChoices = new String[20][10];
    private JLabel welcomeMessage;
    private JRadioButton[][] answerButton = new JRadioButton[20][10];
    private GridBagLayout pollLayout;
    private GridBagConstraints c = new GridBagConstraints();
    private ReadFile student;
    private ButtonGroup[] questionAnswers = new ButtonGroup[20];
    private Button submit;
    private String name, remainingQuestions = "", writeFileName = "Results.txt", questionsFileName = "questions.txt";
    private String[] answerString = new String[20];
    private String[] QandA = new String[1000];
    private ReadQuestions Q = new ReadQuestions(questionsFileName);
    
    public VotingGUI2(ReadFile s)
    {
        student = s;
        frame = new JFrame("Student Poll");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.CENTER;   
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        
        pollLayout = new GridBagLayout();
        
        welcomeMessage = new JLabel("Welcome " + student.returnFirstName() + " " + student.returnLastName());
        submit = new Button("Submit Answers");
        submit.addActionListener(this);
        
        createQuestionsAndAnswers();        
        
        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(pollLayout);
        
        for (int i = 0; i < numQuestions; i++)
        {
                c.gridx = 1;
                c.gridwidth = 2;
                
                panel.add(new JLabel(" "), c);
                c.gridy++;
                if (i == 0)
                {
                    panel.add(welcomeMessage ,c);
                    c.gridy++;
                    panel.add(new JLabel(" "), c);
                    c.gridy++;
                }
                c.gridy++;
                c.gridx = 1;
                c.gridwidth = 2;
                panel.add(question[i], c);
                c.gridy++;
                c.gridx = 0;
                c.gridwidth = 1;
                c.weightx = 0.5;
                for (int n = 0; n < 5; n++)
                {
                    c.gridx++;
                    if (c.gridx == 3)
                    {
                        c.gridx = 1;
                        c.gridy++;
                    }
                    if (answerButton[i][n] != null)
                    {
                        panel.add(answerButton[i][n], c);
                    }
                }
                
        }
        c.gridx = 1;
        c.gridy++;
        c.gridwidth = 2;
        panel.add(new JLabel(" "), c);
        c.gridy++;
        panel.add(submit, c);
        c.gridy++;
        panel.add(new JLabel(" "), c);
        c.gridy++;
        panel.add(new JLabel("Created by Cam Bodie and Kevin Christian"), c);
        
        frame.getContentPane().add(panel);
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == submit)
        {
            for (int n = 0; n < 6; n++)
            {
                for (int i = 0; i < 5; i++)
                {
                    if (answerButton[n][i] != null)
                    {
                        if (answerButton[n][i].isSelected())
                        {
                            responded[n] = true;
                        }
                    }
                }
                if (!responded[n])
                {
                    remainingQuestions += (n + 1) + ", ";
                }
            }
            
            if (remainingQuestions != "")
            {
                remainingQuestions = remainingQuestions.substring(0, remainingQuestions.length() - 2);
                JOptionPane.showMessageDialog(null, "Please respond to questions " + remainingQuestions + ".");
                remainingQuestions = "";
            }
            else
            {
                getAnswer();
                WriteFile studentWriter = new WriteFile(writeFileName, student, answerString);
                
                frame.setVisible(false);
                Vote.enterId();
            }
        }
    }
    
    public void createQuestionsAndAnswers()
    {
        try
        {
            QandA = Q.returnQuestions();
        }
        catch (Exception e){ }

        int questionNum = 0;
        int counter = 0;
        do{
            responded[questionNum] = false;
            question[questionNum] = new JLabel((questionNum + 1) + ". " + QandA[counter]);
            questionAnswers[questionNum] = new ButtonGroup();
            counter++;
            int newCounter = 0;
            do{
                answerChoices[questionNum][newCounter] = QandA[counter];
                answerButton[questionNum][newCounter] = new JRadioButton(answerChoices[questionNum][newCounter]);
                questionAnswers[questionNum].add(answerButton[questionNum][newCounter]);
                newCounter++;
                counter++;
            }while(!QandA[counter].equalsIgnoreCase("Done"));
            counter++;
            questionNum++;
        }while (QandA[counter] != null);
        numQuestions = questionNum - 1;
//         answerButton[0][0] = new JRadioButton(answerChoices[0][0]);
//         answerButton[0][1] = new JRadioButton(answerChoices[0][1]);
//         questionAnswers[0] = new ButtonGroup();
//         questionAnswers[0].add(answerButton[0][0]);
//         questionAnswers[0].add(answerButton[0][1]);
//         
//         responded[1] = false;
//         answerButton[1][0] = new JRadioButton(answerChoices[1][0]);
//         answerButton[1][1] = new JRadioButton(answerChoices[1][1]);
//         answerButton[1][2] = new JRadioButton(answerChoices[1][2]);
//         questionAnswers[1] = new ButtonGroup();
//         questionAnswers[1].add(answerButton[1][0]);
//         questionAnswers[1].add(answerButton[1][1]);
//         questionAnswers[1].add(answerButton[1][2]);
//         
//         responded[2] = false;
//         answerButton[2][0] = new JRadioButton(answerChoices[2][0]);
//         answerButton[2][1] = new JRadioButton(answerChoices[2][1]);
//         answerButton[2][2] = new JRadioButton(answerChoices[2][2]);
//         answerButton[2][3] = new JRadioButton(answerChoices[2][3]);
//         questionAnswers[2] = new ButtonGroup();
//         questionAnswers[2].add(answerButton[2][0]);
//         questionAnswers[2].add(answerButton[2][1]);
//         questionAnswers[2].add(answerButton[2][2]);
//         questionAnswers[2].add(answerButton[2][3]);
//         
//         responded[3] = false;
//         answerButton[3][0] = new JRadioButton(answerChoices[3][0]);
//         answerButton[3][1] = new JRadioButton(answerChoices[3][1]);
//         questionAnswers[3] = new ButtonGroup();
//         questionAnswers[3].add(answerButton[3][0]);
//         questionAnswers[3].add(answerButton[3][1]);
//         
//         responded[4] = false;
//         answerButton[4][0] = new JRadioButton(answerChoices[4][0]);
//         answerButton[4][1] = new JRadioButton(answerChoices[4][1]);
//         questionAnswers[4] = new ButtonGroup();
//         questionAnswers[4].add(answerButton[4][0]);
//         questionAnswers[4].add(answerButton[4][1]);
//         
//         responded[5] = false;
//         answerButton[5][0] = new JRadioButton(answerChoices[5][0]);
//         answerButton[5][1] = new JRadioButton(answerChoices[5][1]);
//         questionAnswers[5] = new ButtonGroup();
//         questionAnswers[5].add(answerButton[5][0]);
//         questionAnswers[5].add(answerButton[5][1]);
    }
    
    public void getAnswer()
    {
        for (int questionNumber=0; questionNumber < 6; questionNumber++)
        {
            for (int i = 0; i < 5; i++)
            {
                if (answerButton[questionNumber][i] != null)
                {
                    if (answerButton[questionNumber][i].isSelected())
                    {
                        answerString[questionNumber] = answerChoices[questionNumber][i];
                    }
                }
            }
        }
    }
    
    public void display()
    {
        frame.pack();
        frame.setVisible(true);
    }
}

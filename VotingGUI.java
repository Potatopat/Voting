import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VotingGUI implements ActionListener
{
    private int width = 400, length = 800, j = 0, ID;
    private JFrame frame;
    private JPanel panel;
    private boolean[] responded = new boolean[24];
    private JLabel[] question = new JLabel[2];
    private String[][] answerChoices = new String[2][];
    private JLabel welcomeMessage;
    private Checkbox[][] answerButton = new Checkbox[2][];
    private GridBagLayout pollLayout;
    private GridBagConstraints c = new GridBagConstraints();
    private ReadFile student;
    //private CheckboxGroup[] questionAnswers = new CheckboxGroup[5];
    private Button submit;
    private String name, remainingQuestions = "", writeFileName = "Results.txt";
    private String[] answerString = new String[24];
    private int answered = 0, x = 0, size = 0;
    
    public VotingGUI(ReadFile s)
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
        
        for (int i = 0; i < 2; i++)
        {
                c.gridx = 1;
                c.gridwidth = 2;
                
                panel.add(new JLabel(" "), c);
                c.gridy++;
                if (i == 0)
                {
                    panel.add(welcomeMessage ,c);
                    c.gridy++;
                    panel.add(new JLabel(student.returnGradClass() + "th Grade"), c);
                    c.gridy++;
                }
                c.gridy++;
                c.gridx = 1;
                c.gridwidth = 2;
                panel.add(new JLabel(" "), c);
                c.gridy++;
                panel.add(question[i], c);
                c.gridy++;
                c.gridx = 0;
                c.gridwidth = 1;
                c.weightx = 0.5;
                
                if (i == 0)
                {
                    x = 0;
                    size = 24;
                }
                else if (i == 1)
                {
                    if (student.returnGradClass() == 9)
                    {
                        x = 0;
                        size = 5;
                    }
                    else if (student.returnGradClass() == 10)
                    {
                        x = 5;
                        size = 5 + x;
                    }
                    else if (student.returnGradClass() == 11)
                    {
                        x = 10;
                        size = 5 + x;
                    }
                    else if (student.returnGradClass() == 12)
                    {
                        x = 15;
                        size = 5 + x;
                    }
                }
                for (int n = x; n < size; n++)
                {
                    c.gridx++;
                    if (c.gridx == 3)
                    {
                        c.gridx = 1;
                        c.gridy++;
                    }
                    
                    try
                    {
                        if (answerButton[i][n] != null)
                        {
                            panel.add(answerButton[i][n], c);
                        }
                    }
                    catch (Exception ArrayIndexOutOfBoundsException){}
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
        panel.add(new JLabel("Created by Pat Bartman"), c);
        
        frame.getContentPane().add(panel);
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        String error1 = "You must select 10 Spirit Days.";
        String error2 = "\nYou must select 1 Hashtag.";
        
        if (e.getSource() == submit)
        {
            for (int n = 0; n < 2; n++)
            {
                for (int i = 0; i < answerButton[n].length; i++)
                {
                    try
                    {
                        if (answerButton[n][i] != null)
                        {
                            if (answerButton[n][i].getState())
                            {
                                answered++;
                                if (answerButton[n].length == 24 && answered == 10)
                                {
                                    responded[n] = true;
                                    error1 = "";
                                }
                                else if (answerButton[n].length == 24 && answered >= 10)
                                {
                                    responded[n] = false;
                                    error1 = "You picked " + (answered - 10) + " too many Spirit Days.";
                                }
                                else if (answerButton[n].length == 24 && answered < 10)
                                {
                                    responded[n] = false;
                                    error1 = "You need " + (10 - answered) + " more Spirit Days.";
                                }
                                if (answerButton[n].length == 20 && answered == 1)
                                {
                                    error2 = "";
                                    responded[n] = true;
                                }
                                else if (answerButton[n].length == 20 && answered >= 1)
                                {
                                    responded[n] = false;
                                    error2 = "\nYou picked " + (answered - 1) + " too many Hashtags.";
                                }
                                else if (answerButton[n].length == 20 && answered < 1)
                                {
                                    responded[n] = false;
                                    error2 = "\nYou need " + (1 - answered) + " more Hashtags.";
                                }
                            }
                        }
                    }
                    catch (Exception ArrayIndexOutOfBoundsException){}
                }
                if (!responded[n])
                {
                    remainingQuestions += (n + 1) + ", ";
                }
                answered = 0;
            }
            
            if (remainingQuestions != "" && (!responded[0] || !responded[1]))
            {
                remainingQuestions = remainingQuestions.substring(0, remainingQuestions.length() - 2);
                JOptionPane.showMessageDialog(null, error1 + error2);
                error1 = "";
                error2 = "";
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
        question[0] = new JLabel("1. Which of the following would you like to see as a potential spirit day? (Choose 10)");
        question[1] = new JLabel("2. Which hashtag do you like the most for your class? (Choose 1)");
        
        frame.add(question[0]);
        
        answerChoices[0] = new String [24];
        
        answerChoices[0][0] = "Jorts Day";
        answerChoices[0][2] = "Disney Day";
        answerChoices[0][4] = "Superhero Day";
        answerChoices[0][6] = "White Out Day";
        answerChoices[0][8] = "Black Out Day";
        answerChoices[0][10] = "Crazy Hair Day";
        answerChoices[0][12] = "Celebrity Day";
        answerChoices[0][14] = "Geek/ Nerd Day";
        answerChoices[0][16] = "Camo/ Military Day";
        answerChoices[0][18] = "Childhood Hero Day";
        answerChoices[0][20] = "Throwback Day '50s";
        answerChoices[0][22] = "Throwback Day '60s";
        answerChoices[0][1] = "Throwback Day '80s";
        answerChoices[0][3] = "Cowboy/ Western Day";
        answerChoices[0][5] = "Mismatch/ Clash Day";
        answerChoices[0][7] = "Thrift Shop Thursday";
        answerChoices[0][9] = "Historical Figure Day";
        answerChoices[0][11] = "Pirate Day --- Aaargh";
        answerChoices[0][13] = "'Merica Day (Patriotic Wear)";
        answerChoices[0][15] = "Breast Cancer Awarness/ Pink Day";
        answerChoices[0][17] = "Teacher Student Clothes Swap Day";
        answerChoices[0][19] = "Crazy Spirit Day (Go Nuts on Gold)";
        answerChoices[0][21] = "Suit and Tie Day (Dress in Formal Attire)";
        answerChoices[0][23] = "Kate +8 Day (Find Multiple People to Dress Alike)";
        
        answerChoices[1] = new String[20];
        
        answerChoices[1][0] = "#2017";
        answerChoices[1][2] = "#borntofly";
        answerChoices[1][4] = "#jacobsswag";
        answerChoices[1][1] = "#keepitfresh";
        answerChoices[1][3] = "#HDJalltheway";
        
        answerChoices[1][5] = "#2k16";
        answerChoices[1][7] = "#sweetsixteen";
        answerChoices[1][9] = "#HDJalltheway";
        answerChoices[1][6] = "#makingh16tory";
        answerChoices[1][8] = "#JacobsForeverandAlways";
        
        answerChoices[1][10] = "#CrayJ";
        answerChoices[1][12] = "#leanmean2015";
        answerChoices[1][14] = "#DoItForHinkle";
        answerChoices[1][11] = "#Ibleedbrownngold";
        answerChoices[1][13] = "#livingthedream2015";
        
        answerChoices[1][15] = "#BestintheNest";
        answerChoices[1][17] = "#staygoldnbrown";
        answerChoices[1][19] = "#soarlikeaneagle";
        answerChoices[1][16] = "#goldenstateofmind";
        answerChoices[1][18] = "#classofonefourwatchussoar";
        
        
        
        answerButton[0] = new Checkbox[24];
        answerButton[1] = new Checkbox[20];
        
        for (int i = 0; i < answerButton[0].length; i++)
        {
            answerButton[0][i] = new Checkbox(answerChoices[0][i]);
        }
        
        for (int i = 0; i < answerButton[1].length; i++)
        {
            answerButton[1][i] = new Checkbox(answerChoices[1][i]);
        }
    }
    
    public void getAnswer()
    {
        int a = 0;
        for (int questionNumber=0; questionNumber < 2; questionNumber++)
        {
            for (int i = 0; i < answerButton[questionNumber].length; i++)
            {
                if (answerButton[questionNumber][i] != null)
                {
                    if (answerButton[questionNumber][i].getState())
                    {
                        answerString[a] = answerChoices[questionNumber][i];
                        a++;
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

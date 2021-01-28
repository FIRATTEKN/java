import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class studentExampanel extends JFrame{
    private JPanel mainPanel;
    private JRadioButton choiceD;
    private JRadioButton choiceA;
    private JRadioButton choiceB;
    private JRadioButton choiceC;
    private JButton sendButton;
    private JLabel Score;
    private JButton NEXTButton;
    private JTextArea question;
    private Integer questionNumber = 1;
    private String studentAnswer ;
    private Integer studentScore = 0;




    private void showQuestion(int number,String examCode) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root","1597534862gs.");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from "+examCode+" where questionNumber="+number);
        while (rs.next()){
            question.setText(rs.getString(2));
            choiceA.setText(rs.getString(3));
            choiceB.setText(rs.getString(4));
            choiceC.setText(rs.getString(5));
            choiceD.setText(rs.getString(6));

        }
    }

    private void checkAnswer(int number) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root","1597534862gs.");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from exams where questionNumber="+number);

        while (rs.next()){
            String corretChoice = rs.getString(7);
            if (choiceA.isSelected())
                studentAnswer = "A";
            else if (choiceB.isSelected())
                studentAnswer = "B";
            else if (choiceC.isSelected())
                studentAnswer = "C";
            else
                studentAnswer = "D";
            if (studentAnswer.equals(corretChoice))
                studentScore+=10;
        }

        Score.setText(String.valueOf(studentScore));
    }


    public studentExampanel(String studentNumber,String className,String examCode) throws SQLException {
        add(mainPanel);
        setSize(400,500);
        setVisible(true);
        setResizable(false);
        setLocation(620,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        showQuestion(1,examCode);


        getRootPane().setDefaultButton(sendButton);


        NEXTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkAnswer(questionNumber);
                    questionNumber ++ ;
                    showQuestion(questionNumber,examCode);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mySQLX mysqlx = null;
                try {
                    mysqlx = new mySQLX("1597534862gs.", "jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                int yesNO = JOptionPane.showConfirmDialog(null, "Click Yes for send Exam", "!!!", JOptionPane.YES_NO_OPTION);

                if (yesNO == 0)
                {
                    try {
                        mysqlx.updateRow( studentNumber, "grade",Score.getText(),className);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Your Exam Sent !");
                }
                else
                    JOptionPane.showMessageDialog(null,"Didn't Send !");
            }
        });


    }


}

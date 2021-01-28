import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class setExam extends JFrame {
    private JPanel mainPanel;
    private JTextField question;
    private JTextField ChoiceA;
    private JTextField ChoiceB;
    private JTextField ChoiceC;
    private JTextField ChoiceD;
    private JTextField questionNumber;
    private JTextField correctChoice;
    private JButton clearButton;
    private JButton saveButton;
    private JButton clearExamButton;
    private JButton goBackButton;
    private JTextField newexamField;
    private JButton createButton;
    private JTextField createdexamField;
    private JButton deleteExamButton;

    private void clearPage(){
        question.setText("");
        ChoiceA.setText("");
        ChoiceB.setText("");
        ChoiceC.setText("");
        ChoiceD.setText("");
        correctChoice.setText("");
        questionNumber.setText("");
    }

    setExam(String teacherName , String teacherDepartment) throws SQLException {
        add(mainPanel);
        setVisible(true);
        setSize(600,630);
        setResizable(false);
        setLocation(630,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        getRootPane().setDefaultButton(saveButton);

        mySQLX mysqlx = new mySQLX("1597534862gs.", "jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mysqlx.creteExam(newexamField.getText() );
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new teacherPanel(teacherName,teacherDepartment);
                    setVisible(false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String innerQuestion = question.getText();
                    String innerChoiceA = ChoiceA.getText();
                    String innerChoiceB = ChoiceB.getText();
                    String innerChoiceC = ChoiceC.getText();
                    String innerChoiceD = ChoiceD.getText();
                    String innercorrectChoice = correctChoice.getText();
                    String innerNumber = questionNumber.getText();
                    mysqlx.addQuestion(createdexamField.getText(),innerNumber, innerQuestion, innerChoiceA, innerChoiceB, innerChoiceC, innerChoiceD, innercorrectChoice);
                    clearPage();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Question number can not be null!");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPage();
            }
        });

        clearExamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mySQLX mysqlx = new mySQLX("1597534862gs.","jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");
                    mysqlx.clearTable("exams");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        deleteExamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String examCode = JOptionPane.showInputDialog("Write exam code ? ");
                int yesNO = JOptionPane.showConfirmDialog(null, "Are you sure ?", "!!!", JOptionPane.YES_NO_OPTION);

                if (yesNO == 0)
                {

                    try {
                        mysqlx.deleteTable(examCode);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Cleared !");
                }
                else
                    JOptionPane.showMessageDialog(null,"Didn't Delete !");


            }
        });


    }

}

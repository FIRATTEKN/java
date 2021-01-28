import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class teacherPanel extends JFrame{
    private JPanel teacherPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton addStudentButton;
    private JButton deleteStudent;
    private JButton updateStudentButton;
    private JButton deleteAllStudentsButton;
    private JTextPane chatBox;
    private JButton deleteChatBoxButton;
    private JLabel messageCounter;
    private JLabel j1;
    private JLabel ıdLabel;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JButton SETEXAMButton;
    private JLabel teacherName;
    private JLabel department;
    private JButton cretaClassButton;
    private JTextField showclassField;
    private JButton deleteClassButton;

    private void refreshData(String tableName) throws SQLException {
        mySQLX mysqlx = new mySQLX("1597534862gs.", "jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");
        try {
            textArea1.setText(mysqlx.refreshStudents(tableName)[0]);
            textArea2.setText(mysqlx.refreshStudents(tableName)[1]);
            textArea3.setText(mysqlx.refreshStudents(tableName)[2]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(null,"Undefined class!!");
        }
    }


    public teacherPanel(String teacherNAme, String teacherDepartment) throws SQLException {
        add(teacherPanel);
        setVisible(true);
        setSize(900,800);
        setLocation(500,200);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mySQLX mysqlx = new mySQLX("1597534862gs.", "jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");

        messageCounter.setText("You have "+String.valueOf(mysqlx.countMessage())+" message");


        teacherName.setText("Teacher = "+teacherNAme);
        department.setText("Department = "+teacherDepartment);

        showclassField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String className = showclassField.getText();
                try {
                    refreshData(stringFix.spaceFix(className));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        deleteClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String className = JOptionPane.showInputDialog(null,"Class Name ?","Delete Class",JOptionPane.QUESTION_MESSAGE);
                try {
                    mysqlx.deleteTable(stringFix.spaceFix(className));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Undefined class!!");
                }
            }
        });


        cretaClassButton.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                String className = JOptionPane.showInputDialog(null,"Class name ? ","New Class",JOptionPane.QUESTION_MESSAGE);
                try {
                    mysqlx.creteClass(stringFix.spaceFix(className));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        SETEXAMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new setExam(teacherNAme,teacherDepartment);
                    setVisible(false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                setVisible(false);
            }
        });

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String inID = JOptionPane.showInputDialog(null,"Write student ID","new student",JOptionPane.QUESTION_MESSAGE);
                String name = JOptionPane.showInputDialog(null,"write student name ","new student",JOptionPane.QUESTION_MESSAGE);
                String ingrade = JOptionPane.showInputDialog(null,"write average grade","new student",JOptionPane.QUESTION_MESSAGE);
                String className = JOptionPane.showInputDialog(null,"write class name","new student",JOptionPane.QUESTION_MESSAGE);
                int ID = Integer.parseInt(inID);
                Double grade = Double.parseDouble(ingrade);

                try {
                    mySQLX mysqlx = new mySQLX("1597534862gs.","jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");
                    mysqlx.addStudent(stringFix.spaceFix(className),ID,name,grade);
                    refreshData(stringFix.spaceFix(className));
                } catch (NumberFormatException | SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null,"something wrong !!!","warning",JOptionPane.WARNING_MESSAGE);
                }





            }
        });


        deleteStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mySQLX mysqlx = new mySQLX("1597534862gs.","jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");
                    String ID = JOptionPane.showInputDialog(null,"Write student's ID ","Remove Student",JOptionPane.QUESTION_MESSAGE);
                    String className = JOptionPane.showInputDialog(null,"Write student's class ","Remove Student",JOptionPane.QUESTION_MESSAGE);
                    mysqlx.removeStudent(className,ID);
                    refreshData(className);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        deleteAllStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mySQLX mysqlx = new mySQLX("1597534862gs.","jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");
                    String className = JOptionPane.showInputDialog(null,"class name ? ","delete all students !!",JOptionPane.WARNING_MESSAGE);
                    int yesNO = JOptionPane.showConfirmDialog(null, "Are you sure ?", "!!!", JOptionPane.YES_NO_OPTION);

                    if (yesNO == 0)
                    {

                        mysqlx.clearTable(stringFix.spaceFix(className));
                        JOptionPane.showMessageDialog(null, "Cleared !");
                        refreshData(stringFix.spaceFix(className));
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Didn't Clear !");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        updateStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mySQLX mysqlx = new mySQLX("1597534862gs.", "jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");
                    String ID = JOptionPane.showInputDialog(null, "Write student's ID", "Update", JOptionPane.QUESTION_MESSAGE);
                    String Property = JOptionPane.showInputDialog(null, "Write property which you want update", "Update", JOptionPane.QUESTION_MESSAGE);
                    String newValue = JOptionPane.showInputDialog(null, "Write new value", "Update", JOptionPane.QUESTION_MESSAGE);
                    String className = JOptionPane.showInputDialog(null, "Write class name", "Update", JOptionPane.QUESTION_MESSAGE);


                    int yesNO = JOptionPane.showConfirmDialog(null, "Click Yes for update.", "!!!", JOptionPane.YES_NO_OPTION);

                    if (yesNO == 0)
                    {
                        mysqlx.updateRow(ID, Property, newValue,className);
                        JOptionPane.showMessageDialog(null, "Updated !");
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Didn't Update !");

                    refreshData(className);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        String finalMessage = "" ;
        finalMessage += (mysqlx.refreshStudents("message_data")[2]+" => "+mysqlx.refreshStudents("message_data")[1]+"\n");
        textArea4.setText(finalMessage);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root","1597534862gs.");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM message_data");
        String Messagetext = "";
        while (rs.next()){
            String Message = rs.getString(2);
            String NAME = rs.getString(3);
            Messagetext += NAME +" => "+Message+"\n";

        }
        textArea4.setText(Messagetext);





        deleteChatBoxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mySQLX mysqlx = new mySQLX("1597534862gs.","jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC");
                    mysqlx.clearTable("message_data");
                    textArea4.setText("");
                    messageCounter.setText("You have "+String.valueOf(mysqlx.countMessage())+" message");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



            }
        });


    }
}

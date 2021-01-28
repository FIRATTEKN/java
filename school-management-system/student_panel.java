import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class student_panel extends JFrame{
    private JPanel studentPanel;
    private JLabel studentID;
    private JButton sendMessageToTeacherButton;
    private JLabel studentNAME;
    private JLabel studentGRADE;
    private JButton button1;
    private JTextPane textPane1;
    private JButton testYourSelfButton;

    public student_panel(String newID ,String newName , double newGrade , String className) throws SQLException {
        add(studentPanel);
        setUndecorated(true);
        setTitle("Save Box");
        setSize(400,500);
        setVisible(true);
        setResizable(false);
        setLocation(620,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(studentPanel, BorderLayout.CENTER);
        getRootPane().setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15,new Color(183,179,242)));

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root","1597534862gs.");

        studentID.setText(newID);
        studentNAME.setText(newName);
        studentGRADE.setText(String.valueOf(newGrade));

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        testYourSelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String examCode = JOptionPane.showInputDialog("exam code ?");
                    new studentExampanel(newID,className,examCode);
                    setVisible(false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                setVisible(false);
            }
        });


    sendMessageToTeacherButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ID = studentID.getText();
            String Message = textPane1.getText();
            String Name = studentNAME.getText();
            textPane1.setText("");

            try {
                mySQLX mysqlx = new mySQLX("1597534862gs.","jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root");
                mysqlx.addMessage(ID,Message,Name);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    });


    }
}

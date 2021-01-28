import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login_panel extends JFrame{
    private JPanel leftPanel;
    private JTextField teacher_name;
    private JPasswordField teacher_password;
    private JButton teacherButton;
    private JTextField studentName;
    private JPasswordField studentPassword;
    private JButton studentButton;
    private JPanel login;
    private JPanel rightPanel;
    private JButton getTeacherAccoutButton;
    private JTextField className;


    public login_panel(){
        add(login);
        setVisible(true);
        setSize(600,630);
        setResizable(false);
        setLocation(630,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        teacher_name.setBorder(BorderFactory.createMatteBorder(
                0, 0, 2, 0,  new Color(112,116,150)));
        studentName.setBorder(BorderFactory.createMatteBorder(
                0, 0, 2, 0,  new Color(112,116,150)));
        teacher_password.setBorder(BorderFactory.createMatteBorder(
                0, 0, 2, 0,  new Color(112,116,150)));
        studentPassword.setBorder(BorderFactory.createMatteBorder(
                0, 0, 2, 0,  new Color(112,116,150)));
        className.setBorder(BorderFactory.createMatteBorder(
                0, 0, 2, 0,  new Color(112,116,150)));

        teacherButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        getTeacherAccoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        studentButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        getTeacherAccoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new get_teacher_account();
                setVisible(false);
            }
        });





        teacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = teacher_name.getText();
                    String password = teacher_password.getText();
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root","1597534862gs.");
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT * FROM teachers_data WHERE teacherName='"+username+"'");

                    while (rs.next()){
                        if (password.equals(rs.getString("teacherPssword"))) {
                            new teacherPanel(rs.getString(1),rs.getString(3));
                            setVisible(false);
                        }
                        else
                            JOptionPane.showMessageDialog(null,"Something wrong!!! \nTry again","!!!",JOptionPane.WARNING_MESSAGE);

                    }
                    statement.close();
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root","1597534862gs.");
                    String username = studentName.getText();
                    String password = studentPassword.getText();
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT * FROM "+stringFix.spaceFix(className.getText())+" WHERE studentName='"+username+"'");
                    while (rs.next()){
                        if (password.equals(rs.getString(1))) {
                            new student_panel(rs.getString(1),rs.getString(2),rs.getDouble(3),stringFix.spaceFix(className.getText()));
                            setVisible(false);
                        }
                        else
                            JOptionPane.showMessageDialog(null,"Something wrong!!! \nTry again","!!!",JOptionPane.WARNING_MESSAGE);

                    }
                    statement.close();
                    connection.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



            }
        });
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new login_panel();
    }




}


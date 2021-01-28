import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class get_teacher_account extends JFrame {

    private JPanel mainPanel;
    private JTextField SUnickname;
    private JPasswordField SUpassword;
    private JPasswordField SUpasscode;
    private JCheckBox iVeReadAndCheckBox;
    private JButton save;
    private JTextField SUdepartment;
    private JLabel congText;
    private JButton openYourPanelButton;
    private JButton backButton;
    private JComboBox comboBox1;
    private JTextField textField1;

    public get_teacher_account(){
        add(mainPanel);
        setVisible(true);
        setSize(700,530);
        setResizable(false);
        setLocation(630,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        save.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openYourPanelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        //SUnickname.setBorder(BorderFactory.createLoweredBevelBorder());
        SUdepartment.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.blue));
        SUpasscode.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.blue));
        SUpassword.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.blue));
        SUnickname.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.blue));
        textField1.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.blue));
        comboBox1.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.blue));

        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));



        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new login_panel();
                setVisible(false);
            }
        });



        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mySQLX mysqlx = new mySQLX("1597534862gs.","jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root");
                    String innerSUnickname = SUnickname.getText();
                    String innerSUpassword = SUpassword.getText();
                    String innerSUpasscode = SUpasscode.getText();
                    String innerSUdepartment = SUdepartment.getText();
                    String defaultPasscode = "kodlava2019";
                    if (defaultPasscode.equals(innerSUpasscode)&&iVeReadAndCheckBox.isSelected()){
                        mysqlx.addTeacher(innerSUnickname,innerSUpassword,innerSUdepartment,innerSUpasscode);
                        congText.setText("<html>Congratulations you get<br>acoount you can start<br>add your students and<br>test them</html>");
                        openYourPanelButton.setEnabled(true);
                    }
                    else {
                        if (!iVeReadAndCheckBox.isSelected())
                            JOptionPane.showMessageDialog(null, "Read our policy and agree with it!", "Wrong PassCode", JOptionPane.WARNING_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(null, "Passcode does not match try again!", "Wrong PassCode", JOptionPane.WARNING_MESSAGE);
                        SUpasscode.setText("");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        openYourPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String innerSUnickname = SUnickname.getText();
                    String innerSUdepartment = SUdepartment.getText();
                    new teacherPanel(innerSUnickname,innerSUdepartment);
                    setVisible(false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}

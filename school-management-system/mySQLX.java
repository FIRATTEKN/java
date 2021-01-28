
import java.sql.*;

public class mySQLX {
    private String userName ;
    private String password ;
    private String url ;

    public mySQLX(){

    }
    public mySQLX(String password,String url,String userName) throws SQLException {
        this.setUserName(userName);
        this.setPassword(password);
        this.setUrl(url);
    }

    public mySQLX(String password,String url) throws SQLException {
        this.setPassword(password);
        this.setUrl(url);
        this.setUserName("root");
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void creteClass(String tableName) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root","1597534862gs.");
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE first_try_schema."+tableName+" (studentNo VARCHAR(15) NOT NULL ,studentName VARCHAR(45) NOT NULL,grade VARCHAR(45) NOT NULL,PRIMARY KEY (studentNo))");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close connection
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void creteExam(String examCode) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_try_schema?autoReconnect=true&useSSL=false&serverTimezone=UTC","root","1597534862gs.");
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE first_try_schema."+examCode+" (questionNumber VARCHAR(2) NOT NULL ,question VARCHAR(150) NOT NULL,choiceA VARCHAR(150) NOT NULL" +
                    ",choiceB VARCHAR(150) NOT NULL ,choiceC VARCHAR(150) NOT NULL ,choiceD VARCHAR(150) NOT NULL, trueChoice VARCHAR(1) NOT NULL , PRIMARY KEY (questionNumber))");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close connection
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteTable(String tableName) throws SQLException{
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE first_try_schema."+tableName);
    }



    public void addStudent(String tableName,int stundetNO, String studentName , double averageGrade) throws SQLException {

        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);


        String query = " insert into "+tableName+"(studentNO,studentName,grade)"
                + " values (?, ?, ?)";


        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt(1,stundetNO);
        preparedStmt.setString(2,studentName);
        preparedStmt.setDouble(3,averageGrade);
        preparedStmt.execute();
        connection.close();

    }


    public void addTeacher(String nickName , String Password , String Department ,String passCode) throws SQLException {

        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);

        String query = " insert into teachers_data(teacherName,teacherPssword,teacherDepartment)"
                + " values (?, ?,?)";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1,nickName);
        preparedStmt.setString(2,password);
        preparedStmt.setString(3,Department);
        preparedStmt.execute();
        connection.close();


    }

    public void addMessage(String ID , String Mesaage ,String Name) throws SQLException {
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);

        String query = " insert into message_data(Sender,Message,Sender_name)"
                + " values (?, ?,?)";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1,ID);
        preparedStmt.setString(2,Mesaage);
        preparedStmt.setString(3,Name);
        preparedStmt.execute();
        connection.close();


    }


    public void removeStudent(String tableName,String studentNO) throws SQLException {
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM "+tableName+" WHERE student_NO="+studentNO);
    }

    public void removeQuestiın(int queestionNumber) throws SQLException {
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM exams WHERE quesstionNumber="+queestionNumber);
    }


    public void clearTable(String data) throws SQLException {
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM "+data);
    }



    public void updateRow(String primaryKey , String property , String newValue , String dataName) throws SQLException{
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE "+dataName+" SET "+property+ "='"+newValue+"' WHERE studentNo ="+primaryKey);
    }

    /*                HATA NIN SEBEBİ NE !!!!!!!!!!
    public boolean checkData(String data,String givenName , String givenPassword) throws SQLException {
        Boolean check = true;
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM "+data+" WHERE teacherName='"+givenName+"'");

        while (rs.next()){
            if (givenPassword.equals(rs.getString("teacherPssword")))
                check = true;
            else
                check = false;
        }
        statement.close();
        connection.close();

        return check;

    }
    */



    public int countMessage() throws SQLException{
        int counter = 0 ;
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM message_data");
        while (rs.next())
            counter++;
        return counter;
    }

    public String[] refreshStudents(String data) throws SQLException {
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM "+data);
        String IDtext = "";
        String NAMEtext = "";
        String Gradetext = "";
        while (rs.next()){
            String ID = rs.getString(1);
            String NAME = rs.getString(2);
            String Grade = rs.getString(3);
            IDtext += ID +"\n";
            NAMEtext += NAME +"\n";
            Gradetext += Grade +"\n";

        }

        return new String[]{IDtext, NAMEtext, Gradetext};
    }

    public void addQuestion(String examCode,String number , String question, String choiceA,String choiceB,String choiceC,String choiceD,String trueChoice) throws SQLException {
        Connection connection = DriverManager.getConnection(this.url,this.userName,this.password);
        String query = " insert into "+examCode+"(questionNumber,question,choiceA,choiceB,choiceC,choiceD,trueChoice)"
                + " values (?,?,?,?,?,?,?)";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1,number);
        preparedStmt.setString(2,question);
        preparedStmt.setString(3,choiceA);
        preparedStmt.setString(4,choiceB);
        preparedStmt.setString(5,choiceC);
        preparedStmt.setString(6,choiceD);
        preparedStmt.setString(7,trueChoice);
        preparedStmt.execute();
        connection.close();
    }
}

import java.sql.*;

/**
 * Created by Alin on 2018/3/21.
 */
public class ManagerImpl implements Manager {
    public int checkInfo(String userName, String passward){
        String sql;
        Connection connection = null;
        int check = 0;
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            connection = getConnection();

            sql = "select * from User_Info where UserName = \""+ userName +"\"";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            user exist
            if(resultSet.next()) {
                if (!resultSet.wasNull()) {
                    String Passward = resultSet.getString("UserPassward");
                    if (passward.equals(Passward)) {
                        //                    pass
                        check = 1;
                    } else {
                        //                    Passward invalid
                        System.out.println("Passward Invalid.");
                        check = 2;
                    }
                } else {
                    //                userName invalid
                    System.out.println("User Name Invalid.");
                    check = 3;
                }
            }

            closeDBResource(connection, statement, resultSet);
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close(); // <-- This is important
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }

    public int userRegister(String userName, String passward){
        String sql;
        Connection connection = null;
        int check = 0;
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            connection = getConnection();

            sql = "select count(*) from User_info where UserName =\"" + userName + "\"";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            user existed
            if(resultSet.next()) {
                if (resultSet.getInt(1) != 0) {// 不等于0 - - -- - - - - - - - - - - - -- -

                    System.out.println("User Name Existed");
                } else {
                    //                register
                    sql = "insert into User_Info(UserName, UserPassward) values(\"" +
                            userName + "\",\"" + passward + "\")";
                    statement.executeUpdate(sql);
                    System.out.println("Register Successed.");
                    check = 1;
                }
            }
            closeDBResource(connection,statement,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    private Connection getConnection() throws Exception {
        Connection connection;
        String diverClass = "com.mysql.jdbc.Driver";
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PCC_lab?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","qazwsxedc");
        Class.forName(diverClass);
        return connection;
    }

    private void closeDBResource(Connection connection, Statement statement, ResultSet resultSet) throws Exception{
        if(resultSet!=null) {
            try{
                resultSet.close();
            }finally{
                resultSet=null;
            }
        }
        if(statement!=null) {
            try{
                statement.close();
            }finally{
                statement=null;
            }
        }
        if(connection!=null) {
            try{
                connection.close();
            }finally{
                connection=null;
            }
        }
    }
}

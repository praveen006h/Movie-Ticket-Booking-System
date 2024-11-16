import java.sql.*;

class test{
	public static void main(String[] args) throws SQLException{

                try{

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/db1";
                    Connection con = DriverManager.getConnection(url,"root", "");
                    System.out.println("Connected Successfully");
                    Statement s = con.createStatement();
                    String query = "select password from users where password = 'as'";
                    ResultSet rs = s.executeQuery(query);
                    if(rs.next()){
                        System.out.println(rs.getString(1));
                    }
                    else {System.out.println("Account Not found");}
                    
                }
                catch (ClassNotFoundException e){
                    System.out.println(e);
                }
                catch(SQLException ex){
                    System.out.println(ex);
                }
	}
}
package com.company.baza;

import java.sql.*;


public class FindLogin {




    public String findByLogin(String login, String pass) {
        Connection connection = ConnectionDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT name FROM UsersChat WHERE login = ? and password = ?");
            ps.setString(1, login);
            ps.setString(2, pass);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                String name = resultSet.getString(1);
                return name;
            }
            else {return null;}


        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        } finally {
            close(connection);
        }
    }

    private void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

//    public static void main(String[] args) {
//        FindLogin findLogin = new FindLogin();
//        System.out.println(findLogin.findByLogin("login1", "pass1"));
//    }

}

//    public boolean update(Users user) {
//        Connection connection = DBConnection.getConnection();
//        try {
//            connection.setAutoCommit(false);
//
//            PreparedStatement statement = connection.prepareStatement("UPDATE UsersChat SET name = ?, surname = ? WHERE id = ?");
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getSurname());
//            statement.setInt(3, user.getId());
////            UPDATE table_name
////            SET column1 = value1, column2 = value2, ...
////            WHERE condition;
//
//            boolean result = statement.execute();
//            connection.commit();
//            return result;
//
//        } catch (SQLException throwables) {
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            throw new RuntimeException(throwables);
//        } finally {
//            close(connection);
//        }
//    }
//


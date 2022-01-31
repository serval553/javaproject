package com.booking.booking4.service;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkWithTicket extends dbConnection {

    public boolean bookingTicket(String login, int idFilm) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM booking.sessions WHERE idFilm="+idFilm+";");
        int maxSeats = 0;
        while(resultSet.next()) {
            maxSeats = resultSet.getInt("maxSeats");
        }
        if (maxSeats > bookingUsers() && compareTicket(login,idFilm)){
            statement.executeUpdate("INSERT INTO booking.bookingticket(idusers,idFilm) VALUES ('"+login+"',"+idFilm+")");
            return true;
        }
        return false;
    }

    public int getIdFilm(String nameFilm, String date, String time) throws SQLException{
        System.out.println(nameFilm +" "+ date +" "+ time);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM booking.sessions WHERE nameFilm='"+nameFilm+"' " +
                "and date='"+date+"' and time='"+time+"';");
        int k = 0;
        k = resultSet.getInt("idFilm");
        System.out.println(k);

        return k;
    }

    public boolean deleteTicket(String login, int idFilm) throws SQLException {
        if (!compareTicket(login,idFilm)){
            statement.executeUpdate("DELETE FROM booking.bookingticket WHERE idusers='"+login+"' and idFilm="+idFilm+";");
            return true;
        }
        return false;
    }

    public List<String> allFilms() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT nameFilm FROM booking.sessions");
        List<String> all = new ArrayList<>();
        while(resultSet.next()){
            String temp = resultSet.getString("nameFilm");
            all.add(temp);
        }
        return all;
    }
    public List<String> allDate(String nameFilm) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT date FROM booking.sessions WHERE nameFilm='"+nameFilm+"';");
        List<String> allDate = new ArrayList<>();
        while(resultSet.next()){
            String temp = resultSet.getString("date");
            allDate.add(temp);
        }
        return allDate;
    }
    public List<String> allTime(String nameFilm,String date) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT time FROM booking.sessions WHERE nameFilm='"+nameFilm+"' and date='"+date+ "';");
        List<String> allTime = new ArrayList<>();
        while(resultSet.next()){
            String temp = resultSet.getString("time");
            allTime.add(temp);
        }
        return allTime;
    }

    public int bookingUsers() throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM booking.bookingticket");
        int count = 0;
        while(resultSet.next()){
            count += 1;
        }
        return count;
    }

    public boolean compareTicket (String login, int idFilm) throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM booking.bookingticket WHERE idusers='"+login+"' and idFilm="+idFilm+";");
        while(resultSet.next()){
            String loginDB = resultSet.getString("idusers");
            int idFilmDB = resultSet.getInt("idFilm");
            if (login.equals(loginDB) && idFilm == idFilmDB){
                return false;
            }
        }
        return true;
    }

    public void checkHall(int idFilm, int hallName) throws SQLException {
        String request = "SELECT * FROM bookingticket WHERE idFilm = "+idFilm+" and hallName="+hallName+";";
        ResultSet resultSet = statement.executeQuery(request);
        List<Integer> row = new ArrayList<>();
        List<Integer> col = new ArrayList<>();
        while (resultSet.next()){
            int row1 = resultSet.getInt("row");
            int col1 = resultSet.getInt("col");
            row.add(row1);
            col.add(col1);
        }
    }

    public void increaseBalance(String login, int sum) throws SQLException {
        statement.executeUpdate("UPDATE booking.users SET balance=balance+"+sum+" WHERE login='"+login+"';");
    }
    public void buyTicketBalance(String login, int idFilm) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM booking.sessions WHERE idFilm="+idFilm+";");
        while (resultSet.next()){
            int cost = resultSet.getInt("balance");
            statement.executeUpdate("UPDATE booking.users SET balance=balance-"+cost+" WHERE login='"+login+"';");
        }
    }

    public boolean checkBalance(String login, int idFilm) throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM booking.users WHERE login='"+login+"'");
        ResultSet resultSet1 = statement.executeQuery("SELECT * FROM booking.sessions WHERE idFilm="+idFilm+";");
        while(resultSet.next() && resultSet1.next()){
            int balance = resultSet.getInt("balance");
            int cost = resultSet1.getInt("cost");
            if (balance <= cost){
                return false;
            }
        }
        return true;
    }
}


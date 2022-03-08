package lesson2.server.authService;

import java.util.*;
import java.sql.*;

public class AuthServiceDB extends AuthServiceCommon {
    private static final String DB_CONTROL = "sqlite";
    private static final String DB_FILE = "chatty.db";

    private List<UserData> users;

    private static Connection connection;
    private static Statement st;

    public AuthServiceDB() {
        users = new ArrayList<>();
        try {
            connect();
            loadUsers();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void connect() throws Exception {
        Class.forName("org." + DB_CONTROL + ".JDBC");
        connection = DriverManager.getConnection("jdbc:" + DB_CONTROL + ":" + DB_FILE);
        st = connection.createStatement();
    }

    @Override public void close() {
        try {
            st.close();
            connection.close();
            users = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // считывание данных пользователей из таблицы БД
    private void loadUsers() {
        users.clear();
        String[] userData = new String[3];
        try (ResultSet rs = st.executeQuery("SELECT * FROM users;")) {
            while (rs.next()) {
                userData[0] = rs.getString("login");
                userData[1] = rs.getString("pwd");
                userData[2] = rs.getString("nickname");
                users.add(new UserData(userData));
            }
            rs.close();
            super.setUsers(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // изменить никнейм пользователя
    @Override public boolean updateData(String oldNick, String newNick) {
        super.updateData(oldNick, newNick);
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE users SET nickname = ? WHERE nickname = ?;")) {
            ps.setString(1, newNick);
            ps.setString(2, oldNick);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // добавить нового зарегистрированного пользователя
    @Override public boolean registered(String login, String password, String nickname) {
        if (super.registered(login, password, nickname)) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users (login, pwd, nickname) VALUES (?, ?, ?);")) {
                ps.setString(1, login);
                ps.setString(2, password);
                ps.setString(3, nickname);
                ps.executeUpdate();
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
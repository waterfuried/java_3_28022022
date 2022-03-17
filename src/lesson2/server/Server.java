package lesson2.server;

import lesson2.Prefs;
import lesson2.server.authService.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
	хранит список подключенных пользователей
	запускает потоки связи с ними (приема/отправки команд) - завершаются по выходу пользователя,
	сервер ими не управляет
*/
public class Server {
    private ServerSocket server;
    private Socket socket;

    private List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new CopyOnWriteArrayList<>();

        // если нет подключения к БД, запустить простой сервис авторизации
        authService = new AuthServiceDB();
        if (!authService.isServiceActive()) {
            authService.close();
            authService = new AuthServiceSimple();
        }

        try {
            server = new ServerSocket(Prefs.PORT);
            System.out.println("Запуск сервера произведен");

            while (true) {
                socket = server.accept();
                System.out.println("Соединение с новым клиентом установлено");
                new ClientHandler(this, socket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                server.close();
                authService.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendBroadcastMsg (ClientHandler sender, String msg){
        for (ClientHandler c : clients)
            c.sendMsg(String.format("[ %s ]: %s", sender.getNickname(), msg));
    }

    public void sendPrivateMsg (ClientHandler sender, String receiver, String msg) {
        String message = "[ личное сообщение %s %s ]: %s";
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(receiver)) {
                c.sendMsg(String.format(message, "от", sender.getNickname(), msg));
                if (!receiver.equals(sender.getNickname()))
                    sender.sendMsg(String.format(message, "для", receiver, msg));
                return;
            }
        }
        sender.sendMsg("Пользователя с ником \"" + receiver + "\" нет в чате");
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder(Prefs.getCommand(Prefs.COM_CLIENT_LIST));

        for (ClientHandler c : clients) {
            sb.append(" ").append(c.getNickname());
        }
        for (ClientHandler c : clients) {
            c.sendMsg(sb.toString());
        }
    }

    // проверить осуществление авторизиации пользователем с определенным логином
    public boolean isUserConnected (String login) {
        for (ClientHandler c : clients)
            if (c.getLogin().equals(login))
                return true;
        return false;
    }

    // проверить наличие регистрации пользователя с определенным ником
    public boolean userRegistered(String nickname){
        return authService.alreadyRegistered(nickname);
    }

    /*
        попытаться изменить ник - как в БД (при наличии связи с ней), так и в списке пользователей
        вернуть результат - удачно/нет

        если данные не были обновлены - например, ошибка записи в БД -
        вместо обновления списка пользователей об этом нужно сообщить,
        но эта функция оставлена за вызывающим методом
     */
    public boolean userDataUpdated(String oldNick, String newNick) {
        if (authService.updateData(oldNick, newNick)) {
            for (ClientHandler c : clients)
                if (c.getNickname().equals(oldNick)) c.setNickname(newNick);
            broadcastClientList();
            return true;
        } else
            return false;
    }

    public void subscribe (ClientHandler clientHandler){
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe (ClientHandler clientHandler){
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public static void main(String[] args) { new Server(); }
}
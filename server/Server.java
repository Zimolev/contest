package com.company.server;

import com.company.baza.FindLogin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    private List<ClientHendler> clients;
    private final FindLogin findLogin; /** объявляем метод поиска логина на сервере чтобы он был для всех user*/

    public Server() {

        findLogin = new FindLogin();
        clients = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(8865);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new ClientHendler(this, socket);
                }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FindLogin getFindLogin() {
        return findLogin;
    }

    public boolean isClientsOccupied(String nameFound){
        for (ClientHendler clientHendler : clients) {
            if (clientHendler.getName().equals(nameFound)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe(ClientHendler o) {
        clients.add(o);
    }

    public synchronized void unsubscribe(ClientHendler o) {
        clients.remove(o);
    }

    public synchronized void broadcastMsg(String message) {
        /**for (ClientHendler o : clients) {
            o.sendMessage(message);
        }
         */
        //поиск всех залогинненых с помощью for_each встроенного в коллецию с помощью Consumer
        clients.forEach(clientHendler -> clientHendler.sendMessage(message));
    }
}

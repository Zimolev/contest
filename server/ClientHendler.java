package com.company.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHendler {

    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    public String getName() {
        return name;
    }

    public ClientHendler(Server server, Socket socket) {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
           // this.name = "";
            this.server = server;

            new Thread(() -> {
                try {
                    doFindLogil();
                    readMassage();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection(socket);
                }
            }).start();

        } catch (IOException e) {
            throw new RuntimeException("Что то пошло не так", e);
        }
    }

    private void closeConnection(Socket socket){
        server.unsubscribe(this);
        server.broadcastMsg(name + " вышел из чата");

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void doFindLogil() throws IOException{
        while (true){
            String maybeCredenfinal = in.readUTF();
            if (maybeCredenfinal.startsWith("-auth")){
                String[] credentinals = maybeCredenfinal.split("\\s");
                String maybeUser = server.getFindLogin().findByLogin(credentinals[1], credentinals[2]);/** передаем от user логин и пароль*/
                if (maybeUser!=null){
                    if (!server.isClientsOccupied(maybeUser)) {
                        sendMessage("/authok " + maybeUser);
                        name = maybeUser;
                        //sendMessage("Вы зашли под именем: " + name);
                        server.broadcastMsg(name + " зашел в чат");
                        server.subscribe(this);

                        return;
                    }
                    else {
                        sendMessage("такой User уже есть");
                    }
                }
                else {
                    sendMessage("Неверные логин/пароль");
                }
            }
            else {
                sendMessage("Не правильная операция идентификации");
            }

        }
    }

    public void sendMessage (String outboxMessage){
        try {
            out.writeUTF(outboxMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMassage() throws IOException{
        while (true) {
            String inboxMessage = in.readUTF();
            System.out.println("от " + name + ": " + inboxMessage);
            if (inboxMessage.equals("-exit")) {
                break;
            }
             server.broadcastMsg(name + ": " + inboxMessage);
        }
    }
}

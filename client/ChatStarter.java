package com.company.client;

import com.company.client.comunication.ClientCommunication;
import com.company.client.gui.ChatFrame;
import com.company.logs.LogsFiles;
import java.util.function.Consumer;

public class ChatStarter {

    private final ChatFrame frame;
    private final ClientCommunication communication;
    private String nick = "";
    private final LogsFiles logsFiles;
    private int size = 0;

    public ChatStarter() {

        communication = new ClientCommunication();
        logsFiles = new LogsFiles();

        Consumer <String> outboxMessageConsumer = outboundMessage -> {
            communication.sendMessage(outboundMessage);
        };

        frame = new ChatFrame(outboxMessageConsumer);
           new Thread(()-> {
               while (true) {
                   String inboxMessage = communication.receiveMessage();
                   if (inboxMessage.startsWith("/authok")) {
                       nick = inboxMessage.split("\\s+")[1];
                       frame.getInboundMessageConsumer().accept("* Вы авторизованы как: " + nick + "\n");
                       frame.getInboundMessageConsumer().accept("Загрузка последних 100 записей: ");
                       logsFiles.setFile("", nick);
                       logsFiles.loadFile(logsFiles.getFile());
                       if (logsFiles.getFilelogs().size() < 100){
                           size = logsFiles.getFilelogs().size();
                       }
                       else {
                           size = 99;
                       }
                       for (int i = 0; i < logsFiles.getFilelogs().size(); i++) {
                           frame.getInboundMessageConsumer().accept(((String) logsFiles.getFilelogs().get(i)));
                       }

                   }
                   else {
                   frame.getInboundMessageConsumer().accept(inboxMessage);
                       logsFiles.setFile(inboxMessage, nick);}
               }
           }).start();
    }
}

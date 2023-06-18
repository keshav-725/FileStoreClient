package com.redhat;

import com.redhat.sendFiles.SendFile;

import java.io.*;
import java.net.Socket;

public class FileStoreClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8079;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to server");

            String command = String.join(" ", args);
            writer.println(command);

            if (command.toLowerCase().startsWith("add") || command.toLowerCase().startsWith("update")) {
                SendFile.sendFiles(args, writer, reader);
            }

            String response;
            while ((response = reader.readLine()) != null && !response.isEmpty()) {
                System.out.println(response);
            }

            System.out.println("Disconnected from server");

            reader.close();
            writer.close();
            consoleReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

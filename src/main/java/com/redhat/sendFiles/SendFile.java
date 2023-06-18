package com.redhat.sendFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SendFile {
    public static void sendFiles(String[] tokens, PrintWriter writer, BufferedReader reader) throws IOException {
        for (int i = 1; i < tokens.length; i++) {
            String fileName = tokens[i];
            File file = new File(fileName);
            if (!file.exists() || !file.isFile()) {
                System.out.println("File not found: " + fileName);
                continue;
            }

            try {
                String fileContent = getFileContent(file);
                String fileHash = getHash(fileContent);

                writer.println(fileHash);
                writer.flush(); // Ensure the hash is sent immediately

                String isExist = reader.readLine();

                if (!isExist.equalsIgnoreCase("EXIST")) {
                    try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = fileReader.readLine()) != null) {
                            writer.println(line);
                            writer.flush(); // Ensure each line of content is sent immediately
                        }
                    }

                    writer.println("END"); // Send the termination signal
                    writer.flush();

                    System.out.println("File sent: " + fileName);

                } else {
                    System.out.println("File already exists on the server with the same content: " + fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
                writer.println("Error sending file: " + fileName);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                writer.println("Error calculating file hash: " + fileName);
            }
        }
        System.out.println("All files processed from client side");
    }


    private static String getFileContent(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new String(bytes);
    }

    private static String getHash(String content) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(content.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}

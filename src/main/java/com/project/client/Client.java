package com.project.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String currentUsername = "";
        while (true) {
            System.out.println("Choose number:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            if (!currentUsername.equals("")) {
                System.out.println("3. Change mail");
                System.out.println("4. Change phone");
                System.out.println("5. Change log out");
            }
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            if (line.equals("1")) {
                System.out.println("Username:");
                String username = br.readLine();
                System.out.println("Password:");
                System.out.println("Generate password? [y/n]");
                String password;
                String passwordConfirm;
                line = br.readLine();
                if (line.equals("y")) {
                    password = new String(RandomPasswordGenerator.generatePassword());
                    passwordConfirm = password;
                    System.out.println("Your password: " + password);
                }
                else {
                    System.out.println("Password:");
                    password = br.readLine();
                    System.out.println("Confirm password:");
                    passwordConfirm = br.readLine();
                }
                System.out.println("Mail:");
                String mail = br.readLine();
                System.out.println("Phone:");
                String phone = br.readLine();
                URL url = new URL("http://localhost:8080/registration");
                String input = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\", \"passwordConfirm\":\"" + passwordConfirm + "\", \"mail\":\"" + mail + "\", \"phone\":\"" + phone + "\"}";
                String result = sendRequestPost(url, input);
                if (result.equals("successfully login")) {
                    currentUsername = username;
                }
                System.out.println(result);
            }
            else if (line.equals("2")) {
                System.out.println("Username:");
                String username = br.readLine();
                System.out.println("Password:");
                String password = br.readLine();
                URL url = new URL("http://localhost:8080/login");
                String input = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
                String result = sendRequestPost(url, input);
                System.out.println(result);
            }
            else if (!currentUsername.equals("")) {
                if (line.equals("3")) {
                    System.out.println("Mail:");
                    String mail = br.readLine();
                    URL url = new URL("http://localhost:8080/mail");
                    String input = "{\"username\":\"" + currentUsername + "\", \"mail\":\"" + mail + "\"}";

                    String result = sendRequestPost(url, input);


                }
                else if (line.equals("4")) {
                    System.out.println("Phone:");
                    String phone = br.readLine();
                    URL url = new URL("http://localhost:8080/phone");
                    String input = "{\"username\":\"" + currentUsername + "\", \"phone\":\"" + phone + "\"}";

                    String result = sendRequestPost(url, input);


                }
                else if (line.equals("5")) {
                    currentUsername = "";
                    System.out.println("successfully log out");
                }
                else {
                    System.out.println("Incorrect number");
                }
            }
            else {
                System.out.println("Incorrect number");
            }
        }
    }


    public static String sendRequestPost(URL url, String input) {
        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            StringBuilder result = new StringBuilder();
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                result.append(output);
            }

            conn.disconnect();
            return result.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

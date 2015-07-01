package com.noldorknight.waiterapp;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class SServer {

    private Socket socket;
    private final int SERVERPORT = 11000;
    private final String SERVER_IP = "192.168.2.222";
    public String answer;
    private String[] answerarr;
    private String[] message;


    public void setserver ()
    {
      try
         {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVERPORT);
            System.out.println(socket);
         }

      catch (Exception ex){System.out.println(ex);}
    }

    public String[] senddata(String[] cmd)
    {
        Gson gsn = new Gson();
        String text = gsn.toJson(cmd);
        System.out.println(text);
       try {
           PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
           out.println(text);
           out.flush();
           System.out.println("-------Начинаем прием данных");
           byte[] data = new byte[4];
           try {
               System.out.println("-------Пытаемся считать первый инпутстрим");
               InputStream inp = new BufferedInputStream(socket.getInputStream());
               inp.read(data);
               answer = new String(data, "UTF-8");
               System.out.println("---answer----Byte data= " + answer);
               int size = Integer.parseInt(answer);
               data = new byte[size];
               try {
                   inp.read(data);
                   answer = new String(data, "UTF-8");
                   System.out.println("---------------Answer is = " + answer);
                   message = gsn.fromJson(answer, String[].class);
                   System.out.println("-------String data= " + answer);
                   answerarr = new String[message.length];
                   System.arraycopy(message, 0, answerarr, 0, message.length);
                   socket.close();
                   } catch (Exception e) {
                   e.printStackTrace();
               }
               }
           catch(Exception ex){}

       }
     catch (UnknownHostException e){e.printStackTrace();}
     catch (IOException e){e.printStackTrace();}
     catch (Exception e){e.printStackTrace();}
     catch (Throwable throwable) {throwable.printStackTrace();}
       return answerarr;
    }
    }



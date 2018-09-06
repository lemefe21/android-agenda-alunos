package br.com.fleme.novaagendaalunos.services;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebClient {

    public String post(String json) {

        try {

            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            //vamos configurar o stream de saída para a requisição POST
            connection.setDoOutput(true);
            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);
            
            connection.connect();
            Scanner input = new Scanner(connection.getInputStream());

            String response = input.next();
            return response;

        } catch (MalformedURLException e) {
            Log.e("LOG_AGENDA", "WebClient - MalformedURLException - post");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("LOG_AGENDA", "WebClient - IOException - post");
            e.printStackTrace();
        }

        return null;
    }

}

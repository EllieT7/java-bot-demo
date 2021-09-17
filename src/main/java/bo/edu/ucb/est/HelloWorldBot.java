/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
/**
 *
 * @author ecampohermoso
 */
public class HelloWorldBot extends TelegramLongPollingBot {
    int a = 0, b=0, c=1;
    @Override
    public String getBotToken() {
        return "1907141560:AAHDm9XASezVYJvWHxZPOCMjmNQ9Vy46fPc";
    }

    @Override
    public void onUpdateReceived(Update update) {


        if(update.hasMessage()) { // Verificamos que tenga mensaje
            try{
                SendMessage message = new SendMessage();// Creo el objeto para enviar un mensaje
                message.setChatId(update.getMessage().getChatId().toString()); //Define a quien le vamos a enviar el mensaje
                System.out.println("Llego mensaje: " + update.toString());
                Message mensaje = update.getMessage();
                boolean flag = false;
                do{
                    flag = false;
                    switch (c){
                        case 1:
                            message.setText("Bienvenido al Bot Calculadora "+update.getMessage().getFrom().getFirstName()+
                                    "!\nSeleccione una de las siguientes opciones:" +
                                    "\n 1. Sumar dos numeros" +
                                    "\n 2. Calcular serie de fibonacci");
                            a = b = 0;
                            c++;
                            break;

                        case 2:
                            if(mensaje.getText().equals("1")){
                                message.setText("Ingresa el primer numero");
                                c++;
                                break;
                            }else if(mensaje.getText().equals("2")){
                                message.setText("Funcionalidad no implementada, intente otro día :)");
                                execute(message);
                                message.setText("😔");
                                execute(message);
                            }
                                flag = true;
                                c = 1;
                                break;

                        case 3:
                            try{
                                a = Integer.parseInt(update.getMessage().getText());
                                message.setText("Ingrese el segundo numero");
                            }catch(NumberFormatException e){
                                flag = true;
                                c = 1;
                                break;
                            }
                            c++;
                            break;
                        case 4:
                            try{
                                b = Integer.parseInt(update.getMessage().getText());
                                message.setText("La suma es "+(a+b)+" 🤠");
                                execute(message);
                                message.setText("🥳");
                                execute(message);
                            }catch(NumberFormatException e){
                                e.printStackTrace();
                            }
                            flag = true;
                            c = 1;
                            break;
                    }
                }while(flag);
                execute(message);

            }catch (TelegramApiException e){
                e.printStackTrace();
            }

        }

    }

    @Override
    public String getBotUsername() {
        return "prueba_naomi_bot";
    }
    
}

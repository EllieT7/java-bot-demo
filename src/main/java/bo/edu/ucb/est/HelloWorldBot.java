package bo.edu.ucb.est;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;

public class HelloWorldBot extends TelegramLongPollingBot {
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) { // Verificamos que tenga mensaje
            try{
                Message mensajeRecibido = update.getMessage(); // Objeto mensaje
                Long usuarioID = mensajeRecibido.getChatId(); //Se obtiene el id del usuario
                SendMessage message = new SendMessage();// Creo el objeto para enviar un mensaje
                System.out.println("Mensaje: "+mensajeRecibido.getText()+"\nLlego mensaje: " + update.toString());
                boolean flag;
                do{
                    Usuario usuario = obtenerUser(usuarioID); //Obtenemos un objeto tipo Usuario dentro de la list
                    message.setChatId(usuario.getIdUser().toString()); //Define a quien le vamos a enviar el mensaje
                    flag = false;
                    int c = usuario.getIteracion(); //Se obtiene la iteracion del objeto usuario
                    switch (c){
                        case 1: //Bienvenida y menu
                            message.setText("Bienvenido al Bot Calculadora "+mensajeRecibido.getFrom().getFirstName()+
                                    "!\nSeleccione una de las siguientes opciones:" +
                                    "\n 1. Sumar dos números" +
                                    "\n 2. Calcular serie de fibonacci");
                            updateVariable(usuarioID,0,0);
                            updateVariable(usuarioID,1,0);
                            break;
                        case 2: //Recibiendo el mensaje
                            if(mensajeRecibido.getText().equals("1")){
                                message.setText("Ingresa el primer número");
                                break;
                            }else if(mensajeRecibido.getText().equals("2")){
                                message.setText("Funcionalidad no implementada, intente otro día :)");
                                execute(message);
                                message.setText("😔");
                                execute(message);
                            }
                            flag = true; c = 0; break;
                        case 3: // Segunda variable para la suma
                            try{
                                double a = Double.parseDouble(mensajeRecibido.getText());
                                updateVariable(usuarioID,0, a);
                                message.setText("Ingrese el segundo número");
                            }catch(NumberFormatException e){
                                flag = true; c = 0; break;
                            }
                            break;
                        case 4: //resultado
                            try{
                                double b = Double.parseDouble(mensajeRecibido.getText());
                                updateVariable(usuarioID,1,b);
                                double[] var = usuario.getVariables();
                                message.setText("La suma es "+(var[0]+var[1])+" 🤠");
                                execute(message);
                                message.setText("🥳");
                                execute(message);
                            }catch(NumberFormatException e){
                                System.out.println("Error");
                            }
                            flag = true; c = 0; break;
                    }
                    c++;
                    updateIteracion(usuarioID, c);
                } while(flag);
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
    @Override
    public String getBotToken() {
        return "1907141560:AAHDm9XASezVYJvWHxZPOCMjmNQ9Vy46fPc";
    }
    public Usuario obtenerUser(Long usuarioID){
        for (Usuario datoUsuario: listaUsuarios) {
            if(datoUsuario.getIdUser().equals(usuarioID)){
                return datoUsuario;
            }
        }
        Usuario usuario = new Usuario(usuarioID);
        listaUsuarios.add(usuario);
        return usuario;
    }
    public void updateIteracion(Long user, int iteracion){
        for (Usuario datoUsuario: listaUsuarios) {
            if(datoUsuario.getIdUser().equals(user)){
                datoUsuario.setIteracion(iteracion);
            }
        }
    }
    public void updateVariable(Long user,int posicion, double valor){
        for (Usuario datoUsuario: listaUsuarios) {
            if(datoUsuario.getIdUser().equals(user)){
                double[] variables = datoUsuario.getVariables();
                variables[posicion] = valor;
                datoUsuario.setVariables(variables);
            }
        }
    }
}

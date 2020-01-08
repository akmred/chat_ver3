package client;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import javafx.fxml.Initializable;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
* Класс работает с историей чата
* ищет файл с историей и выдает его
* или создает файл и записывате в него историю
* */
public class HistoryChat implements Initializable {

     private String login;
     private LinkedHashSet<String> textStructure;
     private File file;


    public HistoryChat(String login) {
        this.login = login;

        // Ищем файл с историей в папке logs, если не находим, то
        // содаем такой файл
        String nameFile =  new String();
        nameFile = "history_" + login + ".txt";

        file = new File("logs/" + nameFile);

        // Прочитаем файл если такой файл есть
        textStructure = new LinkedHashSet<>();

        if (file.exists()) {
            readingFile();
        }

    }

    // Пробую при инициализации использовать опрееделение файла из этого метода
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (login.isEmpty())
            return;

    }

    /*
    * Функция читает текст из файла
    * */
    private void readingFile(){

        String text = new String();

        try(InputStreamReader fileIn = new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8);){

            int x;

            while ((x = fileIn.read()) != -1){
                System.out.println((char) x);
                text += (char) x;

                // Если встретился 769 символ, то запишем в массив
                if (x == 769) {
                    textStructure.add(text);
                    text = "";
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    * Добавить строку в коллекцию
    * */
    public void addString(String str){
        // В конце записи добавляется код, для разбивки строк сообщений
        int c = 769;
        str = str + (char)c;
        textStructure.add(str);
    }

    /*
    * Функция записи в файл
    *
    * */
    public void writeFile(){

        Iterator<String> iterator = textStructure.iterator();
        String str;

        try(OutputStreamWriter fileOut = new OutputStreamWriter(new FileOutputStream(file));){

            while (iterator.hasNext()){
                str = iterator.next();

                fileOut.write(str, 0, str.length());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*
    *   Возвращает текст из файла
    * */

    public String getText() {

       String text = new String();
       int size = textStructure.size();
       int MaxP = 100, index = 0; // максимальное кол-во сообщений

       Iterator<String> iteratorTextStructure = textStructure.iterator();

           while (iteratorTextStructure.hasNext()){

                if((size-index) < 100) {

                    String str = iteratorTextStructure.next();
                    text += str;
                }

               index ++;

            }

        return text;
    }
}

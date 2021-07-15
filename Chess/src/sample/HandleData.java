package sample;

import java.util.Dictionary;
import java.util.HashMap;
import java.io.*;

public class HandleData {

    public void saveData(String fileName, Dictionary<String, ChessPiece> dic) {
        try {
            FileOutputStream fileOut = new FileOutputStream("/data/"+fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(dic);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /data/"+fileName);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

}

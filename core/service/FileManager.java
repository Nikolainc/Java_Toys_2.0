package core.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import core.model.Toy;

public class FileManager<T extends Toy> implements IDataProvider<T> {

    @Override
    public String load(String fileName) {

        StringBuilder builder = new StringBuilder();

        try {

            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            try (BufferedReader reader = new BufferedReader(fr)) {
                
                String rawData = reader.readLine();

                while (rawData != null) {

                    builder.append(rawData);
                    builder.append("\n");
                    
                    rawData = reader.readLine();

                }

                reader.close();
                fr.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return builder.toString();

    }

    @Override
    public boolean save(String data, String fileName) {
        
        try (FileWriter writer = new FileWriter(fileName, false)) {

            writer.append(data);
            writer.flush();
            return true;

        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

        return false;

    }

    public boolean isEmpty(String fileName) {

        try {

            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            try (BufferedReader reader = new BufferedReader(fr)) {

                if (reader.readLine() == null) {

                    return true;
                    
                } else {

                    return false;

                }
                

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }
    
}

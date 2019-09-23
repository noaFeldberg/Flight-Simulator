package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainTrain {

	public static void main(String[] args) {
        try {;
            BufferedReader scriptReader=new BufferedReader(new FileReader("./resources/script.txt"));     
            String line;
            List<String> lines=new ArrayList<>();
            while((line=scriptReader.readLine())!=null){
                lines.add(line);
            }
            scriptReader.close();
            MyInterpreter.interpret(lines.toArray(new String[1]));
        } catch (FileNotFoundException e) {} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}

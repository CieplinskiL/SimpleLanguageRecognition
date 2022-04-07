import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class Vector {
    public String name;
    public ArrayList<Double> list;

    public Vector(String name, ArrayList<Double> list){
        this.name = name;
        this.list = new ArrayList<>(list);
        Double sum = 0.0;
        for (Double d: this.list)
            sum += d;

        for (int i = 0; i < this.list.size() ; i++) {
            this.list.set(i,this.list.get(i)/sum);
        }
        this.list.add(-1.0);
    }


    public static Vector createVector(Path path, HashMap<String, Double > alf, String[] letters) throws IOException {
        HashMap<String, Double> tmpMap = new HashMap<>(alf);
        ArrayList<Double> list = new ArrayList<>();
        Files.readAllLines(path).forEach(str -> {
            for (int i = 0; i < str.length() ; i++) {
                String tmps = String.valueOf(str.charAt(i)).toUpperCase();
                if(tmpMap.containsKey(tmps))
                    tmpMap.replace(tmps,tmpMap.get(tmps)+1);
            }
        });
        for (int i = 0; i < letters.length; i++) {
            list.add(tmpMap.get(letters[i]));
        }

        return new Vector(path.getName(1).toString(),list);
    }

    public static Vector createUserVector(String text, HashMap<String,Double>alf, String[] letters){
        HashMap<String,Double> tmpMap = new HashMap<>(alf);
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < text.length() ; i++) {
            String tmps = String.valueOf(text.charAt(i)).toUpperCase();
            if(tmpMap.containsKey(tmps))
                tmpMap.replace(tmps,tmpMap.get(tmps)+1);
        }
        for (int i = 0; i < letters.length; i++) {
            list.add(tmpMap.get(letters[i]));
        }

        return new Vector(" ",list);
    }

}

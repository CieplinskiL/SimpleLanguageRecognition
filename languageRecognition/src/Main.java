import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        Path path = Paths.get("Languages");
        String[] letters = "A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z".split(";");
        HashMap<String, Double > alf = new HashMap<>();
        List<Perceptron> perceptrons = new ArrayList<>();
        List<Vector> vectors = new ArrayList<>();
        try {
            Arrays.stream(letters).forEach(st -> alf.put(st,0.0) );
            Files.walkFileTree(path,new SimpleFileVisitor<>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(Files.isRegularFile(file))
                        vectors.add(Vector.createVector(file,alf,letters));
                    return super.visitFile(file, attrs);
                }
            });
            Files.list(path).forEach(f -> {
                if(Files.isDirectory(f))
                    perceptrons.add(new Perceptron(f.getFileName().toString(),vectors));
            });

            List<Vector> testList = new ArrayList<>();
            Files.walkFileTree(Paths.get("test"),new SimpleFileVisitor<>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(Files.isRegularFile(file))
                        testList.add(Vector.createVector(file,alf,letters));
                    return super.visitFile(file, attrs);
                }
            });

            int correct =0;

            Map<String, List<Double>> mac = new HashMap<>();
            for (Perceptron p: perceptrons) {
                mac.put(p.name,new ArrayList<>());
                int i =0;
                while (i<4){
                    mac.get(p.name).add(0.0);
                    i++;
                }
            }

            System.out.println("");
            for (Vector v: testList) {
                String res ="";
                List<String> tmp = new ArrayList<>();
                for (Perceptron p : perceptrons) {
                    if (p.countNet(v) == 1)
                        tmp.add(p.name);
                }
                if (!tmp.isEmpty()) {
                    res = tmp.get((int) (Math.random() * tmp.size()));

                    if (v.name.equals(res)) {
                        correct++;

                    }
                    //System.out.println("oczekiwany: " + v.name + " | otrzymany: " + res);
                } else {

                    double d = 0;
                    Perceptron fin = null;
                    for (Perceptron p: perceptrons) {
                        if(p.countFullNet(v)>d ||  d ==0){
                            d = p.countFullNet(v);
                            fin = p;
                        }
                    }
                    res = fin.name;
                    if(v.name.equals(fin.name ))
                        correct++;
                    //System.out.println("oczekiwany: " + v.name + " | otrzymany: " +fin.name);
                }

                for (Perceptron p: perceptrons) {
                    if(p.name.equals(v.name)){
                        if(p.name.equals(res)){
                            mac.get(p.name).set(0,mac.get(p.name).get(0)+1);
                        }else{
                            mac.get(p.name).set(1,mac.get(p.name).get(1)+1);
                        }
                    }else{
                        if(p.name.equals(res)){
                            mac.get(p.name).set(2,mac.get(p.name).get(2)+1);
                        }else {
                            mac.get(p.name).set(3,mac.get(p.name).get(3)+1);
                        }
                    }
                }

            }
            for (Map.Entry<String,List<Double>> me : mac.entrySet()) {

                List<Double> tmp = me.getValue();
                System.out.println(me.getKey());
                System.out.println("   p  n ");
                System.out.println("p ["+(int)(double)tmp.get(0)+"  "+(int)(double)tmp.get(1)+"]");
                System.out.println("n ["+(int)(double)tmp.get(2)+"  "+(int)(double)tmp.get(3)+"]");

                double p = tmp.get(0)/(tmp.get(0)+tmp.get(2));
                double r = tmp.get(0)/(tmp.get(0)+tmp.get(1));
                double f = 2*r*p/(r+p);
                double a = (tmp.get(0)+tmp.get(3))/(tmp.get(0)+tmp.get(1)+tmp.get(2)+tmp.get(3));
                System.out.println("Precision: "+p+"   Recall: "+r+"  F-measure: "+f+"   Accuracy: "+a);
                System.out.println("");

            }

            System.out.println(correct*100/testList.size()+"%");

            new GUI(perceptrons,alf,letters);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}

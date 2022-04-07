import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    private List<Vector> list;
    public String name;
    private List<Double> weights = new ArrayList<>();
    private boolean checker = false;


    public Perceptron(String name, List<Vector> list){
        this.name = name;
        this.list = new ArrayList<>(list);
        for (int i = 0; i < list.get(0).list.size() ; i++) {
            weights.add(Math.random()*5);
        }
        int count = 0;
        while(!checker && count < 1000 ) {
            checker = true;  // checker na true jesli będzie jakiś niepoprawny zmieni na false
            for (Vector w : this.list) {
                checkWeights(w);
            }
            count++;
        }
        //System.out.println(name+"  "+count);


    }

    //sparwadzanie poprawności rekurencyjnie
    private void checkWeights(Vector vector){
        if(vector.name.equals(name)){
            if(countNet(vector)==0){
                checker = false;
                changeWeights(vector,0,1);
                checkWeights(vector);
            }
        }else{
            if(countNet(vector)==1) {
                checker = false;
                changeWeights(vector, 1, 0);
                checkWeights(vector);
            }
        }
    }

    //zmienianie wag
    private void changeWeights(Vector vector, int answer, int expected){
        for (int i = 0; i < weights.size() ; i++) {
            weights.set(i, weights.get(i)+(expected - answer)*0.001*vector.list.get(i));

        }

    }

    public int countNet(Vector vector){
        double res = 0.0;
        for (int i = 0; i < vector.list.size(); i++) {
            res += weights.get(i)*vector.list.get(i);
        }
        if(res>= 0)
            return 1;
        return 0;
    }

    public double countFullNet(Vector vector){
        double res = 0;
        for (int i = 0; i < weights.size(); i++) {
            res += weights.get(i)*vector.list.get(i);
        }
        return res;
    }



}

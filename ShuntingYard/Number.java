package ShuntingYard;

import java.util.ArrayList;

public class Number implements Expression{

    private double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double calculate(ArrayList<String> args, int index) {
        return this.value;
    }
}

package ShuntingYard;

import java.util.ArrayList;

public class Div extends BinaryExpression{

    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double calculate(ArrayList<String> args, int index) {
        return left.calculate(args, index)/right.calculate(args, index);
    }
}

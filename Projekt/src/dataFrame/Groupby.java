package dataFrame;

import dataFrame.Applable;
import dataFrame.DataFrame;

public interface Groupby{

    DataFrame max();

    DataFrame min();

    DataFrame mean();

    DataFrame std();

    DataFrame sum();

    DataFrame var();

    DataFrame apply(Applable op);
}

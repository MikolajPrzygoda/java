import values.DoubleV;
import values.IntegerV;
import values.Value;

import java.util.*;

public class DataFrameGroup implements Groupby {

    private HashMap<List<Value>, DataFrame> map;
    private String[] groupedBy;
    private int frameWidth;
    private String[] columnNames;
    private Class[] columnTypes;
    private final boolean PROGRESS_OUTPUT = true;

    public DataFrameGroup(DataFrame df, String[] groupBy) {
        map = new HashMap<>();
        groupedBy = groupBy;
        frameWidth = df.width();
        columnNames = df.getColumnsNames();
        columnTypes = df.getColumnsTypes();

        for (int i = 0; i < df.size(); i++) {
            List<Value> key = new LinkedList<>();
            for (String s : groupBy)
                key.add(df.getColumn(s).getData(i));
            if (map.containsKey(key)) {
                map.get(key).add(df.getValuesRow(i));
            } else {
                map.put(key, df.getRow(i));
            }

            if (PROGRESS_OUTPUT) {
                if (i % 50000 == 0) {
                    System.out.print(String.format("\rGrouping: %.2f%%...", i / (double) df.size() * 100));
                }
            }
        }
        if (PROGRESS_OUTPUT) {
            System.out.print("\r                          \r"); //clear current line
            System.out.println("Grouping Done.");
        }
    }

    @Override
    public DataFrame max() {
        DataFrame result = new DataFrame(columnNames, columnTypes);

        for (var entry : map.entrySet()) {
            Value[] resultRow = new Value[frameWidth];
            Column[] columns = entry.getValue().getColumns();
            for (int i = 0; i < columns.length; i++) {
                //Check if this column isn't part of the map key, if so, set resultRow accordingly.
                boolean found = false;
                for (int j = 0; j < groupedBy.length; j++) {
                    if (columns[i].getName().equals(groupedBy[j])) {
                        resultRow[i] = entry.getKey().get(j);
                        found = true;
                    }
                }
                if (found)
                    continue;

                //Find max value
                for (int j = 0; j < columns[i].size(); j++) {
                    if (resultRow[i] == null || columns[i].getData(j).gte(resultRow[i]))
                        resultRow[i] = columns[i].getData(j);
                }
            }
            result.add(resultRow);
        }

        return result;
    }

    @Override
    public DataFrame min() {
        DataFrame result = new DataFrame(columnNames, columnTypes);

        for (var entry : map.entrySet()) {
            Value[] resultRow = new Value[frameWidth];
            Column[] columns = entry.getValue().getColumns();
            for (int i = 0; i < columns.length; i++) {
                //Check if this column isn't part of the map key, if so, set resultRow accordingly.
                boolean found = false;
                for (int j = 0; j < groupedBy.length; j++) {
                    if (columns[i].getName().equals(groupedBy[j])) {
                        resultRow[i] = entry.getKey().get(j);
                        found = true;
                    }
                }
                if (found)
                    continue;

                //Find min value
                for (int j = 0; j < columns[i].size(); j++) {
                    if (resultRow[i] == null || columns[i].getData(j).lte(resultRow[i]))
                        resultRow[i] = columns[i].getData(j);
                }
            }
            result.add(resultRow);
        }

        return result;
    }

    @Override
    public DataFrame mean() {
        DataFrame result = new DataFrame(columnNames, columnTypes);

        for (var entry : map.entrySet()) {
            Value[] resultRow = new Value[frameWidth];
            Column[] columns = entry.getValue().getColumns();
            for (int i = 0; i < columns.length; i++) {
                //Check if this column isn't part of the map key, if so, set resultRow accordingly.
                boolean found = false;
                for (int j = 0; j < groupedBy.length; j++) {
                    if (columns[i].getName().equals(groupedBy[j])) {
                        resultRow[i] = entry.getKey().get(j);
                        found = true;
                    }
                }
                if (found)
                    continue;

                //Find mean value if it wasn't column we grouped by.
                Value sum = columns[i].getData(0).clone();
                for (int j = 1; j < columns[i].size(); j++) {
                    sum = sum.add(columns[i].getData(j));
                }
                resultRow[i] = sum.div(new IntegerV(columns[i].size()));
                System.out.println(sum.toString());
            }
            result.add(resultRow);
        }

        return result;
    }

    @Override
    public DataFrame std() {
        DataFrame result = new DataFrame(columnNames, columnTypes);

        for (var entry : map.entrySet()) {
            Value[] resultRow = new Value[frameWidth];
            Column[] columns = entry.getValue().getColumns();
            for (int i = 0; i < columns.length; i++) {
                //Check if this column isn't part of the map key, if so, set resultRow accordingly.
                boolean found = false;
                for (int j = 0; j < groupedBy.length; j++) {
                    if (columns[i].getName().equals(groupedBy[j])) {
                        resultRow[i] = entry.getKey().get(j);
                        found = true;
                    }
                }
                if (found)
                    continue;

                //Find std value if it wasn't column we grouped by.
                Value sum = columns[i].getData(0).clone();
                for (int j = 1; j < columns[i].size(); j++) {
                    sum = sum.add(columns[i].getData(j));
                }
                Value mean = sum.div(new IntegerV(columns[i].size()));
                sum = sum.sub(sum); // reset sum to 0
                for (int j = 1; j < columns[i].size(); j++) {
                    sum = sum.add(
                            columns[i].getData(j).sub(mean).pow(new IntegerV(2))
                    );
                }
                resultRow[i] = sum.div(new IntegerV(columns[i].size() - 1)).pow(new DoubleV(0.5));
            }
            result.add(resultRow);
        }

        return result;
    }

    @Override
    public DataFrame sum() {
        DataFrame result = new DataFrame(columnNames, columnTypes);

        for (var entry : map.entrySet()) {
            Value[] resultRow = new Value[frameWidth];
            Column[] columns = entry.getValue().getColumns();
            for (int i = 0; i < columns.length; i++) {
                //Check if this column isn't part of the map key, if so, set resultRow accordingly.
                boolean found = false;
                for (int j = 0; j < groupedBy.length; j++) {
                    if (columns[i].getName().equals(groupedBy[j])) {
                        resultRow[i] = entry.getKey().get(j);
                        found = true;
                    }
                }
                if (found)
                    continue;

                //Find sum of the column if it wasn't column we grouped by.
                Value sum = columns[i].getData(0).clone();
                for (int j = 1; j < columns[i].size(); j++) {
                    sum = sum.add(columns[i].getData(j));
                }
                resultRow[i] = sum;
            }
            result.add(resultRow);
        }

        return result;
    }

    @Override
    public DataFrame var() {
        DataFrame result = new DataFrame(columnNames, columnTypes);

        for (var entry : map.entrySet()) {
            Value[] resultRow = new Value[frameWidth];
            Column[] columns = entry.getValue().getColumns();
            for (int i = 0; i < columns.length; i++) {
                //Check if this column isn't part of the map key, if so, set resultRow accordingly.
                boolean found = false;
                for (int j = 0; j < groupedBy.length; j++) {
                    if (columns[i].getName().equals(groupedBy[j])) {
                        resultRow[i] = entry.getKey().get(j);
                        found = true;
                    }
                }
                if (found)
                    continue;

                //Find std value if it wasn't column we grouped by.
                Value sum = columns[i].getData(0).clone();
                for (int j = 1; j < columns[i].size(); j++) {
                    sum = sum.add(columns[i].getData(j));
                }
                Value mean = sum.div(new IntegerV(columns[i].size()));
                sum = sum.sub(sum); // reset sum to 0
                for (int j = 1; j < columns[i].size(); j++) {
                    sum = sum.add(
                            columns[i].getData(j).sub(mean).pow(new IntegerV(2))
                    );
                }
                resultRow[i] = sum.div(new IntegerV(columns[i].size() - 1));
            }
            result.add(resultRow);
        }

        return result;
    }

    @Override
    public DataFrame apply(Applable op) {
        DataFrame result = new DataFrame(columnNames, columnTypes);
        for (var entry : map.entrySet())
            result.merge(op.apply(entry.getValue()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<List<Value>, DataFrame> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append('\n');
            sb.append(entry.getValue());
            sb.append('\n');
            sb.append('\n');
        }
        return sb.toString();
    }
}

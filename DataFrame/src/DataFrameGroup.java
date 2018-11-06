import java.util.HashMap;
import java.util.Map;

public class DataFrameGroup implements Groupby {

    private HashMap<String, DataFrame> map;
    private String groupedBy;

    public DataFrameGroup(DataFrame df, String[] groupBy) {
        map = new HashMap<>();
        groupedBy = groupBy[0];

        for (int i = 0; i < df.size(); i++) {
            String key = df.getColumn(groupedBy, false).getData(i).toString();
            if (map.containsKey(key)) {
                map.get(key).add(df.getValuesRow(i));
            } else {
                map.put(key, df.getRow(i));
            }
        }
    }

    @Override
    public DataFrame max() {
        return null;
    }

    @Override
    public DataFrame min() {
        return null;
    }

    @Override
    public DataFrame mean() {
        return null;
    }

    @Override
    public DataFrame std() {
        return null;
    }

    @Override
    public DataFrame sum() {
        return null;
    }

    @Override
    public DataFrame var() {
        return null;
    }

    @Override
    public DataFrame apply(Applyable op) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, DataFrame> entry : map.entrySet()) {
            sb.append(entry.getValue());
            sb.append('\n');
        }
        return sb.toString();
    }
}

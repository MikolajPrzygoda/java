import java.util.HashMap;
import java.util.Map;

//TODO: Stores C00Value objects in data ArrayList from Column as pairs of exception to the default value stored in this column.
public class SparseColumn extends Column {
    private String hidden;

    public SparseColumn(String name, String typeName, String hidden) {
        super(name, typeName);
        this.hidden = hidden;
    }

    /**
     * Deep copy constructor.
     * @param column Column to copy everything from.
     */
    public SparseColumn(SparseColumn column) {
        super(column);
        this.hidden = column.hidden;
    }
}

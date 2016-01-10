package brightseer.com.brewhaha.objects;

/**
 * Created by Michael McCulloch on 3/3/2015.
 */
public class KeyValuepair {
    private int Key;
    private String Value;

    public KeyValuepair() {
    }

    public KeyValuepair(String Value, int Key) {
        this.Value = Value;
        this.Key = Key;
    }

    public int getKey() {
        return Key;
    }

    public void setKey(int key) {
        this.Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        this.Value = value;
    }
}

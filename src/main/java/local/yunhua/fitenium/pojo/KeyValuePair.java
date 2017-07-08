package local.yunhua.fitenium.pojo;

/**
 * Created by yuyunhua on 2015-11-05.
 */
public class KeyValuePair {

    private String key;
    private Object value;
    private String note;

    public KeyValuePair() {

    }

    public KeyValuePair(String key, Object value) {
        setKey(key);
        setValue(value);
    }

    public KeyValuePair(String key, Object value, String note) {
        setKey(key);
        setValue(value);
        setNote(note);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}

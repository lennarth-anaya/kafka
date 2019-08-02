package org.lrth.kafkaavro.model.json;

public class SomeRecord {

    private String strAtt;
    private int intAtt;
    private NestedRecord nested;

    public String getStrAtt() {
        return strAtt;
    }

    public void setStrAtt(String strAtt) {
        this.strAtt = strAtt;
    }

    public int getIntAtt() {
        return intAtt;
    }

    public void setIntAtt(int intAtt) {
        this.intAtt = intAtt;
    }

    public NestedRecord getNested() {
        return nested;
    }

    public void setNested(NestedRecord nested) {
        this.nested = nested;
    }

    @Override
    public String toString() {
        return "ExampleRecord{" +
                "address='" + strAtt + '\'' +
                ", yearBuilt=" + intAtt +
                ", metadata=" + nested +
                '}';
    }
}

package org.lrth.kafkaavro.model.json;

import java.util.List;

public class NestedRecord {
    private String strAtt2;
    private List<String> arrAtt;
    
    public String getStrAtt2() {
        return strAtt2;
    }
    public void setStrAtt2(String strAtt2) {
        this.strAtt2 = strAtt2;
    }
    public List<String> getArrAtt() {
        return arrAtt;
    }
    public void setArrAtt(List<String> arrAtt) {
        this.arrAtt = arrAtt;
    }
}

package cn.edu.nuc.cushion.bean;

import java.util.List;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class HardInfo {

    /**
     * nwkAddr : 0000
     * parAddr : FFFF
     * macAddr : 5DA00904004B1200
     * funcList : [{"typeCode":241,"type":"协调器","id":0,"cycle":0,"data":null}]
     */

    private String nwkAddr;
    private String parAddr;
    private String macAddr;
    private List<FuncListBean> funcList;

    public FuncListBean getInfo() {
        return getFuncList().get(0);
    }

    public String getNwkAddr() {
        return nwkAddr;
    }

    public void setNwkAddr(String nwkAddr) {
        this.nwkAddr = nwkAddr;
    }

    public String getParAddr() {
        return parAddr;
    }

    public void setParAddr(String parAddr) {
        this.parAddr = parAddr;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public List<FuncListBean> getFuncList() {
        return funcList;
    }

    public void setFuncList(List<FuncListBean> funcList) {
        this.funcList = funcList;
    }

    public static class TemSecure {
        private double temperature;
        private String isSit;

        public TemSecure(double temperature, String isSit) {
            this.temperature = temperature;
            this.isSit = isSit;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public String isSit() {
            return isSit;
        }

        public void setSit(String sit) {
            this.isSit = sit;
        }

        @Override
        public String toString() {
            return "TemSecure{" +
                    "temperature=" + temperature +
                    ", isSit=" + isSit +
                    '}';
        }
    }


    public static class FuncListBean {
        /**
         * typeCode : 241
         * type : 协调器
         * id : 0
         * cycle : 0
         * data : null
         */

        private int typeCode;
        private String type;
        private int id;
        private int cycle;
        private Object data;

        public int getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(int typeCode) {
            this.typeCode = typeCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCycle() {
            return cycle;
        }

        public void setCycle(int cycle) {
            this.cycle = cycle;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}

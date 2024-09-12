package com.Ipaisa.axis;

import java.util.List;
import java.util.List;
import java.util.StringJoiner;

public class BillFetchRequest {
    private int chId = 1;
    private boolean isRealTimeFetch;
    private CustDetails custDetails;
    private AgentDetails agentDetails;
    private BillDetails billDetails;

    // Getters and Setters

    public BillFetchRequest() {
	this.isRealTimeFetch=true;	
    }
    public int getChId() {
        return chId;
    }

    public void setChId(int chId) {
        this.chId = chId;
    }

    public boolean isRealTimeFetch() {
        return isRealTimeFetch;
    }

    public void setRealTimeFetch(boolean realTimeFetch) {
        isRealTimeFetch = realTimeFetch;
    }

    public CustDetails getCustDetails() {
        return custDetails;
    }

    public void setCustDetails(CustDetails custDetails) {
        this.custDetails = custDetails;
    }

    public AgentDetails getAgentDetails() {
        return agentDetails;
    }

    public void setAgentDetails(AgentDetails agentDetails) {
        this.agentDetails = agentDetails;
    }

    public BillDetails getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(BillDetails billDetails) {
        this.billDetails = billDetails;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("chId=" + chId)
                .add("isRealTimeFetch=" + isRealTimeFetch)
                .add("custDetails=" + custDetails)
                .add("agentDetails=" + agentDetails)
                .add("billDetails=" + billDetails)
                .toString();
    }

    public static class CustDetails {
        private String mobileNo;
        private List<CustomerTag> customerTags;

        // Getters and Setters

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public List<CustomerTag> getCustomerTags() {
            return customerTags;
        }

        public void setCustomerTags(List<CustomerTag> customerTags) {
            this.customerTags = customerTags;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ")
                    .add("mobileNo='" + mobileNo + "'")
                    .add("customerTags=" + customerTags)
                    .toString();
        }
    }

    public static class CustomerTag {
        private String name="EMAIL";
        private String value;

        // Getters and Setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", CustomerTag.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("value='" + value + "'")
                    .toString();
        }
    }

    public static class AgentDetails {
        private String agentId="AM01AM11BNK519046222";
        private List<DeviceTag> deviceTags;
        

        // Getters and Setters

        public String getAgentId() {
            return agentId;
        }

        public void setAgentId(String agentId) {
            this.agentId = agentId;
        }

        public List<DeviceTag> getDeviceTags() {
            return deviceTags;
        }

        public void setDeviceTags(List<DeviceTag> deviceTags) {
            this.deviceTags = deviceTags;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", AgentDetails.class.getSimpleName() + "[", "]")
                    .add("agentId='" + agentId + "'")
                    .add("deviceTags=" + deviceTags)
                    .toString();
        }
    }

    public static class DeviceTag {
        private String name;
        private String value;

        // Getters and Setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", DeviceTag.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("value='" + value + "'")
                    .toString();
        }
    }

    public static class BillDetails {
        private String billerId;
        private List<CustomerParam> customerParams;

        // Getters and Setters

        public String getBillerId() {
            return billerId;
        }

        public void setBillerId(String billerId) {
            this.billerId = billerId;
        }

        public List<CustomerParam> getCustomerParams() {
            return customerParams;
        }

        public void setCustomerParams(List<CustomerParam> customerParams) {
            this.customerParams = customerParams;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", BillDetails.class.getSimpleName() + "[", "]")
                    .add("billerId='" + billerId + "'")
                    .add("customerParams=" + customerParams)
                    .toString();
        }
    }

    public static class CustomerParam {
        private String name;
        private String value;

        // Getters and Setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", CustomerParam.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("value='" + value + "'")
                    .toString();
        }
    }
    
   
    
}

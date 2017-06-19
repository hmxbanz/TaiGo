package com.xtdar.app.server.response;


import java.util.List;

public class OrderResponse {

    /**
     *
     *{"state":1,"msg":"成功","TotalPages":1,"Orders":[{"Remark":"最低级别会员期限","Price":200,"ProductName":"三个月会员","ProductID":1,"OrderID":1,"OrderType":1,"CreateDate":"\/Date(1458057600000)\/","UserID":2,"UserInfoID":2,"Sex":false,"NickName":"管理员","RealName":"陈小姐","CellPhone":"13729213015         ","EducationName":"中专","NativeProvinceName":"广东","NativeCityName":"汕头","NativeAreaName":"金平区","SexName":"女","Age":25}]}
     */

    private int state;
    private String msg;
    private String totalPages;
    private List<ResultEntity> result;

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }
    public List<ResultEntity> getOrders() {
        return result;
    }
    public void setOrders(List<ResultEntity> result) {
        this.result = result;
    }
    public static class ResultEntity {
        private String ProductID;
        private String ProductName;
        private String OrderID;
        private String OrderType;
        private String UserInfoID;
        private String NickName;
        private String Price;
        private String CreateDate;

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getProductID() {
            return ProductID;
        }

        public void setProductID(String productID) {
            ProductID = productID;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public String getOrderID() {
            return OrderID;
        }

        public void setOrderID(String orderID) {
            OrderID = orderID;
        }

        public String getOrderType() {
            return OrderType;
        }

        public void setOrderType(String orderType) {
            OrderType = orderType;
        }

        public String getUserInfoID() {
            return UserInfoID;
        }

        public void setUserInfoID(String userInfoID) {
            UserInfoID = userInfoID;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }
    }

}

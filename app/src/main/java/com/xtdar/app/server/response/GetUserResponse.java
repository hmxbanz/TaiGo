package com.xtdar.app.server.response;


public class GetUserResponse {

    /**
     * state : 1
     * result : {"UserInfo":{"ErrMessage":null,"PageHeader":null,"UserAccount":null,"UserInfo":{"AuthorityList":null,"UserInfoID":557,"UserID":557,"PersonalityDescribe":null,"SelfDescribe":"愿未来我们遇见","IDnumber":null,"Tel":null,"CellPhone":"15802058148         ","Address":null,"Sex":null,"Birthday":"\/Date(631555200000)\/","NickName":"绘生活","RealName":"郑晴","NationName":"汉族","EducationName":"大专","OccupationName":null,"IconSmall":"/Images/User/2017/04/23/2017042320274001_s.jpg","IconBig":"/Images/User/2017/04/23/2017042320274001_b.jpg","LastLogInDate":"\/Date(1492950253630)\/","BodyHeight":154,"BodyWeight":42,"Industry":null,"Province":1005,"City":1095,"Area":1551,"NativeProvince":1005,"NativeCity":1095,"NativeArea":1551,"Occupation":null,"Nation":6,"Education":14,"IndustryName":null,"ProvinceName":"广东","CityName":"汕头","AreaName":"金平区","NativeProvinceName":"广东","NativeCityName":"汕头","NativeAreaName":"金平区","MaritalStatus":42,"Salary":27,"House":35,"Car":38,"MaritalStatusName":"未婚","SalaryName":"2000-5000","HouseName":"与父母同住","CarName":"暂未购车","SexName":"女","NoReadMessage":1,"CreateDate":"\/Date(1492950205917)\/","UserName":"15802058148         ","Password":"ga123456            ","Status":true,"StatusName":"开通","Check":52,"OutDate":null,"OutDateCount":null,"RoleName":"用户","CheckName":"已审核","Score":2,"QQ":"2464657613","Email":"2464657613@qq.com","VisitedCount":6,"IsRealPerson":null,"ThumbUpCount":0,"FavorCount":0,"RoleID":3,"Age":27,"ModifyDate":"\/Date(1492950460223)\/","IsHomePage":true},"ShowPhotos":null,"UrlReferrer":null,"LifeShares":null,"LifeShare":null,"UserMessage":null,"UserAlbums":null,"AlbumPhotoes":null,"FriendCondition":{"MyConditionId":152,"Education":14,"AgeMax":33,"Salary":null,"AgeMin":27,"BodyHeightMin":168,"BodyHeightMax":176,"BodyWeightMin":null,"BodyWeightMax":null,"MaritalStatus":41,"House":null,"Car":null,"Province":1005,"City":1095,"Area":1551,"NativeProvince":null,"NativeCity":null,"NativeArea":null,"EducationName":"大专","SalaryName":null,"ProvinceName":"广东","CityName":"汕头","AreaName":"金平区","NativeProvinceName":null,"NativeCityName":null,"NativeAreaName":null,"MaritalStatusName":"未婚,","HouseName":"","CarName":"","UserInfoID":557,"UserID":557},"AuthorityList":null},"state":1,"msg":"成功"}
     */

    private int state;
    private String msg;
    private ResultEntity result;

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
    public ResultEntity getUserInfo() {        return result;    }
    public void setUserInfo(ResultEntity userInfo){this.result=userInfo;}

    public static class ResultEntity {
        private UserInfoEntity UserInfo;
        public void setUserInfo(UserInfoEntity userInfo) {
            UserInfo = userInfo;
        }
        public UserInfoEntity getUserInfo() {
            return UserInfo;
        }
        public static class UserInfoEntity {

            private String UserInfoID;
            private String UserID;
            private String PersonalityDescribe;
            private String SelfDescribe;
            private String NickName;
            private String RealName;
            private String NationName;
            private String EducationName;
            private String OccupationName;
            private String IconSmall;
            private String IconBig;
            private String BodyHeight;
            private String BodyWeight;
            private String Province;
            private String Occupation;
            private String IndustryName;
            private String ProvinceName ;
            private String CityName;
            private String AreaName;
            private String NativeProvinceName;
            private String NativeCityName;
            private String NativeAreaName;
            private String MaritalStatusName;
            private String SalaryName;
            private String HouseName;
            private String CarName;
            private String SexName;
            private String CreateDate;
            private String Age;

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String userID) {
                UserID = userID;
            }

            public String getUserInfoID() {
                return UserInfoID;
            }

            public void setUserInfoID(String userInfoID) {
                UserInfoID = userInfoID;
            }

            public String getPersonalityDescribe() {
                return PersonalityDescribe;
            }

            public void setPersonalityDescribe(String personalityDescribe) {
                PersonalityDescribe = personalityDescribe;
            }

            public String getSelfDescribe() {
                return SelfDescribe;
            }

            public void setSelfDescribe(String selfDescribe) {
                SelfDescribe = selfDescribe;
            }

            public String getNickName() {
                return NickName;
            }

            public void setNickName(String nickName) {
                NickName = nickName;
            }

            public String getRealName() {
                return RealName;
            }

            public void setRealName(String realName) {
                RealName = realName;
            }

            public String getNationName() {
                return NationName;
            }

            public void setNationName(String nationName) {
                NationName = nationName;
            }

            public String getEducationName() {
                return EducationName;
            }

            public void setEducationName(String educationName) {
                EducationName = educationName;
            }

            public String getOccupationName() {
                return OccupationName;
            }

            public void setOccupationName(String occupationName) {
                OccupationName = occupationName;
            }

            public String getIconSmall() {
                return IconSmall;
            }

            public void setIconSmall(String iconSmall) {
                IconSmall = iconSmall;
            }

            public String getIconBig() {
                return IconBig;
            }

            public void setIconBig(String iconBig) {
                IconBig = iconBig;
            }

            public String getBodyHeight() {
                return BodyHeight;
            }

            public void setBodyHeight(String bodyHeight) {
                BodyHeight = bodyHeight;
            }

            public String getBodyWeight() {
                return BodyWeight;
            }

            public void setBodyWeight(String bodyWeight) {
                BodyWeight = bodyWeight;
            }

            public String getProvince() {
                return Province;
            }

            public void setProvince(String province) {
                Province = province;
            }

            public String getOccupation() {
                return Occupation;
            }

            public void setOccupation(String occupation) {
                Occupation = occupation;
            }

            public String getIndustryName() {
                return IndustryName;
            }

            public void setIndustryName(String industryName) {
                IndustryName = industryName;
            }

            public String getProvinceName() {
                return ProvinceName;
            }

            public void setProvinceName(String provinceName) {
                ProvinceName = provinceName;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String cityName) {
                CityName = cityName;
            }

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String areaName) {
                AreaName = areaName;
            }

            public String getNativeProvinceName() {
                return NativeProvinceName;
            }

            public void setNativeProvinceName(String nativeProvinceName) {
                NativeProvinceName = nativeProvinceName;
            }

            public String getNativeCityName() {
                return NativeCityName;
            }

            public void setNativeCityName(String nativeCityName) {
                NativeCityName = nativeCityName;
            }

            public String getNativeAreaName() {
                return NativeAreaName;
            }

            public void setNativeAreaName(String nativeAreaName) {
                NativeAreaName = nativeAreaName;
            }

            public String getMaritalStatusName() {
                return MaritalStatusName;
            }

            public void setMaritalStatusName(String maritalStatusName) {
                MaritalStatusName = maritalStatusName;
            }

            public String getSalaryName() {
                return SalaryName;
            }

            public void setSalaryName(String salaryName) {
                SalaryName = salaryName;
            }

            public String getHouseName() {
                return HouseName;
            }

            public void setHouseName(String houseName) {
                HouseName = houseName;
            }

            public String getCarName() {
                return CarName;
            }

            public void setCarName(String carName) {
                CarName = carName;
            }

            public String getSexName() {
                return SexName;
            }

            public void setSexName(String sexName) {
                SexName = sexName;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String createDate) {
                CreateDate = createDate;
            }

            public String getAge() {
                return Age;
            }

            public void setAge(String age) {
                Age = age;
            }



           }
        private FriendConditionEntity FriendCondition;
        public void setFriendCondition(FriendConditionEntity friendCondition) {
            FriendCondition = friendCondition;
        }
        public FriendConditionEntity getFriendCondition() {
            return FriendCondition;
        }
        public static class FriendConditionEntity {
            private String UserID;
            private String UserInfoID;
            private String AgeMin;
            private String AgeMax;
            private String ProvinceName ;
            private String CityName;
            private String AreaName;
            private String NativeProvinceName;
            private String NativeCityName;
            private String NativeAreaName;
            private String BodyHeightMin;
            private String BodyHeightMax;
            private String BodyWeightMin;
            private String BodyWeightMax;
            private String MaritalStatusName;
            private String EducationName;
            private String HouseName;
            private String CarName;
            private String SalaryName;
            private String CreateDate;

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String userID) {
                UserID = userID;
            }

            public String getUserInfoID() {
                return UserInfoID;
            }

            public void setUserInfoID(String userInfoID) {
                UserInfoID = userInfoID;
            }

            public String getAgeMin() {
                return AgeMin;
            }

            public void setAgeMin(String ageMin) {
                AgeMin = ageMin;
            }

            public String getAgeMax() {
                return AgeMax;
            }

            public void setAgeMax(String ageMax) {
                AgeMax = ageMax;
            }

            public String getProvinceName() {
                return ProvinceName;
            }

            public void setProvinceName(String provinceName) {
                ProvinceName = provinceName;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String cityName) {
                CityName = cityName;
            }

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String areaName) {
                AreaName = areaName;
            }

            public String getNativeProvinceName() {
                return NativeProvinceName;
            }

            public void setNativeProvinceName(String nativeProvinceName) {
                NativeProvinceName = nativeProvinceName;
            }

            public String getNativeCityName() {
                return NativeCityName;
            }

            public void setNativeCityName(String nativeCityName) {
                NativeCityName = nativeCityName;
            }

            public String getNativeAreaName() {
                return NativeAreaName;
            }

            public void setNativeAreaName(String nativeAreaName) {
                NativeAreaName = nativeAreaName;
            }

            public String getBodyHeightMin() {
                return BodyHeightMin;
            }

            public void setBodyHeightMin(String bodyHeightMin) {
                BodyHeightMin = bodyHeightMin;
            }

            public String getBodyHeightMax() {
                return BodyHeightMax;
            }

            public void setBodyHeightMax(String bodyHeightMax) {
                BodyHeightMax = bodyHeightMax;
            }

            public String getBodyWeightMin() {
                return BodyWeightMin;
            }

            public void setBodyWeightMin(String bodyWeightMin) {
                BodyWeightMin = bodyWeightMin;
            }

            public String getBodyWeightMax() {
                return BodyWeightMax;
            }

            public void setBodyWeightMax(String bodyWeightMax) {
                BodyWeightMax = bodyWeightMax;
            }

            public String getMaritalStatusName() {
                return MaritalStatusName;
            }

            public void setMaritalStatusName(String maritalStatusName) {
                MaritalStatusName = maritalStatusName;
            }

            public String getEducationName() {
                return EducationName;
            }

            public void setEducationName(String educationName) {
                EducationName = educationName;
            }

            public String getHouseName() {
                return HouseName;
            }

            public void setHouseName(String houseName) {
                HouseName = houseName;
            }

            public String getCarName() {
                return CarName;
            }

            public void setCarName(String carName) {
                CarName = carName;
            }

            public String getSalaryName() {
                return SalaryName;
            }

            public void setSalaryName(String salaryName) {
                SalaryName = salaryName;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String createDate) {
                CreateDate = createDate;
            }
        }
        }


}

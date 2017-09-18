package com.byth.lifesaver.bean;

/**
 * Created by Administrator on 2017/7/12 0012.
 * 卡信息提交bean
 */

public class CardActiveInfoBean {
    private CardDto cardDto;//卡信息
    private UserDto userDto;//用户信息
    private ContactsDto contactsDto;//紧急联系人信息
    private HealthDto healthDto;//健康信息
    private CodeDto codeDto;//验证码信息

    public void setCardDto(CardDto cardDto) {
        this.cardDto = cardDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public void setContactsDto(ContactsDto contactsDto) {
        this.contactsDto = contactsDto;
    }

    public void setHealthDto(HealthDto healthDto) {
        this.healthDto = healthDto;
    }

    public void setCodeDto(CodeDto codeDto) {
        this.codeDto = codeDto;
    }

    public static class CardDto {
        private String cardCode;//卡号
        private String activatePic;//激活图片url

        public void setCardCode(String cardCode) {
            this.cardCode = cardCode;
        }

        public void setActivatePic(String activatePic) {
            this.activatePic = activatePic;
        }
    }

    public static class UserDto {
        private String userName;//用户名
        private String idCode;//身份证号码
        private int gender;//性别
        private String user_address;//详细地址
        private int id;

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setIdCode(String idCode) {
            this.idCode = idCode;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class ContactsDto {
        private String linkman;//联系人姓名
        private String relationship;//关系
        private String tel;//电话

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }

    public static class HealthDto {
        private String bloodType;//血型
        private String illItem;//病史
        private String illRemarks;//病史备注
        private String drugAllergy;//药物过敏
        private String drugRemarks;//药物过敏备注

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }

        public void setIllItem(String illItem) {
            this.illItem = illItem;
        }

        public void setIllRemarks(String illRemarks) {
            this.illRemarks = illRemarks;
        }

        public void setDrugAllergy(String drugAllergy) {
            this.drugAllergy = drugAllergy;
        }

        public void setDrugRemarks(String drugRemarks) {
            this.drugRemarks = drugRemarks;
        }
    }

    public static class CodeDto {
        private String mobileNo;//手机号码
        private String type;//验证码类型
        private String randCode;//验证码

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setRandCode(String randCode) {
            this.randCode = randCode;
        }
    }
}
package com.byth.lifesaver.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17 0017.
 * 主页轮播图片list bean
 */

public class HomePictureListBean {
    public List<FiledirDto> filedirDtoList;

    public List<FiledirDto> getFiledirDtoList() {
        return filedirDtoList;
    }

    public static class FiledirDto {
        private int fileId;//ID主键
        private String code;//文件类型
        private String fileName;//文件路径
        private String imageUrl;//链接
        private int seqNo;//序号

        public int getFileId() {
            return fileId;
        }

        public String getCode() {
            return code;
        }

        public String getFileName() {
            return fileName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public int getSeqNo() {
            return seqNo;
        }
    }

}

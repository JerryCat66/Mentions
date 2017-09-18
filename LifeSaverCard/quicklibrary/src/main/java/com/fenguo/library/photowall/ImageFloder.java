package com.fenguo.library.photowall;

public class ImageFloder {
    /**
     * 图片的文件夹路径
     */
    private String dir;

    /**
     * 第一张图片的路径
     */
    private String firstImagePath;

    /**
     * 文件夹的名称
     */
    private String name;

    /**
     * 图片的数量
     */
    private int count;

    boolean selected = false;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        if (dir.equals("all")) {
            this.dir = "all";
            this.name = "全部图片";
        } else {
            this.dir = dir;
            int lastIndexOf = this.dir.lastIndexOf("/");
            this.name = this.dir.substring(lastIndexOf);
        }
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}

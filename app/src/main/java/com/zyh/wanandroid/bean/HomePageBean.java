package com.zyh.wanandroid.bean;

import java.util.List;

/**
 * @author zyh
 * @date 2019/1/17
 */


public class HomePageBean {

    private int curPage;
    private List<HomePageDetail> datas;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setData(List<HomePageDetail> datas) {
        this.datas = datas;
    }

    public List<HomePageDetail> getData() {
        return datas;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean getOver() {
        return over;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

}

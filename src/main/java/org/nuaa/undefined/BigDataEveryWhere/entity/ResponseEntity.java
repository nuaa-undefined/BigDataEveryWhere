package org.nuaa.undefined.BigDataEveryWhere.entity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/6/1 14:53
 */
public class ResponseEntity<T> extends Response{

    private int count;
    private List<T> data;

    public ResponseEntity(){}
    public ResponseEntity(int code, String msg, int count, List<T> data){
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public ResponseEntity(int code, String msg, List<T> data){
        this.code = code;
        this.msg = msg;
        this.count = data != null ? data.size() : 0;
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

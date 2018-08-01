package org.nuaa.undefined.BigDataEveryWhere.entity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/6/1 14:53
 */
public class ResponseEntity<T> {
    public static final int GET_DATA_SUCCESS_CODE = 0;
    private int code;
    private String msg;
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
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

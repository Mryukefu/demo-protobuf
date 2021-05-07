package com.example.demoprotobuf.entry;

/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/7 18:13
 */
public enum PhoneType {

    MOBILE(0),
    HOME (1),
    WORK (2);
    private Integer code;
    PhoneType(Integer code) {
        this.code = code;

    }

}

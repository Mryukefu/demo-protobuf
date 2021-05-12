package com.example.demoprotobuf.entry.contanst;

import lombok.Getter;

/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/10 10:39
 */
@Getter
public enum LogEnum {
        NO(0,"no","不打印"),
        INFO(1,"info","info等级"),
        DEBUG(2,"debug","debug等级"),
        ERR(3,"err","err等级");

        /** 日记等级*/
        private Integer level;

        /** 日记等级名称 info ，debug,err*/
        private String levelName;

        /** 描述*/
        private String desc;

        LogEnum(Integer level, String levelName, String desc){
            this.level = level;
            this.levelName = levelName;
            this.desc = desc;
        }
}

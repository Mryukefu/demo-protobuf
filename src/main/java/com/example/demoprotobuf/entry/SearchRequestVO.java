package com.example.demoprotobuf.entry;

import lombok.Data;

/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/7 20:38
 */
@Data
public class SearchRequestVO {

    private String query;
    private String pageNumber;
    private String resultPerPage;
}

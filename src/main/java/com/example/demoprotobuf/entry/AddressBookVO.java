package com.example.demoprotobuf.entry;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * class desc
 * todo
 *
 * @author ykf
 * @date 2021/5/7 18:09
 */
@Data
//@ToString
public class AddressBookVO {

    private List<Person> people;
}

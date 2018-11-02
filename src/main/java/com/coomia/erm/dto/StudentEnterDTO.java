package com.coomia.erm.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author hcqi .
 * @since 2018/10/19
 * describe:
 * email:hechuanqi.top@gmail.com
 */
@Data
public class StudentEnterDTO {
    private long schoolId;
    private String school;
    private List<Map> students;
}

package com.one.exercise.pojo.dynamicmessage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 接受前端参数
 * 发布动态、
 */
@Setter
@Getter
@ToString
public class DynamicMessageBO {

    /**
     * 动态id
     */
    private Long dyId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    // private MultipartFile[] pictures;

    /**
     * 动态图片
     */
    private String[] pictures;

}

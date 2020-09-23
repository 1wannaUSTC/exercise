package com.one.exercise.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageUtil {

    // 传入参数
    private int total; // 查询得到的总记录数                 构造函数获取
    private int pageSize;// 分页每页大小                    构造器获取

    // 作为返回类的其他参数
    private int totalPage;  // 数据库分页得到的总页数
    private int pageStart;  // 当前页


    public PageUtil(int total, int pageSize) {
        this.total = total;
        this.pageSize = pageSize;

        if (pageSize <= 0) {
            pageSize = 1;
        }

        if (total % pageSize == 0) {
            this.totalPage = total / pageSize;
        } else {
            this.totalPage = total / pageSize + 1;
        }
    }

    /** 获取总页数 */
    public int getTotalPage() {
        return totalPage;
    }

    /** 获取开始索引，需要传入开始页 */
    public int getStartIndex(int pageStart) {

        if (pageStart <= 0) {
            pageStart = 1;
        }
        if (pageStart >= getTotalPage()) {
            pageStart = getTotalPage();
        }
        this.pageStart = pageStart;
        int startIndex = (pageStart - 1) * pageSize;
        if (startIndex < 0) {
            startIndex = 0;
        }
        return startIndex;
    }

}

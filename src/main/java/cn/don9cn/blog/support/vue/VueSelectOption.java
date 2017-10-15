package cn.don9cn.blog.support.vue;

import cn.don9cn.blog.model.BaseModel;


/**
 * @Author: liuxindong
 * @Description: 前端vue支持类,用于显示分类的下拉列表
 * @Create: 2017/10/10 10:21
 * @Modify:
 */
public class VueSelectOption extends BaseModel {


    private final String label;

    private final String value;

    private final String level;


    public VueSelectOption(String label, String value, String level) {
        this.label = label;
        this.value = value;
        this.level = level;
    }

    public String getLabel() {
        return label;
    }


    public String getValue() {
        return value;
    }


    public String getLevel() {
        return level;
    }


}

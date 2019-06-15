package com.sw.core.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-25
 */
@TableName("sw_app_province_code")
public class AppProvinceCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 省份编码
     */
    @TableField("province_code")
    private Integer provinceCode;
    /**
     * 省份名称
     */
    @TableField("province_name")
    private String provinceName;
    @TableField("sort_num")
    private Integer sortNum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "AppProvinceCode{" +
        "id=" + id +
        ", provinceCode=" + provinceCode +
        ", provinceName=" + provinceName +
        ", sortNum=" + sortNum +
        "}";
    }
}

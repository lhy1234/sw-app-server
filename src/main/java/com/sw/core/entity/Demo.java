package com.sw.core.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 *  MybatisPlus会默认使用实体类的类名到数据中找对应的表. 
 * </p>
 *
 * @author lihaoyang123
 * @since 2019-05-29
 */
@TableName("sw_demo")
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
	 * @TableId:
	 * 	 value: 指定表中的主键列的列名， 如果实体属性名与列名一致，可以省略不指定. 
	 *   type: 指定主键策略. 
	 */
    @TableId(value = "id", type = IdType.ID_WORKER)  
    private Long id;
    private String name;
    private Integer sex;
    private Integer age;
    @TableField("create_time")
    private Date createTime;

    //@TableField(exist=false)
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Demo{" +
        "id=" + id +
        ", name=" + name +
        ", sex=" + sex +
        ", age=" + age +
        ", createTime=" + createTime +
        "}";
    }
}

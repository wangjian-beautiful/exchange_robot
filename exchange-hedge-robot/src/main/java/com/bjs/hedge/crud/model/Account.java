package com.bjs.hedge.crud.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * UID，10000以内保留，作为公司账户
     */
    private Integer uid;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 标签，冗余，只帮助直观反馈
     */
    private String tag;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 修改时间
     */
    private Date mtime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取UID，10000以内保留，作为公司账户
     *
     * @return uid - UID，10000以内保留，作为公司账户
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置UID，10000以内保留，作为公司账户
     *
     * @param uid UID，10000以内保留，作为公司账户
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取余额
     *
     * @return balance - 余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置余额
     *
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取标签，冗余，只帮助直观反馈
     *
     * @return tag - 标签，冗余，只帮助直观反馈
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签，冗余，只帮助直观反馈
     *
     * @param tag 标签，冗余，只帮助直观反馈
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 获取创建时间
     *
     * @return ctime - 创建时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 设置创建时间
     *
     * @param ctime 创建时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 获取修改时间
     *
     * @return mtime - 修改时间
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * 设置修改时间
     *
     * @param mtime 修改时间
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}
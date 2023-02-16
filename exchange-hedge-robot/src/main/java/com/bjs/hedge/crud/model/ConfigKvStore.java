package com.bjs.hedge.crud.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "config_kv_store")
public class ConfigKvStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 唯一key
     */
    @Column(name = "config_key")
    private String configKey;

    /**
     * 配置值
     */
    private String value;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String meta;

    private Date ctime;

    private Date mtime;

    /**
     * 状态：1开启，0关闭
     */
    private Byte status;

    /**
     * 是否在后台配置管理显示：1显示，0隐藏
     */
    @Column(name = "is_open")
    private Byte isOpen;

    /**
     * 是否需要重新发布 1是，0否
     */
    @Column(name = "is_release")
    private Byte isRelease;

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
     * 获取唯一key
     *
     * @return config_key - 唯一key
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * 设置唯一key
     *
     * @param configKey 唯一key
     */
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    /**
     * 获取配置值
     *
     * @return value - 配置值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置配置值
     *
     * @param value 配置值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取描述
     *
     * @return meta - 描述
     */
    public String getMeta() {
        return meta;
    }

    /**
     * 设置描述
     *
     * @param meta 描述
     */
    public void setMeta(String meta) {
        this.meta = meta;
    }

    /**
     * @return ctime
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * @param ctime
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * @return mtime
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * @param mtime
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * 获取状态：1开启，0关闭
     *
     * @return status - 状态：1开启，0关闭
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态：1开启，0关闭
     *
     * @param status 状态：1开启，0关闭
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取是否在后台配置管理显示：1显示，0隐藏
     *
     * @return is_open - 是否在后台配置管理显示：1显示，0隐藏
     */
    public Byte getIsOpen() {
        return isOpen;
    }

    /**
     * 设置是否在后台配置管理显示：1显示，0隐藏
     *
     * @param isOpen 是否在后台配置管理显示：1显示，0隐藏
     */
    public void setIsOpen(Byte isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * 获取是否需要重新发布 1是，0否
     *
     * @return is_release - 是否需要重新发布 1是，0否
     */
    public Byte getIsRelease() {
        return isRelease;
    }

    /**
     * 设置是否需要重新发布 1是，0否
     *
     * @param isRelease 是否需要重新发布 1是，0否
     */
    public void setIsRelease(Byte isRelease) {
        this.isRelease = isRelease;
    }
}
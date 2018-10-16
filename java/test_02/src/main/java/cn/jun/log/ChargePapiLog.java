package cn.jun.log;

import java.util.Date;

public class ChargePapiLog {

    private static final long serialVersionUID = 1L;

    /**
     * 对应表中id
     * 主键
     */
    private Long id;

    /**
     * 对应表中log_level
     * 日志级别
     */
    private String logLevel;

    /**
     * 对应表中timestamp
     * 日志生成时间
     */
    private Date timestamp;

    /**
     * 对应表中create_time
     * 创建时间
     */
    private Date createTime;

    /**
     * 对应表中message
     * 日志内容
     */
    private String message;

    /**
     * 对应表中api
     * 接口名称
     */
    private String api;

    public ChargePapiLog(){
    }

    public ChargePapiLog(String logLevel, Date timestamp, String message, String api) {
        this.logLevel = logLevel;
        this.timestamp = timestamp;
        this.message = message;
        this.api = api;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getLogLevel(){
        return logLevel;
    }
    public void setLogLevel(String logLevel){
        this.logLevel = logLevel;
    }
    public Date getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(Date timestamp){
        this.timestamp = timestamp;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getApi() {
        return api;
    }
    public void setApi(String api) {
        this.api = api;
    }

    @Override
    public String toString() {
        return "ChargePapiLog{" +
                "id=" + id +
                ", logLevel='" + logLevel + '\'' +
                ", timestamp=" + timestamp +
                ", createTime=" + createTime +
                ", message='" + message + '\'' +
                ", api='" + api + '\'' +
                '}';
    }
}
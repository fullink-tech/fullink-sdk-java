package tech.fullink.api;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author crow
 */
public class FullinkResponse implements Serializable {
    private static final long serialVersionUID = -7703585421100075440L;
    private Integer status;
    private String internalErrorCode;
    private String msg;

    private String data;

    private FullinkHashMap score;

    @JSONField(serialize = false)
    public boolean isOk() {
        return null != status && 200 == status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInternalErrorCode() {
        return internalErrorCode;
    }

    public void setInternalErrorCode(String internalErrorCode) {
        this.internalErrorCode = internalErrorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public FullinkHashMap getScore() {
        return score;
    }

    public void setScore(FullinkHashMap score) {
        this.score = score;
    }
}

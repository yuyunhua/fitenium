package local.yunhua.fitenium.result;

import java.util.Date;
import java.util.Map;

/**
 * Created by yuyunhua on 2015-12-29.
 */
public class AbstractResult {

    private Date startTime;
    private Date endTime;
    private ResultCode code;
    private Map<ResultCode, Integer> stat;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public Map<ResultCode, Integer> getStat() {
        return stat;
    }

    public void setStat(Map<ResultCode, Integer> stat) {
        this.stat = stat;
    }
}

package tech.fullink.api.response;

import tech.fullink.api.FullinkHashMap;
import tech.fullink.api.FullinkResponse;

/**
 * @author crow
 */
public class PersonalLxfResponse extends FullinkResponse {
    private static final long serialVersionUID = -4338727039483171880L;
    private FullinkHashMap score;
    public FullinkHashMap getScore() {
        return score;
    }

    public void setScore(FullinkHashMap score) {
        this.score = score;
    }
}

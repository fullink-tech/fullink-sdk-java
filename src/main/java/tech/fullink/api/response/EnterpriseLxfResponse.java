package tech.fullink.api.response;

import tech.fullink.api.FullinkHashMap;
import tech.fullink.api.FullinkResponse;

/**
 * @author crow
 */
public class EnterpriseLxfResponse extends FullinkResponse {
    private static final long serialVersionUID = 334070954083593879L;

    private FullinkHashMap score;

    @Override
    public FullinkHashMap getScore() {
        return score;
    }

    @Override
    public void setScore(FullinkHashMap score) {
        this.score = score;
    }
}

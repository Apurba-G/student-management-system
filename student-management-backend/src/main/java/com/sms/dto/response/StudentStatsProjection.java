package com.sms.dto.response;

public interface StudentStatsProjection {
    long getTotal();
    long getActive();
    long getInactive();
    long getSuspended();
}

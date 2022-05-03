package com.group2.campus.service;

import java.io.IOException;
import java.util.Map;

public interface CampusService {

    /**
     * Make an appointment
     * @param paramMap
     * @return
     */
    Map<String, Object> submitOrder(Map<String, Object> paramMap);

    /**
     * Update payment status
     * @param paramMap
     */
    void updatePayStatus(Map<String, Object> paramMap);

    /**
     * Update Cancellation Status
     * @param paramMap
     */
    void updateCancelStatus(Map<String, Object> paramMap);


}

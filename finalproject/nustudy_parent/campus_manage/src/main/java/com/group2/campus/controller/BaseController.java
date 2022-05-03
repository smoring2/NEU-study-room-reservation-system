package com.group2.campus.controller;


import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public class BaseController {

    //prompt information
    public final static String MESSAGE_SUCCESS = "Successful operation！";
    public final static String MESSAGE_FAILURE = "operation failed！";

    /**
     * success
     *
     * @param message
     * @param redirectAttributes
     */
    protected void successMessage(String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", StringUtils.isEmpty(message) ? MESSAGE_SUCCESS : message);
        redirectAttributes.addFlashAttribute("messageType", 1);
    }

    /**
     * fail
     *
     * @param message
     * @param redirectAttributes
     */
    protected void failureMessage(String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", StringUtils.isEmpty(message) ? MESSAGE_FAILURE : message);
        redirectAttributes.addFlashAttribute("messageType", 0);
    }

    protected void failureMessage(String message, HttpServletRequest request) {
        request.setAttribute("message", StringUtils.isEmpty(message) ? MESSAGE_SUCCESS : message);
        request.setAttribute("messageType", 0);
    }

    /**
     * success page
     *
     * @param message
     * @param request
     */
    protected String successPage(String message, HttpServletRequest request) {
        request.setAttribute("messagePage", StringUtils.isEmpty(message) ? MESSAGE_SUCCESS : message);
        return "common/successPage";
    }

    /**
     * failure page
     *
     * @param message
     * @param request
     * @return
     */
    protected String failurePage(String message, HttpServletRequest request) {
        request.setAttribute("messagePage", StringUtils.isEmpty(message) ? MESSAGE_FAILURE : message);
        return "common/failurePage";
    }

    protected Map<String, Object> getFilters(HttpServletRequest request) {
        Map<String, Object> filters = WebUtils.getParametersStartingWith(request, "s_");
        return filters;
    }

}

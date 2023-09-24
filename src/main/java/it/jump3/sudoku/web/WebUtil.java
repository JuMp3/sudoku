package it.jump3.sudoku.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 01/04/2016.
 */
@Component
@Slf4j
public class WebUtil {

    private static final String DEFAULT_ERROR_VIEW = "error/error";

    @Value(value = "${app.version}")
    private String appVersion;

    @Value(value = "${spring.profiles.active}")
    private String profile;

    @Autowired
    private ErrorAttributes errorAttributes;

    public static final String IS_AJAX = "isAjax";

    public ModelAndView getErrorModelAndView(HttpServletRequest req, HttpServletResponse res, WebRequest webRequest, Exception e) {
        ModelAndView mav = new ModelAndView();

        if (ObjectUtils.isEmpty(e.getCause())) {
            mav.addObject("cause", "No cause available");
        } else {
            mav.addObject("cause", ExceptionUtils.getRootCause(e));
        }

        mav.addAllObjects(getErrorAttributes(webRequest, true));

        mav.addObject("appVersion", appVersion);
        mav.addObject("profile", profile);

        if (Boolean.TRUE.equals(isAjax(req))) {
            mav.setViewName("fragments/ajax/error");
        } else {
            mav.setViewName(DEFAULT_ERROR_VIEW);
        }

        res.setStatus(getStatus(req).value());
        mav.addObject("codeStatus", res.getStatus());

        log.error("WebUtil - Error: " + mav.getModelMap().get("cause"));
        return mav;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    private Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        return errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));
    }

    public static Boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }

    public static Boolean isAjaxUpload(HttpServletRequest request) {
        return request.getParameter("ajaxUpload") != null;
    }
}

package it.jump3.sudoku.controller;

import it.jump3.sudoku.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 01/04/2016.
 */
@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Autowired
    private WebUtil webUtil;

    @RequestMapping(value = PATH)
    public ModelAndView error(HttpServletRequest req, HttpServletResponse res, WebRequest webRequest, Exception e) throws Exception {
        return webUtil.getErrorModelAndView(req, res, webRequest, e);
    }

    public String getErrorPath() {
        return PATH;
    }
}

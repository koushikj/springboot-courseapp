package com.coursepoject.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
public class CustomErrorController implements ErrorController {

    Logger logger = Logger.getLogger(String.valueOf(CustomErrorController.class));
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        logger.info("error code"+request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        return "error";
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
}

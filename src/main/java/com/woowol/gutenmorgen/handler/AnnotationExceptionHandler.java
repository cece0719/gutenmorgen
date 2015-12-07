package com.woowol.gutenmorgen.handler;

import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Result.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class AnnotationExceptionHandler {
    @Autowired
    MappingJackson2JsonView jsonView;

    @ExceptionHandler(ResultException.class)
    public ModelAndView handleResultException(HttpServletRequest request, ResultException e) {
        log.warn("Result Exception Handled", e);
        ModelAndView mav = getModelAndView(e.getResult());
        setView(request, mav);
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception e) {
        log.error("Unexpected Exception", e);
        ModelAndView mav = getModelAndView(new Result(ReturnCode.UNKNOWN_ERROR, ExceptionUtils.getStackTrace(e)));
        setView(request, mav);
        return mav;
    }

    private ModelAndView getModelAndView(Result result) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("rtnCode", result.getRtnCode());
        mav.addObject("rtnMsg", result.getRtnMsg());
        return mav;
    }

    private void setView(HttpServletRequest request, ModelAndView mav) {
        if (request.getRequestURI().endsWith(".json")) {
            mav.setView(jsonView);
        } else {
            mav.setViewName("error");
        }
    }
}

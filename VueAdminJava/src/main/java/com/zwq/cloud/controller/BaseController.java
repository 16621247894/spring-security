package com.zwq.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
/**
 * @author wuqing.zhu
 */
public class BaseController {

    @Autowired
    HttpServletRequest request;
}

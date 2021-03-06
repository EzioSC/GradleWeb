package com.sunchao.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunchao.util.ConstantEnum;

@Controller
public class PageErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping("/error")
	public String error() { 
		return ConstantEnum.PAGE_404;
	}
}

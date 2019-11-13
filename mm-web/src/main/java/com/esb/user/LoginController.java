package com.esb.user;


import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esb.result.Result;
import com.esb.validator.IsMobile;
import com.esb.vo.LoginVo;

import lombok.extern.log4j.Log4j2;





@Log4j2
@RestController
@RequestMapping("/login/*")
public class LoginController {
	@SuppressWarnings("all")
	@Autowired
    UserService userService;

	 @RequestMapping("/do_login")
	    public Result<Map<String,Object>> doLogin(HttpServletResponse response,@Valid @IsMobile @RequestBody LoginVo loginVo) {//加入JSR303参数校验
	        log.info(loginVo.toString());
	        Map<String,Object> token = userService.login(loginVo);
	        return Result.success(token);
	    }   
}

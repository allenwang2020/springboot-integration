package com.esb.user;


import javax.servlet.http.HttpServletResponse;

import org.mm.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	    public Result<String> doLogin(HttpServletResponse response,@RequestBody LoginVo loginVo) {//加入JSR303参数校验
	        log.info(loginVo.toString());
	        String token = userService.login(response, loginVo);
	        return Result.success(token);
	    }   
}

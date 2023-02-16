package com.jeesuite.admin.controller;

import com.jeesuite.admin.common.Constants;
import com.jeesuite.admin.dao.entity.UserEntity;
import com.jeesuite.admin.dao.mapper.UserEntityMapper;
import com.jeesuite.admin.exception.JeesuiteBaseException;
import com.jeesuite.admin.model.LoginUserInfo;
import com.jeesuite.admin.model.WrapperResponseEntity;
import com.jeesuite.common.util.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserEntityMapper userMapper;


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<WrapperResponseEntity> login(HttpServletRequest request, @RequestBody Map<String, String> params) {
        String userName = StringUtils.trimToEmpty(params.get("userName"));
        String password = StringUtils.trimToEmpty(params.get("password"));

        UserEntity userEntity = userMapper.findByName(userName);
        if (userEntity == null || !userEntity.getPassword().equals(DigestUtils.md5WithSalt(password, userName))) {
            return new ResponseEntity<>(new WrapperResponseEntity(4001, "账号或密码错误"), HttpStatus.OK);
        }

        LoginUserInfo loginUserInfo = new LoginUserInfo(userName);
        loginUserInfo.setSuperAdmin(userEntity.getType().intValue() == 1);
        loginUserInfo.setId(userEntity.getId());
        if (!loginUserInfo.isSuperAdmin()) {
            if (userEntity.getStatus() != 0) {
                throw new JeesuiteBaseException(1001, "该账号已停用");
            }
            loginUserInfo.setGantProfiles(new ArrayList<>(Arrays.asList(userEntity.getGantEnvs().split(";|,"))));
        }

        request.getSession().setAttribute(Constants.LOGIN_SESSION_KEY, loginUserInfo);
        return new ResponseEntity<>(new WrapperResponseEntity(loginUserInfo), HttpStatus.OK);
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5WithSalt("a123456", "admin"));
    }

    @RequestMapping(value = "login_user_info", method = RequestMethod.GET)
    public ResponseEntity<WrapperResponseEntity> loginUserInfo(HttpServletRequest request) {
        LoginUserInfo loginUserInfo = (LoginUserInfo) request.getSession().getAttribute(Constants.LOGIN_SESSION_KEY);
        if (loginUserInfo == null) new ResponseEntity<>(new WrapperResponseEntity(401, "未登录"), HttpStatus.OK);
        return new ResponseEntity<>(new WrapperResponseEntity(loginUserInfo), HttpStatus.OK);
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.LOGIN_SESSION_KEY);
        return "redirect:" + request.getContextPath() + "/login.html";
    }
}

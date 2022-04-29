package com.fastPuter.website.service;

import com.fastPuter.website.controller.vo.FastPuterUserVO;
import com.fastPuter.website.entity.MallUser;
import com.fastPuter.website.util.PageQueryUtil;
import com.fastPuter.website.util.PageResult;

import javax.servlet.http.HttpSession;

public interface FastPuterUserService {

    PageResult getFastPuterUsersPage(PageQueryUtil pageUtil);


    String register(String loginName, String password);


    String login(String loginName, String passwordMD5, HttpSession httpSession);


    FastPuterUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession);


    Boolean lockUsers(Integer[] ids, int lockStatus);
}

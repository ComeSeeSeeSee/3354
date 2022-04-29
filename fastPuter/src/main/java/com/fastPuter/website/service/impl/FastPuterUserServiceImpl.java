package com.fastPuter.website.service.impl;

import com.fastPuter.website.common.Constants;
import com.fastPuter.website.common.ServiceResultEnum;
import com.fastPuter.website.controller.vo.FastPuterUserVO;
import com.fastPuter.website.dao.FastPuterUserMapper;
import com.fastPuter.website.entity.MallUser;
import com.fastPuter.website.service.FastPuterUserService;
import com.fastPuter.website.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class FastPuterUserServiceImpl implements FastPuterUserService {

    @Autowired
    private FastPuterUserMapper fastPuterUserMapper;

    @Override
    public PageResult getFastPuterUsersPage(PageQueryUtil pageUtil) {
        List<MallUser> mallUsers = fastPuterUserMapper.findMallUserList(pageUtil);
        int total = fastPuterUserMapper.getTotalMallUsers(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String register(String loginName, String password) {
        if (fastPuterUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (fastPuterUserMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        MallUser user = fastPuterUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            FastPuterUserVO fastPuterUserVO = new FastPuterUserVO();
            BeanUtil.copyProperties(user, fastPuterUserVO);
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, fastPuterUserVO);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public FastPuterUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession) {
        FastPuterUserVO userTemp = (FastPuterUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallUser userFromDB = fastPuterUserMapper.selectByPrimaryKey(userTemp.getUserId());
        if (userFromDB != null) {
            if (!StringUtils.isEmpty(mallUser.getNickName())) {
                userFromDB.setNickName(FastPuterUtils.cleanString(mallUser.getNickName()));
            }
            if (!StringUtils.isEmpty(mallUser.getAddress())) {
                userFromDB.setAddress(FastPuterUtils.cleanString(mallUser.getAddress()));
            }
            if (!StringUtils.isEmpty(mallUser.getIntroduceSign())) {
                userFromDB.setIntroduceSign(FastPuterUtils.cleanString(mallUser.getIntroduceSign()));
            }
            if (fastPuterUserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                FastPuterUserVO fastPuterUserVO = new FastPuterUserVO();
                userFromDB = fastPuterUserMapper.selectByPrimaryKey(mallUser.getUserId());
                BeanUtil.copyProperties(userFromDB, fastPuterUserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, fastPuterUserVO);
                return fastPuterUserVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return fastPuterUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }
}

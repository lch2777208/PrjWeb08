package org.zerock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zerock.domain.PointInsertVO;
import org.zerock.domain.UserVO;
import org.zerock.dto.LoginDTO;
import org.zerock.persistence.PointDAO;
import org.zerock.persistence.UserColorDAO;
import org.zerock.persistence.UserDAO;
import org.zerock.util.PointUtils;
import org.zerock.util.UnifyMessage;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by macbookpro on 2017. 2. 26. PM 7:53
 * sp4chap11-Project / org.zerock.service
 * No pain, No gain!
 * What : UserServiceImpl 클래스
 * Why : UserService interface에서 설정한(선언한) 메소드를 구현하려고
 * How : UserDAO에 의존성 주입하고 implemets(구현)함으로서 B/L 처리
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private UserDAO userDAO;

    @Inject
    private PointDAO pointDAO;                              // 포인트 DAO

    @Inject
    private UserColorDAO userColorDAO;                      // 칼라 DAO

    @Override
    public UserVO login(LoginDTO dto) throws Exception {
        return userDAO.login(dto);
    }

    @Override
    public void loginIpUpd(UserVO userVO) throws Exception {
        userDAO.loginIpUpd(userVO);
    }

    @Override
    public UserVO loginInfoNoPW(String uid) throws Exception {
        return userDAO.loginInfoNoPW(uid);
    }

    @Override
    public void keepLogin(String uid, String sessionId, Date next) throws Exception {

        userDAO.keepLogin(uid, sessionId, next);
    }

    @Override
    public UserVO checkLoginBefore(String value) {

        return userDAO.checkUserWithSessionKey(value);
    }

    @Override
    public void regist(UserVO userVO) throws Exception {

        /* 회원가입시 등록할 ip주소 */
        HttpServletRequest httpServletRequest =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // 출처: http://sesok808.tistory.com/378 [살아가는 그 이유]
        String ip = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (ip == null || ip.length() == 0) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");  // 웹로직
        }
        if (ip == null || ip.length() == 0) {
            ip = httpServletRequest.getRemoteAddr();
        }
        userVO.setRegip(ip);
        /* 회원가입시 등록할 ip주소 */

        userDAO.create(userVO);

        /* 회원가입시 100 포인트 증정 */
        PointUtils pointUtils = new PointUtils(userVO.getUid(), "회원가입", Integer.parseInt(UnifyMessage.getMessage("RegisterPoint")));
        PointInsertVO pointInsertVO = new PointInsertVO();
        pointInsertVO.setPinsid(userVO.getUid());
        pointInsertVO.setPinspoint(Integer.parseInt(UnifyMessage.getMessage("RegisterPoint"))); // 회원가입시 100 포인트 증정
        pointInsertVO.setPinsdeldate(pointUtils.getDeleteScheduleDate());
        pointInsertVO.setPinscontent(pointUtils.getSavingPointContent());
        pointDAO.insertOperPoint(pointInsertVO);

        pointDAO.balancePointUpdate(userVO.getUid(), Integer.parseInt(UnifyMessage.getMessage("RegisterPoint")));
        /* 회원가입시 100 포인트 증정 */

        /* 색깔별 회원가입수 tbl_color_result에 저장 */
        int userColorNum = userColorDAO.userColorInputGET(userVO.getUday());
        userColorDAO.userColorInputTotalUpd(userColorNum, userVO.getUday());
        /* 색깔별 회원가입수 tbl_color_result에 저장 */

    }

    @Override
    public int registUsersNumGET() throws Exception {
        return userDAO.registUsersNumGET();
    }
}

package org.zerock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.*;
import org.zerock.persistence.*;
import org.zerock.util.PointUtils;
import org.zerock.util.UnifyMessage;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by wtime on 2017-02-13. ${time}
 * org.zerock.service / Web Ex02
 * Better late than never!
 * What :
 * Why :
 * How : 댓글의 등록과 삭제시 처리할 부분이 있기 때문에 BoardDAO를 같이 사용하도록 다음과 2개의 @Inject.
 */
@Service
public class ReplyServiceImpl implements ReplyService {

    @Inject
    private ReplyDAO replyDAO;

    @Inject
    private BoardDAO boardDAO;

    @Inject
    private PointDAO pointDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private UserColorDAO userColorDAO;

//    @Override
//    public void addReply(ReplyVO vo) throws Exception {
//
//        replyDAO.create(vo);
//    }

    /**
     * 새로운 댓글이 추가되면 tbl_board의 replycnt 칼럼의 값을 1 증가시키는 작업과 댓글이 삭제될 때 replycnt 칼럼의 값을 -1 시키는 작업으로 수정하고 @Transactional을 이용해서 처리.
     *
     * @param replyVO the vo
     * @throws Exception the exception
     */
    @Transactional
    @Override
    public void addReply(ReplyVO replyVO, HttpSession httpSession) throws Exception {

        Object object = httpSession.getAttribute("login");
        UserVO loginUserVO = (UserVO) object;
        if (object != null) {
        }

        replyDAO.create(replyVO);
        boardDAO.updateReplyCnt(replyVO.getBno(), 1);

        /* 댓글 작성시 접속한 유저의 별명을 통해서 총 댓글 등록수 구함 */
        int tReplyNnum = replyDAO.totalUserReplyNumGET(loginUserVO.getUid());
        loginUserVO.setTreply(tReplyNnum);
        userDAO.totalUserReplyNumUPD(loginUserVO);
        /* 댓글 작성시 접속한 유저의 별명을 통해서 총 댓글 등록수 구함 */

        /* 댓글 작성시 칼라별 tbl_color_result로 update 하는 비지니스 로직 */
        int tcReplyNum = replyDAO.totalColorReplyNumGet(loginUserVO.getUday());
        userColorDAO.totalColorReplyNumUPD(tcReplyNum, loginUserVO.getUday());
        /* 댓글 작성시 칼라별 tbl_color_result로 update 하는 비지니스 로직 */

        /* 댓글 작성시 +10 포인트 */
        replyVO = replyDAO.readByIDnBnonText(replyVO);

        PointUtils pointUtils = new PointUtils(loginUserVO.getUid(), replyVO.getRno(), "댓글 작성", Integer.parseInt(UnifyMessage.getMessage("ReplyWritePoint")));

        PointInsertVO pointInsertVO = new PointInsertVO();
        pointInsertVO.setPinsid(replyVO.getRid());
        pointInsertVO.setPinspoint(Integer.parseInt(UnifyMessage.getMessage("ReplyWritePoint")));
        pointInsertVO.setPinsdeldate(pointUtils.getDeleteScheduleDate());
        pointInsertVO.setPinscontent(pointUtils.getSavingPointContent());
        pointDAO.insertOperPoint(pointInsertVO);

        pointUtils.setBalancePoint(loginUserVO.getUpoint());
        pointDAO.balancePointUpdate(loginUserVO.getUid(), Integer.parseInt(UnifyMessage.getMessage("ReplyWritePoint")));
        /* 댓글 작성시 +10 포인트 */

    }

    /**
     * 새로운 댓글이 추가되면 tbl_board의 replycnt 칼럼의 값을 1 증가시키는 작업과 댓글이 삭제될 때 replycnt 칼럼의 값을 -1 시키는 작업으로 수정하고 @Transactional을 이용해서 처리.
     *
     * @param rno
     * @throws Exception the exception
     */
    @Transactional
    @Override
    public void removeReply(Integer rno, HttpSession httpSession) throws Exception {

        Object object = httpSession.getAttribute("login");
        UserVO loginUserVO = (UserVO) object;

        /* tbl_board의 replycnt 칼럼의 값을 1 증가시키는 작업과 댓글이 삭제될 때 replycnt 칼럼의 값을 -1 시키는 작업 */
        int bno = replyDAO.getBno(rno);
        replyDAO.delete(rno);                   // MyBatis 맵퍼 딜리트에서 업데이트(replyvisible = 'Y') 로 변경
        boardDAO.updateReplyCnt(bno, -1);
        /* tbl_board의 replycnt 칼럼의 값을 1 증가시키는 작업과 댓글이 삭제될 때 replycnt 칼럼의 값을 -1 시키는 작업 */

        /* 댓글 삭제시 접속한 유저의 별명을 통해서 총 댓글 등록수 구함 */
        int tReplynum = replyDAO.totalUserReplyNumGET(loginUserVO.getUid());    // 로그인한 유저의 아이디에 따라 총 댓글 수 조회
        loginUserVO.setTreply(tReplynum);
        userDAO.totalUserReplyNumUPD(loginUserVO);                              // 로그인한 유저의 총 댓글수(treply 칼럼)에 업데이트
        /* 댓글 삭제시 접속한 유저의 별명을 통해서 총 댓글 등록수 구함 */

        /* 댓글 삭제시 칼라별 tbl_color_result로 update 하는 비지니스 로직 */
        int tcReplynum = replyDAO.totalColorReplyNumGet(loginUserVO.getUday()); // 로그인한 유저의 칼라에 따라서 칼라 기준으로 총 토탈수 조회
        userColorDAO.totalColorReplyNumUPD(tcReplynum, loginUserVO.getUday());  // tbl_color_result에 칼라 기준의 총 토탈수 업데이트 주입
        /* 댓글 삭제시 칼라별 tbl_color_result로 update 하는 비지니스 로직 */

        /* 댓글 삭제시 -10 포인트 */
        PointUtils pointUtils = new PointUtils(
                loginUserVO.getUid(),
                Integer.parseInt(UnifyMessage.getMessage("ReplyDeletePoint")),
                "댓글 삭제",
                rno);

        PointUpdateVO pointUpdateVO = new PointUpdateVO();
        pointUpdateVO.setPupdid(loginUserVO.getUid());
        pointUpdateVO.setPupdpoint(Integer.parseInt(UnifyMessage.getMessage("ReplyDeletePoint")));
        pointUpdateVO.setPupdcontent(pointUtils.getUsePointContent());

        pointUtils.setBalancePoint(loginUserVO.getUpoint());

        pointDAO.updateOperPoint(pointUpdateVO);                                            // tbl_point_update 추가(insert)
        pointDAO.balancePointUpdate(loginUserVO.getUid(), pointUtils.getBalancePoint());    // tbl_user 유저 포인트 변경(update)

        PointDeleteVO pointDeleteVO = new PointDeleteVO();
        pointDeleteVO.setPdelid(loginUserVO.getUid());
        pointDeleteVO.setPdelpoint(Integer.parseInt(UnifyMessage.getMessage("ReplyDeletePoint")));
        pointDeleteVO.setPdelcontent(pointUtils.getExtinctPointContent());
        pointDAO.deleteOperPoint(pointDeleteVO);                                            // tbl_point_delete 추가(insert)
       /* 댓글 삭제시 -10 포인트 */

    }

    @Override
    public List<ReplyVO> listReply(Integer bno) throws Exception {

        return replyDAO.list(bno);
    }

    @Override
    public void modifyReply(ReplyVO vo) throws Exception {

        replyDAO.update(vo);
    }

//    @Override
//    public void removeReply(Integer rno) throws Exception {
//
//        replyDAO.delete(rno);
//    }

    @Override
    public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {

        return replyDAO.listPage(bno, cri);
    }

    @Override
    public int count(Integer bno) throws Exception {

        return replyDAO.count(bno);
    }


}

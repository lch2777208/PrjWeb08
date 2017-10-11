package org.zerock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zerock.domain.GoodCntVO;
import org.zerock.service.CntService;

import javax.inject.Inject;

/**
 * Created by Emiya on 2017-10-01 오후 2:07
 * untitled / org.zerock.controller
 * No pain, No gain!
 * What :
 * Why :
 * How :
 * << 개정이력(Modification Information) >>
 * 수정일         수정자          수정내용
 * -------       --------       ---------------------------
 * 2017/04/21     김진국          최초 생성
 * 2017/05/27     이몽룡          인증이 필요없는 URL을 패스하는 로직 추가
 *
 * @author 개발팀 김진국
 * @version 1.0
 * @see
 * @since 2017/04/10
 */

@Controller
@RequestMapping("/cnt")
public class CntController {

    private static final Logger logger = LoggerFactory.getLogger(CntController.class);

    @Inject
    private CntService cntService;

    @RequestMapping(value = "/goodcntpush", method = RequestMethod.POST)
    public ResponseEntity<String> goodCntPush(@RequestBody GoodCntVO goodCntVO) {

//        logger.info("lll~~~ 01 : " + goodCntVO.toString());

        ResponseEntity<String> entity = null;
        try {
//            logger.info("lll~~~ 02 : " + goodCntVO.toString());
            cntService.goodCntPush(goodCntVO);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/badcntpush", method = RequestMethod.POST)
    public ResponseEntity<String> badCntPush(@RequestBody GoodCntVO badCntVO) {

//        logger.info("lll~~~ 01 : " + badCntVO.toString());

        ResponseEntity<String> entity = null;

        try {
//            logger.info("lll~~~ 02 : " + badCntVO.toString());
            cntService.badCntPush(badCntVO);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/spamcntpush", method = RequestMethod.POST)
    public ResponseEntity<String> spamCntPush(@RequestBody GoodCntVO spamCntVO) {

        logger.info("lll~~~ 01 : " + spamCntVO.toString());

        ResponseEntity<String> entity = null;
        try {
            logger.info("lll~~~ 02 : " + spamCntVO.toString());
            cntService.spamCntPush(spamCntVO);
            entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
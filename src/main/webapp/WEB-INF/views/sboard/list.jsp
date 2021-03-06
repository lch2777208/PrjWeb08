<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ page session="false" %>--%>

<%@include file="../include/header.jsp" %>

<!--=== Breadcrumbs v4 ===-->
<div class="breadcrumbs-v4">
    <div class="container">
        <span class="page-name">게시판 리스트 페이지</span>
        <h1>Maecenas <span class="shop-green">enim</span> sapien</h1>
        <ul class="breadcrumb-v4-in">
            <li><a href="home.jsp">Home</a></li>
            <li><a href="">Product</a></li>
            <li class="active">Product Filter Page</li>
        </ul>
    </div><!--/end container-->
</div>
<!--=== End Breadcrumbs v4 ===-->

<!-- Main content -->
<section class="content">
    <div class="row">
        <!-- left column -->
        <div class="col-md-12">
            <!-- general form elements -->

            <%-- 전체 검색하면 검색 버튼 및 기능 안보임 --%>
            <c:if test="${not empty pageMaker}">
                <div class='box'>
                    <div class="box-header with-border">
                            <%--<h3 class="box-title">Board List</h3>--%>
                        <h3 class="box-title">${cateName}</h3>
                    </div>

                    <div class='box-body text-right'>
                        <select name="searchType">
                                <%-- 옵션 기본값으로 하고 싶을 때 맨 상위를 주석으로 처리해서 제목이 기본값으로 보이게! --%>
                                <%--<option value="n"--%>
                                <%--<c:out value="${cri.searchType == null?'selected':''}"/>>--%>
                                <%--검색 옵션--%>
                                <%--</option>--%>
                            <option value="t"
                                    <c:out value="${cri.searchType eq 't' ? 'selected' : ''}"/>>
                                제목
                            </option>
                            <option value="c"
                                    <c:out value="${cri.searchType eq 'c' ? 'selected' : ''}"/>>
                                내용
                            </option>
                            <option value="w"
                                    <c:out value="${cri.searchType eq 'w' ? 'selected' : ''}"/>>
                                작성자
                            </option>

                            <option value="tc"
                                    <c:out value="${cri.searchType eq 'tc' ? 'selected' : ''}"/>>
                                제목 or 내용
                            </option>
                            <option value="cw"
                                    <c:out value="${cri.searchType eq 'cw' ? 'selected' : ''}"/>>
                                내용 or 작성자
                            </option>
                            <option value="tcw"
                                    <c:out value="${cri.searchType eq 'tcw' ? 'selected' : ''}"/>>
                                제목 or 내용 or 작성자
                            </option>
                        </select>

                        <input type="text" name='keyword' id="keywordInputCate" value='${cri.keyword}'>
                            <%--<button id='newBtn'>글 쓰기0</button>--%>
                        <button id='searchBtn01' class="btn btn-primary">검색</button>
                    </div>
                </div>
            </c:if>

            <div class="box">
                <div class="box-header with-border">
                    <%--<h3 class="box-title">LIST</h3>--%>
                    <h3 class="box-title"></h3>
                </div>
                <div class="box-body">
                    <table class="table table-bordered">
                        <tr>
                            <th style="width: 10px; text-align: center;">BNO</th>
                            <th>TITLE</th>
                            <th style="width: 40px; text-align: center;" id="goodSort">Good</th>
                            <th style="width: 40px; text-align: center;" id="gbSort">G/B</th>
                            <th style="width: 40px;  text-align: center;" id="badSort">Bad</th>
                            <th style="width: 120px; text-align: center;">WRITER</th>
                            <th style="width: 100px; text-align: center;">REGDATE</th>
                            <th style="width: 40px; text-align: center;" id="spamSort">Spam</th>
                            <th style="width: 40px; text-align: center;" id="viewSort">VIEW</th>
                        </tr>

                        <%--<c:forEach items="${list}" var="boardVO">--%>

                        <%--<tr>--%>
                        <%--<td>${boardVO.bno}</td>--%>
                        <%--<td><a--%>
                        <%--href='/sboard/readPage${pageMaker.makeSearch(pageMaker.cri.page) }&bno=${boardVO.bno}'>--%>
                        <%--${boardVO.title} </a></td>--%>
                        <%--<td>${boardVO.writer}</td>--%>
                        <%--<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"--%>
                        <%--value="${boardVO.regdate}"/></td>--%>
                        <%--<td><span class="badge bg-red">${boardVO.viewcnt }</span></td>--%>
                        <%--</tr>--%>

                        <%--</c:forEach>--%>

                        <c:forEach items="${list}" var="boardVO" varStatus="itr">
                            <tr>
                                <input type="hidden" id="bringBoardVOgetcolor" value=${boardVO.getcolor}>
                                <td align="center">${boardVO.bno}</td>
                                <td>
                                    <a href='/sboard/readPa${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${boardVO.bno}' class="cookieBoardPOST">
                                        ${boardVO.title}
                                            <c:if test="${boardVO.replycnt > 0}">
                                                <strong>[ ${boardVO.replycnt} ]</strong>
                                            </c:if>
                                    </a>
                                </td>
                                <td align="right"><span class="badge bg-green-gradient center listgcnt">${boardVO.goodcnt}</span>
                                </td>
                                <td align="right"><span
                                        class="badge bg-yellow-gradient center listgbcnt">${boardVO.gbcnt}</span></td>
                                <td align="right"><span class="badge bg-red-gradient center listbcnt">${boardVO.badcnt}</span>
                                </td>
                                <%--<td align="left"><img src="/assets/img/colors/001.png" id="getColor" alt="">--%>
                                        <%--${boardVO.getcolor} // ${boardVO.writer}--%>
                                <%--</td>--%>
                                <td align="left"><img src="/assets/img/colors/${boardVO.getcolor}.png" id="getColor" alt="">
                                    ${boardVO.writer}
                                </td>
                                <td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${boardVO.regdate}"/></td>
                                <td align="right"><span class="badge bg-orange-active center listscnt">${boardVO.spamcnt}</span>
                                </td>
                                <td align="right"><span class="badge bg-gray-light center listvcnt">${boardVO.viewcnt}</span>
                                </td>
                            </tr>
                        </c:forEach>

                        <%-- listAny 전체 아이템 검색 / 반복문을 통한 리스트 목록 --%>
                        <c:forEach items="${listAny}" var="boardVO" varStatus="itr">
                            <tr>
                                <input type="hidden" id="bringBoardVOgetcolor" value=${boardVO.getcolor}>
                                <td align="center">${boardVO.bno}</td>
                                <td>
                                    <a href='/sboard/readPage${pageMakerAny.makeSearchAll(pageMakerAny.criListAny.page)}&bno=${boardVO.bno}' class="cookieBoardPOST">
                                        ${boardVO.title}
                                            <c:if test="${boardVO.replycnt > 0}">
                                                <strong>[ ${boardVO.replycnt} ]</strong>
                                            </c:if>
                                    </a>
                                </td>
                                <td align="right"><span class="badge bg-green-gradient center listgcnt">${boardVO.goodcnt}</span>
                                </td>
                                <td align="right"><span
                                        class="badge bg-yellow-gradient center listgbcnt">${boardVO.gbcnt}</span></td>
                                <td align="right"><span class="badge bg-red-gradient center listbcnt">${boardVO.badcnt}</span>
                                </td>
                                <td align="left"><img src="/assets/img/colors/${boardVO.getcolor}.png" id="getColor" alt="">
                                    ${boardVO.writer}
                                </td>
                                    <%--<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"--%>
                                <td align="center"><fmt:formatDate value="${boardVO.regdate}"
                                                                   pattern="yyyy-MM-dd"/></td>
                                <td align="right"><span class="badge bg-orange-active center listscnt">${boardVO.spamcnt}</span>
                                </td>
                                <td align="right"><span class="badge bg-gray-light center listvcnt">${boardVO.viewcnt}</span>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <!-- /.box-body -->

                <div>
                    <button id='newBtn' class="btn btn-primary pull-right">글 쓰기</button>
                </div>

                <div class="box-footer">

                    <div class="text-center">
                        <ul class="pagination pagination-v2">

                            <c:choose>
                                <%-- cate에 맞는 검색 (페이징 기능) --%>
                                <c:when test="${not empty pageMaker}">
                                    <c:if test="${pageMaker.prev}">
                                        <li><a href="list${pageMaker.makeSearch(pageMaker.startPage - 1) }">&laquo;</a>
                                        </li>
                                    </c:if>

                                    <c:forEach begin="${pageMaker.startPage }"
                                               end="${pageMaker.endPage }" var="idx">
                                        <li<c:out value="${pageMaker.cri.page == idx?' class=active':''}"/>>
                                            <a href="list${pageMaker.makeSearch(idx)}">${idx}</a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
                                        <li><a href="list${pageMaker.makeSearch(pageMaker.endPage + 1) }">&raquo;</a>
                                        </li>
                                    </c:if>
                                </c:when>

                                <%-- 전체 게시판 검색 (페이징 기능) --%>
                                <c:when test="${not empty pageMakerAny}">
                                    <c:if test="${pageMakerAny.prev}">
                                        <li><a href="listAny${pageMakerAny.makeSearchAll(pageMakerAny.startPage - 1) }">&laquo;</a>
                                        </li>
                                    </c:if>

                                    <c:forEach begin="${pageMakerAny.startPage }"
                                               end="${pageMakerAny.endPage }" var="idx">
                                        <li<c:out value="${pageMakerAny.criListAny.page == idx?' class=active':''}"/>>
                                            <a href="listAny${pageMakerAny.makeSearchAll(idx)}">${idx}</a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${pageMakerAny.next && pageMakerAny.endPage > 0}">
                                        <li><a href="listAny${pageMakerAny.makeSearchAll(pageMakerAny.endPage + 1) }">&raquo;</a>
                                        </li>
                                    </c:if>
                                </c:when>
                            </c:choose>
                            <%--<input type="text" id="goUrl" name="goUrl" class="goUrl" value="">--%>
                        </ul>
                    </div>
                    <%-- JSTL의 <c:if>는 boolean으로 나오는 결과를 확인하므로 ${pageMaker.prev}를 이용해서 이전 페이지로 가는 링크가 있어야 하는지를 판단. --%>
                    <%-- 현재 페이지의 번호는 PageMaker 객체 내의 Criteria 객체에 존재하므로 ${pageMaker.cri.page}를 이용해서
                    getCriteria(), getPage()를 호출하는 형태로 사용. 이를 이용해서 화면에 현재 페이지 번호만을 눈에 띄게 표현할 수 있음. --%>
                    <%-- 이전의 예제는 단순히 게시물의 번호를 전송하는 링크만 생성되었지만, 위와 같이 페이지 정보를 유지할 수 있도록 변경. --%>

                </div>
                <!-- /.box-footer-->
            </div>
        </div>
        <!--/.col (left) -->
    </div>
    <!-- /.row -->
</section>
<!-- /.content -->

<%--<script src="http://code.jquery.com/jquery-latest.js"></script>--%>
<script>
    var result = '${msg}';

    if (result == 'SUCCESS') {
        alert("처리가 완료되었습니다.");
    }
</script>

<%--<script src="http://code.jquery.com/jquery-latest.js"></script>--%>
<%--ex) www.profile.com?name=hyewon&age=25--%>
<script>

    // list(리스트)의 g/b 넘버 포맷팅 Todo: 나머지 넘버 포맷팅 해야함
    $('.listgcnt').number(true);
    $('.listgbcnt').number(true);
    $('.listbcnt').number(true);
    $('.listscnt').number(true);
    $('.listvcnt').number(true);

    function getUrlParams() {
        var params = {};
        window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (str, key, value) {
            params[key] = value;
        });
        return params;
    }

    $(document).ready(function () {

        var allUrlAddr = $(location).attr('href');

        $('#searchBtn01').on("click", function (event) { // cate
            self.location = "list"
                + '${pageMaker.makeQuery(1)}'
                + "&searchType="
                + $("select option:selected").val()
                + "&keyword="
                + $('#keywordInputCate').val()
                + "&cate="
                + oParams.cate;
        });

        $('#searchBtn02').on("click", function (event) { // 전체 검색
            self.location = "listAny"
                + '${pageMaker.makeQuery(1)}'
                // + "&searchType="
                // + $("select option:selected").val()
                + "&keyword="
                + $('#keywordInputCate').val();
            // + "&cate="
            // + oParams.cate;
        });

        window.onload = function () {
            oParams = getUrlParams();
        }

        // 새 글 등록
        $('#newBtn').on("click", function (evt) {
            self.location = 'register?cate='
                + oParams.cate;
            <%--+ '${getQuerystring(cate)}';--%>
        });

//        alert("prevSelfLocation : " + prevSelfLocation);
//        var prevSelfLocation = "";

        $('#goodSort').on("click", function (event) {

            var criUrlParam = "&cntSortType=gc";
            var temp = allUrlAddr + "";

            if (temp.match(criUrlParam)) {
                return true;
            } else {
                self.location = temp + "&cntSortType=gc";
            }
        });

        $('#gbSort').on("click", function (event) {

            var criUrlParam = "&cntSortType=gbc";
            var temp = allUrlAddr + "";

            if (temp.match(criUrlParam)) {
                return true;
            } else {
                self.location = temp + "&cntSortType=gbc";
            }
        });

        $('#badSort').on("click", function (event) {

            var criUrlParam = "&cntSortType=bc";
            var temp = allUrlAddr + "";

            if (temp.match(criUrlParam)) {
                return true;
            } else {
                self.location = temp + "&cntSortType=bc";
            }
        });

        $('#spamSort').on("click", function (event) {

            var criUrlParam = "&cntSortType=sc";
            var temp = allUrlAddr + "";

            if (temp.match(criUrlParam)) {
                return true;
            } else {
                self.location = temp + "&cntSortType=sc";
            }
        });

        $('#viewSort').on("click", function (event) {

            var criUrlParam = "&cntSortType=vc";
            var temp = allUrlAddr + "";

            if (temp.match(criUrlParam)) {
                return true;
            } else {
                self.location = temp + "&cntSortType=vc";
            }
        });

        function pad(n, width) {
            n = n + '';
            return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;

        }

//
//        var number = $('.bringBoardVOgetcolor').val();
//
//        console.log("lllll~~~~~ number : " + number);
//
//        var tempUrl01 = "/assets/img/colors/";
//        var tempUrl02 = pad(number, 3);
//        var tempUrl03 = ".png";
//
//        var tempUrl = "";
//        var temp = tempUrl.concat(tempUrl01, tempUrl02, tempUrl03);
//
//        console.log("lllll~~~~~ temp : " + temp);
//
//        $('.getColor').prop("src", temp);

        <%--$("#getColor").each(function (itr) {--%>
            <%--&lt;%&ndash;$(this).after($m[${list}]);&ndash;%&gt;--%>
            <%--console.log("lllll~~~~~ {itr} : " + itr.length);--%>

            <%--var number = $('#bringBoardVOgetcolor').val();--%>

            <%--console.log("lllll~~~~~ number : " + number);--%>

            <%--var tempUrl01 = "/assets/img/colors/";--%>
            <%--var tempUrl02 = pad(number, 3);--%>
            <%--var tempUrl03 = ".png";--%>

            <%--var tempUrl = "";--%>
            <%--var temp = tempUrl.concat(tempUrl01, tempUrl02, tempUrl03);--%>

            <%--console.log("lllll~~~~~ temp : " + temp);--%>

            <%--$('.getColor')[itr.current()].prop("src", temp);--%>
        <%--});--%>
        <%--&lt;%&ndash;$(".reptilia").remove();&ndash;%&gt;--%>

//        $('#cookieBoardPOST').on("click", function () {
//
//
//        });

    });

</script>

<%@include file="../include/footer.jsp" %>

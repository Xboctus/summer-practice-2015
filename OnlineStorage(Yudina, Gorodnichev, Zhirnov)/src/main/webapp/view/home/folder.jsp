<%--
  Created by IntelliJ IDEA.
  User: sergej
  Date: 21.07.15
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<rapid:override name="content">
  <header>
    <div class = "col-sm-12">
      <a href="/folder/${id}/load" class="btn btn-default">Load file</a>
      <a href="/folder/${id}/add" class="btn btn-default">Add folder</a>
    </div>
  </header>
  <div class="content-wrapper">
    <section class="content">
      <div class="row">
        <section class="col-lg-12">
          <table class="table table-bordered table-hover">
            <thead>
            <tr>
              <th>#</th>
              <th>Name</th>
              <th>Type</th>
              <th>Options</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${!empty files}">
              <c:forEach items="${files}" var="file">
                <tr>
                  <td>${file.getId()}</td>
                  <c:choose>
                    <c:when test="${file.getType() == 1}">
                      <td><a href="/folder/${file.getId()}">${file.getName()}</a></td>
                      <td><i class="fa fa-folder"></i></td>
                    </c:when>
                    <c:when test="${file.getType() == 0}">
                      <td>${file.getName()}</td>
                      <td><i class="fa fa-file"></i></td>
                    </c:when>
                    <c:otherwise>
                      <td>${file.getName()}</td>
                      <td></td>
                    </c:otherwise>
                  </c:choose>
                  <td>
                    <c:if test="${file.userId == user.id}">
                      <a name="button" class="btn btn-default btn-sm" href="/delete/${file.getId()}"><i class="fa fa-trash"></i>Delete</a>
                      <a name="button" class="btn btn-default btn-sm" href="/share/${file.id}"><i class="fa fa-share-alt"></i>Share</a>
                    </c:if>
                    <a name="button" class="btn btn-default btn-sm" href="#"><i class="fa fa-cloud-download"></i>Download</a>
                  </td>
                </tr>
              </c:forEach>
            </c:if>
            </tbody>
          </table>
        </section>
      </div>
    </section>
  </div>
</rapid:override>

<%@ include file="base.jsp"%>

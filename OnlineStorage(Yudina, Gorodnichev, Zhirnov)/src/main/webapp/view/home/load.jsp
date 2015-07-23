<%--
  Created by IntelliJ IDEA.
  User: sergej
  Date: 21.07.15
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<rapid:override name="content">
  <div class="content-wrapper">
    <section class="content">
      <div class="row">
        <section class="col-lg-12">
          <form:form method="POST" action="/loadAction" class="form-horizontal"  enctype="multipart/form-data">
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="url" value="${url}">
            <div class="form-group">
              <label class="col-sm-1 control-label" for="file">Load file:</label>
              <div class="col-sm-11">
                <input type="file" name="file" id="file">
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-1 col-sm-11">
                <button type="submit" class="btn btn-default">Submit</button>
              </div>
            </div>
          </form:form>
        </section>
      </div>
    </section>
  </div>
</rapid:override>

<%@ include file="base.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: sergej
  Date: 22.07.15
  Time: 15:03
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
          <form:form method="POST" action="/addAction" class="form-horizontal"  commandName="store">
            <form:hidden path="parentId" value="${id}"></form:hidden>
            <input type="hidden" name="url" value="${url}">
            <div class="form-group">
              <form:label cssClass="col-sm-1 control-label" path="name">Name:</form:label>
              <div class="col-sm-11">
                <form:input path="name"></form:input>
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

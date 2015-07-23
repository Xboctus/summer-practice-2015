<%--
  Created by IntelliJ IDEA.
  User: sergej
  Date: 23.07.15
  Time: 21:57
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
          <h3>Registration</h3>
          <form:form method="POST" action="/registrationAction" class="form-horizontal"  commandName="user">
            <div class="form-group">
              <form:label cssClass="col-sm-1 control-label" path="login">Login:</form:label>
              <div class="col-sm-11">
                <form:input path="login"></form:input>
              </div>
            </div>
            <div class="form-group">
              <form:label cssClass="col-sm-1 control-label" path="password">Password:</form:label>
              <div class="col-sm-11">
                <form:password path="password"></form:password>
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

<%@ include file="../home/base.jsp"%>

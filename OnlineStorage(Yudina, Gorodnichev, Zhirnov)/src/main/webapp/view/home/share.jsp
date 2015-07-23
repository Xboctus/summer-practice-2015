<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<rapid:override name="content">
  <div class="content-wrapper">
    <section class="content">
      <div class="row">
        <section class="col-lg-12">
          <form:form method="POST" action="/shareAddAction" class="form-horizontal" commandName="cs" enctype="multipart/form-data">
            <input type="hidden" name="sid" value="${id}">
            <div class="form-group">
              <form:label path="userId" cssClass="col-sm-1 control-label">Add user to share:</form:label>
              <div class="col-sm-11">
                <form:input path="userId" cssClass="form-control"/>
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-1 col-sm-11">
                <button type="submit" class="btn btn-default">Submit</button>
              </div>
            </div>
          </form:form>
          <c:if test="${!empty users}">
            <h3>Shared to users</h3>
            <table class="table table-bordered table-striped">
              <thead>
              <tr>
                <th>#</th>
                <th>Login</th>
                <th>Options</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${users}" var="user">
                <tr>
                  <td>${user.id}</td>
                  <td>${user.login}</td>
                  <td>
                    <a name="button" class="btn btn-default btn-sm" href="/share/${id}/delete/${user.id}">Delete</a>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </c:if>
        </section>
      </div>
    </section>
  </div>
</rapid:override>

<%@ include file="base.jsp"%>
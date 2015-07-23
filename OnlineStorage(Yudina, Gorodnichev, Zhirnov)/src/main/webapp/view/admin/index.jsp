<%--
Created by IntelliJ IDEA.
User: sergej
Date: 16.07.15
Time: 14:05
To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<rapid:override name="content">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                Users
                <small>Control panel</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">Users</li>
            </ol>
        </section>

        <section class="content">
            <div class="row">
                <section class="col-lg-12 connectedSortable">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="ion ion-person-stalker"></i>
                            <h3 class="box-title">Users list</h3>
                        </div>
                        <div class="box-body">
                            <table class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Login</th>
                                        <th>Role</th>
                                        <th>Path</th>
                                        <th>Options</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${!empty users}">
                                        <c:forEach items="${users}" var="user">
                                            <tr>
                                                <td>${user.getId()}</td>
                                                <td>${user.getLogin()}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${user.getRole() == 0}"><c:out value="admin"></c:out></c:when>
                                                        <c:when test="${user.getRole() == 1}"><c:out value="user"></c:out></c:when>
                                                    </c:choose>
                                                </td>
                                                <td>${user.getPath()}</td>
                                                <td>
                                                    <a name="button" class="btn btn-default btn-sm" href="admin/delete/${user.getId()}">Delete</a>
                                                    <a name="button" class="btn btn-default btn-sm" href="admin/change/${user.getId()}">Change</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </section>
    </div>
</rapid:override>

<%@ include file="base.jsp"%>

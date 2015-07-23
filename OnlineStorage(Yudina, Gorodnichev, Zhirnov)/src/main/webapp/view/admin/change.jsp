<%--
  Created by IntelliJ IDEA.
  User: sergej
  Date: 20.07.15
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<rapid:override name="content">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                Change user ${user.getLogin()}
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
                            <form:form method="POST" action="/admin/changeAction" commandName="user" class="form-horizontal">
                                <form:hidden path="id" />
                                <div class="form-group">
                                    <form:label cssClass="col-sm-1 control-label" path="role">Role:</form:label>
                                    <div class="col-sm-11">
                                        <form:select path="role" cssClass="form-control">
                                            <form:option value="0" label="Administrator"></form:option>
                                            <form:option value="1" label="User"></form:option>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-1 col-sm-11">
                                        <button type="submit" class="btn btn-default">Submit</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </section>
            </div>
        </section>
    </div>
</rapid:override>

<%@ include file="base.jsp"%>

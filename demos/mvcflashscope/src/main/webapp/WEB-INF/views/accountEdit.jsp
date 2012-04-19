<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="<c:url value="/styles/springsource.css"/>" type="text/css"/>
    <title>Edit Account</title>
</head>


<body>
<div id="main_wrapper">

    <h1>Edit Account</h1>

    <form:form modelAttribute="account" method="post">
        <table>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>Number:</td>
                            <td><form:errors path="number" cssClass="errors"/>
                                <form:input path="number" size="20" maxlength="9"/></td>
                        </tr>
                        <tr>
                            <td>Name:</td>
                            <td><form:errors path="name" cssClass="errors"/>
                                <form:input path="name" size="20" maxlength="50"/></td>
                    </table>
                </td>
            </tr>
        </table>
        <p class="submit"><input type="submit" value="Save"/></p>
    </form:form>
</div>
</body>

</html>
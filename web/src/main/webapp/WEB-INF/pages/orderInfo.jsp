<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>

<header>

    <sec:authorize access="isAuthenticated()">
        <form method="post" action="<c:url value="/logout"/>">
            <input type="submit" class="btn btn-outline-danger" value="Logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </sec:authorize>
</header>

<body>

<form:form modelAttribute="orderModelInfo">
    <p>
    <div>
        <label>
            <input value="Delivery Cost : ${orderModelInfo.deliveryPrice}" readonly>
        </label>
        <label>
            <input value="Total Cost : ${orderModelInfo.totalPrice}" readonly>
        </label>
        <label>
            <input value="Subtotal Cost : ${orderModelInfo.subtotal}" readonly>
        </label>
    </div>

    <table class="table table-bordered">
        <thead class="thead-light">
        <tr>
            <th scope="col" style="width: 10rem;">Brand</th>
            <th scope="col" style="width: 10rem;">Model</th>
            <th scope="col" style="width: 10rem;">Color</th>
            <th scope="col" style="width: 20rem;">Display size</th>
            <th scope="col" style="width: 10rem;">Quantity</th>
            <th scope="col" style="width: 10rem;">Price</th>
            <th scope="col" style="width: 10rem;">Status</th>
        </tr>
        </thead>
        <c:forEach var="orderItem" items="${orderModelInfo.orderItems}">
            <tr class="prod">
                <td>
                        ${orderItem.phone.brand}
                </td>
                <td>${orderItem.phone.model}</td>
                <td>
                    <c:forEach var="colorItem" items="${orderItem.phone.colors}">
                        ${colorItem.code}
                    </c:forEach>
                </td>
                <td>${orderItem.phone.displaySizeInches}</td>
                <td>${orderItem.quantity}</td>
                <td>${orderItem.phone.price}</td>
                <td>${orderModelInfo.status}</td>
            </tr>
        </c:forEach>
    </table>

    <br>

    <div style="margin-left: 20%; margin-bottom: 3%;">
        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
                <th scope="col">First name</th>
                <th scope="col">Last Name</th>
                <th scope="col">Address</th>
                <th scope="col">Phone</th>
                <th scope="col">Other info</th>
            </tr>
            </thead>
            <td>
                    ${orderModelInfo.firstName}
            </td>
            <td>
                    ${orderModelInfo.lastName}
            </td>
            <td>
                    ${orderModelInfo.deliveryAddress}
            </td>
            <td>
                    ${orderModelInfo.contactPhoneNo}
            </td>
            <td>
                    ${orderModelInfo.otherInfo}
            </td>
            </tr>
        </table>
    </div>

</form:form>

<form method="post" action="${pageContext.servletContext.contextPath}/admin/orders/${orderModelInfo.number}">
    <input type="submit" name="status" value="DELIVERED">
    <input type="submit" name="status" value="REJECTED">
    <input type="submit" name="status" value="NEW">
</form>
</p>
</body>
</html>

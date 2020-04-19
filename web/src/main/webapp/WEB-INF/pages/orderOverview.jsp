<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
<p>
    <tr>
        <a href="${pageContext.servletContext.contextPath}/productList">Back To The Main Page</a>
    </tr>
</p>

<form method="get" action="${pageContext.servletContext.contextPath}/admin/orders?pageForOrder=1">
    <input type="submit" value="Admin Page">
</form>

<form:form modelAttribute="orderModel">
    <p> Congratulations! Your order is :</p>
    <p>
    <div>
        <label>
            <input value="Delivery Cost : ${orderModel.deliveryPrice}" readonly>
        </label>
        <label>
            <input value="Total Cost : ${orderModel.totalPrice}" readonly>
        </label>
        <label>
            <input value="Subtotal Cost : ${orderModel.subtotal}" readonly>
        </label>
    </div>

    <table class="table table-bordered">
        <thead class="thead-light">
        <tr>
            <th scope="col" style="width: 10rem;">Image</th>
            <th scope="col" style="width: 10rem;">Description</th>
            <th scope="col" style="width: 10rem;">Price</th>
            <th scope="col" style="width: 20rem;">Quantity</th>
        </tr>
        </thead>

        <c:forEach var="orderItem" items="${orderModel.orderItems}">
            <tr class="prod">
                <td>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${orderItem.phone.imageUrl}">
                </td>
                <td>
                    <a>${orderItem.phone.description}</a>
                </td>
                <td class="price">
                    <input value="${orderItem.phone.price}"/>
                </td>
                <td>
                    <label>
                        <input name="quantity" value="${orderItem.quantity}" readonly>
                    </label>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
</form:form>
<p>Customer info:</p>
<form:form modelAttribute="orderModel">
    <table class="table table-bordered">
        <thead class="thead-light">
        <tr>
            <th scope="col" style="width: 10rem;">Name</th>
            <th scope="col" style="width: 10rem;">Surname</th>
            <th scope="col" style="width: 10rem;">Address</th>
            <th scope="col" style="width: 20rem;">Phone</th>
            <th scope="col">Other info</th>
        </tr>
        <tr>
            <td>
                <form:input path="firstName" cssClass="text-input" readonly="true"/>
                <div style="color: red; font-size: small">
                    <form:errors cssClass="errors" path="firstName"/>
                </div>
            </td>
            <td>
                <form:input path="lastName" cssClass="text-input"/>
                <div style="color: red; font-size: small">
                    <form:errors cssClass="errors" path="lastName"/>
                </div>
            </td>
            <td>
                <form:input path="deliveryAddress" cssClass="text-input" readonly="true"/>
                <div style="color: red; font-size: small">
                    <form:errors cssClass="errors" path="deliveryAddress"/>
                </div>
            </td>
            <td>
                <form:input path="contactPhoneNo" cssClass="text-input" readonly="true"/>
                <div style="color: red; font-size: small">
                    <form:errors cssClass="errors" path="contactPhoneNo"/>
                </div>
            </td>
            <td>
                <form:input path="otherInfo" cssClass="text-input" readonly="true"/>
                <div style="color: red; font-size: small">
                    <form:errors cssClass="errors" path="otherInfo"/>
                </div>
            </td>
        </tr>
        </thead>
    </table>

</form:form>
</body>
</html>

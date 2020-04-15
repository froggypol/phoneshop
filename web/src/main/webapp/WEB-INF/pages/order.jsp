<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>

<header>
    <form class="cartInfo" action="${pageContext.servletContext.contextPath}/cart">
        <input type="submit" class="btn btn-outline-danger" id="cartQuantity"
               value="Total quantity : ${cart.totalQuantity} items">
    </form>
    <form class="cartInfo" action="${pageContext.servletContext.contextPath}/cart">
        <input type="submit" class="btn btn-outline-danger" id="cartValue" value="Total cost : ${cart.totalCost} $">
    </form>
</header>

<body>

<form method="get" action="${pageContext.servletContext.contextPath}/cart" class="btn btn-outline-success">
    <input type="submit" value="Back to cart">
</form>

<form method="get" action="${pageContext.servletContext.contextPath}/admin/orders?pageForOrder=1">
    <input type="submit" value="Admin Page">
</form>

<table class="table table-bordered">
    <thead class="thead-light">
    <tr>
        <th scope="col" style="width: 10rem;">Brand</th>
        <th scope="col" style="width: 10rem;">Model</th>
        <th scope="col" style="width: 10rem;">Price</th>
        <th scope="col" style="width: 20rem;">Display Size Inches</th>
        <th scope="col">Color</th>
        <th scope="col">Description</th>
        <th scope="col">Quantity</th>
    </tr>
    </thead>

    <form:form modelAttribute="orderModel" method="get">
        <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
            <input style="visibility: hidden" name="orderItems[status.index].phone" value="${cartItem.getPhone()}">
            <tr class="productList">
                <td>${cartItem.getPhone().getBrand()}</td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/productDetailsPage/productId=${cartItem.getPhone().getId()}">${cartItem.getPhone().getModel()}</a>
                    <input type="hidden" name="phoneId" value="${cartItem.getPhone().getId()}">
                </td>
                <td>$ ${cartItem.getPhone().getPrice()}</td>
                <td>${cartItem.getPhone().getDisplaySizeInches()}</td>
                <td>
                    <c:forEach items="${cartItem.getPhone().colors}" var="colorItem">
                        <p>
                                ${colorItem.code}
                        </p>
                    </c:forEach>
                </td>
                <td>${cartItem.getPhone().getDescription()}</td>
                <td>
                    <input type="text" name="quantity" id="${cartItem.getPhone().getId()}"
                           value="${cartItem.getQuantity()}">
                    <div style="color: red; font-size: small">
                        <c:forEach var="errorItem" items="${errors}">
                            <c:if test="${errorItem.code.equals(cartItem.getPhone().getId().toString())}">
                                <c:out value="${errorItem.defaultMessage}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </td>
                </div>
            </tr>

        </c:forEach>
    </form:form>
</table>

<form:form modelAttribute="orderForm" method="post">
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
                <form:input path="firstName" cssClass="text-input"/>
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
                <form:input path="deliveryAddress" cssClass="text-input"/>
                <div style="color: red; font-size: small">
                    <form:errors cssClass="errors" path="deliveryAddress"/>
                </div>
            </td>
            <td>
                <form:input path="contactPhoneNo" cssClass="text-input"/>
                <div style="color: red; font-size: small">
                    <form:errors cssClass="errors" path="contactPhoneNo"/>
                </div>
            </td>
            <td>
                <form:input path="otherInfo" cssClass="text-input"/>
                <div style="color: red; font-size: small">
                    <form:errors cssClass="errors" path="otherInfo"/>
                </div>
            </td>
        </tr>
        </thead>
        <input formmethod="post" type="submit" value="Place order">
    </table>

</form:form>

<form:form modelAttribute="orderModel">
    <input name="firstName" value="${orderForm.firstName}" hidden>
    <input name="lastName" value="${orderForm.lastName}" hidden>
    <input name="deliveryAddress" value="${orderForm.deliveryAddress}" hidden>
    <input name="contactPhoneNo" value="${orderForm.contactPhoneNo}" hidden>
    <input name="otherInfo" value="${orderForm.otherInfo}" hidden>
    <c:forEach var="cartItem" items="${cart.cartItems}">
        <c:set var="orderItem" value="${cartItem.phone}, ${cartItem.quantity}"/>
        ${orderModel.orderItems.add(orderItem)}
    </c:forEach>
</form:form>
</body>
</html>

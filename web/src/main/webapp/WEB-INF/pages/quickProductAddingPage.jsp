<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>

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
    <form method="get" action="${pageContext.servletContext.contextPath}/productList">
        <input type="submit" class="btn btn-outline-success" value="Back to PLP">
    </form>
    <div style="color: red; font-size: small">
        <c:if test="${errors!=null}">
            <a>There were mistakes</a>
        </c:if>
    </div>
</header>

<body>

<table class="table table-bordered">

    <spring:form modelAttribute="productDTO" method="post" action="${pageContext.servletContext.contextPath}/quickAdding/add">
        <input type="submit" class="btn btn-outline-danger" value="Add">
        <c:forEach begin="0" end="${itemsNumber}" var="cartItem" varStatus="status">
            <c:set var="i" value="${status.index}"/>
            <tr class="productList">
                <td>
                    <form:input path="productInfoDTOs[${i}].modelToAdd"/>
                    <div style="color: red; font-size: small">
                        <form:errors cssClass="error" path="productInfoDTOs[${i}].modelToAdd"/>
                    </div>
                </td>
                <td>
                    <form:input path="productInfoDTOs[${i}].quantityToAdd"/>
                    <form:input style="visibility: hidden" path="productInfoDTOs[${i}].productId"/>
                    <c:if test="${cart.cartItems.indexOf(cartItem) != -1}">
                        <div style="color: red; font-size: small">
                            <form:errors cssClass="error" path="productInfoDTOs[${i}].quantityToAdd"/>
                        </div>
                    </c:if>
                    <c:if test="${cart.cartItems.indexOf(cartItem) == -1}">
                        <div style="color: red; font-size: small">
                            <form:errors cssClass="error" path="productInfoDTOs[${i}].quantityToAdd"/>
                        </div>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </spring:form>
</table>
</body>
</html>
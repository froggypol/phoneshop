<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="FORM" uri="http://www.springframework.org/tags/form" %>

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

    <form:form modelAttribute="productDTO" method="post"
               action="${pageContext.servletContext.contextPath}/quickAdding/add">
        <input type="submit" class="btn btn-outline-daanger" value="Add">
        <c:forEach begin="0" end="${itemsNumber}" var="cartItem" varStatus="status">
            <tr class="productList">
                <td>
                    <input name="productInfoDTOs[${status.index}].modelToAdd"
                           value="${productDTO.productInfoDTOs[status.index].modelToAdd}"/>
                    <div style="color: red; font-size: small">
                        <form:errors cssClass="error" path="productInfoDTOs[${status.index}].modelToAdd"/>
                    </div>
                </td>
                <td>

                    <input name="productInfoDTOs[${status.index}].quantityToAdd"
                           value="${productDTO.productInfoDTOs[status.index].quantityToAdd}"/>
                    <c:if test="${cart.cartItems.indexOf(cartItem) != -1}">
                        <input style="visibility: hidden" name="productInfoDTOs[${status.index}].productId"
                               value="${cart.cartItems.get(cart.cartItems.indexOf(cartItem)).phone.id}"/>
                        <div style="color: red; font-size: small">
                            <form:errors cssClass="error" path="productInfoDTOs[${status.index}].quantityToAdd"/>
                        </div>
                    </c:if>
                    <c:if test="${cart.cartItems.indexOf(cartItem) == -1}">
                        <input style="visibility: hidden" name="productInfoDTOs[${status.index}].productId" value=" "/>
                        <div style="color: red; font-size: small">
                            <form:errors cssClass="error" path="productInfoDTOs[${status.index}].quantityToAdd"/>
                        </div>
                    </c:if>

                </td>
            </tr>
        </c:forEach>
    </form:form>
</table>
</p>

</body>
</html>
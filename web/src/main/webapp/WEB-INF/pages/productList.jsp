<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>

<header>
    <form class="cartInfo">
        PhoneShop cart:
        <p>
            <label style="color: coral;" href="${pageContext.servletContext.contextPath}/cart" class="totalQuantity">
                Total quantity : ${cart.totalQuantity} items </label>
        </p>
        <p>
            <label style="color: coral;" href="${pageContext.servletContext.contextPath}/cart" class="totalCost"> Total
                cost : ${cart.totalCost} $</label>
        </p>
    </form>
</header>

<p>
    Hello from product list!
</p>
<p>
    Found
    <c:out value="${phones.size()}"/> phones.
    <thead>
    <body>
    <table border="1px" class="product">
        <thead>
<p>
<form method="get"
      href="/phoneshop_web_war_exploded/productList?query=${param.query}&order=desc&fieldToSort=model&page=${page}">
    <input name="query" value="${param.query}"/>
    <button>Search</button>
    <input type="hidden" name="order" value="desc">
    <input type="hidden" name="fieldToSort" value="displaySizeInches">
    <input type="hidden" name="page" value="${page}">
</form>
</p>
<tr>
    <td>Image</td>
    <td>Brand
        <form method="get">
            <button value="asc">asc</button>
            <input type="hidden" name="query" value="${param.query}">
            <input type="hidden" name="order" value="asc">
            <input type="hidden" name="fieldToSort" value="brand">
            <input type="hidden" name="page" value="${page}">
        </form>
        <form method="get">
            <button value="desc">desc</button>
            <input type="hidden" name="query" value="${param.query}">
            <input type="hidden" name="order" value="desc">
            <input type="hidden" name="fieldToSort" value="brand">
            <input type="hidden" name="page" value="${page}">
        </form>
    </td>
    <td>Model
        <form method="get">
            <button value="asc">asc</button>
            <input type="hidden" name="query" value="${param.query}">
            <input type="hidden" name="order" value="asc">
            <input type="hidden" name="fieldToSort" value="model">
            <input type="hidden" name="page" value="${page}">
        </form>
        <form method="get">
            <button value="desc">desc</button>
            <input type="hidden" name="query" value="${param.query}">
            <input type="hidden" name="order" value="desc">
            <input type="hidden" name="fieldToSort" value="model">
            <input type="hidden" name="page" value="${page}">
        </form>
    </td>
    <td>Price
        <form method="get">
            <button value="asc">asc</button>
            <input type="hidden" name="query" value="${param.query}">
            <input type="hidden" name="order" value="asc">
            <input type="hidden" name="fieldToSort" value="price">
            <input type="hidden" name="page" value="${page}">
        </form>
        <form method="get">
            <button value="desc">desc</button>
            <input type="hidden" name="query" value="${param.query}">
            <input type="hidden" name="order" value="desc">
            <input type="hidden" name="fieldToSort" value="price">
            <input type="hidden" name="page" value="${page}">
        </form>
    </td>
    <td>Display Size Inches
        <form method="get">
            <button value="asc">asc</button>
            <input type="hidden" name="query" value="${param.query}">
            <input type="hidden" name="order" value="asc">
            <input type="hidden" name="fieldToSort" value="displaySizeInches">
            <input type="hidden" name="page" value="${page}">
        </form>
        <form method="get">
            <button value="desc">desc</button>
            <input type="hidden" name="query" value="${param.query}">
            <input type="hidden" name="order" value="desc">
            <input type="hidden" name="fieldToSort" value="displaySizeInches">
            <input type="hidden" name="page" value="${page}">
        </form>
    </td>
    <td>Color</td>
    <td>Description</td>
    <td>Quantity</td>
    <td>Action</td>
</tr>
</thead>

<c:forEach var="phoneItem" items="${phones}">
    <tr class="productList">
        <td>
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phoneItem.imageUrl}">
        </td>
        <td>${phoneItem.brand}</td>
        <td>${phoneItem.model}</td>
        <td>$ ${phoneItem.price}</td>
        <td>${phoneItem.displaySizeInches}</td>
        <td>
            <c:forEach items="${phoneItem.colors}" var="colorItem">
                <p>
                        ${colorItem.code}
                </p>
            </c:forEach>
        </td>
        <td>${phoneItem.description}</td>
        <td>
            <input type="text" name="quantity" id="${phoneItem.id}">
        </td>
        <td>
            <input type="submit" onclick="addPhoneViaAjax(${phoneItem.id},  $('#${phoneItem.id}').val())" value="Add"
                   class="add"/>
            <div>
                <a readonly name="error-message" class="error-message&${phoneItem.id}" style="color: crimson"/>
            </div>
        </td>
    </tr>
</c:forEach>
</table>
</p>

<nav aria-label="Page navigation example">
    <ul class="pagination">
        <li class="page-item">
            <form style="display: inline-block"
                  href="/phoneshop_web_war_exploded/productList?query=${param.query}&order=${param.order}&fieldToSort=${param.fieldToSort}&page=${param.page}">
                <button class="page-link"> Prev</button>
                <input type="hidden" name="query" value="${param.query}">
                <input type="hidden" name="order" value="${param.order}">
                <input type="hidden" name="fieldToSort" value="${param.fieldToSort}">
                <input type="hidden" name="page" value="${page-1}">
            </form>
        </li>
        <c:if test="${(page + 9) < (maxPages-1)}">
            <c:forEach begin="${page-1}" end="${page+9}" varStatus="loop">
                <li class="page-item">
                    <form style="display: inline-block"
                          href="/phoneshop_web_war_exploded/productList?query=${param.query}&order=${param.order}&fieldToSort=${param.fieldToSort}&page=${param.page}">
                        <button class="page-link">${loop.index+1}
                            <input type="hidden" name="query" value="${param.query}">
                            <input type="hidden" name="order" value="${param.order}">
                            <input type="hidden" name="fieldToSort" value="${param.fieldToSort}">
                            <input type="hidden" name="page" value="${loop.index+1}">
                        </button>
                    </form>
                </li>
            </c:forEach>
        </c:if>
        <c:if test="${(page + 9) > (maxPages - 1)}">
            <c:forEach begin="${page}" end="${(maxPages - 1)}" varStatus="loop">
                <li class="page-item">
                    <form href="/phoneshop_web_war_exploded/productList?query=${param.query}&order=${param.order}&fieldToSort=${param.fieldToSort}&page=${param.page}">
                        <button class="page-link">${loop.index+1}
                            <input type="hidden" name="query" value="${param.query}">
                            <input type="hidden" name="order" value="${param.order}">
                            <input type="hidden" name="fieldToSort" value="${param.fieldToSort}">
                            <input type="hidden" name="page" value="${loop.index+1}">
                        </button>
                    </form>
                </li>
            </c:forEach>
        </c:if>
        <li class="page-item">
            <form class="page-information"
                  href="/phoneshop_web_war_exploded/productList?query=${param.query}&order=${param.order}&fieldToSort=${param.fieldToSort}&page=${param.page}">
                <button class="page-link"> Next</button>
                <input type="hidden" name="query" value="${param.query}">
                <input type="hidden" name="order" value="${param.order}">
                <input type="hidden" name="fieldToSort" value="${param.fieldToSort}">
                <input type="hidden" name="page" value="${page + 1}">
            </form>
        </li>
    </ul>
</nav>
</body>
</html>

<script>
    function addPhoneViaAjax(phoneId, quantity, errorMsg) {
        var data = {}
        data["totalCost"] = ${sessionScope.get("cart").totalCost};
        data["totalQuantity"] = ${sessionScope.get("cart").totalQuantity};
        data["quantityToAdd"] = quantity;
        data["productId"] = phoneId;
        data["msg"] = errorMsg;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "${pageContext.servletContext.contextPath}/ajaxCart",
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (data) {
                var allMessages = document.getElementsByName('error-message');
                allMessages.forEach(function (messageItem) {
                    messageItem.innerHTML = "";
                })
                display(data);
            },
            error: function (res) {
                var tbody = document.getElementsByClassName('error-message&' + phoneId)[0];
                if (res.responseText === "")
                    res.responseText = "No letters in quantity are allowed";
                tbody.innerHTML = res.responseText;
                document.createElement('div').innerHTML = tbody.innerHTML;
            }
        });
    }

    function display(data) {
        $(".totalCost").text("Total cost : " + data.totalCost + " $ ");
        $(".totalQuantity").text("Total quantity " + data.totalQuantity + " items ");
    }
</script>

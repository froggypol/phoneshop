<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<header>
    <div>
        <label>
            <input type="text" value="My cart : 0 items 0$">
            <a href="${pageContext.servletContext.contextPath}/cart">
                PhoneShop cart:
                <jsp:include page="minicart.jsp"></jsp:include>
            </a>
        </label>
    </div>
</header>

<p>
    Hello from product list!
</p>
<p>
    Found
    <c:out value="${phones.pageList.size()}"/> phones.
    <thead>
    <body>
    <table border="1px">
        <thead>
<p>
<form method="get" action="/phoneshop_web_war_exploded/productList/${param.query}">
    <input name="query" value="${param.query}"/>
    <button>Search</button>
</form>
</p>
<tr>
    <td>Image</td>
    <td>Brand
        <form method="get" action="/phoneshop_web_war_exploded/productList">
            <button value="asc">asc</button>
            <input type="hidden" name="order" value="asc">
            <input type="hidden" name="fieldToSort" value="brand">
        </form>
        <form method="get" action="/phoneshop_web_war_exploded/productList">
            <button value="desc">desc</button>
            <input type="hidden" name="order" value="desc">
            <input type="hidden" name="fieldToSort" value="brand">
        </form>
    </td>
    <td>Model
        <form method="get" action="/phoneshop_web_war_exploded/productList">
            <button value="asc">asc</button>
            <input type="hidden" name="order" value="asc">
            <input type="hidden" name="fieldToSort" value="model">
        </form>
        <form method="get" action="/phoneshop_web_war_exploded/productList">
            <button value="desc">desc</button>
            <input type="hidden" name="order" value="desc">
            <input type="hidden" name="fieldToSort" value="model">
        </form>
    </td>
    <td>Price
        <form method="get" action="/phoneshop_web_war_exploded/productList">
            <button value="asc">asc</button>
            <input type="hidden" name="order" value="asc">
            <input type="hidden" name="fieldToSort" value="price">
        </form>
        <form method="get" action="/phoneshop_web_war_exploded/productList">
            <button value="desc">desc</button>
            <input type="hidden" name="order" value="desc">
            <input type="hidden" name="fieldToSort" value="price">
        </form>
    </td>
    <td>Display Size Inches
        <form method="get" action="/phoneshop_web_war_exploded/productList">
            <button value="asc">asc</button>
            <input type="hidden" name="order" value="asc">
            <input type="hidden" name="fieldToSort" value="size">
        </form>
        <form method="get" action="/phoneshop_web_war_exploded/productList">
            <button value="desc">desc</button>
            <input type="hidden" name="order" value="desc">
            <input type="hidden" name="fieldToSort" value="size">
        </form>
    </td>
    <td>Color</td>
    <td>Description</td>
    <td>Quantity</td>
    <td>Action</td>
</tr>
</thead>

<c:set var="pageListHolder" value="${phones}" scope="session"/>

<c:forEach var="phoneItem" items="${pageListHolder.pageList}">
    <tr>
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
        <td><input value="${param.query}"></td>
        <td>
            <form method="get" action="/phoneshop_web_war_exploded/productList/add">
                <input name="quantity" value="${param.query}"/>
                <button>Add</button>
                <input type="hidden" name="productId" value="${phoneItem.id}">
            </form>
        </td>
    </tr>
</c:forEach>
</table>
</p>

<span style="float:left;">
<div>
    <c:choose>
        <c:when test="${pageListHolder.firstPage}"> Prev </c:when>
        <c:otherwise>
            <form style="display: inline-block">
            <button href="/phoneshop_web_war_exploded/productList"> Prev </button>
            <input type="hidden" name="page" value="${page-1}">
     </form>
        </c:otherwise>
    </c:choose>

</span>
<span>
    <c:forEach begin="0" end="${pageListHolder.pageCount-1}" varStatus="loop">
        <c:choose>
            <c:when test="${loop.index == pageListHolder.page}">
                <form style="display: inline-block">
                    <button href="/phoneshop_web_war_exploded/productList" style="background-color: crimson" >${loop.index+1}
                    <input type="hidden" name="page" value="${loop.index+1}">
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <form style="display: inline-block">
                <button href="/phoneshop_web_war_exploded/productList">${loop.index+1}
                <input type="hidden" name="page" value="${loop.index+1}">
                </button>
                </form>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </span>
<span>
    <c:choose>
        <c:when test="${pageListHolder.lastPage}"> Next </c:when>
        <c:otherwise>
            <form style="display: inline-block">
            <button href="/phoneshop_web_war_exploded/productList"> Next </button>
            <input type="hidden" name="page" value="${page + 1}">
                </form>
        </c:otherwise>
    </c:choose>
    </span>
</form>
</body>
</html>

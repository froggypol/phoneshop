<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <c:out value="${phones.size()}"/> phones.
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
        <form method="get" action="/phoneshop_web_war_exploded/productList/${param.query}&brand&asc">
            <button value="asc">asc</button>
        </form>
        <form method="get" action="/phoneshop_web_war_exploded/productList/${param.query}&brand&desc">
            <button value="desc">desc</button>
        </form>
    </td>
    <td>Model
        <form method="get" action="/phoneshop_web_war_exploded/productList/${param.query}&model&asc">
            <button value="asc">asc</button>
        </form>
        <form method="get" action="/phoneshop_web_war_exploded/productList/${param.query}&model&desc">
            <button value="desc">desc</button>
        </form>
    </td>
    <td>Price
        <form method="get" action="/phoneshop_web_war_exploded/productList/${param.query}&price&asc">
            <button value="asc">asc</button>
        </form>
        <form method="get" action="/phoneshop_web_war_exploded/productList/${param.query}&price&desc">
            <button value="desc">desc</button>
        </form>
    </td>
    <td>Display Size Inches
        <form method="get" action="/phoneshop_web_war_exploded/productList/${param.query}&size&asc">
            <button value="asc">asc</button>
        </form>
        <form method="get" action="/phoneshop_web_war_exploded/productList/${param.query}&size&desc">
            <button value="desc">desc</button>
        </form>
    </td>
    <td>Color</td>
    <td>Description</td>
    <td>Quantity</td>
    <td>Action</td>
</tr>
</thead>
<c:forEach var="phone" items="${phones}">
    <tr>
        <td>
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
        </td>
        <td>${phone.brand}</td>
        <td>${phone.model}</td>
        <td>$ ${phone.price}</td>
        <td>${phone.displaySizeInches}</td>
        <td>
            <c:forEach items="${phone.colors}" var="colorItem">
                <p>
                        ${colorItem.code}
                </p>
            </c:forEach>
        </td>
        <td>${phone.description}</td>
        <td><input value="${param.query}"></td>
        <td>
            <form method="get">
                <input href="productList/add">Add</input>
            </form>
        </td>
    </tr>
</c:forEach>
</table>
</p>

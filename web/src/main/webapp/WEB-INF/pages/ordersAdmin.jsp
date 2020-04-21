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
    <form method="post" class="cartInfo" action="<c:url value="/logout"/>">
        <input type="submit" class="btn btn-outline-danger"  value="Logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
    </sec:authorize>
</header>

<c:if test="${orderList.size()!=0}">
<p>
    Found
    <c:out value="${orderList.size()}"/> orders.
</p>
    <body>

    <form:form modelAttribute="orderList">
        <c:forEach var="orderItemModel" items="${orderList}">
        <p>

        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
                <th scope="col" style="width: 10rem;">Order number</th>
                <th scope="col" style="width: 10rem;">Customer</th>
                <th scope="col" style="width: 10rem;">Phone</th>
                <th scope="col" style="width: 20rem;">Address</th>
                <th scope="col" style="width: 10rem;">Date</th>
                <th scope="col" style="width: 10rem;">Total price</th>
                <th scope="col" style="width: 10rem;">Status</th>
            </tr>
            </thead>

                <tr class="prod">
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/admin/orders/${orderItemModel.number}">${orderItemModel.number}</a>
                    </td>
                    <td>${orderItemModel.firstName} ${orderItemModel.lastName}</td>
                    <td>${orderItemModel.contactPhoneNo}</td>
                    <td>${orderItemModel.deliveryAddress}</td>
                    <td>date</td>
                    <td>${orderItemModel.totalPrice}</td>
                    <td>${orderItemModel.status}</td>
                </tr>

        </table>
        <br>
        </c:forEach>
    </form:form>
</p>

<nav aria-label="page navigation example">
    <ul class="pagination">
        <li class="page-item">
            <form style="display: inline-block"
                  href="/phoneshop_web_war_exploded/admin/orders?pageForOrder=${param.pageForOrder}">
                <button class="page-link"> Prev</button>
                <input type="hidden" name="pageForOrder" value="${pageForOrder-1}">
            </form>
        </li>
        <c:if test="${(pageForOrder + 9) < (maxPagesForOrder - 1)}">
            <c:forEach begin="${pageForOrder-1}" end="${pageForOrder+9}" varStatus="loop">
                <li class="page-item">
                    <form style="display: inline-block"
                          href="/phoneshop_web_war_exploded/admin/orders?pageForOrder=${param.pageForOrder}">
                        <button class="page-link">${loop.index+1}
                            <input type="hidden" name="pageForOrder" value="${loop.index+1}">
                        </button>
                    </form>
                </li>
            </c:forEach>
        </c:if>
        <c:if test="${(pageForOrder + 9) > (maxPagesForOrder - 1)}">
            <c:forEach begin="${pageForOrder}" end="${(maxPagesForOrder - 1)}" varStatus="loop">
                <li class="page-item">
                    <form href="/phoneshop_web_war_exploded/admin/orders?pageForOrder=${param.pageForOrder}">
                        <button class="page-link">${loop.index+1}
                            <input type="hidden" name="pageForOrder" value="${loop.index+1}">
                        </button>
                    </form>
                </li>
            </c:forEach>
        </c:if>
        <li class="page-item">
            <form class="page-information"
                  href="/phoneshop_web_war_exploded/admin/orders?pageForOrder=${param.pageForOrder}">
                <button class="page-link"> Next</button>
                <input type="hidden" name="pageForOrder" value="${pageForOrder + 1}">
            </form>
        </li>
    </ul>
</nav>
    </c:if>
    <c:if test="${orderList.size() == 0}">
        <c:out value="Empty order list"/>
        <a href="${pageContext.servletContext.contextPath}/productList">Back</a>
    </c:if>
</body>
</html>

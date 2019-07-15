<hr width="3px">
Debug Information:<br>
<table border="1" width="50%" class="debug">
    <tr>
        <th colspan="3" style="background:orange">
            <b>Request Parameters</b>
        </th>
    </tr>
    <c:forEach items="${paramValues}" var="parameter">
        <tr> 
            <td><c:out value="${parameter.key}"/></td>
            <td colspan="2">
                <c:forEach var="value" items="${parameter.value}">
                    <textarea rows="2" cols="50">
                        <c:out value="${value}"/>
                    </textarea>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>

    <tr>
        <th colspan="3" style="background:orange">
            <b>Header Values</b>
        </th>
    </tr>
    <c:forEach items="${header}" var="h">
        <tr>
            <td><c:out value="${h.key}"/></td>
            <td colspan="2">
                <textarea rows="2" cols="50">
                    <c:out value="${h.value}"/>
                </textarea>
            </td>
        </tr>
    </c:forEach>

    <tr>
        <th colspan="3" style="background:orange">
            <b>Initialization Parameters</b>
        </th>
    </tr>
    <c:forEach items="${initParam}" var="parameter">
        <tr>
            <td><c:out value="${parameter.key}"/></td>
            <td colspan="2">
                <textarea rows="2" cols="50">
                    <c:out value="${parameter.value}"/>
                </textarea>
            </td>
        </tr>
    </c:forEach>

    <tr>
        <th colspan="3" style="background:orange">
            <b>Cookies</b>
        </th>
    </tr>
    <c:forEach items="${cookie}" var="mapEntry">
        <tr>
            <td rowspan="8"><c:out value="${mapEntry.key}"/></td>
            <td align="right">Name:</td>
            <td><c:out value="${mapEntry.value.name}"/></td>
        </tr>
        <tr>
            <td align="right">Value:</td>
            <td><c:out value="${mapEntry.value.value}"/></td>
        </tr>
        <tr>
            <td align="right">Domain:</td>
            <td><c:out value="${mapEntry.value.domain}"/></td>
        </tr>
        <tr>
            <td align="right">Max Age:</td>
            <td><c:out value="${mapEntry.value.maxAge}"/></td>
        </tr>
        <tr>
            <td align="right">Path:</td>
            <td><c:out value="${mapEntry.value.path}"/></td>
        </tr>
        <tr>
            <td align="right">Secure:</td>
            <td><c:out value="${mapEntry.value.secure}"/></td>
        </tr>
        <tr>
            <td align="right">Version:</td>
            <td><c:out value="${mapEntry.value.version}"/></td>
        </tr>
        <tr>
            <td align="right">Comment:</td>
            <td><c:out value="${mapEntry.value.comment}"/></td>
        </tr>
    </c:forEach>

    <tr>
        <th colspan="3" style="background:orange">
            <b>Page Scope Attributes</b>
        </th>
    </tr>
    <c:forEach items="${pageScope}" var="itm">
        <c:if test="${itm.key != 'javax.servlet.jsp.jspResponse'}">
            <tr>
                <td><c:out value="${itm.key}"/></td>
                <td colspan="2">
                    <textarea rows="2" cols="50">
                        <c:out value="${itm.value}"/>
                    </textarea>
                </td>
            </tr>
        </c:if>
    </c:forEach>

    <tr>
        <th colspan="3" style="background:orange">
            <b>Request Scope Attributes</b>
        </th>
    </tr>
    <c:forEach items="${requestScope}" var="itm">
        <tr>
            <td><c:out value="${itm.key}"/></td>
            <td colspan="2">
                <textarea rows="2" cols="50">
                    <c:out value="${itm.value}"/>
                </textarea>
            </td>
        </tr>
    </c:forEach>

    <tr>
        <th colspan="3" style="background:orange">
            <b>Session Scoped Attributes</b>
        </th>
    </tr>
    <c:forEach items="${sessionScope}" var="itm">
      <tr>
        <td><c:out value="${itm.key}"/></td>
        <td colspan="2">
            <textarea rows="2" cols="50">
                <c:out value="${itm.value}"/>
            </textarea>
        </td>
      </tr>
    </c:forEach>

    <tr>
        <th colspan="3" style="background:orange">
            <b>Application Scope Attributes</b>
        </th>
    </tr>
    <c:forEach items="${applicationScope}" var="itm">
      <tr>
        <td><c:out value="${itm.key}"/></td>
        <td colspan="2">
            <c:choose>
                <c:when test="${itm.key eq 'org.apache.struts.action.
                                   PLUG_INS'}">
                     <c:forEach items="${itm.value}" var="subitm">
                         <textarea rows="2" cols="50">
                             <c:out value="${subitm}"/>
                         </textarea>
                    </c:forEach>                    
                </c:when>
                <c:otherwise>
                    <textarea rows="2" cols="50">
                        <c:out value="${itm.value}"/>
                    </textarea>                    
                </c:otherwise>
            </c:choose>            
        </td>
      </tr>
    </c:forEach>
</table>

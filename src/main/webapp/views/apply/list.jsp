<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${general}">
	<spring:message code="apply.searchText"/>
	<input type="text" value="" id="textSearch" />
	<input type="button" id="buttonSearch"
	value="<spring:message code="apply.search"/>" />
	
	<script type="text/javascript">
		$(document).ready(function(){
			var urlSearch = 'apply/customer/search.do?keyword=';
			$("#buttonSearch").click(function(){
				window.location.replace(urlSearch + $("#textSearch").val());
			});
			$("#buttonSearch").onsubmit(function(){
				window.location.replace(urlSearch + $("#textSearch").val());
			});
		});
	</script>
</jstl:if>
<display:table name="applies" id="row" requestURI="${requestURI }" class="displaytag">
	
	<acme:column code="apply.status" property="status"/>
	<acme:column code="apply.customer" property="customer"/>
	<acme:column code="apply.transaction" property="transaction"/>
	
	
</display:table>


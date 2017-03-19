<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI }" modelAttribute="${actorForm }">
	
	<fieldset>
		<legend>
			<spring:message code="customer.user" />
		</legend>
		<acme:input path="username" code="customer.username" />

		<acme:password path="password" code="customer.password" />
		
		<acme:password path="confirmPassword" code="customer.confirmPassword" />
		<br />
	</fieldset>
		
	<fieldset>
		<legend>
			<spring:message code="customer.personalData" />
		</legend>
		<acme:input path="name" code="customer.name" />
		<br />

		<acme:input path="email" code="customer.email" />
		<br />
		
		<acme:input path="phoneNumber" code="customer.phoneNumber" />
		<br />
		
	</fieldset>
	<br>
		
	<form:checkbox path="isAgree"/>
	<form:label path="isAgree">
		<spring:message code="customer.acceptConditions" />
		<a href="misc/conditions.do" target="_blank"><spring:message code="customer.conditions" /></a>
	</form:label>
	<br />
	
	<acme:submit name="save" code="customer.save" />
	<acme:cancel url="welcome/index.do" code="customer.cancel" />
		
</form:form>
	

<script>
	function GeneralYes(){
		document.getElementById("general").disabled = false;
	}
	
	function GeneralNo(){
		document.getElementById("general").disabled = true;
	}
</script>
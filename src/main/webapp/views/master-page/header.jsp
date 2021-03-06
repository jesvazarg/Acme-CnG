<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-CnG Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="banner/administrator/edit.do"><spring:message code="master.page.banner.editBanner" /></a></li>	
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="offer/customer/listMyOffers.do"><spring:message code="master.page.offer.listMyOffers" /></a></li>
					<li><a href="request/customer/listMyRequests.do"><spring:message code="master.page.request.listMyRequests" /></a></li>
					<li><a href="apply/customer/listMyApplies.do"><spring:message code="master.page.apply.listMyApplies" /></a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv" href="apply/customer/list.do"><spring:message code="master.page.apply.list" />
				</a>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="customer/create.do"><spring:message code="master.page.registerCustomer" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.folder" /> 
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="folder/actor/list/inBox.do"><spring:message code="master.page.folder.inBox" /></a></li>
					<li><a href="folder/actor/list/outBox.do"><spring:message code="master.page.folder.outBox" /></a></li>
					<li><a href="message/actor/create.do"><spring:message code="master.page.message.create" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv" href="offer/customer/list.do"><spring:message code="master.page.offer.list" />
				</a>
			</li>
			<li>
				<a class="fNiv" href="request/customer/list.do"><spring:message code="master.page.request.list" />
				</a>
			</li>
			
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/displayPrincipal.do"><spring:message code="master.page.profile.display" /> </a></li>
					<li><a href="profile/edit.do"><spring:message code="master.page.profile.edit" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		
	</ul>
</div>

<div>
	<a href="javascript:setParam('language', 'en');">en</a> | <a href="javascript:setParam('language', 'es');">es</a>
</div>

<script> 
    function setParam(name, value) {
        var l = window.location;

        /* build params */
        var params = {};        
        var x = /(?:\??)([^=&?]+)=?([^&?]*)/g;        
        var s = l.search;
        for(var r = x.exec(s); r; r = x.exec(s))
        {
            r[1] = decodeURIComponent(r[1]);
            if (!r[2]) r[2] = '%%';
            params[r[1]] = r[2];
        }

        /* set param */
        params[name] = encodeURIComponent(value);

        /* build search */
        var search = [];
        for(var i in params)
        {
            var p = encodeURIComponent(i);
            var v = params[i];
            if (v != '%%') p += '=' + v;
            search.push(p);
        }
        search = search.join('&');

        /* execute search */
        l.search = search;
    }
</script>


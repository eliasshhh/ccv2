/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.sap.cec.firstweb.controller;

import static com.sap.cec.firstweb.constants.FirstwebConstants.PLATFORM_LOGO_CODE;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sap.cec.firstweb.service.FirstwebService;


@Controller
public class FirstwebHelloController
{
	private static final Logger LOG = Logger.getLogger(FirstwebHelloController.class.getName());

	@Autowired
	private FirstwebService firstwebService;

	@Autowired
	ServletContext servletContext;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model)
	{
		LOG.info("FirstwebHelloController.printWelcome()");

		model.addAttribute("logoUrl", firstwebService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		model.addAttribute("serverName", servletContext.getServerInfo());
		model.addAttribute("virtualServerName", servletContext.getVirtualServerName());
		model.addAttribute("classloaderInfo", getClassloaderInfo());
		return "welcome";
	}

	String getClassloaderInfo()
	{
		final Set<ClassLoader> visited = new HashSet<ClassLoader>();
		final StringBuilder sb = new StringBuilder();
		for (ClassLoader cl = this.getClass().getClassLoader(); cl.getParent() != null
				&& !visited.contains(cl.getParent()); cl = cl.getParent())
		{
			sb.append(cl.toString()).append("&nbsp;&nbsp;&nbsp;");
		}
		return sb.toString();
	}

}

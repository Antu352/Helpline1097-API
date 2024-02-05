/*
* AMRIT – Accessible Medical Records via Integrated Technology
* Integrated EHR (Electronic Health Records) Solution
*
* Copyright (C) "Piramal Swasthya Management and Research Institute"
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.helpline1097.controller.co.services;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.helpline1097.data.co.beneficiarycall.SubServices;
import com.iemr.helpline1097.data.co.services.CategoryDetails;
import com.iemr.helpline1097.service.co.services.CommonService;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

@RestController
@RequestMapping(value = "/api/helpline1097/co/get")
public class CommonController {
	InputMapper inputMapper = new InputMapper();
	Logger logger = LoggerFactory.getLogger(CommonController.class);

	@CrossOrigin()
	@RequestMapping(value = "/category", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, headers = "Authorization")

	public String getCategories() {
		OutputResponse response = new OutputResponse();
		try {
			response.setResponse(commonService.getCategories().toString());
		} catch (Exception e) {
			response.setError(e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@RequestMapping(value = "/subcategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, headers = "Authorization")

	public String getSubcategories(@RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			CategoryDetails category = inputMapper.gson().fromJson(request, CategoryDetails.class);
			response.setResponse(commonService.getSubCategories(category.getCategoryID()).toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@RequestMapping(value = "/categoryByID", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, headers = "Authorization")

	public String getcategoriesById(@RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			SubServices serviceMaster = inputMapper.gson().fromJson(request, SubServices.class);
			response.setResponse(commonService.getCategories(serviceMaster.getSubServiceID()).toString());
		} catch (

		Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@RequestMapping(value = "/servicetypes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, headers = "Authorization")

	public String getservicetypes() {
		OutputResponse response = new OutputResponse();
		try {
			response.setResponse(commonService.getActiveServiceTypes().toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	/**
	 * Designation Service
	 */
	private CommonService commonService;

	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
}
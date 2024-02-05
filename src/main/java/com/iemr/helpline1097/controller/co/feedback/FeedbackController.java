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
package com.iemr.helpline1097.controller.co.feedback;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.helpline1097.data.co.feedback.FeedbackDetails;
import com.iemr.helpline1097.service.co.feedback.FeedbackService;
import com.iemr.helpline1097.service.co.feedback.FeedbackServiceImpl;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/co")
@RestController
public class FeedbackController {
	private static final String request = null;
	private InputMapper inputMapper = new InputMapper();
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * feedback service
	 */
	private FeedbackService feedbackService;

	/***
	 * FeedbackServiceImpl
	 */
	private FeedbackServiceImpl feedbackServiceImpl;

	@Autowired
	public void setFeedbackServiceImpl(FeedbackServiceImpl feedbackServiceImpl) {
		this.feedbackServiceImpl = feedbackServiceImpl;
	}

	@Autowired
	public void setFeedbackService(FeedbackService feedbackService) {

		this.feedbackService = feedbackService;
	}

	@CrossOrigin()
	@ApiOperation(value = "Get feedback list", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/getfeedbacklist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String feedbackReuest(@RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			FeedbackDetails feedbackDetails = inputMapper.gson().fromJson(request, FeedbackDetails.class);
			List<FeedbackDetails> feedbackList = feedbackService
					.getFeedbackRequests(feedbackDetails.getBeneficiaryRegID());
			response.setResponse(feedbackList.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Get feedback by post", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/getfeedback/{feedbackID}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String getFeedbackByPost(@PathVariable("feedbackID") int feedbackID) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("" + feedbackID);
			List<FeedbackDetails> savedDetails = feedbackService.getFeedbackRequests(feedbackID);
			response.setResponse(savedDetails.toString());
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();
	}

	@CrossOrigin()
	@ApiOperation(value = "Save beneficiary feedback", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/saveBenFeedback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String saveBenFeedback(@RequestBody String feedbackRequest, HttpServletRequest request) {
		OutputResponse response = new OutputResponse();
		try {
			String savedFeedback = feedbackServiceImpl.saveFeedbackFromCustomer(feedbackRequest, request);
			response.setResponse(savedFeedback);
		} catch (Exception e) {
			logger.error("saveBenFeedback failed with error " + e.getMessage(), e);
			response.setError(e);
		}

		return response.toString();
	}
}

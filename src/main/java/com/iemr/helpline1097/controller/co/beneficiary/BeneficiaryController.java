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
package com.iemr.helpline1097.controller.co.beneficiary;

import java.util.Arrays;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.helpline1097.data.co.beneficiary.M_Promoservice;
import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.service.co.beneficiary.BenInformationCounsellingFeedbackReferralImpl;
import com.iemr.helpline1097.service.co.beneficiary.IEMRPromoserviceDetailsServiceImpl;
import com.iemr.helpline1097.utils.mapper.InputMapper;
import com.iemr.helpline1097.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/iEMR")
@RestController
public class BeneficiaryController {
	InputMapper inputMapper = new InputMapper();
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private IEMRPromoserviceDetailsServiceImpl iEMRPromoserviceDetailsServiceImpl;
	private BenInformationCounsellingFeedbackReferralImpl benInformationCounsellingFeedbackReferralImpl;

	@Autowired
	public void setiEMRPromoserviceDetailsServiceImpl(
			IEMRPromoserviceDetailsServiceImpl iEMRPromoserviceDetailsServiceImpl) {
		this.iEMRPromoserviceDetailsServiceImpl = iEMRPromoserviceDetailsServiceImpl;
	}

	@Autowired
	public void setBenInformationCounsellingFeedbackReferralImpl(
			BenInformationCounsellingFeedbackReferralImpl benInformationCounsellingFeedbackReferralImpl) {
		this.benInformationCounsellingFeedbackReferralImpl = benInformationCounsellingFeedbackReferralImpl;
	}

	@CrossOrigin()
	@ApiOperation(value = "Add promo service detail", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "add/promoServiceDetails", method = RequestMethod.POST, headers = "Authorization")
	public String addPromoServiceDetails(@RequestBody String request) {

		OutputResponse response = new OutputResponse();
		try {
			M_Promoservice m_promoservice = inputMapper.gson().fromJson(request, M_Promoservice.class);
			M_Promoservice m_Promoservice = iEMRPromoserviceDetailsServiceImpl.addPromoServiceDetail(m_promoservice);

			if (m_Promoservice != null) {
				response.setResponse("PromoServiceDetails Added");
			} else {
				response.setResponse("Failed to add PromoServiceDetails");
			}
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();

	}

	@CrossOrigin()
	@ApiOperation(value = "Save information requested by the beneficiary during call")
	@RequestMapping(value = "/saveBenCalServiceCatSubcatMapping", method = RequestMethod.POST, headers = "Authorization")
	public String saveBenCalServiceCatSubcatMapping(@RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			BenCallServicesMappingHistory[] benCallServicesMappingHistories = inputMapper.gson().fromJson(request,
					BenCallServicesMappingHistory[].class);
			Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = Arrays
					.asList(benCallServicesMappingHistories);

			response.setResponse(benInformationCounsellingFeedbackReferralImpl
					.saveBenCallServiceCatSubCat(benCallServicesMappingHistory));
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();

	}

	@CrossOrigin()
	@ApiOperation(value = "Save counselling requested by beneficiary")
	@RequestMapping(value = "/saveBenCalServiceCOCatSubcatMapping", method = RequestMethod.POST, headers = "Authorization")
	public String saveBenCalServiceCOCatSubcatMapping(@RequestBody String request) {
		OutputResponse response = new OutputResponse();
		try {
			BenCallServicesMappingHistory[] benCallServicesMappingHistories = inputMapper.gson().fromJson(request,
					BenCallServicesMappingHistory[].class);
			Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = Arrays
					.asList(benCallServicesMappingHistories);

			response.setResponse(benInformationCounsellingFeedbackReferralImpl
					.saveBenCallServiceCOCatSubCat(benCallServicesMappingHistory));
		} catch (Exception e) {
			logger.error("", e);
			response.setError(e);
		}
		return response.toString();

	}

	/**
	 * @param referralRequest
	 * @return
	 */
	@CrossOrigin()
	@ApiOperation(value = "Save beneficiary call referral mapping", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/saveBenCalReferralMapping", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON, headers = "Authorization")
	public String saveBenCalReferralMapping(@RequestBody String referralRequest) {
		OutputResponse response = new OutputResponse();
		logger.debug("saveBenCalReferralMapping request is " + referralRequest);
		try {

			response.setResponse(
					benInformationCounsellingFeedbackReferralImpl.saveBenCalReferralMapping(referralRequest));
		} catch (Exception e) {
			logger.error("saveBenCalReferralMapping failed with exception " + e.getMessage(), e);
			response.setError(e);
		}
		logger.debug("saveBenCalReferralMapping response is " + response);
		return response.toString();
	}

}

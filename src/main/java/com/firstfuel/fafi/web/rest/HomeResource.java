package com.firstfuel.fafi.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.firstfuel.fafi.service.FranchiseQueryService;
import com.firstfuel.fafi.service.dto.FranchiseCriteria;
import com.firstfuel.fafi.service.dto.FranchiseDTO;
import com.firstfuel.fafi.web.rest.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class HomeResource {
	private final Logger log = LoggerFactory.getLogger(HomeResource.class);
	private final FranchiseQueryService franchiseQueryService;

	public HomeResource(FranchiseQueryService franchiseQueryService) {
		this.franchiseQueryService = franchiseQueryService;
	}

	@GetMapping("/leading-franchises")
	@Timed
	public ResponseEntity<List<FranchiseDTO>> getAllFranchises(FranchiseCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Franchises by criteria: {}", criteria);
		Page<FranchiseDTO> page = franchiseQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/franchises");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
}

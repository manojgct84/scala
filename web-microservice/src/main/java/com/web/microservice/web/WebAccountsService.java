package com.web.microservice.web;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.microservice.account.Account;
import com.web.microservice.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


/**
 * Hide the access to the microservice inside this local service.
 *
 * @author Manojkumar
 */
@Service
public class WebAccountsService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	@Autowired
	private DiscoveryClient discoveryClient;

	protected Logger logger = Logger.getLogger(WebAccountsService.class.getName());

	public WebAccountsService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	/**
	 * The RestTemplate works because it uses a custom request-factory that uses
	 * Ribbon to look-up the service to use. This method simply exists to show
	 * this.
	 */
	@PostConstruct
	public void demoOnly() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		logger.warning("The RestTemplate request factory is "
				+ restTemplate.getRequestFactory().getClass());
	}

	public Account findByNumber(String accountNumber) throws JsonProcessingException {

		List<ServiceInstance> list = discoveryClient.getInstances("accounts-service");
		if (list != null && list.size() > 0) {
			System.out.print(list.get(0).getUri());
		}
		logger.info("findByNumber() invoked: for " + accountNumber);

		logger.info("URL : for " + list.get(0).getUri());
		logger.info("PORT: for " + list.get(0).getPort());
		logger.info("Service: for " + list.get(0).getServiceId());
		logger.info("Instance: for " + list.get(0).getInstanceId());

		logger.info("MicroService for invoked: for " + list.get(0).getServiceId() + ":" + list.get(0).getPort() + "/accounts/" + accountNumber);
		return restTemplate.getForObject(serviceUrl + ":" + list.get(0).getPort() + "/accounts/" + accountNumber,
				Account.class, accountNumber);


	}

	public List<Account> byOwnerContains(String name) {
		logger.info("byOwnerContains() invoked:  for " + name);
		Account[] accounts = null;
		List<ServiceInstance> list = discoveryClient.getInstances("accounts-service");
		if (list != null && list.size() > 0) {
			System.out.print(list.get(0).getUri());
		}

		try {
			accounts = restTemplate.getForObject(serviceUrl + ":" + list.get(0).getPort() + "/accounts/owner/" + name, Account[].class, name);
		} catch (HttpClientErrorException e) { // 404
			// Nothing found
		}

		if (accounts == null || accounts.length == 0)
			return null;
		else
			return Arrays.asList(accounts);
	}

	public Account getByNumber(String accountNumber) {

		List<ServiceInstance> list = discoveryClient.getInstances("accounts-service");
		if (list != null && list.size() > 0) {
			System.out.print(list.get(0).getUri());
		}

		Account account = restTemplate.getForObject(serviceUrl + ":" + list.get(0).getPort()
				+ "/accounts/" + accountNumber, Account.class, accountNumber);

		if (account == null)
			throw new AccountNotFoundException(accountNumber);
		else
			return account;
	}
}

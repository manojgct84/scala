package com.web.microservice.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.microservice.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.logging.Logger;

/**
 * Client controller, fetches Account info from the microservice via
 * {@link WebAccountsService}.
 *
 * @author Manojkumar
 */
@Controller
public class WebAccountsController {

	@Autowired
	protected WebAccountsService accountsService;


	protected Logger logger = Logger.getLogger(WebAccountsController.class.getName());

	public WebAccountsController(WebAccountsService accountsService) {
		this.accountsService = accountsService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("accountNumber", "searchText");
	}

	@RequestMapping("/accounts")
	public String goHome() {
		return "index";
	}

	@RequestMapping("/accounts/{accountNumber}")
	public @ResponseBody
	String byNumber(Model model, @PathVariable("accountNumber") String accountNumber) throws JsonProcessingException {

		logger.info("web-service byNumber() invoked: " + accountNumber);

		Account account = accountsService.findByNumber(accountNumber);
		logger.info("web-service byNumber() found: " + account);
		model.addAttribute("account", account);
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = mapperObj.writeValueAsString(model);
		return jsonStr;

	}

	@RequestMapping("/accounts/owner/{text}")
	public @ResponseStatus
	String ownerSearch(Model model, @PathVariable("text") String name) throws JsonProcessingException {
		logger.info("web-service byOwner() invoked: " + name);

		List<Account> accounts = accountsService.byOwnerContains(name);
		logger.info("web-service byOwner() found: " + accounts);
		model.addAttribute("search", name);
		if (accounts != null)
			model.addAttribute("accounts", accounts);
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = mapperObj.writeValueAsString(model);

		return jsonStr;
	}

	@RequestMapping(value = "/accounts/search", method = RequestMethod.GET)
	public String searchForm(Model model) {
		model.addAttribute("searchCriteria", new SearchCriteria());
		return "accountSearch";
	}

	@RequestMapping(value = "/accounts/dosearch")
	public String doSearch(Model model, SearchCriteria criteria,
	                       BindingResult result) throws JsonProcessingException {
		logger.info("web-service search() invoked: " + criteria);

		criteria.validate(result);

		if (result.hasErrors())
			logger.info("Account not found");

		String accountNumber = criteria.getAccountNumber();
		if (StringUtils.hasText(accountNumber)) {
			return byNumber(model, accountNumber);
		} else {
			String searchText = criteria.getSearchText();
			return ownerSearch(model, searchText);
		}
	}
}

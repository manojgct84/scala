package com.eureka.client;

//import com.netflix.discovery.DiscoveryClient;

import com.eureka.client.account.Account;
import com.eureka.client.account.AccountRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ServiceInstanceRestController {
    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    protected Logger logger = Logger.getLogger(ServiceInstanceRestController.class.getName());
    protected AccountRepository accountRepository;

    @Autowired
    public ServiceInstanceRestController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;

        logger.info("AccountRepository says system has "
                + accountRepository.countAccounts() + " accounts");
    }


    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {

        List<ServiceInstance> list = discoveryClient.getInstances("STORES");
        if (list != null && list.size() > 0) {
            System.out.print(list.get(0).getUri());
        }

        return this.discoveryClient.getInstances(applicationName);
    }

    @RequestMapping("/serviceUrl")
    public String serviceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("accounts-service", false);
        return instance.getHomePageUrl();
    }


    @RequestMapping("/accounts/owner/{name}")
    public List<Account> byOwner(@PathVariable("name") String partialName) throws AccountNotFoundException {
        logger.info("accounts-service byOwner() invoked: "
                + accountRepository.getClass().getName() + " for "
                + partialName);

        List<Account> accounts = accountRepository
                .findByOwnerContainingIgnoreCase(partialName);
        logger.info("accounts-service byOwner() found: " + accounts);

        if (accounts == null || accounts.size() == 0)
            throw new AccountNotFoundException(partialName);
        else {
            return accounts;
        }
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }


    @RequestMapping("/accounts/{accountNumber}")
    public Account byNumber(@PathVariable("accountNumber") String accountNumber) throws AccountNotFoundException {

        logger.info("accounts-service byNumber() invoked: " + accountNumber);
        Account account = accountRepository.findByNumber(accountNumber);
        logger.info("accounts-service byNumber() found: " + account);

        if (account == null)
            throw new AccountNotFoundException(accountNumber);
        else {
            return account;
        }
    }

}

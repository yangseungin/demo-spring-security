package com.giantdwarf.demospringsecurity.form;

import com.giantdwarf.demospringsecurity.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    public void dashboard() {
//        Account account = AccountContext.getAccount();
//        System.out.println("=====================");
//        System.out.println("account = " + account.getUsername());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        System.out.println("username = " + username);

    }

    @Async
    public void asyncService() {
        SecurityLogger.log("async Service");
        System.out.println("Async service is called");

    }
}

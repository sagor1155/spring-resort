package edu.miu.cs.cs544.aop;

import edu.miu.cs.cs544.domain.enums.UserType;
import edu.miu.cs.cs544.dto.LoggedInUserDTO;
import edu.miu.cs.cs544.service.util.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AuthorizationAdvice {

//    @Around("execution(* edu.miu.cs.cs544.service.impl.CustomerServiceImpl(..))")
//    public Object AuthorizeCustomerCheckerForUpdate(ProceedingJoinPoint call) throws Throwable{
//
//    }

    private void checkAuthorization(String email) {
        LoggedInUserDTO loggedInUserDTO = SecurityUtils.getLoggedInUser();
        if (loggedInUserDTO == null) {
            throw new IllegalArgumentException("User is not logged in");
        }
        if (loggedInUserDTO.getRole() == UserType.CLIENT && !loggedInUserDTO.getName().equals(email)) {
            throw new IllegalArgumentException("User is not authorized to update the customer");
        }
    }
}

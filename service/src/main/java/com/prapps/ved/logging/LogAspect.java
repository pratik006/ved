package com.prapps.ved.logging;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Aspect
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Autowired ObjectMapper objectMapper;

    @Around("execution(public * com.prapps.ved.service.*.*(..))")
    public Object logAfter(ProceedingJoinPoint joinPoint) {
        long started = System.currentTimeMillis();
        Object object = null;
        StringBuilder sb = new StringBuilder();
        Collection<String> requests = new ArrayList<>(joinPoint.getArgs().length);
        try {
            sb.append("Before: "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
            for(Object arg : joinPoint.getArgs()) {
                try {
                    requests.add(objectMapper.writeValueAsString(arg));
                    sb.append("("+objectMapper.writeValueAsString(arg)+", ");
                }catch (JsonMappingException e) {
                    LOG.info("Cannot serialize: " + e.getMessage());
                }
            }
            if(sb.indexOf(", ") > -1) {
                sb.delete(sb.length()-2, sb.length()-1);
                sb.append(")");
            }
            LOG.trace("arguements: "+sb);
            object = joinPoint.proceed();
            long duration = System.currentTimeMillis() - started;
            String response = objectMapper.writeValueAsString(object);
            LOG.trace("Completed in ["+duration+" ms] "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()
                    +" [Returned: " + response +"]");
            //log(joinPoint, duration, requests, response);
        } catch (Throwable e) {
            LOG.error("Error while audit logging: " + e.getMessage());
        }
        return object;
    }
}

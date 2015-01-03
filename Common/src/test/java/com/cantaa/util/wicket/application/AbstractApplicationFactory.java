package com.cantaa.util.wicket.application;

import org.apache.wicket.spring.SpringWebApplicationFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.cantaa.util.StringUtil;
import com.cantaa.util.spring.CantaaPropertyPlaceholderConfigurer;

/**
 * @author Hans Lesmeister
 */
public abstract class AbstractApplicationFactory extends SpringWebApplicationFactory {

    private ApplicationContext applicationContext;

    @Override
    protected ConfigurableWebApplicationContext newApplicationContext() {
        System.clearProperty(CantaaPropertyPlaceholderConfigurer.APPLICATION_TYPE);
        if (!StringUtil.isEmpty(getApplicationType())) {
            System.setProperty(CantaaPropertyPlaceholderConfigurer.APPLICATION_TYPE, getApplicationType());
        }

        ConfigurableWebApplicationContext configurableWebApplicationContext = super.newApplicationContext();
        applicationContext = configurableWebApplicationContext;
        return configurableWebApplicationContext;
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @return Type (code-name) of the application. If null or empty then the main application is assumed. This
     * is used to make it possible to distinguish for properties
     */
    protected abstract String getApplicationType();

}

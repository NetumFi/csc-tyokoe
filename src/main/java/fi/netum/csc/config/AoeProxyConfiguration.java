package fi.netum.csc.config;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
public class AoeProxyConfiguration implements EnvironmentAware {

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        Properties properties= (Properties) bindResult.get();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), properties.getProperty("servlet_url"));
        servletRegistrationBean.addInitParameter(ProxyServlet.P_TARGET_URI, properties.getProperty("target_url"));
        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, properties.getProperty("logging_enabled", "false"));
        return servletRegistrationBean;
    }

    private BindResult bindResult;

    @Override
    public void setEnvironment(Environment environment) {
        Iterable sources = ConfigurationPropertySources.get(environment);
        Binder binder = new Binder(sources);
        BindResult bindResult = binder.bind("proxy.aoe", Properties.class);
        this.bindResult = bindResult;
    }
}

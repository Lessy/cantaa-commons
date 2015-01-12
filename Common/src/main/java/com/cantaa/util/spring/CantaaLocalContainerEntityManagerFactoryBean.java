package com.cantaa.util.spring;

import java.util.Properties;
import java.util.Set;
import javax.persistence.spi.PersistenceUnitInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

import com.cantaa.util.PropertiesLoader;
import com.cantaa.util.StringUtil;

/**
 * Use this instead of LocalContainerEntityManagerFactoryBean to have the opportunity to override
 * persistence-properties from normal input-properties (i.e. from GlobalProperties)
 * @author Hans Lesmeister
 */
public class CantaaLocalContainerEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {
    private static final Logger log = LoggerFactory.getLogger(CantaaLocalContainerEntityManagerFactoryBean.class);

    /**
     * Injected in applicationContext.xml with good old Setter. Properties prefixed with "db." will
     * be used to override persistence-properties
     */
    private Properties overridingProperties;

    @Override
    protected PersistenceUnitInfo determinePersistenceUnitInfo(PersistenceUnitManager persistenceUnitManager) {
        PersistenceUnitInfo unitInfo = super.determinePersistenceUnitInfo(persistenceUnitManager);
        mergeAdditionalProperties(unitInfo);
        return unitInfo;
    }

    /**
     * If additional properties are specfied then put those properties in the properties
     * that have been read from the persistence.xml, thus overriding properties that where
     * specified in there.
     *
     * @param unitInfo Container that (among other stuff) contains the properties from persistence.xml
     */
    private void mergeAdditionalProperties(PersistenceUnitInfo unitInfo) {
        Properties mergableProperties = PropertiesLoader.stripProperties("db", overridingProperties, false);
        Set<String> names = mergableProperties.stringPropertyNames();
        for (String name : names) {
            String value = mergableProperties.getProperty(name);
            if (!StringUtil.isEmpty(value)) {
                unitInfo.getProperties().setProperty(name, value);
            }
        }

        log.info("** Persistence Properties to be used **");
        PropertiesLoader.logProperties(unitInfo.getProperties());
    }

    /**
     * Set Properties to override properties from the persistence.xml
     *
     * @param overridingProperties overriding properties
     */
    public void setOverridingProperties(Properties overridingProperties) {
        this.overridingProperties = overridingProperties;
    }
}

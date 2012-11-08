package com.cantgetnosleep.view;

import groovy.util.logging.Slf4j

import javax.servlet.ServletContext

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.View
import org.springframework.web.servlet.ViewResolver

@Slf4j
class SimpleTemplateEngineViewResolver implements ViewResolver {

    @Autowired
    ServletContext context
    
    String prefix
    String suffix
    
	View resolveViewName(String viewName, Locale locale) {
        
		log.info "${prefix}${viewName}${suffix}"
        
        String templateFilePath = "${prefix}${viewName}${suffix}"
        
        InputStream template = context.getResourceAsStream(templateFilePath)

        if (template) {
            log.info "FOUND!!!"
            new SimpleTemplateEngineView(template.text, templateFilePath)
        }   
        else {
            log.info "NOT FOUND!!!"
            null
        }     
        
 	}
    
}
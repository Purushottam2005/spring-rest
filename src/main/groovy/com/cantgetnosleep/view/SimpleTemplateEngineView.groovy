 package com.cantgetnosleep.view;

import groovy.text.SimpleTemplateEngine

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.control.CompilationFailedException
import org.springframework.web.servlet.view.AbstractView

public class SimpleTemplateEngineView extends AbstractView {

   private static final String CONTENT_TYPE = "application/json";

   final String templateText
   final String templatePath
   
   public SimpleTemplateEngineView(String templateText, String templatePath) {
       this.templateText = templateText
       this.templatePath = templatePath
   }
   
   /**
    * The model attribute to be set by the controller.
    */
   private String modelAttributeName;

   /**
    * Accessor
    *
    * @return
    */
   public String getModelAttributeName() {
       return modelAttributeName;
   }

   /**
    * Mutator
    *
    * @param modelAttributeName
    */
   public void setModelAttributeName(String modelAttributeName) {
       this.modelAttributeName = modelAttributeName;
   }

   @Override
   public String getContentType() {
       return CONTENT_TYPE;
   }

   /**
    * Render the XML object response which will be sent to the requested client.
    *
    * @param objectMap
    * @param request
    * @param response
    * @throws Exception
    */
   @Override
   protected void renderMergedOutputModel(Map objectMap,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {

                                         
       PrintWriter writer = null;

       try {
           response.setContentType(CONTENT_TYPE);
           writer = response.getWriter();
           /*
           SimpleTemplateEngine engine = new SimpleTemplateEngine()
           def writable = engine.createTemplate(templateText).make(objectMap)
           writer.write(writable.toString())
           */
           
           def builder = new groovy.json.JsonBuilder()
           def root = builder.people {
               person {
                   firstName 'Guillame'
                   lastName 'Laforge'
                   // Named arguments are valid values for objects too
                   address(
                           city: 'Paris',
                           country: 'France',
                           zip: 12345,
                   )
                   married true
                   // a list of values
                   conferences 'JavaOne', 'Gr8conf'
               }
           }
           
           writer.write(builder.toString())
         
       } catch (CompilationFailedException exc) {
           log.error "Error rendering template '${templatePath}'!", exc
       } catch (ClassNotFoundException exc) {
           log.error "Error rendering template! '${templatePath}'", exc
       } catch (IOException exc) {
           log.error "Error rendering template! '${templatePath}'", exc
       } finally {
           if (writer != null) {
               writer.flush();
               writer.close();
               writer = null;
           }
       }
       
   }
}

package com.pragma.toolbar.util;

import org.apache.log4j.Category;
import org.dom4j.Document;
import org.dom4j.io.SAXWriter;
import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierFactory;
import org.iso_relax.verifier.VerifierHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;



public class Validator {

  private final static Category CATEGORY = Category.getInstance(Validator.class);
  
  private String schemaURI;
  private Document document;

  public Validator(Document document, String schemaURI) {
    this.schemaURI = schemaURI;
    this.document = document;
  }
  
  public boolean validate() throws Exception {
  
    // (1) use autodetection of schemas
    VerifierFactory factory = new com.sun.msv.verifier.jarv.TheFactoryImpl();    
    Schema schema = factory.compileSchema(Validator.class.getResourceAsStream(schemaURI));
        
    // (2) configure a Vertifier
    Verifier verifier = schema.newVerifier();
    
    verifier.setErrorHandler(
        new ErrorHandler() {
            public void error(SAXParseException saxParseEx) {
               CATEGORY.error( "Error during validation.", saxParseEx);
            }

            public void fatalError(SAXParseException saxParseEx) {
               CATEGORY.fatal( "Fatal error during validation.", saxParseEx);
            }

            public void warning(SAXParseException saxParseEx) {
               CATEGORY.warn( saxParseEx );
            }
        }
    );    
        
    // (3) starting validation by resolving the dom4j document into sax     
    VerifierHandler handler = verifier.getVerifierHandler();
    SAXWriter writer = new SAXWriter( handler );
    writer.write( document );   
    
    return handler.isValid();
  }
  
}





















package org.jboss.resteasy.test.cdi.inheritance;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.cdi.inheritence.Book;
import org.jboss.resteasy.cdi.inheritence.BookSpecialized;
import org.jboss.resteasy.cdi.inheritence.InheritanceResource;
import org.jboss.resteasy.cdi.inheritence.JaxRsActivator;
import org.jboss.resteasy.cdi.inheritence.SelectBook;
import org.jboss.resteasy.cdi.inheritence.StereotypeAlternative;
import org.jboss.resteasy.cdi.util.UtilityProducer;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class tests CDI inheritance.
 * 
 * @author <a href="ron.sigal@jboss.com">Ron Sigal</a>
 * @version $Revision: 1.1 $
 *
 * Copyright Dec 5, 2012
 */
@RunWith(Arquillian.class)
public class SpecializedInheritanceTest
{
   @Inject Logger log;

   @Deployment
   public static Archive<?> createTestArchive()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "resteasy-cdi-ejb-test.war")
            .addClasses(JaxRsActivator.class, UtilityProducer.class)
            .addClasses(SelectBook.class, StereotypeAlternative.class)
            .addClasses(Book.class, BookSpecialized.class, InheritanceResource.class)
            .addAsWebInfResource("inheritence/specializedBeans.xml", "beans.xml");
      System.out.println(war.toString(true));
      return war;
   }

   @Test
   public void testAlternative() throws Exception
   {
      log.info("starting testAlternative()");
      ClientRequest request = new ClientRequest("http://localhost:8080/resteasy-cdi-ejb-test/rest/specialized");
      ClientResponse<?> response = request.get();
      log.info("Status: " + response.getStatus());
      assertEquals(200, response.getStatus());
      response.releaseConnection();
   }
}

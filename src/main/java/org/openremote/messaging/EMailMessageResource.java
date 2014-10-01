/*
 * OpenRemote, the Home of the Digital Home.
 * Copyright 2008-2014, OpenRemote Inc.
 *
 * See the contributors.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.openremote.messaging;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.openremote.messaging.domain.EMailMessage;

@Path("accounts/{accountId}/EMailMessages")
public class EMailMessageResource
{

  @PathParam(value = "accountId")
  String accountId;

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  public Response sendEMailMessage(@Context final ServletConfig config,
      @Context SecurityContext sc, EMailMessage message)
  {
    // TODO: validate payload and return appropriate error messages
    
    Properties props = System.getProperties();
    props.put("mail.smtp.host", config.getInitParameter("mail.smtp.host"));
    props.put("mail.smtp.ssl.enable", "true");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");

    // Get a Session object
    Session session = Session.getInstance(props, new Authenticator()
    {
      @Override
      protected PasswordAuthentication getPasswordAuthentication()
      {
        return new PasswordAuthentication(
            config.getInitParameter("mail.smtp.user"),
            config.getInitParameter("mail.smtp.password"));
      }
    });

    session.setDebug(true);

    Message msg = new MimeMessage(session);
    try
    {
      msg.setFrom(new InternetAddress(config.getInitParameter("mail.smtp.from")));
      for (String recipient : message.getRecipients())
      {
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
      }

      msg.setSubject(message.getSubject());
      msg.setText(message.getMessage());

      msg.setSentDate(new Date());

      Transport.send(msg);
    } catch (AddressException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (MessagingException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    System.out.println("\nMail was sent successfully.");

    // TODO: implement e-mail sending using standard Java mail API

    return Response.ok().build();
  }

}

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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Path("message")
public class MessageResource
{
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "AC16455642ff6433eb01a8d7d712174c04";
  // "AC209a0ca3d9a192180bdd727a8a6779c5";
  public static final String AUTH_TOKEN = "abe99b7ba357c701536ab048c8eeb199";
  // "75228acce647d88068bb764dcb32a839";
  /*
   * Exception in thread "main" java.lang.IllegalArgumentException: AuthToken ''
   * is not valid. at
   * com.twilio.sdk.TwilioRestClient.validateAuthToken(TwilioRestClient
   * .java:175) at
   * com.twilio.sdk.TwilioRestClient.<init>(TwilioRestClient.java:130) at
   * com.twilio.sdk.TwilioRestClient.<init>(TwilioRestClient.java:110) at
   * TwilioTest.main(TwilioTest.java:19)
   */

  @GET
  @Produces("text/plain")
  public String getHello()
  {
    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

    // Build a filter for the MessageList
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", "Test SMS message"));
    params.add(new BasicNameValuePair("To", "+32473983436"));
    // params.add(new BasicNameValuePair("From", "+3278482066"));
    params.add(new BasicNameValuePair("From", "+15005550006"));
    /*
     * Exception in thread "main" com.twilio.sdk.TwilioRestException: The From
     * phone number +3278482066 is not a valid, SMS-capable inbound phone number
     * or short code for your account. at
     * com.twilio.sdk.TwilioRestException.parseResponse
     * (TwilioRestException.java:74) at
     * com.twilio.sdk.TwilioRestClient.safeRequest(TwilioRestClient.java:583) at
     * com.twilio.sdk.resource.list.MessageList.create(MessageList.java:70) at
     * TwilioTest.main(TwilioTest.java:36)
     */

    MessageFactory messageFactory = client.getAccount().getMessageFactory();
    Message message;
    try
    {
      message = messageFactory.create(params);
      System.out.println(message.getSid());
    } catch (TwilioRestException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return "test done";
  }

}
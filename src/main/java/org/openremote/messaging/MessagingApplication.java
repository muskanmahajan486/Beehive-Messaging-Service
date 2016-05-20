/*
 * OpenRemote, the Home of the Digital Home.
 * Copyright 2008-2016, OpenRemote Inc.
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

import org.glassfish.jersey.server.ResourceConfig;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/v1")
public class MessagingApplication extends ResourceConfig
{

  public MessagingApplication()
  {
    register(SMSMessageResource.class);
    register(EMailMessageResource.class);

    register(new org.glassfish.hk2.utilities.binding.AbstractBinder()
    {
      @Override
      protected void configure()
      {
        /*
         * EBR Note: as per JAX-RS spec, it should be possible to use @Context
         * Application to inject application subclass into resources. However,
         * this fails under Jersey (tested up to 2.12) and the injected class is
         * a Jersey specific implementation, not this class. Way around it is to
         * use HK2 to register then inject application class using standard
         * JSR-330 annotations.
         */
        bind(new ServiceConfiguration());
      }
    });

  }

}
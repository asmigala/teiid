/*
 * JBoss, Home of Professional Open Source.
 * Copyright (C) 2008 Red Hat, Inc.
 * Copyright (C) 2000-2007 MetaMatrix, Inc.
 * Licensed to Red Hat, Inc. under one or more contributor 
 * license agreements.  See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package com.metamatrix.common.config.model;


import java.io.Serializable;

import com.metamatrix.common.config.api.ComponentTypeID;
import com.metamatrix.common.config.api.ConfigurationID;
import com.metamatrix.common.config.api.Host;
import com.metamatrix.common.config.api.HostID;
import com.metamatrix.common.config.api.HostType;
import com.metamatrix.core.util.FileUtils;

public class BasicHost extends BasicComponentDefn implements Host, Serializable {


    public BasicHost(ConfigurationID configID, HostID hostID, ComponentTypeID typeID) {
        super(configID, hostID, typeID);

    }

    protected BasicHost(BasicHost component) {
        super(component);
    }

    /** 
     * @see com.metamatrix.common.config.api.Host#getDataDirectory()
     * @since 4.3
     */
    public String getDataDirectory() {
        return getProperty(HostType.DATA_DIRECTORY);
    }
    /** 
     * @see com.metamatrix.common.config.api.Host#getLogDirectory()
     * @since 4.3
     */
    public String getLogDirectory() {
        return getProperty(HostType.LOG_DIRECTORY);
    }
    
    /**
     * @see com.metamatrix.common.config.api.Host#getTempDirectory()
     * @since 4.3
     */    
    public String getTempDirectory() {
        String datadir = getDataDirectory();
        return FileUtils.buildDirectoryPath(new String[] {datadir, "temp"}); //$NON-NLS-1$
        
    }
    /** 
     * @see com.metamatrix.common.config.api.Host#getPort()
     * @since 4.3
     */
    public String getPort() {
        return getProperty(HostType.PORT_NUMBER);
    }
/**
 * Return a deep cloned instance of this object.  Subclasses must override
 *  this method.
 *  @return the object that is the clone of this instance.
 */
   public synchronized Object clone() {
    	return new BasicHost(this);

    }

   /**
    * Return the address that should be used to bind to the host 
    * @return
    * @since 4.3
    */
   public String getBindAddress() {
       return getProperty(HostType.HOST_BIND_ADDRESS);
   }
   
   /**
    * Return the physical host address.  The physical may or may not 
    * be the same as the logical host name (@see #getID().getFullName()) 
    * @return
    * @since 4.3
    */
   public String getHostAddress() {
       return getProperty(HostType.HOST_PHYSICAL_ADDRESS);
   }
}

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

/*
 */
package com.metamatrix.connector.jdbc;

import java.sql.SQLException;
import java.util.Properties;

import com.metamatrix.connector.jdbc.extension.ResultsTranslator;
import com.metamatrix.connector.jdbc.extension.SQLTranslator;
import com.metamatrix.data.api.Connection;
import com.metamatrix.data.api.ConnectorCapabilities;
import com.metamatrix.data.api.ConnectorEnvironment;
import com.metamatrix.data.api.ConnectorLogger;
import com.metamatrix.data.api.Execution;
import com.metamatrix.data.api.ExecutionContext;
import com.metamatrix.data.exception.ConnectorException;
import com.metamatrix.data.metadata.runtime.RuntimeMetadata;
import com.metamatrix.data.pool.PoolAwareConnection;

/**
 * 
 */
public class JDBCSourceConnection implements Connection, PoolAwareConnection {
    protected java.sql.Connection physicalConnection;
    protected ConnectorEnvironment environment;
    private ConnectorLogger logger;
    private ConnectorCapabilities capabilities;
    private SQLTranslator sqlTranslator;
    private ResultsTranslator resultsTranslator;
    private ConnectionStrategy connectionStrategy;
    private ConnectionListener connectionListener;
    /**
     * @param connection
     */
    public JDBCSourceConnection(java.sql.Connection connection, ConnectorEnvironment environment, ConnectionStrategy connectionStrategy) throws ConnectorException{
        this(connection, environment, connectionStrategy, null);
    }
    
    public JDBCSourceConnection(java.sql.Connection connection, ConnectorEnvironment environment, ConnectionStrategy connectionStrategy, ConnectionListener connectionListener) throws ConnectorException {
        physicalConnection = connection;
        this.environment = environment;
        this.logger = environment.getLogger();
        this.connectionStrategy = connectionStrategy;
        this.connectionListener = connectionListener;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();

            Properties connectorProps = environment.getProperties();
            //create SQLTranslator
            String className = connectorProps.getProperty(JDBCPropertyNames.EXT_SQL_TRANSLATOR_CLASS);  
            if(className == null){
                throw new ConnectorException(JDBCPlugin.Util.getString("JDBCSourceConnection.Property_{0}_is_required,_but_not_defined_1", JDBCPropertyNames.EXT_SQL_TRANSLATOR_CLASS)); //$NON-NLS-1$
            }
            Class sqlTransClass = loader.loadClass(className);
            sqlTranslator = (SQLTranslator) sqlTransClass.newInstance();
            
            // Wrap in wrapper to standardize  
            sqlTranslator = new SQLTranslatorWrapper(sqlTranslator); 
            
            //create ResultsTranslator
            className = connectorProps.getProperty(JDBCPropertyNames.EXT_RESULTS_TRANSLATOR_CLASS);  
            if(className == null){
                throw new ConnectorException(JDBCPlugin.Util.getString("JDBCSourceConnection.Property_{0}_is_required,_but_not_defined_1", JDBCPropertyNames.EXT_RESULTS_TRANSLATOR_CLASS)); //$NON-NLS-1$
            }
            Class resultsTransClass = loader.loadClass(className);
            resultsTranslator = (ResultsTranslator) resultsTransClass.newInstance();
            resultsTranslator.initialize(environment);           
            capabilities = JDBCConnector.createCapabilities(environment, loader);
        } catch (ClassNotFoundException e1) {
            throw new ConnectorException(e1);
        } catch (InstantiationException e2) {
            throw new ConnectorException(e2);
        } catch (IllegalAccessException e3) {
            throw new ConnectorException(e3);
        }

        // notify the listner that coneection created
        if (this.connectionListener != null) { 
            this.connectionListener.afterConnectionCreation(this.physicalConnection, this.environment);
        }
    }

    /* 
     * @see com.metamatrix.data.Connection#getCapabilities()
     */
    public ConnectorCapabilities getCapabilities() {
        return this.capabilities;
    }

    /* (non-Javadoc)
     * @see com.metamatrix.data.Connection#createSynchExecution(com.metamatrix.data.language.ICommand)
     */
    public Execution createExecution(int executionMode, ExecutionContext executionContext, RuntimeMetadata metadata) throws ConnectorException {               
        sqlTranslator.initialize(environment, metadata);
        
        switch(executionMode) {
        	case ConnectorCapabilities.EXECUTION_MODE.SYNCH_QUERY:
            case ConnectorCapabilities.EXECUTION_MODE.SYNCH_QUERYCOMMAND:
            {
                return new JDBCQueryExecution(this.physicalConnection, sqlTranslator, resultsTranslator, logger, this.environment.getProperties(), executionContext, this.environment);
            }
            case ConnectorCapabilities.EXECUTION_MODE.BATCHED_UPDATES:
            case ConnectorCapabilities.EXECUTION_MODE.UPDATE:
            case ConnectorCapabilities.EXECUTION_MODE.BULK_INSERT:                
            {
                return new JDBCUpdateExecution(this.physicalConnection, sqlTranslator, resultsTranslator, logger, this.environment.getProperties(), executionContext);
            }
            case ConnectorCapabilities.EXECUTION_MODE.PROCEDURE:
            {
                return new JDBCProcedureExecution(this.physicalConnection, sqlTranslator, resultsTranslator, logger, this.environment.getProperties(), metadata, executionContext, this.environment);
            }
            default:
            {
                throw new ConnectorException(JDBCPlugin.Util.getString("JDBCSourceConnection.Execution_mode_not_supported__{0}_1", ""+executionMode)); //$NON-NLS-1$ //$NON-NLS-2$
            }                            
        }
    }

    public void release() {
        try {
            // notify the listener that connection being destroyed
            if (connectionListener != null) { 
                connectionListener.beforeConnectionClose(physicalConnection, environment);
            }
            this.physicalConnection.close();
        } catch(SQLException e) {
        	logger.logDetail("Exception during close: " + e.getMessage());
        }
    }

    public boolean isAlive() {
        if (connectionStrategy == null) {
            try {
                return !this.physicalConnection.isClosed();
            } catch (SQLException e) {
                return false;
            }
        }
        return connectionStrategy.isConnectionAlive(this.physicalConnection);
    }
    
    @Override
    public void connectionReleased() {
    	
    }
    
}

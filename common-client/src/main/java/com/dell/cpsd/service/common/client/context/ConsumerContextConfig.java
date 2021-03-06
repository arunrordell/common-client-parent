/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.service.common.client.context;

import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * This is the configuration for a client consumer context.
 *
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 * </p>
 * 
 * @since 1.0
 */
public class ConsumerContextConfig implements IConsumerContextConfig
{
    /*
     * The container identifier
     */
    private static final String CONTAINER_ID = "container.id";

    /*
     * The name of the consumer.
     */
    private String              consumerName;

    /*
     * The consumer stateful flag.
     */
    private boolean             stateful;

    /**
     * ConsumerContextConfig constructor
     * 
     * @param consumerName
     *            The name of the consumer.
     * @param stateful
     *            The consumer stateful flag.
     * 
     * @since 1.0
     */
    public ConsumerContextConfig(final String consumerName, final boolean stateful)
    {
        super();

        this.consumerName = consumerName;
        this.stateful = stateful;
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    @Override
    public String consumerName()
    {
        return this.consumerName;
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    @Override
    public String consumerUuid()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append(this.consumerName());
        builder.append(".");
        builder.append(this.getContainerId());

        final String uuid = UUID.randomUUID().toString();
        builder.append(".");
        builder.append(uuid);

        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    @Override
    public boolean stateful()
    {
        return this.stateful;
    }

    /**
     * This returns the container id or the name of the host.
     * 
     * @return The name of the host.
     * 
     * @since 1.0
     */
    protected String getContainerId()
    {
        String containerId = System.getProperty(CONTAINER_ID);

        if (containerId == null)
        {
            try
            {
                containerId = InetAddress.getLocalHost().getHostName();
            }
            catch (UnknownHostException e)
            {
                throw new RuntimeException("Unable to identify hostname", e);
            }
        }

        return containerId;
    }
}

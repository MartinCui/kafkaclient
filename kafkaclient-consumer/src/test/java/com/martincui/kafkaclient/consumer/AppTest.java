package com.martincui.kafkaclient.consumer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppTest extends TestCase
{
    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    public AppTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        logger.debug("aaa--{}---bbb", "asdfasdf");
        assertTrue( true );
    }
}

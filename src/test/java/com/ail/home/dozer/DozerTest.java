package com.ail.home.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DozerTest {

    @Test
    public void testDozer1() throws Exception {
        Mapper mapper = new DozerBeanMapper();

        SourceClass sourceClass = new SourceClass();
        sourceClass.setStr("str1");
        sourceClass.setNumberSrc(123);

        DestinationClass destinationClass = new DestinationClass();
        destinationClass.setStr("str2");
        destinationClass.setNumberDst(234);

        mapper.map(sourceClass, destinationClass);

        assertEquals(destinationClass.getStr(), "str1");
        assertEquals(destinationClass.getNumberDst(), new Integer(123));
        assertEquals(destinationClass.getPk(), "999");
    }

    @Test
    public void testDozer2() throws Exception {
        Mapper mapper = new DozerBeanMapper();

        DestinationClass destinationClass = new DestinationClass();
        destinationClass.setStr("str1");
        destinationClass.setNumberDst(123);
        destinationClass.setPk("999");

        SourceClass sourceClass = new SourceClass();
        sourceClass.setStr("str2");
        sourceClass.setNumberSrc(234);

        mapper.map(destinationClass, sourceClass); // should throw exception

        assertEquals(sourceClass.getStr(), "str1");
        assertEquals(sourceClass.getNumberSrc(), new Integer(123));
        assertEquals(sourceClass.getId(), new Long(999L));
    }

}

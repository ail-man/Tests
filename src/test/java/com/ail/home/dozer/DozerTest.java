package com.ail.home.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.classmap.RelationshipType;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.dozer.loader.api.TypeMappingOptions;
import org.junit.Test;

import static org.dozer.loader.api.FieldsMappingOptions.*;
import static org.dozer.loader.api.TypeMappingOptions.mapId;
import static org.dozer.loader.api.TypeMappingOptions.mapNull;
import static org.junit.Assert.assertEquals;

public class DozerTest {

    @Test
    public void testDozer1() throws Exception {
        Mapper mapper = new DozerBeanMapper();

        SourceClass sourceClass = new SourceClass();
        sourceClass.setStr("str1");
        sourceClass.setNumberSrc(123);
        SourceClass.SourceInclude sourceInclude = new SourceClass.SourceInclude();
        sourceInclude.setS("555");
        sourceClass.setIncluded(sourceInclude);

        DestinationClass destinationClass = new DestinationClass();
        destinationClass.setStr("str2");
        destinationClass.setNumberDst(234);
        DestinationClass.DestinationInclude destinationInclude = new DestinationClass.DestinationInclude();
        destinationInclude.setD("777");
        destinationClass.setIncluded(destinationInclude);

        mapper.map(sourceClass, destinationClass);

        assertEquals(destinationClass.getStr(), "str1");
        assertEquals(destinationClass.getNumberDst(), new Integer(123));
        assertEquals(destinationClass.getPk(), "999");
        assertEquals(destinationClass.getIncluded().getD(), "555");
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

    @Test
    public void testDozer3() throws Exception {
        BeanMappingBuilder builder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(SourceClass.class, DestinationClass.class,
                        TypeMappingOptions.oneWay(),
                        mapId("A"),
                        mapNull(true)
                )
                        .exclude("excluded")
                        .fields("src", "dest",
                                copyByReference(),
                                collectionStrategy(true, RelationshipType.NON_CUMULATIVE),
                                hintA(String.class),
                                hintB(Integer.class),
                                FieldsMappingOptions.oneWay(),
                                useMapId("A"),
                                customConverterId("id")
                        )
                        .fields("src", "dest",
                                customConverter("org.dozer.CustomConverter")
                        );
            }
        };

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(builder);
    }
}

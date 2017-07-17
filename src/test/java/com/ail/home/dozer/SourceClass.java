package com.ail.home.dozer;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

public class SourceClass {

    private Long id = 999L;

    @Getter
    @Setter
    private String str;

    @Getter
    @Setter
    @Mapping("numberDst")
    private Integer numberSrc;

    @Mapping("pk")
    public Long getId() {
        return id;
    }

}

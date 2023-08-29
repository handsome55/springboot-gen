package com.example.springbootgen.enums;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/27 16:38
 */
public enum GenJavaTypeEnum {
    /** Integer */
    Integer("Integer"),

    /** Long */
    Long("Long"),

    /** String */
    String("String"),

    /** Boolean */
    Boolean("Boolean"),

    /** Float */
    Float("Float"),

    /** Double */
    Double("Double"),

    /** Date */
    Date("Date"),

    /** BigDecimal */
    BigDecimal("BigDecimal"),

    /** LocalDateTime */
    LocalDateTime("LocalDateTime"),

    /** LocalDate */
    LocalDate("LocalDate"),

    /** LocalTime */
    LocalTime("LocalTime");

    private final String value;

    GenJavaTypeEnum(String value) {
        this.value = value;
    }

    public java.lang.String getValue() {
        return value;
    }
}

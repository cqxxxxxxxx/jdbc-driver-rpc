package com.cqx.jdbc.rpc.result;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public interface Row {
    Columns getColumns();

    String getStringByIndex(Integer columnIndex);

    String getStringByName(String columnName);

    Integer getIntByIndex(Integer columnIndex);

    Integer getIntByName(String columnName);

    Long getLongByIndex(Integer columnIndex);

    Long getLongByName(String columnName);

    Float getFloatByIndex(Integer columnIndex);

    Float getFloatByName(String columnName);

    Double getDoubleByIndex(Integer columnIndex);

    Double getDoubleByName(String columnName);

    Boolean getBooleanByIndex(Integer columnIndex);

    Boolean getBooleanByName(String columnName);

    BigDecimal getBigDecimalByIndex(Integer columnIndex);

    BigDecimal getBigDecimalByName(String columnName);
}

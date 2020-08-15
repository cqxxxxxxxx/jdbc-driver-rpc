package com.cqx.jdbc.rpc.result;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JacksonNodeRow implements Row {
    private JsonNode jsonNode;
    /**
     * 列名数据
     */
    private Columns columns;

    public JacksonNodeRow(JsonNode jsonNode, Columns columns) {
        Objects.requireNonNull(jsonNode, "jsonNode required");
        Objects.requireNonNull(columns, "columnNameMap required");
        this.jsonNode = jsonNode;
        this.columns = columns;
    }

    @Override
    public Columns getColumns() {
        return columns;
    }

    @Override
    public String getStringByIndex(Integer columnIndex) {
        return getStringByName(columns.getName(columnIndex));
    }

    @Override
    public String getStringByName(String columnName) {
        return jsonNode.get(columnName).asText();
    }

    @Override
    public Integer getIntByIndex(Integer columnIndex) {
        return getIntByName(columns.getName(columnIndex));
    }

    @Override
    public Integer getIntByName(String columnName) {
        return jsonNode.get(columnName).intValue();
    }

    @Override
    public Long getLongByIndex(Integer columnIndex) {
        return getLongByName(columns.getName(columnIndex));
    }

    @Override
    public Long getLongByName(String columnName) {
        return jsonNode.get(columnName).longValue();
    }

    @Override
    public Float getFloatByIndex(Integer columnIndex) {
        return getFloatByName(columns.getName(columnIndex));
    }

    @Override
    public Float getFloatByName(String columnName) {
        return jsonNode.get(columnName).floatValue();
    }

    @Override
    public Double getDoubleByIndex(Integer columnIndex) {
        return getDoubleByName(columns.getName(columnIndex));
    }

    @Override
    public Double getDoubleByName(String columnName) {
        return jsonNode.get(columnName).doubleValue();
    }

    @Override
    public Boolean getBooleanByIndex(Integer columnIndex) {
        return getBooleanByName(columns.getName(columnIndex));
    }

    @Override
    public Boolean getBooleanByName(String columnName) {
        return jsonNode.get(columnName).booleanValue();
    }

    @Override
    public BigDecimal getBigDecimalByIndex(Integer columnIndex) {
        return getBigDecimalByName(columns.getName(columnIndex));
    }

    @Override
    public BigDecimal getBigDecimalByName(String columnName) {
        return jsonNode.get(columnName).decimalValue();
    }
}

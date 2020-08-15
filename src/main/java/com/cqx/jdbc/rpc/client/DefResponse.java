package com.cqx.jdbc.rpc.client;

import com.cqx.jdbc.rpc.result.Columns;
import com.cqx.jdbc.rpc.result.JacksonNodeRow;
import com.cqx.jdbc.rpc.result.Rows;
import com.cqx.jdbc.rpc.util.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.Iterator;

public class DefResponse implements IResponse {
    private String code;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    /**
     * response转换成rows
     *
     * @return
     */
    @Override
    public Rows toRows() {
        String jsonData = JSONUtil.toJson(data);
        JsonNode jsonNode = JSONUtil.toJsonNode(jsonData);
        ArrayNode arrayNode = jsonNode.isArray() ? (ArrayNode) jsonNode : JsonNodeFactory.instance.arrayNode().add(jsonNode);
        Columns columns = new Columns();
        Iterator<String> columnNameIt = arrayNode.get(0).fieldNames();
        int i = 1;
        while (columnNameIt.hasNext()) {
            columns.addColumn(i++, columnNameIt.next());
        }
        Rows rows = new Rows(columns);
        for (JsonNode node : arrayNode) {
            rows.addRow(new JacksonNodeRow(node, columns));
        }
        return rows;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

package com.freq.arvand.arvand.utils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JqlTool {
    private static final Map<String, BiFunction<String, String, String>> OPERATOR_MAP = new HashMap<>();

    static {
        OPERATOR_MAP.put("eq", (f, v) -> f + " = " + v);
        OPERATOR_MAP.put("ne", (f, v) -> f + " <> " + v);
        OPERATOR_MAP.put("gt", (f, v) -> f + " > " + v);
        OPERATOR_MAP.put("lt", (f, v) -> f + " < " + v);
        OPERATOR_MAP.put("ge", (f, v) -> f + " >= " + v);
        OPERATOR_MAP.put("le", (f, v) -> f + " <= " + v);
        OPERATOR_MAP.put("like", (f, v) -> "LOWER(" + f + ") LIKE LOWER('%" + stripQuotes(v) + "%')");
        OPERATOR_MAP.put("in", (f, v) -> {
            String[] vals = v.split(",");
            String joined = String.join(", ", Arrays.stream(vals)
                    .map(val -> isNumericValue(val) ? val.trim() : "'" + val.trim() + "'")
                    .collect(Collectors.toList()));
            return f + " IN (" + joined + ")";
        });
    }
    private static boolean isNumericValue(String v) {
        return v.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isBoolean(String v) {
        return "true".equalsIgnoreCase(v) || "false".equalsIgnoreCase(v);
    }

    private static String stripQuotes(String v) {
        return v.replaceAll("^['\"]|['\"]$", "");
    }
    public static void addDelWhere(StringBuffer query){
        int order_index = query.lastIndexOf("order by");
        String oldOrder = null;

        if(order_index > -1){
            oldOrder = query.substring(order_index+8).trim();
            query = query.replace(order_index,query.length(),"");
        }
        int group_index = query.lastIndexOf("group by");
        String oldGroup = null;
        if(group_index != -1){
            oldGroup = query.substring(group_index+8).trim();
            query.replace(group_index,query.length(), "");
        }
        if(query.lastIndexOf( " where ") == -1){
            query.append(" where (");
            query.append("e.deleted = false");
            query.append(")");
        }
        else{
            query.append(" and ");
            query.append(" (");
            query.append("e.deleted = false");
            query.append(")");
        }
        if(oldGroup != null){
            query.append(" group by ").append(oldGroup);
        }
        if(oldOrder == null){
            query.append(" order by ").append("e.id desc");
        }
        else
            query.append(" order by ").append(oldOrder);
    }

    public static Map<String, Object> createQuery(StringBuffer jpql, String filter, String order) {
        Map param = new HashMap();
        String _order = null;
        int order_index = jpql.lastIndexOf("order by");
        if(order_index != -1){
            String old_order = jpql.substring(order_index+8).trim();
            jpql = jpql.replace(order_index,jpql.length(),"");
        }
        if(order.equals("no_order") || order == null){
            _order = "order by e.id desc";
        }
        if(order != null && order.length() > 0){
            order = "order by " + order;
        }
        return param;
    }

    public static void checkFilter(String filter, List<String> fields) {
        if (filter == null) return;

        Pattern pattern = Pattern.compile(
                "(?:\\(?\\s*)([eE]\\.[a-zA-Z0-9_]+)\\s*(=|<>|>=|<=|>|<|in|like|not\\s+in)?",
                Pattern.CASE_INSENSITIVE
        );

        String[] parts = filter.split("@@");
        for (String part : parts) {
            String c = part.trim();
            if (c.isEmpty()) continue;

            Matcher matcher = pattern.matcher(c);

            if (matcher.find()) {
                String field = matcher.group(1).trim();
                if (!fields.contains(field)) {
                    throw new IllegalArgumentException("Invalid field: " + field + " (allowed: " + fields + ")");
                }

            } else {
                throw new IllegalArgumentException("Invalid filter syntax: " + c);
            }
        }
    }

    public static int findparenthrse(char[] charArray, int i) {
        int close = i;
        int counter = 1;
        while (counter > 0){
            char c = charArray[++close];
            if (c == '(') counter++;
            else if (c == ')') counter--;
        }
        return close;
    }

    public static String addFilter(String input) {
        if (input == null || input.isEmpty()) return "";

        String[] parts = input.split(";");
        List<String> conditions = new ArrayList<>();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part == null || part.isEmpty()) continue;
            part = part.trim();

            String conditionStr;

            if (!part.contains("@@")) {
                conditionStr = part.startsWith("and") || part.startsWith("AND")
                        ? part
                        : part;
            } else {
                String[] seg = part.split("@@");
                if (seg.length != 3) continue;

                String field = seg[0].trim();
                String op = seg[1].trim().toLowerCase();
                String value = seg[2].trim();

                if (!OPERATOR_MAP.containsKey(op)) continue;

                String jpqlField = field.startsWith("e.") ? field : "e." + field;
                String jpqlValue = isNumericValue(value) || isBoolean(value)
                        ? value
                        : "'" + stripQuotes(value) + "'";

                conditionStr = OPERATOR_MAP.get(op).apply(jpqlField, jpqlValue);
            }

            conditionStr = "( " + conditionStr + " )";
            conditions.add(conditionStr);

            if (i < parts.length - 1) {
                conditions.add("and");
            }
        }

        return String.join(" ", conditions);
    }


//        Map<String,Object> param = new HashMap<>();
//        for(Iterator iterator = param.entrySet().iterator(); iterator.hasNext();){
//            Map.Entry<String,Object> entry = (Map.Entry<String,Object>)iterator.next();
//            entry.getKey();
//            entry.getValue();
//        }
//    }
}

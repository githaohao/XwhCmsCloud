package com.xwh.article.annotation;

/**
 * 时间范围
 * @author xwh
 **/
public enum TimeRange {
    DAY("date_sub(curdate(), INTERVAL 1 DAY)"),
    WEEK("date_sub(curdate(), INTERVAL 7 DAY)"),
    MONTH("date_sub(curdate(), INTERVAL 30 DAY)"),
    YEAR("date_sub(curdate(), INTERVAL 365 DAY)");

    private String expression;

    TimeRange(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }
}

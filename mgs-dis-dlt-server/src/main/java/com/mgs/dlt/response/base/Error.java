package com.mgs.dlt.response.base;

import lombok.Data;

/**
 *   2018/5/10.
 */
@Data
public class Error {

    private String Message;
    private String ErrorCode;
    private String StackTrace;
    private String SeverityCode;
    private String ErrorFields;
    private String ErrorClassification;

}

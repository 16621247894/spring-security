package com.zwq.cloud;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {



    /**
     * SUCCESS
     */
    ZERO_SUCCESS(0, "SUCCESS"),
    /**
     * SUCCESS
     */
    SUCCESS(200, "SUCCESS"),
    /**
     * Invalid Argument
     */
    INVALID_ARGUMENT(400, "Invalid Argument"),
    /**
     * Unauthenticated
     */
    UNAUTHENTICATED(401, "Unauthenticated"),
    /**
     * Permission Denied
     */
    PERMISSION_DENIED(403, "Permission Denied"),
    /**
     * Not Found
     */
    NOT_FOUND(404, "Not Found"),
    /**
     * Method Not Allowed
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    /**
     * Already Exists
     */
    ALREADY_EXISTS(409, "Already Exists"),
    /**
     * Request Body Too Large
     */
    REQUEST_BODY_TOO_LARGE(414, "Request Body Too Large"),
    /**
     * Parameter Out Of Range
     */
    PARAMETER_OUT_OF_RANGE(415, "Parameter Out Of Range"),
    /**
     * Resource Exhausted
     */
    RESOURCE_EXHAUSTED(429, "Resource Exhausted"),
    /**
     * Internal Server Error
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    /**
     * Not Implemented
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),

    /**
     * Service Timeout
     */
    SERVICE_TIMEOUT(504, "Service Timeout"),

    SOP_CONNECTION_FAIL(602, "Sop Connection Fail"),

    SOP_JWT_TOKEN_FAIL(603, "Sop JWT Token Fail"),

    MPS_TOKEN_NULL(603,"Token is Null"),

    SOP_FAIL(602, "Sop Connection Fail"),

    MPS_DEVICE_EXIST(601, "device already exist"),

    MPS_FACTORY_CALENDAR_EXIST(605, "factory calendar already exist"),

    MPS_FACTORY_CYCLE_EXIST(610, "factory cycle already exist"),

    MPS_DATE_PARSE_FAIL(615,"date parse fail"),


    MPS_PROJECT_EXIST(701, "project already exist"),

    MPS_SAP_SOP_PROJECT_EXIST(801, "sap project already exist"),


    MPS_SET_PACKAGE_MORE_NOT_ARRANGED(802, "setpackage add number more than not arranged"),

    SNAPSHOT_VERSION_NUMBER_EXIST(803, "version number exist"),

    SNAPSHOT_DATA_CHECK_FAILED(804, "date check failed"),

    MASTER_PLAN_SUBMIT_FAIL_MSG(901, "master plan submit fail, there are unfinished tasks"),

    NO_RESULT_MSG(902, "no result"),

    VERSION_RECORD_CHECK_FAIL(903, "version record check fail"),

    PROJECT_HAS_EXIST(904, "project has exist"),

    TASK_CALCULATING(905, "sync data"),

    MPS_SET_PACKAGE_PRODUCTION_NOT_WORK_DAY(805, "setpackage production is in workday");

    private final int code;

    private final String msg;
}

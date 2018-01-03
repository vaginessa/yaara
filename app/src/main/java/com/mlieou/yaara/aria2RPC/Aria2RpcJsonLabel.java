package com.mlieou.yaara.aria2RPC;

/**
 * Created by mengdi on 12/29/17.
 */

public interface Aria2RpcJsonLabel {
    String JSONRPC = "jsonrpc";
    String ID = "id";
    String METHOD = "method";
    String PARAMS = "params";
    String METHOD_NAME = "methodName";
    String RESULT = "result";
    String ERROR = "error";

    String URI = "uri";
    String STATUS = "status";

    String COMPLETED_LENGTH = "completedLength";
    String INDEX = "index";
    String LENGTH = "length";
    String PATH = "path";
    String SELECTED = "selected";
    String URIS = "uris";

    String TOKEN = "token";
}

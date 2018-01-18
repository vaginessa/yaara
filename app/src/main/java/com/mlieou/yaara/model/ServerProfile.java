/*
 *         YAARA - Aria2 Remote for Android
 *
 * Copyright 2017-2018 Mlieou <ifddd@outlook.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mlieou.yaara.model;

import com.mlieou.yaara.ServerPreferencesManager;

public class ServerProfile {

    public enum Protocol {
        HTTP,
        HTTPS,
        WEB_SOCKET,
        WEB_SOCKET_SECURITY
    }

    public enum RequestMethod {
        POST,
        GET
    }

  private String name;
  private String host;
  private int port;
  private String requestPath;
  private String secret;
  private Protocol protocol;
  private RequestMethod requestMethod;

    public ServerProfile(String name, String host, int port, String requestPath, String secret, Protocol protocol, RequestMethod requestMethod) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.requestPath = requestPath;
        this.secret = secret;
        this.protocol = protocol;
        this.requestMethod = requestMethod;
    }

    public ServerProfile(ServerPreferencesManager manager) {

    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getSecret() {
        return secret;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }
}

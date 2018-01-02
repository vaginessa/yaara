package com.mlieou.yaara.aria2RPC;

/**
 * Created by mengdi on 12/29/17.
 */

public interface Aria2RpcOption {
    String ALL_PROXY = "all-proxy";
    String ALL_PROXY_PASSWD = "all-proxy-passwd";
    String ALL_PROXY_USER = "all-proxy-user";
    String ALLOW_OVERWRITE = "allow-overwrite";
    String ALLOW_PIECE_LENGTH_CHANGE = "allow-piece-length-change";
    String ALWAYS_RESUME = "always-resume";
    String ASYNC_DNS = "async-dns";
    String AUTO_FILE_RENAMING = "auto-file-renaming";
    String BT_ENABLE_HOOK_AFTER_HASH_CHECK = "bt-enable-hook-after-hash-check";
    String BT_ENABLE_LPD = "bt-enable-lpd";
    String BT_EXCLUDE_TRACKER = "bt-exclude-tracker";
    String BT_EXTERNAL_IP = "bt-external-ip";
    String BT_FORCE_ENCRYPTION = "bt-force-encryption";
    String BT_HASH_CHECK_SEED = "bt-hash-check-seed";
    String BT_LOAD_SAVED_METADATA = "bt-load-saved-metadata";
    String BT_MAX_PEERS = "bt-max-peers";
    String BT_METADATA_ONLY = "bt-metadata-only";
    String BT_MIN_CRYPTO_LEVEL = "bt-min-crypto-level";
    String BT_PRIORITIZE_PIECE = "bt-prioritize-piece";
    String BT_REMOVE_UNSELECTED_FILE = "bt-remove-unselected-file";
    String BT_REQUEST_PEER_SPEED_LIMIT = "bt-request-peer-speed-limit";
    String BT_REQUIRE_CRYPTO = "bt-require-crypto";
    String BT_SAVE_METADATA = "bt-save-metadata";
    String BT_SEED_UNVERIFIED = "bt-seed-unverified";
    String BT_STOP_TIMEOUT = "bt-stop-timeout";
    String BT_TRACKER = "bt-tracker";
    String BT_TRACKER_CONNECT_TIMEOUT = "bt-tracker-connect-timeout";
    String BT_TRACKER_INTERVAL = "bt-tracker-interval";
    String BT_TRACKER_TIMEOUT = "bt-tracker-timeout";
    String CHECK_INTEGRITY = "check-integrity";
    String CHECKSUM = "checksum";
    String CONDITIONAL_GET = "conditional-get";
    String CONNECT_TIMEOUT = "connect-timeout";
    String CONTENT_DISPOSITION_DEFAULT_UTF8 = "content-disposition-default-utf8";
    String CONTINUE = "continue";
    String DIR = "dir";
    String DRY_RUN = "dry-run";
    String ENABLE_HTTP_KEEP_ALIVE = "enable-http-keep-alive";
    String ENABLE_HTTP_PIPELINING = "enable-http-pipelining";
    String ENABLE_MMAP = "enable-mmap";
    String ENABLE_PEER_EXCHANGE = "enable-peer-exchange";
    String FILE_ALLOCATION = "file-allocation";
    String FOLLOW_METALINK = "follow-metalink";
    String FOLLOW_TORRENT = "follow-torrent";
    String FORCE_SAVE = "force-save";
    String FTP_PASSWD = "ftp-passwd";
    String FTP_PASV = "ftp-pasv";
    String FTP_PROXY = "ftp-proxy";
    String FTP_PROXY_PASSWD = "ftp-proxy-passwd";
    String FTP_PROXY_USER = "ftp-proxy-user";
    String FTP_REUSE_CONNECTION = "ftp-reuse-connection";
    String FTP_TYPE = "ftp-type";
    String FTP_USER = "ftp-user";
    String GID = "gid";
    String HASH_CHECK_ONLY = "hash-check-only";
    String HEADER = "header";
    String HTTP_ACCEPT_GZIP = "http-accept-gzip";
    String HTTP_AUTH_CHALLENGE = "http-auth-challenge";
    String HTTP_NO_CACHE = "http-no-cache";
    String HTTP_PASSWD = "http-passwd";
    String HTTP_PROXY = "http-proxy";
    String HTTP_PROXY_PASSWD = "http-proxy-passwd";
    String HTTP_PROXY_USER = "http-proxy-user";
    String HTTP_USER = "http-user";
    String HTTPS_PROXY = "https-proxy";
    String HTTPS_PROXY_PASSWD = "https-proxy-passwd";
    String HTTPS_PROXY_USER = "https-proxy-user";
    String INDEX_OUT = "index-out";
    String LOWEST_SPEED_LIMIT = "lowest-speed-limit";
    String MAX_CONNECTION_PER_SERVER = "max-connection-per-server";
    String MAX_DOWNLOAD_LIMIT = "max-download-limit";
    String MAX_FILE_NOT_FOUND = "max-file-not-found";
    String MAX_MMAP_LIMIT = "max-mmap-limit";
    String MAX_RESUME_FAILURE_TRIES = "max-resume-failure-tries";
    String MAX_TRIES = "max-tries";
    String MAX_UPLOAD_LIMIT = "max-upload-limit";
    String METALINK_BASE_URI = "metalink-base-uri";
    String METALINK_ENABLE_UNIQUE_PROTOCOL = "metalink-enable-unique-protocol";
    String METALINK_LANGUAGE = "metalink-language";
    String METALINK_LOCATION = "metalink-location";
    String METALINK_OS = "metalink-os";
    String METALINK_PREFERRED_PROTOCOL = "metalink-preferred-protocol";
    String METALINK_VERSION = "metalink-version";
    String MIN_SPLIT_SIZE = "min-split-size";
    String NO_FILE_ALLOCATION_LIMIT = "no-file-allocation-limit";
    String NO_NETRC = "no-netrc";
    String NO_PROXY = "no-proxy";
    String OUT = "out";
    String PARAMETERIZED_URI = "parameterized-uri";
    String PAUSE = "pause";
    String PAUSE_METADATA = "pause-metadata";
    String PIECE_LENGTH = "piece-length";
    String PROXY_METHOD = "proxy-method";
    String REALTIME_CHUNK_CHECKSUM = "realtime-chunk-checksum";
    String REFERER = "referer";
    String REMOTE_TIME = "remote-time";
    String REMOVE_CONTROL_FILE = "remove-control-file";
    String RETRY_WAIT = "retry-wait";
    String REUSE_URI = "reuse-uri";
    String RPC_SAVE_UPLOAD_METADATA = "rpc-save-upload-metadata";
    String SEED_RATIO = "seed-ratio";
    String SEED_TIME = "seed-time";
    String SELECT_FILE = "select-file";
    String SPLIT = "split";
    String SSH_HOST_KEY_MD = "ssh-host-key-md";
    String STREAM_PIECE_SELECTOR = "stream-piece-selector";
    String TIMEOUT = "timeout";
    String URI_SELECTOR = "uri-selector";
    String USE_HEAD = "use-head";
    String USER_AGENT = "user-agent";
}
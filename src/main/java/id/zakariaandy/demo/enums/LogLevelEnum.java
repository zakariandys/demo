package id.zakariaandy.demo.enums;

public enum LogLevelEnum {

    BLOCKER("BLOCKER", "Blocker data"),

    CRITICAL("CRITICAL", "Critical data"),

    WARN("WARN", "Warn data"),

    INFO("INFO", "Info data");

    private String code;

    private String prefix;

    LogLevelEnum(String code, String prefix) {
        this.code = code;
        this.prefix = prefix;
    }

    public String getCode() {
        return code;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPrefixByCode(String code) {
        for (LogLevelEnum logLevelEnum : values()) {
            if (logLevelEnum.getCode().equalsIgnoreCase(code)) {
                return logLevelEnum.getPrefix();
            }
        }
        return null;
    }
}

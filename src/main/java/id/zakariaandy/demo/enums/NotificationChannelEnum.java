package id.zakariaandy.demo.enums;

public enum NotificationChannelEnum {

    SMS("SMS"),
    EMAIL("E-Mail"),
    PHONE("Phone")
    ;

    private String channel;

    NotificationChannelEnum(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }
}

package COMPONENT;

import java.util.UUID;

public class IdAutoGenerral {
    public static String generateUserId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}

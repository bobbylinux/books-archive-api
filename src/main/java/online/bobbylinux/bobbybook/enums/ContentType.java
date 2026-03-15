package online.bobbylinux.bobbybook.enums;

public enum ContentType {
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_WEBP("image/webp"),
    IMAGE_GIF("image/gif");

    private final String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static ContentType fromMimeType(String mimeType) {
        for (ContentType ct : values()) {
            if (ct.mimeType.equalsIgnoreCase(mimeType)) {
                return ct;
            }
        }
        throw new IllegalArgumentException("ContentType not supported: " + mimeType);
    }
}

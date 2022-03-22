package fis.pms.domain.fileEnum;

//기록물형태
// (1.일반문서, 2.도면류, 3.사진-필름류 4. 녹음-동영상류, 5.카드류)
public enum F_type {
    NONE(""),
    GENERAL("1"),
    DRAWING("2"),
    PHOTO("3"),
    VIDEO("4"),
    CARD("5");

    private String type;

    F_type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

package fis.pms.domain.caseEnum;

//포맷 1 (1.문서, 2.오디오, 3.복합, 4.이미지(default), 5.비디오, 9.기타)
public enum C_format {
    DOC("1"),
    AUDIO("2"),
    COMPLEX("3"),
    IMAGE("4"),
    VIDEO("5"),
    ETC("9");

    private String format;

    C_format(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}

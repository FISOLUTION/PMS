package fis.pms.domain.caseEnum;

//첨부물 종류 2 (01.오디오, 02.복합, 03.이미지(default), 04.문서, 05.비디오, 99.기타)
public enum C_attachtype {
    AUDIO("01"),
    COMPLEX("02"),
    IMAGE("03"),
    DOC("04"),
    VIDEO("05"),
    ETC("99");

    private String attachtype;

    C_attachtype(String attachtype) {
        this.attachtype = attachtype;
    }

    public String getAttachtype() {
        return attachtype;
    }
}

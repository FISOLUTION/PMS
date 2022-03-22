package fis.pms.domain.fileEnum;

//인수인계구분 (0.없음 1.인수 2.인계)
public enum F_inheritance {
    NONE("0"),
    TAKEOVER("1"),
    HANDOVER("2");

    private String inheritance;

    F_inheritance(String inheritance) {
        this.inheritance = inheritance;
    }

    public String getInheritance() {
        return inheritance;
    }
}

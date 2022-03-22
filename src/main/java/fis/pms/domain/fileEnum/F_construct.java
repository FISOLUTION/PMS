package fis.pms.domain.fileEnum;


//디비 구축여부 (0구축 1비구축)
//스캔 구축여부 (0구축 1비구축)
public enum F_construct {
    NONE(""),
    YES("0"),
    NO("1");

    private String construct;

    F_construct(String construct) {
        this.construct = construct;
    }

    public String getConstruct() {
        return construct;
    }
}

package fis.pms.domain.fileEnum;

//수정여부 (0.해당없음 1. 수정함)
public enum F_modify {
    NONE(""),
    NOTHING("0"),
    MODIFY("1");

    private String modify;

    F_modify(String modify) {
        this.modify = modify;
    }

    public String getModify() {
        return modify;
    }
}

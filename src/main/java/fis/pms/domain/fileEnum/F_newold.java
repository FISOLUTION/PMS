package fis.pms.domain.fileEnum;

//기록물구분(1.신기록물, 2.구기록물)
public enum F_newold {
    NONE(""),
    NEW("1"),
    OLD("2");

    private String newold;

    F_newold(String newold) {
        this.newold = newold;
    }

    public String getNewold() {
        return newold;
    }
}

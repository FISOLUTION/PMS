package fis.pms.domain.fileEnum;


//현재진행중인 공정(색인 입력(12), 색인검수(13), 로딩(14))
public enum F_process {
    NONE(""),
    PREINFO("1"),
    EXPORT("3"),
    SCAN("8"),
    IMGMODIFY("10"),
    INPUT("12"),
    CHECK("13"),
    LOADING("14"),
    // 2021 8/24 현승구 upload부분 추가
    UPLOAD("15"),
    UPLOADED("16");

    //이게 뭔데 쓰면 오류 안나지 ???
    private String process;

    F_process(String process) {
        this.process = process;
    }

    public String getProcess() {
        return process;
    }
}

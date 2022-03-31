package fis.pms.domain.fileEnum;

//현재완료한 공정
public enum F_process {
    //NONE(""),
    PREINFO("1"),           //사전조사
    //LIST("2"),              //목록작업
    EXPORT("3"),            //반입반출
    //CLASSIFY("4"),          //분류
    //CLASSIFY_CHECK("5"),    //분류검증
    //MKPAGE("6"),            //면표시
    //MKPAGE_CHECK("7"),      //면표시검증
    SCAN("8"),              //스캔문서
    //SCAN_PAGE("9"),         //스캔도면
    IMGMODIFY("10"),        //이미지보정
    IMG_CHECK("11"),        //이미지검수
    INPUT("12"),            //색인입력
    CHECK("13"),            //색인검수
    //LOADING("14"),          //로딩
    UPLOAD("15");           //업로드
    //UPLOADED("16");         //업로드

    private String process;

    F_process(String process) {
        this.process = process;
    }

    public String getProcess() {
        return process;
    }
}

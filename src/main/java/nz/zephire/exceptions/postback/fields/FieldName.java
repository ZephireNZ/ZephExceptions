package nz.zephire.exceptions.postback.fields;

public enum FieldName {

    SUBMITTED_FOR("V1_I1_X1"),
    SUBMITTED_BY("V1_I1_X2"),
    APPROVER("V1_I1_D3"),
    DATE("V1_I1_T4"),
    REASON("V1_I1_D6"),
    DESCRIPTION("V1_I1_RTC7_RTI1_RT1"),
    ORIG_START("V1_I1_T8"),
    ORIG_END("V1_I1_T9"),
    NEW_START("V1_I1_T10"),
    NEW_END("V1_I1_T11"),
    SUBMIT("CTRL15_5;V1_I1"),
    ;

    private final String id;

    FieldName(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

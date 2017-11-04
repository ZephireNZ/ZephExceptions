package nz.zephire.exceptions.postback.fields;

import nz.zephire.exceptions.postback.SPPostbackObject;

public class SPField implements SPPostbackObject {

    private final FieldName type;
    private final String text;

    public SPField(FieldName id, String text) {

        this.type = id;
        this.text = text;
    }

    @Override
    public String toString() {
        switch(type) {
            case SUBMITTED_FOR:
            case SUBMITTED_BY:
                return "23;"+ type.getId() + ";" + text;

            case APPROVER:
            case REASON:
                return "0;" + type.getId() + ";;" + text;

            case DESCRIPTION:
                return "12;" + type.getId() + ";;" + text;

            case DATE:
            case ORIG_START:
            case ORIG_END:
            case NEW_START:
            case NEW_END:
                return "0;" + type.getId() + ";1;" + text;

            case SUBMIT:
                return "1;" + type.getId() + ";";
            default:
                throw new IllegalStateException("Missing an enum");
        }
    }
}

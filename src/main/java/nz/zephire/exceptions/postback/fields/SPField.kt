package nz.zephire.exceptions.postback.fields

import nz.zephire.exceptions.postback.SPPostbackObject

class SPField(private val type: FieldName, private val text: String) : SPPostbackObject {

    override fun toString(): String {
        when (type) {
            FieldName.SUBMITTED_FOR, FieldName.SUBMITTED_BY -> return "23;" + type.id + ";" + text

            FieldName.APPROVER, FieldName.REASON -> return "0;" + type.id + ";;" + text

            FieldName.DESCRIPTION -> return "12;" + type.id + ";;" + text

            FieldName.DATE, FieldName.ORIG_START, FieldName.ORIG_END, FieldName.NEW_START, FieldName.NEW_END -> return "0;" + type.id + ";1;" + text

            FieldName.SUBMIT -> return "1;" + type.id + ";"

            else -> throw IllegalStateException("Missing an enum")
        }
    }
}

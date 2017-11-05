package nz.zephire.exceptions.postback.fields

import nz.zephire.exceptions.postback.SPFormData
import nz.zephire.exceptions.postback.SPPostbackObject

class SPHeader(val uuid: String, val zeroedTimestamp: String, val canary: String) : SPPostbackObject {
    private val header: String =
            "1\n11\nFormControl1268\n" +
            "1 8;0;" +
            uuid + ";" + // Submission UUID
            "AFUNAIHEPRJJIRM342GUZE4BMGYC2L2UJ5HUYUZPKJCVCVKFKNKFGL2FLBBUKUCUJFHU4UZPIZHVETKTF5KEKTKQJRAVIRJOLBJU4KTZMIZWKZ2GORYGKZTTIVHUQT3FJZITC53EINFW4RBUG5UXKMKJJNUG4VTSKFLVKS3HIE;" + // Form UUID

            "0;;" +
            "http%3A%2F%2F2dintranet.2degreesmobile.co.nz%2Ftools%2Frequests%2FExceptions%2FForms%2Ftemplate.xsn;" +
            "http%3A%2F%2F2dintranet.2degreesmobile.co.nz%2Ftools%2Frequests%2FExceptions%2FForms%2Ftemplate.xsn;" +
            "http%3A%2F%2F2d.zephire.nz%2F;" + // Referer

            "1;1;0;1;0;0;" +
            zeroedTimestamp + ";" + // A timestamp of sorts, I think?

            ";FormControl;2;5129;1033;0;17;" +
            canary

    constructor(data: SPFormData) : this(data.uuid, data.zeroedTimestamp, data.canary)

    init {
        // Same as Canary passed in initial request
    }

    override fun toString() = header
}

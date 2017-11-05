package nz.zephire.exceptions.postback.backend

import com.google.common.collect.Lists
import nz.zephire.exceptions.Utils
import nz.zephire.exceptions.postback.SPFormData
import java.io.IOException

// Used for testing, just returns static data
class FakeBackend : SPBackend {

    override val form: SPFormData
        @Throws(IOException::class)
        get() {
            Utils.debugLog("GET")
            return SPFormData("bdbaf792c2d74b1d9c441d43db4a4645_63e2b16760f74bb4a2c715e4573eeaad",
                    "636349282014412000",
                    "uG2TEcJ9jnR0s96EWLWW4yL+UHIICLVz/AZ28jQ6G2EOzmyGUtClh8r/yDKYtEAW5loNRpY4D0VEd1DTIGzfRQ==|636439601974863152",
                    Lists.newArrayList("Reina Sancho", "Deneshen Naidoo"),
                    emptyList<Any>()
            )
        }

    @Throws(IOException::class)
    override fun postForm(body: String) {
        Utils.debugLog("POST")
        Utils.debugLog(body)
        Utils.debugLog("")
    }
}

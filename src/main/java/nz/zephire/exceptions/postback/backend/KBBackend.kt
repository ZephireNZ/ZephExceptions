package nz.zephire.exceptions.postback.backend

import com.google.common.base.Joiner
import com.google.common.io.CharStreams
import nz.zephire.exceptions.Utils
import nz.zephire.exceptions.postback.SPFormData
import nz.zephire.exceptions.postback.SharePointPostback
import nz.zephire.exceptions.postback.SharePointPostback.INIT_URL
import nz.zephire.exceptions.postback.SharePointPostback.POSTBACK_URL
import java.io.IOException
import java.io.InputStreamReader
import java.net.*

class KBBackend : SPBackend {

    override val form: SPFormData
        @Throws(IOException::class)
        get() {
            val con = URL(INIT_URL).openConnection() as HttpURLConnection
            con.requestMethod = "GET"

            val body = CharStreams.toString(InputStreamReader(con.inputStream))

            Utils.debugLog("GET REQ HEADERS:")

            for ((key, value) in con.requestProperties) {
                Utils.debugLog(key + ": " + Joiner.on("; ").join(value))
            }

            Utils.debugLog("\nGET RESP HEADERS:")

            for ((key, value) in con.headerFields) {
                Utils.debugLog(key + ": " + Joiner.on("; ").join(value))
            }

            Utils.debugLog("\nGET RESP BODY:")
            Utils.debugLog(body)

            return SharePointPostback.parseFormData(body)
        }

    @Throws(IOException::class)
    override fun postForm(body: String) {
        // Write the body of the POST
        val postBody = toString().toByteArray()

        val con = URL(POSTBACK_URL).openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "text/plain")
        con.setRequestProperty("Content-Length", Integer.toString(postBody.size))

        val output = con.outputStream
        output.write(postBody)
        output.flush()
        output.close()

        con.connect()

        Utils.debugLog("POST REQ HEADERS:")

        for ((key, value) in con.requestProperties) {
            Utils.debugLog(key + ": " + Joiner.on("; ").join(value))
        }

        Utils.debugLog("\nPOST REQ BODY:")
        Utils.debugLog(body)

        Utils.debugLog("\nPOST RESP HEADERS:")

        for ((key, value) in con.headerFields) {
            Utils.debugLog(key + ": " + Joiner.on("; ").join(value))
        }

        Utils.debugLog("\nPOST RESP BODY:")
        Utils.debugLog(CharStreams.toString(InputStreamReader(con.inputStream)))
    }

    companion object {
        init {
            CookieHandler.setDefault(CookieManager(null, CookiePolicy.ACCEPT_ALL))
        }
    }
}

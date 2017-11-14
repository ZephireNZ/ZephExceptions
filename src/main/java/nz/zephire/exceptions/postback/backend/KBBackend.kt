package nz.zephire.exceptions.postback.backend

import com.google.common.base.Joiner
import com.google.common.io.CharStreams
import com.google.gson.Gson
import nz.zephire.exceptions.Utils
import nz.zephire.exceptions.postback.SPFormData
import java.io.IOException
import java.io.InputStreamReader
import java.net.*
import java.util.*
import java.util.regex.Pattern

// URL to GET, provides the initial setup of the form
private const val INIT_URL = "http://2dintranet.2degreesmobile.co.nz/tools/requests/_layouts/FormServer.aspx?" +
        "XsnLocation=http://2dintranet.2degreesmobile.co.nz/tools/requests/Exceptions/Forms/template.xsn" +
        "&SaveLocation=http%3A%2F%2F2dintranet%2E2degreesmobile%2Eco%2Enz%2Ftools%2Frequests%2FExceptions"

// URL to send the form to once complete
private const val POSTBACK_URL = "https://2dintranet.2degreesmobile.co.nz/tools/requests/_layouts/Postback.FormServer.aspx"

// Locations of important parts in currentFormData object
private const val REQUEST_UUID = 3
private const val TIMESTAMP_ZEROED = 18
private const val CANARY = 37

// Described path through tree to find important parts
val APPROVERS_UNWRAP_SEQUENCE = intArrayOf(1, 1, 0, 0, 1, 2, 2, 1, 2)

/**
 * Finds the JSON variable representing form state, and returns it
 * @param html Unencoded HTML body
 * @return [SPFormData] representing important info in state
 */
fun parseFormData(html: String): SPFormData {
    val regex = Pattern.compile("var g_objCurrentFormData_FormControl = (.*);")
    val match = regex.matcher(html)
    match.find()

    return readFormData(match.group(1))
}

// Parses "JSON of var g_objCurrentFormData_FormControl"
private fun readFormData(formData: String): SPFormData {
    val obj = Gson().fromJson(formData, List::class.java)

    // Navigates the nested arrays using given sequence
    var curr = obj
    for (i in APPROVERS_UNWRAP_SEQUENCE) {
        curr = curr[i] as List<*>
    }

    val approvers = ArrayList<String>(curr.size)
    for (approver in curr) {
        if (approver !is String) continue

        approvers.add(approver)
    }

    return SPFormData(
            obj[REQUEST_UUID] as String,
            (obj[TIMESTAMP_ZEROED] as Int).toString(),
            obj[CANARY] as String,
            approvers,
            obj
    )
}

class KBBackend : SPBackend {

    override val form: SPFormData
        @Throws(IOException::class)
        get() {
            val con = URL(INIT_URL).openConnection() as HttpURLConnection
            con.requestMethod = "GET"

            val reqProps = con.requestProperties // throws an exception if called prior to connecting

            val body = CharStreams.toString(InputStreamReader(con.inputStream))

            Utils.debugLog("GET REQ HEADERS:")

            for ((key, value) in reqProps) {
                Utils.debugLog(key + ": " + Joiner.on("; ").join(value))
            }

            Utils.debugLog("\nGET RESP HEADERS:")

            for ((key, value) in con.headerFields) {
                Utils.debugLog(key + ": " + Joiner.on("; ").join(value))
            }

            Utils.debugLog("\nGET RESP BODY:")
            Utils.debugLog(body)

            return parseFormData(body)
        }

    @Throws(IOException::class)
    override fun postForm(body: String) {
        // Write the body of the POST
        val postBody = toString().toByteArray()

        val con = URL(POSTBACK_URL).openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "text/plain")
        con.setRequestProperty("Content-Length", Integer.toString(postBody.size))

        val reqProps = con.requestProperties // throws an exception if called prior to connecting

        val output = con.outputStream
        output.write(postBody)
        output.flush()
        output.close()

        con.connect()

        Utils.debugLog("POST REQ HEADERS:")

        for ((key, value) in reqProps) {
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

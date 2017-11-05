package nz.zephire.exceptions.postback.backend

import nz.zephire.exceptions.postback.SPFormData

import java.io.IOException

interface SPBackend {

    val form: SPFormData

    @Throws(IOException::class)
    fun postForm(body: String)

}

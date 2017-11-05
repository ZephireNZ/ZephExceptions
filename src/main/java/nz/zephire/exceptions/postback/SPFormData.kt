package nz.zephire.exceptions.postback

class SPFormData(val uuid: String, val zeroedTimestamp: String, val canary: String, val approvers: List<String>, val raw: List<*>)

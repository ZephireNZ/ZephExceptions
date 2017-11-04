package nz.zephire.exceptions.postback.fields;

import nz.zephire.exceptions.postback.SPFormData;
import nz.zephire.exceptions.postback.SPPostbackObject;

public class SPHeader implements SPPostbackObject {

    private final String uuid;
    private final String zeroedTimestamp;
    private final String canary;
    private final String header;

    public SPHeader(SPFormData data) {
        this(data.getUuid(), data.getZeroedTimestamp(), data.getCanary());
    }

    public SPHeader(String uuid, String zeroedTimestamp, String canary) {

        this.uuid = uuid;
        this.zeroedTimestamp = zeroedTimestamp;
        this.canary = canary;

        this.header = "1\n11\nFormControl1268\n"
                + "1 8;0;"
                + uuid + ";" // Submission UUID
                +
                "AFUNAIHEPRJJIRM342GUZE4BMGYC2L2UJ5HUYUZPKJCVCVKFKNKFGL2FLBBUKUCUJFHU4UZPIZHVETKTF5KEKTKQJRAVIRJOLBJU4KTZMIZWKZ2GORYGKZTTIVHUQT3FJZITC53EINFW4RBUG5UXKMKJJNUG4VTSKFLVKS3HIE;" // Form UUID
                + "0;;"
                + "http%3A%2F%2F2dintranet.2degreesmobile.co.nz%2Ftools%2Frequests%2FExceptions%2FForms%2Ftemplate.xsn;"
                + "http%3A%2F%2F2dintranet.2degreesmobile.co.nz%2Ftools%2Frequests%2FExceptions%2FForms%2Ftemplate.xsn;"
                + "http%3A%2F%2F2d.zephire.nz%2F;" // Referer
                + "1;1;0;1;0;0;"
                + zeroedTimestamp + ";" // A timestamp of sorts, I think?
                + ";FormControl;2;5129;1033;0;17;"
                + canary; // Same as Canary passed in initial request
    }

    @Override
    public String toString() {
        return header;
    }

    public String getUuid() {
        return uuid;
    }

    public String getZeroedTimestamp() {
        return zeroedTimestamp;
    }

    public String getCanary() {
        return canary;
    }
}

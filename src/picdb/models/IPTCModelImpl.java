package picdb.models;

import BIF.SWE2.interfaces.models.IPTCModel;

/**
 * Created by if16b014 on 05.03.18.
 */
public class IPTCModelImpl implements IPTCModel {

    private String keywords;
    private String byLine;
    private String copyright;
    private String headline;
    private String caption;


    @Override
    public String getKeywords() {
        return keywords;
    }

    @Override
    public void setKeywords(String s) {
        keywords = s;
    }

    @Override
    public String getByLine() {
        return byLine;
    }

    @Override
    public void setByLine(String s) {
        byLine = s;
    }

    @Override
    public String getCopyrightNotice() {
        return copyright;
    }

    @Override
    public void setCopyrightNotice(String s) {
        copyright = s;
    }

    @Override
    public String getHeadline() {
        return headline;
    }

    @Override
    public void setHeadline(String s) {
        headline = s;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public void setCaption(String s) {
        caption = s;
    }
}

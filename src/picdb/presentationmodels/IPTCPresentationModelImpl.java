package picdb.presentationmodels;

import BIF.SWE2.interfaces.models.IPTCModel;
import BIF.SWE2.interfaces.presentationmodels.IPTCPresentationModel;
import picdb.models.IPTCModelImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class IPTCPresentationModelImpl implements IPTCPresentationModel {

    private IPTCModel iptc;


    public IPTCPresentationModelImpl(IPTCModel i){
        this.iptc = i;
    }

    @Override
    public String getKeywords() {
        return iptc.getKeywords();
    }

    @Override
    public void setKeywords(String s) {
        iptc.setKeywords(s);
    }

    @Override
    public String getByLine() {
        return iptc.getByLine();
    }

    @Override
    public void setByLine(String s) {
        iptc.setByLine(s);
    }

    @Override
    public String getCopyrightNotice() {
        return iptc.getCopyrightNotice();
    }

    @Override
    public void setCopyrightNotice(String s) {
        iptc.setCopyrightNotice(s);
    }

    @Override
    public Collection<String> getCopyrightNotices() {
        return new ArrayList<>(Arrays.asList("All rights reserved", "CC-BY",
                "CC-BY-SA", "CC-BY-ND", "CC-BY-NC", "CC-BY-NC-SA", "CC-BY-NC-ND"));
    }

    @Override
    public String getHeadline() {
        return iptc.getHeadline();
    }

    @Override
    public void setHeadline(String s) {
        iptc.setHeadline(s);
    }

    @Override
    public String getCaption() {
        return iptc.getCaption();
    }

    @Override
    public void setCaption(String s) {
        iptc.setCaption(s);
    }
}

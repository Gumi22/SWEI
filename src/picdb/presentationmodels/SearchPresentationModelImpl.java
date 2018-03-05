package picdb.presentationmodels;

import BIF.SWE2.interfaces.presentationmodels.SearchPresentationModel;

/**
 * Created by if16b014 on 05.03.18.
 */
public class SearchPresentationModelImpl implements SearchPresentationModel {
    @Override
    public String getSearchText() {
        return null;
    }

    @Override
    public void setSearchText(String s) {

    }

    @Override
    public boolean getIsActive() {
        return false;
    }

    @Override
    public int getResultCount() {
        return 0;
    }
}

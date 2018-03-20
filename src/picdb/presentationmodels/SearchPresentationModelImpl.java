package picdb.presentationmodels;

import BIF.SWE2.interfaces.presentationmodels.SearchPresentationModel;

/**
 * Created by if16b014 on 05.03.18.
 */
public class SearchPresentationModelImpl implements SearchPresentationModel {

    String searchText;
    boolean isActive = false;

    @Override
    public String getSearchText() {
        return searchText;
    }

    @Override
    public void setSearchText(String s) {
        searchText = s;
        if(s!= null && !s.isEmpty()){
            isActive = true;
        }else{
            isActive = false;
        }
    }

    @Override
    public boolean getIsActive() {
        return isActive;
    }

    @Override
    public int getResultCount() {
        return 0;
    }
}

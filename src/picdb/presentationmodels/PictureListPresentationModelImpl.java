package picdb.presentationmodels;

import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.PictureListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import picdb.BusinessLayerImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by if16b014 on 05.03.18.
 */
public class PictureListPresentationModelImpl implements PictureListPresentationModel {

    private ArrayList<PicturePresentationModel> l = new ArrayList<>();
    private ArrayList<ImageView> list = new ArrayList<>();
    int index = 0;
    String path = BusinessLayerImpl.getPath();

    private ObservableList<ImageView> obList = new ObservableListWrapper<ImageView>(list);

    public PictureListPresentationModelImpl(){}

    public PictureListPresentationModelImpl(Collection<PictureModel> l, ListView Parent){
        for (PictureModel pm: l) {
            ImageView imgV = new ImageView(new Image("file:" + path + "/" + pm.getFileName()));
            imgV.setPreserveRatio(true);
            imgV.setFocusTraversable(true);
            imgV.fitHeightProperty().bind(Parent.heightProperty().subtract( 30));
            list.add(imgV);
            this.l.add(new PicturePresentationModelImpl(pm));
        }
    }

    @Override
    public PicturePresentationModel getCurrentPicture() {
        return l.get(index < 0 ? 0 : index);
    }

    @Override
    public Collection<PicturePresentationModel> getList() {
        return l;
    }

    public Collection<ImageView> getImages(){return obList;}

    public void setSelected(int i){
        index = i;
    }

    @Override
    public Collection<PicturePresentationModel> getPrevPictures() {
        ArrayList<PicturePresentationModel> ret = new ArrayList<>();
        try{
            ret.add(l.get(index-1));
            try{
                ret.add(l.get(index-2));
                try{
                    ret.add(l.get(index-3));
                }catch (Exception e){
                }
            }catch (Exception e){
            }
        }catch (Exception e) {
        }
        return ret;
    }

    @Override
    public Collection<PicturePresentationModel> getNextPictures() {
        ArrayList<PicturePresentationModel> ret = new ArrayList<>();
        try{
            ret.add(l.get(index+1));
            try{
                ret.add(l.get(index+2));
                try{
                    ret.add(l.get(index+3));
                }catch (Exception e){
                }
            }catch (Exception e){
            }
        }catch (Exception e) {
        }
        return ret;
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Override
    public int getCurrentIndex() {
        return index;
    }

    @Override
    public String getCurrentPictureAsString() {
        return l.get(index).getDisplayName();
    }
}

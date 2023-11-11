package best.project.multithreading.storage;

public class Storage {

    private boolean isItemUnloaded = Boolean.TRUE;
    private int itemValue;

    public void setItem(int itemValue){
        this.itemValue = itemValue;
        isItemUnloaded = Boolean.FALSE;
    }
    public int getItem(){
        isItemUnloaded = Boolean.TRUE;
        return itemValue;
    }
    public boolean getUploadStatus(){
        return isItemUnloaded;
    }
}

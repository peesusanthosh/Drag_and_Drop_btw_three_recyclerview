package srp.com.dragndrop;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import java.util.ArrayList;

import srp.com.dragndrop.model.ListItems;
import srp.com.dragndrop.model.PassObject;

import static srp.com.dragndrop.DWEvansConstants.DONE_LIST;
import static srp.com.dragndrop.DWEvansConstants.IN_PROGRESS_LIST;
import static srp.com.dragndrop.DWEvansConstants.TO_DO_LIST;

/**
 * Created by Santhosh on 12/31/2017.
 */

public class ItemOnDragAndDropListener implements View.OnDragListener {

    private static final String TAG = ItemOnDragAndDropListener.class.getSimpleName();
    ListItems taskDetails;
    private boolean isDropped = false;

    /**
     * Constructor.
     *
     * @param taskDetails
     */
    public ItemOnDragAndDropListener(ListItems taskDetails) {
        this.taskDetails = taskDetails;
    }

    @Override
    public boolean onDrag(View v, DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.e(TAG, "Item ACTION_DRAG_STARTED: " + "\n");
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.e(TAG, "Item ACTION_DRAG_ENTERED: " + "\n");
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                Log.e(TAG, "Item ACTION_DRAG_EXITED: " + "\n");
                return true;
            case DragEvent.ACTION_DROP:
                Log.e(TAG, "Item ACTION_DROP: " + "\n");

                isDropped = true;

                PassObject passObj = (PassObject) dragEvent.getLocalState();
                View view = passObj.getView();
                ListItems passedItem = passObj.getTaskDetails();
                ArrayList<ListItems> srcList = passObj.getTaskDetailsArrayList();
                RecyclerView oldParent = (RecyclerView) view.getParent();

                ArrayList<ListItems> destList = new ArrayList<>();
                RecyclerView newParent = (RecyclerView) v.getParent();
                if (newParent.getTag().equals(TO_DO_LIST)) {
                    destList = ((ToDoAdapter) newParent.getAdapter()).getTaskDetailsArrayList();
                } else if (newParent.getTag().equals(IN_PROGRESS_LIST)) {
                    destList = ((InProgressAdapter) newParent.getAdapter()).getTaskDetailsArrayList();
                } else if (newParent.getTag().equals(DONE_LIST)) {
                    destList = ((DoneAdapter) newParent.getAdapter()).getTaskDetailsArrayList();
                }

                //If dest list is not empty then only execute this block to avoid ConcurrentModificationException
//                if(!destList.isEmpty()) {
//                    Log.e(TAG, "Dest list is NON EMPTY");
                    int removeLocation = srcList.indexOf(passedItem);
                    int insertLocation = destList.indexOf(taskDetails);

                /*
                 * If drag and drop on the same list, same position,
                 * ignore
                 */
                    if (srcList != destList || removeLocation != insertLocation) {
                        if (removeItemFromList(srcList, passedItem)) {
                            oldParent.getAdapter().notifyDataSetChanged();
                            destList.add(insertLocation, passedItem);
                        }

                        newParent.getAdapter().notifyDataSetChanged();
                    }
//                }


                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                Log.e(TAG, "Item ACTION_DRAG_ENDED: " + "\n");

                if (!isDropped) {
                    PassObject passObject = (PassObject) dragEvent.getLocalState();
                    passObject.getView().setVisibility(View.VISIBLE);
                }
                return true;

            default:
                return true;
        }
    }

    /**
     * Remove the item from recycler view list.
     *
     * @param taskDetailsArrayList
     * @param taskDetails
     * @return
     */
    private boolean removeItemFromList(ArrayList<ListItems> taskDetailsArrayList, ListItems taskDetails) {
        boolean result = taskDetailsArrayList.remove(taskDetails);
        return result;
    }
}

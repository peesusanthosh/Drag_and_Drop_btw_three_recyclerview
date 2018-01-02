package srp.com.dragndrop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import srp.com.dragndrop.model.ListItems;
import srp.com.dragndrop.model.PassObject;

import static srp.com.dragndrop.DWEvansConstants.DONE_EMPTY_LAYOUT;
import static srp.com.dragndrop.DWEvansConstants.DONE_LIST;
import static srp.com.dragndrop.DWEvansConstants.IN_PROGRESS_EMPTY_LAYOUT;
import static srp.com.dragndrop.DWEvansConstants.IN_PROGRESS_LIST;
import static srp.com.dragndrop.DWEvansConstants.TO_DO_EMPTY_LAYOUT;
import static srp.com.dragndrop.DWEvansConstants.TO_DO_LIST;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    RecyclerView rvDoneList,rvTodoList,rvInProgressList;
    LinearLayout llToDoInside,llInProgressInside,llDoneInside;
    private ArrayList<ListItems> donelist;
    private ArrayList<ListItems> toDoList;
    private ArrayList<ListItems> inProgressList;
    private DoneAdapter doneAdapter;
    private ToDoAdapter toDoAdapter;
    private InProgressAdapter inProgressAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //rvDoneList,rvTodoList,rvInProgressList;
        rvDoneList = findViewById(R.id.rv_done_list);
        rvTodoList = findViewById(R.id.rv_todo_list);
        rvInProgressList = findViewById(R.id.rv_inprogress);
        llToDoInside = findViewById(R.id.ll_to_do_inside);
        llInProgressInside = findViewById(R.id.ll_in_progress_inside);
        llDoneInside = findViewById(R.id.ll_done_inside);

        donelist = new ArrayList<>();
        toDoList = new ArrayList<>();
        inProgressList = new ArrayList<>();

        toDoList.add(new ListItems("To Do  1", "Lots of things"));
        toDoList.add(new ListItems("To Do 2", "Lots of things2"));
        toDoList.add(new ListItems("To Do 3", "Lots of things2"));
        toDoList.add(new ListItems("To Do 4", "Lots of things2"));
        toDoList.add(new ListItems("To Do 5", "Lots of things2"));
        toDoList.add(new ListItems("To Do 6", "Lots of things2"));
        toDoList.add(new ListItems("To Do 7", "Lots of things2"));


        inProgressList.add(new ListItems("WIP 1", "working on it"));
        inProgressList.add(new ListItems("WIP 2", "working on it as well"));

        donelist.add(new ListItems("Air 1", "Done Task"));
        donelist.add(new ListItems("Air 2", "Done Task"));
        donelist.add(new ListItems("Air 3", "Done Task"));

        toDoAdapter = new ToDoAdapter(toDoList);
        doneAdapter = new DoneAdapter(donelist);
        inProgressAdapter = new InProgressAdapter(inProgressList);

        rvTodoList.setLayoutManager(new GridLayoutManager(this, 1));
        rvDoneList.setLayoutManager(new GridLayoutManager(this, 1));
        rvInProgressList.setLayoutManager(new GridLayoutManager(this, 1));


        rvTodoList.setTag(TO_DO_LIST);
        rvInProgressList.setTag(IN_PROGRESS_LIST);
        rvDoneList.setTag(DONE_LIST);

        //Set adapter after setting click listeners and tag to avoid NPE.
        rvTodoList.setAdapter(toDoAdapter);
        rvDoneList.setAdapter(doneAdapter);
        rvInProgressList.setAdapter(inProgressAdapter);

        llToDoInside.setTag(TO_DO_EMPTY_LAYOUT);
        llInProgressInside.setTag(IN_PROGRESS_EMPTY_LAYOUT);
        llDoneInside.setTag(DONE_EMPTY_LAYOUT);

        llToDoInside.setOnDragListener(emptyLayoutDragAndDropListener);
        llInProgressInside.setOnDragListener(emptyLayoutDragAndDropListener);
        llDoneInside.setOnDragListener(emptyLayoutDragAndDropListener);

        ItemTouchHelper.Callback doneCallback = new SwipeDelete(doneAdapter);
        ItemTouchHelper doneItemTouchHelper = new ItemTouchHelper(doneCallback);
        doneItemTouchHelper.attachToRecyclerView(rvDoneList);

        ItemTouchHelper.Callback todoCallback = new SwipeDelete(toDoAdapter);
        ItemTouchHelper todoItemTouchHelper = new ItemTouchHelper(todoCallback);
        todoItemTouchHelper.attachToRecyclerView(rvTodoList);

        ItemTouchHelper.Callback inprogressCallback = new SwipeDelete(inProgressAdapter);
        ItemTouchHelper inprogressItemTouchHelper = new ItemTouchHelper(inprogressCallback);
        inprogressItemTouchHelper.attachToRecyclerView(rvInProgressList);


    }


    private boolean isDropped = false;
    private View.OnDragListener emptyLayoutDragAndDropListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent dragEvent) {
//            if(toDoList.isEmpty() || inProgressList.isEmpty() || donelist.isEmpty()) {
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.e(TAG, "EMPTY LAYOUT ******** Item ACTION_DRAG_STARTED: " + "\n");
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.e(TAG, "EMPTY LAYOUT ******** Item ACTION_DRAG_ENTERED: " + "\n");
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.e(TAG, "EMPTY LAYOUT ******** Item ACTION_DRAG_EXITED: " + "\n");
                    return true;
                case DragEvent.ACTION_DROP:
                    Log.e(TAG, "EMPTY LAYOUT ******** Item ACTION_DROP: " + "\n");

                    isDropped = true;

                    PassObject passObj = (PassObject) dragEvent.getLocalState();
                    View view = passObj.getView();
                    ListItems passedItem = passObj.getTaskDetails();
                    ArrayList<ListItems> srcList = passObj.getTaskDetailsArrayList();
                    RecyclerView oldParent = (RecyclerView) view.getParent();

                    ArrayList<ListItems> destList = new ArrayList<>();
                    LinearLayout newParent = (LinearLayout) v;

                    //Compare tag to get the target list
                    if (newParent.getTag().equals(TO_DO_EMPTY_LAYOUT)) {
                        destList = toDoAdapter.getTaskDetailsArrayList();
                    } else if (newParent.getTag().equals(IN_PROGRESS_EMPTY_LAYOUT)) {
                        destList = inProgressAdapter.getTaskDetailsArrayList();
                    } else if (newParent.getTag().equals(DONE_EMPTY_LAYOUT)) {
                        destList = doneAdapter.getTaskDetailsArrayList();
                    }
                    //If item is dropped into empty layout then only execute this block.
//                        if(destList.isEmpty()) {
//                            Log.e(TAG, "Dest list is empty, dropping in empty layout");

                    if (removeItemFromList(srcList, passedItem)) {
                        //Refresh old list
                        oldParent.getAdapter().notifyDataSetChanged();
                        addItemToList(destList, passedItem);
                    }



                    //Compare tag to refresh the list
                    if (newParent.getTag().equals(TO_DO_EMPTY_LAYOUT)) {
                        toDoAdapter.notifyDataSetChanged();
                    } else if (newParent.getTag().equals(IN_PROGRESS_EMPTY_LAYOUT)) {
                        inProgressAdapter.notifyDataSetChanged();
                    } else if (newParent.getTag().equals(DONE_EMPTY_LAYOUT)) {
                        doneAdapter.notifyDataSetChanged();
                    }
//                        }

                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.e(TAG, "EMPTY LAYOUT Item ACTION_DRAG_ENDED: " + "\n");

                    if (!isDropped) {
                        PassObject passObject = (PassObject) dragEvent.getLocalState();
                        passObject.getView().setVisibility(View.VISIBLE);
                    }
                    return true;

                default:
                    return true;
            }
//            }
//            return true;
        }
    };



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

    /**
     * Add item to recycler view list.
     * @param taskDetailsArrayList
     * @param taskDetails
     * @return
     */
    private boolean addItemToList(ArrayList<ListItems> taskDetailsArrayList, ListItems taskDetails){
        boolean result = taskDetailsArrayList.add(taskDetails);
        return result;
    }

}

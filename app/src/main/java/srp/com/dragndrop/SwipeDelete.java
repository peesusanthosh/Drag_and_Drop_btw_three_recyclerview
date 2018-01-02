package srp.com.dragndrop;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Adapter;

/**
 * Created by Santosh on 1/1/2018.
 */

public class SwipeDelete extends ItemTouchHelper.SimpleCallback {
    private DoneAdapter doneAdapter;
    private ToDoAdapter toDoAdapter;
    private InProgressAdapter inProgressAdapter;

    public SwipeDelete(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeDelete(DoneAdapter doneAdapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.doneAdapter = doneAdapter;
    }

    public SwipeDelete(ToDoAdapter toDoAdapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.toDoAdapter = toDoAdapter;
    }

    public SwipeDelete(InProgressAdapter inProgressAdapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.inProgressAdapter = inProgressAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        try {
            InProgressAdapter.InProgressViewHolder view1 = (InProgressAdapter.InProgressViewHolder) viewHolder;
            inProgressAdapter.dismiss(view1.getAdapterPosition());
        } catch (ClassCastException e) {
            try {
                DoneAdapter.DoneViewHolder view2 = (DoneAdapter.DoneViewHolder) viewHolder;
                doneAdapter.dismiss(view2.getAdapterPosition());
            }catch (Exception ex) {
                ToDoAdapter.ToDoViewHolder view3 = (ToDoAdapter.ToDoViewHolder) viewHolder;
                toDoAdapter.dismiss(view3.getAdapterPosition());
            }

        }
    }
}


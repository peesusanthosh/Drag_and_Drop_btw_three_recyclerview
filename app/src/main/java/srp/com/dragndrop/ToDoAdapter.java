package srp.com.dragndrop;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import srp.com.dragndrop.model.ListItems;
import srp.com.dragndrop.model.PassObject;

/**
 * Created by Santosh on 12/31/2017.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private ArrayList<ListItems> taskDetailsArrayList;

    /**
     * Constructor.
     *
     * @param taskDetailsArrayList
     */
    public ToDoAdapter(ArrayList<ListItems> taskDetailsArrayList) {
        this.taskDetailsArrayList = taskDetailsArrayList;
    }


    @Override
    public ToDoAdapter.ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_todo, null);
        // create ViewHolder
        ToDoAdapter.ToDoViewHolder todoViewHolder = new ToDoAdapter.ToDoViewHolder(itemLayoutView);
        return todoViewHolder;
    }

    @Override
    public void onBindViewHolder(ToDoAdapter.ToDoViewHolder holder, final int position) {
        holder.tvToDoTitle.setText(taskDetailsArrayList.get(position).getTitle());
        holder.tvToDoContent.setText(taskDetailsArrayList.get(position).getContent());

        holder.rowItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PassObject passObj = new PassObject(v, taskDetailsArrayList.get(position), taskDetailsArrayList);
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, passObj, 0);
                v.setVisibility(View.INVISIBLE);
//                v.setOnDragListener(new ItemOnDragAndDropListener(getTaskDetailsArrayList().get(position)));
                return true;
            }
        });
        holder.rowItem.setOnDragListener(new ItemOnDragAndDropListener(getTaskDetailsArrayList().get(position)));
    }


    @Override
    public int getItemCount() {
        return taskDetailsArrayList.size();
    }

    /**
     * Return the task details array list.
     *
     * @return ArrayList of TaskDetails
     */
    public ArrayList<ListItems> getTaskDetailsArrayList() {
        return taskDetailsArrayList;
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvToDoTitle;

        public TextView tvToDoContent;

        LinearLayout rowItem;


        public ToDoViewHolder(View itemView) {
            super(itemView);

            tvToDoTitle = itemView.findViewById(R.id.tv_to_do_title_name);
            tvToDoContent = itemView.findViewById(R.id.tv_to_do_content);
            rowItem = itemView.findViewById(R.id.ll_to_do_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    //remove item from list
    public void dismiss(int pos) {
        Log.d("demo",pos+" pos");
        if(pos !=-1) {
            taskDetailsArrayList.remove(pos);
            this.notifyItemRemoved(pos);
        }
    }
}

package srp.com.dragndrop;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
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

public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.DoneViewHolder> {

    private ArrayList<ListItems> taskDetailsArrayList;

    /**
     * Constructor.
     *
     * @param taskDetailsArrayList
     */
    public DoneAdapter(ArrayList<ListItems> taskDetailsArrayList) {
        this.taskDetailsArrayList = taskDetailsArrayList;
    }


    @Override
    public DoneAdapter.DoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_done_list, null);
        // create ViewHolder
        DoneAdapter.DoneViewHolder doneViewHolder = new DoneAdapter.DoneViewHolder(itemLayoutView);
        return doneViewHolder;
    }

    @Override
    public void onBindViewHolder(DoneViewHolder holder, final int position) {
        holder.tvDoneTitle.setText(taskDetailsArrayList.get(position).getTitle());
        holder.tvDoneContent.setText(taskDetailsArrayList.get(position).getContent());

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

    public class DoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvDoneTitle;

        public TextView tvDoneContent;

        LinearLayout rowItem;



        public DoneViewHolder(View itemView) {
            super(itemView);
            tvDoneTitle = itemView.findViewById(R.id.tv_done_title_name);
            tvDoneContent = itemView.findViewById(R.id.tv_done_content);
            rowItem = itemView.findViewById(R.id.ll_done_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    //remove item from list
    public void dismiss(int pos){
        if(pos !=-1) {
            taskDetailsArrayList.remove(pos);
            this.notifyItemRemoved(pos);
        }
    }
}

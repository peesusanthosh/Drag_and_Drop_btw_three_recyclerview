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

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.InProgressViewHolder> {

    private ArrayList<ListItems> taskDetailsArrayList;

    /**
     * Constructor.
     *
     * @param taskDetailsArrayList
     */
    public InProgressAdapter(ArrayList<ListItems> taskDetailsArrayList) {
        this.taskDetailsArrayList = taskDetailsArrayList;
    }



    @Override
    public InProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_in_progress, null);
        // create ViewHolder
        InProgressViewHolder todoViewHolder = new InProgressViewHolder(itemLayoutView);
        return todoViewHolder;
    }

    @Override
    public void onBindViewHolder(InProgressViewHolder holder, final int position) {
        holder.tvInProgressTitle.setText(taskDetailsArrayList.get(position).getTitle());
        holder.tvInProgressContent.setText(taskDetailsArrayList.get(position).getContent());

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

    public class InProgressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvInProgressTitle;

        public TextView tvInProgressContent;

        LinearLayout rowItem;

        public InProgressViewHolder(View itemView) {
            super(itemView);
            tvInProgressTitle = itemView.findViewById(R.id.tv_in_progress_title_name);
            tvInProgressContent = itemView.findViewById(R.id.tv_in_progress_content);
            rowItem = itemView.findViewById(R.id.ll_in_progress_item);
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


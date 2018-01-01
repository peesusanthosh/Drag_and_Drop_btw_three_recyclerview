package srp.com.dragndrop.model;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by Santosh on 12/31/2017.
 */

public class PassObject  {
    private View view;
    private ListItems taskDetails;
    private ArrayList<ListItems> taskDetailsArrayList;

    /**
     * Constructor.
     * @param v
     * @param taskDetails
     * @param taskDetailsArrayList
     */
    public PassObject(View v, ListItems taskDetails, ArrayList<ListItems> taskDetailsArrayList) {
        view = v;
        this.taskDetails = taskDetails;
        this.taskDetailsArrayList = taskDetailsArrayList;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public ListItems getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(ListItems taskDetails) {
        this.taskDetails = taskDetails;
    }

    public ArrayList<ListItems> getTaskDetailsArrayList() {
        return taskDetailsArrayList;
    }

    public void setTaskDetailsArrayList(ArrayList<ListItems> taskDetailsArrayList) {
        this.taskDetailsArrayList = taskDetailsArrayList;
    }

}

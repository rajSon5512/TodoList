package com.knoxpo.rajivsonawala.easytow;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
class TodoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private TodoLab tabLab;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_main,container,false);

        mRecyclerView=view.findViewById(R.id.fragment_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.action_menubar,menu);
        MenuItem menuItem=menu.findItem(R.id.add_button);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.add_button:Todo todo=new Todo();
                                  tabLab.addTodoItem(todo);
                                    return true;
            default:
                   return super.onOptionsItemSelected(item);

        }


    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Todo todoList;
        TextView mTextView;
        ImageView mImageView;

        public Holder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_view,parent,false));
            mImageView.setOnClickListener(this);

            mTextView=(TextView)itemView.findViewById(R.id.text_myString);
            mImageView=(ImageView)itemView.findViewById(R.id.delete_button);

        }

        public void bind(){



        }


        @Override
        public void onClick(View v) {

        }


    }

    public class Adapter extends RecyclerView.Adapter<Holder>{


        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}


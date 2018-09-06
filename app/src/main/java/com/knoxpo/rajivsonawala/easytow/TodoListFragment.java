package com.knoxpo.rajivsonawala.easytow;


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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TodoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;

    private ArrayList<Todo> mTodos = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mRecyclerView = view.findViewById(R.id.fragment_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {

        TodoLab todoList = TodoLab.get(getActivity());
        List<Todo> mtodoList = todoList.getTodo();
        if (mAdapter == null) {

            mAdapter = new Adapter();
            mRecyclerView.setAdapter(mAdapter);

        } else {

            mAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onResume() {
        updateUI();
        super.onResume();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_menubar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add_button:
                Todo todo = new Todo();
                mTodos.add(todo);
                mAdapter.notifyItemInserted(mTodos.size()-1);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private ImageButton mImageView;
        private int mpoition;

        public Holder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.text_myString);
            mImageView = itemView.findViewById(R.id.delete_button);

            mImageView.setOnClickListener(this);
        }

        public void bind(Todo todo,int position) {
            mTextView.setText(todo.getString());
            mpoition=position;
        }


        @Override
        public void onClick(View v) {

            mTodos.remove(mpoition);
            updateUI();
            Toast.makeText(getActivity(),"Click ="+mpoition,Toast.LENGTH_SHORT).show();
        }
    }

    public class Adapter extends RecyclerView.Adapter<Holder> {
       private LayoutInflater mInflater;

        public Adapter() {
            mInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.list_item_view, parent, false);
            return new Holder(itemView);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Todo todo = mTodos.get(position);
            holder.bind(todo,position);
        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }
    }
}

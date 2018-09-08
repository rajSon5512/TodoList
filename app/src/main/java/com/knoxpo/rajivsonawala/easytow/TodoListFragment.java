package com.knoxpo.rajivsonawala.easytow;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TodoListFragment extends Fragment {

    private static final String TAG ="Firebase" ;
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;

   private final FirebaseFirestore db=FirebaseFirestore.getInstance();
   private final CollectionReference todoList=db.collection("TodoList");
//   private final DocumentReference docRef = db.collection("TodoList").document("");

    private ArrayList<Todo> mTodos = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        todoList.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> documents=task.getResult().getDocuments();
                for(int i=0;i<documents.size();i++){
                    Todo mTodo=new Todo();

                    mTodo.setId(UUID.fromString(documents.get(i).getId()));
                  //  String text=documents.get(i).getString("text");
                    mTodo.setString(documents.get(i).getString("data"));
                    mTodos.add(mTodo);
                    Log.d(TAG, "onComplete: "+mTodo.getId());
                    Log.d(TAG, "onComplete: "+mTodo.getString());
                }
                mAdapter.notifyDataSetChanged();
            }
        });



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
                Map<String,Object> todoData=new HashMap<String,Object>();
                todoData.put("data", todo.getString());
                mTodos.add(todo);
                mAdapter.notifyItemInserted(mTodos.size()-1);
                todoList.document(String.valueOf(todo.getId())).set(todoData);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private ImageButton mImageView;
        private Todo mBoundTodo;

        public Holder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.text_myString);
            mImageView = itemView.findViewById(R.id.delete_button);

            mImageView.setOnClickListener(this);
        }

        public void bind(Todo todo) {
            mBoundTodo = todo;
            mTextView.setText(todo.getString());
        }


        @Override
        public void onClick(View v) {

            if(mTodos.isEmpty()){

                Toast.makeText(getActivity(),"Empty",Toast.LENGTH_SHORT).show();

            }else {

                int position = mTodos.indexOf(mBoundTodo);
                if(position >= 0){

                    todoList.document(String.valueOf(mTodos.get(position).getId())).delete();
                    mTodos.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }

                //Toast.makeText(getActivity(), "Click =" + mpoition, Toast.LENGTH_SHORT).show();

            }

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
            holder.bind(todo);
        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }
    }
}

package com.knoxpo.rajivsonawala.easytow;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TodoLab {

    private static TodoLab sTodoLab;


    private List<Todo> mList;

    public static TodoLab get(Context context) {
        if (sTodoLab == null) {
            sTodoLab= new TodoLab(context);
        }

        return sTodoLab;
    }

    public TodoLab(Context context){
     mList=new ArrayList<>();
    }

    public void addTodoItem(Todo c){

        mList.add(c);


    }

    public List<Todo> getTodoItem(){
        
        return mList;

    }

    public Todo getTodo(UUID id){

        for(Todo tempDo:mList ){

            if(tempDo.getId().equals(id)){

                return tempDo;
            }

        }

    return null;
    }

}

package com.pengbo.mydemo.dagthreadexecutor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class TreeTask<T> extends RecursiveTask<T> {

    private T name;

    private String type;

    private List<TreeTask<T>> parentTasks = new ArrayList<>();

    private List<TreeTask<T>> childTasks = new ArrayList<>();

    public TreeTask(T name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    protected T compute() {

        // join parentTasks
        String parentResult = "";
        for (TreeTask<T> task : parentTasks) {
            parentResult += task.join() + "-";
        }
        System.out.println("[" + name + "]" + Thread.currentThread().getName() + " join parentTasks results : " + parentResult);

        // process itself
        parentResult += "-" + name;
        System.out.println("[" + name + "]" + Thread.currentThread().getName() + " process itself results : " + parentResult);


        // fork childTasks
        System.out.println("[" + name + "]" + Thread.currentThread().getName() + " fork childTasks");
        for (TreeTask<T> task : childTasks) {
            task.fork();
        }

        return (T) parentResult;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TreeTask<T>> getParentTasks() {
        return parentTasks;
    }

    public void setParentTasks(List<TreeTask<T>> parentTasks) {
        this.parentTasks = parentTasks;
    }

    public List<TreeTask<T>> getChildTasks() {
        return childTasks;
    }

    public void setChildTasks(List<TreeTask<T>> childTasks) {
        this.childTasks = childTasks;
    }
}

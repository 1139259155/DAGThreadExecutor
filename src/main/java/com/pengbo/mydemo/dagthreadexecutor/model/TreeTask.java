package com.pengbo.mydemo.dagthreadexecutor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class TreeTask<T> extends RecursiveTask<T> {

    private T name;

    private String type;

    private long sleepTime;

    private long beginTime;

    private int isFork;


    private List<TreeTask<T>> parentTasks = new ArrayList<>();

    private List<TreeTask<T>> childTasks = new ArrayList<>();

    public TreeTask(T name, String type, long sleepTime, long beginTime) {
        this.name = name;
        this.type = type;
        this.sleepTime = sleepTime;
        this.beginTime = beginTime;
    }

    @Override
    protected T compute() {
        String parentResult = "";
        try {

            long beginTime = System.currentTimeMillis();
            System.out.println("[" + name + "]" + Thread.currentThread().getName() + " beginTime : " + (beginTime - this.beginTime));

            // join parentTasks
            for (TreeTask<T> task : parentTasks) {
                parentResult += task.join() + "-";
            }
            System.out.println("[" + name + "]" + Thread.currentThread().getName() +
                    " wait... : " + (System.currentTimeMillis() - beginTime) +
                    " results : " + parentResult);

            // process itself
            parentResult += "-" + name;
            System.out.println("[" + name + "]" + Thread.currentThread().getName() + " process itself results : " + parentResult);
            Thread.sleep(sleepTime);

            // fork childTasks
            for (TreeTask<T> task : childTasks) {
                // fork前0
                // fork后，执行完成前 状态status=0，
                // 执行完成后 status<0
                if (task.isFork == 0) {
                    task.fork();
                    task.isFork=1;
                }
            }
            System.out.println("[" + name + "]" + Thread.currentThread().getName() + " fork childTasks, " +
                    "endTime : " + System.currentTimeMillis() +
                    " total cost : " + (System.currentTimeMillis() - beginTime));

        } catch (Exception e) {

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


    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public int getIsFork() {
        return isFork;
    }

    public void setIsFork(int isFork) {
        this.isFork = isFork;
    }
}

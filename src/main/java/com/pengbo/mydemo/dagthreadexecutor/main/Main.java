package com.pengbo.mydemo.dagthreadexecutor.main;

import com.pengbo.mydemo.dagthreadexecutor.model.TreeTask;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class Main {


    public static void main(String[] args) throws Exception {

        long beginTime = System.currentTimeMillis();
        // build task
        TreeTask<String> topology1 = new TreeTask("topology1", "topology", 1000, beginTime);

        TreeTask<String> layer1 = new TreeTask("layer1", "layer", 1000, beginTime);
        TreeTask<String> layer2 = new TreeTask("layer2", "layer", 1000, beginTime);

        TreeTask<String> node1 = new TreeTask("node1", "node", 1000, beginTime);
        TreeTask<String> node2 = new TreeTask("node2", "node", 1000, beginTime);
        TreeTask<String> node3 = new TreeTask("node3", "node", 1000, beginTime);
        TreeTask<String> snode1 = new TreeTask("snode1", "node", 5000, beginTime);

        TreeTask<String> link1 = new TreeTask("link1", "link", 1000, beginTime);

        // build task dependies
        topology1.setChildTasks(Arrays.asList(layer1, layer2));

        layer1.setParentTasks(Arrays.asList(topology1));
        layer1.setChildTasks(Arrays.asList(node1, node2));

        node1.setParentTasks(Arrays.asList(layer1));
        node1.setChildTasks(Arrays.asList(snode1));

        node2.setParentTasks(Arrays.asList(layer1));
        node2.setChildTasks(Arrays.asList(link1));

        snode1.setParentTasks(Arrays.asList(node1));
        snode1.setChildTasks(Arrays.asList(link1));

        link1.setParentTasks(Arrays.asList(snode1, node2));

        layer2.setParentTasks(Arrays.asList(topology1));
        layer2.setChildTasks(Arrays.asList(node3));

        node3.setParentTasks(Arrays.asList(layer2));

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        System.out.println("begin: " + beginTime);
        Future<String> future = forkJoinPool.submit(topology1);
        String result = future.get();

        //String result=forkJoinPool.invoke(topology1);

        Thread.sleep(20000);

        System.out.println(result);

    }
}
package edu.wpi.teamname.views.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class RevisionManager {

    private static final RevisionManager instance = new RevisionManager();
    private QueueStack<List<Action>> queueStackNormal;
    private QueueStack<List<Action>> queueStackReverse;
    private HashMap<String, Snapshot> snapshotMap;

    private List<String> actionTracker;
    private List<String> lastActionSnapshot = new ArrayList();
    public static synchronized RevisionManager getInstance() {
        return instance;
    }

    private RevisionManager() {
        queueStackNormal = new QueueStack<>();
        queueStackReverse = new QueueStack<>();
        actionTracker = new ArrayList<>();
    }

    public void execute(List<Action> actionList) {
        if (!(queueStackReverse.isEmpty()) && !(actionTracker.contains(actionList.get(0)))) {
            actionTracker.clear();
            clearReverse();
            clearNormal();
        }
        actionList.forEach(Action::execute);
        queueStackNormal.push(actionList);
        if (actionList.size() == 2) {
            if (actionList.get(0).isNode()) {
//                if (!(actionHistory.get(actionHistory.size() - 1).equals("Edited Node: " + actionList.get(0).getLongName() + "-execute"))) {


                    actionTracker.add(actionList.get(0).checkChangesDo() + "-execute");
                    lastActionSnapshot.clear();
                lastActionSnapshot.add(actionList.get(0).checkChangesDo() + "-execute");

               // }
            }
            return;
        }
        lastActionSnapshot.clear();
        actionList.forEach(a -> actionTracker.add(a.getActionName()));
        actionList.forEach(a -> lastActionSnapshot.add(a.getActionName()));
    }
//    public void execute(Action action){
//        if(!(queueStackReverse.isEmpty()) && !(actionHistory.contains(action))){
//            actionHistory.clear();
//            clearReverse();
//            clearNormal();
//        }
//        action.execute();
//        List<Action> singleAction = new ArrayList<>();
//        singleAction.add(action);
//        queueStackNormal.push(singleAction);
//         actionHistory.add(action.getActionName());
//
//    }

    public void undo() {
        lastActionSnapshot.clear();
        if(queueStackNormal.isEmpty()){return;}
        Optional<List<Action>> optionalActions = queueStackNormal.pop();
        optionalActions.ifPresent(aList -> {
            aList.forEach(Action::undo);
//            System.out.println((aList.get(0)));
//            System.out.println(aList.get(1));
            queueStackReverse.push(aList);
//            if(aList.isEmpty()){return;}
            if (aList.size() == 2) {
             //   System.out.println("undonee");
                if (aList.get(0).isNode()) {
                    lastActionSnapshot.clear();
                    actionTracker.add(aList.get(0).checkChangesUndo() + "-undo");
                    lastActionSnapshot.add(aList.get(0).checkChangesUndo() + "-undo");
                }
                return;
            }
            aList.forEach(a -> actionTracker.add(a.getActionName() + " - undo"));
            lastActionSnapshot.clear();
            aList.forEach(a -> lastActionSnapshot.add(a.getActionName() + " - undo"));
        });
    }

    public void redo() {
        lastActionSnapshot.clear();
        if(queueStackReverse.isEmpty()){ return;}
        Optional<List<Action>> optionalActions = queueStackReverse.pop();
        optionalActions.ifPresent(aList -> {
            aList.forEach(Action::execute);
            queueStackNormal.push(aList);
            if (aList.size() == 2) {
                if (aList.get(0).isNode()) {

                       actionTracker.add(aList.get(0).checkChangesDo() + "-redo");
                    lastActionSnapshot.add(aList.get(0).checkChangesDo() + "-redo");
                }
                return;
            }
            aList.forEach(a -> actionTracker.add(a.getActionName() + " - redo"));
            lastActionSnapshot.clear();
            aList.forEach(a -> lastActionSnapshot.add(a.getActionName() + " - undo"));
        });
    }

    public void clearNormal() {
        queueStackNormal.clear();
    }

    public void clearReverse() {
        queueStackReverse.clear();
    }

    public void clearQueues() {
        clearNormal();
        clearReverse();
    }
    public boolean normalQueueIsEmpty(){
        return normalQueueIsEmpty();
    }

    public boolean reverseQueueIsEmpty(){
        return reverseQueueIsEmpty();
    }
    public List<String> getActionHistory() {
        System.out.println(lastActionSnapshot.size());
        return lastActionSnapshot;
    }

//        public static void main(String[] args) {
//            CommandManager manager = CommandManager.getInstance();
//            List<Action> actionList = new ArrayList<>();
//            actionList.add(new Action1("Action 1"));
//            actionList.add(new Action2("Action 2"));
//            System.out.println("===ACTIONS===");
//            manager.execute(actionList);
//
//            manager.undo();
//            manager.redo();
//
//            manager.clearNormal();
//            manager.undo();
//            manager.redo();
//
//            System.out.println("===HISTORY===");
//            System.out.println(manager.getActionHistory().toString());
//        }


}

package edu.wpi.teamname.views.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RevisionManager {

        private static final RevisionManager instance = new RevisionManager();
        private QueueStack<List<Action>> queueStackNormal;
        private QueueStack<List<Action>> queueStackReverse;

        private List<String> actionHistory;

       public static synchronized RevisionManager getInstance(){return instance;}

        private RevisionManager() {
            queueStackNormal = new QueueStack<>();
            queueStackReverse = new QueueStack<>();
            actionHistory = new ArrayList<>();
        }

        public void execute(List<Action> actionList){
            actionList.forEach(Action::execute);
            queueStackNormal.push(actionList);
            actionList.forEach(a -> actionHistory.add(a.getActionName()));
        }
    public void execute(Action action){
        if(!(queueStackReverse.isEmpty()) && !(actionHistory.contains(action))){
            actionHistory.clear();
            clearReverse();
            clearNormal();
        }
        action.execute();
        List<Action> singleAction = new ArrayList<>();
        singleAction.add(action);
        queueStackNormal.push(singleAction);
         actionHistory.add(action.getActionName());

    }

        void undo() {
            Optional<List<Action>> optionalActions = queueStackNormal.pop();
            optionalActions.ifPresent(aList -> {
                aList.forEach(Action::undo);
                queueStackReverse.push(aList);
                aList.forEach(a -> actionHistory.add(a.getActionName() + " - undo"));
            });
        }

        void redo() {
            Optional<List<Action>> optionalActions = queueStackReverse.pop();
            optionalActions.ifPresent(aList -> {
                aList.forEach(Action::execute);
                queueStackNormal.push(aList);
                aList.forEach(a -> actionHistory.add(a.getActionName() + " - redo"));
            });
        }

        void clearNormal() {
            queueStackNormal.clear();
        }

        void clearReverse() {
            queueStackReverse.clear();
        }

        List<String> getActionHistory() {
            return actionHistory;
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

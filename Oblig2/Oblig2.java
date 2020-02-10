import java.io.*;
import java.util.*;

class Task {
  int id, time, staff;
  String name;
  int earliestStart, latestStart;
  int latestFinish;
  List<Task> outEdges = new ArrayList<Task>();
  int cntPredecessors;

  int slack;
  int[] dependencies;

  public Task(int id, String name, int time, int staff, int[] dependencies){
    this.id = id;
    this.name = name;
    this.time = time;
    this.staff = staff;
    this.dependencies = dependencies;
  }

  //latest possible finishing time for a task without delaying the project


  // public check(){
  //   try{
  //     if(i > 0 && i <= num_tasks){
  //
  //     }
  //   }
  //   catch (IndexOutOfBoundsException e) {
  //     System.out.println("try another ID");
  //   }
  // }

  public void setEarlyStart(HashSet<Task> visited){
    if(cntPredecessors == 0){
      earliestStart = 0;
      visited.add(this);
    }
    for(Task o : outEdges){
      if(o.earliestStart < earliestStart+time){
        o.earliestStart = earliestStart+time;
        visited.add(this);
      }
      o.setEarlyStart(visited);
    }
  }

  public void printInfo(){
    System.out.println("Identity number: " + id);
    System.out.println("Name: " + name);
    System.out.println("Time needed to finish the task: " + time);
    System.out.println("Manpower required to complete the task: " + staff);
    System.out.println("Earliest starting time: " + earliestStart);
    System.out.println("Slack: " + slack);
    System.out.println("Critical?: "+(slack==0));
    System.out.print("OutEdges: ");
    for(Task u: outEdges){
      System.out.print(u.id+" ");
    }
    System.out.println();
  }
}
class Project{
  int num_tasks;
  HashMap<Integer,Task> TaskList = new HashMap<Integer,Task>();
  int earliestFinish = 0;
  String cycle = "";

  public Project(File f) throws FileNotFoundException{
    Scanner sc = new Scanner(f);
    String linje = sc.nextLine();
    num_tasks = Integer.parseInt(linje);

    while(sc.hasNextLine()){
      linje = sc.nextLine();

      while(linje.isEmpty()){
        linje = sc.nextLine();
      }

      String[] ind = linje.split("\\s+");
      int tID = Integer.parseInt(ind[0]);
      String tName = ind[1];
      int tTime = Integer.parseInt(ind[2]);
      int tMP = Integer.parseInt(ind[3]);
      int[] dependencies = new int[ind.length-5];

      for(int i=0; i<ind.length-5;i++){
        dependencies[i] = Integer.parseInt(ind[i+4]);
      }

      Task task = new Task(tID, tName, tTime, tMP, dependencies);
      task.cntPredecessors = dependencies.length;
      TaskList.put(tID, task);
    }

    for(Task t:TaskList.values()){
      for(int i: t.dependencies){
          TaskList.get(i).outEdges.add(t);
      }
    }


  }

  //time schedule - starting time, finishing time, manpower used
  //list of all tasks -ID, name, time needed, manpower, earliest starting time, slack, dependencies
  public boolean isCycle(){
    HashSet<Task> start = new HashSet<Task>();
    HashSet<Task> end = new HashSet<Task>();

    for(Task s: TaskList.values()){
      start.add(s);
    }
    while(start.size() > 0){
      Task i = start.iterator().next();
      if(findCycle(i,new HashSet<Task>(), start, end)){
        return true;
      }
    }
    return false;
  }
  public boolean findCycle(Task i, HashSet<Task> nodes, HashSet<Task> start, HashSet<Task> end){
    start.remove(i);
    nodes.add(i);
    for(Task t: i.outEdges){
      if(end.contains(t)){
        continue;
      }
      if(nodes.contains(t)){
        for(Task p: nodes){
          cycle += " -> " + p.id;
        }
        return true;
      }
      if(findCycle(t, nodes, start, end)){
        return true;
      }
    }

    nodes.remove(i);
    end.add(i);
    return false;
  }

  public ArrayList<Task> TopologiskSort(){
    Queue<Task> q = new LinkedList<Task>();
    ArrayList<Task> ordered = new ArrayList<Task>();
    for(Task t : TaskList.values()){
      if(t.cntPredecessors == 0){
        t.earliestStart = 0;
        q.add(t);
        ordered.add(t);
      }
    }
    while(!q.isEmpty()){
      Task v = q.poll();
      for(Task x : v.outEdges){
        x.cntPredecessors--;
        if(x.cntPredecessors == 0){
          q.add(x);
          ordered.add(x);
        }
      }
    }
    return ordered;
  }

  public void OptimalTimeSchedule(){
    ArrayList<Task> start_tasks = new ArrayList<Task>();
    HashSet<Task> visited = new HashSet<Task>();
    int time = 0;
    for(Task t: TaskList.values()){
      System.out.println(t.id + "ID : " + t.time + "earlies Start :" + t.earliestStart);
      if(time < t.earliestStart+t.time){
        time = t.earliestStart + t.time;
        System.out.println(t.id);
      }
      // if((t.earliestStart+t.time) > earliestFinish){
      //   earliestFinish = (t.earliestStart + t.time);
      //   System.out.println(t.earliestStart);
      // }
      // for(Task k: t.outEdges){
      //   if(k.earliestStart < (t.earliestStart+t.time)){
      //     k.earliestStart = t.earliestStart+t.time;
      //   }
      // }
      if(t.dependencies.length == 0){
        start_tasks.add(t);
      }
    }
    earliestFinish = time;

    for(Task t : start_tasks){
      if(!visited.contains(t)){
        t.setEarlyStart(visited);
      }
    }

    ArrayList<Task> topSort = this.TopologiskSort();
    for(int i=topSort.size()-1; i>-1; i--){
      Task o = topSort.get(i);
      if(o.outEdges.size()==0){
        o.latestFinish = earliestFinish;
        o.latestStart = earliestFinish - o.time;
      }else{
        int y = Integer.MAX_VALUE;
        for(Task t : o.outEdges){

          if(y > t.latestStart){
            y = t.latestStart;
          }
        }
        o.latestFinish = y;
        o.latestStart = o.latestFinish - o.time;

      }
      o.slack = o.latestStart - o.earliestStart;
    }
  }

  public void executionOutput(){
    this.OptimalTimeSchedule();
    // int mp = 0;
    // for(int i=0; i<=earliestFinish; i++){
    //   boolean newStage = false;
    //   for(Task y:TaskList.values()){
    //     if(y.earliestStart==i){
    //       if(!newStage){
    //         System.out.println("Time:"+i);
    //         newStage = true;
    //       }
    //       System.out.println("Starting: "+y.id);
    //       mp += y.staff;
    //     } else if ((y.earliestStart+y.time)==i) {
    //       if(!newStage){
    //         System.out.println("Time:"+i);
    //         newStage = true;
    //       }
    //       System.out.println("Finished: "+y.id);
    //       mp -= y.staff;
    //     }
    //   }
    //   if(newStage){
    //     System.out.println("Current staff: "+mp);
    //     System.out.println();
    //   }
    // }
    System.out.println("**** Shortest possible project execution is "+earliestFinish+" ****");
  }

  public void listTask() {
    if(this.isCycle()){
      System.out.println("This project is unrealizable.");
      System.out.println("cycle found: " + cycle);
      System.out.println();
      System.exit(0);
    }
    for(Task t: TaskList.values()){
      t.printInfo();
      System.out.println("");
    }
  }
}
class Oblig2 {
  public static void main(String[] args){
    //realisability - cycles find one -print cycle terminate
    //ideal time schedule
    //  delayed - slack, critical = 0 slack
    // overall project completion time
    //dependency

    File fil = new File(args[0]);
    Project pro = null;
    try{
      pro = new Project(fil);
    } catch(FileNotFoundException e) {
      System.out.println("File not found");
      System.exit(0);
    }

    pro.executionOutput();
    System.out.println("");
    System.out.println("");
    pro.listTask();
  }
}

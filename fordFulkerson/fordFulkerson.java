import java.util.LinkedList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
// import java.util.*;

class newmaxflow{
  public Kattio io;
  public int[][] c;
  public int[][] f;
  public int[][] rc;
  public int[] p;
  public int v;
  public int s;
  public int t;
  public int e;
  public int count;
  public int oldv;
  public int max_flow;
  public double i;
  public double max_cap=0;
  public double delta;
  public double smax=1;

  public ArrayList<ArrayList<Integer>> Graph ;


  boolean bfs( int s, int t) {

          boolean visited[] = new boolean[v+1];
          for(int i=0; i<v; ++i)
              visited[i]=false;
          LinkedList<Integer> q = new LinkedList<Integer>();
          q.add(s);
          visited[s] = true;
          p[s] = -1; // s has no p (source)
          int b=0;
          while (q.size() != 0 ) {
              int a = q.poll();

              for(int i = 0; i < Graph.get(a).size(); i++){

                  b = Graph.get(a).get(i);

                  if ( rc[a][b] > 0 && visited[b]==false && rc[a][b] >= delta){
                      q.add(b);
                      p[b] = a;
                      visited[b] = true;
                  }
              }

          }
          return (visited[t] == true);
      }

  void readFlowGraph(){

    oldv=io.getInt();
    s=io.getInt();
    t=io.getInt();

    e=io.getInt();
    v = oldv + 1;
    c = new int[v][v]; // capacity
    f = new int[v][v]; // flow
    rc = new int[v][v]; // residual capacity
    p = new int[v];

    // initializing the graphc with empty lists
    Graph = new ArrayList<ArrayList<Integer>>();

    for(int x=0; x < v ; x++){
       ArrayList<Integer> adj = new ArrayList<Integer>();
       Graph.add(adj);
    }

    if (s==t){
      int a = io.getInt();
      int b = io.getInt();
      int cap = io.getInt();
      count=0;                  
      max_flow = cap;          
      return;
    }

    for(int i = 0; i < e; i++){
        int a = io.getInt();
        int b = io.getInt();
        int cap = io.getInt();

        if(cap > max_cap) max_cap = cap;

        Graph.get(a).add(b);
        Graph.get(b).add(a);


        c[a][b]= cap;
        f[a][b]=0;
        f[b][a]=0;
        rc[a][b]=cap;
        if(c[b][a]!=0){ 
          rc[b][a]=c[b][a];
        }

        if(a == s && smax < c[a][b]){
          smax = c[a][b];
        }
    }


    int d_counter=1;
    while(delta <= smax ){
      delta = Math.pow(2,d_counter);
      d_counter++;
    }

    delta = Math.pow(2,d_counter-2);

    while(delta >= 1){

        boolean visited[][] = new boolean[v+1][v+1];
        while (bfs( s, t)  ) {

            int r = Integer.MAX_VALUE;
                for (int b = t ; b != s; b = p[b]) { // find bottleneck capacity
                    int a = p[b];
                    r = Math.min(r, rc[a][b]);
                }
                for (int b=t; b != s; b=p[b]) {
                    int a = p[b];
                      f[a][b] += r;
                      f[b][a] = -f[a][b];
                      rc[a][b] = c[a][b] - f[a][b];
                      rc[b][a] = c[b][a] - f[b][a];

                }
                max_flow += r;
        }
        delta = delta / 2 ;
      }

    }

  void writeFlowGraph(){
    io.println(oldv);
    io.println(s + " " + t + " " +  max_flow );

    for (int i = 1; i <= oldv; i++) {

        for (int j = 1; j <= oldv; j++) {
            if (f[i][j] > 0 ){
              count++;
            }
        }
    }
    io.println(count);

    for (int i = 1; i <= oldv; i++) {
        for (int j = 1; j <= oldv; j++) {
            if (f[i][j] > 0 && !(f[i][j] > c[i][j])){
              io.println(i + " " + j + " " + f[i][j]);
            }
        }
    }


    io.flush();

  }

  newmaxflow(){
    io = new Kattio(System.in,System.out);


    readFlowGraph();

    writeFlowGraph();
    io.close();
  }

  public static void main(String[] args) {
    new newmaxflow();
  }

}

